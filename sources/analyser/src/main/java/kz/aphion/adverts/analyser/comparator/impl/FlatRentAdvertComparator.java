package kz.aphion.adverts.analyser.comparator.impl;

import kz.aphion.adverts.analyser.comparator.AdvertComparator;
import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.models.mq.adverts.AnalyserProcessStatus;
import kz.aphion.adverts.models.realty.FlatRentAdvertModel;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertStatus;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;

import org.mongodb.morphia.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;

/**
 * 
 * Класс выполнает функцию сравнения старого и нового объявления об арнеде недвижимости.
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
public class FlatRentAdvertComparator implements AdvertComparator {

	private static Logger logger = LoggerFactory.getLogger(FlatRentAdvertComparator.class);
	
	/*
	 * НА ДАННЫЙ МОМЕНТ БУДЕТ РЕАЛИЗОВАН ОЧЕНЬ ПРОСТОЙ АЛГОРИТМ СРАВНЕНИЯ ОСНОВАННЫЙ
	 * НА 2-3 ПАРАМЕТРАХ БЕЗ СЛОЖНОЙ ПРОВЕРКИ
	 * 	 * 
	 * У объектов продажи нежвижимости должны быть учтены основные условия включая следующие:
	 * 1. Цена (Больше Меньше Такая же) 
	 * 2. Фото (Не было, Стало, Так же, Новые появились)
	 * 3. ЖК   (Не было, Указали, Так же или изменилось но всё равно также)
	 * 4. Улучшились критерии квартиры (Например сначала ничего не указали но потом решили и указали топ)
	 * 5. ....
	 * 
	 * 6. ВОЗМОЖНО НУЖНО СРАВНИВАТЬ ДАТУ ПУБЛИКАЦИЙ ЧТОБЫ ПОНЯТЬ ЧТО НОВОЕ ОБЪЯВЛЕНИЕ (
	 * ТОЧНЕЕ СТАРОЕ НО УЖЕ ОПУБЛИКОВАНО ПО НОВОМУ, через пару недель или месяцев)
	 * 
	 */

	
	@Override
	public AnalyserProcessStatus compare(Advert newAdvert, Advert oldAdvert) {
		logger.debug("Start comparing new advert {} with old advert {}", newAdvert.id, oldAdvert.id);
		
		if (newAdvert == null || newAdvert.status != AdvertStatus.ACTIVE) {
			// ERROR ... MUST BE ACTIVE
			logger.warn("New advert with id {} not found, please check why it could happened", newAdvert.id);
			return null;
		}
		
		if (oldAdvert == null || oldAdvert.status != AdvertStatus.ARCHIVED) {
			// ERROR ... MUST BE ARCHIEVED
			logger.warn("Old advert with id {} not found, please check why it could happened", oldAdvert.id);
			return AnalyserProcessStatus.SAME; // Раз старое объявление не найдено, то пока считаем что новое есть новое и кого нужно уведомляем
		}
		
		// Сравниваем изменения в цене
		AnalyserProcessStatus changeStatus = comparePrices(newAdvert, oldAdvert);
		if (changeStatus == null || changeStatus == AnalyserProcessStatus.SAME) {
			// Другие проверки
			logger.debug("New advert with id {} and old advert id {} has the same prices, will check photo and complexes", newAdvert.id, oldAdvert.id);
			
			// TODO Add Photo check
			changeStatus = comparePhotos(newAdvert, oldAdvert);
			if (changeStatus == null || changeStatus == AnalyserProcessStatus.SAME) {
				logger.debug("New advert with id {} and old advert id {} has the same photos, will check complexes", newAdvert.id, oldAdvert.id);
				
				// TODO Add Residential Complex
				changeStatus = compareResidentialComplexes(newAdvert, oldAdvert);
				if (changeStatus == null || changeStatus == AnalyserProcessStatus.SAME) {
					// Nothing more to check
					
				} else {
					logger.debug("New advert with id {} and old advert id {} has not the same residential complexes data. Status was changed to {}", newAdvert.id, oldAdvert.id, changeStatus);
					return changeStatus;
				}
				
			} else {
				logger.debug("New advert with id {} and old advert id {} has not the same photo data. Status was changed to {}", newAdvert.id, oldAdvert.id, changeStatus);
				return changeStatus;
			}

		} else {
			// Но если цена изменилась, то это серьезно и нужно уведомлять пользователя
			logger.debug("In new advert with id {} price was changed, status is {}", newAdvert.id, changeStatus);
			return changeStatus;
		}
		
		logger.debug("New advert with id {} and old advert with id {} seems the same...", newAdvert.id, oldAdvert.id);
		return AnalyserProcessStatus.SAME; // Ничего не поменялось
	}

	
	/**
	 * Сравнивает изменения в ЖК.
	 * НА ДАННЫЙ МОМЕНТ СРАВНИВАЕТ ТОЛЬКО ЕСЛИ ИЗМЕНИЛОСЬ СОСТОЯНИЕ ЖК
	 * @param newAdvert
	 * @param oldAdvert
	 * @return
	 */
	private AnalyserProcessStatus compareResidentialComplexes(Advert newAdvert, Advert oldAdvert) {
		if (newAdvert.data == null) {
			logger.warn("New advert with id {} has no data, strange..", newAdvert.id);
			return null;
		}
		
		if (oldAdvert.data == null) {
			logger.warn("Old advert with id {} has no data, new advert by default better now..", oldAdvert.id);
			return AnalyserProcessStatus.BETTER;
		}
		
		Mapper mapper = new Mapper();
		mapper.getConverters().addConverter(CalendarConverter.class);
		ResidentialComplex oldComplex = (DBObject)oldAdvert.data.get("residentalComplex") != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)oldAdvert.data.get("residentalComplex"), mapper.createEntityCache()) : null;
		ResidentialComplex newComplex = (DBObject)newAdvert.data.get("residentalComplex") != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)newAdvert.data.get("residentalComplex"), mapper.createEntityCache()) : null;
		
		
		if (newComplex != null && oldComplex == null) {
			logger.debug("New advert with id {} and old advert with id {} different. New advert has residendial id {}", newAdvert.id, oldAdvert.id, newComplex.id);
			return AnalyserProcessStatus.BETTER;
		}
		
		if (newComplex == null && oldComplex != null) {
			return AnalyserProcessStatus.WORSTE;
		}

		if (newComplex == null && oldComplex == null) {
			logger.debug("New advert with id {} and old advert with id {} seems the same NULL residential complexes...", newAdvert.id, oldAdvert.id);
			return AnalyserProcessStatus.SAME;
		}
		
		if (newComplex.id == oldComplex.id) {
			logger.debug("New advert with id {} and old advert with id {} seems has the same residential comlpex {}...", newAdvert.id, oldAdvert.id, newComplex.id);
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
	private AnalyserProcessStatus comparePhotos(Advert newAdvert, Advert oldAdvert) {
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
	private AnalyserProcessStatus comparePrices(Advert newAdvert, Advert oldAdvert) {
		Long newPrice = new FlatRentAdvertModel(newAdvert).getDataModel().getPrice();
		Long oldPrice = new FlatRentAdvertModel(oldAdvert).getDataModel().getPrice();
		/// Проверка цены
		if (newPrice == null && oldPrice == null) {
			// Аномально, но ничего не поменялось
			// 
		} else {
			if (newPrice == null && oldPrice != null) {
				return AnalyserProcessStatus.WORSTE;
			}
			
			if (newPrice != null && oldPrice == null) {
				return AnalyserProcessStatus.BETTER;
			}
			
			if (newPrice != null && oldPrice != null) {
				if (newPrice > oldPrice) {
					logger.debug("New advert with id {} has higher price {} than old advert id {} with price {} ", newAdvert.id, newPrice, oldAdvert.id, oldPrice);
					return AnalyserProcessStatus.WORSTE;
				}
				
				if (newPrice < oldPrice) {
					logger.debug("New advert with id {} has lower price {} than old advert id {} with price {} ", newAdvert.id, newPrice, oldAdvert.id, oldPrice);					
					return AnalyserProcessStatus.BETTER;
				}
				
				if (newPrice == oldPrice) {
					logger.debug("New advert with id {} and old advert id {} have the same price {} ", newAdvert.id, oldAdvert.id, newPrice);
					return AnalyserProcessStatus.SAME;
				}
			}
		}
		
		return AnalyserProcessStatus.SAME;
	}
	
}
