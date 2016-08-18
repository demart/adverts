package kz.aphion.adverts.persistence.notification;

import java.util.Calendar;

/**
 * Класс описывает статус обработки сообщений для каналов
 * 
 * @author artem.demidovich
 *
 * Created at Aug 15, 2016
 */
public class NotificationChannelProgressStatus {

	/**
	 * Отметка статуса
	 */
	public NotificationProcessStatus status;
	
	/**
	 * Отметка времени
	 */
	public Calendar time;
	
	/**
	 * Система которая отправила отметку.
	 * Здесь не ENUM так как не понятно какие систему будут.
	 * Возможно в следующих верси
	 */
	public String system;
	
	/**
	 * Дополнительная информация (Возможно что-то техническое хз пока)
	 */
	public String additional;
	
}
