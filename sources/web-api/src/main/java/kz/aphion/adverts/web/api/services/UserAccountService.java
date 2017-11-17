package kz.aphion.adverts.web.api.services;

import java.util.Calendar;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserRegistrationChannel;
import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.UserType;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.users.CreateUserAccountModel;
import kz.aphion.adverts.web.api.models.users.ForgotPasswordRequestModel;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Сервис по управлению учетной записи пользователя
 * @author artem.demidovich
 *
 * Created at Nov 15, 2017
 */
@Named
@RequestScoped
public class UserAccountService {

	private static Logger logger = LoggerFactory.getLogger(UserAccountService.class);
	
	public ResponseWrapper isLoginAvailable(String login) {
		if (isEmailAvailable(login)) {
			return ResponseWrapper.with(Status.NOT_FOUND, true, "Login is available");
		} else {
			return ResponseWrapper.with(Status.FOUND, false, "Login is already used by another user");
		}
	}
	
	
	// Controller - parameters, basic checks, methods etc
	//	catch all exceptions and properly response
	// Service - business logic
	//	throw business exceptions
	// DAL - data access layer, db operations
	//	throw data exceptions
	
	public ResponseWrapper createAccount(CreateUserAccountModel model) {
		// Validate model
		
		// Check isLoginAvailable
		if (!isEmailAvailable(model.email)) {
			return ResponseWrapper.with(Status.NOT_ACCEPTABLE, null, "login is unavailable");
		}
		
		User user = new User();
		
		user.name = model.name;
		user.login = model.email;
		user.email = model.email;
		
		user.company = null;
		user.password = user.password;
		
		user.channel = model.registrationChannel == null ? UserRegistrationChannel.STANDARD : model.registrationChannel;
		if (user.channel != UserRegistrationChannel.STANDARD) {
			// social network
			if (StringUtils.isBlank(model.accessToken)) {
				// ERROR Throw exception
				return ResponseWrapper.with(Status.BAD_REQUEST, null, "accessToken is incorrect for social authenication");
			}
		}
		
		user.type = model.type == null ? UserType.USER : model.type;
		
		user.created = Calendar.getInstance();
		user.updated = Calendar.getInstance();
		user.lastAcitivyTime = Calendar.getInstance();
		
		user.status = UserStatus.NOT_VERIFIED;
		
		DB.DS().save(user);
		// Send email verification request
		
		try {
			MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_QUEUE.getValue(), "TO_BE_IMPLEMENTED");
		} catch (JMSException e) {
			DB.DS().delete(user);
			logger.error("Error during creating new user account", e);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, "Error during creating profile, try later.");
		}
		
		return ResponseWrapper.with(Status.OK, true, "Profile successfully created, verification email sent.");
	}
	
	
	public ResponseWrapper forgotPassword(ForgotPasswordRequestModel model) {
		
		if (isEmailAvailable(model.email)) {
			return ResponseWrapper.with(Status.NOT_ACCEPTABLE, false, "User account not found for the email");
		}
		
		// Add request to temporary table
		// TODO
		
		// Send Email notification with navigation link
		// TODO
		
		return ResponseWrapper.with(Status.OK, true, null);
	}
	
	
	
	/**
	 * Метод проверяет есть ли указанный емаил в базе пользователей или нет
	 * @param email
	 * @return
	 */
	private boolean isEmailAvailable(String email) {
		long found = DB.DS().createQuery(User.class).field("login").equalIgnoreCase(email).countAll();
		return found > 0 ? false : true;
	}
	
	
}
