package kz.aphion.adverts.persistence.realty;

/**
 * Тип пользователя опубликовавшего объявление
 * 
 * @author artem.demidovich
 *
 */
public enum RealtyPublisherType {

	/**
	 * Не определено
	 */
	UNDEFINED,
	
	/**
	 * Хозяин
	 */
	OWNER,
	
	/**
	 * Риэлтор
	 */
	REALTOR,
	
	/**
	 * Компания риэлтор
	 */
	REALTOR_COMPANY,
	
	/**
	 * Компания застройщик
	 */
	DEVELOPER_COMPANY,
	
}
