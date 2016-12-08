package kz.aphion.adverts.persistence.subscription.criteria;

/**
 * Тип поиска по ключевым словам
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public enum KeywordsCriteriaType {

	/**
	 * Ключевые слова будут искаться по правилу И это И это должно быть
	 */
	AND,
	
	/**
	 * Ключевые слова будут искаться по правилу ИЛИ это ИЛИ это должно быть
	 */
	OR,
	
}
