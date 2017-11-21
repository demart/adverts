package kz.aphion.adverts.web.api.models.users;

import java.util.Map;

import org.apache.commons.lang.StringUtils;

import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.utils.EmailUtils;


/**
 * Модель аутентификации пользователей
 * @author artem.demidovich
 *
 * Created at Nov 18, 2017
 */
public class AuthenticationModel {

	/**
	 * Тип аутентификации
	 */
	public String type;
	
	public String email;
	
	public String password;
	
	/**
	 * Флаг позволяет указать пользователю что его токен можно хранить дольше
	 */
	public Boolean rememberMe;
	
	/**
	 * Клиент в котором подключились
	 */
	public String source;
	
	/**
	 * Токен из социальных сетей
	 */
	public String accessToken;
	
	/**
	 * Список дополнительный параметров
	 */
	public Map<String,String> parameters;
	
	/**
	 * Валидация модели данных
	 * @param model
	 * @throws ModelValidationException В случае возникновение ошибок
	 */
	public static void validate(AuthenticationModel model) throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("model is null");
		
		model.validate();
	}
	
	/**
	 * Валидация модели данных
	 * @throws ModelValidationException В случае возникновение ошибок
	 */
	public void validate() throws ModelValidationException {
		if (StringUtils.isBlank(type))
			throw new ModelValidationException("model.type is null or empty.");
		if (StringUtils.isBlank(email))
			throw new ModelValidationException("model.email is null or empty.");
		if (!EmailUtils.isValidEmailAddress(email))
			throw new ModelValidationException("model.email is incorrect");
		if (StringUtils.isBlank(password))
			throw new ModelValidationException("model.password is null or empty.");
	}
	
}
