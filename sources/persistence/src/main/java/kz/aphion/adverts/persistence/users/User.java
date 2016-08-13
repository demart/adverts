package kz.aphion.adverts.persistence.users;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.subscription.Subscription;

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
@Entity("users")
public class User extends BaseEntity {

	/**
	 * Имя пользователя
	 */
	@Property
	public String name;
	
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
	
}
