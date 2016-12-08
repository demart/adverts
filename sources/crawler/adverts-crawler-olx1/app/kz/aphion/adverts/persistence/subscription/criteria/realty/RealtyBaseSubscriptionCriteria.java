package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.persistence.realty.types.SquareType;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteria;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Базовые критерии поиска для всех объектов недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class RealtyBaseSubscriptionCriteria extends SubscriptionCriteria {

	/**
	 * Вид недвижимости
	 */
	@Property
	public RealtyType type;
	
	/**
	 * Тип операции
	 */
	@Property
	public RealtyOperationType operation;	
	
	/**
	 * Тип исчисления площади
	 */
	@Property
	public SquareType squareType;
	
	/**
	 * Общая площадь от
	 */
	@Property
	public Float squareFrom;
	
	/**
	 * Общая площадь до
	 */
	@Property
	public Float squareTo;
	
	/**
	 * Кто опубликовал объявление (различные виды)
	 */
	@Embedded
	public List<RealtyPublisherType> publisherTypes;
	

	
}
