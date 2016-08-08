package controllers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.phone.providers.MongoDbProvider;

import org.mongodb.morphia.Datastore;

import play.Logger;
import play.mvc.Controller;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Application extends Controller {

	public static LinkedHashMap<String , Integer> words = new LinkedHashMap<String, Integer>();;
	
    public static void index() throws Exception {
    	Datastore ds = MongoDbProvider.getInstance().getDatastore();
    	DBCursor cursor = ds.getCollection(FlatSellRealty.class).find();
    	for (DBObject dbObject : cursor) {
    		String text = (String)((DBObject)dbObject.get("data")).get("text");
    		
    		if (text == null)
    			continue;
    		
    		//String[] splittedText = text.split("\\s+");
    		String[] splittedText = text.split("[[ ]*|[,]*|[\\.]*|[:]*|[/]*|[!]*|[?]*|[+]*]+");
    		
    		if (splittedText != null) {
	     		for (String word : splittedText) {
					word = word.toLowerCase();
					word = word.replace("\"", "");
					word = word.replace("(", "");
					word = word.replace(")", "");
					word = word.replace(")", "");
					word = word.trim();
					if (words.containsKey(word)) {
						//Logger.info("Found non unique word [%s]", word);
						words.put(word, words.get(word) + 1);
					} else {
						words.put(word, 1);
					}
				}
    		}
    	
    		//Logger.info(text);
		}
    	
    	
    	List<Map.Entry<String, Integer>> entries = new ArrayList<Map.Entry<String, Integer>>(words.entrySet());
		Collections.sort(entries, new Comparator<Map.Entry<String, Integer>>() {
		  public int compare(Map.Entry<String, Integer> a, Map.Entry<String, Integer> b){
		    return a.getValue().compareTo(b.getValue());
		  }
		});
		Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();
		for (Map.Entry<String, Integer> entry : entries) {
		  sortedMap.put(entry.getKey(), entry.getValue());
		}
    	
		Logger.info("");Logger.info("");Logger.info("");Logger.info("");
    	Logger.info("============================");
    	Logger.info("======== = Stats = =========");
    	Logger.info("============================");
    	for (Entry<String, Integer> wordEntry : sortedMap.entrySet()) {
    		if (wordEntry.getValue() > 1)
    			Logger.info("Words: [%s] found [%d]", wordEntry.getKey(), wordEntry.getValue());
		}
    	
    	
        render();
    }

}