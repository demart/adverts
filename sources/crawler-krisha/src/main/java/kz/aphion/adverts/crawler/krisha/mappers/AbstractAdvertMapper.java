package kz.aphion.adverts.crawler.krisha.mappers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertLocation;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;
import kz.aphion.adverts.persistence.adverts.AdvertPublisher;
import kz.aphion.adverts.persistence.adverts.AdvertSource;
import kz.aphion.adverts.persistence.adverts.AdvertStatus;
import kz.aphion.adverts.persistence.adverts.AdvertType;

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
	
	public T mapAdvertObject(Map<String,Object> advert) {
		
		realty.type = AdvertType.REALTY;
		realty.status = AdvertStatus.ACTIVE;
		
		realty.source = new AdvertSource();
		realty.source.type = SourceSystemType.KRISHA;
		
		realty.location = new AdvertLocation();
		realty.publisher = new AdvertPublisher();
		
		for (Entry<String,Object> entry : advert.entrySet()) {
			switch (entry.getKey()) {
			case "id":
				realty.source.externalId = (String)entry.getValue();
				break;
			case "storage_id":
				// Никак не используем
				// Может быть это архив или не архив
				break;
			case "version":
				// Никак не используем, но возможно нужно, 
				// как раз смотреть на версию, чтобы обновлять данные у себя
				//realty.source.sourceDataVersion = entry.getValue().toString();
				realty.source.dataVersion = (String)entry.getValue();
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
				List<AdvertPhoto> photos = CommonMapperUtils.convertPhotos((Map<String, Object>)entry.getValue());
				realty.hasPhoto = (photos != null && photos.size() > 0) ? true : false;
				realty.photos = photos;
				
				if (logger.isDebugEnabled()) {
					logger.debug("Found " + (photos != null ? photos.size() : "[null]") + " photos");
				}
				break;
				
			default:
				logger.error("ATTENTION! Found new advert key: " + entry.getKey() + " with value: " + entry.getValue());
				break;
			}
		}
		
		return realty;
	}
	
	
	/**
	 * Абстрактный метод реализуемый в наследниках, так как объект DATA для всех видов объявлений разный
	 * @param advert
	 */
	public abstract void mapAdvertData(Map<String,Object> advert);
	
}
