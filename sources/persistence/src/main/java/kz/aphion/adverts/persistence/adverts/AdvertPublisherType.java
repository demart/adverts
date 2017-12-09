package kz.aphion.adverts.persistence.adverts;

/**
 * Тип (Вид) автора опубликовавшего объявление
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public enum AdvertPublisherType {
	/**
	 * Не определено
	 */
	UNDEFINED(0),
	
	/**
	 * Хозяин
	 */
	OWNER(1),
	
	/**
	 * Посредник
	 */
	AGENT(2),
	
	/**
	 * Компания посредник
	 */
	AGENT_COMPANY(3),
	
	/**
	 * Компания владелец (или застройщик) 
	 */
	COMPANY(4);
	
	private final int value;
	
	private AdvertPublisherType(int value){
		this.value = value;
	}
	
	public int getValue() {return value;}
	
}
