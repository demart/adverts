package kz.aphion.adverts.web.api.models.users;

import kz.aphion.adverts.web.api.exceptions.ModelValidationException;

import org.apache.commons.lang.StringUtils;

/**
 * Модель запроса на смену пароля пользователя.
 * Модель используется в двух случаях
 * 1. Когда пользователь сбрасывает пароль
 * 2. Когда пользователь меняет пароль внутри системы
 * @author artem.demidovich
 *
 * Created at Nov 19, 2017
 */
public class ChangePasswordRequestModel {

	/**
	 * Старый пароль только вслучае с изменением пароля в системе
	 */
	public String oldPassword;
	
	/**
	 * Новый пароль 
	 */
	public String newPassword;
	
	/**
	 * Валидирует модель данных
	 * @throws ModelValidationException 
	 */
	public void validate() throws ModelValidationException {
		if (StringUtils.isBlank(this.newPassword))
			throw new ModelValidationException("NewPassword is null or empty.");
	}
	
	/**
	 * Валидация модели
	 * @param model
	 * @throws ModelValidationException
	 */
	public static void validate(ChangePasswordRequestModel model) throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("Model is null or empty.");
		
		model.validate();
	}
	
}
