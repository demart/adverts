package kz.aphion.adverts.models.subscription;

import kz.aphion.adverts.models.SubscriptionModel;
import kz.aphion.adverts.models.subscription.realty.FlatRentRealtySubscriptionCriteriaModel;
import kz.aphion.adverts.persistence.subscription.Subscription;

public class FlatRentSubscriptionModel extends SubscriptionModel {

	protected FlatRentRealtySubscriptionCriteriaModel dataModel;
	
	public FlatRentSubscriptionModel(Subscription subscription) {
		super(subscription);
		dataModel = new FlatRentRealtySubscriptionCriteriaModel(subscription);
	}

	public FlatRentRealtySubscriptionCriteriaModel getCriteriaModel() {
		return dataModel;
	}
	
}
