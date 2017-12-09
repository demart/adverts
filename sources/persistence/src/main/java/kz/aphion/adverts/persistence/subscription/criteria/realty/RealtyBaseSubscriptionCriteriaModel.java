package kz.aphion.adverts.persistence.subscription.criteria.realty;

import kz.aphion.adverts.persistence.realty.SquareType;

/**
 * Базовые критерии поиска для всех объектов недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public class RealtyBaseSubscriptionCriteriaModel {
	
	/**
	 * Тип исчисления площади
	 */
	public SquareType squareType;
	
	/**
	 * Общая площадь от
	 */
	public Float squareFrom;
	
	/**
	 * Общая площадь до
	 */
	public Float squareTo;
	
}
