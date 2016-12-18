package kz.aphion.adverts.crawler.olx.mappers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.olx.OlxJsonToMapParser;
import kz.aphion.adverts.crawler.olx.UrlBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonParseException;

/**
 * Класс извлечения телефонных номеров с сайта OLX. Требует обращения к API OLX
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxPhoneMapper {

	private static Logger logger = LoggerFactory.getLogger(OlxPhoneMapper.class);

	//private static final String phoneUrl = "https://olx.kz/i2/ajax/ad/getcontact/?type=phone&json=1&id=";
	
	
	/**
	 * Метод выполняет запрос к API серверу OLX и извлекает телефоны добавляя их к объявлению
	 * 
	 * @param externalAdvertId
	 * @throws Exception 
	 * @throws IOException 
	 */
	public static List<String> callServerAndGetPhone(String externalAdvertId) throws CrawlerException {
		String phoneUrl = UrlBuilder.getInstance().getQueryBuilder().buildPhoneQueryUrl(externalAdvertId);
		String response = UrlBuilder.getInstance().callServerAndGetJsonData(phoneUrl);
		
		logger.debug("Got JSON response with phone number: " + response + " advertId: " + externalAdvertId);
		
		Map<String, Object> json = OlxJsonToMapParser.convertJson(response);
		
		if (!json.containsKey("urls"))
			throw new JsonParseException("No urls element in get phones response");
		
		Map<String, Object> urls = (Map<String, Object>)json.get("urls");
		
		if (!urls.containsKey("phone"))
			throw new JsonParseException("No urls.phone element in get phones response");
		
		List<String> phoneList = new ArrayList<String>();
		List<Map<String, Object>> phones = (List<Map<String, Object>>)urls.get("phone");
		for (Map<String, Object> map : phones) {
			
			String phoneItem = map.get("uri").toString();
			phoneItem = formatPhoneNumber(phoneItem);
			phoneList.add(phoneItem);
			
			if (logger.isDebugEnabled())
				logger.debug("Advert [{}] phone number [{}]", externalAdvertId, phoneItem);
			//Logger.info("Advert [%s] phone number [%s]", externalAdvertId, phoneItem);
		}
		
		return phoneList;
	}
	
	public static String formatPhoneNumber(String phone) {
		phone = phone.replaceAll("[^\\d]", "");
		if (phone.length() > 10)
			phone = phone.substring(phone.length()-10, phone.length());
		return phone;
	}
	
}
