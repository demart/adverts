package kz.aphion.adverts.crawler.core;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import kz.aphion.adverts.crawler.core.annotations.CrawlerJob;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerGroupNotFoundException;
import kz.aphion.adverts.crawler.core.exceptions.CrawlersNotFoundException;
import kz.aphion.adverts.crawler.core.jobs.CrawlerProcessJob;
import kz.aphion.adverts.crawler.core.models.CrawlerGroupModel;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;
import kz.aphion.adverts.crawler.entity.CrawlerEntity;
import kz.aphion.adverts.crawler.entity.CrawlerGroupEntity;
import kz.aphion.adverts.crawler.entity.CrawlerParameterEntity;
import kz.aphion.adverts.crawler.entity.CrawlerSourceSystemTypeEnum;
import play.Logger;
import play.Play;
import play.PlayPlugin;
import play.jobs.JobsPlugin;
import play.libs.Expression;
import play.libs.Time;

/**
 * Класс инициализации потоков Crawlerов
 * @author artem.demidovich
 *
 */
public class CrawlerInitializationFactory {

	private static CrawlerInitializationFactory _instance;
	
	private CrawlerInitializationFactory() {
		super();
	}
	public static CrawlerInitializationFactory getInstance() {
		if (_instance == null)
			_instance = new CrawlerInitializationFactory();
		return _instance;
	}

	// 1. Читает конфигурацию Crawlerа (Какой именно Crawler
	// 2. Читает конфигурацию из БД
	// 3. Запускает Crawler потоки
	// 4. Проверяет доступность сервера очередей
	// 5. Проверяет доступность очередей на сервере очередей
	// 6. Проверяет наличие User-Agent если выбрана опция
	// 7. Проверяет наличие Proxy Servers если выбрана опция
	public void init() throws CrawlersNotFoundException, InstantiationException, IllegalAccessException, CrawlerException {
		List<CrawlerProcessJob> crawlerClasses = loadAllCrawlers();
		
		if (Logger.isDebugEnabled()) {
			Logger.debug("========= All loaded plugins ==========");
			Logger.debug("All loaded plugins");
			for (PlayPlugin plugin : Play.pluginCollection.getAllPlugins()) {
				Logger.debug(plugin.getClass().getName());
			}
			Logger.debug("========= ================== ==========");
			
			Logger.debug("========= All enabled plugins =========");
			for (PlayPlugin plugin : Play.pluginCollection.getEnabledPlugins()) {
				Logger.debug(plugin.getClass().getName());
			} 
			Logger.debug("========= ================== ==========");
		}
		
		for (CrawlerProcessJob crawlerProcessJob : crawlerClasses) {
			List<CrawlerGroupEntity> groups = DataManager.getCrawlerGroupBySourceSystemType(crawlerProcessJob.sourceSystemType);
			if (groups == null || groups.size() == 0)
				throw new CrawlerGroupNotFoundException("CrawlerGroup with source " + crawlerProcessJob.sourceSystemType + " not found. Check configuration.");
			if (groups.size() > 1)
				throw new CrawlerGroupNotFoundException("CrawlerGroup with source " + crawlerProcessJob.sourceSystemType + " found more than 1 times. Check configuration.");
			
			// Инициализируем все доступные Crawler
			initCrawlers(crawlerProcessJob, groups.get(0));
		}
		
		// Найти конфигурации для группы
		// Проинициализировать каждый Crawler
		// Поставить в очередь исполнения с указанной переодичностью
		
	}
	
	private void initCrawlers(CrawlerProcessJob crawlerProcessJob, CrawlerGroupEntity group) throws CrawlersNotFoundException, InstantiationException, IllegalAccessException {
		List<CrawlerEntity> creawlerEntities = group.getActiveCrawlers();
		if (creawlerEntities == null || creawlerEntities.size() < 1)
			throw new CrawlersNotFoundException("Active crawlers not found. Please check DB crawler configurations");
		
		for (CrawlerEntity crawlerEntity : creawlerEntities) {
			initCrawler(crawlerProcessJob, group, crawlerEntity);
		}
		
	}
	
