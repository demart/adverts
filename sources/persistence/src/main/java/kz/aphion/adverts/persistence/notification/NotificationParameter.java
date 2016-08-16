package kz.aphion.adverts.persistence.notification;

import org.mongodb.morphia.annotations.Property;

/**
 * Параметры используемые в системе уведомлений. 
 * Основные кейсы:
 * 	Информация о системах источниках, для последующей отправки callback с необходимыми параметрам
 *  Информация для систем транспорта с передачей необходимых параметров 
 *  (например какой скрит открыть в мобильном приложении при получении уведомления)
 * 
 * @author artem.demidovich
 *
 * Created at Aug 15, 2016
 */
public class NotificationParameter {

	@Property
	public String key;
	
	@Property
	public String value;
	
}
