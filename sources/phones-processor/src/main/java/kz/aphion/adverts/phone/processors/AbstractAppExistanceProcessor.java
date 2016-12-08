package kz.aphion.adverts.phone.processors;

import kz.aphion.adverts.phone.models.PhoneApplicationCheckModel;

import com.google.gson.Gson;

/**
 * Абстактный класс для всех классов процессоров проверки наличия приложения
 * @author artem.demidovich
 *
 * Created at May 16, 2016
 */
public abstract class AbstractAppExistanceProcessor {

	/**
	 * Обрабатывает запрос на проверку наличия приложения
	 * @param message JSON сообщение
	 * @throws Exception
	 */
	public abstract void processMessage(String message) throws Exception;
	
	
	/**
	 * Разбирает JSON объект в модель для последующей обработки
	 * @param jsonModel
	 * @return
	 */
	private PhoneApplicationCheckModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		PhoneApplicationCheckModel model = gson.fromJson(jsonModel, PhoneApplicationCheckModel.class);
		
		return model;
	}
}
