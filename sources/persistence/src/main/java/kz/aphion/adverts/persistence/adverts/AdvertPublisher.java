package kz.aphion.adverts.persistence.adverts;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Автор объявления
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public class AdvertPublisher {

	@Property
	public AdvertPublisherType type;
	
	/**
	 * Идентификатор пользователя во внешней системе
	 */
	@Property
	public String externalUserId;
	
	/**
	 * Имя автора объявления
	 */
	@Property
	public String name;
	
	/**
	 * Список контактных номеров
	 */
	@Embedded
	public List<String> phones;
	

	@Embedded
	public AdvertPublisherCompany company;
	
}
