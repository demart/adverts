package kz.aphion.adverts.subscription.searcher;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.subscription.Subscription;

/**
 * Общий интерфейс для всех возможных реализаций поиска подписок по недвижимости
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public interface SubscriptionSearcher {

	
	public List<Subscription> search();
	
	/**
	 * Указываем идентификтор записи в БД
	 * @param objectId
	 */
	public void setAdvertObjectId(String objectId);
	
	/**
	 * Получает объект
	 * @return
	 */
	public BaseEntity getAdvertObject();
	
	
}
