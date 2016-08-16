package kz.aphion.adverts.persistence.notification;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Параметры для отправки callback уведомлений в систему источника события
 * 
 * @author artem.demidovich
 *
 * Created at Aug 15, 2016
 */
public class NotificationCallback {

	/**
	 * Необходимо ли отправлять callback сообщения в систему источника события
	 */
	@Property
	public Boolean needCallback;
	
	/**
	 * Название очереди в которую необходимо отправлять callback событие
	 */
	@Property
	public String callbackQueue;
	
	/**
	 * Список параметров для отправки callback в систему источника события
	 */
	@Embedded
	public List<NotificationParameter> parameters;
}
