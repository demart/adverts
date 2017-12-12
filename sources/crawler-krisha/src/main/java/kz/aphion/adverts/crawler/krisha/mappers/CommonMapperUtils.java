package kz.aphion.adverts.crawler.krisha.mappers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.krisha.KrishaAdvertCategoryType;
import kz.aphion.adverts.persistence.CurrencyType;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.adverts.MapName;
import kz.aphion.adverts.persistence.adverts.MapType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Класс с методами для конвертации общий (повторяющихся) частей в объявлениях крыши.
 * Например: фото, сервис данные, служебные данные и так далее
 * @author artem.demidovich
 *
 */
public class CommonMapperUtils {

	private static Logger logger = LoggerFactory.getLogger(CommonMapperUtils.class);
	
	/**
	 * Извлекает тип объявления из JSON ответа, необходимо для динамического создания сущностей
	 * @param rawAdvert
	 * @return
	 */
	public static KrishaAdvertCategoryType getAdvertCategoryType(Map<String, Object> rawAdvert) {
		if (rawAdvert.containsKey("service_data")) {
			Map<String, Object> serviceDataEntry = (Map<String, Object>)rawAdvert.get("service_data");
			if (serviceDataEntry.containsKey("cat_id")) {
				Object categoryEntry = (Object)serviceDataEntry.get("cat_id");
				String categoryId = null;
				//if (categoryEntry instanceof Number) {
					categoryId = String.valueOf(categoryEntry);
				//} else {
				//	categoryId = (String)categoryEntry;
				//}
				
				for (KrishaAdvertCategoryType type : KrishaAdvertCategoryType.values()) {
					if (type.getValue() == Integer.parseInt(categoryId)) {
						if (logger.isDebugEnabled())
							logger.debug("Advert category type: " + type);
						return type;
					}
				}
			}
		}
		return null;
	}
	
	public static String getEntryStringValue(Entry<String, Object> dataItem) {
		if (dataItem.getValue() instanceof Number) {
			return String.valueOf(dataItem.getValue());
		} else {
			if (dataItem.getValue() instanceof Boolean) {
				return dataItem.getValue().toString();
			} else {
				return (String)dataItem.getValue();
			}
		}
	}
	
	public static CurrencyType getCurrencyType(Entry<String,Object> dataItem) {
		String currency = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (currency) {
			case "1":
				return CurrencyType.USD;
			case "2":
				return CurrencyType.KZT;
			case "3":
				return CurrencyType.EUR;
			case "4":
				return CurrencyType.RUR;
			default:
				logger.error("ATTENTION! Found new [data.currencyType] value: " + currency);
				return null;
			}
	}


	public static AdvertPublisherType getPublisherType(Entry<String, Object> dataItem) {
		String whoSell = CommonMapperUtils.getEntryStringValue(dataItem);
		
		switch (whoSell) {
			case "0":
				return AdvertPublisherType.UNDEFINED;
			case "1": // Хозяин или представитель
				return AdvertPublisherType.OWNER;
			case "2": // Компания или специалист (риэлтор)
				return AdvertPublisherType.AGENT;
			case "3": // ????
				return AdvertPublisherType.AGENT_COMPANY;
			default:
				logger.error("ATTENTION! Found new [who] value: " + whoSell);
				return AdvertPublisherType.UNDEFINED;
			}
	}

	public static MapName getMapName(Entry<String,Object> dataItem) {
		String mapName = (String)dataItem.getValue();
		switch (mapName) {
			case "google":
				return MapName.GOOGLE;
			case "yandex":
				return MapName.YANDEX;
			default:
				logger.error("ATTENTION! Found new [map.name] value: " + mapName);
				return MapName.UNDEFINED;
			}
	}
	
	public static MapType getMapType(Entry<String, Object> dataItem) {
		String mapType = (String)dataItem.getValue();
		switch (mapType) {
		case "yandex#map":
			return MapType.YANDEX;	
		case "google":
			return MapType.GOOGLE;
		case "road" : // ???
			return MapType.UNDEFINED;
		case "hybrid": // ???
			return MapType.UNDEFINED;
		case "yandex#hybrid": // ???
			return MapType.YANDEX;
		case "yandex#publicMapHybrid":
			return MapType.YANDEX;
		case "yandex#satellite":
			return MapType.YANDEX;
		case "yandex#publicMap":
			return MapType.YANDEX;
		default:
			logger.error("ATTENTION! Found new [map.type] value: " + mapType);
			return MapType.YANDEX;
		}
	}

