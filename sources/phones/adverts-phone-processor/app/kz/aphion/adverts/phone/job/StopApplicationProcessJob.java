package kz.aphion.adverts.phone.job;

import kz.aphion.adverts.phone.providers.MongoDbProvider;
import play.Logger;
import play.jobs.Job;
import play.jobs.OnApplicationStop;

/**
 * Класс Job закрывающий подключения к MQ
 * @author artem.demidovich
 *
 */
@OnApplicationStop
public class StopApplicationProcessJob extends Job {

	
	@Override
	public void doJob() throws Exception {
		super.doJob();
		Logger.info("Shutting down phone processor application.");
		
		// Завершаем работу потоков чтения сообщений из очередей


		// Закрываем подключение к MongoDB
		Logger.info("Closing MongoDB connection...");
		MongoDbProvider.getInstance().getDatastore().getMongo().close();
		Logger.info("MongoDB connection was closed.");
		Logger.info("Phone processor application stopped.");
	}
	
}
