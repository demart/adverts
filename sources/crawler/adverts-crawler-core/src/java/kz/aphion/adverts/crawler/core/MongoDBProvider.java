package kz.aphion.adverts.crawler.core;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;


/**
 * Класс инициализации MongoDB соединения
 * @author artem.demidovich
 *
 */
public class MongoDBProvider {
	

	private MongoDBProvider(){}
	
	private static MongoDBProvider _instance;
	
	private static Morphia _morphia;
	private static Datastore _datastore;
	
	public static MongoDBProvider getInstance() { 
		if (_instance == null) {
			_instance = new MongoDBProvider();
			try {
			_instance.init();
			} catch (Exception e) {
				_instance = null;
				throw e;
			}
		}
		return _instance;
	}
	
	public void init() {
		_morphia = new Morphia();

		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		_morphia.mapPackage("kz.aphion.adverts");

		// create the Datastore connecting to the default port on the local host
		_datastore = _morphia.createDatastore(new MongoClient(), "adverts");
		_datastore.ensureIndexes();

	}
	
	
	public Datastore getDatastore() {
		return _datastore;
	}
	
}
