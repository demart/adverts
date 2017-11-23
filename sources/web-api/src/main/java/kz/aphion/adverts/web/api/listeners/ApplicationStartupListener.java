package kz.aphion.adverts.web.api.listeners;

import javax.jms.JMSException;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting web-api application...");
		
		try {
			// Инициализируем подключение к Mongo
			logger.info("Initializing connection to MongoDB...");
			DB.INSTANCE.init();
			logger.info("MongoDB connection is opened.");
			
			// Инициализируем подключение к ActiveMQ
			logger.info("Initializing connection to ActiveMQ...");
			MQ.INSTANCE.init();
			logger.info("ActiveMQ connection is opened.");
		
		} catch (Throwable e) {
			logger.error("FATAL error while starting web-api project", e);
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Shutting down web-api application...");
		
		// Завершаем работу потоков чтения сообщений из очередей
		logger.info("Closing JMS connection...");
		try {
			MQ.INSTANCE.getConnection().stop();
			MQ.INSTANCE.getConnection().close();
			logger.info("JMS connection was closed.");
		} catch (JMSException e) {
			logger.error("Error closing JMS connection", e);
		}
		
		// Закрываем подключение к MongoDB
		logger.info("Closing MongoDB connection...");
		try {
			//DB.DS().getMongo().fsync(false);
			DB.DS().getMongo().close();
			logger.info("MongoDB connection was closed.");
		} catch (Exception e) {
			logger.error("Error closing MongoDB connection", e);
		}
		
		logger.info("web-api application stopped.");
	}

	
	
}
