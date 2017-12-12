package kz.aphion.adverts.persistence.subscription.criteria;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Информация об интересующих местах расположение объектов недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class SubscriptionCriteriaLocation extends BaseEntity {

	// Можем искать по интересующим регионам
	// Можем искать по интересующим ЖК
	// Можем искать по радиусу на карте
	// Можем комбинировать даные условия
	
	/**
	 * Интересующие регионы (районы или еще что-то)
	 */
	@Embedded
	public List<String> regionIds;
	
	/**
	 * Возможные расположение объекта на карте
	 */
	@Embedded
	public List<SubscriptionCriteriaGeoLocation> points;
	
}
