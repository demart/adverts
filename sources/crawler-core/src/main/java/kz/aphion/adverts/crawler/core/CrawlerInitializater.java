package kz.aphion.adverts.crawler.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerGroupNotFoundException;
import kz.aphion.adverts.crawler.core.exceptions.CrawlersNotFoundException;
import kz.aphion.adverts.crawler.core.jobs.CrawlerProcessJob;
import kz.aphion.adverts.crawler.core.models.CrawlerGroupInitializationModel;
import kz.aphion.adverts.crawler.core.models.CrawlerGroupModel;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;
import kz.aphion.adverts.persistence.crawler.Crawler;
import kz.aphion.adverts.persistence.crawler.CrawlerGroup;
import kz.aphion.adverts.persistence.crawler.CrawlerParameter;
import kz.aphion.adverts.persistence.crawler.CrawlerParameterStatusEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс инициализации потоков Crawlerов
 * @author artem.demidovich
 *
 */
public class CrawlerInitializater {
	
	private static Logger logger = LoggerFactory.getLogger(CrawlerInitializater.class);
	
	private static CrawlerInitializater _instance;
	
	public static ScheduledThreadPoolExecutor executor = null;
	public static List<CrawlerProcessJob<?>> scheduledJobs = null;
	
	private CrawlerInitializater() {
		super();
		
		scheduledJobs = new ArrayList<CrawlerProcessJob<?>>();
		
		//TODO EXPORT TO DB CONF
		int core = Integer.parseInt(System.getProperties().getProperty("adverts.crawler.pool", "10"));
		executor = new ScheduledThreadPoolExecutor(core, new CrawlerThreadFactory("crawlers"), new ThreadPoolExecutor.AbortPolicy());
	}
	public static CrawlerInitializater getInstance() {
		if (_instance == null)
			_instance = new CrawlerInitializater();
		return _instance;
	}
	
	// TODO Передать что есть в списке
	public void initWithCrawlerClasses(List<CrawlerGroupInitializationModel> crawlerGroups)  throws Exception {
		logger.info("Crawlers initialization started");
		
		// Инициализация MQ соединения
		MQ.INSTANCE.init();
		
		// Инициализация MongoDB
		DB.INSTANCE.init();
		
		
		for (CrawlerGroupInitializationModel crawlerGroupModel : crawlerGroups) {
			CrawlerProcessJob<?> crawlerProcessJob = (CrawlerProcessJob<?>)crawlerGroupModel.jobClass.newInstance();
			crawlerProcessJob.sourceSystemType = crawlerGroupModel.sourceSystem;
			
			List<CrawlerGroup> groups = DataManager.getCrawlerGroupBySourceSystemType(crawlerProcessJob.sourceSystemType);
			if (groups == null || groups.size() == 0)
				throw new CrawlerGroupNotFoundException("CrawlerGroup with source " + crawlerProcessJob.sourceSystemType + " not found. Check configuration.");
			if (groups.size() > 1)
				throw new CrawlerGroupNotFoundException("CrawlerGroup with source " + crawlerProcessJob.sourceSystemType + " found more than 1 times. Check configuration.");
			
			// Инициализируем все доступные Crawler
			initCrawlers(crawlerProcessJob, groups.get(0));
		}
		
		logger.info("Crawlers initialization finished");
	}
	
	
	private void initCrawlers(CrawlerProcessJob<?> crawlerProcessJob, CrawlerGroup group) throws CrawlersNotFoundException, InstantiationException, IllegalAccessException, Exception {
		List<Crawler> creawlerEntities = DataManager.getCrawlersByGroup(group);
		if (creawlerEntities == null || creawlerEntities.size() < 1)
			throw new CrawlersNotFoundException("Active crawlers not found. Please check DB crawler configurations");
		
		for (Crawler crawlerEntity : creawlerEntities) {
			initCrawler(crawlerProcessJob, group, crawlerEntity);
		}
	}
	
	private void initCrawler(CrawlerProcessJob<?> crawlerProcessJob, CrawlerGroup group, Crawler crawlerEntity) throws InstantiationException, IllegalAccessException {
		CrawlerModel crawlerModel = convertCrawlerEntityToModel(group, crawlerEntity);
		
		CrawlerProcessJob<?> job = crawlerProcessJob.getClass().newInstance();
        job.crawlerModel = crawlerModel;
        
        scheduledJobs.add(job);
        String value = job.crawlerModel.runEvery;
    	executor.scheduleWithFixedDelay(job, Long.parseLong(value), Long.parseLong(value), TimeUnit.SECONDS);
	}
	
	
	private CrawlerModel convertCrawlerEntityToModel(CrawlerGroup group, Crawler crawlerEntity) {
		CrawlerModel model = new CrawlerModel();
		model.alias = crawlerEntity.alias;
		model.destinationQueueName = crawlerEntity.destinationQueueName;
		model.id = crawlerEntity.id.toHexString();
		model.lastSourceSystemScannedTime = crawlerEntity.lastSourceSystemScannedTime;
		model.lastUsage = crawlerEntity.lastUsage;
		model.name = crawlerEntity.name;
		model.runEvery = crawlerEntity.runEvery;
		model.status = crawlerEntity.status;
		
		CrawlerGroupModel groupModel = new CrawlerGroupModel();
		groupModel.alias = group.alias;
		groupModel.description = group.description;
		groupModel.id = group.id.toHexString();
		groupModel.name = group.name;
		groupModel.sourceSystemBaseUrl = group.sourceSystemBaseUrl;
		groupModel.sourceSystemType = group.sourceSystemType;
		groupModel.status = group.status;
		groupModel.useProxyServers = group.useProxyServers;
		groupModel.useUserAgents = group.useUserAgents;
		model.crawlerGroup = groupModel;
		
		if (crawlerEntity.parameters != null)
			model.parameters = new ArrayList<CrawlerParameterModel>();
		
		for (CrawlerParameter parameter : crawlerEntity.parameters) {
			if (!parameter.status.equals(CrawlerParameterStatusEnum.ACTIVE))
				continue;
			
			CrawlerParameterModel parameterModel = new CrawlerParameterModel();
			parameterModel.description = parameter.description;
//			parameterModel.id = parameter.id.toHexString();
			parameterModel.key = parameter.key;
			parameterModel.name = parameter.name;
			parameterModel.status = parameter.status;
			parameterModel.value = parameter.value;
			model.parameters.add(parameterModel);
		}
		
		return model;
	}
	
	/**
	 * Метод останавливает всех crawlers нужно выполнить для того чтобы 
	 * корректно завершить работу приложения.
	 */
	public void stopCrawlers() {
		for (CrawlerProcessJob<?> crawlerProcessJob : scheduledJobs) {
			executor.remove(crawlerProcessJob);
		}
		
		// TODO Подумать как улучшить, чет не очень останавливается
		
		scheduledJobs.clear();
		executor.shutdownNow();
	}
	
}
