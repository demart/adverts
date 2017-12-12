package kz.aphion.adverts.models.subscription.realty;

import java.util.List;

import kz.aphion.adverts.models.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.subscription.Subscription;

public class FlatRentRealtySubscriptionCriteriaModel extends FlatBaseRealtySubscriptionCriteriaModel {
	
	public FlatRentRealtySubscriptionCriteriaModel(Subscription subscription) {
		super(subscription);
	}

	/**
	 * Срок сдачи недвижимости
	 */
	public List<FlatRentPeriodType> rentPeriods;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	public List<FlatRentMiscellaneousType> rentMiscellaneous;
	
}
