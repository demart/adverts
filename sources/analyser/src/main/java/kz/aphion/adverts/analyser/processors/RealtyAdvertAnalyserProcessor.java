package kz.aphion.adverts.analyser.processors;

import java.util.Calendar;

import javax.jms.JMSException;

import kz.aphion.adverts.analyser.mq.AnalyserProcessStatus;
import kz.aphion.adverts.analyser.mq.ProcessRealtyModel;
import kz.aphion.adverts.analyser.mq.QueueNameConstants;
import kz.aphion.adverts.analyser.mq.RealtyAnalyserToSubscriptionProcessModel;
import kz.aphion.adverts.analyser.providers.ActiveMqProvider;
import kz.aphion.adverts.analyser.utils.MessageUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RealtyAdvertAnalyserProcessor implements AdvertAnalyserProcessor  {
	
	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertAnalyserProcessor.class);
	
	/**
	 * Метод является входной точкой для обработки запроса на анализ объявления поступающие от краулера
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		ProcessRealtyModel model = MessageUtils.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message not belongs to ProcessRealtyModel");
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
	private void processNewAdvert(ProcessRealtyModel model) throws JMSException, Exception {
		// Отправляем объявление в систему подписок
		logger.debug("Realty model with id: "+ model.advertId + " is NEW and will be send to subscription module.");
		
		// Формируем модель
		RealtyAnalyserToSubscriptionProcessModel analyserModel = new RealtyAnalyserToSubscriptionProcessModel();
		analyserModel.advertId = model.advertId;
		analyserModel.oldAdvertId = model.oldAdvertId;
		analyserModel.eventTime = Calendar.getInstance();
		analyserModel.operation = model.operation;
		analyserModel.type = model.type;
		analyserModel.status = AnalyserProcessStatus.NEW;
		
		// Сериализуем
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String text = gson.toJson(analyserModel);
		
		// Отправляем в очередь подписок
		ActiveMqProvider.getInstance().sendTextMessageToQueue(QueueNameConstants.MQ_REALTY_ADVERTS_SUBSCRIPTION_QUEUE, text);
		// Отправляем в очередь live подписок
		ActiveMqProvider.getInstance().sendTextMessageToTopic(QueueNameConstants.MQ_REALTY_ADVERTS_SUBSCRIPTION_LIVE_QUEUE, text);
		
		logger.debug("Realty model with id: "+ model.advertId + " was sent to subscription module.");
	}
	
	/**
	 * Метод обрабатывает все существующие но обновленные объявления
	 * @param model
	 */
	private void processExistingAdvert(ProcessRealtyModel model) {
		logger.debug("Realty model with id: "+ model.advertId + " is UPDATED, for now skipped");
		// Что делать если в подписках фигурирует старое объявление которое было заменнено новым?
		// Надо как-то обработать эту ситуацию
		
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
	}
	
}
