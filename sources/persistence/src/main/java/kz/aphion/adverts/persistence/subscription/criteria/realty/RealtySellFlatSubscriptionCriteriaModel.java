package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.models.realty.data.MortgageStatus;

/**
 * Критерии для продажи квартир
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public class RealtySellFlatSubscriptionCriteriaModel extends RealtyFlatBaseSubscriptionCriteriaModel {

	/**
	 * В залоге или нет
	 */
	public List<MortgageStatus> mortgageStatuses;
	
}
