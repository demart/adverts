package kz.aphion.adverts.web.api.models.subscriptions.requests;

/**
 * Модель запроса на получение подписок пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class UserSubscriptionsRequestModel {

	
	/**
	 * Для постраничной выгрузки
	 */
	public Integer offset;
	
	/**
	 * Для постраничной выгрузки
	 */
	public Integer limit;
	
	/**
	 * Сортировать по полю
	 * null - default by date
	 * 0 - date
	 * 1 - 
	 */
	public Integer sort;
	
	/**
	 * Сортировать по возрастанию
	 */
	public Boolean asc;

	/**
	 * Валидация модели
	 * @param model
	 */
	public static void validate(UserSubscriptionsRequestModel model) {
		// TODO
		
	}
	
}
