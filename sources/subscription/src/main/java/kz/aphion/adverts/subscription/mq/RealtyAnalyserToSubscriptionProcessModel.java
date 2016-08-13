package kz.aphion.adverts.subscription.mq;

import java.util.Calendar;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

/**
 * Модель используется для передачи сообщения из Analyser в модуль подписок
 * @author artem.demidovich
 *
 * Created at Aug 10, 2016
 */
public class RealtyAnalyserToSubscriptionProcessModel {
	
	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Идентификатор обновленного объявления
	 */
	public String oldAdvertId;

	/**
	 * Вид недвижимости
	 */
	public RealtyType type;
	
	/**
	 * Вид операции (Продажа, Аренда)
	 */
	public RealtyOperationType operation;
	
	/**
	 * Результат обработки. Лучше Хуже или новое
	 */
	public AnalyserProcessStatus status;
	
	/**
	 * Время отправки объявления в очередь
	 */
	public Calendar eventTime;
}
