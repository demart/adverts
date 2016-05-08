package kz.aphion.adverts.crawler.core;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.ProxyServerModel;
import kz.aphion.adverts.crawler.core.models.UserAgentModel;
import kz.aphion.adverts.crawler.entity.CrawlerGroupEntity;
import kz.aphion.adverts.crawler.entity.CrawlerGroupStatusEnum;
import kz.aphion.adverts.crawler.entity.CrawlerSourceSystemTypeEnum;
import kz.aphion.adverts.crawler.entity.ProxyServerEntity;
import kz.aphion.adverts.crawler.entity.ProxyServerStatusEnum;
import kz.aphion.adverts.crawler.entity.ProxyServerTypeEnum;
import kz.aphion.adverts.crawler.entity.UserAgentEntity;
import kz.aphion.adverts.crawler.entity.UserAgentStatusEnum;
import kz.aphion.adverts.crawler.entity.UserAgentTypeEnum;
import play.db.jpa.JPA;

/**
 * Менеджер по работе с persistence данными
 * 
 * @author artem.demidovich
 *
 */
public class DataManager {

	@SuppressWarnings("unchecked")
	public static List<CrawlerGroupEntity> getCrawlerGroupBySourceSystemType(CrawlerSourceSystemTypeEnum type) {
		List<CrawlerGroupEntity> crawlerGroups = JPA.em().createQuery("from CrawlerGroupEntity where sourceSystemType = :type and status = :status")
				.setParameter("type", type)
				.setParameter("status", CrawlerGroupStatusEnum.ACTIVE)
				.getResultList();
		return crawlerGroups;
	}
	
	
	/**
	 * Возвращает UserAgentModel для использования при вызовах сервиса
	 * 
	 * @return
	 * @throws CrawlerException 
	 */
	@SuppressWarnings("unchecked")
	public static UserAgentModel getRandomUserAgent(UserAgentTypeEnum type) throws CrawlerException {
		List<UserAgentEntity> ua = JPA.em().createQuery("FROM UserAgentEntity where status = :status and type = :type ORDER BY RANDOM()")
				.setParameter("status", UserAgentStatusEnum.ACTIVE)
				.setParameter("type", type)
				.setMaxResults(1)
				.getResultList();
		if (ua.size() > 0) {
			return UserAgentModel.buildFromEntity(ua.get(0));
		} else {
			throw new CrawlerException("Active User-Agent not found");
		}
	}
	
	/**
	 * Возвращает активный ProxyServer для использованиях в вызовах краулерах 
	 * @param type
	 * @return
	 * @throws CrawlerException 
	 */
	@SuppressWarnings("unchecked")
	public static ProxyServerModel getRandomProxyServer(ProxyServerTypeEnum type) throws CrawlerException {
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
	}
	
	
	/**
	 * Метод обновляет время последнего сканирования в БД, для последующей синхронизации
	 * @param model
	 * @param lastSourceScannedTime
	 * @throws CrawlerException 
	 */
	public static void updateLastSourceScannedTime(CrawlerModel model, Calendar lastSourceScannedTime) throws CrawlerException {
		if (model == null || lastSourceScannedTime == null)
			throw new CrawlerException("CrawlerModel or lastSourceScannedTime is null");
		
		JPA.em().createQuery("update CrawlerEntity set lastSourceSystemScannedTime = :time where id = :id")
		.setParameter("id", model.id)
		.setParameter("time", lastSourceScannedTime)
		.executeUpdate();
	}
	
}
