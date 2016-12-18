package kz.aphion.adverts.phone.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import kz.aphion.adverts.phone.MqConsumerInitializator;
import kz.aphion.adverts.phone.providers.ActiveMqProvider;
import kz.aphion.adverts.phone.providers.MongoDbProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationStartupListener  implements ServletContextListener  {

	private static Logger logger = LoggerFactory.getLogger(ApplicationStartupListener.class);

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info("Starting Phones Processor application...");
		
		try {
			
			// Инициализируем подключение к Mongo
			logger.info("Initializing connection to MongoDB...");
			MongoDbProvider dbProvider = MongoDbProvider.getInstance();
			logger.info("MongoDB connection is opened.");
			
			// Инициализируем подключение к ActiveMQ
			logger.info("Initializing connection to ActiveMQ...");
			ActiveMqProvider mqProvider = ActiveMqProvider.getInstance();
			logger.info("ActiveMQ connection is opened.");
			
			// Запускаем Lister для обработки сообщений
			logger.info("Activating MQ Listeners...");
			MqConsumerInitializator.initListeners();
			logger.info("MQ Listeners activated.");
			
			mqProvider.getConnection().start();
			
		} catch (Exception ex) {
			logger.error("Can't start Phones Processor", ex);
		}
	}	
	
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info("Shutting down Phones Processor application.");
		
		// Завершаем работу потоков чтения сообщений из очередей
		// TODO close listeneres on mq

		// Закрываем подключение к MongoDB
		logger.info("Closing MongoDB connection...");
		try {
			MongoDbProvider.getInstance().getDatastore().getMongo().close();
			logger.info("MongoDB connection was closed.");
		} catch (Exception ex) {
			logger.error("Can't close Mongo connection", ex);
		}
		
		logger.info("Phones Processor application stopped.");
	}

}