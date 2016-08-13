package kz.aphion.adverts.analyser.mq;

/**
 * Состояние объявления на момент обработки crawler 
 * 
 * @author artem.demidovich
 *
 */
public enum RealtyProcessStatus {

	/**
	 * Новое объявление.
	 */
	NEW,
	
	/**
	 * Объявление было обновлено.
	 * oldAdvertId должно быть заполнено при указании этого статуса
	 */
	UPDATED,
	
}
