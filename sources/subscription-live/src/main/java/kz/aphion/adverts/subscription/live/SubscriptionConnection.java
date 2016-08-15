package kz.aphion.adverts.subscription.live;

import javax.websocket.Session;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.subscription.live.models.SubscriptionModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;


/**
 * Класс описывает соединение между сервером и клиентом и также данные
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */
public class SubscriptionConnection {

	private static Logger logger = LoggerFactory.getLogger(SubscriptionConnection.class);
	
	public SubscriptionConnection(SubscriptionQuery query) {
		this.query = query;
	}
	
	/**
	 * Мета информации о подписке
	 */
	public SubscriptionModel subscription;
	
	/**
	 * Соединение с клиентом
	 */
	public Session session;
	
	/**
	 * 
	 */
	public SubscriptionQuery query;
	
	/**
	 * Метод принимает объявление и решает нужно ли уведомлять клиента или нет
	 * @param advert
	 * @throws Exception 
	 */
	public void notify(BaseEntity advert) throws Exception {
		if (query.isAdvertBelongToQuery(advert)) {
			// Если объявление принадлежит, то
			// формируем сообщение для клиента
			// отправляем сообщение
			// обновляем статистику
			// Вставляем также рекламные секции если необходимо
			
			logger.info("Found subscription for the advert!");
			Gson gson = new Gson();
			session.getBasicRemote().sendText(gson.toJson(advert));
			
		}
	}
	
	/**
	 * Получаем модель от клиента
	 * @param userQuery
	 * @throws Exception 
	 */
	public void initWithQuery(String userQuery) throws Exception {
		query.buidQuery(userQuery);
	}
	
	
	/*
	public void initSubscriptionWithQuery(String model) {
		logger.info("Got Subscription: {}", model);
		Gson gson = new Gson();
		T criteria = gson.fromJson(model, this.model.getClass().getGenericSuperclass());
		this.model.criteria = criteria;
		connectionReady = true;
		logger.info("ConvertIt again: {}", gson.toJson(this.model.criteria));
	}
	*/
	
}
