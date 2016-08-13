package kz.aphion.adverts.analyser.processors;

import kz.aphion.adverts.analyser.mq.ProcessRealtyModel;
import kz.aphion.adverts.analyser.searcher.DuplicateSearcher;
import kz.aphion.adverts.analyser.searcher.DuplicateSearcherFactory;
import kz.aphion.adverts.analyser.utils.MessageUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Класс обработчик сообщений о недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class RealtyAdvertAnalyserProcessor implements AdvertAnalyserProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertAnalyserProcessor.class);
	
	/**
	 * Метод является входной точкой для обработки запроса на анализ объявления поступающие от краулера
	 * @param message
	 */
	public void processMessage(String message) {
		ProcessRealtyModel model = MessageUtils.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message not belongs to ProcessRealtyModel");
			return;
		}

		// Если новое объявление то нужно получить похожие и сравнить на похожесть
			// Если есть похожие то нужно оценить и добавить в группу
			// Если нет похожих то создать новую группу
		// Если сущаствующие нужно проверить что изменилось
			// Если изменения значительные то нужно прогнать по группе и сделать переоценку
		
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
	 */
	private void processNewAdvert(ProcessRealtyModel model) {
		// Достать все похожие объявления
		// Найти похожие объявления
		//	Если есть похожие то посмотреть что в какие группы входят
		//		Если группа одна то добавить в группу
		//			Возможно пересичтать группу для того чтобы найти лучшее
		//		Если групп несколько то это проблема, так как такого быть по хорошему не должно
		//			Пока можно добавить в группу с большим совпадением
		//	Если нет похожих объявлений то создаем группу
		// Отправляем сообщение в очередь подписок для дальнейшей обработки
		
		// Достаем запись из БД
		
		
		// Создаем подходящего искателя дубликатов
		DuplicateSearcher searcher = DuplicateSearcherFactory.getDuplicateSearcherInstance(model);
		searcher.searchDuplicates(model.advertId);
		
	}
	
	/**
	 * Метод обрабатывает все существующие но обновленные объявления
	 * @param model
	 */
	private void processExistingAdvert(ProcessRealtyModel model) {
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
