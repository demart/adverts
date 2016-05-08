package kz.aphion.adverts.persistence;

import java.util.Calendar;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Version;


/**
 * Базовый класс для всех сущностей MongoDB
 * 
 * @author artem.demidovich
 *
 */
public abstract class BaseEntity {

	/**
	 * Идентификатор региона
	 */
	@Id
	public ObjectId id;
	
	/**
	 * Версия для контроля
	 */
	@Version
	public Long version;
	
	/**
	 * Дата создания объекта
	 */
	@Property
	public Calendar created;
	
	/**
	 * Дата изменения объекта
	 */
	@Property
	public Calendar updated;
	
}
