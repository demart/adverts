package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.models.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentPeriodType;

/**
 * Критерии поиска для аренды квартиры
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
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
	
}
