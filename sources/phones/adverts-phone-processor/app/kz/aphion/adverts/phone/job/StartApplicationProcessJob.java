package kz.aphion.adverts.phone.job;

import kz.aphion.adverts.phone.MqConsumerInitializator;
import kz.aphion.adverts.phone.providers.ActiveMqProvider;
import kz.aphion.adverts.phone.providers.MongoDbProvider;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * Класс запускает produceroв для обработки сообщений из очередей
 * 
 * @author artem.demidovich
 *
 */
@OnApplicationStart
public class StartApplicationProcessJob extends Job {

	@Override
	public void doJob() throws Exception {
		super.doJob();
		Logger.info("Starting phone processor application...");
		
		// Инициализируем подключение к Mongo
		Logger.info("Initializing connection to MongoDB...");
		MongoDbProvider dbProvider = MongoDbProvider.getInstance();
		Logger.info("MongoDB connection is opened.");
		
		// Инициализируем подключение к ActiveMQ
		Logger.info("Initializing connection to ActiveMQ...");
		ActiveMqProvider mqProvider = ActiveMqProvider.getInstance();
		Logger.info("ActiveMQ connection is opened.");
		
		// Запускаем Lister для обработки сообщений
		Logger.info("Activating MQ Listeners...");
		MqConsumerInitializator.initListeners();
		Logger.info("MQ Listeners activated.");
	}
	
}
