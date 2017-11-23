package kz.aphion.adverts.web.api.models.subscriptions.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.types.MortgageStatus;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyFlatBaseSubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtySellFlatSubscriptionCriteria;

/**
 * Критерии для продажи квартир
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class RealtySellFlatSubscriptionCriteriaModel extends RealtyFlatBaseSubscriptionCriteriaModel {
	
	/**
	 * В залоге или нет
	 */
	public List<MortgageStatus> mortgageStatuses;
	
	public RealtySellFlatSubscriptionCriteriaModel convertToModel(RealtySellFlatSubscriptionCriteria criteria) {
		if (criteria == null)
			return null;
		
		RealtySellFlatSubscriptionCriteriaModel model = new RealtySellFlatSubscriptionCriteriaModel();
		
		model.mortgageStatuses = criteria.mortgageStatuses;
		
		model.fillModel((RealtyFlatBaseSubscriptionCriteria)criteria);
		
		return model;
	}
}
