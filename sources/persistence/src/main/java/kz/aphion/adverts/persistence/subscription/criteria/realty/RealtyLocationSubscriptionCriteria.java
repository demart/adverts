package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Информация об интересующих местах расположение объектов недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class RealtyLocationSubscriptionCriteria extends BaseEntity {

	// Можем искать по интересующим регионам
	// Можем искать по интересующим ЖК
	// Можем искать по радиусу на карте
	// Можем комбинировать даные условия
	
	/**
	 * Интересующие регионы (районы или еще что-то)
	 */
	@Embedded
	public List<Region> regions;
	
	/**
	 * Возможные расположение объекта на карте
	 */
	@Embedded
	public List<RealtyGeoLocation> mapLocations;
	
}
