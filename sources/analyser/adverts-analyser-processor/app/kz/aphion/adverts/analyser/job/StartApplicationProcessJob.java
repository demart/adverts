package kz.aphion.adverts.analyser.job;

import kz.aphion.adverts.analyser.MqConsumerInitializator;
import kz.aphion.adverts.analyser.providers.ActiveMqProvider;
import kz.aphion.adverts.analyser.providers.MongoDbProvider;
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
		Logger.info("Starting analyser processor application...");
		
		// Инициализируем подключение к Mongo
		Logger.info("Initializing connection to MongoDB...");
		MongoDbProvider dbProvider = MongoDbProvider.getInstance();
		Logger.info("MongoDB connection is opened.");
		
		// Инициализируем подключение к ActiveMQ
		Logger.info("Initializing connection to ActiveMQ...");
		ActiveMqProvider mqProvider = ActiveMqProvider.getInstance();
		Logger.info("ActiveMQ connection is opened.");
		
		// Запускаем Listener для обработки сообщений
		Logger.info("Activating MQ Listeners...");
		MqConsumerInitializator.initListeners();
		Logger.info("MQ Listeners activated.");
	}
	
}
