package kz.aphion.adverts.common.models.mq.realties;

import java.util.Calendar;

/**
 * Модель для передачи сообщения о необходимости обработки объявления о недвижимости
 * с crawler в analyze систему
 * 
 * @author artem.demidovich
 *
 */
public class ProcessRealtyModel {

	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Идентификатор обновленного объявления
	 */
	public String oldAdvertId;
	
	/**
	 * Статус объявления для обработки
	 */
	public RealtyProcessStatus status;
	
	/**
	 * Время отправки объявления в очередь
	 */
	public Calendar eventTime;
	
}
