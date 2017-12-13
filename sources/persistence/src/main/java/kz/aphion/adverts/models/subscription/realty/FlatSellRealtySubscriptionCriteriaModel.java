package kz.aphion.adverts.models.subscription.realty;

import java.util.List;

import kz.aphion.adverts.models.ModelUtils;
import kz.aphion.adverts.models.realty.data.MortgageStatus;
import kz.aphion.adverts.persistence.subscription.Subscription;

public class FlatSellRealtySubscriptionCriteriaModel extends FlatBaseRealtySubscriptionCriteriaModel {
	
	public FlatSellRealtySubscriptionCriteriaModel(Subscription subscription) {
		super(subscription);
	}

	/**
	 * В залоге или нет
	 */
	//public List<MortgageStatus> mortgageStatuses;

	public List<MortgageStatus> getMortgageStatuses() {
		return ModelUtils.getEnumsFromObject(MortgageStatus.class, getData().get("mortgageStatuses"));
	}

	public void setMortgageStatuses(List<MortgageStatus> mortgageStatuses) {
		getData().put("mortgageStatuses", mortgageStatuses);
	}
	
}
