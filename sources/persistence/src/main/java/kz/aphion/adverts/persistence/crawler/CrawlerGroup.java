package kz.aphion.adverts.persistence.crawler;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;


/**
 * Группа Crawler'ов по источникам (донорам)
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.crawler.groups")
@Converters(CalendarConverter.class)
public class CrawlerGroup extends BaseEntity {
	
	/**
	 * Название группы Crawler
	 */
	@Property
	public String name;
	
	/**
	 * Алиас crawler группы для ведения логов.
	 * Например:
	 * 	KRISHA-[ASTANA-1-ROOM], 
	 * 	KN-[KAZAKHSTAN-1-ROOM], 
	 * 	IRR-[KAZAKHSTAN-1-2-ROOM],
	 */
	@Property
	public String alias;	
	
	/**
	 * Описание группы Crawler
	 */
	@Property
	public String description;

	/**
	 * Тип источника (донора) информации 
	 */
	@Property
	public CrawlerSourceSystemTypeEnum sourceSystemType;

	/**
	 * Базовый URL для обращения к API системы источника (донора)
	 */
	@Property
	public String sourceSystemBaseUrl;
	
	/**
	 * Статус группы Crawler
	 */
	@Property
	public CrawlerGroupStatusEnum status;
	
	/**
	 * Включена ли опция работы через Proxy Servers
	 */
	@Property
	public Boolean useProxyServers;
	
	/**
	 * Включена ли опция работы с исползование разных User-Agent
	 */
	@Property
	public Boolean useUserAgents;

}
