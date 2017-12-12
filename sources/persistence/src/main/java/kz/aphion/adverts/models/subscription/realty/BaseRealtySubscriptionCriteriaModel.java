package kz.aphion.adverts.models.subscription.realty;

import kz.aphion.adverts.models.ModelUtils;
import kz.aphion.adverts.models.realty.data.SquareType;
import kz.aphion.adverts.models.subscription.SubscriptionCriteriaModel;
import kz.aphion.adverts.persistence.subscription.Subscription;


public abstract class BaseRealtySubscriptionCriteriaModel extends SubscriptionCriteriaModel {

	public BaseRealtySubscriptionCriteriaModel(Subscription subscription) {
		super(subscription);
		// TODO Auto-generated constructor stub
	}

//	public Long priceFrom;
	
//	public Long priceTo;
	
	/**
	 * Тип исчисления площади
	 */
//	public SquareType squareType;
	
	/**
	 * Общая площадь от
	 */
//	public Float squareFrom;
	
	/**
	 * Общая площадь до
	 */
//	public Float squareTo;

	public Long getPriceFrom() {
		return ModelUtils.getLongFromObject(getData().get("priceFrom"));
	}

	public void setPriceFrom(Long priceFrom) {
		getData().put("priceFrom", priceFrom);
	}

	public Long getPriceTo() {
		return ModelUtils.getLongFromObject(getData().get("priceTo"));
	}

	public void setPriceTo(Long priceTo) {
		getData().put("priceTo", priceTo);
	}

	public SquareType getSquareType() {
		return ModelUtils.getEnumFromObject(SquareType.class, getData().get("squareType"));
	}

	public void setSquareType(SquareType squareType) {
		getData().put("squareType", squareType);
	}

	public Float getSquareFrom() {
		return ModelUtils.getFloatFromObject(getData().get("squareFrom"));
	}

	public void setSquareFrom(Float squareFrom) {
		getData().put("squareFrom", squareFrom);
	}

	public Float getSquareTo() {
		return ModelUtils.getFloatFromObject(getData().get("squareTo"));
	}

	public void setSquareTo(Float squareTo) {
		getData().put("squareTo", squareTo);
	}
	
}
