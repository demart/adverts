package kz.aphion.adverts.persistence.phones;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.Region;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Класс описывающий коллекцию телефонов полученных из разных источников
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.phones")
@Converters(CalendarConverter.class)
public class Phone extends BaseEntity {

	/**
	 * Номер телефона
	 */
	@Property
	public String number;
	
	/**
	 * Последний регион
	 */
	@Embedded
	public Region region;
	
	/**
	 * Иерархия регионов
	 */
	@Embedded
	public List<Region> regions;
	
	/**
	 * Список установленных приложений
	 */
	@Property
	public List<PhoneApp> apps;
	
	/**
	 * Послдений источник откуда поступил телефон
	 */
	@Property
	public PhoneSource source;
	
	/**
	 * Последняя зафиксированная категория объявления где использовался телефон
	 */
	@Property
	public PhoneSourceCategory category;
	
	/**
	 * Владелец телефона (Полседний зафиксированный)
	 */
	public PhoneOwner owner;
	
	/**
	 * Время последнего обновления
	 */
	@Property
	public Calendar lastUpdate;
	
	/**
	 * Статус номера телефона
	 */
	@Property
	public PhoneStatus status;

	/**
	 * История изменения информации о телефоне
	 */
	@Embedded
	public List<PhoneChangesHistory> history;
	
}
