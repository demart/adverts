package kz.aphion.adverts.persistence.users;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Устройства или каналы через которые работает пользователь.
 * Описывает различные варианты такие как мобильные приложения, браузеры, 
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */

@Entity("adverts.users.devices")
@Converters(CalendarConverter.class)
public class UserDevice extends BaseEntity {
	
	/**
	 * Устройство может быть связано с пользователем, а может и не связано.
	 * Так как есть анонимные пользователи по котором нет никакой информации кроме
	 * абстрактного девайса
	 */
	@Reference
	public User user;
	
	/**
	 * Тип устройства или канала
	 */
	@Property
	public UserDeviceType type;

	/**
	 * Статус девайса
	 */
	@Property
	public UserDeviceStatus status;
	
	/**
	 * Идентификатор устройства
	 */
	@Property
	public String deviceId;
	
	/**
	 * Идентифкатор для отправки уведомлений
	 */
	@Property
	public String pushId;
	
	/**
	 * Операционная система
	 */
	@Property
	public String os;
	
	/**
	 * Версия операционной системы
	 */
	@Property
	public String osVersion;
	
	/**
	 * Время последней активности. В основном для мобильников
	 */
	@Property
	public Calendar lastAcitivyTime;
	
}
