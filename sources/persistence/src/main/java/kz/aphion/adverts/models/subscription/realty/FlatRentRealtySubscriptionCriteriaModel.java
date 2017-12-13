package kz.aphion.adverts.models.subscription.realty;

import java.util.List;

import kz.aphion.adverts.models.ModelUtils;
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
	//public List<FlatRentPeriodType> rentPeriods;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	//public List<FlatRentMiscellaneousType> rentMiscellaneous;

	public List<FlatRentPeriodType> getRentPeriods() {
		return ModelUtils.getEnumsFromObject(FlatRentPeriodType.class, getData().get("rentPeriods"));
	}

	public void setRentPeriods(List<FlatRentPeriodType> rentPeriods) {
		getData().put("rentPeriods", rentPeriods);
	}

	public List<FlatRentMiscellaneousType> getRentMiscellaneous() {
		return ModelUtils.getEnumsFromObject(FlatRentMiscellaneousType.class, getData().get("rentMiscellaneous"));
	}

	public void setRentMiscellaneous(List<FlatRentMiscellaneousType> rentMiscellaneous) {
		getData().put("rentMiscellaneous", rentMiscellaneous);
	}
	
	
}
