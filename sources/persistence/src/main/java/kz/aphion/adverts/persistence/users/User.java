package kz.aphion.adverts.persistence.users;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.subscription.Subscription;

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
	 * Логин пользователя
	 */
	@Property
	public String login;
	
	/**
	 * Пароль пользователя (MD5)
	 */
	@Property
	public String password;
	
	/**
	 * Почтовый адрес пользователя
	 */
	@Property
	public String email;
	
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
	public Calendar lastAcitivyTime;
	
	/**
	 * Статус пользователя
	 */
	@Property
	public UserStatus status;
	
	/**
	 * Список токенов авторизации пользователя
	 */
	@Reference
	public List<UserAccessToken> accessTokens;
}
