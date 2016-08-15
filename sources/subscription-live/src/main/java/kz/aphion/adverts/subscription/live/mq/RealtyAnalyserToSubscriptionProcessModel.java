package kz.aphion.adverts.subscription.live.mq;

import java.util.Calendar;

import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import com.google.gson.Gson;

/**
 * Модель используется для передачи сообщения из Analyser в модуль подписок
 * @author artem.demidovich
 *
 * Created at Aug 10, 2016
 */
public class RealtyAnalyserToSubscriptionProcessModel {
	
	/**
	 * Идентификатор объявления
	 */
	public String advertId;
	
	/**
	 * Идентификатор обновленного объявления
	 */
	public String oldAdvertId;

	/**
	 * Вид недвижимости
	 */
	public RealtyType type;
	
	/**
	 * Вид операции (Продажа, Аренда)
	 */
	public RealtyOperationType operation;
	
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
	public static RealtyAnalyserToSubscriptionProcessModel parseModel(String jsonModel) {
		if (jsonModel == null)
			return null;
		
		Gson gson = new Gson();
		RealtyAnalyserToSubscriptionProcessModel model = gson.fromJson(jsonModel, RealtyAnalyserToSubscriptionProcessModel.class);
		
		return model;
	}
	
}
