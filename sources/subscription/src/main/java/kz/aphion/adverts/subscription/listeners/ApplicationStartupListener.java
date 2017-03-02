package kz.aphion.adverts.subscription.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.subscription.MqConsumerInitializator;
import kz.aphion.adverts.subscription.jobs.SchedulerJobManager;

import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting subscription application...");
		
		try {
			
			// Инициализируем подключение к Mongo
			logger.info("Initializing connection to MongoDB...");
			DB.INSTANCE.init();
			logger.info("MongoDB connection is opened.");
		
			// Инициализируем подключение к ActiveMQ
			logger.info("Initializing connection to ActiveMQ...");
			MQ.INSTANCE.init();
			logger.info("ActiveMQ connection is opened.");
			
			// Запускаем Listener для обработки сообщений
			logger.info("Activating MQ Listeners...");
			MqConsumerInitializator.initListeners();
			logger.info("MQ Listeners activated.");
		
			logger.info("Starting Scheduler...");
			SchedulerJobManager.getInstance().startScheduler();
			logger.info("Scheduler started...");
			
		} catch (Throwable e) {
			logger.error("FATAL error while starting subscription application", e);
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Shutting down subscription application.");
		
		// Завершаем работу потоков чтения сообщений из очередей		

		// Закрываем подключение к MongoDB
		logger.info("Closing MongoDB connection...");
		try {
			DB.DS().getMongo().close();
			logger.info("MongoDB connection was closed.");
			MQ.INSTANCE.getConnection().close();
			Thread.sleep(1000);
		} catch (Exception e) {
			logger.error("Error closing MongoDB connection", e);
		}
		
		logger.info("Stopping Scheduler...");
		try {
			SchedulerJobManager.getInstance().stopScheduler();
			logger.info("Scheduler stopped...");
		} catch (SchedulerException e) {
			logger.error("Error stopping scheduler...", e);
		}
		
		logger.info("Subscription application stopped.");
	}

}
