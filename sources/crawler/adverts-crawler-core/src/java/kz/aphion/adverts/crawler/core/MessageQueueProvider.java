package kz.aphion.adverts.crawler.core;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.core.exceptions.CrawlersNotFoundException;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.lang.StringUtils;

import play.Logger;
import play.Play;

/**
 * Провайдер предоставляет возможность работы с сервером очередей
 * 
 * @author artem.demidovich
 *
 */
public class MessageQueueProvider {

	private MessageQueueProvider(){}
	
	private static MessageQueueProvider _instance;
	
	private static ActiveMQConnectionFactory _connectionFactory;
	
	private static Connection _connection;
	
	public static MessageQueueProvider getInstance() throws JMSException, CrawlerException{ 
		if (_instance == null) {
			_instance = new MessageQueueProvider();
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
	 * @throws JMSException 
	 * @throws CrawlerException 
	 */
	private void init() throws JMSException, CrawlerException {
		if (_connectionFactory == null) {
			
			String connectionUrl = (String)Play.configuration.get(ACTIVE_MQ_URL_PROPERTIES);
			if (StringUtils.isEmpty(connectionUrl))
				throw new CrawlersNotFoundException("ActiveMQ conneciton url [" + ACTIVE_MQ_URL_PROPERTIES + "] not found in application config");
			Logger.info("ActiveMQ connection url: " + connectionUrl);
			
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
