package kz.aphion.adverts.crawler.kn.mappers.flat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.crawler.kn.KnDataManager;
import kz.aphion.adverts.crawler.kn.QueryBuilder;
import kz.aphion.adverts.crawler.kn.mappers.AbstractAdvertMapper;
import kz.aphion.adverts.crawler.kn.mappers.CommonMapperUtils;
import kz.aphion.adverts.crawler.kn.entity.KnResidentalComplexEntity;
import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.RealtyPublisherCompany;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import play.Logger;

/**
 * Класс конвертирует объявления о продаже или арнеде квартир
 * 
 * @author denis.krylov
 *
 */
public class FlatSellDataMapper extends AbstractAdvertMapper<FlatSellRealty> {
	

	public FlatSellDataMapper(FlatSellRealty realty) {
		super(realty);
	}
	
	@Override
	public void mapAdvertData (String advert, QueryBuilder queryBuilder, KnAdvertCategoryType advertType) throws ParseException, CrawlerException {
		realty.data = new FlatSellData();
		realty.data.residentalComplex = new ResidentialComplex();
		
		Document adv = Jsoup.parse(advert);
		
	    //Уникальный номер объявления
		realty.source.externalAdvertId  = CommonMapperUtils.convertId(adv.select("span.advert-number").text());
		realty.type = RealtyType.FLAT;
		realty.operation = RealtyOperationType.SELL;
		
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
					realty.data.flatFloor = FlatDataMapperUtils.convertFloor(tr.select("td").text());
				if (FlatDataMapperUtils.convertHouseFloorCount(tr.select("td").text()) != null)
					realty.data.houseFloorCount = FlatDataMapperUtils.convertHouseFloorCount(tr.select("td").text());
	}
	
			if (tr.select("th").text().equals("Количество комнат"))
				realty.data.rooms = FlatDataMapperUtils.convertLiveRooms(tr.select("td").text());
			
			if (tr.select("th").text().equals("Площадь")) {
				realty.data.square = FlatDataMapperUtils.convertSquare(tr.select("td").text());
				
				if (FlatDataMapperUtils.convertLivingSquare(tr.select("td").text()) != null)
					realty.data.squareLiving = FlatDataMapperUtils.convertLivingSquare(tr.select("td").text());
				
				if (FlatDataMapperUtils.convertKitchenSquare(tr.select("td").text()) != null)
					realty.data.squareKitchen = FlatDataMapperUtils.convertKitchenSquare(tr.select("td").text());
				
				}
			
		   	 if (tr.select("th").text().equals("Год постройки"))
				 realty.data.houseYear = FlatDataMapperUtils.convertHouseYear(tr.select("td").text());
			
			
    		 if (tr.select("th").text().equals("В залоге"))
				realty.data.mortgageStatus = FlatDataMapperUtils.getFlatMortgageStatus(tr.select("td").text());

			
			if (tr.select("th").text().equals("Материал стен"))
				realty.data.flatBuildingType = FlatDataMapperUtils.getFlatBuildingType(tr.select("td").text());
			
			
			if (tr.select("th").text().equals("Название ЖК")) {
					realty.data.residentalComplex.externalComplexName = FlatDataMapperUtils.convertComplexName(tr.select("td").text());
					
					KnResidentalComplexEntity complexEntity = KnDataManager.getResidentalComplex(FlatDataMapperUtils.convertComplexName(tr.select("td").text()), CommonMapperUtils.getRegionName(adv.select("div.address").text()));
					 if (complexEntity != null) {
							realty.data.residentalComplex.relationId = complexEntity.id;
							realty.data.residentalComplex.name = complexEntity.name;
						} 
						
				}
				
		}
		
		//Цена
		realty.price = FlatDataMapperUtils.convertPrice(adv.select("span.price").text());
		
		//В некоторых объявлениях отсутствует описание, поэтому необходима такая проверка
		if (adv.select("div.description").size() > 0)
			realty.data.text = adv.select("div.description").select("p").text();
		
		//Работа с телефонами
		String[] phones = CommonMapperUtils.convertPhonesNumber (adv.select("div.phones").text());
		realty.publisher.phones = new ArrayList<String>();
		for (String phone : phones) {
			realty.publisher.phones.add(phone);
		}
		
		//Работа с фотографиями
		//Выдергиваем картинки из объявления
		Elements images = adv.select("div.image-preview-list").select("div.image-item");
		ArrayList<String> linkImages = new ArrayList();
		
		for (Element image : images) {
			String linkToImage = null;
			linkToImage = queryBuilder.buildQueryUrlForImage(image.select("a[href]").first().attr("href"));
			linkImages.add(linkToImage);
		}
		
		List<RealtyPhoto> photos = CommonMapperUtils.convertPhotos(linkImages);
		if (photos.size() > 0)
			realty.photos = photos;
		
		//Ищем к какому объекту из регионов относится. Варианты: область, город, район
		realty.location.externalRegionId = CommonMapperUtils.getRegionName(adv.select("div.address").text());

		
		//Улица и номер дома
		realty.location.streetName = CommonMapperUtils.getStreetName(adv.select("div.col-content").select("h1").text());
		if (CommonMapperUtils.getHouseNumber(adv.select("div.col-content").select("h1").text()) != null)
			realty.location.houseNumber = CommonMapperUtils.getHouseNumber(adv.select("div.col-content").select("h1").text());
	
		//Кто продает: частное лицо или компания
		if (adv.select("div.contact-name").size() > 0) {
			if (adv.select("div.contact-name").select("strong").text().equals("Частное лицо"))
				realty.publisher.publisherType = RealtyPublisherType.OWNER;
			else {
				if (adv.select("div.company-main-info").size() > 0) {
					realty.publisher.publisherType = RealtyPublisherType.REALTOR_COMPANY;
					if (adv.select("div.field").size() > 0)
						if (adv.select("div.field").get(0).select("a[href]").size() > 0)
							if (CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text()) != null)
								realty.publisher.name = CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text());
				
				}
				
				else {
					realty.publisher.publisherType = RealtyPublisherType.REALTOR;
					if (adv.select("div.field").size() > 0)
						if (adv.select("div.field").get(0).select("a[href]").size() > 0)
							if (CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text()) != null)
								realty.publisher.name = CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text());
					}
				}
		}
		else {
			realty.publisher.publisherType = RealtyPublisherType.REALTOR_COMPANY;
			if (adv.select("div.field").size() > 0)
				if (adv.select("div.field").get(0).select("a[href]").size() > 0)
					if (CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text()) != null)
						realty.publisher.name = CommonMapperUtils.getRealtorName(adv.select("div.field").get(0).select("a[href]").first().text());
		}
		
		
	}
}