	/**
	 * Конвертирует системные данные
	 * @param realty
	 * @param systemData
	 */
	public static void convertSystemData(Advert realty, Map<String, Object> systemData) {
		for (Entry<String, Object> systemDataItem : systemData.entrySet()) {
			switch (systemDataItem.getKey()) {
			case "price-2":
				// Пока ничего не делаем
				//realty.price = (Long)systemDataItem.getValue();
				realty.data.put("price", Long.parseLong((String)systemDataItem.getValue()));
				break;
			case "price-3":
				// Пока ничего не делаем						
				break;
			case "price-1":
				// Пока ничего не делаем						
				break;
			case "price-4":
				// Пока ничего не делаем						
				break;
				
			case "price-kzt": // ??
				break;
			case "price-eur": // ??
				break;
			case "price-usd": // ??
				break;
				
			case "hasphoto":
				// Пока ничего не делаем						
				break;
			case "color":
				// Пока ничего не делаем						
				break;
			case "wphoto":
				// Пока ничего не делаем
				break;
			case "torg": // true
				break;
			case "hot": // 2015-08-12T08:56:37+06:00
				break;
			case "re":
				// Подумать!						
				break;
			case "show_till":
				// Время до которого объявление будет висеть на сайте (Планируемое, нужно перепроверять)
				String showTillString = (String)systemDataItem.getValue();
				Calendar showTill = javax.xml.bind.DatatypeConverter.parseDateTime(showTillString);
				realty.expireAt = showTill; 
				
				// Также выставляем дату публикации равную showTill минус 7 дней
				realty.publishedAt = ((Calendar)showTill.clone());
				realty.publishedAt.add(Calendar.DATE, -7);
				
				break;
			case "up":
				// Подумать!						
				break;
			case "auto_re":
				// Подумать!
				break;
			case "show_till_sec": // ??? 57
				break;
			default: 
				logger.error("ATTENTION! Found new system_data key: " + systemDataItem.getKey() + " with value: " + systemDataItem.getValue());
				break;
			}
		}
	}
	
	/**
	 * Конвертирует общую информацию о сервисе
	 * @param realty
	 * @param serviceData
	 */
	public static void convertServiceData(Advert realty, Map<String, Object> serviceData) {
		for (Entry<String, Object> serviceDataItem : serviceData.entrySet()) {
			switch (serviceDataItem.getKey()) {
				case "cat_id":
				// Подумать!
				// Возможно извлекать динамически и строить тип
				break;
				case "status":
					Map<String, Object> statuses = (Map<String, Object>)serviceDataItem.getValue();
					for (Entry<String, Object> statusEntry : statuses.entrySet()) {
						switch (statusEntry.getKey()) {
						case "draft":
							Map<String, Object> draftStatus = (Map<String, Object>)statusEntry.getValue();
							convertStatusData(realty, draftStatus);
							break;
						case "moder":
							Map<String, Object> moderStatus = (Map<String, Object>)statusEntry.getValue();
							convertStatusData(realty, moderStatus);
							break;
						case "photo":
							Map<String, Object> photoStatus = (Map<String, Object>)statusEntry.getValue();
							convertStatusData(realty, photoStatus);
							break;
						case "limit_pay":
							Map<String, Object> limitPayStatus = (Map<String, Object>)statusEntry.getValue();
							convertStatusData(realty, limitPayStatus);
							break;
						default:
							logger.error("ATTENTION! Found new service_data.status key: " + statusEntry.getKey() + " with value: " + statusEntry.getValue());
							break;
						}
					}
					break;
				case "appId":
					// Пока не используем
					break;
				case "edit_id": // 26088718 
					break;
				case "paper_service":
					// Редко встречается
					// [{ts=2014-07-10 12:59:43, pub=np, type=text, id=dot, das_pay_id=12494177, opts={text=Квартира в прив.общежитии.Санузел,кухня внутри квартиры, head=}}]
					break;
				case "photo_ids":
					// Пока не используем
					break;
				case "photo_autoinc":
					// Пока не используем
					break;
				case "temp_id":
					// Пока не используем
					break;
				case "published_at":
					// Возможно нужно использовать
					break;
				case "nb_views":
					// Кол-во просмотров (возможно нужно использовать)
					break;
				case "nb_phone_views":
					// Кол-во просмотров телефонов (возможно нужно использовать)
					break;
				case "photo_last_checked":
					// Не понятно что это и как его едят
					break;
				case "live_id":
					// 6939580
					break;
				case "delete_id":
					// 180268
					break;
				case "archive_id":
					// 19664008
					break;
				case "":
					break;
				case "view_num":
					// Существует только для старый версий
					break;
				case "oldEmail":
					// adilkhan.alshinbaev@bcc.kz
					break;
				case "user_login":
					// orient00@inbox.ru
					break;
				case "old_advert_id":
					break;
				case "old_cat_id":
					break;
				default: 
					logger.error("ATTENTION! Found new service_data key: " + serviceDataItem.getKey() + " with value: " + serviceDataItem.getValue());
					break;
			}
		}
		
	}
	
