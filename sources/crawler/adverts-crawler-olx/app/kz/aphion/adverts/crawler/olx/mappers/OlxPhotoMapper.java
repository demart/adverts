package kz.aphion.adverts.crawler.olx.mappers;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.olx.OlxJsonToMapParser;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import play.Logger;
import play.libs.WS;

/**
 * Класс разбирает описание фотографий на OLX и конвертирует их во внутренне описание
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class OlxPhotoMapper {
	
	private static final String configurationUrl = "https://ssl.olx.kz/i2/definitions/startuplight/?json=1";
	
	private static String riakPhotoPattern = "";
	private static String riakBucket = "";
	
	private static Map<String, List<String>> ringUrls = new LinkedHashMap<String, List<String>>();
	
	private static Boolean isImageConfigurationLoaded = false;
	
	/**
	 * Конвертирует JSON данные в корретный список фото
	 * @param rawPhotos
	 */
	public static List<RealtyPhoto> convertPhotos(Map<String, Object> rawPhotos) {
		List<RealtyPhoto> photos = new ArrayList<RealtyPhoto>();
		Long riakRing = null;
		Long riakKey = null;
		Long riakRev = null;
		
		riakRing =  Long.parseLong((String)rawPhotos.get("riak_ring"));
		riakKey = Long.parseLong((String)rawPhotos.get("riak_key"));
		riakRev = Long.parseLong((String)rawPhotos.get("riak_rev"));
		
		List<Map<String, Object>> rawPhotoItems = (List<Map<String, Object>>)rawPhotos.get("data");
		for (Map<String, Object> photoItem : rawPhotoItems) {
			RealtyPhoto photo = new RealtyPhoto();
			
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
			
			if (Logger.isDebugEnabled())
				Logger.debug("Advert has photo: %s", photo.path);
			//Logger.info("Advert has photo: %s", photo.path);
			
			photos.add(photo);
		}
		
		return photos;
	}
	
	
	private static String buildImageUrl(Long riakRing, Long riakKey, Long riakRev, Long slotId, Long width, Long height) {
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
	
	
	private synchronized static void retrieveConfigurationFromServer() {
		if (isImageConfigurationLoaded)
			return;
		
		String jsonResponse = WS.url(configurationUrl).get().getString();
		if (jsonResponse == null)
			throw new com.google.gson.JsonParseException("Response form OLX URL [" + configurationUrl + "] was null");
		
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
	
	
}
