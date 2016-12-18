package kz.aphion.adverts.crawler.olx.mappers.flat;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.olx.OlxAdvertCategoryType;
import kz.aphion.adverts.crawler.olx.mappers.CommonMapperUtils;
import kz.aphion.adverts.crawler.olx.mappers.OlxPhoneMapper;
import kz.aphion.adverts.crawler.olx.mappers.OlxPhotoMapper;
import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import kz.aphion.adverts.persistence.realty.RealtyLocation;
import kz.aphion.adverts.persistence.realty.RealtyPublisher;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.RealtySource;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертирует объявления о продаже или арнеде квартир
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class FlatSellDataMapper {

	private static Logger logger = LoggerFactory.getLogger(FlatSellDataMapper.class);

	public static Realty mapAdvertObject(Map<String, Object> advert) throws CrawlerException {
		
		FlatSellRealty realty = new FlatSellRealty();
		
		realty.data = new FlatSellData();
		realty.status = RealtyAdvertStatus.ACTIVE;
		realty.source = new RealtySource();
		realty.source.sourceType = SourceSystemType.OLX;
		
		realty.location = new RealtyLocation();
		realty.publisher = new RealtyPublisher();
		
		// Это нужно для того чтобы понять до какого уровня у нас есть данные
		// Например до района или только до города
		// На данный момент OLX схема районов выглядит совсем странно, поэтому пока до города
		String regionId = null;
		String cityId = null;
		String districtId = null;
		
		for (Entry<String,Object> entry : advert.entrySet()) {
			switch (entry.getKey()) {
				case "id":
					// id": "107604886",
					realty.source.externalAdvertId = (String)entry.getValue();
					break;
				case "url":
					// url": "http://olx.kz/obyavlenie/prodam-2-komnatnuyu-kvartiru-zhk-kypchak-76-5-kv-m-teplaya-neuglovaya-ID7huVG.html",
					realty.source.originalAdvertLink = (String)entry.getValue();
					break;

				case "title":
					// title": "Продам 2-комнатную квартиру ЖК \"Кыпчак\",76,5 кв.м. теплая, неугловая.",
					break;

				case "created":
					// "created": "Сегодня 00:36",
					realty.publishedAt = CommonMapperUtils.convertCreatedDate((String)entry.getValue());
					// Дата истечения объявления 7 дней
					realty.expireAt = (Calendar)realty.publishedAt.clone();
					realty.expireAt.add(Calendar.DATE, 7);
					break;

				case "description":
					// description": "Продам 2-х комнатную квартиру ЖК \"Кыпчак\", теплая, солнечная сторона, евроремонт, балкон большой застекленнный, 2 лифта, кухня студия с установленным новым кухонным гарнитуром.",
					realty.data.text = (String)entry.getValue();
					break;
				
				case "category_id":
					// Do nothing
					// category_id": 207,

					// Так как краулер может работать в группой продажа квартир,
					// то эта группа так же содержит две подргуппы 
					// Вторичное жилье и Новостройки
					// Если мы добавим такой параметр в основную БД, тогда тут можно извлеч и выставить флаг
					
					OlxAdvertCategoryType categoryType = CommonMapperUtils.parseAdvertCategoryType((String)entry.getValue());
					
					switch (categoryType) {
						case REAL_ESTATE_SELL_FLAT:
							// Просто продажа квартир
							break;
						case REAL_ESTATE_SELL_FLAT_NEW:
							// Продажа вторичного жилья
							break;
						case REAL_ESTATE_SELL_FLAT_USED:
							// Продажа новостроек
							break;
						default:
							break;
					}
					
					break;
				case "params":
					// Do nothing
					List<List<String>> params = (List<List<String>>)entry.getValue();
					convertAdvertParams(realty, params);
					break;
				
				case "business":
					// "business": 0,
					break;

				case "status":
					// status": "active",
					break;
				
				case "has_phone":
					// has_phone": 1,
					// Если есть телефон то отправляем запрос на сервер для его получения
					if ("1".equals(entry.getValue())) {
						List<String> phones = OlxPhoneMapper.callServerAndGetPhone(realty.source.externalAdvertId);
						realty.publisher.phones = phones;
					} else {
						logger.warn("Olx Advert has_phone field [{}] not equals 1, maybe there is no any phones",  entry.getValue());
					}
					break;
				case "has_email":
					// "has_email": 1,
					break;
					
				case "has_skype":
					// "has_skype": 1,
					break;
				case "has_gg":
					// "has_gg":1
					break;
				case "is_price_negotiable":
					// "is_price_negotiable": 1,
					break;
					
				case "map_zoom":
					// map_zoom": "12",
					realty.location.mapZoom = (String)entry.getValue();
					break;
				case "map_lat":
					// map_lat": "51.16645000",
					realty.location.mapLatitude = (String)entry.getValue();
					break;
				case "map_lon":
					// "map_lon": "71.43271000",
					realty.location.mapLongitude = (String)entry.getValue();
					break;
				case "city_label":
					// Do nothing
					// city_label": "Астана",
					// TODO EXTRACT REGION BY NAME
					
					// Commented because OLX added region_id and city_id fields
					//CommonMapperUtils.convertRegion(realty, entry);
					break;
					
				case "person":
					// Do nothing
					// person": "Марат",
					realty.publisher.name = (String)entry.getValue();
					break;
				case "user_label":
					// Do nothing
					// user_label": "Марат",
					break;
				case "user_ads_id":
					// Do nothing
					// user_ads_id": "ZvfO",
					break;
				case "user_id":
					// Do nothing
					// "user_id": "ZvfO",
					break;
				case "numeric_user_id":
					// Do nothing
					// numeric_user_id": "14661996",
					realty.publisher.externalUserId = (String)entry.getValue();
					break;
				
				case "list_label":
					// Do nothing
					// "list_label": "26 000 000 тг.",
					//String priceLabel = (String)entry.getValue();
					//realty.price = CommonMapperUtils.convertPrice(priceLabel);
					break;
				case "list_label_ad":
					// Do nothing
					// list_label_ad": "26 000 000 тг.",
					break;
				case "list_label_small":
					// Do nothing
					// list_label_small": "Договорная",
					break;
				case "pricePrevious":
					// "pricePrevious": 17 220 001 тг.
					break;
				case "photos":
					// Do nothing
					// Parse Photos
					realty.photos = OlxPhotoMapper.convertPhotos((Map<String, Object>)entry.getValue());
					if (realty.photos != null && realty.photos.size() > 0)
						realty.hasPhoto = true;
					break;

				case "accurate_location":
					// "accurate_location":1
					break;
				case "user_ads_url":
					// Do nothing
					// user_ads_url": "https://ssl.olx.kz/i2/list/user/ZvfO/?json=1&search%5Buser_id%5D=14661996",
					break;
				case "preview_url":
					// Do nothing
					// "preview_url": "https://ssl.olx.kz/i2/astana/obyavlenie/prodam-2-komnatnuyu-kvartiru-zhk-kypchak-76-5-kv-m-teplaya-neuglovaya-ID7huVG.html?json=1",
					break;					
				case "subtitle":
					// Do nothing
					// Do nothing
					// subtitle": [
					// 	"76 м<sup>2</sup>",
					//	"комнаты: 2"
					// ],
					break;
				case "hide_user_ads_button":
					// Do nothing
					// hide_user_ads_button": 0,
					break;
				case "age":
					// Do nothing
					// "age": 60,
					break;
				case "header":
					// Do nothing
					// "header": "Обычные объявления",
					break;
				case "header_type":
					// Do nothing
					// header_type": "all",
					break;
				case "map_radius":
					// Do nothing
					// map_radius": 3,
					break;
				case "map_show_detailed":
					// Do nothing
					// "map_show_detailed": false,
					break;
				case "map_location":
					// Do nothing
					// map_location": "lJo9IfohWIXR0exgVWu2VE0JuVpkv0UUlt2jeIgM0maoM4b6cS2tfpt1B/1c1C4YEir2jFm8LPA=",
					break;	
				case "chat_options":
					// Do nothing
					// chat_options": 1
					break;
				case "highlighted":
					// Do nothing
					// highlighted": 0,
					break;
				case "urgent":
					// Do nothing
					// urgent": 0,
					break;
				case "topAd":
					// Do nothing
					// "topAd": 0,
					break;
				case "promotion_section":
					// Do nothing
					// promotion_section": 0,
					break;
					
				// ====================
				// 09.12.2016 NEW FIELDS
				// ====================
					
				case "price_type":
					// DO NOTHING
					// value: price
					break;
					
				case "price_numeric":
					// DO NOTHING
					// value: 8472500
					// Moved transforming price here
					String priceLabel = (String)entry.getValue();
					realty.price = Long.valueOf(priceLabel);
					break;
				
				case "region_id":
					// value: 87
					String regionIdVal = (String)entry.getValue();
					regionId = regionIdVal;
					break;
					
				case "city_id":
					// value: 87
					String cityIdVal = (String)entry.getValue();
					cityId = cityIdVal;
					break;
				
				case "district_id":
					// value: 13
					String districtIdVal = (String)entry.getValue();
					districtId = districtIdVal;
					break;
					
				case "campaignSource":
					// DO NOTHING
					// value: null
					break;
				
				case "featured":
					// DO NOTHING
					// value: []
					break;
					
				case "user_business_logo":
					// DO NOTHING
					// value: https://olxkz-ring09.akamaized.net/images_shops_slandokz/149257006_1_94x72.jpg
					break;
				
				default:
					logger.error("ATTENTION! Found new advert key: " + entry.getKey() + " with value: " + entry.getValue());
					break;
				}		
			}
		
		// Пробуем получить место где публикуется объявление
		// Максимально до уровня района, минимально до уровня города
		CommonMapperUtils.convertRegion(realty, regionId, cityId, districtId);
		
		return realty;
	}

	/**
	 * Конвертирует параметры объявлений о продаже в служебные поля
	 * @param realty
	 * @param entry
	 */
	private static void convertAdvertParams(FlatSellRealty realty, List<List<String>> params) {
		for (List<String> list : params) {
			
			String name = list.get(0).trim();
			String value = list.get(1).trim();
			
			switch (name) {
				case "Объявление от":
					// "Частного лица", "Агентства"
					switch (value) {
						case "Частного лица":
							realty.publisher.publisherType = RealtyPublisherType.OWNER;
							break;
						case "Агентства":
							realty.publisher.publisherType = RealtyPublisherType.REALTOR;
							break;
						default:
							logger.warn("Found new key in advert parameter [{}] with value [{}]", name, value);
							break;
					}
					
					break;
				case "Тип квартиры":
					// "Вторичный рынок", "Новостройки"
					switch (value) {
						case "Вторичный рынок":
							//realty.publisher.publisherType = RealtyPublisherType.OWNER;
							break;
						case "Новостройки":
							//realty.publisher.publisherType = RealtyPublisherType.REALTOR;
							break;
						default:
							logger.warn("Found new key in advert parameter [{}] with value [{}]", name, value);
							break;
					}
					
					break;
				case "Количество комнат":
					// "2 "	
					Float roomCount = Float.parseFloat(value);
					realty.data.rooms = roomCount;
					break;
				case "Общая площадь":
					// "50 м²"
					String tatalSquare = value.replaceAll("м²", "").trim();
					tatalSquare = tatalSquare.replace(" ", "");
					Float square = Float.parseFloat(tatalSquare);
					realty.data.square = square;
					break;
				case "Жилая площадь":
					// "50 м²"
					String livingSquareStr = value.replaceAll("м²", "").trim();
					livingSquareStr = livingSquareStr.replace(" ", "");
					Float livingSquare = Float.parseFloat(livingSquareStr);
					realty.data.squareLiving = livingSquare;
					break;	
				case "Площадь кухни":
					// "50 м²"				
					String kitchenSquareStr = value.replaceAll("м²", "").trim();
					kitchenSquareStr = kitchenSquareStr.replace(" ", "");
					Float kitchenSquare = Float.parseFloat(kitchenSquareStr);
					realty.data.squareKitchen = kitchenSquare;
					break;					
				case "Тип":
					// "Кирпичный", "Панельный", "Монолитный", "Блочный", "Деревянный"
					switch (value) {
						case "Кирпичный":
							realty.data.flatBuildingType = FlatBuildingType.BRICK;
							break;
						case "Панельный":
							realty.data.flatBuildingType = FlatBuildingType.PANEL;
							break;
						case "Монолитный":
							realty.data.flatBuildingType = FlatBuildingType.MONOLITHIC;
							break;
						case "Блочный":
							realty.data.flatBuildingType = FlatBuildingType.BLOCK;
							break;
						case "Деревянный":
							realty.data.flatBuildingType = FlatBuildingType.WOODEN;
							break;
							
						default:
							logger.warn("Found new key in advert parameter [{}] with value [{}]", name, value);
							break;
					}
					break;
				case "Этажность дома":
					// "2 "
					Long houseFloorCount = Long.parseLong(value);
					realty.data.houseFloorCount = houseFloorCount;
					break;
				case "Этаж":
					// "5 "
					Long floor = Long.parseLong(value);
					realty.data.flatFloor = floor;
					break;
				default:
					break;
			}
		}
		
	}
	
}
