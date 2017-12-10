package kz.aphion.adverts.persistence.phones;

/**
 * Зафиксированный владелей телефона
 * @author artem.demidovich
 *
 */
public enum PhoneOwner {

	/**
	 * Не указано
	 */
	UNDEFINED,
	
	/**
	 * Хозяин
	 */
	OWNER,
	
	/**
	 * Риэлтор или посредник
	 */
	AGENT,
	
	/**
	 * Компания риэлтор или посредник
	 */
	AGENT_COMPANY,
	
	/**
	 * Кампания
	 */
	COMPANY,
	
	/**
	 * Строительная компания
	 */
	DEVELOPER_COMPANY,
	
}
