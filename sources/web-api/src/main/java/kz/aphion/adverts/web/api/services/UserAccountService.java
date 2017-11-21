package kz.aphion.adverts.web.api.services;

import java.io.IOException;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.jms.JMSException;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.notification.mq.models.NotificationEventMessage;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserAccessToken;
import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.request.EmailVerificationRequest;
import kz.aphion.adverts.persistence.users.request.ResetPasswordRequest;
import kz.aphion.adverts.web.api.builder.notification.EmailVerificationNotificationBuilder;
import kz.aphion.adverts.web.api.builder.notification.ResetPasswordNotificationBuilder;
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
import kz.aphion.adverts.web.api.models.users.AuthenticationResponseModel;
import kz.aphion.adverts.web.api.models.users.ChangePasswordRequestModel;
import kz.aphion.adverts.web.api.models.users.CreateUserAccountModel;
import kz.aphion.adverts.web.api.models.users.EmailVerificationModel;
import kz.aphion.adverts.web.api.models.users.ResetPasswordModel;
import kz.aphion.adverts.web.api.repositories.EmailVerificationRequestRepository;
import kz.aphion.adverts.web.api.repositories.ResetPasswordRequestRepository;
import kz.aphion.adverts.web.api.repositories.UserAccessTokenRepository;
import kz.aphion.adverts.web.api.repositories.UserRepository;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateException;


/**
 * Сервис по управлению учетной записи пользователя
 * @author artem.demidovich
 *
 * Created at Nov 15, 2017
 */
@Named
@RequestScoped
public class UserAccountService extends BaseSecuredService {

	private static Logger logger = LoggerFactory.getLogger(UserAccountService.class);
	
	@Inject
	UserRepository repository;
	
	public ResponseWrapper isLoginAvailable(String email) {
		if (repository.isUserLoginAvailable(email)) {
			return ResponseWrapper.with(Status.OK, true, "email is available");
		} else {
			return ResponseWrapper.with(Status.CONFLICT, false, "email is already used by another user");
		}
	}
	
	
	public ResponseWrapper createAccount(CreateUserAccountModel model) throws DataValidationException, IOException, TemplateException, JMSException, EmailAlreadyUsedException {
		
		// Создаем учетную запись пользователя
		User user = repository.createUser(model.type, model.channel, model.name, model.email, model.password, model.accessToken);
		
		// Создаем запрос на верификацию почты
		EmailVerificationRequest evr = new EmailVerificationRequestRepository().createRequest(user);

		// Создаем уведомление
		NotificationEventMessage message = new EmailVerificationNotificationBuilder().build(evr);
		
		// Отправляем уведомление в очередь
		MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_QUEUE.getValue(), message.toJson());
		
