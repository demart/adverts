package kz.aphion.adverts.subscription.live.models.realty.flat;

import java.util.List;

import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.types.MortgageStatus;

public class RealtySellFlatSubscriptionCriteria extends RealtyFlatBaseSubscriptionCriteria {
	
	/**
	 * В залоге или нет
	 */
	public List<MortgageStatus> mortgageStatuses;
	
	public boolean isBelongs(FlatSellRealty realty) {
		if (isAdvertBelongsToQuery(realty) == false)
			return false;
		
		if (isAdvertBelongsToQuery(realty.data) == false)
			return false;
		
		if (checkEnums(mortgageStatuses, realty.data.mortgageStatus) == false)
			return false;
		
		return true;
	}
	
}
