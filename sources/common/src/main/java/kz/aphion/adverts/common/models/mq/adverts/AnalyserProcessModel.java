package kz.aphion.adverts.common.models.mq.adverts;

import java.util.Calendar;

/**
 * Модель передачи данных из модуля анализатора в модуль подписок
 * @author artem.demidovich
 *
 * Created at Dec 12, 2017
 */
public class AnalyserProcessModel {
	
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
