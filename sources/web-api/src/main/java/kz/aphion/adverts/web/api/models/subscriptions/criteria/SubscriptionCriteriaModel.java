package kz.aphion.adverts.web.api.models.subscriptions.criteria;

import java.util.List;

import kz.aphion.adverts.persistence.subscription.criteria.KeywordsCriteriaType;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyRentFlatSubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtySellFlatSubscriptionCriteria;
import kz.aphion.adverts.web.api.models.geo.ResidentialComplexModel;
import kz.aphion.adverts.web.api.models.subscriptions.criteria.realty.RealtyRentFlatSubscriptionCriteriaModel;
import kz.aphion.adverts.web.api.models.subscriptions.criteria.realty.RealtySellFlatSubscriptionCriteriaModel;

/**
 * Базовые критерии подписки
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class SubscriptionCriteriaModel {

	/**
	 * Цена от
	 */
	public Long priceFrom;
	
	/**
	 * Цена до
	 */
	public Long priceTo;

	/**
	 * Каким способом искать ключевые слова. Правилу И или по правилу ИЛИ
	 */
	public KeywordsCriteriaType keywordsType;
	
	/**
	 * Список текста который может быть использован для поиска
	 */
	public List<String> keywords;
	
	/**
	 * Объявления только с фотографией?
	 */
	public Boolean hasPhoto;
	
	/**
	 * Интересующее расположение объектов.
	 * Если null то учитываются все регионы без разбора
	 * Если указаны какие-то регионы то учитывается только их присутсвие в объявлениях
	 * 
	 */
	public LocationSubscriptionCriteriaModel location;
	

	/**
	 * Информация о ЖК которые участвуют в поиске
	 * Если NULL то не важно какие
	 */
	public List<ResidentialComplexModel> residentalComplexs;


	public static SubscriptionCriteriaModel convertToModel(SubscriptionCriteria criteria) {
		if (criteria == null)
			return null;
		
		if (criteria instanceof RealtySellFlatSubscriptionCriteria)
			return new RealtySellFlatSubscriptionCriteriaModel().convertToModel((RealtySellFlatSubscriptionCriteria)criteria);
		
		if (criteria instanceof RealtyRentFlatSubscriptionCriteria)
			return new RealtyRentFlatSubscriptionCriteriaModel().convertToModel((RealtyRentFlatSubscriptionCriteria)criteria);
		
		// Not supported yet
		return null;
	}
	
	
	public void fillModel(SubscriptionCriteria criteria) {
		if (criteria == null)
			return;

		this.hasPhoto = criteria.hasPhoto;
		this.keywords = criteria.keywords;
		this.keywordsType = criteria.keywordsType;
		this.location = LocationSubscriptionCriteriaModel.convertToModel(criteria.location);
		this.priceFrom = criteria.priceFrom;
		this.priceTo = criteria.priceTo;
		this.residentalComplexs = ResidentialComplexModel.convertToModel(criteria.residentalComplexs);
		
	}
	
}
