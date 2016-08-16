package kz.aphion.adverts.notification.mq;

import java.util.Calendar;

import com.google.gson.Gson;

/**
 * Модель передачи данных из модуля подписок в модуль уведолмений.
 * Должно использоваться при IMMEDIATE уведомлениях в подписках
 * @author artem.demidovich
 *
 * Created at Aug 13, 2016
 */
public class RealtySubscriptionToNotificationProcessModel {

	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Идентификатор подписки
	 */
	public String subscriptionId;
	
	/**
	 * Идентификатор объявления в подписке
	 */
	public String subscriptionAdvertId;
	
	/**
	 * Статус объявления для конкретного пользователя
	 * Для него даже ухудшенное объявление может быть новым, так как он например
	 * подписался только сегодня, а объявление уже было до этого несколько дней и
	 * только сегодня оно ухудшилось
	 */
	public SubscriptionProcessStatus status;
	
	/**
	 * Время события, для того чтобы потом отслеживать производительность
	 */
	public Calendar eventTime;
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	public static RealtySubscriptionToNotificationProcessModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		RealtySubscriptionToNotificationProcessModel model = gson.fromJson(jsonModel, RealtySubscriptionToNotificationProcessModel.class);
		
		return model;
	}
	
	
}
