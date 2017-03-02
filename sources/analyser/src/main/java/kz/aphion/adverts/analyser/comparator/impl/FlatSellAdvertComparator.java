package kz.aphion.adverts.analyser.comparator.impl;

import java.util.List;

import kz.aphion.adverts.analyser.comparator.AdvertComparator;
import kz.aphion.adverts.analyser.mq.AnalyserProcessStatus;
import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс выполнает функцию сравнения старого и нового объявления о продажи недвижимости.
 * На основе внутренней логики принимает решение о том в какюу сторону изменился новый объект
 * по сравнению со старым.
 * 
 * Логика зашита в коде, при улучшениях системы необходимо предусмотреть вынос логики
 * в отдельную систему правил BRMS
 * 
 * КАК ВАРИАНТ МОЖНО СДЕЛАТЬ СИСТЕМУ РЕЙТИНГОВ ДЛЯ КАЖДОГО ВИДА ОБЪЯВЛЕНИЯ
 * ПОТОМ ВЫСЧИТЫВАТЬ КОЛ-ВО ОЧКОВ У НОВОГО И СТАРОГО ОБЪЯВЛЕНИЯ И ЕСЛИ 
 * ОЧКОВ БОЛЬШЕ У НОВОГО ТО ЛУЧШЕ ЕСЛИ ОЧКОВ МЕНЬШЕ ТО ХУЖЕ, ЕСЛИ СТОЛЬКО ЖЕ ТО ОК
 * 
 * @author artem.demidovich
 *
 * Created at Feb 27, 2017
 */
public class FlatSellAdvertComparator implements AdvertComparator {

	private static Logger logger = LoggerFactory.getLogger(FlatSellAdvertComparator.class);
	
