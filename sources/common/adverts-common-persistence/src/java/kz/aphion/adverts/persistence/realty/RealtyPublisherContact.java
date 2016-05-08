package kz.aphion.adverts.persistence.realty;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

import kz.aphion.adverts.persistence.PhoneType;

/**
 * Список контактов автора в объявлении
 * 
 * @author artem.demidovich
 *
 */
@Entity
public class RealtyPublisherContact {

	/**
	 * Телефон
	 */
	@Property
	public String phone;
	
	/**
	 * Тип телефона
	 */
	@Property
	public PhoneType type;
	
}
