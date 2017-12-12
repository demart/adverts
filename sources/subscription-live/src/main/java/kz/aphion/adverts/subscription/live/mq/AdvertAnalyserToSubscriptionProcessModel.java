package kz.aphion.adverts.subscription.live.mq;

import java.util.Calendar;

import com.google.gson.Gson;

/**
 * Модель используется для передачи сообщения из Analyser в модуль подписок
 * @author artem.demidovich
 *
 * Created at Aug 10, 2016
 */
public class AdvertAnalyserToSubscriptionProcessModel {
	
	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Идентификатор обновленного объявления
	 */
	public String oldAdvertId;

	/**
	 * Результат обработки. Лучше Хуже или новое
	 */
	public AnalyserProcessStatus status;
	
	/**
	 * Время отправки объявления в очередь
	 */
	public Calendar eventTime;
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	public static AdvertAnalyserToSubscriptionProcessModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		AdvertAnalyserToSubscriptionProcessModel model = gson.fromJson(jsonModel, AdvertAnalyserToSubscriptionProcessModel.class);
		
		return model;
	}
	
}
