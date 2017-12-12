package kz.aphion.adverts.crawler.irr.mappers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.irr.IrrAdvertCategoryType;
import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertLocation;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.adverts.AdvertPublisher;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.adverts.AdvertSource;
import kz.aphion.adverts.persistence.adverts.AdvertStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Абстрактный класс для конвертации различных типов объявлений о недвижимости
 * 
 * @author artem.demidovich
 *
 */
public abstract class AbstractAdvertMapper<T extends Advert> {

	private static Logger logger = LoggerFactory.getLogger(AbstractAdvertMapper.class);
	
	protected T realty;
	
	public AbstractAdvertMapper(T realty) {
		this.realty = realty;
	}
	
	public T mapAdvertObject(Map<String,Object> advert) throws CrawlerException, ParseException {
		
		realty.status = AdvertStatus.ACTIVE;
		
		realty.data = new HashMap<String, Object>();
		
		realty.source = new AdvertSource();
		realty.source.type = SourceSystemType.IRR;
		
		realty.location = new AdvertLocation();
		realty.publisher = new AdvertPublisher();
		realty.publisher.phones = new ArrayList<String>();
		
		realty.publisher.type = AdvertPublisherType.UNDEFINED;
		
		//пришлось вынести описание отдельно для сохранения. Так как харастеристики 
		//у них лежат в блоке "group_custom_fields".
		//для удобства подключения других модулей необходимо иметь для каждой категории
		//свой маппер. Описание идет перед данным параметрами!
		String description = null;
		
		
		for (Entry<String,Object> entry : advert.entrySet()) {
			switch (entry.getKey()) {
				//Номер объявления
				case "id":
					realty.source.externalId = (String)entry.getValue();
					break;
				
				//Характеристики объекта	
				case "group_custom_fields":
					mapAdvertData((List<Map<String, Object>>)entry.getValue(), description);
					break;
					
				//Категория	
				case "category_url":
					String categoryUrl = (String)entry.getValue();
					IrrAdvertCategoryType categoryType = CommonMapperUtils.parseAdvertCategoryType(categoryUrl);
					//TODO надо решить, что делать с новостройками и вторичным рынком
					break;
				
				//Местоположение
				case "region_url":
					String region = (String)entry.getValue();
					realty.location.externalRegionId = region;
					break;
					
				//Url
				case "url":
					realty.source.originalLink = (String)entry.getValue();
					break;
					
				//Цена
				case "price":
					String price = (String)entry.getValue();
					realty.data.put("price", Long.parseLong(price));
					break;
					
				//Продавец
				case "seller":
					realty.publisher.name = (String)entry.getValue();
					break;
				
				//Id продавца
				case "seller_info":
					Map<String, Object> info = (Map<String, Object>)entry.getValue();
					for (Entry<String, Object> keys : info.entrySet()) {
						switch (keys.getKey()) {
							case "id":
								realty.publisher.externalUserId = (String)keys.getValue();
								break;
							case "name":
								String name = (String)keys.getValue();
								if (!name.isEmpty())
									realty.publisher.name = name;
								break;
						}
					}
					break;
					
				//Номер телефона 1
				case "phone":
					List<Map<String, Object>> phones = (List<Map<String, Object>>)entry.getValue();
					String phone = phones.toString();
					phone = phone.substring(1, phone.length() - 1);
					if (phone.indexOf(",") > 0) {
						String[] phonesInBlock = phone.split(", ");
	       				for (String phoneNumber : phonesInBlock) {
	       					realty.publisher.phones.add(phoneNumber);
	       				}
					}
					
					else
						realty.publisher.phones.add(phone);
					break;
				
				//Номер телефона 2
				case "phone2":
					List<Map<String, Object>> phones2 = (List<Map<String, Object>>)entry.getValue();
					String phone2 = phones2.toString();
					phone2 = phone2.substring(1, phone2.length() - 1);
					if (phone2.indexOf(",") > 0) {
						String[] phonesInBlock2 = phone2.split(", ");
	       				for (String phoneNumber : phonesInBlock2) {
	       					realty.publisher.phones.add(phoneNumber);
	       				}
					}
					
					else {
						if (!phone2.isEmpty())
							realty.publisher.phones.add(phone2);
					}
					break;	
					
				//Описание
				case "text":
					description = (String)entry.getValue();
					break;
					
				//Время публикации
				case "date_create":
					//Формат времени на irr.kz
					realty.publishedAt = CommonMapperUtils.convertAddDate((String)entry.getValue());
					break;
					
				
				//Фотографии
				case "images":
					List<Map<String, Object>> allImages = (List<Map<String, Object>>)entry.getValue();
					List<AdvertPhoto> photos = new ArrayList<AdvertPhoto>();
					for (Map<String, Object> image : allImages) {
						AdvertPhoto photo = new AdvertPhoto ();
						for (Entry<String, Object> img : image.entrySet()) {
							switch (img.getKey()) {
								case "orig":
									photo.path = (String)img.getValue();
									break;
							}
						}
						
						if (photo != null)
							photos.add(photo);
					}
					if (photos != null) {
						if (photos.size() > 0) {
							realty.hasPhoto = true;
							realty.photos = photos;
						}
					}
					break;
						
					
		/*
			case "storage_id":
				// Никак не используем
				// Может быть это архив или не архив
				break;
			case "version":
				// Никак не используем, но возможно нужно, 
				// как раз смотреть на версию, чтобы обновлять данные у себя
				//realty.source.sourceDataVersion = entry.getValue().toString();
				realty.source.sourceDataVersion = (String)entry.getValue();
				break;
			case "created_at":
				// Пока никак не используем
				break;
			case "updated_at":
				// Пока никак не используем
				break;
				
			case "data":
				// Извлекаем данные
				Map<String, Object> advertData = (Map<String, Object>)entry.getValue();
				mapAdvertData(advertData);
				break;
			case "service_data":
				Map<String, Object> serviceData = (Map<String, Object>)entry.getValue();
				CommonMapperUtils.convertServiceData(realty, serviceData);
				break;
				
			case "system_data":
				Map<String, Object> systemData = (Map<String, Object>)entry.getValue();
				CommonMapperUtils.convertSystemData(realty, systemData);
				break;
				
			case "Files":
				List<RealtyPhoto> photos = CommonMapperUtils.convertPhotos((Map<String, Object>)entry.getValue());
				realty.hasPhoto = (photos != null && photos.size() > 0) ? true : false;
				realty.photos = photos;
				
				if (Logger.isDebugEnabled()) {
					Logger.debug("Found " + (photos != null ? photos.size() : "[null]") + " photos");
				}
				break;
				
			default:
				Logger.error("ATTENTION! Found new advert key: " + entry.getKey() + " with value: " + entry.getValue());
				break;
			*/
			}
		}
		
		return realty;
	}
	
	
	/**
	 * Абстрактный метод реализуемый в наследниках, так как объект DATA для всех видов объявлений разный
	 * @param list
	 * @param description 
	 */
	public abstract void mapAdvertData(List<Map<String, Object>> list, String description);
	
}
