package kz.aphion.adverts.crawler.kn.jobs;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import controllers.Application;
import kz.aphion.adverts.crawler.core.CrawlerHttpClient;
import kz.aphion.adverts.crawler.core.DataManager;
import kz.aphion.adverts.crawler.core.MongoDBProvider;
import kz.aphion.adverts.crawler.core.annotations.CrawlerJob;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.exceptions.CrawlersNotFoundException;
import kz.aphion.adverts.crawler.core.jobs.CrawlerProcessJob;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.CrawlerParameterModel;
import kz.aphion.adverts.crawler.core.models.ProxyServerModel;
import kz.aphion.adverts.crawler.core.models.UserAgentModel;
import kz.aphion.adverts.crawler.entity.CrawlerSourceSystemTypeEnum;
import kz.aphion.adverts.crawler.entity.ProxyServerTypeEnum;
import kz.aphion.adverts.crawler.entity.UserAgentTypeEnum;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.crawler.kn.QueryBuilder;
import kz.aphion.adverts.crawler.kn.mappers.KnAdvertMapper;
import kz.aphion.adverts.crawler.kn.mappers.RealtyComparator;
import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import play.Logger;

@CrawlerJob(source=CrawlerSourceSystemTypeEnum.KN)
public class KnCrawlerJob extends CrawlerProcessJob {

	@Override
	public void doJob() throws Exception {
		super.doJob();
		
		// Указываем названия crawler для последующего логирования
		Thread.currentThread().setName(crawlerModel.getCralwerFullAlias());
		try {
			Logger.info("Crawler started");
	
			startCrawle();
			
			Logger.info("Crawler finished");
		}
		
		catch (Exception e) {
			Logger.error(e.getMessage());
			e.printStackTrace();
		}
		//c3p0();
	}
	
	private void startCrawle() throws CrawlerException, IOException, ParseException {
		// Метод проводит валидацию обязательный полей
		validateModel();
		
		// Время последней успешной выгрузки данных из источника
		if (crawlerModel.lastSourceSystemScannedTime == null) {
			Logger.info("Last source system  scanned time is null. First start. It's ok.");
		}
		
		// Подключение к Монго
		Datastore ds = MongoDBProvider.getInstance().getDatastore();
		
		// Подготавливаем query для запроса
		QueryBuilder queryBuilder = new QueryBuilder();
		queryBuilder.prepareFilters(crawlerModel);
		
		// Время старта выгрузки
		Calendar startProcessingTime = null;
				
		
				
		// STATS
		int foundNewAdvertsCount = 0;
		int foundExistingAdvertsCount = 0;
		int foundExistingUpdateAdvertsCount = 0;
		int totalCount = 0;
		

		//Рабочие цикл
		//текущая страница
		Integer currentPage = 0;
				


		//для цикла do while, когда необходимо остановиться
		boolean isFinished = false;
		
		/**
		 * Алгоритм:
		 * 1. При первом запуске отсутствует значение поля у crawler-а last_source_update_time.
		 * 	  После первой выгрузки объявлений оно заносится в БД;
		 * 2. Потом при выгрузке каждого объявления проверяется его дата публикация, если она меньше чем 
		 * 	  last_source_update_time, то json == null при конвертации и цикл останавливается (так как
		 * 	  автоматически считаем, что все другие объявления мы уже просматривали);
		 * 3. Если время публикации и last_source_update_time совпадают, то объявление конвертится
		 * 	  в json и затем ищется его копия в БД, если нет копии то оно сохраняется, если есть, то
		 * 	  проверяется дата публикации, на сервисе КН можно редактировать и поднимать объявление
		 * 	  только раз в сутки! поэтому если мы его уже сегодня обработали, то считаем, что оно не обновленно и скипаем.
		 *    Дальше проверяем цену, фото, и описание (другие поля не надо, так как они обязательны к заполнению
		 *    и соответственно вряд ли человек будет их менять.
		 *    В случае нахождения изменений, то объявление в БД меняется статус ARCHIVED, а новое заносися в бд.
		 *    
		 */
		        
				do {
		        	try {
		        		//Увеличиваем текущую страницу на +1
			        	currentPage++;
			        	
			        	//При первом проходе время загрузки у кравлера null, поэтому выгружаем сразу все страницы
			        	//Потом у нас есть данное время, и будем грузить максимум 3 страницы, так как
			        	//вероятность того, что за минуту-две будет более 75 новых/поднятых объявлений =0,01
			        	if (crawlerModel.lastSourceSystemScannedTime != null)
			        		if (currentPage == 3)
			        			isFinished=true;
			        	
			        	//Засекаем время
			        	if (currentPage == 1)
			        		startProcessingTime = Calendar.getInstance();
			        	
			    		//Формируем запрос для каждой страницы
			    		String targetUrl = queryBuilder.buildQueryUrl(currentPage);
			        	
			    		//Получаем страницу с объявлениями
			    		String content = callServerAndGetData(targetUrl);
			    		
						// Получаем тип объявления для разбора
						KnAdvertCategoryType advertType = getAdvertType(crawlerModel);
			    		
			    		//Готовый список объявлений со странице
						List<Realty> adverts = KnAdvertMapper.parseAdvertsFromCurrentPageAndConvert(advertType, content, queryBuilder);
			        	
			    		//Условия для торможения цикла, а именно получения страниц
						//Если вернулся пустой список, тонадо тормозить цикл
			    		if (adverts.size() == 0) {
			    			isFinished = true;
			    		}
			    		
			    		if (adverts.size() > 0) {
			    			for (Realty realty : adverts) {
								//Увеличиваем счетчик всех объявлений
			    				totalCount++;
			    				
							    List existingAdverts = getExistingAdverts(ds, realty);
							    boolean wasUpdated = false;
							    if (existingAdverts.size() > 0) {
									 foundExistingAdvertsCount++;
									 
								// Так как поидее не должно быть больше одного
							    Realty existingRealty = (Realty)existingAdverts.get(0); 
							    wasUpdated = RealtyComparator.isUpdated(existingRealty, realty);
							    
							    if (wasUpdated) {
							    	foundExistingUpdateAdvertsCount++;
							    	
							    	 // Обновляем старую версию
									 Query updateQuery = ds.createQuery(realty.getClass()).field(Mapper.ID_KEY).equal(existingRealty.id);
									 UpdateOperations<?> ops = ds.createUpdateOperations(realty.getClass()).set("status", RealtyAdvertStatus.ARCHIVED);
									 ds.update(updateQuery, ops);
									
									 // Сохраняем новую версию
									 ds.save(realty);
									 
									 Logger.info("Advert [%s] with id [%s] was moved to archive, with id [%s] added.", realty.source.externalAdvertId, existingRealty.id, realty.id); 	
							    }
							    
							    existingAdverts.clear();
							    
							    }
							    
							    else {
							    	foundNewAdvertsCount++;
							    	ds.save(realty);
							    }
							}
			    		}
		        	}
		        	
		        	catch (Exception e) {
		        		Logger.error(e.getMessage());
		    			e.printStackTrace();
		        	}
		        
				}
				
				while (isFinished == false);
		        
				// Обновляем время последнего запуска
				crawlerModel.lastSourceSystemScannedTime = startProcessingTime;
				DataManager.updateLastSourceScannedTime(crawlerModel, startProcessingTime);

		        Logger.info ("Count of Adverts: " + totalCount +
		        					"; foundExistingUpdateAdvertsCount: " + foundExistingUpdateAdvertsCount +
		        					"; foundNewAdvertsCount: " + foundNewAdvertsCount + 
		        					"; foundExistingAdvertsCount: " + foundExistingAdvertsCount);
		
	}
	
	

