package kz.aphion.adverts.subscription.mq;

import java.util.Calendar;

/**
 * Модель используется для передачи сообщения из Analyser в модуль подписок
 * @author artem.demidovich
 *
 * Created at Aug 10, 2016
 */
public class AdvertAnalyserToSubscriptionProcessModel {
	
	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Идентификатор обновленного объявления
	 */
	public String oldAdvertId;

	/**
	 * Результат обработки. Лучше Хуже или новое
	 */
	public AnalyserProcessStatus status;
	
	/**
	 * Время отправки объявления в очередь
	 */
	public Calendar eventTime;
}
