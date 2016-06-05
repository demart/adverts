package kz.aphion.adverts.crawler.irr;

import java.lang.reflect.Type;
import java.util.Map;

import play.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Конвертор JSON строки в нормальлизованный Map<String, Object>
 * 
 * @author artem.demidovich
 *
 */
public class IrrJsonToMapParser {

	private static Gson gson;
	
	private static Type type = new TypeToken<Map<String, Object>>(){}.getType();
	
	private static MapDeserializerDoubleAsIntFix gsonDeserializer = new MapDeserializerDoubleAsIntFix();
	
	private static synchronized void prepareGson() {
		if (gson == null) {
			GsonBuilder builder = new GsonBuilder();
			builder.registerTypeAdapter(type,  gsonDeserializer);
			gson = builder.setPrettyPrinting().create();
		}
	}
	
	/**
	 * Конвертирует JSON строку в Map<String, Object>
	 * @param jsonContent
	 * @return
	 */
	public static Map<String, Object> convertJson(String jsonContent) {
		if (gson == null)
			prepareGson();
		
		Map<String, Object> jsonMap = gson.fromJson(jsonContent, type);
		
		if (Logger.isTraceEnabled()) {
			Logger.trace("jsonContent converted into following stucture");
			Logger.trace(gson.toJson(jsonMap));
		}
		return jsonMap;
	}
	
	
}
