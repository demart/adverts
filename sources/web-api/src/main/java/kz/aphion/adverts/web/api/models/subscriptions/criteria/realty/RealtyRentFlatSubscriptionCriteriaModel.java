package kz.aphion.adverts.web.api.models.subscriptions.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyFlatBaseSubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyRentFlatSubscriptionCriteria;


/**
 * Критерии поиска для аренды квартиры
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class RealtyRentFlatSubscriptionCriteriaModel extends RealtyFlatBaseSubscriptionCriteriaModel {
	
	/**
	 * Срок сдачи недвижимости
	 */
	public List<FlatRentPeriodType> rentPeriods;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	public List<FlatRentMiscellaneousType> rentMiscellaneous;
	
	
	public RealtyRentFlatSubscriptionCriteriaModel convertToModel(RealtyRentFlatSubscriptionCriteria criteria) {
		if (criteria == null)
			return null;
		
		RealtyRentFlatSubscriptionCriteriaModel model = new RealtyRentFlatSubscriptionCriteriaModel();
		
		model.rentPeriods = criteria.rentPeriods;
		model.rentMiscellaneous = criteria.rentMiscellaneous;
		
		model.fillModel((RealtyFlatBaseSubscriptionCriteria)criteria);
		
		return model;
	}
}
