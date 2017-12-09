package kz.aphion.adverts.persistence.adverts;

import org.mongodb.morphia.annotations.Property;

/**
 * Информация о компании автора объявления
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public class AdvertPublisherCompany {

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
