package kz.aphion.adverts.persistence.users;

/**
 * Типы устройсвт пользователей. Делятся в основном по каналам отправки уведомлений
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public enum UserDeviceType {
	
	/**
	 * iPhone, iPad и другие девайсы под iOS
	 */
	IOS,
	
	/**
	 * Андроид приложения мобильники и таблеты
	 */
	ANDROID,
	
	/**
	 * 
	 */
	WINDOWS_PHONE,
	
	/**
	 * Маковская операционная система, так как не понятно это то-то другое или нет
	 */
	MACOS,
	
	/**
	 * PUSH уведомления в браузер
	 */
	SAFARI,
	
	/**
	 * PUSH уведомления в браузер
	 */
	CHROME,
	
	/**
	 * PUSH уведомления в браузер
	 */
	FIREFOX,
	
	/**
	 * PUSH уведомления в браузер
	 */
	OPERA

}
