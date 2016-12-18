package kz.aphion.adverts.notification.sender.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.notification.sender.mq.MqConsumerInitializator;
import kz.aphion.adverts.notification.sender.providers.ActiveMqProvider;
import kz.aphion.adverts.notification.sender.providers.MongoDbProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting analyser processor application...");
		
		try {
			
			// Инициализируем подключение к Mongo
			logger.info("Initializing connection to MongoDB...");
			MongoDbProvider.getInstance();
			logger.info("MongoDB connection is opened.");
			
			// Инициализируем подключение к ActiveMQ
			logger.info("Initializing connection to ActiveMQ...");
			ActiveMqProvider.getInstance();
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
			MongoDbProvider.getInstance().getDatastore().getMongo().close();
			logger.info("MongoDB connection was closed.");
			ActiveMqProvider.getInstance().getConnection().close();
			Thread.sleep(1000);
		} catch (Exception e) {
			logger.error("Error closing MongoDB connection", e);
		}
		
		logger.info("Analyser processor application stopped.");
	}



}