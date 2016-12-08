package kz.aphion.adverts.crawler.core.models;

import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;


public class CrawlerGroupInitializationModel {

	/**
	 * Тип источника данных
	 */
	public CrawlerSourceSystemTypeEnum sourceSystem;
	
	/**
	 * Job класс для вызгрузки объявлений
	 */
	public Class<?> jobClass;
	
	public CrawlerGroupInitializationModel(CrawlerSourceSystemTypeEnum sourceSystem, Class<?> jobClass) {
		this.sourceSystem = sourceSystem;
		this.jobClass = jobClass;
	}
}
