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
	UNDEFINED(0),
	
	/**
	 * Хозяин
	 */
	OWNER(1),
	
	/**
	 * Риэлтор
	 */
	REALTOR(2),
	
	/**
	 * Компания риэлтор
	 */
	REALTOR_COMPANY(3),
	
	/**
	 * Компания застройщик
	 */
	DEVELOPER_COMPANY(4);
	
	private final int value;
	
	private RealtyPublisherType(int value){
		this.value = value;
	}
	
	public int getValue() {return value;}
	
}
