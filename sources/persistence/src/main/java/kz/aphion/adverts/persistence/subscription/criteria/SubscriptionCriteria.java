package kz.aphion.adverts.persistence.subscription.criteria;

import java.util.HashMap;
import java.util.List;

import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Критерии поиска подходящих объявлений
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public abstract class SubscriptionCriteria {

	/**
	 * Цена от
	 */
	@Property
	public Long priceFrom;
	
	/**
	 * Цена до
	 */
	@Property
	public Long priceTo;

	/**
	 * Каким способом искать ключевые слова. Правилу И или по правилу ИЛИ
	 */
	@Property
	public KeywordsCriteriaType keywordsType;
	
	/**
	 * Список текста который может быть использован для поиска
	 */
	@Embedded
	public List<String> keywords;
	
	/**
	 * Объявления только с фотографией?
	 */
	@Property
	public Boolean hasPhoto;
	
	/**
	 * Список криетриев по объявлениям
	 */
	@Embedded
	public HashMap<String, Object> data;
	
	/**
	 * Интересующее расположение объектов.
	 * Если null то учитываются все регионы без разбора
	 * Если указаны какие-то регионы то учитывается только их присутсвие в объявлениях
	 * 
	 */
	@Embedded
	public SubscriptionCriteriaLocation location;
	
	/**
	 * Кто опубликовал объявление (различные виды)
	 */
	@Embedded
	public List<AdvertPublisherType> publisherTypes;

}