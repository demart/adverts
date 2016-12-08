package kz.aphion.adverts.persistence.realty;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Компаия от имени которых подают объявления
 * 
 * @author artem.demidovich
 *
 */
@Entity
public class RealtyPublisherCompany {

	/**
	 * Внешний идентификатор компании на сайте источнике
	 */
	@Property
	public String externalId;
	
	/**
	 * Название компании
	 */
	@Property
	public String name;
	
}
