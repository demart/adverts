package kz.aphion.adverts.persistence.users.request;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.users.User;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Временный ключ для проверки email пользователей перед активацией профилей или смены почты
 * @author artem.demidovich
 *
 * Created at Nov 18, 2017
 */

@Entity("adverts.users.email.verification")
@Converters(CalendarConverter.class)
public class EmailVerificationRequest extends BaseEntity {

	/**
	 * Какой пользователь запрашивал сброс пароля
	 */
	@Reference
	public User user;
	
	/**
	 * Уникальный ключ
	 */
	@Property
	public String token;
	
	/**
	 * Время действия данного токена
	 */
	@Property
	public Calendar expiresAt;
	
	/**
	 * Был ли использован данный запрос
	 */
	@Property
	public boolean used;
	
	/**
	 * Время когда сбросили пароль
	 */
	@Property
	public Calendar usedTime;
	
}
