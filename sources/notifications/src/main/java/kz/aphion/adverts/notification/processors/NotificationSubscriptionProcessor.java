package kz.aphion.adverts.notification.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.notification.mq.RealtySubscriptionToNotificationProcessModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * Класс обработчик сообщений о недвижимости
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class NotificationSubscriptionProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(NotificationSubscriptionProcessor.class);
	
	/**
	 * Метод является входной точкой для обработки запроса
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		RealtySubscriptionToNotificationProcessModel model = RealtySubscriptionToNotificationProcessModel.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to RealtyAnalyserToSubscriptionProcessModel");
			return;
		}
		

	}

}
