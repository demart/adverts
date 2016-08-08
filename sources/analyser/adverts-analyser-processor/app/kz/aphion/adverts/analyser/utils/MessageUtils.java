package kz.aphion.adverts.analyser.utils;

import kz.aphion.adverts.common.models.mq.realties.ProcessRealtyModel;

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
	public static ProcessRealtyModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		ProcessRealtyModel model = gson.fromJson(jsonModel, ProcessRealtyModel.class);
		
		return model;
	}
	
}
