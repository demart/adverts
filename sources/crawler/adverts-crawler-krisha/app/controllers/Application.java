package controllers;

import kz.aphion.adverts.crawler.core.MongoDBProvider;
import kz.aphion.adverts.persistence.realty.RealtyLocation;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;

import play.Logger;
import play.mvc.Controller;

import com.mongodb.MongoClient;

public class Application extends Controller {

    public static void index() {
    	//mongoTest();
        render();
    }
    
    
    private static void mongoTest() {
    	MongoDBProvider.getInstance().getDatastore().createQuery(FlatSellRealty.class)
    	.getCollection().drop();
    	
    	MongoDBProvider.getInstance().getDatastore().save(generateObject(1f));
    	MongoDBProvider.getInstance().getDatastore().save(generateObject(10f));
    	MongoDBProvider.getInstance().getDatastore().save(generateObject(9f));
    	MongoDBProvider.getInstance().getDatastore().save(generateObject(100f));
    	MongoDBProvider.getInstance().getDatastore().save(generateObject(5f));
    	
    	Logger.info("Fetching more than 5f");
    	for (FlatSellRealty sell : MongoDBProvider.getInstance().getDatastore().createQuery(FlatSellRealty.class)
        		.field("data.ceilingHeight").greaterThanOrEq(5f)
        		.asList()) {
			Logger.info("Sell object id" + sell.id);
		} ;
    	
    	Logger.info("Fetching more than 1f");
    	for (FlatSellRealty sell : MongoDBProvider.getInstance().getDatastore().createQuery(FlatSellRealty.class)
    		.field("data.ceilingHeight").greaterThanOrEq(1f)
    		.asList()) {
    		Logger.info("Sell object id" + sell.id);
    	}
    	
    	Logger.info("Fetching more than 11f");
    	for (FlatSellRealty sell : MongoDBProvider.getInstance().getDatastore().createQuery(FlatSellRealty.class)
    		.field("data.ceilingHeight").greaterThanOrEq(11f)
    		.asList()) {
    		Logger.info("Sell object id" + sell.id);
    	}
    }
    
    private static FlatSellRealty generateObject(Float celiling) {
    	FlatSellRealty sell = new FlatSellRealty();
    	
    	sell.data = new FlatSellData();
    	sell.data.ceilingHeight = celiling;
    	sell.location = new RealtyLocation();
    	sell.location.houseNumber = "10";
    	
    	return sell;
    }
    

}