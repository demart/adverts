package kz.aphion.adverts.persistence.crawler;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * 
 * Сущность для хранения информации о доступных User-Agent. 
 * Используется для маскировки User-Agent при вызовах в Crawlerах
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.crawler.user.agents")
@Converters(CalendarConverter.class)
public class UserAgent extends BaseEntity {

	/**
	 * Название User-Agent
	 */
	@Property
	public String name;

	/**
	 * Описание User-Agent
	 */
	@Property
	public String description;
	
	/**
	 * Сам User-Agent
	 */
	@Property
	public String userAgent;
	
	/**
	 * Тип или предназначение User-Agent
	 */
	@Property
	public UserAgentTypeEnum type;
	
	/**
	 * Статус User-Agent
	 */
	@Property
	public UserAgentStatusEnum status;

}
