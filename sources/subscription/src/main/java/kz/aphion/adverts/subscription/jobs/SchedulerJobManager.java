package kz.aphion.adverts.subscription.jobs;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс позволяет управлять Job процессами
 * @author artem.demidovich
 *
 * Created at Aug 18, 2016
 */
public class SchedulerJobManager {

	private static Logger logger = LoggerFactory.getLogger(SchedulerJobManager.class);

	private static Scheduler scheduler;
	
	private static SchedulerJobManager _instance;
	
	public static SchedulerJobManager getInstance() {
		if (_instance == null) {
			_instance = new  SchedulerJobManager();
		}
		return _instance;
	}
	
	
	public void startScheduler() throws SchedulerException {
	 // Grab the Scheduler instance from the Factory
	  scheduler = StdSchedulerFactory.getDefaultScheduler();

	  // Init adverts searcher in subscriptions
	  logger.info("Registering Job: " + SearchSubscriptionNotificationJob.class.getName());
	  initJob(scheduler, "SearchAdvertSubscriptionToNotify", SearchSubscriptionNotificationJob.class, 30);
	  
	  scheduler.start();
	}
	
	public void stopScheduler() throws SchedulerException {
		scheduler.shutdown();
	}

	
    /**
     * Метод позволяет зарегистрировать данную зачачу
     * @param scheduler
     * @throws SchedulerException
     */
    @SuppressWarnings("rawtypes")
	private static void initJob(Scheduler scheduler, String jobName, Class jobClass, int secondInterval) throws SchedulerException {
    	// define the job and tie it to our MyJob class
    	  @SuppressWarnings("unchecked")
		JobDetail job = JobBuilder.newJob(jobClass)
    	      .withIdentity(jobName, "ScheduledJobs")
    	      .build();

    	  // Trigger the job to run now, and then repeat every 40 seconds
    	  Trigger trigger = newTrigger()
    	      .withIdentity("RunJob", "ScheduledJobs")
    	      .startNow()
    	      .withSchedule(
    	    		  simpleSchedule()
    	    		  .withIntervalInSeconds(secondInterval)
    	    		  .repeatForever())
    	      .build();

    	  // Tell quartz to schedule the job using our trigger
    	  scheduler.scheduleJob(job, trigger);
    }
	
	
}