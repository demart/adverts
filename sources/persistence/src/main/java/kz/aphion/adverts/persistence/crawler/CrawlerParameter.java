package kz.aphion.adverts.persistence.crawler;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Сущность для описания параметров каждого отдельного  Crawler'a
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.crawler.parameters")
@Converters(CalendarConverter.class)
public class CrawlerParameter extends BaseEntity {

	/**
	 * Название Crawler.
	 * Например: Выгрузка однокомнатных квартир.
	 * 			Выгрузка офисов и чего нить еще.
	 */
	@Property
	public String name;
	
	/**
	 * Описание параметра Crawler
	 */
	@Property
	public String description;
	
	/**
	 * Crawler к которому относится данный параметр
	 */
	@Reference
	public Crawler crawler;
	
	/**
	 * Ключ параметра Crawler
	 */
	@Property
	public String key;
	
	/**
	 * Значние параметра Crawler
	 */
	@Property
	public String value;	
	
	/**
	 * Статус-состояние параметра для Crawler'а
	 */
	@Property
	public CrawlerParameterStatusEnum status;

}
