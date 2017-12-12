package kz.aphion.adverts.persistence.subscription.criteria;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Точка и радиус интересующий при поиске.
 * В данный момент простая реализация не позволяющая указать несколько точек
 * и создать произвольную облать для посика так как может оно и не нужно
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class SubscriptionCriteriaGeoLocation {

	@Property
	public String longitude;
	
	@Property
	public String latitude;
	
	/**
	 * Радиус от точки (возмжоно это должен быть int)
	 */
	@Property
	public Float radus;
	
}