	/**
	 * Конвертирует общую информацию о статусе объявления
	 * 
	 * @param realty
	 * @param status
	 */
	public static void convertStatusData(Advert realty, Map<String, Object> status) {
		for (Entry<String, Object> statusEntry : status.entrySet()) {
			switch (statusEntry.getKey()) {
			case "value":
				// Не используем
				break;
			case "ttl":
				if (statusEntry.getValue() instanceof Map<?, ?>) {
					Map<String, Object> statusTtl = (Map<String, Object>)statusEntry.getValue();
					for (Entry<String, Object> statusTtlEntry : statusTtl.entrySet()) {
						switch (statusTtlEntry.getKey()) {
						case "date":
							// Не используем
							break;
						case "timezone_type":
							// Не используем
							break;
						case "timezone":
							// Не используем
							break;
						default:
							logger.error("ATTENTION! Found new service_data.status.XXX.ttl key: " + statusTtlEntry.getKey() + " with value: " + statusTtlEntry.getValue());
							break;
						}
					}
				} else {
					// String value: "2013-02-15T14:21:08+06:00"
					// DO SOMETHING
				}
				break;
			case "who":
				// Не используем
				break;
			case "moder":
				// Не используем
				break;
			case "userType":
				// Не используем
				break;
			case "reason":
				// 4
				break;
			case "reason_description":
				// не берет трубку
				break;
			default:
				logger.error("ATTENTION! Found new service_data.status key: " + statusEntry.getKey() + " with value: " + statusEntry.getValue());
				break;
			}
		}
	}
	
	
	/**
	 * Конвертирует данные по фотографиям
	 * @param rawPhotos
	 * @return
	 */
	public static List<AdvertPhoto> convertPhotos(Map<String, Object> rawPhotos) {
		List<AdvertPhoto> photos = new ArrayList<AdvertPhoto>();
		for (Entry<String, Object> rawPhotoElement : rawPhotos.entrySet()) {
			Map<String, Object> rawPhoto = (Map<String, Object>)rawPhotoElement.getValue();
			
			AdvertPhoto photo = new AdvertPhoto();
			for (Entry<String, Object> rawPhotoItem : rawPhoto.entrySet()) {
				switch (rawPhotoItem.getKey()) {
				case "mime-type":
					photo.mimeType = (String)rawPhotoItem.getValue();
					break;
				case "path":
					photo.path = (String)rawPhotoItem.getValue();
					break;
				case "width":
					photo.width = Long.parseLong((String)rawPhotoItem.getValue());
					break;
				case "height":
					photo.height = Long.parseLong((String)rawPhotoItem.getValue());
					break;
				case "thumbnails":
					photo.thumbnails = new ArrayList<AdvertPhoto>();
					List<Map<String,Object>> thumbnails = (List<Map<String,Object>>)rawPhotoItem.getValue();
					for (Map<String, Object> thumbnail : thumbnails) {
						AdvertPhoto thumb = new AdvertPhoto();
						for (Entry<String, Object> map : thumbnail.entrySet()) {
							switch (map.getKey()) {
							case "mime-type":
								break;
							case "path":
								thumb.path = (String)map.getValue();
								break;
							case "width":
								thumb.width = Long.parseLong((String)map.getValue());
								break;
							case "height":
								thumb.height = Long.parseLong((String)map.getValue());
								break;
							default:
								break;
							}
						}
						photo.thumbnails.add(thumb);
					}
					
					break;
				default:
					break;
				}
			}
			photos.add(photo);

		}
		
		return photos;
	}
}
