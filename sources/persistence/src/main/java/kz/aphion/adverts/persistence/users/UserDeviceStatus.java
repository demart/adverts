package kz.aphion.adverts.persistence.users;

/**
 * Состояние девайса или канала пользователя.
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public enum UserDeviceStatus {

	/**
	 * Устройство активно. Значит будет участвовать в выборках и рассылках
	 */
	ACTIVE,
	
	// TODO продумать остальные статусы
	
	/**
	 * Устройство удалено или не активно
	 */
	DELETED,
	
}
