package kz.aphion.adverts.web.api.models.users;

import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.utils.EmailUtils;

import org.apache.commons.lang.StringUtils;

/**
 * Модель используемая для версификации почтового ящика
 * 
 * @author artem.demidovich
 *
 * Created at Nov 18, 2017
 */
public class EmailVerificationModel {

	/**
	 * Верифицируемый Email 
	 */
	public String email;
	
	/**
	 * Токен для верификации. 
	 */
	public String token;
	

	public static void validate(EmailVerificationModel model) throws ModelValidationException {
		if (model == null)
			throw new ModelValidationException("model is null");
		
		model.validate();
	}
	
	public void validate() throws ModelValidationException {
		if (StringUtils.isBlank(email))
			throw new ModelValidationException("model.email is null or empty");
		
		if (!EmailUtils.isValidEmailAddress(email))
			throw new ModelValidationException("model.email is incorrect");
	}
	
	
	public void validateToken() throws ModelValidationException {
		if (StringUtils.isBlank(token))
			throw new ModelValidationException("model.token is null or empty");
	}
	
}
