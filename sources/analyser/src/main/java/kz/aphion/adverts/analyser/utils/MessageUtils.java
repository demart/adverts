package kz.aphion.adverts.analyser.utils;

import kz.aphion.adverts.common.models.mq.adverts.ProcessModel;

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
	public static ProcessModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		ProcessModel model = gson.fromJson(jsonModel, ProcessModel.class);
		
		return model;
	}
	
}
