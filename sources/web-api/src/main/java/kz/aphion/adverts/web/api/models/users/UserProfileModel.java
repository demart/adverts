package kz.aphion.adverts.web.api.models.users;

import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.UserType;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;

/**
 * Модель пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class UserProfileModel {

	/**
	 * Идентификатор пользоваля
	 */
	public String id;
	
	/**
	 * Имя пользователя
	 */
	public String name;
	
	/**
	 * Email/Login пользователя
	 */
	public String email;
	
	/**
	 * Контактный телефон (если есть)
	 */
	public String phone;
	
	/**
	 * Тип пользователя
	 */
	public UserType type;
	
	/**
	 * Статус пользователя
	 */
	public UserStatus status;
	
	
	/**
	 * Создает модель на основе объекта пользователя
	 * @param user
	 * @return
	 * @throws ModelValidationException
	 */
	public static UserProfileModel convertToModel(User user) throws ModelValidationException {
		if (user == null)
			throw new ModelValidationException("User is null");
		
		UserProfileModel model = new UserProfileModel();
		model.email = user.email;
		model.id = user.id.toHexString();
		model.name = user.name;
		model.phone = user.phone;
		model.status = user.status;
		model.type = user.type;
	
		return model;
	}
	
}
