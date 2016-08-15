package kz.aphion.adverts.subscription.live.models.realty.flat;

import java.util.List;

import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

public class RealtyRentFlatSubscriptionCriteria extends RealtyFlatBaseSubscriptionCriteria {
	
	/**
	 * Срок сдачи недвижимости
	 */
	public List<FlatRentPeriodType> rentPeriods;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	public List<FlatRentMiscellaneousType> rentMiscellaneous;
	
	
	public boolean isBelongs(FlatRentRealty realty) {
		if (isAdvertBelongsToQuery(realty) == false)
			return false;
		
		if (isAdvertBelongsToQuery(realty.data) == false)
			return false;
		
		if (checkEnums(rentPeriods, realty.data.rentPeriod) == false)
			return false;
		
		// rentMiscellaneous
		
		return true;
	}
	
	
}
