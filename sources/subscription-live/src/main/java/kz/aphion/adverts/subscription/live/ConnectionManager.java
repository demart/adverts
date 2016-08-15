package kz.aphion.adverts.subscription.live;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.Session;

import kz.aphion.adverts.persistence.BaseEntity;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * Класс отвечает за работу с Live подписками
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class ConnectionManager {
	
	private static Logger logger = LoggerFactory.getLogger(ConnectionManager.class);
	
	private static final Set<SubscriptionConnection> connections = new CopyOnWriteArraySet<>();

	public static SubscriptionConnection createConnection(Session session, String type, String subType, String operation, String query) throws Exception {
		SubscriptionConnection connection = SubscriptionConnectionFactory.createConnection(type, subType, operation);
    	
		connection.session = session;
    	connection.initWithQuery(query);
        
		connections.add(connection);
		
		return connection;
	}
	
	public static void removeConnection(SubscriptionConnection connection) {
		connections.remove(connection);
	}
	
	public static void notifyClient(BaseEntity adverts) {
		for (SubscriptionConnection subscriptionConnection : connections) {
			try {
				subscriptionConnection.notify(adverts);
			} catch (Exception ex) {
				logger.error("Error occurs in subscription", ex);
			}
		}
	}
	
}
