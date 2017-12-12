package kz.aphion.adverts.models.subscription;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kz.aphion.adverts.models.ModelUtils;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.criteria.KeywordsCriteriaType;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteriaGeoLocation;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteriaLocation;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;

import com.mongodb.DBObject;

/**
 * Модель критериев подписки
 * @author artem.demidovich
 *
 * Created at Dec 12, 2017
 */
public class SubscriptionCriteriaModel {

	protected Subscription subscription;
	
	public SubscriptionCriteriaModel(Subscription subscription) {
		this.subscription = subscription;
	}

	protected HashMap<String, Object> getData() {
		if (subscription.criteria == null)
			subscription.criteria = new HashMap<String, Object>();
		return subscription.criteria;
	}
	
//	/**
//	 * Каким способом искать ключевые слова. Правилу И или по правилу ИЛИ
//	 */
//	public KeywordsCriteriaType keywordsType;
//	
//	/**
//	 * Список текста который может быть использован для поиска
//	 */
//	public List<String> keywords;
//	
//	/**
//	 * Объявления только с фотографией?
//	 */
//	public Boolean hasPhoto;
//
//	/**
//	 * Интересующее расположение объектов.
//	 * Если null то учитываются все регионы без разбора
//	 * Если указаны какие-то регионы то учитывается только их присутсвие в объявлениях
//	 * 
//	 */
//
//	public SubscriptionCriteriaLocation location;
//	
//	/**
//	 * Кто опубликовал объявление (различные виды)
//	 */
//	public List<AdvertPublisherType> publisherTypes;

	public Subscription getSubscription() {
		return subscription;
	}

	public KeywordsCriteriaType getKeywordsType() {
		return ModelUtils.getEnumFromObject(KeywordsCriteriaType.class, getData().get("keywordsType"));
	}

	public void setKeywordsType(KeywordsCriteriaType keywordsType) {
		getData().put("keywordsType", keywordsType);
	}

	public List<String> getKeywords() {
		Object obj = getData().get("keywords");
		if (obj != null && obj instanceof List) {
			List<String> keywords = new ArrayList<String>();
			for (Object objVal : (List<?>)obj) {
				if (objVal != null)
					keywords.add(objVal.toString());
			}
			return keywords;
		}
		return null;
	}

	public void setKeywords(List<String> keywords) {
		getData().put("keywords", keywords);
	}

	public Boolean getHasPhoto() {
		return (Boolean)getData().get("hasPhoto");
	}

	public void setHasPhoto(Boolean hasPhoto) {
		getData().put("hasPhoto", hasPhoto);
	}

	public SubscriptionCriteriaLocation getLocation(Datastore ds) {
		Object obj = getData().get("location");
		if (obj == null)
			return null;
		
		if (obj instanceof SubscriptionCriteriaLocation)
			return (SubscriptionCriteriaLocation)obj;
		
		Mapper mapper = new Mapper();
		mapper.getConverters().addConverter(CalendarConverter.class);
		mapper.addMappedClass(SubscriptionCriteriaGeoLocation.class);
		SubscriptionCriteriaLocation location = (DBObject)obj != null ? mapper.fromDBObject(ds, SubscriptionCriteriaLocation.class, (DBObject)obj, mapper.createEntityCache()) : null;
		
		return location;
	}

	public void setLocation(SubscriptionCriteriaLocation location) {
		getData().put("location", location);
	}

	public List<AdvertPublisherType> getPublisherTypes() {
		return ModelUtils.getEnumsFromObject(AdvertPublisherType.class, getData().get("publisherTypes"));
	}

	public void setPublisherTypes(List<AdvertPublisherType> publisherTypes) {
		getData().put("publisherTypes", publisherTypes);
	}

}
