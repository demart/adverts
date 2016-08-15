package kz.aphion.adverts.subscription.live.websocket;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

import kz.aphion.adverts.subscription.live.ConnectionManager;
import kz.aphion.adverts.subscription.live.SubscriptionConnection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервлет отвечающий за WebSocket соединения по Live подпискам на недвижимость
 * @author artem.demidovich
 *
 * Created at Aug 13, 2016
 */
@ServerEndpoint("/websocket/search/{type}/{subType}/{operation}")
public class SubscriptionWebSocketServlet {
	
	private static Logger logger = LoggerFactory.getLogger(SubscriptionWebSocketServlet.class);
	
	private static final Set<SubscriptionWebSocketServlet> connections = new CopyOnWriteArraySet<>();
	
	private SubscriptionConnection connection;
	
	private Session session;
	
	public SubscriptionWebSocketServlet() {
		logger.debug("Created new instance of RealtyWebSocketServlet");
	}
	
    @OnOpen
    public void start(
    		@PathParam("type") String type,
    		@PathParam("subType") String subType,
    		@PathParam("operation") String operation,
			Session session) throws Exception {
    	logger.info("Established new connection with Id {} with type {}, subType {} and opeartion {}. Active connections: {}", session.getId(), type, subType, operation, connections.size());
    	
    	// Сохраняем сессию
    	this.session = session;
    	
    	// Добавляем в коллецию для отслеживания
        connections.add(this);
    }

    @OnMessage
    public void incoming(
    		@PathParam("type") String type,
    		@PathParam("subType") String subType,
    		@PathParam("operation") String operation, 
    		String message, Session session) throws Exception {
    	
    	logger.info("Received new subscription query {} with type: {}, subType {} and operation {}", message, type, subType, operation);
       
    	// Здесь могут быть две ситуации
    	// 1. Когда мы получили критерии поиска и это было первый раз
    	// 2. Когда мы получили критерии поиска повторно, человек поменял условия например
    	
    	
    	if (this.connection != null) {
    		
    		// Если уже есть активная подписка
    		// То удаляем её
    		ConnectionManager.removeConnection(connection);
    		
    		// И cоздаем новую подписку
    		connection = ConnectionManager.createConnection(session, type, subType, operation, message);
    		
    	} else {
    		
    		// Если подписки нету
        	connection = ConnectionManager.createConnection(session, type, subType, operation, message);
        	
    	}
    }
    
    @OnClose
    public void end() {
    	ConnectionManager.removeConnection(connection);
        connections.remove(this);
        logger.info("Closed connection with id {}...", session.getId());
    }

    @OnError
    public void onError(Throwable t) throws Throwable {
    	ConnectionManager.removeConnection(connection);
    	connections.remove(this);
        logger.warn("Connection closed with id " + session.getId() + " because of error: " + t.toString(), t);
    }
	
}
