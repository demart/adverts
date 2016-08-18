package kz.aphion.adverts.subscription.jobs;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Служба поиска подписок в которых есть объявление о которых нужно сообщить
 * пользователю через систему уведомлений
 * 
 * @author artem.demidovich
 *
 * Created at Aug 18, 2016
 */
public class SearchSubscriptionNotificationJob implements org.quartz.Job {

	private static Logger logger = LoggerFactory.getLogger(SearchSubscriptionNotificationJob.class);

    public SearchSubscriptionNotificationJob() {
    	logger.info("Job: Search subscriptions with adverts to notify started.");
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
    	logger.info("Search adverts in subscription job run.");
    	
    	logger.info("Search adverts in subscription job finished.");
    }

}
