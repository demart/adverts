package kz.aphion.adverts.crawler.kn.mappers.flat;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
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
import kz.aphion.adverts.persistence.adverts.AdvertPublisherCompany;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mongodb.morphia.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;

/**
 * Класс конвертирует объявления о продаже или арнеде квартир
 * 
 * @author denis.krylov
 *
 */
public class FlatSellDataMapper extends AbstractAdvertMapper<Advert> {

	private static Logger logger = LoggerFactory.getLogger(FlatSellDataMapper.class);
		

	public FlatSellDataMapper(Advert realty) {
		super(realty);
	}
	
	@Override
	public void mapAdvertData (String advert, QueryBuilder queryBuilder, KnAdvertCategoryType advertType) throws ParseException, CrawlerException {
		realty.data = new HashMap<String, Object>();
		
		Document adv = Jsoup.parse(advert);
		
	    //Уникальный номер объявления
		realty.source.externalId  = CommonMapperUtils.convertId(adv.select("span.advert-number").text());
		
		realty.type = AdvertType.REALTY;
		realty.subType = RealtyType.FLAT.toString();
		realty.operation = AdvertOperationType.SELL;
		
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
			
			if (tr.select("th").text().equals("Площадь")) {
				realty.data.put("square", FlatDataMapperUtils.convertSquare(tr.select("td").text()));
				
				if (FlatDataMapperUtils.convertLivingSquare(tr.select("td").text()) != null)
					realty.data.put("squareLiving", FlatDataMapperUtils.convertLivingSquare(tr.select("td").text()));
				
				if (FlatDataMapperUtils.convertKitchenSquare(tr.select("td").text()) != null)
					realty.data.put("squareKitchen", FlatDataMapperUtils.convertKitchenSquare(tr.select("td").text()));
				
				}
			
		   	 if (tr.select("th").text().equals("Год постройки"))
		   		realty.data.put("houseYear", FlatDataMapperUtils.convertHouseYear(tr.select("td").text()));
			
			
    		 if (tr.select("th").text().equals("В залоге"))
    			 realty.data.put("mortgageStatus", FlatDataMapperUtils.getFlatMortgageStatus(tr.select("td").text()));

			
			if (tr.select("th").text().equals("Материал стен"))
				realty.data.put("flatBuildingType", FlatDataMapperUtils.getFlatBuildingType(tr.select("td").text()));
			
			
			if (tr.select("th").text().equals("Название ЖК")) {
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
						realty.data.put("residentialComplex", complexDBO);
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
		realty.data.put("price", FlatDataMapperUtils.convertPrice(adv.select("span.price").text()));
		
		//В некоторых объявлениях отсутствует описание, поэтому необходима такая проверка
		if (adv.select("div.description").size() > 0)
			realty.data.put("text", adv.select("div.description").select("p").text());
		
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

		//Работа с телефонами
		if (adv.select("span.con-pers__phone").size() > 0) {
			realty.publisher.phones = new ArrayList<String>();
			for (Element element : adv.select("span.con-pers__phone")) {
				realty.publisher.phones.add(element.text());
			}
		}
		
		//Кто продает: частное лицо или компания
		if (adv.select("section.con-info").size() > 0) {
			// Если частное лицо
			String agentName = adv.select("h4.con-info__title").text();
			if ("Частное лицо".equals(agentName)) {
				realty.publisher.type = AdvertPublisherType.OWNER;
			}
			
			if ("Частный риэлтор".equals(agentName)) {
				realty.publisher.type = AdvertPublisherType.AGENT;
				realty.publisher.name = adv.select("a.con-pers__rielt").text();
			}
			
			if (agentName.contains("Риэлтор агентства")) {
				realty.publisher.type = AdvertPublisherType.AGENT_COMPANY;
				realty.publisher.name = adv.select("a.con-pers__rielt").text();
				realty.publisher.company = new AdvertPublisherCompany();
				realty.publisher.company.name = adv.select("h4.con-info__title").select("a.con-info__link").text();
			}
			
			if (agentName.contains("Агентство недвижимости")) {
				realty.publisher.type = AdvertPublisherType.AGENT_COMPANY;
				realty.publisher.name = adv.select("h4.con-info__title").select("a.con-info__link").text();
				realty.publisher.company = new AdvertPublisherCompany();
				realty.publisher.company.name = realty.publisher.name;
			}
			
			if (agentName.contains("Девелоперская")) {
				realty.publisher.type = AdvertPublisherType.COMPANY;
				realty.publisher.name = adv.select("a.con-pers__rielt").text();
				realty.publisher.company = new AdvertPublisherCompany();
				realty.publisher.company.name = adv.select("h4.con-info__title").select("a.con-info__link").text();
			}
		}
	}
}
