package kz.aphion.adverts.crawler.olx.jobs;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.models.mq.phones.RegisterPhoneModel;
import kz.aphion.adverts.crawler.core.DataManager;
import kz.aphion.adverts.crawler.core.annotations.CrawlerJob;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.jobs.CrawlerProcessJob;
import kz.aphion.adverts.crawler.olx.OlxJsonToMapParser;
import kz.aphion.adverts.crawler.olx.OlxRealtyComparator;
import kz.aphion.adverts.crawler.olx.UrlBuilder;
import kz.aphion.adverts.crawler.olx.mappers.OlxAdvertMapper;
import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;
import kz.aphion.adverts.persistence.phones.PhoneOwner;
import kz.aphion.adverts.persistence.phones.PhoneSource;
import kz.aphion.adverts.persistence.phones.PhoneSourceCategory;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.GsonBuilder;

/**
 * Класс маркер для того чтобы указать что в данном проекте есть Crawler который отвечает
 * за обработку объявлений OLX
 * @author artem.demidovich
 *
 * Created at May 18, 2016
 */
@CrawlerJob(source=CrawlerSourceSystemTypeEnum.OLX)
public class OlxRealtyCrawlerJob extends CrawlerProcessJob  {

	private static Logger logger = LoggerFactory.getLogger(OlxRealtyCrawlerJob.class);
	
