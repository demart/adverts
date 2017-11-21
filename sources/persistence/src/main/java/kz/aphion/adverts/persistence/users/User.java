package kz.aphion.adverts.persistence.users;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.users.request.EmailVerificationRequest;
import kz.aphion.adverts.persistence.users.request.ResetPasswordRequest;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * 
 * Пользователь системы
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.users")
@Converters(CalendarConverter.class)
public class User extends BaseEntity {

	/**
	 * Имя пользователя
	 */
	@Property
	public String name;

	/**
	 * Канал регистрации пользователя
	 */
	@Property
	public UserRegistrationChannel channel;
	
	/**
	 * Тока для доступа (Из социальных сетей если)
	 */
	@Property
	public String accessToken;
	
	
	/**
	 * Почтовый адрес пользователя, он же логин в системе
	 */
	@Property
	public String email;

	/**
	 * Список токенов для проверки email
	 */
	@Reference
	public List<EmailVerificationRequest> emailVerificationRequests;	
	
	/**
	 * Пароль пользователя (MD5)
	 */
	@Property
	public String password;
	
	/**
	 * Список токенов авторизации пользователя
	 */
	@Reference
	public List<ResetPasswordRequest> resetPasswordRequests;

	
	/**
	 * Информация о контактном телефоне
	 */
	@Property
	public String phone;
	
	/**
	 * Тип или вид пользователя
	 */
	@Property
	public UserType type;
	
	/**
	 * Компания к которой относиться пользователь
	 */
	public Company company;
	
	/**
	 * Список подписок пользователя
	 */
	@Reference
	public List<Subscription> subscriptions;

	/**
	 * Каналы или устройства пользователя
	 */
	@Reference
	public List<UserDevice> devices;
	
	/**
	 * Время последней активности.
	 */
	@Property
	public Calendar lastActivityTime;
	
	/**
	 * Статус пользователя
	 */
	@Property
	public UserStatus status;
	
}
