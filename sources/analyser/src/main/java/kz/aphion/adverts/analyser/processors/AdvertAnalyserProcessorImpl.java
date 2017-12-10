package kz.aphion.adverts.analyser.processors;

import java.util.Calendar;
import java.util.List;

import javax.jms.JMSException;

import kz.aphion.adverts.analyser.comparator.AdvertComparator;
import kz.aphion.adverts.analyser.comparator.AdvertComparatorFactory;
import kz.aphion.adverts.analyser.mq.AnalyserProcessStatus;
import kz.aphion.adverts.analyser.mq.RealtyAnalyserToSubscriptionProcessModel;
import kz.aphion.adverts.analyser.utils.MessageUtils;
import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.models.mq.adverts.ProcessModel;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.persistence.adverts.Advert;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class AdvertAnalyserProcessorImpl implements AdvertAnalyserProcessor  {
	
	private static Logger logger = LoggerFactory.getLogger(AdvertAnalyserProcessorImpl.class);
	
	/**
	 * Метод является входной точкой для обработки запроса на анализ объявления поступающие от краулера
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		ProcessModel model = MessageUtils.parseModel(message);
		if (model == null || model.status == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message not belongs to ProcessModel");
			return;
		}

		// Обработка без группировок
		// В этой схеме рассматриваются только простые объявления которые могут быть:
		//	1. Новыми, их сравнивать не нужно просто отравляем на обработку в модуль подписок
		//	2. Существующими, их сравнивать нужно для того чтобы решить
		//		2.1 Новое объявление (например когда опубликовали после длительного периода
		//		2.2 Объявление улучшилось (цена изменилась или фотки появились)
		//		2.3 Объявление ухудшилось (цена увеличилась, например)
		
		switch(model.status) {
			case NEW:
				processNewAdvert(model);
				break;
			case UPDATED:
				processExistingAdvert(model);
				break;
			default:
				logger.warn("Found unknown status " + model.status + " processing will be skipped.");
		}
		
	}
	
	
	/**
	 * Метод обрабатывает все новые объявления
	 * @param model
	 * @throws Exception 
	 * @throws JMSException 
	 */
	private void processNewAdvert(ProcessModel model) throws JMSException, Exception {
		// Отправляем объявление в систему подписок
		logger.debug("Advert model with id: "+ model.advertId + " is NEW and will be send to subscription module.");
		
		// Формируем модель
		RealtyAnalyserToSubscriptionProcessModel analyserModel = new RealtyAnalyserToSubscriptionProcessModel();
		analyserModel.advertId = model.advertId;
		analyserModel.oldAdvertId = model.oldAdvertId;
		analyserModel.eventTime = Calendar.getInstance();
		analyserModel.status = AnalyserProcessStatus.NEW;
		
		// Сериализуем
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String text = gson.toJson(analyserModel);
		
		// Отправляем в очередь подписок
		MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.ADVERTS_SUBSCRIPTION_QUEUE.getValue(), text);
		// Отправляем в очередь live подписок
		MQ.INSTANCE.sendTextMessageToTopic(QueueNameConstants.ADVERTS_SUBSCRIPTION_LIVE_QUEUE.getValue(), text);
		
		logger.debug("Advert model with id: "+ model.advertId + " was sent to subscription & subscription-live modules.");
	}
	
	/**
	 * Метод обрабатывает все существующие но обновленные объявления
	 * @param model
	 */
	private void processExistingAdvert(ProcessModel model) throws JMSException, Exception  {
		logger.debug("Advert model with id: "+ model.advertId + " is UPDATED, for now skipped");
		// Что делать если в подписках фигурирует старое объявление которое было заменнено новым?
		// Надо как-то обработать эту ситуацию
		
		
		// 1. Пришло объявление которое изменилось
		// 2. Есть два ID объявлений которые необходимо обработать
		// 3. Необходимо выполнить сравнение объектов 
		// 4. Достать подходящий сравниватель и получить результат оценки Лучше/Хуже/Не изменилось
		// 5. Софрмировать и отправить в подписки
		
		Advert oldAdvert = getAdvertObject(model.oldAdvertId);
		Advert newAdvert = getAdvertObject(model.advertId);
		
		AdvertComparator comparator = AdvertComparatorFactory.getAdvertComparatorInstance(newAdvert, oldAdvert);
		if (comparator == null) {
			logger.warn("New advert.id {} is null, analyzer will skip processing, check why it may happened", model.advertId);
			return;
		}
		
		AnalyserProcessStatus comparationStatus = comparator.compare(newAdvert, oldAdvert);
		
		// Если нужно, то можно что-то сделать на основе статуса, уведомить или еще что нить
		if (comparationStatus == null) {
			// Возникли ошибки которые невозможно обработать и отправить объявление 
			// в дальнешую обраотку.
			// Это может произойти при кординальноим изменении объявления или удаления обявления и т.д.
			
			logger.warn("Advert model with id: "+ model.advertId + " was not sent to subscription & subscription-live modules, because it was impossible to process");
			return;
		}
		
		// Формируем модель
		RealtyAnalyserToSubscriptionProcessModel analyserModel = new RealtyAnalyserToSubscriptionProcessModel();
		analyserModel.advertId = model.advertId;
		analyserModel.oldAdvertId = model.oldAdvertId;
		analyserModel.eventTime = Calendar.getInstance();
		analyserModel.status = comparationStatus;
		
		// Сериализуем
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String text = gson.toJson(analyserModel);
		
		// Отправляем в очередь подписок
		MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.ADVERTS_SUBSCRIPTION_QUEUE.getValue(), text);
		// Отправляем в очередь live подписок
		MQ.INSTANCE.sendTextMessageToTopic(QueueNameConstants.ADVERTS_SUBSCRIPTION_LIVE_QUEUE.getValue(), text);
		
		logger.debug("Advert model with id: "+ model.advertId + " was sent to subscription & subscription-live modules.");
		
	}		
		
		
		
		// Сравнить две версии объявления
		//	Если есть отличия то
		//		Обработать
		//	Если нет отличий (например даты разные но в пределах одной недели) то
		//		TODO НУЖНО ПОДУМАТЬ ЧТО ДЕЛАТЬ, ТАК КАК ЕСТЬ ЕЩЕ ПОДПИСКИ
		// Достать все объявления в группе
		//	Проверить являются ли они до сих пор группой
		//		Если не являются то
		//			Удалить объявление из группы
		//			Найти все объявления похожие объявления куда теперь отнести объявлени
		//				Если нету похожих объявлений то
		//					Создать новую группу объявления
		//					Отправить уведомление системе подписок
		//				Если есть похожие то посмотреть в какие группы они относяться
		//					Если одна и таже группа то
		//						Пересчитать кто является самый лучшим объявлением
		//						Отправить уведомление системе подписок
		//					Если разные группы то 
		//						Это проблема, нужто что-то сделать
		//		Если являются частью той же самой группы то
		//			Пересчитать кто является самым лучшим
		//			Отправить уведомление системе подписок

	
	private Advert getAdvertObject(String advertId) {
		Query<Advert> q = DB.DS().createQuery(Advert.class);
		 
		q.field("id").equal(new ObjectId(advertId));
		 
		List<Advert> result = q.asList();
		
		if (result.size() > 0)
			return result.get(0);
		return null;
		
	}

	
}
