package kz.aphion.adverts.persistence.phones;

/**
 * Система источник от кого поступил телефон
 * 
 * @author artem.demidovich
 *
 */
public enum PhoneSource {
	
	/**
	 * Не указано
	 */
	UNDEFINED,

	/**
	 * Клиенты использующие систему
	 */
	CLIENTS,
	
	/**
	 * Система наша
	 */
	ADVERTS,
	
	/**
	 * Система ломбардов
	 */
	LOMBARDS,
	
	/**
	 * Сайт колеса
	 */
	KOLESA,
	
	/**
	 * Сайт крыша
	 */
	KRISHA,
	
	/**
	 * Сайт kn.kz
	 */
	KN,
	
	/**
	 * Сайт olx.kz
	 */
	OLX,
	
	/**
	 * Сайт из рук в руки
	 */
	IRR,
	
}
