package kz.aphion.adverts.persistence.users;

/**
 * Статусы учетных записей пользователей
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public enum UserStatus {

	/**
	 * Зарегистрирован
	 */
	REGISTERED,
	
	/**
	 * Необходимо подтвердить email
	 */
	NOT_VERIFIED,
	
	/**
	 * Заблокирован, не знаю пока почему
	 */
	BLOCKED,
	
	/**
	 * Удален
	 */
	DELETED,
	
}
