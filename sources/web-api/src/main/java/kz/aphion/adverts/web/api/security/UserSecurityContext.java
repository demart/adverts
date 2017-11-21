package kz.aphion.adverts.web.api.security;

import java.security.Principal;

import javax.ws.rs.core.SecurityContext;

import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserAccessToken;

public class UserSecurityContext  implements SecurityContext {

	public UserSecurityContext(User user, UserAccessToken uat) {
		this.userPrincipal = new UserPrincipal(user, uat);
	}
	
	private UserPrincipal userPrincipal;
	
	public User getUser() {
		return userPrincipal.getUser();
	}
	
	@Override
	public String getAuthenticationScheme() {
		return SecurityContext.BASIC_AUTH;
	}

	@Override
	public Principal getUserPrincipal() {
		return userPrincipal;
	}

	@Override
	public boolean isSecure() {
		return false;
	}

	@Override
	public boolean isUserInRole(String arg0) {
		return false;
	}
}
