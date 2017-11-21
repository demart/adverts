package kz.aphion.adverts.web.api.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserAccessToken;
import kz.aphion.adverts.web.api.security.UserPrincipal;

public class BaseSecuredController {

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
	 * Метод возвращает запись токена которая была использована для аутентификации пользователя
	 * @return
	 */
	protected UserAccessToken getUserAccessToken() {
		return ((UserPrincipal)securityContext.getUserPrincipal()).getUserAccessToken();
	}
	
}
