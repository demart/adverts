package kz.aphion.adverts.web.api.search;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.web.api.exceptions.IncorrectParameterValueException;
import kz.aphion.adverts.web.api.query.SearchAdvertQuery;
import kz.aphion.adverts.web.api.search.realty.FlatRentRealtySearcher;
import kz.aphion.adverts.web.api.search.realty.FlatSellRealtySeacher;

import org.apache.commons.lang.NotImplementedException;

public class AdvertSearchFactory {

	
	public static AdvertSearch getAdvetSearcher(SearchAdvertQuery query) throws IncorrectParameterValueException {
		switch (query.type) {
			case REALTY:
				RealtyType realtyType = getRealtyType(query.subType);
				switch (realtyType) {
					case FLAT:
						RealtyOperationType realtyOperationType = getRealtyOperationType(query.operationType);
						switch (realtyOperationType) {
						case SELL:
							return new FlatSellRealtySeacher();
						case RENT:
							return new FlatRentRealtySearcher();
						default:
							throw new NotImplementedException("Selected AdvertType not implemented");
						}
		
					default:
						throw new NotImplementedException("Selected AdvertType not implemented");
				}
	
			default:
				throw new NotImplementedException("Selected AdvertType not implemented");
		}
	}
	
	
	private static RealtyOperationType getRealtyOperationType(String operationType) throws IncorrectParameterValueException {
		for (RealtyOperationType rt : RealtyOperationType.values()) {
			if (operationType.toUpperCase().equals(rt.toString()))
				return rt;
		}
		
		throw new IncorrectParameterValueException("Incorrect realty operation type!");
	}


	private static RealtyType getRealtyType(String realtyType) throws IncorrectParameterValueException{
		for (RealtyType rt : RealtyType.values()) {
			if (realtyType.toUpperCase().equals(rt.toString()))
				return rt;
		}
		
		throw new IncorrectParameterValueException("Incorrect realty type!");
	}
	
}
