package kz.aphion.adverts.crawler.kn.listener;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.crawler.core.CrawlerInitializater;
import kz.aphion.adverts.crawler.core.models.CrawlerGroupInitializationModel;
import kz.aphion.adverts.crawler.kn.jobs.KnCrawlerJob;
import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting KN Crawler application...");
		
		try {
			List<CrawlerGroupInitializationModel> crawlerGroups = new ArrayList<CrawlerGroupInitializationModel>();
			crawlerGroups.add(new CrawlerGroupInitializationModel(CrawlerSourceSystemTypeEnum.KN, KnCrawlerJob.class));
			CrawlerInitializater.getInstance().initWithCrawlerClasses(crawlerGroups);
		} catch (Throwable e) {
			logger.error("FATAL error while starting KN Crawler application", e);
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Shutting down KN Crawler application.");
		
		// Завершаем работу потоков чтения сообщений из очередей		

		try {
			CrawlerInitializater.getInstance().stopCrawlers();
		} catch (Exception e) {
			logger.error("Error finishing crawlers connection", e);
		}
		
		logger.info("Closing MongoDB connection...");
		try {
			DB.DS().getMongo().close();
			logger.info("MongoDB connection was closed.");
			MQ.INSTANCE.getConnection().close();
			Thread.sleep(1000);
		} catch (Exception e) {
			logger.error("Error closing MongoDB connection", e);
		}
		// TODO Подумать как улучшить работу, мньго исключений об ошибках -> утечки памяти
		
		
		logger.info("KN Crawler application stopped.");
	}

}
