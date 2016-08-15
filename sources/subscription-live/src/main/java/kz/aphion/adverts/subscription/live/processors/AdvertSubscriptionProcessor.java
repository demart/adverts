package kz.aphion.adverts.subscription.live.processors;

import javax.jms.JMSException;

/**
 * Интерфейс для процессоров. 
 * Создан потому что нужно как-то унифицировать обработку с разными реализациями
 * @author artem.demidovich
 *
 * Created at Aug 10, 2016
 */
public interface AdvertSubscriptionProcessor {

	/**
	 * Метод принимает модель строкой для соотвествующего типа объявления
	 * 
	 * @param message
	 */
	public void processMessage(String message) throws JMSException, Exception;
}