	/*
	 * НА ДАННЫЙ МОМЕНТ БУДЕТ РЕАЛИЗОВАН ОЧЕНЬ ПРОСТОЙ АЛГОРИТМ СРАВНЕНИЯ ОСНОВАННЫЙ
	 * НА 2-3 ПАРАМЕТРАХ БЕЗ СЛОЖНОЙ ПРОВЕРКИ
	 * 
	 * У объектов продажи нежвижимости должны быть учтены основные условия включая следующие:
	 * 1. Цена (Больше Меньше Такая же) 
	 * 2. Фото (Не было, Стало, Так же)
	 * 3. ЖК   (Не было, Указали, Так же или изменилось но всё равно также)
	 * 4. Улучшились критерии квартиры (Например сначала ничего не указали но потом решили и указали топ)
	 * 5. ....
	 * 
	 * 6. ВОЗМОЖНО НУЖНО СРАВНИВАТЬ ДАТУ ПУБЛИКАЦИЙ ЧТОБЫ ПОНЯТЬ ЧТО НОВОЕ ОБЪЯВЛЕНИЕ (
	 * ТОЧНЕЕ СТАРОЕ НО УЖЕ ОПУБЛИКОВАНО ПО НОВОМУ, через пару недель или месяцев)
	 * 
	 * Приоритетность:
	 * 1. Цена (если она не поменялась то идем дальше)
	 * 2. Фото (если оно не поменялось то идем дальше)
	 * 3. ЖК   (если не поменялось то идем дальше)
	 * 
	 */

	
	@Override
	public AnalyserProcessStatus compare(String newAdvertId, String oldAdvertId) {
		logger.debug("Start comparing new advert {} with old advert {}", newAdvertId, oldAdvertId);
		
		FlatSellRealty newAdvert = getAdvertObject(newAdvertId);
		if (newAdvert == null || newAdvert.status != RealtyAdvertStatus.ACTIVE) {
			// ERROR ... MUST BE ACTIVE
			logger.warn("New advert with id {} not found, please check why it could happened", newAdvertId);
			return null;
		}
		
		FlatSellRealty oldAdvert = getAdvertObject(oldAdvertId);
		if (oldAdvert == null || oldAdvert.status != RealtyAdvertStatus.ARCHIVED) {
			// ERROR ... MUST BE ARCHIEVED
			logger.warn("Old advert with id {} not found, please check why it could happened", oldAdvertId);
			return AnalyserProcessStatus.NEW; // Раз старое объявление не найдено, то пока считаем что новое есть новое и кого нужно уведомляем
		}
		
		// Сравниваем изменения в цене
		AnalyserProcessStatus changeStatus = comparePrices(newAdvert, oldAdvert);
		if (changeStatus == null || changeStatus == AnalyserProcessStatus.SAME) {
			// Другие проверки
			logger.debug("New advert with id {} and old advert id {} has the same prices, will check photo and complexes", newAdvertId, oldAdvertId);
			
			// TODO Add Photo check
			changeStatus = comparePhotos(newAdvert, oldAdvert);
			if (changeStatus == null || changeStatus == AnalyserProcessStatus.SAME) {
				logger.debug("New advert with id {} and old advert id {} has the same photos, will check complexes", newAdvertId, oldAdvertId);
				
				// TODO Add Residential Complex
				changeStatus = compareResidentialComplexes(newAdvert, oldAdvert);
				if (changeStatus == null || changeStatus == AnalyserProcessStatus.SAME) {
					// Nothing more to check
					
				} else {
					logger.debug("New advert with id {} and old advert id {} has not the same residential complexes data. Status was changed to {}", newAdvertId, oldAdvertId, changeStatus);
					return changeStatus;
				}
				
			} else {
				logger.debug("New advert with id {} and old advert id {} has not the same photo data. Status was changed to {}", newAdvertId, oldAdvertId, changeStatus);
				return changeStatus;
			}

		} else {
			// Но если цена изменилась, то это серьезно и нужно уведомлять пользователя
			logger.debug("In new advert with id {} price was changed, status is {}", newAdvertId, changeStatus);
			return changeStatus;
		}
		
		logger.debug("New advert with id {} and old advert with id {} seems the same...", newAdvertId, oldAdvertId);
		return AnalyserProcessStatus.SAME; // Ничего не поменялось
	}

	
	/**
	 * Сравнивает изменения в ЖК.
	 * НА ДАННЫЙ МОМЕНТ СРАВНИВАЕТ ТОЛЬКО ЕСЛИ ИЗМЕНИЛОСЬ СОСТОЯНИЕ ЖК
	 * @param newAdvert
	 * @param oldAdvert
	 * @return
	 */
	private AnalyserProcessStatus compareResidentialComplexes(FlatSellRealty newAdvert, FlatSellRealty oldAdvert) {
		if (newAdvert.data == null) {
			logger.warn("New advert with id {} has no data, strange..", newAdvert.id);
			return null;
		}
		
		if (oldAdvert.data == null) {
			logger.warn("Old advert with id {} has no data, new advert by default better now..", oldAdvert.id);
			return AnalyserProcessStatus.BETTER;
		}
		
		if (newAdvert.data.residentalComplex != null && oldAdvert.data.residentalComplex == null) {
			logger.debug("New advert with id {} and old advert with id {} different. New advert has residendial id {}", newAdvert.id, oldAdvert.id, newAdvert.data.residentalComplex.id);
			return AnalyserProcessStatus.BETTER;
		}
		
		if (newAdvert.data.residentalComplex == null && oldAdvert.data.residentalComplex != null) {
			return AnalyserProcessStatus.WORSTE;
		}

		if (newAdvert.data.residentalComplex == null && oldAdvert.data.residentalComplex == null) {
			logger.debug("New advert with id {} and old advert with id {} seems the same NULL residential complexes...", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.SAME;
		}
		
		if (newAdvert.data.residentalComplex.id == oldAdvert.data.residentalComplex.id) {
			logger.debug("New advert with id {} and old advert with id {} seems has the same residential comlpex {}...", newAdvert.id, oldAdvert.id, newAdvert.data.residentalComplex.id);
			return AnalyserProcessStatus.SAME;
		}
		
		return AnalyserProcessStatus.SAME;
	}
	
	/**
	 * Сравнивает изменения в фотограциях.
	 * НА ДАННЫЙ МОМЕНТ СРАВНИВАЕТ ТОЛЬКО ЕСЛИ ИЗМЕНИЛОСЬ КОЛ-ВО ФОТО
	 * @param newAdvert
	 * @param oldAdvert
	 * @return
	 */
	private AnalyserProcessStatus comparePhotos(FlatSellRealty newAdvert, FlatSellRealty oldAdvert) {
		if (newAdvert.hasPhoto == null && oldAdvert.hasPhoto == null) {
			logger.debug("New advert with id {} and old advert with id {} has not photo", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.SAME;
		}
		
		if (newAdvert.hasPhoto != null && oldAdvert.hasPhoto == null) {
			logger.debug("New advert with id {} and old advert with id {}. New advert has photo", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.BETTER;
		}
		
		if (newAdvert.hasPhoto == null && oldAdvert.hasPhoto != null) {
			logger.debug("New advert with id {} and old advert with id {}. New advert has no photo now", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.WORSTE;
		}
		
		if (newAdvert.hasPhoto == oldAdvert.hasPhoto) {
			logger.debug("New advert with id {} and old advert with id {}. New and Old photos status the same", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.SAME;
		}
		
		if (newAdvert.hasPhoto == false && oldAdvert.hasPhoto == true) {
			logger.debug("New advert with id {} and old advert with id {}. In new advert photo was removed.", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.WORSTE;
		}
		
		if (newAdvert.hasPhoto == true && oldAdvert.hasPhoto == false) {
			logger.debug("New advert with id {} and old advert with id {}. In new advert photo was added.", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.BETTER;
		}
		
		return AnalyserProcessStatus.SAME;
	}
	
	/**
	 * Сравнивает цены, решение в лоб, нужно заменять потом
	 * @param newAdvert
	 * @param oldAdvert
	 * @return
	 */
	private AnalyserProcessStatus comparePrices(FlatSellRealty newAdvert, FlatSellRealty oldAdvert) {
		/// Проверка цены
		if (newAdvert.price == null && oldAdvert.price == null) {
			// Аномально, но ничего не поменялось
			// 
		} else {
			if (newAdvert.price == null && oldAdvert.price != null) {
				return AnalyserProcessStatus.WORSTE;
			}
			
			if (newAdvert.price != null && oldAdvert.price == null) {
				return AnalyserProcessStatus.BETTER;
			}
			
			if (newAdvert.price != null && oldAdvert.price != null) {
				if (newAdvert.price > oldAdvert.price) {
					logger.debug("New advert with id {} has higher price {} than old advert id {} with price {} ", newAdvert.id, newAdvert.price, oldAdvert.id, oldAdvert.price);
					return AnalyserProcessStatus.WORSTE;
				}
				
				if (newAdvert.price < oldAdvert.price) {
					logger.debug("New advert with id {} has lower price {} than old advert id {} with price {} ", newAdvert.id, newAdvert.price, oldAdvert.id, oldAdvert.price);					
					return AnalyserProcessStatus.BETTER;
				}
				
				if (newAdvert.price == oldAdvert.price) {
					logger.debug("New advert with id {} and old advert id {} have the same price {} ", newAdvert.id, oldAdvert.id, newAdvert.price);
					return AnalyserProcessStatus.SAME;
				}
			}
		}
		
		return AnalyserProcessStatus.SAME;
	}
	
	private FlatSellRealty getAdvertObject(String advertId) {
		Query<FlatSellRealty> q = DB.DS().createQuery(FlatSellRealty.class);
		 
		//q.field("status").equal(RealtyAdvertStatus.ACTIVE);
		q.field("id").equal(new ObjectId(advertId));
		 
		List<FlatSellRealty> result = q.asList();
		
		if (result.size() > 0)
			return result.get(0);
		return null;
		
	}

}
