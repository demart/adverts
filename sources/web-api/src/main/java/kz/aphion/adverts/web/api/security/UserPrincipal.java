package kz.aphion.adverts.web.api.security;

import java.security.Principal;

import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserAccessToken;

public class UserPrincipal implements Principal {

	public UserPrincipal(User user, UserAccessToken uat) {
		this.user = user;
		this.uat = uat;
	}
	
	private User user;
	
	private UserAccessToken uat;
	
	public User getUser() {
		return user;
	}
	
	public UserAccessToken getUserAccessToken() {
		return uat;
	}
	
	@Override
	public String getName() {
		return user.id.toHexString();
	}

}
