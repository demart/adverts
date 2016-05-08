package kz.aphion.adverts.persistence.phones;

import java.util.Calendar;

import org.mongodb.morphia.annotations.Property;

/**
 * История изменения информации о телефоне
 * 
 * @author artem.demidovich
 *
 */
public class PhoneChangesHistory {

	/**
	 * Время изменения
	 */
	@Property
	public Calendar time;
	
	/**
	 * Источник откуда поступило обновление
	 */
	@Property
	public PhoneSource source;
	
	/**
	 * Категория объявления или информации где было зафиксировано
	 */
	@Property
	public PhoneSourceCategory category;
	
	/**
	 * Кто подал объявления или информацию
	 */
	@Property
	public PhoneOwner owner;
	
}
