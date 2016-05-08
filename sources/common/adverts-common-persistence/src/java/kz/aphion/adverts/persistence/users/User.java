package kz.aphion.adverts.persistence.users;

/**
 * 
 * Пользователь системы
 * 
 * @author artem.demidovich
 *
 */
public class User {

	/**
	 * Имя пользователя
	 */
	public String name;
	
	public String login;
	
	public String password;
	
	/**
	 * Тип или вид пользователя
	 */
	public UserType type;
	
	/**
	 * Компания к которой относиться пользователь
	 */
	public Company company;
	
}
