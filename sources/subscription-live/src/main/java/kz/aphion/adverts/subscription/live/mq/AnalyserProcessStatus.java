package kz.aphion.adverts.subscription.live.mq;

/**
 * Состояние объявления на момент обработки analyser
 * 
 * @author artem.demidovich
 *
 * Created at Aug 10, 2016
 */
public enum AnalyserProcessStatus {

	/**
	 * Новое объявление.
	 */
	NEW,
	
	/**
	 * Объявление было улучшено
	 * oldAdvertId должно быть заполнено при указании этого статуса
	 */
	BETTER,
	
	/**
	 * Объявлени стало хуже чем было до этого
	 * oldAdvertId должно быть заполнено при указании этого статуса
	 */
	WORSTE,
	
}
