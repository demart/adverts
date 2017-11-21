package kz.aphion.adverts.web.api.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.ws.rs.NameBinding;

import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.UserType;

/**
 * This annotation should be used for secured methods<br>
 * System will check is there is some valid token for provided Header Token value<br>
 * If user account token is invalid system will response with 401 status (Unathorized)<br>
 * If specified some UserTypes or UserStatus only they will be allowed to access the method.
 * @author artem.demidovich
 *
 */
@NameBinding
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface SecuredMethod {
	
	/**
	 * List of allowed user types to access this API method
	 * @return
	 */
	public UserType[] allowedUserTypes() default {};
	
	/**
	 * List of allowed user statuses to access this API method
	 * @return
	 */
	public UserStatus[] allowedUserStatus() default {};
	
}
