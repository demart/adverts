package kz.aphion.adverts.subscription.live.providers;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;

/**
 * Класс инициализирующий подключение к MongoDB
 * 
 * @author artem.demidovich
 *
 */
public class MongoDbProvider {

	private static Logger logger = LoggerFactory.getLogger(MongoDbProvider.class);
	
	private MongoDbProvider(){}
	
	private static MongoDbProvider _instance;
	
	private static Morphia _morphia;
	private static Datastore _datastore;
	
	
	/**
	 * Ключ для того чтобы достать хост для подключения к монго из application.conf
	 */
	public static final String MONGO_DBNAME_PROPERTY = "mongodb.dbname"; 
	
	/**
	 * Ключ для того чтобы достать хост для подключения к монго из application.conf
	 */
	public static final String MONGO_HOST_PROPERTY = "mongodb.connection.host"; 

	/**
	 * Ключ для того чтобы достать порт для подключения к монго из application.conf
	 */
	public static final String MONGO_PORT_PROPERTY = "mongodb.connection.port"; 
			
	
	public static MongoDbProvider getInstance() throws Exception { 
		if (_instance == null) {
			_instance = new MongoDbProvider();
			try {
			_instance.init();
			} catch (Exception e) {
				_instance = null;
				throw e;
			}
		}
		return _instance;
	}
	
	public void init() throws Exception {
		_morphia = new Morphia();

		// tell Morphia where to find your classes
		// can be called multiple times with different packages or classes
		_morphia.mapPackage("kz.aphion.adverts");

		String dbName = "adverts";//getApplicationConfigParameter(MONGO_DBNAME_PROPERTY);
		String host = "localhost"; //getApplicationConfigParameter(MONGO_HOST_PROPERTY);
		String portStr = "27017"; //getApplicationConfigParameter(MONGO_PORT_PROPERTY);
		Integer port = Integer.parseInt(portStr);
		
		// create the Datastore connecting to the default port on the local host
		_datastore = _morphia.createDatastore(new MongoClient(host,port), dbName);
		_datastore.ensureIndexes();

	}
	
	public Datastore getDatastore() {
		return _datastore;
	}
	
}