	private void initCrawler(CrawlerProcessJob crawlerProcessJob, CrawlerGroupEntity group, CrawlerEntity crawlerEntity) throws InstantiationException, IllegalAccessException {
		CrawlerModel crawlerModel = convertCrawlerEntityToModel(group, crawlerEntity);
		
		CrawlerProcessJob job = (CrawlerProcessJob) crawlerProcessJob.getClass().newInstance();
        job.crawlerModel = crawlerModel;
        
        JobsPlugin.scheduledJobs.add(job);
        String value = job.crawlerModel.runEvery;
        if (value.startsWith("cron.")) {
            value = Play.configuration.getProperty(value);
        }
        value = Expression.evaluate(value, value).toString();
        if(!"never".equalsIgnoreCase(value)){
        	JobsPlugin.executor.scheduleWithFixedDelay(job, Time.parseDuration(value), Time.parseDuration(value), TimeUnit.SECONDS);
        }
	}
	
	
	private CrawlerModel convertCrawlerEntityToModel(CrawlerGroupEntity group, CrawlerEntity crawlerEntity) {
		CrawlerModel model = new CrawlerModel();
		model.alias = crawlerEntity.getAlias();
		model.destinationQueueName = crawlerEntity.getDestinationQueueName();
		model.id = crawlerEntity.getId();
		model.lastSourceSystemScannedTime = crawlerEntity.getLastSourceSystemScannedTime();
		model.lastUsage = crawlerEntity.getLastUsage();
		model.name = crawlerEntity.getName();
		model.runEvery = crawlerEntity.getRunEvery();
		model.status = crawlerEntity.getStatus();
		
		CrawlerGroupModel groupModel = new CrawlerGroupModel();
		groupModel.alias = group.getAlias();
		groupModel.description = group.getDescription();
		groupModel.id = group.getId();
		groupModel.name = group.getName();
		groupModel.sourceSystemBaseUrl = group.getSourceSystemBaseUrl();
		groupModel.sourceSystemType = group.getSourceSystemType();
		groupModel.status = group.getStatus();
		groupModel.useProxyServers = group.getUseProxyServers();
		groupModel.useUserAgents = group.getUseUserAgents();
		model.crawlerGroup = groupModel;
		
		if (crawlerEntity.getActiveParameters() != null)
			model.parameters = new ArrayList<CrawlerParameterModel>();
		
		for (CrawlerParameterEntity parameter : crawlerEntity.getActiveParameters()) {
			CrawlerParameterModel parameterModel = new CrawlerParameterModel();
			parameterModel.description = parameter.getDescription();
			parameterModel.id = parameter.getId();
			parameterModel.key = parameter.getKey();
			parameterModel.name = parameter.getName();
			parameterModel.status = parameter.getStatus();
			parameterModel.value = parameter.getValue();
			model.parameters.add(parameterModel);
		}
		
		return model;
	}
	
	
	/**
	 * Метод выполняет поиск в classloader всех Crawler наследников и создает их экземпляры
	 * 
	 * @return
	 * @throws CrawlersNotFoundException Не найдено ни одного Crawler необходимо остановить работу процесса
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("rawtypes")
	private List<CrawlerProcessJob> loadAllCrawlers() throws CrawlersNotFoundException, InstantiationException, IllegalAccessException {
		Logger.info("Start looking for crawler jobs classes");
		
		Class[] allClasses = Thread.currentThread().getContextClassLoader().getClass().getClasses();
		for (Class class1 : allClasses) {
			Logger.warn(class1.getClass().getName());
		}
		
		List<Class> crawlerClasses = Play.classloader.getAnnotatedClasses(CrawlerJob.class);
		if (crawlerClasses == null || crawlerClasses.size() < 1)
			throw new CrawlersNotFoundException("Crawlers were not found. Please check you classloader and CrawlerJob annotation.");

		List<CrawlerProcessJob> crawlerProcessJobs = new ArrayList<CrawlerProcessJob>();
		for (Class crawlerClass : crawlerClasses) {
			if (!CrawlerProcessJob.class.isAssignableFrom(crawlerClass)) {
				Logger.warn("Crawler class: " + crawlerClass.getName() + " will be skipped because it is not an inheritor of " + CrawlerProcessJob.class.getName());
				continue;
			}

			CrawlerProcessJob crawlerProcessJob = (CrawlerProcessJob)crawlerClass.newInstance();
			CrawlerSourceSystemTypeEnum value = crawlerProcessJob.getClass().getAnnotation(CrawlerJob.class).source();
			crawlerProcessJob.sourceSystemType = value;
			crawlerProcessJobs.add(crawlerProcessJob);
			
			Logger.info("Found Crawler class: " + crawlerClass.getName() + " with Source System: " + value);
		}
		
		if (crawlerProcessJobs == null || crawlerProcessJobs.size() < 1)
			throw new CrawlersNotFoundException("Crawlers were not found. Please check you classloader and CrawlerJob annotation and ancestor class.");
		
		return crawlerProcessJobs;
	}
	
	
}
