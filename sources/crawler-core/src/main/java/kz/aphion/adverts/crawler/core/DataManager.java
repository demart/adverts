package kz.aphion.adverts.crawler.core;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.ProxyServerModel;
import kz.aphion.adverts.crawler.core.models.UserAgentModel;
import kz.aphion.adverts.persistence.crawler.Crawler;
import kz.aphion.adverts.persistence.crawler.CrawlerGroup;
import kz.aphion.adverts.persistence.crawler.CrawlerGroupStatusEnum;
import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;
import kz.aphion.adverts.persistence.crawler.CrawlerStatusEnum;
import kz.aphion.adverts.persistence.crawler.ProxyServer;
import kz.aphion.adverts.persistence.crawler.ProxyServerStatusEnum;
import kz.aphion.adverts.persistence.crawler.ProxyServerTypeEnum;
import kz.aphion.adverts.persistence.crawler.UserAgent;
import kz.aphion.adverts.persistence.crawler.UserAgentStatusEnum;
import kz.aphion.adverts.persistence.crawler.UserAgentTypeEnum;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;

/**
 * Менеджер по работе с persistence данными
 * 
 * @author artem.demidovich
 *
 */
public class DataManager {

	public static List<CrawlerGroup> getCrawlerGroupBySourceSystemType(CrawlerSourceSystemTypeEnum type) throws Exception {
		/*List<CrawlerGroupEntity> crawlerGroups = JPA.em().createQuery("from CrawlerGroupEntity where sourceSystemType = :type and status = :status")
				.setParameter("type", type)
				.setParameter("status", CrawlerGroupStatusEnum.ACTIVE)
				.getResultList();
	
				return crawlerGroups;
		*/
		Query<CrawlerGroup> q = DB.DS().createQuery(CrawlerGroup.class);		 
		q.field("sourceSystemType").equal(type);
		q.field("status").equal(CrawlerGroupStatusEnum.ACTIVE);
		 
		return q.asList();
	}
	
	
	public static List<Crawler> getCrawlersByGroup(CrawlerGroup crawlerGroup) throws Exception {
		Query<Crawler> q = DB.DS().createQuery(Crawler.class);		 
		q.field("crawlerGroup").equal(crawlerGroup);
		q.field("status").equal(CrawlerStatusEnum.ACTIVE);
		 
		return q.asList();
	}
	
	
	/**
	 * Возвращает UserAgentModel для использования при вызовах сервиса
	 * 
	 * @return
	 * @throws Exception 
	 */
	public static UserAgentModel getRandomUserAgent(UserAgentTypeEnum type) throws Exception {
		/*
		List<UserAgentEntity> ua = JPA.em().createQuery("FROM UserAgentEntity where status = :status and type = :type ORDER BY RANDOM()")
				.setParameter("status", UserAgentStatusEnum.ACTIVE)
				.setParameter("type", type)
				.setMaxResults(1)
				.getResultList();
		if (ua.size() > 0) {
			return UserAgentModel.buildFromEntity(ua.get(0));
		*/
		
		
		Query<UserAgent> q = DB.DS().createQuery(UserAgent.class);		 
		q.field("type").equal(type);
		q.field("status").equal(UserAgentStatusEnum.ACTIVE);
		
		long totalAmount = q.countAll();
		
		if (totalAmount < 0) {
			throw new CrawlerException("Active User-Agent not found");
		}
		
		q.limit(1);
		
		Random random = new Random(totalAmount);
		q.offset(random.nextInt());
		
		List<UserAgent> result = q.asList();
		if (result.size() > 0) {
			return UserAgentModel.buildFromEntity(result.get(0));
		} else {
			throw new CrawlerException("Active User-Agent not found");
		}
		
	}
	
	/**
	 * Возвращает активный ProxyServer для использованиях в вызовах краулерах 
	 * @param type
	 * @return
	 * @throws Exception 
	 */
	public static ProxyServerModel getRandomProxyServer(ProxyServerTypeEnum type) throws Exception {
		/*
		List<ProxyServerEntity> ua = JPA.em().createQuery("FROM ProxyServerEntity where status = :status and type = :type ORDER BY RANDOM()")
				.setParameter("status", ProxyServerStatusEnum.ACTIVE)
				.setParameter("type", type)
				.setMaxResults(1)
				.getResultList();
		if (ua.size() > 0) {
			return ProxyServerModel.buildFromEntity(ua.get(0));
		} else {
			throw new CrawlerException("Active ProxyServers not found");
		}
		*/
		
		Query<ProxyServer> q = DB.DS().createQuery(ProxyServer.class);		 
		q.field("type").equal(type);
		q.field("status").equal(ProxyServerStatusEnum.ACTIVE);
		
		long totalAmount = q.countAll();
		
		if (totalAmount < 0) {
			throw new CrawlerException("Active User-Agent not found");
		}
		
		q.limit(1);
		
		Random random = new Random();
		q.offset(random.nextInt());
		
		List<ProxyServer> result = q.asList();
		if (result.size() > 0) {
			return ProxyServerModel.buildFromEntity(result.get(0));
		} else {
			throw new CrawlerException("Active ProxyServers not found");
		}
		
	}
	
	
	/**
	 * Метод обновляет время последнего сканирования в БД, для последующей синхронизации
	 * @param model
	 * @param lastSourceScannedTime
	 * @throws Exception 
	 */
	public static void updateLastSourceScannedTime(CrawlerModel model, Calendar lastSourceScannedTime) throws Exception {
		if (model == null || lastSourceScannedTime == null)
			throw new CrawlerException("CrawlerModel or lastSourceScannedTime is null");
		
		/*
		JPA.em().createQuery("update CrawlerEntity set lastSourceSystemScannedTime = :time where id = :id")
		.setParameter("id", model.id)
		.setParameter("time", lastSourceScannedTime)
		.executeUpdate();
		*/
		
		Query<Crawler> q = DB.DS().createQuery(Crawler.class);		 
		q.field("id").equal(new ObjectId(model.id));
		List<Crawler> crawlers = q.asList();
		
		if (crawlers.size() > 0) {
			Crawler crwl = crawlers.get(0);
			crwl.lastSourceSystemScannedTime = lastSourceScannedTime;
			DB.DS().merge(crwl);
		} else {
			// TODO Show WARN message
		}
		
		/* replaced with merge operations
		UpdateOperations<Crawler> ops = MongoDBProvider
			.getInstance().getDatastore()
			.createUpdateOperations(Crawler.class)
			.set("lastSourceSystemScannedTime", lastSourceScannedTime);
			
			
		MongoDBProvider.getInstance().getDatastore().update(
				MongoDBProvider.getInstance().getDatastore().createQuery(Crawler.class).field("id").equal(new ObjectId(model.id)), ops);
		*/
	}
	
}
