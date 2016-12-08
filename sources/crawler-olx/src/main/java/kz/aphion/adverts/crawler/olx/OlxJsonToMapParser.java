package kz.aphion.adverts.crawler.olx;

import java.lang.reflect.Type;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import kz.aphion.adverts.crawler.olx.MapDeserializerDoubleAsIntFix;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class OlxJsonToMapParser {

	private static Logger logger = LoggerFactory.getLogger(OlxJsonToMapParser.class);

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
		
		logger.trace("jsonContent converted into following stucture\n{}", gson.toJson(jsonMap));

		return jsonMap;
	}
	
	
}
