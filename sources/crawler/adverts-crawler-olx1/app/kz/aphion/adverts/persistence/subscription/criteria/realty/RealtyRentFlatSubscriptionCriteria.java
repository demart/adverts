package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Критерии поиска для аренды квартиры
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class RealtyRentFlatSubscriptionCriteria extends RealtyFlatBaseSubscriptionCriteria {

	/**
	 * Срок сдачи недвижимости
	 */
	@Embedded
	public List<FlatRentPeriodType> rentPeriods;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	@Embedded
	public List<FlatRentMiscellaneousType> rentMiscellaneous;
	
}