	@Override
	public void doJob() throws Exception {
		super.doJob();
		
		// Указываем названия crawler для последующего логирования
		Thread.currentThread().setName(crawlerModel.getCralwerFullAlias());
		
		logger.info("Crawler started");
		
		try {
			startCrawle();
		} catch (Exception ex) {
			logger.error("We've got an error during crawler process. Please see error information.", ex);
		}
		
		logger.info("Crawler finished");
	}
	
	
	private void startCrawle() throws Exception {
		// Метод проводит валидацию обязательный полей
		validateModel();
		
		// Время последней успешной выгрузки данных из источника
		if (crawlerModel.lastSourceSystemScannedTime == null) {
			// Если время не указано, значит только начал работать данный crawler.
			// Необходимо обновить время crawler и закончиться обработку до следующего раза
			logger.warn("Crawler lastSourceSystemScannedTime is null, will be set current and processing continue next time.");
			crawlerModel.lastSourceSystemScannedTime = Calendar.getInstance();
			// TODO UPDATE TIME IN CRAWLER
			DataManager.updateLastSourceScannedTime(crawlerModel, crawlerModel.lastSourceSystemScannedTime);
			return;
		}
		
		
		// Подключение к Монго
		Datastore ds = DB.DS();
		
		// Инициализиурем UrlBuilder
		UrlBuilder.getInstance().init(this.crawlerModel);
					
		// Время старта выгрузки
		Calendar startProcessingTime = null;
		
		// Выгружаемая страница
		int page = 0;
		
		// Были ли новые объявления в выборке
		// Нужно для того чтобы завершить выборки постраничные
		boolean foundNewAdverts = false;
		
		// STATS
		int foundNewAdvertsCount = 0;
		int foundExistingAdvertsCount = 0;
		int foundExistingUpToDateAdvertsCount = 0;
		int foundExistingUpdateAdvertsCount = 0;
		int totalCount = 0;
		int skippedAdvertsCount = 0;
		
		do {
			foundNewAdverts = false;
			page = page + 1;
			String targetUrl = UrlBuilder.getInstance().getQueryBuilder().buildQueryUrl(page);  
			
			// Выгружаем контент с источника
			String jsonContent = UrlBuilder.getInstance().callServerAndGetJsonData(targetUrl);
			
			// Время запуска процесса для отсеивания обработки
			if (page == 1) {
				startProcessingTime = Calendar.getInstance();
				
				// Отнимаем минуту чтобы не пропутить предыдущие записи
				crawlerModel.lastSourceSystemScannedTime.add(Calendar.MINUTE, -3);
			}
			
			// Конвертируем полученные ответ с сервера в JSON Map
			Map<String, Object> jsonResponseMap = OlxJsonToMapParser.convertJson(jsonContent);
			
			// Проверка на корректный ответ с сервера
			boolean isResponseStatusOk = isResponseStatusOk(jsonResponseMap);
			if (!isResponseStatusOk) {
				// TODO complete process
				logger.error("Response from Krisha was not OK. Proccessing stuck. Please check service URL:" + targetUrl);
				return;
			}
			
			// Передать
			// 1. QueryBuilder
			// 2. 
			// Корвертируем объекты
			List<Realty> adverts = OlxAdvertMapper.extractAndConvertAdverts(jsonResponseMap);
			if (adverts == null) {
				logger.info("No any adverts to process. Compliting process.");
				// TODO Completing process
				
			}		
			
			totalCount += adverts.size();
			
			// Делаем проверки или дополнительные связи со структурой БД
			// Проверяем обрабатывали ли объявление		
			// Выкидываем обработанные объявления
			for (Realty realty : adverts) {
				if (crawlerModel.lastSourceSystemScannedTime.compareTo(realty.publishedAt) < 0  
						&& startProcessingTime.compareTo(realty.publishedAt) >= 0
						) {
					// Объявление нужно обработать
					//Logger.info("Advert with Id [" + realty.source.externalAdvertId + "] with publishedTime: [" + realty.publishedAt.getTime().toLocaleString() + "] in scanning period between: ["+ crawlerModel.lastSourceSystemScannedTime.getTime().toLocaleString() +"] and ["+ startProcessingTime.getTime().toLocaleString() +"]");
					// Проверить есть ли такое объявление уже в БД то что делать?

					 List existingAdverts = getExistingAdverts(ds, realty);
					 if (existingAdverts.size() > 0) {
						 foundExistingAdvertsCount += 1;
						 
						 // Так как поидее не должно быть больше одного
						 Realty existingRealty = (Realty)existingAdverts.get(0);
						 boolean wasUpdated = false;
						 
						 wasUpdated = OlxRealtyComparator.isRealtyUpdated(existingRealty, realty);
						 
						 if (wasUpdated) {
							 foundExistingUpdateAdvertsCount +=1;
							 
							 // TODO что-то сделать
							 // Пока возьмем и старое в архив, а новое запишем
							 // потом отправим с обработку с пометкой
							 
							 // Обновляем старую версию
							 Query updateQuery = ds.createQuery(realty.getClass()).field(Mapper.ID_KEY).equal(existingRealty.id);
							 UpdateOperations<?> ops = ds.createUpdateOperations(realty.getClass()).set("status", RealtyAdvertStatus.ARCHIVED);
							 ds.update(updateQuery, ops);
							 
							 // Сохраняем новую версию
							 ds.save(realty);
							 
							 // Отправляем сообщение в очередь обработки телефонов
							 // ПОКА ПОД ВОПРОСОМ ТАК КАК МНОГО СООБЩЕНИЙ ПОЙДЕТ В ОЧЕРЕДЬ ТЕЛЕФОНОВ
							 sendPhoneNumberRegistrationMessage(realty);
							 
							 logger.info("Advert [{}] with id [{}] was moved to archive, with id [{}] added.", realty.source.externalAdvertId, existingRealty.id, realty.id);
							 
							 /*
							 GsonBuilder builder = new GsonBuilder();
							 builder.setPrettyPrinting();
							 Gson gson = builder.create();
							 Logger.warn("");
							 Logger.warn(gson. toJson(realty));
							 Logger.warn("");
							 Logger.warn(gson.toJson(existingRealty));
							 Logger.warn("");
							 */
						 } else {
							 // Ничего не делаем, так как по умолчанию считаем что объявление не изменилось
							 logger.info("Advert [" + realty.source.externalAdvertId + "] already exists and up-to-date.");
							 foundExistingUpToDateAdvertsCount +=1;
						 }
						 
						 //Logger.info("Advert was found in db with ActualStatus. Need to check and update");
					 } else {
						 foundNewAdvertsCount += 1;
						 logger.info("Advert is new one, we can process it");
						 ds.save(realty);
						 
						 // Отправляем сообщени в очердеь обработки объявления
						 // 
						 
						 // Отправляем сообщение в очередь обработки телефонов
						 sendPhoneNumberRegistrationMessage(realty);
					 }
					 
					 // Нашли новые объявления (даже если они уже есть в БД,
					 // Существующие нужно проверить на изменения, если нет то пропутсить
					 // Если есть изменения то нужно что-то сделать
					 foundNewAdverts = true;
				} else {
					skippedAdvertsCount += 1;
					// Время публикации не совпадает с интересующим нас временным отрезком
					if (logger.isDebugEnabled())
						logger.debug("Advert with Id [" + realty.source.externalAdvertId + "] with publishedTime: [" + realty.publishedAt.getTime().toLocaleString() + "] out of scanning period between: ["+ crawlerModel.lastSourceSystemScannedTime.getTime().toLocaleString() +"] and ["+ startProcessingTime.getTime().toLocaleString() +"]");
				}
			}
			
			
			// На OLX можно просматривать только 500 последних страниц.
			// Поэтому если ограничение сработало то нужно заканчивать
			// При нормальной стабильной работе мы до сюда даже доходить не должны
			// Я так понимаю, что они сделали эти ограничения потому что нормальный человек
			// даже 500 страниц просматривать не будет, он скорее всего начнет сужать критерии
			// поиска до получаения наилучшей выборки
			if (page == 500)
				foundNewAdverts = false;
			
		} while(foundNewAdverts);

		// Статистика
		logger.info("{\"new\":{}, \"existing\":{}, \"up-to-date\":{}, \"updated\":{}, \"skipped\":{}, \"total\":{}}", foundNewAdvertsCount, foundExistingAdvertsCount, foundExistingUpToDateAdvertsCount, foundExistingUpdateAdvertsCount, skippedAdvertsCount, totalCount);
		
		// Закончили обработку
		
		// Массово??? отправляем в очередь или сохраняем в БД
		// TODO:
		
		// Обновляем время последнего запуска
		crawlerModel.lastSourceSystemScannedTime = startProcessingTime;
		DataManager.updateLastSourceScannedTime(crawlerModel, startProcessingTime);		
		
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
		 q.field("source.sourceType").equal(SourceSystemType.OLX);
		 
		 return q.asList();
	}
	
