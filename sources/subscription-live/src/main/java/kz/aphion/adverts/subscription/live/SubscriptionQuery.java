package kz.aphion.adverts.subscription.live;

import kz.aphion.adverts.persistence.BaseEntity;

/**
 * Базовый класс для критериев поиска в подписках
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public interface SubscriptionQuery {
	
	/**
	 * Модель для инифиализации Query.
	 * Должно поступать от 
	 * @param query
	 */
	public void buidQuery(String query) throws Exception;
	
	/**
	 * Метод сообщеает проинициализована ли она.
	 * Например её могли создать при откртии сессии с пользователем, но
	 * пользователь еще не передал критерии
	 * @return
	 */
	public boolean isQueryReady();

	
	/**
	 * Метод принимает объявление и проверяет совпадают ли критерии с объявлением
	 * @param advert
	 * @return
	 */
	public boolean isAdvertBelongToQuery(BaseEntity advert) throws Exception;
	
}
