package kz.aphion.adverts.crawler.core.jobs;

import kz.aphion.adverts.crawler.core.MessageQueueProvider;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.entity.CrawlerSourceSystemTypeEnum;
import play.jobs.Job;

/**
 * Класс предок для всех Crawler процессов, содержит все необходимые свойства для выполнения выгрузки данных конркетными Crawler'aми.
 * Для того чтобы использовать класс необходимо насладовать потомка в play проекте (Так как classloader не видит другие классы на данный момент).  
 * 
 * @author artem.demidovich
 *
 */
@SuppressWarnings("rawtypes")
public abstract class CrawlerProcessJob extends Job {

	/**
	 * Тип источника для выгрузки данных
	 */
	public CrawlerSourceSystemTypeEnum sourceSystemType;
	
	/**
	 * Модель конфигурации crawlera
	 */
	public CrawlerModel crawlerModel;
	
	/**
	 * Провайдер MQ для отправки сообщений в очередь
	 */
	public MessageQueueProvider mqProvider;
	
	
	public CrawlerModel getCrawlerModel() {
		return crawlerModel;
	}
}