	/**
	 * Отправляет необходимую информацию для регистрации телефона в объявлении
	 * 
	 * @param realty
	 */
	private void sendPhoneNumberRegistrationMessage(Realty realty) {
		try {
			RegisterPhoneModel model = new RegisterPhoneModel();
			model.source = PhoneSource.OLX;
			model.category = PhoneSourceCategory.REALTY;
			model.time = realty.publishedAt;
			model.phone = realty.publisher.phones;
			model.region = realty.location.region;
			model.regions = realty.location.regions;
			
			if (realty.publisher == null) {
				logger.error("Advert Id [{}] with Id[{}] Can't send message to registration phone queue, published is null.", realty.source.externalAdvertId, realty.id);
			}
			
			switch (realty.publisher.publisherType) {
				case DEVELOPER_COMPANY:
					model.owner = PhoneOwner.DEVELOPER_COMPANY;
					break;
				case OWNER:
					model.owner = PhoneOwner.OWNER;
					break;
				case REALTOR:
					model.owner = PhoneOwner.REALTOR;
					break;
				case REALTOR_COMPANY:
					model.owner = PhoneOwner.REALTOR_COMPANY;
					break;
				case UNDEFINED:
					model.owner = PhoneOwner.UNDEFINED;
					break;
				default:
					model.owner = PhoneOwner.UNDEFINED;
					break;
			}
			
			String message = new GsonBuilder().setPrettyPrinting().create().toJson(model);
			
		
			getMqProvider().sendTextMessageToQueue("adverts.phones.registration", message);
		} catch (JMSException | CrawlerException e) {
			logger.error("Error seding message to Phone registration queue", e);
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
	 * Метод выполняет вызов сервера источника для получения JSON ответа.
	 * Также метод выполняет проврерку на необхдоимость использования прокси сервера или разных заголовков
	 * @param targetUrl
	 * @return
	 * @throws CrawlerException
	 * @throws IOException
	 */
	/*
	private String callServerAndGetJsonData(String targetUrl) throws CrawlerException, IOException, Exception {
		// TODO: Увеличить счетчики использования User-Agent и Proxy Servers
		UserAgentModel uam = null;
		if (crawlerModel.crawlerGroup.useUserAgents) {
			uam = DataManager.getRandomUserAgent(UserAgentTypeEnum.MOBILE_PLATFORM);
			
			if (logger.isDebugEnabled())
				logger.debug("User-Agent: " + uam.userAgent + " with name [" + uam.name + "]");
		} 
		
		ProxyServerModel psm = null;
		if (crawlerModel.crawlerGroup.useProxyServers) {
			psm = DataManager.getRandomProxyServer(ProxyServerTypeEnum.HTTPS);
			if (logger.isDebugEnabled())
				logger.debug("Proxy-server: " + psm.host + ":" + psm.port + " with name [" + psm.name + "]");
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
	*/
	
	
	/**
	 * Метод проверяет корректный ли ответ с сервера
	 * @param jsonResponseMap
	 * @return
	 */
	private boolean isResponseStatusOk(Map<String, Object> jsonResponseMap) {
		if (jsonResponseMap.containsKey("total_ads")) {
			return true;
		} else {
			// нет в отчете статуса.. Чет не так ответили, поругаться и завершить обработку
			logger.error("Received data with empty total_ads field. Stopping current processing.");
			return false;
		}
	}
	
}
