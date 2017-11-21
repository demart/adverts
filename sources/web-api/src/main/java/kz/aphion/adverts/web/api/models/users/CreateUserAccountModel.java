package kz.aphion.adverts.web.api.models.users;

import kz.aphion.adverts.persistence.users.UserRegistrationChannel;
import kz.aphion.adverts.persistence.users.UserType;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.utils.EmailUtils;

import org.apache.commons.lang.StringUtils;

/**
 * Модель создания нового аккаунта пользователя
 * @author artem.demidovich
 *
 * Created at Nov 16, 2017
 */
public class CreateUserAccountModel {

	/**
	 * Тип пользователя, для разных типов будет поддерживатся разная валидация в будущем
	 */
	public UserType type;
	
	/**
	 * Имя пользователя (как он себе его представляет и как его видят другие)
	 */
	public String name;
	
	/**
	 * Через какой канал мы регистрируем пользователя 
	 */
	public UserRegistrationChannel channel;
	
	/**
	 * Токен доступа пользователя
	 */
	public String accessToken;
	
	/**
	 * Уникальный email он же является логином для входа в систему
	 */
	public String email;
	
	/**
	 * Пароль для входа в систему
	 */
	public String password;
	
	/**
	 * Сотовый телефон
	 */
	public String phone;
	
	
	public static void validate(CreateUserAccountModel model) throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("model is null");
		
		model.validate();
	}
	
	public void validate() throws ModelValidationException {
	
		if (type == null)
			throw new ModelValidationException("model.type is null");

		if (StringUtils.isBlank(name))
			throw new ModelValidationException("model.name is null or empty");
		
		if (StringUtils.isBlank(email))
			throw new ModelValidationException("model.email is null or empty");
		
		if (!EmailUtils.isValidEmailAddress(email))
			throw new ModelValidationException("model.email is incorrect");
		
		if (StringUtils.isBlank(password))
			throw new ModelValidationException("model.password is null or empty");

	}
	
}
