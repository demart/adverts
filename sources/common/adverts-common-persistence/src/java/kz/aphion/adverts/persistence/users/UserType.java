package kz.aphion.adverts.persistence.users;

/**
 * Тип пользователей в системе
 * 
 * @author artem.demidovich
 *
 */
public enum UserType {
	
	/**
	 * Обыкновенный пользователь
	 */
	NORMAL,
	
	/**
	 * Риэлтор
	 */
	REALTOR,
	
	/**
	 * Представитель компании застройщика
	 */
	CONSTRACTION_COMPANY_REALTOR,
	
	/**
	 * Модератор системы
	 */
	MODERATOR,
	
	/**
	 * Администратор системы
	 */
	ADMINISTRATOR,
	
}
