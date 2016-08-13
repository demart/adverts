package kz.aphion.adverts.analyser.mq;

import java.util.Calendar;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

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
	 * Вид недвижимости
	 */
	public RealtyType type;
	
	/**
	 * Вид операции (Продажа, Аренда)
	 */
	public RealtyOperationType operation; 
	
	/**
	 * Статус объявления для обработки
	 */
	public RealtyProcessStatus status;
	
	/**
	 * Время отправки объявления в очередь
	 */
	public Calendar eventTime;
	
}
