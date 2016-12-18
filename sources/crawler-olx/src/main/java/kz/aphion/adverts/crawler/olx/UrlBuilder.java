package kz.aphion.adverts.crawler.olx;

import kz.aphion.adverts.crawler.core.CrawlerHttpClient;
import kz.aphion.adverts.crawler.core.DataManager;
import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.models.CrawlerModel;
import kz.aphion.adverts.crawler.core.models.ProxyServerModel;
import kz.aphion.adverts.crawler.core.models.UserAgentModel;
import kz.aphion.adverts.persistence.crawler.ProxyServerTypeEnum;
import kz.aphion.adverts.persistence.crawler.UserAgentTypeEnum;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UrlBuilder {

	private static Logger logger = LoggerFactory.getLogger(UrlBuilder.class);

	private static UrlBuilder _instance;
	
	private boolean isInitizalized = false;
	
	private CrawlerModel crawlerModel;
	
	private QueryBuilder queryBuilder;
	
	private UserAgentTypeEnum userAgent = UserAgentTypeEnum.MOBILE_PLATFORM;
	private ProxyServerTypeEnum proxyServer = ProxyServerTypeEnum.HTTPS;
	
	private UrlBuilder() {}
	
	public static UrlBuilder getInstance() {
		if (_instance == null) {
			_instance = new UrlBuilder();
			_instance.isInitizalized = false;
		}
		return _instance;
	}
	
	public void init(CrawlerModel crawlerModel) throws CrawlerException {
		this.crawlerModel = crawlerModel;
		
		this.queryBuilder = new QueryBuilder();
		this.queryBuilder.prepareFilters(crawlerModel);
		
		this.isInitizalized = true;
	}
	
	public QueryBuilder getQueryBuilder() throws CrawlerException {
		if (!isInitizalized)
			throw new CrawlerException("UrlBuilder wasn't intialized.");
		return this.queryBuilder;
	}
	
	/**
	 * Метод выполняет вызов сервера источника для получения JSON ответа.
	 * Также метод выполняет проврерку на необхдоимость использования прокси сервера или разных заголовков
	 * @param targetUrl
	 * @return
	 * @throws CrawlerException
	 */
	public String callServerAndGetJsonData(String targetUrl) throws CrawlerException {
		if (!isInitizalized)
			throw new CrawlerException("UrlBuilder wasn't intialized.");
		
		try {
		
			// TODO: Увеличить счетчики использования User-Agent и Proxy Servers
			UserAgentModel uam = null;
			if (crawlerModel.crawlerGroup.useUserAgents) {
				uam = DataManager.getRandomUserAgent(userAgent);
				
				if (logger.isDebugEnabled())
					logger.debug("User-Agent: " + uam.userAgent + " with name [" + uam.name + "]");
			} 
			
			ProxyServerModel psm = null;
			if (crawlerModel.crawlerGroup.useProxyServers) {
				psm = DataManager.getRandomProxyServer(proxyServer);
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
		
		} catch (Exception ex) {
			throw new CrawlerException(ex);
		}
	}
	
}
