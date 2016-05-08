package kz.aphion.adverts.crawler.core.models;

import kz.aphion.adverts.crawler.entity.CrawlerGroupStatusEnum;
import kz.aphion.adverts.crawler.entity.CrawlerSourceSystemTypeEnum;

public class CrawlerGroupModel {

	public Long id;
	
	/**
	 * Название группы Crawler
	 */
	public String name;
	
	/**
	 * Алиас crawler группы для ведения логов.
	 * Например:
	 * 	KRISHA-[ASTANA-1-ROOM], 
	 * 	KN-[KAZAKHSTAN-1-ROOM], 
	 * 	IRR-[KAZAKHSTAN-1-2-ROOM],
	 */
	public String alias;	
	
	/**
	 * Описание группы Crawler
	 */
	public String description;

	/**
	 * Тип источника (донора) информации 
	 */
	public CrawlerSourceSystemTypeEnum sourceSystemType;

	/**
	 * Базовый URL для обращения к API системы источника (донора)
	 */
	public String sourceSystemBaseUrl;
	
	/**
	 * Статус группы Crawler
	 */
	public CrawlerGroupStatusEnum status;
	
	/**
	 * Включена ли опция работы через Proxy Servers
	 */
	public Boolean useProxyServers;
	
	/**
	 * Включена ли опция работы с исползование разных User-Agent
	 */
	public Boolean useUserAgents;

}
