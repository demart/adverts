package kz.aphion.adverts.persistence.realty;

/**
 * Статус объявления о недвижимости.
 * 
 * Необходимо расширять при появлении процесса публикации у нас на сайте
 * 
 * @author artem.demidovich
 *
 */
public enum RealtyAdvertStatus {

	/**
	 * Не определен
	 */
	UNDEFINED,
	
	/**
	 * Нормальная запись
	 */
	ACTIVE,
	
	/**
	 * Объявление в архиве
	 * 1. При поступлении нового объявления с изменениями текущее объявление уходит в архив
	 * 2. При переодическом обновлении статуса объявления на сайте источника выявлено, что объявление в архиве. 
	 */
	ARCHIVED,
	
	/**
	 * Объявление удалено
	 */
	DELETED,
	
}
