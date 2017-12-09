package kz.aphion.adverts.persistence.users;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Property;

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
	 * Пароль пользователя (MD5)
	 */
	@Property
	public String password;
	
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
	@NotSaved
	public Company company;
	
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
