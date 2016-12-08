package kz.aphion.adverts.crawler.krisha.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.crawler.core.CrawlerInitializater;
import kz.aphion.adverts.crawler.core.MessageQueueProvider;
import kz.aphion.adverts.crawler.core.MongoDBProvider;
import kz.aphion.adverts.crawler.core.models.CrawlerGroupInitializationModel;
import kz.aphion.adverts.crawler.krisha.jobs.KrishaCrawlerJob;
import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener  implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting Krisha Crawler application...");
		
		try {
			List<CrawlerGroupInitializationModel> crawlerGroups = new ArrayList<CrawlerGroupInitializationModel>();
			crawlerGroups.add(new CrawlerGroupInitializationModel(CrawlerSourceSystemTypeEnum.KRISHA, KrishaCrawlerJob.class));
			CrawlerInitializater.getInstance().initWithCrawlerClasses(crawlerGroups);
		} catch (Throwable e) {
			logger.error("FATAL error while starting Krisha Crawler application", e);
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Shutting down Krisha Cralwer application.");
		
		// Завершаем работу потоков чтения сообщений из очередей		

		try {
			CrawlerInitializater.getInstance().stopCrawlers();
		} catch (Exception e) {
			logger.error("Error finishing Krisha Crawlers connection", e);
		}
		
		// Закрываем подключение к MongoDB
		logger.info("Closing MongoDB connection...");
		try {
			MongoDBProvider.getInstance().getDatastore().getMongo().close();
			logger.info("MongoDB connection was closed.");
			MessageQueueProvider.getInstance().getConnection().close();
			Thread.sleep(1000);
		} catch (Exception e) {
			logger.error("Error closing MongoDB connection", e);
		}
		
		// TODO Подумать как улучшить работу, мньго исключений об ошибках -> утечки памяти
		
		
		logger.info("Krisha Crawler application stopped.");
	}


}
