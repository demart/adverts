package kz.aphion.adverts.crawler.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;

import org.apache.commons.lang.StringUtils;

import play.Logger;

/**
 * Класс клиент для обращения к API источника.
 * Поддерживается работа с User-Agent и прокси серверами.
 * 
 * @author artem.demidovich
 *
 */
public class CrawlerHttpClient {
	
	/**
	 * Метод выполняет вызов вшеншей системы без использования прокси и хэдеров
	 * @param targetUrl
	 * @return
	 * @throws IOException
	 */
	public static String getContent(String targetUrl) throws IOException {
		return getContent(targetUrl, null, 0, null);
	}
	

	/**
	 * Метод выполняет вызов вшеншей системы c хэдером
	 * @param targetUrl
	 * @param userAgent
	 * @return
	 * @throws IOException
	 */
	public static String getContent(String targetUrl, String userAgent) throws IOException {
		return getContent(targetUrl, null, 0, userAgent);
	}
	
	/**
	 * Метод выполняет вызов вшеншей системы через прокси сервер
	 * @param targetUrl
	 * @param userAgent
	 * @return
	 * @throws IOException
	 */
	public static String getContent(String targetUrl, String proxyHost, Integer proxyPort) throws IOException {
		return getContent(targetUrl, proxyHost, proxyPort, null);
	}	
	
	
	/**
	 * Метод выполняет вызов внешней системы. 
	 * Умеет использовать прокси сервер и заданный User-Agent в заголовке запроса.
	 * 
	 * @param targetUrl Сформированный URL для обращения к API системы источника
	 * @param proxyHost Прокси хост
	 * @param proxyPort Прокси порт
	 * @param userAgent User-Agent запроса
	 * @return HTTP ответ, строка
	 * @throws IOException 
	 */
	public static String getContent(String targetUrl, String proxyHost, Integer proxyPort, String userAgent) throws IOException {
		URL url = new URL(targetUrl);
		HttpURLConnection uc = null;
		
		// Inject proxy server if needed
		if (StringUtils.isBlank(proxyHost) || proxyPort < 1) {
			// Without proxy
			uc = (HttpURLConnection)url.openConnection();
		} else {
			// With proxy			
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort)); 
			uc = (HttpURLConnection)url.openConnection(proxy);
		}
		
		// Set User-Agent header
		if (!StringUtils.isBlank(userAgent)) {
			uc.setRequestProperty("User-Agent", userAgent);
		}
		
		uc.setRequestMethod("GET");
		uc.connect();
		
	    StringBuffer tmp = new StringBuffer();
	    BufferedReader in = new BufferedReader(new InputStreamReader(uc.getInputStream()));
	    String line = null;
	    while ((line = in.readLine()) != null) {
	      tmp.append(line);
	    }
	    
	    if (Logger.isDebugEnabled()) {
	    	Logger.info("HTTP response:");
	    	Logger.info(tmp.toString());
	    }
	    return tmp.toString();
	}
	
}