	/**
	 * Проверяет наличие в базе активный записей с таким же внешним ID
	 * @param ds
	 * @param realty
	 * @return
	 */
	private List getExistingAdverts(Datastore ds, Realty realty) {
		Query q = ds.createQuery(realty.getClass());
		 
		 q.field("status").equal(RealtyAdvertStatus.ACTIVE);
		 q.field("source.externalAdvertId").equal(realty.source.externalAdvertId);
		 q.field("source.sourceType").equal(SourceSystemType.KN);
		 
		 return q.asList();
	}
    
	
	
	/**
	 * Метод выполняет вызов сервера источника для получения ответа.
	 * Также метод выполняет проврерку на необхдоимость использования прокси сервера или разных заголовков
	 * @param targetUrl
	 * @return
	 * @throws CrawlerException
	 * @throws IOException
	 */
	private String callServerAndGetData(String targetUrl) throws CrawlerException, IOException {
		// TODO: Увеличить счетчики использования User-Agent и Proxy Servers
		UserAgentModel uam = null;
		if (crawlerModel.crawlerGroup.useUserAgents) {
			uam = DataManager.getRandomUserAgent(UserAgentTypeEnum.BROWSER);
			
			if (Logger.isDebugEnabled())
				Logger.debug("User-Agent: " + uam.userAgent + " with name [" + uam.name + "]");
		} 
		
		ProxyServerModel psm = null;
		if (crawlerModel.crawlerGroup.useProxyServers) {
			psm = DataManager.getRandomProxyServer(ProxyServerTypeEnum.HTTPS);
			if (Logger.isDebugEnabled())
				Logger.debug("Proxy-server: " + psm.host + ":" + psm.port + " with name [" + psm.name + "]");
		}
		if (uam == null) {
			if (psm == null) {
				return CrawlerHttpClient.getContent(targetUrl);
			} else {
				return CrawlerHttpClient.getContent(targetUrl, psm.host, psm.port, null);
			}
			
		} else {
			if (psm == null) {
				return CrawlerHttpClient.getContent(targetUrl, null, null, uam.userAgent);
			} else {
				return CrawlerHttpClient.getContent(targetUrl, psm.host, psm.port, uam.userAgent);
			}
		}
	}
	
	
	// Статистика обработки в памяти доступная по JMX
	// Состояние обработки в памяти доступное по REST или JMX
	// Логирование в общий лог и лог каждого crawlerа
	// Отправка стат данных в MQ -> elasticsearch + kebana
	// Локальный кэш объявлений для проверки уже пройденных

	private void validateModel() throws CrawlerException {
		if (StringUtils.isBlank(crawlerModel.destinationQueueName))
			throw new CrawlerException("Destination queue name for crawler is empty");
		
		if (StringUtils.isBlank(crawlerModel.alias))
			throw new CrawlerException("Alias for crawler is empty");
		
		if (crawlerModel.parameters == null || crawlerModel.parameters.size() == 0)
			throw new CrawlerException("Parameters for crawler is empty");
	}
	
	/**
	 * Извлекает вид объявлений crawler'a
	 * 
	 * @param crawlerModel
	 * @return
	 * @throws CrawlersNotFoundException Если не удалось найти в настройках тип выгружаемых объявлений
	 */
	private KnAdvertCategoryType getAdvertType(CrawlerModel crawlerModel) throws CrawlersNotFoundException {
		for (CrawlerParameterModel model : crawlerModel.parameters) {
		//	Logger.warn("Value of model: " + model.value);
			if ("CATEGORY".equalsIgnoreCase(model.key)) {
				for (KnAdvertCategoryType type : KnAdvertCategoryType.values()) {
					if (type.getValue().equals(model.value))
						return type;
				}
				
			}
		}
		throw new CrawlersNotFoundException("Could not define kn advert type to crawle");
	}
}
