package kz.aphion.adverts.common;

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

public enum MQ {

	INSTANCE;
	
	private Logger logger = LoggerFactory.getLogger(MQ.class);
	
	private MQ(){
		logger.debug("MQ.INSTANCE object created.");
	}
	
	private static ActiveMQConnectionFactory _connectionFactory;
	
	private static Connection _connection;
	
	
	/**
	 * Ключ для того чтобы достать URL подключения к очереди из application.conf
	 */
	public static final String ACTIVE_MQ_URL_PROPERTIES = "mq.connection.url"; 
	
	/**
	 * Подключаемся к серверу очередей
	 * @throws JMSException 
	 * @throws CrawlerException 
	 */
	public void init() throws JMSException {
		if (_connectionFactory == null) {
			
			String connectionUrl = (String)System.getProperties().get(ACTIVE_MQ_URL_PROPERTIES);
			if (StringUtils.isEmpty(connectionUrl))
				throw new NullPointerException("ActiveMQ conneciton url [" + ACTIVE_MQ_URL_PROPERTIES + "] not found in application config");
			logger.info("ActiveMQ connection url: " + connectionUrl);
			
			// Create a ConnectionFactory, default url should be "tcp://localhost:61616"
			_connectionFactory = new ActiveMQConnectionFactory(connectionUrl);
            
            // Create a Connection
            _connection = _connectionFactory.createConnection();
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
        //text = "Hello world! From: " + Thread.currentThread().getName() + " : " + this.hashCode();
        TextMessage message = session.createTextMessage(text);

        // Tell the producer to send the message
        //System.out.println("Sent message: "+ message.hashCode() + " : " + Thread.currentThread().getName());
        producer.send(message);

        // Clean up
        session.close();
	}
	
}
