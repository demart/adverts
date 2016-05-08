package kz.aphion.adverts.crawler.core.models;

import kz.aphion.adverts.crawler.entity.CrawlerParameterStatusEnum;

/**
 * Модель описания параметров Crawler'a
 * 
 * @author artem.demidovich
 *
 */
public class CrawlerParameterModel {

	public Long id;
	
	/**
	 * Название Crawler.
	 * Например: Выгрузка однокомнатных квартир.
	 * 			Выгрузка офисов и чего нить еще.
	 */
	public String name;
	
	/**
	 * Описание параметра Crawler
	 */
	public String description;
	
	/**
	 * Ключ параметра Crawler
	 */
	public String key;
	
	/**
	 * Значние параметра Crawler
	 */
	public String value;	
	
	/**
	 * Статус-состояние параметра для Crawler'а
	 */
	public CrawlerParameterStatusEnum status;
}
