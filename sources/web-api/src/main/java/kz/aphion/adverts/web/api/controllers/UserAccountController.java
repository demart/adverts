package kz.aphion.adverts.web.api.controllers;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.EmailAlreadyUsedException;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.exceptions.TokenNotFoundException;
import kz.aphion.adverts.web.api.exceptions.TokenVerificationException;
import kz.aphion.adverts.web.api.exceptions.UserNotFoundException;
import kz.aphion.adverts.web.api.exceptions.WrongPasswordException;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.users.AuthenticationModel;
import kz.aphion.adverts.web.api.models.users.ChangePasswordRequestModel;
import kz.aphion.adverts.web.api.models.users.CreateUserAccountModel;
import kz.aphion.adverts.web.api.models.users.EmailVerificationModel;
import kz.aphion.adverts.web.api.models.users.ResetPasswordModel;
import kz.aphion.adverts.web.api.security.SecuredMethod;
import kz.aphion.adverts.web.api.services.UserAccountService;
import kz.aphion.adverts.web.api.utils.EmailUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;

/**
 * Котроллер отвечает за базовые функции в части управления user account'om 
 * @author artem.demidovich
 *
 * Created at Nov 15, 2017
 */
@Named
@RequestScoped
@Path("/v1/user/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAccountController extends BaseSecuredController {

	private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

	
	@Inject
	UserAccountService service;
	
	/**
	 * Проверяет свободный ли логин для использования
	 * @param login
	 * @return
	 */
	@GET  
	@Path("/is/email/available")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)  
	public Response isEmailAvailable(@QueryParam("email") String email) {
		logger.debug("UAC0001D: isEmailAvailable: invoked with the email: [{}]", email);
		
		if (StringUtils.isBlank(email)) {
			logger.debug("UAC0002D: isEmailAvailable: email [{}] is null or empty.", email);
			return ResponseWrapper.with(Status.BAD_REQUEST, "email is null or empty").buildResponse();
		}
		
		if (!EmailUtils.isValidEmailAddress(email)) {
			logger.debug("UAC0003D: isEmailAvailable: email [{}] is incorrect.", email);
			return ResponseWrapper.with(Status.BAD_REQUEST, "email is incorrect").buildResponse();
		}

		ResponseWrapper wrapper = service.isLoginAvailable(email);		
		logger.debug("UAC0004D: isEmailAvailable: email [{}] is available [{}]", email, wrapper.data);
		
		return wrapper.buildResponse();
	}	
	
	@POST  
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createUserAccount(CreateUserAccountModel model) {
		logger.debug("UAC0010: createUserAccount: invoked. User.email [{}]", model != null ? model.email : "anonymous");
		
		try {
			CreateUserAccountModel.validate(model);
			
			ResponseWrapper result = service.createAccount(model);
			logger.debug("UAC0011D: createAccount: succesefully completed. User.email [{}]", model.email);
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UAC0012D: createUserAccount: Model validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) { // Email already used exception
			logger.error("UAC0012E: createUserAccount: Data validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (EmailAlreadyUsedException e) {
			logger.error("UAC0013E: createUserAccount: Email validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.CONFLICT, false, e.getMessage()).buildResponse();
		} catch (IOException |  TemplateException e) {
			// Sent Status = OK, because we created account, email can be verified later
			logger.error("UAC0014E: createUserAccount: Internal exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous", e);
			return ResponseWrapper.with(Status.OK, true, "User account created, but email verification message has not been sent").buildResponse();
		} catch (JMSException e) {
			// Sent Status = OK, because we created account, email can be verified later			
			logger.error("UAC0015E: createUserAccount: Internal JMS exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous", e);
			return ResponseWrapper.with(Status.OK, true, "User account created, but email verification message has not been sent").buildResponse();
		} 
	}
	
	@POST  
	@Path("/send/email/verification/request")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response sendEmailVerificationRequest(EmailVerificationModel model) {
		logger.debug("UAC0020D: sendEmailVerificationRequest: invoked. User.email [{}]", model != null ? model.email : "anonymous");

		try {
			EmailVerificationModel.validate(model);
			
			ResponseWrapper result = service.sendEmailVerificationRequest(model);
			logger.debug("UAC0021D: sendEmailVerificationRequest: successfully completed, User.email [{}]",  model.email);
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UAC0022D: sendEmailVerificationRequest: Model validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (UserNotFoundException e) {
			logger.debug("UAC0023D: sendEmailVerificationRequest: User validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.error("UAC0024E: sendEmailVerificationRequest: Data validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, e.getMessage()).buildResponse();
		} catch (IOException |  TemplateException e) {
			logger.error("UAC0025E: sendEmailVerificationRequest: Internal Template exception: [{}], User.email [{}]", e.getMessage(), model.email, e);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, "Email verification message has not been sent").buildResponse();
		} catch (JMSException e) {			
			logger.error("UAC0026E: sendEmailVerificationRequest: Internal JMS exception: [{}], User.email [{}]", e.getMessage(), model.email, e);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, "Email verification message has not been sent").buildResponse();
		}
	}
	
	@POST  
	@Path("/verify/email")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response verifyEmail(EmailVerificationModel model) {
		logger.debug("UAC0030D: verifyEmail: invoked. User.email [{}]", model != null ? model.email : "anonymous");
		
		try {
			EmailVerificationModel.validate(model);
			model.validateToken();
			
			ResponseWrapper result = service.verifyEmail(model);
			logger.debug("UAC0031D: verifyEmail: email [{}] with token [{}] succesefully completed", model.email, model.token);
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UAC0032D: verifyEmail: Model validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (TokenNotFoundException e) {
			logger.debug("UAC0033D: verifyEmail: Token not found exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (TokenVerificationException e) {
			logger.debug("UAC0034D: verifyEmail: Token validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (UserNotFoundException e) {
			logger.debug("UAC0035D: verifyEmail: User validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.error("UAC0036E: verifyEmail: Data validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, e.getMessage()).buildResponse();
		} 
		
	}

	
	@POST 
	@Path("/reset/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response resetPassword(ResetPasswordModel model) {
		logger.debug("UAC0040D: resetPassword: invoked. User.email [{}]", model != null ? model.email : "anonymous");
		
		try {
			ResetPasswordModel.validate(model);
			
			ResponseWrapper result = service.resetPassword(model);
			logger.debug("UAC0041D: resetPassword: for email [{}] successfully processed", model.email);
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UAC0042D: resetPassword: Model validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (UserNotFoundException e) {
			logger.debug("UAC0043D: resetPassword: User validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.error("UAC0044E: resetPassword: Data validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, e.getMessage()).buildResponse();
		} catch (IOException |  TemplateException e) {
			logger.error("UAC0045E: resetPassword: Internal Template exception: [{}], User.email [{}]", e.getMessage(), model.email, e);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, "Email verification message has not been sent").buildResponse();
		} catch (JMSException e) {			
			logger.error("UAC0046E: resetPassword: Internal JMS exception: [{}], User.email [{}]", e.getMessage(), model.email, e);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, "Email verification message has not been sent").buildResponse();
		} 
	}
	
	/**
	 * Метод должен выполнять сверку с присланным токеном и в случае совпадения 
	 * выдадавать новый сессионный токен, как в операции Login, и позволять сменить пароль без указания 
	 * старого пароля 
	 * @param model
	 * @return
	 */
	@POST 
	@Path("/reset/password/confirm")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response confirmResetPassword(ResetPasswordModel model) {
		logger.debug("UAC0050D: confirmResetPassword: invoked. User.email [{}]", model != null ? model.email : "anonymous");
		
		try {
			ResetPasswordModel.validate(model);
			model.validateToken();
			
			ResponseWrapper result = service.confirmResetPassword(model);
			logger.debug("UAC0051D: confirmResetPassword: for email [{}] successfully completed", model.email);
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UAC0052D: confirmResetPassword: Model validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (TokenNotFoundException e) {
			logger.debug("UAC0053D: confirmResetPassword: Token not found exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (TokenVerificationException e) {
			logger.debug("UAC0054D: confirmResetPassword: Token validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (UserNotFoundException e) {
			logger.debug("UAC0055D: confirmResetPassword: User validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.error("UAC0056E: confirmResetPassword: Data validation exception: [{}], User.email [{}]", e.getMessage(), model.email);
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, e.getMessage()).buildResponse();
		} 
	}
	

	/**
	 * Метод аутентифицирует пользователя пока только по логин/паролю.
	 * 
	 * @param model
	 * @return
	 */
	@POST  
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(AuthenticationModel model) {
		logger.debug("UAC0060D: login: invoked for User.email [{}]", model != null ? model.email : "anonymous");
		
		try {

			AuthenticationModel.validate(model);
			ResponseWrapper result = service.login(model);
			logger.debug("UAC0061D: login: successfully comleted. User.email [{}]", model.email);
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UAC0062D: login: Model validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (UserNotFoundException e) {
			logger.debug("UAC0063D: login: User validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.error("UAC0064E: login: Data validation exception: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.INTERNAL_SERVER_ERROR, false, e.getMessage()).buildResponse();
		} catch (AccessDeniedException e) {
			logger.warn("UAC0065W: login: Access Denied: [{}], User.email [{}]", e.getMessage(), model != null ? model.email : "anonymous");
			return ResponseWrapper.with(Status.FORBIDDEN, false, e.getMessage()).buildResponse();
		} 	
	}

	/**
	 * Метод получает существющих сессионных токен и удаляет его 
	 * @return
	 */
	@POST  
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@SecuredMethod
	public Response logout() {
		logger.debug("UAC0070D: logout: invoked for User.email [{}]", getUser().email);
		
		ResponseWrapper result = service.logout();
		
		logger.debug("UAC0071D: logout: for User.email [{}] successfully completed.", getUser().email);
		return result.buildResponse();
	}
	

	/**
	 * Метод смены парооля пользователя, работает в двух режимах<br>
	 * 1. Передают старый и новый пароль<br>
	 * 2. Передают только новый пароль, в этом случае старый должен быть в базе null<br>
	 * Таким образом этим методом можно покрыть оба варианта<br>
	 * 1. Пользователь забыл пароль и мы его сбросили но теперь нужно указать новый пароль<br>
	 * 2. Пользователь сам меняет пароль в системе<br>
	 * @param model
	 * @return
	 */
	@POST  
	@Path("/change/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@SecuredMethod(allowedUserStatus={UserStatus.ACTIVE})
	public Response changePassword(ChangePasswordRequestModel model) {
		logger.debug("UAC0080D: changePassword: invoked for User.email [{}]", getUser().email);
		
		try {
			
			ChangePasswordRequestModel.validate(model);
			ResponseWrapper result = service.changePassword(model);
			logger.debug("UAC0081D: changePassword: for email [{}], completed ", getUser().email);
			return result.buildResponse();
			
		} catch (WrongPasswordException e) {
			logger.warn("UAC0082W: changePassword: User [{}] used incorrect old password, processing was stopped", getUser().email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, "Wrong old password.").buildResponse();
		} catch (ModelValidationException e) {
			logger.debug("UAC0083D: changePassword: Model validation exception: [{}], User.email [{}]", e.getMessage(), getUser().email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.warn("UAC0084W: changePassword: Data validation exception: [{}]. User.email [{}]", e.getMessage(), getUser().email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (AccessDeniedException e) {
			logger.debug("UAC0085E: changePassword: Unauthorized exception: [{}], User.email [{}]", e.getMessage(), getUser().email);
			return ResponseWrapper.with(Status.FORBIDDEN, false, e.getMessage()).buildResponse();
		}
	}

}
