package kz.aphion.adverts.models.subscription;

import kz.aphion.adverts.models.SubscriptionModel;
import kz.aphion.adverts.models.subscription.realty.FlatSellRealtySubscriptionCriteriaModel;
import kz.aphion.adverts.persistence.subscription.Subscription;

public class FlatSellSubscriptionModel extends SubscriptionModel {

	protected FlatSellRealtySubscriptionCriteriaModel dataModel;
	
	public FlatSellSubscriptionModel(Subscription subscription) {
		super(subscription);
		dataModel = new FlatSellRealtySubscriptionCriteriaModel(subscription);
	}

	public FlatSellRealtySubscriptionCriteriaModel getCriteriaModel() {
		return dataModel;
	}
	
}