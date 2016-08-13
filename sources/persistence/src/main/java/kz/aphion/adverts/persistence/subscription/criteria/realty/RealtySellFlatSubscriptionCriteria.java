package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.types.MortgageStatus;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Критерии для продажи квартир
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class RealtySellFlatSubscriptionCriteria extends RealtyFlatBaseSubscriptionCriteria {

	/**
	 * В залоге или нет
	 */
	@Embedded
	public List<MortgageStatus> mortgageStatuses;
	
}
