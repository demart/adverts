package kz.aphion.adverts.subscription.processors;

import javax.jms.JMSException;

import kz.aphion.adverts.subscription.mq.ImmeadiateNotificationEventModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс отвечает за обработку запросов поступающих из очередь для 
 * мнгновенных уведомлений
 * @author artem.demidovich
 *
 * Created at Aug 18, 2016
 */

public class ImmediateSubscriptionProcessor implements AdvertSubscriptionProcessor {

	private static Logger logger = LoggerFactory.getLogger(ImmediateSubscriptionProcessor.class);
	
	@Override
	public void processMessage(String message) throws JMSException, Exception {
		ImmeadiateNotificationEventModel model = ImmeadiateNotificationEventModel.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to ImmediateSubscriptionProcessor");
			return;
		}
		
		// Отпарвляем в процес подготовки уведомлений
			// для каждого канала подписки формируем сообщение
			// лучше брать из настроек чтобы не заморачиваться потом
		// Забываем о сообщении
		
		
	}

}
