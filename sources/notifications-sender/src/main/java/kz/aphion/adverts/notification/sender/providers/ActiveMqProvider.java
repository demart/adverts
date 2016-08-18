package kz.aphion.adverts.notification.sender.providers;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Провайдер инициализации и предоставления доступа к серверу очередей
 * @author artem.demidovich
 *
 */
public class ActiveMqProvider {

	private static Logger logger = LoggerFactory.getLogger(ActiveMqProvider.class);
	

	private ActiveMqProvider(){}
	
	private static ActiveMqProvider _instance;
	
	private static ActiveMQConnectionFactory _connectionFactory;
	
	private static Connection _connection;
	
	public static ActiveMqProvider getInstance() throws JMSException, Exception { 
		if (_instance == null) {
			_instance = new ActiveMqProvider();
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
	 * Ключ для того чтобы достать URL подключения к очереди из application.conf
	 */
	public static final String ACTIVE_MQ_URL_PROPERTIES = "mq.connection.url"; 
	
	/**
	 * Подключаемся к серверу очередей
	 * @throws Exception 
	 * @throws CrawlerException 
	 */
	private void init() throws Exception {
		if (_connectionFactory == null) {
			
			String connectionUrl = "tcp://localhost:61616";  //(String)Play.configuration.get(ACTIVE_MQ_URL_PROPERTIES);
			if (StringUtils.isEmpty(connectionUrl))
				throw new Exception("ActiveMQ conneciton url [" + ACTIVE_MQ_URL_PROPERTIES + "] not found in application config");
			logger.info("ActiveMQ connection url: " + connectionUrl);
			
			// Create a ConnectionFactory, default url should be "tcp://localhost:61616"
			_connectionFactory = new ActiveMQConnectionFactory(connectionUrl);
            
            // Create a Connection
            _connection = _connectionFactory.createConnection();
            _connection.start();
		}
	}

	
	public Connection getConnection() {
		return _connection;
	}
	
	public Session getSession() throws JMSException {
		// Create a Session
        Session session = getConnection().createSession(false, Session.AUTO_ACKNOWLEDGE);
        return session;
	}
	
	/**
	 * Метод отправляет текстовое сообщение в указанную очередь.
	 * 
	 * @param queueName Название очереди
	 * @param text Текстовое сообщение
	 * @throws JMSException Исключение в ходе отправки сообщения
	 */
	public void sendTextMessageToQueue(String queueName, String text) throws JMSException {
        Session session = getSession();
		// Create the destination (Topic or Queue)
        Destination destination = session.createQueue(queueName);

        // Create a MessageProducer from the Session to the Topic or Queue
        MessageProducer producer = session.createProducer(destination);
        producer.setDeliveryMode(DeliveryMode.PERSISTENT);

        // Create a messages
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        producer.send(message);

        // Clean up
        session.close();
	}
	
	
	
}
