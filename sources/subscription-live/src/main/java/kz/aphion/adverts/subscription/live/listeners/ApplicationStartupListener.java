package kz.aphion.adverts.subscription.live.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.subscription.live.mq.MqConsumerInitializator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting analyser processor application...");
		
		try {
//			
//			// assume SLF4J is bound to logback in the current environment
//			LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
//			// 	print logback's internal status
//			StatusPrinter.print(lc);
			
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
		
		} catch (Throwable e) {
			logger.error("FATAL error while starting analyser project", e);
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Shutting down phone processor application.");
		
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
		
		logger.info("Analyser processor application stopped.");
	}



}
