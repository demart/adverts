package kz.aphion.adverts.crawler.olx.mappers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.crawler.olx.OlxJsonToMapParser;

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

	private static final String phoneUrl = "https://olx.kz/i2/ajax/ad/getcontact/?type=phone&json=1&id=";
	
	
	/**
	 * Метод выполняет запрос к API серверу OLX и извлекает телефоны добавляя их к объявлению
	 * 
	 * @param externalAdvertId
	 */
	public static List<String> callServerAndGetPhone(String externalAdvertId) {
		//String url = phoneUrl + externalAdvertId;
		//String response = WS.url(url).get().getString();
		String response = getPhoneJsonResponse(externalAdvertId);
		
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
	
	private static String getPhoneJsonResponse(String externalAdvertId) {
		String desiredUrl = phoneUrl + externalAdvertId;
		
		logger.debug("Calling API to get phone number: " + desiredUrl);
		
	    URL url = null;
	    BufferedReader reader = null;
	    StringBuilder stringBuilder;

	    try {
	      // create the HttpURLConnection
	      url = new URL(desiredUrl);
	      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
	      
	      // just want to do an HTTP GET here
	      connection.setRequestMethod("GET");

	      // give it 15 seconds to respond
	      connection.setReadTimeout(15*1000);
	      connection.connect();

	      // read the output from the server
	      reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
	      stringBuilder = new StringBuilder();

	      String line = null;
	      while ((line = reader.readLine()) != null) {
	        stringBuilder.append(line + "\n");
	      }
	      return stringBuilder.toString();
	    } catch (Exception ex) {
	    	logger.error("Error trying to get JSON of advert to extract phone number from OLX server", ex);
	    	return null;
	    } finally {
	      // close the reader; this can throw an exception too, so
	      // wrap it in another try/catch block.
	      if (reader != null) {
	        try {
	          reader.close();
	        } catch (IOException ioe) {
	          ioe.printStackTrace();
	        }
	      }
	    }
		
	}
	
	
	public static String formatPhoneNumber(String phone) {
		phone = phone.replaceAll("[^\\d]", "");
		if (phone.length() > 10)
			phone = phone.substring(phone.length()-10, phone.length());
		return phone;
	}
	
}
