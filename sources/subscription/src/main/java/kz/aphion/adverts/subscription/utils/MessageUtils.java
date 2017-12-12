package kz.aphion.adverts.subscription.utils;

import kz.aphion.adverts.common.models.mq.adverts.AnalyserProcessModel;

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
	public static AnalyserProcessModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		AnalyserProcessModel model = gson.fromJson(jsonModel, AnalyserProcessModel.class);
		
		return model;
	}
	
}
