package kz.aphion.adverts.persistence.subscription.criteria;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyLocationSubscriptionCriteria;

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
public abstract class SubscriptionCriteria extends BaseEntity {

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
	 * Интересующее расположение объектов.
	 * Если null то учитываются все регионы без разбора
	 * Если указаны какие-то регионы то учитывается только их присутсвие в объявлениях
	 * 
	 */
	@Embedded
	public RealtyLocationSubscriptionCriteria location;
	

	/**
	 * Информация о ЖК которые участвуют в поиске
	 * Если NULL то не важно какие
	 */
	@Embedded("residentialComplex")
	public List<ResidentialComplex> residentalComplexs;
	
}