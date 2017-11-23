package kz.aphion.adverts.web.api.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserAccessToken;
import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.exceptions.UserNotFoundException;
import kz.aphion.adverts.web.api.security.UserPrincipal;

public class BaseSecuredService {

	@Context 
	protected HttpServletRequest httpRequest;
	
	@Context 
	protected HttpServletResponse httpResponse;

	@Context
	protected SecurityContext securityContext;
	
	/**
	 * Возвращает учетную запись пользователя
	 * @return
	 */
	protected User getUser() {
		return ((UserPrincipal)securityContext.getUserPrincipal()).getUser();
	}
	
	/**
	 * Возвращает всегда not null пользователя.
	 * @return
	 * @throws AccessDeniedException 
	 */
	protected User getUserOrThrowException() throws AccessDeniedException {
		User user = getUser();
		if (user == null)
			throw new AccessDeniedException("User is not authrorized");
		return user;
	}
	
	
	/**
	 * Метод возвращает запись токена которая была использована для аутентификации пользователя
	 * @return
	 */
	protected UserAccessToken getUserAccessToken() {
		return ((UserPrincipal)securityContext.getUserPrincipal()).getUserAccessToken();
	}
	
}
