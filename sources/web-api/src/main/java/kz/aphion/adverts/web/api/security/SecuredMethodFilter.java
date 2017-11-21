package kz.aphion.adverts.web.api.security;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.persistence.users.UserAccessToken;
import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.UserType;
import kz.aphion.adverts.web.api.HttpHeaderConstant;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.repositories.UserAccessTokenRepository;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SecuredMethodFilter implements ContainerRequestFilter {

	private static Logger logger = LoggerFactory.getLogger(SecuredMethodFilter.class);
	
	@Context
	ResourceInfo resourceInfo;

	@Inject
	UserAccessTokenRepository repository;
	
	/**
	 * If on some methods use SecuredMethod annotation system will check following<br>
	 * 1. If request has SSO Token in the header<br>
	 * 2. If there is valid token with SSO token header in the request<br>
	 * 3. If there is user account with status not equal ACTIVE<br>
	 * If these checks will fail server responses with 401 HTTP Status and 
	 * dissalow execution of secured API 
	 */
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		logger.trace("RSF0001T: Security Filter invoked");
		
		if (isMethodSecured() == false) {
			// Don't need to check security
			return;
		}
		logger.trace("RSF0002T: Access to secured method, checking Security Token...");
		
		// Check is required header presented
		// If not will be generated response with 401 UNAUTHORIZED status and message
		String token = requestContext.getHeaderString(HttpHeaderConstant.ADVERTS_SSO_TOKEN);
		if (StringUtils.isBlank(token)) {
			logger.debug("RSF0003D: Access to secured method without " + HttpHeaderConstant.ADVERTS_SSO_TOKEN + " header value,  request will be rejected.");
			Response response = ResponseWrapper.with(Status.UNAUTHORIZED, null, "Missing SSO Token Header Value").buildResponse();
			requestContext.abortWith(response);
			return;
		}
		
		// try to look up in database valid token
		UserAccessToken userAccessToken;
		try {
			userAccessToken = repository.getAccesToken(token);
		} catch (Exception e) {
			logger.error("RSF0004E: " + e.getMessage(), e);
			Response response = ResponseWrapper.with(Status.UNAUTHORIZED, null, "Invalid SSO Token Header Value").buildResponse();
			requestContext.abortWith(response);
			return;
		}
		
		if (userAccessToken == null) {
			// No any valid token, so.. rejecting request with sorry message
			logger.debug("RSF0005D: Access to secured method [{}] with invalid token, request will be rejected.", getMethodName());
			Response response = ResponseWrapper.with(Status.UNAUTHORIZED, null, "Invalid SSO Token Header Value").buildResponse();
			requestContext.abortWith(response);
			return;
		}
		
		
		if (userAccessToken.isExpired()) {
			logger.debug("RSF0006D: User.login [{}] access to secured method [{}] with expired token, request will be rejected.", getUserLogin(userAccessToken), getMethodName());
			Response response = ResponseWrapper.with(Status.UNAUTHORIZED, null, "Token is expired").buildResponse();
			requestContext.abortWith(response);
			return;
		}
		
		
		// Extend user session lifetime
		new UserAccessTokenRepository().prolongateSessionLifeTime(userAccessToken);
		
		
		// Check User Types
		if (!isUserTypeAllowed(userAccessToken)) {
			logger.warn("RSF0006W: User.login [{}] access to secured method [{}] with wrong user type, request will be rejected.", 
					getUserLogin(userAccessToken), resourceInfo.getResourceMethod().getName(), getUserType(userAccessToken));
			Response response = ResponseWrapper.with(Status.FORBIDDEN, null, "Access is forbidden").buildResponse();
			requestContext.abortWith(response);
			return;
		}
		
		// Check User Statuses
		if (!isUserStatusAllowed(userAccessToken)) {
			logger.warn("RSF0007W: User.login [{}] access to secured method [{}] with wrong user status [{}], request will be rejected.", 
					getUserLogin(userAccessToken), resourceInfo.getResourceMethod().getName(), getUserStatus(userAccessToken));
			Response response = ResponseWrapper.with(Status.FORBIDDEN, null, "Access is forbidden").buildResponse();
			requestContext.abortWith(response);
			return;
		}
		
		// Everything is Ok, injecting SecurityContext with UserAccount
		requestContext.setSecurityContext(new UserSecurityContext(userAccessToken.user, userAccessToken));
		logger.debug("RSF0008D: User.login [{}] successfully granted access to the method {}.", getUserLogin(userAccessToken), getMethodName());
	}
	
	private String getUserLogin(UserAccessToken uat) {
		return uat.user.email;
	}
	
	private String getUserStatus(UserAccessToken uat) {
		return uat.user.status != null ? uat.user.status.toString() : "!UNDEFINED!";
	}
	
	private String getUserType(UserAccessToken uat) {
		return uat.user.type != null ? uat.user.type.toString() : "!UNDEFINED!";
	}
	
	private String getMethodName() {
		return resourceInfo.getResourceMethod().getName();
	}
	
	
	private boolean isUserTypeAllowed(UserAccessToken uat) {
		UserType[] userTypes = getAllowedUserTypes();
		if (userTypes != null && userTypes.length > 0) {
			boolean found = false;
			for (UserType userType : userTypes)
				if (userType.equals(uat.user.type))
					found = true;
			return found;
		}
		return true;
	}
	
	private boolean isUserStatusAllowed(UserAccessToken uat) {
		UserStatus[] userStatuses = getAllowedUserStatuses();
		if (userStatuses != null && userStatuses.length > 0) {
			boolean found = false;
			for (UserStatus userStatus : userStatuses) {
				if (userStatus.equals(uat.user.status))
					found = true;
			}
			return found;
		}
		return true;
	}
	
	
	private UserType[] getAllowedUserTypes(){
		SecuredMethod securedMethod = getSecuredMethod();
		return securedMethod != null ? securedMethod.allowedUserTypes() : null;
	}
	
	private UserStatus[] getAllowedUserStatuses(){
		SecuredMethod securedMethod = getSecuredMethod();
		return securedMethod != null ? securedMethod.allowedUserStatus() : null;
	}
	
	private boolean isMethodSecured() {
		return getSecuredMethod() != null ? true : false;
	}
	
	private SecuredMethod getSecuredMethod() {
		return resourceInfo.getResourceMethod().getAnnotation(SecuredMethod.class);
	}

}
