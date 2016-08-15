package kz.aphion.adverts.subscription.live;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertType;
import kz.aphion.adverts.subscription.live.query.realty.FlatRentSubscriptionQuery;
import kz.aphion.adverts.subscription.live.query.realty.FlatSellSubscriptionQuery;

import org.apache.commons.lang.NotImplementedException;
import org.apache.commons.lang.NullArgumentException;

public class SubscriptionConnectionFactory {

	public static SubscriptionConnection createConnection(String type, String subType, String operationType) throws Exception {
		SubscriptionAdvertType advertType = getSubscriptionAdvertType(type);
		
		switch (advertType) {
		
			case REALTY:
				return createRealtySubscriptionConnection(subType.toUpperCase(), operationType.toUpperCase());
	
			default:
				throw new NotImplementedException("Not supported only REALTY subscription advert type");
		}
	}
	

	private static SubscriptionConnection createRealtySubscriptionConnection(String subType, String operationType) throws Exception {
		RealtyType realtyType = getRealtyType(subType);
		RealtyOperationType realtyOperationType = getRealtyOperationType(operationType);
		
		switch (realtyType) {
			case FLAT:
				switch (realtyOperationType) {
				
				case SELL:
					return new SubscriptionConnection(new FlatSellSubscriptionQuery());
					
				case RENT:
					return new SubscriptionConnection(new FlatRentSubscriptionQuery());
					
				default:
					throw new NotImplementedException("Unsupported selected realty and operation types");
				}
			
	
			default:
				throw new NotImplementedException("Unsupported realty type");
		}
		
	}
	
	public static SubscriptionAdvertType getSubscriptionAdvertType(String advertType) throws Exception {
		if (advertType == null)
			throw new NullPointerException("Advert Type is null");
		advertType = advertType.toUpperCase();
		for (SubscriptionAdvertType subscriptionAdvertType : SubscriptionAdvertType.values()) {
			if (subscriptionAdvertType.equals(SubscriptionAdvertType.valueOf(advertType)))
					return subscriptionAdvertType;
		}
		throw new Exception("Incorrect SubscriptionAdvertType parameter");
	}
	
	public static RealtyOperationType getRealtyOperationType(String operationType) throws Exception {
		operationType = operationType.toUpperCase();
		for (RealtyOperationType realtyOperationType : RealtyOperationType.values()) {
			if (realtyOperationType.equals(RealtyOperationType.valueOf(operationType)))
					return realtyOperationType;
		}
		throw new Exception("Incorrect RealtyType parameter");
	}
	
	public static RealtyType getRealtyType(String type) throws Exception {
		type = type.toUpperCase();
		for (RealtyType realtyType : RealtyType.values()) {
			if (realtyType.equals(RealtyType.valueOf(type)))
					return realtyType;
		}
		throw new Exception("Incorrect RealtyType parameter");
	}
	
}
