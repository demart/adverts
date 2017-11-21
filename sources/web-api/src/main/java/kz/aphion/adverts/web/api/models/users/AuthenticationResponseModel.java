package kz.aphion.adverts.web.api.models.users;

import java.util.Calendar;

/**
 * Модель ответа на запрос аутентификации пользователя
 * 
 * @author artem.demidovich
 *
 * Created at Nov 19, 2017
 */
public class AuthenticationResponseModel {
	
	public String accessToken;
	
	public Calendar expiresAt;
	
}
