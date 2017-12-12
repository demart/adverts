package kz.aphion.adverts.subscription.live;

import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;
import kz.aphion.adverts.subscription.live.query.realty.FlatRentSubscriptionQuery;
import kz.aphion.adverts.subscription.live.query.realty.FlatSellSubscriptionQuery;

import org.apache.commons.lang.NotImplementedException;

public class SubscriptionConnectionFactory {

	public static SubscriptionConnection createConnection(String type, String subType, String operationType) throws Exception {
		AdvertType advertType = getSubscriptionAdvertType(type);
		
		switch (advertType) {
		
			case REALTY:
				return createRealtySubscriptionConnection(subType.toUpperCase(), operationType.toUpperCase());
	
			default:
				throw new NotImplementedException("Not supported only REALTY subscription advert type");
		}
	}
	

	private static SubscriptionConnection createRealtySubscriptionConnection(String subType, String operationType) throws Exception {
		RealtyType realtyType = getRealtyType(subType);
		AdvertOperationType realtyOperationType = getAdvertOperationType(operationType);
		
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
	
	public static AdvertType getSubscriptionAdvertType(String advertType) throws Exception {
		if (advertType == null)
			throw new NullPointerException("Advert Type is null");
		advertType = advertType.toUpperCase();
		for (AdvertType subscriptionAdvertType : AdvertType.values()) {
			if (subscriptionAdvertType.equals(AdvertType.valueOf(advertType)))
					return subscriptionAdvertType;
		}
		throw new Exception("Incorrect AdvertType parameter");
	}
	
	public static AdvertOperationType getAdvertOperationType(String operationType) throws Exception {
		operationType = operationType.toUpperCase();
		for (AdvertOperationType realtyOperationType : AdvertOperationType.values()) {
			if (realtyOperationType.equals(AdvertOperationType.valueOf(operationType)))
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
