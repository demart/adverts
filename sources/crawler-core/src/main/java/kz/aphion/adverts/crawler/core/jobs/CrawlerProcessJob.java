package kz.aphion.adverts.crawler.core.jobs;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

import javax.jms.JMSException;

import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.crawler.core.CrawlerInitializater;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс предок для всех Crawler процессов, содержит все необходимые свойства для выполнения выгрузки данных конркетными Crawler'aми.
 * Для того чтобы использовать класс необходимо насладовать потомка в play проекте (Так как classloader не видит другие классы на данный момент).  
 * 
 * @author artem.demidovich
 *
 */
public abstract class CrawlerProcessJob<V> implements Callable<V>, Runnable {

	private static Logger logger = LoggerFactory.getLogger(CrawlerInitializater.class);
	
	/**
	 * Тип источника для выгрузки данных
	 */
	public CrawlerSourceSystemTypeEnum sourceSystemType;
	
	/**
	 * Модель конфигурации crawlera
	 */
	public CrawlerModel crawlerModel;
	
	
    public static final String invocationType = "CrawlerJob";
    
    protected ExecutorService executor;
    protected long lastRun = 0;
    protected boolean wasError = false;
    protected Throwable lastException = null;

	/**
	 * Провайдер MQ для отправки сообщений в очередь
	 * @throws CrawlerException 
	 * @throws JMSException 
	 */
	public MQ getMqProvider() throws JMSException, CrawlerException {
		return MQ.INSTANCE;
	}
	
	
	public CrawlerModel getCrawlerModel() {
		return crawlerModel;
	}
 
    /**
     * Here you do the job
     */
    public void doJob() throws Exception {
    }

    /**
     * Here you do the job and return a result
     */
    public V doJobWithResult() throws Exception {
        doJob();
        return null;
    }

    @Override
    public void run() {
    	call();
    }

    public V call() {
        try {
        	V result = doJobWithResult();
            return result;
        } catch (Throwable e) {
        	logger.error("Error during crawler job execution", e);
        } finally {
        }
        return null;
    }
    
    @Override
    public String toString() {
        return this.getClass().getName();
    }
	
}
