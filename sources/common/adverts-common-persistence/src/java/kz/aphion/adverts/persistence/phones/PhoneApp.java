package kz.aphion.adverts.persistence.phones;

import java.util.Calendar;

import org.mongodb.morphia.annotations.Property;

/**
 * Приложение установленное на телефон
 * 
 * @author artem.demidovich
 *
 */
public class PhoneApp {

	/**
	 * Тип приложения
	 */
	@Property
	public PhoneAppType type;
	
	/**
	 * Статус приложения (установлено или нет)
	 */
	@Property
	public PhoneAppStatus status;
	
	@Property
	public Calendar lastCheck;
}
