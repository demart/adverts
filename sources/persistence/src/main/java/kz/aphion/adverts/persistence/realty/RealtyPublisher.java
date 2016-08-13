package kz.aphion.adverts.persistence.realty;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Кто опубликовал объявление
 * 
 * @author artem.demidovich
 *
 */
@Entity
public class RealtyPublisher extends BaseEntity {

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
	
	/**
	 * Кто опубликовал объявление
	 */
	@Property
	public RealtyPublisherType publisherType;
	
	/**
	 * Название компании к котрой принадлежит автор объявления
	 */
	@Embedded
	public RealtyPublisherCompany company;
	
}
