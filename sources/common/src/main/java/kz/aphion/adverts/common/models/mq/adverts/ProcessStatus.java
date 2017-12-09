package kz.aphion.adverts.common.models.mq.adverts;

/**
 * Состояние объявления на момент обработки crawler 
 * 
 * @author artem.demidovich
 *
 */
public enum ProcessStatus {

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
