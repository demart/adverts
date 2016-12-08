package kz.aphion.adverts.crawler.core;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.MongoClient;


/**
 * Класс инициализации MongoDB соединения
 * @author artem.demidovich
 *
 */
public class MongoDBProvider {

	private static Logger logger = LoggerFactory.getLogger(MongoDBProvider.class);
		
	private MongoDBProvider(){}
	
	private static MongoDBProvider _instance;
	
	private static Morphia _morphia;
	private static Datastore _datastore;
	
	public static MongoDBProvider getInstance() throws Exception { 
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
		
	
	
	public void init() throws Exception {
		_morphia = new Morphia();
		
		_morphia.mapPackage("kz.aphion.adverts");

		String dbName = getApplicationConfigParameter(MONGO_DBNAME_PROPERTY);
		String host = getApplicationConfigParameter(MONGO_HOST_PROPERTY);
		String portStr = getApplicationConfigParameter(MONGO_PORT_PROPERTY);
		Integer port = Integer.parseInt(portStr);
		
		// create the Datastore connecting to the default port on the local host
		_datastore = _morphia.createDatastore(new MongoClient(host,port), dbName);
		_datastore.ensureIndexes();

	}
	
	private String getApplicationConfigParameter(String paramName) throws Exception {
		String paramValue = (String)System.getProperties().get(paramName);
		if (StringUtils.isEmpty(paramValue))
			throw new Exception("MongoDb connection parameter [" + paramName + "] not found in application config");
		logger.info("MongoDb connection parameter ["+ paramName +"]: " + paramValue);
		return paramValue;
	}
	
	public Datastore getDatastore() {
		return _datastore;
	}
	
}
