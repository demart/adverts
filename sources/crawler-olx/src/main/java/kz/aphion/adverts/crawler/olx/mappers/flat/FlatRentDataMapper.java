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
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертирует объявления об арнеде квартир
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class FlatRentDataMapper {

	private static Logger logger = LoggerFactory.getLogger(FlatRentDataMapper.class);

	public static Realty mapAdvertObject(Map<String, Object> advert) throws CrawlerException {

		FlatRentRealty realty = new FlatRentRealty();
		
		realty.data = new FlatRentData();
		realty.status = RealtyAdvertStatus.ACTIVE;
		realty.source = new RealtySource();
		realty.source.sourceType = SourceSystemType.OLX;
		
		realty.location = new RealtyLocation();
		realty.publisher = new RealtyPublisher();
		
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
					
					OlxAdvertCategoryType categoryType = CommonMapperUtils.parseAdvertCategoryType((String)entry.getValue());
					
					switch (categoryType) {
						case REAL_ESTATE_RENT_FLAT:
							// Сдача квартир (включает в себя всё остальное
							break;
						case REAL_ESTATE_RENT_FLAT_HOURLY:
							realty.data.rentPeriod = FlatRentPeriodType.HOURLY;
							// Сдача по часам
							break;
						case REAL_ESTATE_RENT_FLAT_DAILY:
							// Сдача на сутки
							realty.data.rentPeriod = FlatRentPeriodType.DAILY;
							break;
						case REAL_ESTATE_RENT_FLAT_LONG_TERM:
							// Сдача на длительный срок
							realty.data.rentPeriod = FlatRentPeriodType.MONTHLY;
							break;
							
						case REAL_ESTATE_RENT_FLAT_BY_OWNERS:
							// Сдача без посредников
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
					CommonMapperUtils.convertRegion(realty, entry);
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
					String priceLabel = (String)entry.getValue();
					realty.price = CommonMapperUtils.convertPrice(priceLabel);
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
				default:
					logger.error("ATTENTION! Found new advert key: " + entry.getKey() + " with value: " + entry.getValue());
					break;
				}		
			}
		
		return realty;
	}

	/**
	 * Конвертирует параметры объявлений о продаже в служебные поля
	 * @param realty
	 * @param entry
	 */
	private static void convertAdvertParams(FlatRentRealty realty, List<List<String>> params) {
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
				case "Тип аренды":
					// "Квартиры посуточно", "Долгосрочная аренда квартир", "Квартиры с почасовой оплатой"
					
					switch (value) {
					case "Квартиры посуточно":
						realty.data.rentPeriod = FlatRentPeriodType.DAILY;
						break;
					case "Долгосрочная аренда квартир":
						// Пока так
						realty.data.rentPeriod = FlatRentPeriodType.MONTHLY;
						break;
					case "Квартиры с почасовой оплатой":
						realty.data.rentPeriod = FlatRentPeriodType.HOURLY;
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
					Float square = Float.parseFloat(value.replaceAll("м²", "").trim());
					realty.data.square = square;
					break;
				case "Жилая площадь":
					// "50 м²"
					Float livingSquare = Float.parseFloat(value.replaceAll("м²", "").trim());
					realty.data.squareLiving = livingSquare;
					break;	
				case "Площадь кухни":
					// "50 м²"				
					Float kitchenSquare = Float.parseFloat(value.replaceAll("м²", "").trim());
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
