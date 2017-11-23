package kz.aphion.adverts.web.api.models.subscriptions.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.persistence.realty.types.SquareType;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyBaseSubscriptionCriteria;
import kz.aphion.adverts.web.api.models.subscriptions.criteria.SubscriptionCriteriaModel;

/**
 * Базовые критерии для подписок по недвижимости
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class RealtyBaseSubscriptionCriteriaModel extends SubscriptionCriteriaModel {
	/**
	 * Вид недвижимости
	 */
	public RealtyType type;
	
	/**
	 * Тип операции
	 */
	public RealtyOperationType operation;	
	
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
	
	/**
	 * Кто опубликовал объявление (различные виды)
	 */
	public List<RealtyPublisherType> publisherTypes;
	
	public void fillModel(RealtyBaseSubscriptionCriteria criteria) {
		if (criteria == null)
			return;
		
		this.type = criteria.type;
		this.operation = criteria.operation;
		this.squareFrom = criteria.squareFrom;
		this.squareTo = criteria.squareTo;
		this.squareType = criteria.squareType;
		this.publisherTypes = criteria.publisherTypes;
		
		this.fillModel((SubscriptionCriteria)criteria);
	}
	
}
