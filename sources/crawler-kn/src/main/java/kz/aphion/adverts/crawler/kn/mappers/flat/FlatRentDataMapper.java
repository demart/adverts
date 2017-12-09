package kz.aphion.adverts.crawler.kn.mappers.flat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.crawler.kn.KnDataManager;
import kz.aphion.adverts.crawler.kn.QueryBuilder;
import kz.aphion.adverts.crawler.kn.mappers.AbstractAdvertMapper;
import kz.aphion.adverts.crawler.kn.mappers.CommonMapperUtils;
import kz.aphion.adverts.crawler.kn.persistence.KnResidentialComplex;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mongodb.morphia.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;

/**
 * Класс конвертирует объявления об арнеде квартир
 * 
 * @author artem.demidovich
 *
 */

public class FlatRentDataMapper extends AbstractAdvertMapper<Advert> {

	private static Logger logger = LoggerFactory.getLogger(FlatRentDataMapper.class);
	
	public FlatRentDataMapper(Advert realty) {
		super(realty);
	}
	
	@Override
	public void mapAdvertData(String advert, QueryBuilder queryBuilder, KnAdvertCategoryType advertType) throws ParseException, CrawlerException {

		Document adv = Jsoup.parse(advert);
		
	    //Уникальный номер объявления
		realty.source.externalId  = CommonMapperUtils.convertId(adv.select("span.advert-number").text());
		
		realty.type = AdvertType.REALTY;
		realty.subType = RealtyType.FLAT.toString();
		realty.operation = AdvertOperationType.RENT;
		
		if (advertType == KnAdvertCategoryType.RENT_APARTMENT_DAILY)
			realty.data.put("rentPeriod", FlatRentPeriodType.DAILY);
		if (advertType == KnAdvertCategoryType.RENT_APARTMENT)
			realty.data.put("rentPeriod", FlatRentPeriodType.MONTHLY);
		
		//Время публикации.
		//Установим время на 23-59-59, это необходимо для проверки на обновления
		//На  kn.kz обновления происходят раз в сутки, соотвественно если сегодня объявление
		//уже было забрано, то второй раз это делать не надо.
		realty.publishedAt = CommonMapperUtils.convertAddDate(adv.select("span.date").text());
		
		//Основные данные: количество комнат, этаж, площади, тип дома, год и тд
		//Находятся в таблице, поэтому следующий цикл для парсинга данных параметров
		Elements trs = adv.select("table").first().select("tr");
		for (Element tr : trs) {
			
			if (tr.select("th").text().equals("Этаж")) {
				if (FlatDataMapperUtils.convertFloor(tr.select("td").text()) != null)
					realty.data.put("flatFloor", FlatDataMapperUtils.convertFloor(tr.select("td").text()));
				if (FlatDataMapperUtils.convertHouseFloorCount(tr.select("td").text()) != null)
					realty.data.put("houseFloorCount", FlatDataMapperUtils.convertHouseFloorCount(tr.select("td").text()));
			}
	
			if (tr.select("th").text().equals("Количество комнат"))
				realty.data.put("rooms", FlatDataMapperUtils.convertLiveRooms(tr.select("td").text()));
			
			if (tr.select("th").text().equals("Площадь")) 
				realty.data.put("square", FlatDataMapperUtils.convertSquare(tr.select("td").text()));
			
			if (tr.select("th").text().equals("Мебель")) 
				realty.data.put("furnitureType", FlatDataMapperUtils.convertFurniture(tr.select("td").text()));
			
			if (tr.select("th").text().equals("Название комплекса")) {
				try {
					String complexName = FlatDataMapperUtils.convertComplexName(tr.select("td").text());
					String regionName = CommonMapperUtils.getRegionName(adv.select("div.address").text());
					KnResidentialComplex complexEntity = KnDataManager.getResidentalComplex(complexName, regionName);
					if (complexEntity != null) {
						// Convert to DBObject
						Mapper mapper = new Mapper();
						mapper.getConverters().addConverter(CalendarConverter.class);
						DBObject complexDBO = mapper.toDBObject(complexEntity.complex);
						logger.info("{}", complexDBO);
						realty.data.put("residentalComplex", complexDBO);
					} else {
						// If not found, what to do
					}
				} catch (Exception e) {
					logger.warn("WARN ResidentialComplex not found, error: " + e.getMessage());
 					logger.debug("Error during trying to parse ResidentialComplex", e);
				}
			}
		}
		
		//Цена
		realty.price = FlatDataMapperUtils.convertPrice(adv.select("span.price").text());
		
		//В некоторых объявлениях отсутствует описание, поэтому необходима такая проверка
		if (adv.select("div.description").size() > 0)
			realty.data.put("text", adv.select("div.description").select("p").text());
		
		//Работа с телефонами
		String[] phones = CommonMapperUtils.convertPhonesNumber (adv.select("div.phones").text());
		realty.publisher.phones = new ArrayList<String>();
		for (String phone : phones) {
			realty.publisher.phones.add(phone);
		}
		
		//Работа с фотографиями
		//Выдергиваем картинки из объявления
		Elements images = adv.select("div.image-preview-list").select("div.image-item");
		ArrayList<String> linkImages = new ArrayList<String>();
		
		for (Element image : images) {
			String linkToImage = null;
			linkToImage = queryBuilder.buildQueryUrlForImage(image.select("a[href]").first().attr("href"));
			linkImages.add(linkToImage);
		}
		
		List<AdvertPhoto> photos = CommonMapperUtils.convertPhotos(linkImages);
		if (photos.size() > 0)
			realty.photos = photos;
		
		//Ищем к какому объекту из регионов относится. Варианты: область, город, район
		realty.location.externalRegionId = CommonMapperUtils.getRegionName(adv.select("div.address").text());

		
		
		//Улица и номер дома
		realty.location.streetName = CommonMapperUtils.getStreetName(adv.select("div.col-content").select("h1").text());
		if (CommonMapperUtils.getHouseNumber(adv.select("div.col-content").select("h1").text()) != null)
			realty.location.houseNumber = CommonMapperUtils.getHouseNumber(adv.select("div.col-content").select("h1").text());
	
		//Кто сдает: частное лицо или компания
		if (adv.select("div.contact-name").size() > 0) {
			if (adv.select("div.contact-name").select("strong").text().equals("Частное лицо"))
				realty.publisher.type = AdvertPublisherType.OWNER;
			else {
				if (adv.select("div.company-main-info").size() > 0) {
					realty.publisher.type = AdvertPublisherType.AGENT_COMPANY;
					if (adv.select("div.field").size() > 0)
						if (adv.select("div.field").get(0).select("a[href]").size() > 0)
							if (CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text()) != null)
								realty.publisher.name = CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text());
				
				}
				
				else {
					realty.publisher.type = AdvertPublisherType.AGENT;
					if (adv.select("div.field").size() > 0)
						if (adv.select("div.field").get(0).select("a[href]").size() > 0)
							if (CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text()) != null)
								realty.publisher.name = CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text());
					}
				}
		}
		else {
			realty.publisher.type = AdvertPublisherType.AGENT_COMPANY;
			if (adv.select("div.field").size() > 0)
				if (adv.select("div.field").get(0).select("a[href]").size() > 0)
					if (CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text()) != null)
						realty.publisher.name = CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text());
		}
	}
	
}
