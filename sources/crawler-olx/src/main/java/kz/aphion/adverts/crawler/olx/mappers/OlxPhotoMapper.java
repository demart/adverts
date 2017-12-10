package kz.aphion.adverts.crawler.olx.mappers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.olx.OlxJsonToMapParser;
import kz.aphion.adverts.crawler.olx.UrlBuilder;
import kz.aphion.adverts.persistence.adverts.AdvertPhoto;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс разбирает описание фотографий на OLX и конвертирует их во внутренне описание
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxPhotoMapper {

	private static Logger logger = LoggerFactory.getLogger(OlxPhotoMapper.class);

	//private static final String configurationUrl = "https://ssl.olx.kz/i2/definitions/startuplight/?json=1";
	
	private static String riakPhotoPattern = "";
	private static String riakBucket = "";
	
	private static Map<String, List<String>> ringUrls = new LinkedHashMap<String, List<String>>();
	
	private static Boolean isImageConfigurationLoaded = false;
	
	/**
	 * Конвертирует JSON данные в корретный список фото
	 * @param rawPhotos
	 * @throws CrawlerException 
	 */
	public static List<AdvertPhoto> convertPhotos(Map<String, Object> rawPhotos) throws CrawlerException {
		List<AdvertPhoto> photos = new ArrayList<AdvertPhoto>();
		Long riakRing = null;
		Long riakKey = null;
		Long riakRev = null;
		
		riakRing =  Long.parseLong((String)rawPhotos.get("riak_ring"));
		riakKey = Long.parseLong((String)rawPhotos.get("riak_key"));
		riakRev = Long.parseLong((String)rawPhotos.get("riak_rev"));
		
		List<Map<String, Object>> rawPhotoItems = (List<Map<String, Object>>)rawPhotos.get("data");
		for (Map<String, Object> photoItem : rawPhotoItems) {
			AdvertPhoto photo = new AdvertPhoto();
			
			Long slotId = null;
			
			for (Entry<String, Object> map : photoItem.entrySet()) {
				switch (map.getKey()) {
				case "slot_id":
					slotId = Long.parseLong((String)map.getValue());
					break;
				case "w":
					photo.width = Long.parseLong((String)map.getValue());
					break;
					
				case "h":
					photo.height = Long.parseLong((String)map.getValue());
					break;
				
				default:
					break;
				}
			}
			
			// Build URL to photo
			photo.path = buildImageUrl(riakRing, riakKey, riakRev, slotId, photo.width, photo.height);
			
			if (logger.isDebugEnabled())
				logger.debug("Advert has photo: {}", photo.path);
			//Logger.info("Advert has photo: %s", photo.path);
			
			photos.add(photo);
		}
		
		return photos;
	}
	
	
	private static String buildImageUrl(Long riakRing, Long riakKey, Long riakRev, Long slotId, Long width, Long height) throws CrawlerException {
		if (isImageConfigurationLoaded == false) {
			retrieveConfigurationFromServer();
		}
		
		String riakRingUrl = getRiakRingUrl(riakRing);
		String riakBucket = getRiakBucket();
		
		String imageUrl = riakPhotoPattern
				.replace("{riak_ring_url}", riakRingUrl)
				.replace("{riak_bucket}", riakBucket)
				.replace("{riak_key}", riakKey.toString())
				.replace("{slot_id}", slotId.toString())
				.replace("{width}", width.toString())
				.replace("{height}", height.toString())
				.replace("{riak_rev}", riakRev.toString());
		
		return imageUrl;
	}
	
	private static String getRiakRingUrl(Long riakRing) {
		return ringUrls.get(riakRing.toString()).get(0);
	}
	
	private static String getRiakBucket() {
		return riakBucket;
	}
	
	
	private synchronized static void retrieveConfigurationFromServer() throws CrawlerException {
		if (isImageConfigurationLoaded)
			return;
		
		//String jsonResponse = WS.url(configurationUrl).get().getString();
		String jsonResponse = getConfigurationJsonResponse();
		
		// Конвертируем полученные ответ с сервера в JSON Map
		Map<String, Object> jsonResponseMap = OlxJsonToMapParser.convertJson(jsonResponse);
		// Получаем конфигурацию изображений
		Map<String, Object> imagesConfig = (Map<String, Object>)jsonResponseMap.get("images_config");
		
		// Конфигурации серверов с изображениями
		Map<String, Object> riakRingUrls = (Map<String, Object>)imagesConfig.get("riak_ring_urls");
		for (Entry<String, Object> ringUrlEntry : riakRingUrls.entrySet()) {
			ringUrls.put(ringUrlEntry.getKey(), (List<String>)ringUrlEntry.getValue());
		}
		
		// Конфигурации корзины в которой лежат фотки
		Map<String, Object> riakBuckets = (Map<String, Object>)imagesConfig.get("riak_buckets");		
		riakBucket = (String)riakBuckets.get("ad");
		
		// Конфигурации серверов с изображениями
		riakPhotoPattern =  (String)imagesConfig.get("riak_photo_pattern");
		
		isImageConfigurationLoaded = true;
	}
	
	private static String getConfigurationJsonResponse() throws CrawlerException {
		String imageConfigUrl = UrlBuilder.getInstance().getQueryBuilder().buildImageConfigUrl();
		String jsonResponse = UrlBuilder.getInstance().callServerAndGetJsonData(imageConfigUrl);
		if (jsonResponse == null)
			throw new com.google.gson.JsonParseException("Response form OLX URL [" + imageConfigUrl + "] was null");
		return jsonResponse;
		/*
		try {
			URL obj = new URL(configurationUrl);
			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			con.setRequestMethod("GET");
			con.setRequestProperty("User-Agent", "Mozilla/5.0 (Linux; Android 4.0.4; Galaxy Nexus Build/IMM76B) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.133 Mobile Safari/535.19");
			
			con.connect();
			
			BufferedReader in = new BufferedReader(
			        new InputStreamReader(con.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
	
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			
			return response.toString();
		} catch (Exception ex) {
			logger.error("Error trying to get JSON configuration from OLX server", ex);
			return null;
		}
		*/
	}
	
}