		return ResponseWrapper.with(Status.OK, true, "UserAccount was successfully created, verification email has been sent.");
	}
	
	
	public ResponseWrapper sendEmailVerificationRequest(EmailVerificationModel model) throws DataValidationException, IOException, TemplateException, JMSException, UserNotFoundException, ModelValidationException {
		
		// Достаем учетную запись пользователя
		User user = repository.getUser(model.email);
		
		// Только не верифицированные пользователи могут запрашивать подтерждение на почту
		if (user.status != UserStatus.NOT_VERIFIED)
			throw new ModelValidationException("User is not waiting for verification");
		
		// Создаем запрос на верификацию почты
		EmailVerificationRequest evr = new EmailVerificationRequestRepository().createRequest(user);
		
		// Создаем уведомление
		NotificationEventMessage message = new EmailVerificationNotificationBuilder().build(evr);
			
		// Отправляем уведомление в очередь
		MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_QUEUE.getValue(), message.toJson());
		
		return ResponseWrapper.with(Status.OK, true, "Email verification request successfuly sent.");		
	}
	
	
	public ResponseWrapper verifyEmail(EmailVerificationModel model) throws DataValidationException, TokenVerificationException, UserNotFoundException, TokenNotFoundException {
		
		// Get user record
		User user = repository.getUser(model.email);
		
		EmailVerificationRequestRepository requestRepository = new EmailVerificationRequestRepository();
		
		// Get the token
		EmailVerificationRequest request = requestRepository.getRequest(model.token);
		
		// Validate token & user status
		requestRepository.validateRequest(request, user);
		
		// Activate user
		repository.activeUser(user);
		
		requestRepository.markRequestAsUsed(request, user);
		
		return ResponseWrapper.with(Status.OK, null, "User verification email has been successfully verified");
	}
	
	
	
	public ResponseWrapper resetPassword(ResetPasswordModel model) throws DataValidationException, IOException, TemplateException, JMSException, UserNotFoundException {
		// Get the user
		User user = repository.getUser(model.email);
		
		// Add request to temporary table
		ResetPasswordRequest rpr = new ResetPasswordRequestRepository().createRequest(user);
		
		// Send Email notification with navigation link
		NotificationEventMessage message = new ResetPasswordNotificationBuilder().build(rpr);

		MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_QUEUE.getValue(), message.toJson());
				
		return ResponseWrapper.with(Status.OK, true, "Reset password email has been successfully sent");
	}
	
	
	public ResponseWrapper confirmResetPassword(ResetPasswordModel model) throws DataValidationException, TokenVerificationException, UserNotFoundException, TokenNotFoundException {

		// Get the user
		User user = repository.getUser(model.email);
		
		// Get and validate token
		ResetPasswordRequestRepository requestRepository = new ResetPasswordRequestRepository();
		ResetPasswordRequest request = requestRepository.getRequest(model.token);
		requestRepository.validateRequest(request, user);
	
		// Reset password to null
		repository.resetPassword(user);
		
		// Поидее нам нужно указать что этот токен заюзан
		// Но раз мы выдаем сессионный токен, то получается что нам нужно заставить пользователя
		// поменять пароль и тут можно либо оставить этот токен живой и попросить его прислать нам в процессе
		// смены пароля либо сбить пароль в null и получить форму смены пароля с пустым старым паролем
		// либо
		// указать что этот токен активирован, выдать сессионный токен, но указать статус пользователя
		// REQUIRE_CHANGE_PASSWORD!!!! и это будет означать что чувак при входе должен сменить пароль
		// и ничего больше делать не получиться, хотя тоже не очень, так как надо будет проверять везде статус
	
		// Create session token
		UserAccessToken uat = new UserAccessTokenRepository().generateNewAccessToken(user, false, "TBD");
		
		// Mark Token as used
		requestRepository.markRequestAsUsed(request, user);
		
		// Response model
		AuthenticationResponseModel rm = new AuthenticationResponseModel();
		rm.accessToken = uat.token;
		rm.expiresAt = uat.expiresAt;
		
		return ResponseWrapper.with(Status.OK, rm, "Operation is sucessfully proceded.");
	}
	
	

	/**
	 * Метод аутентификации пользователя.
	 * @param model
	 * @return
	 * @throws DataValidationException системная ошибка в случае если нашлось больше одного пользователя с логин
	 * @throws AccessDeniedException В случае если не правильные логин/пароль или статус
	 * @throws UserNotFoundException Если пользователя не нашли в базе
	 */
	public ResponseWrapper login(AuthenticationModel model) throws DataValidationException, AccessDeniedException, UserNotFoundException {
		// retrieve user
		User user = repository.getUser(model.email);
		
		// check user.status
		switch (user.status) {
			case BLOCKED:
			case SUSPENDED:
			case DELETED:
				logger.debug("UAS0100D: User.email (email) [{}] is in {} status. Access Denied.", model.email, user.status);
				throw new AccessDeniedException("User status is " + user.status + ".");
			default:
				logger.debug("UAS0100D: User.email (email) [{}] is {}.", model.email, user.status);
				break;
		}
		
		// check user.password
		String hex = DigestUtils.md5Hex(model.password);
		if (!hex.equals(user.password)) {
			logger.debug("UAS0101D: User.email [{}], wrong password. Access Denied.", model.email);
			throw new AccessDeniedException("Wrong credentials.");
		}
		
		// Update user last activity time
		repository.updateUserLastActivityTime(user, null);
		
		// Creating new session access token
		UserAccessToken uat = new UserAccessTokenRepository().generateNewAccessToken(user, model.rememberMe, model.source);
		
		AuthenticationResponseModel rm = new AuthenticationResponseModel();
		rm.accessToken = uat.token;
		rm.expiresAt = uat.expiresAt;
		
		return ResponseWrapper.with(Status.OK, rm, "Operation is sucessfully completed.");
	}
	
	
	/**
	 * Удаляем сессию пользователя
	 * @return
	 */
	public ResponseWrapper logout() {
		UserAccessToken uat = getUserAccessToken();
		if (uat == null) // No idea how it may happen
			return ResponseWrapper.with(Status.OK, null, "Operation succesfully completed.");
		
		DB.DS().delete(uat);
		
		return ResponseWrapper.with(Status.OK, null, "Operation succesfully completed.");
	}
	
	
	public ResponseWrapper changePassword(ChangePasswordRequestModel model) throws WrongPasswordException, DataValidationException, AccessDeniedException {
		User user = getUser();
		if (user == null) // No idea how it may happen
			throw new AccessDeniedException("User is not authorized");
		
		if (StringUtils.isBlank(model.oldPassword)) {
			// If it is new password after reset password operation
			if (StringUtils.isBlank(user.password)) {
				repository.changePassword(user, model.oldPassword, model.newPassword);
			} else {
				throw new DataValidationException("Attempt to change password wihout reset password operation");
			}
		} else {
			// If it is change password with new and old passwords
			if (StringUtils.isBlank(model.newPassword) || StringUtils.isBlank(user.password)) {
				throw new DataValidationException("Attempt to change password using when reset password operation was requested");
			} else {
				repository.changePassword(user, model.oldPassword, model.newPassword);
			}
		}
		
		return ResponseWrapper.with(Status.OK, null, "Request successfully completed");
	}
	
}
