package kz.aphion.adverts.subscription.utils;

import kz.aphion.adverts.subscription.mq.RealtyAnalyserToSubscriptionProcessModel;

import com.google.gson.Gson;

/**
 * Утилиты для работы сообщениями сервера очередей
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class MessageUtils {

	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	public static RealtyAnalyserToSubscriptionProcessModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		RealtyAnalyserToSubscriptionProcessModel model = gson.fromJson(jsonModel, RealtyAnalyserToSubscriptionProcessModel.class);
		
		return model;
	}
	
}
