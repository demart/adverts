package kz.aphion.adverts.crawler.krisha.mappers;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import kz.aphion.adverts.persistence.realty.RealtyLocation;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.RealtyPublisher;
import kz.aphion.adverts.persistence.realty.RealtySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Абстрактный класс для конвертации различных типов объявлений о недвижимости
 * 
 * @author artem.demidovich
 *
 */
public abstract class AbstractAdvertMapper<T extends Realty> {

	private static Logger logger = LoggerFactory.getLogger(AbstractAdvertMapper.class);
	
	protected T realty;
	
	public AbstractAdvertMapper(T realty) {
		this.realty = realty;
	}
	
	public T mapAdvertObject(Map<String,Object> advert) {
		
		realty.status = RealtyAdvertStatus.ACTIVE;
		
		realty.source = new RealtySource();
		realty.source.sourceType = SourceSystemType.KRISHA;
		
		realty.location = new RealtyLocation();
		realty.publisher = new RealtyPublisher();
		
		for (Entry<String,Object> entry : advert.entrySet()) {
			switch (entry.getKey()) {
			case "id":
				realty.source.externalAdvertId = (String)entry.getValue();
				break;
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
