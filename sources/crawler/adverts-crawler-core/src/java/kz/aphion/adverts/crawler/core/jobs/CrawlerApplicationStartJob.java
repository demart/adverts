package kz.aphion.adverts.crawler.core.jobs;

import kz.aphion.adverts.crawler.core.CrawlerInitializationFactory;
import kz.aphion.adverts.crawler.core.MessageQueueProvider;
import kz.aphion.adverts.crawler.core.MongoDBProvider;
import play.Logger;
import play.db.DB;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * Служба отвечает за запуск Cralwer процессов при старте Play приложения.
 * Возможно использовать и синхронный и асинхронный режим старта.
 * 
 * Для работы класса необходимо чтобы от него существовал класс-наследник в Play проекте
 * 
 * @author artem.demidovich
 *
 */
@SuppressWarnings("rawtypes")
public class CrawlerApplicationStartJob extends play.jobs.Job {
	
	/**
	 * Медот выполняет инициализацию всех Crawler процессов с соотвествии с указанной группой в коде или в настройках.
	 *
	 * 1) Вначале идет проверка открытыя соединения с ActiveMQ
	 * 2) Проверка подключаения к БД
	 * 3) Запуск cralwer
	 */
	@Override
	public void doJob() throws Exception {
		super.doJob();
		Logger.info("Crawlers initialization started");
		
		// Инициализация MQ соединения
		MessageQueueProvider.getInstance();
		
		// Инициализация MongoDB
		MongoDBProvider.getInstance();
		
		
		//c3p0();
		
		CrawlerInitializationFactory.getInstance().init();
		
		//c3p0();
		
		Logger.info("Crawlers initialization finished");
		
	}
	
	
	public static void c3p0() {
        ComboPooledDataSource local = (ComboPooledDataSource) DB.datasource;
        try {
            Logger.info("===============C3P0 STATS================");

            Logger.info("MaxConnectionAge: " + local.getMaxConnectionAge());
            Logger.info("MaxPoolSize: " + local.getMaxPoolSize());
            Logger.info("NumConnectionsAllUsers: " + local.getNumConnectionsAllUsers());
            Logger.info("NumConnectionsDefaultUsers: " + local.getNumConnectionsDefaultUser());

            Logger.info("NumBusyConnectionsAllUsers: " + local.getNumBusyConnectionsAllUsers());
            Logger.info("NumBusyConnectionsDefaultUser: " + local.getNumBusyConnectionsDefaultUser());

            Logger.info("LastCheckinFailureDefaultUser: " + local.getLastCheckinFailureDefaultUser());
            Logger.info("NumFailedCheckinsDefaultUser: " + local.getNumFailedCheckinsDefaultUser());
            Logger.info("NumFailedCheckoutsDefaultUser: " + local.getNumFailedCheckoutsDefaultUser());

            Logger.info("NumIdleConnectionsAllUser: " + local.getNumIdleConnectionsAllUsers());
            Logger.info("NumIdleConnectionsDefaultUser: " + local.getNumIdleConnectionsDefaultUser());

            Logger.info("NumUnclosedOrphanedConnectionsAllUsers: " + local.getNumUnclosedOrphanedConnectionsAllUsers());
            Logger.info("NumUnclosedOrphanedConnectionsDefaultUsers: " + local.getNumUnclosedOrphanedConnectionsDefaultUser());
            Logger.info("===============END STATS================");
        } 
        catch (Exception e) {
        	e.printStackTrace();
        }
    }
	
}
