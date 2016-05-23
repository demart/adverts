package kz.aphion.adverts.phone.models;

import java.util.Calendar;

/**
 * Модель для передачи информации о телефоне для проверки на наличие определенного типа приложения установленного на мобильном устройстве.
 * 
 * @author artem.demidovich
 *
 */
public class PhoneApplicationCheckModel {

	/**
	 * Идентификатор записи телефона в БД
	 */
	public String objectId;
	
	/**
	 * Номер телефона (возможно будет быстрее работать)
	 */
	public String phone;
	
	/**
	 * Время когда было сгенерировано событиые
	 */
	public Calendar time;
	
}
