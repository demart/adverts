package kz.aphion.adverts.web.api.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.request.EmailVerificationRequest;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.TokenNotFoundException;
import kz.aphion.adverts.web.api.exceptions.TokenVerificationException;

/**
 * Класс репозитория по работе с запросами на проверку почтового адреса
 * @author artem.demidovich
 *
 * Created at Nov 21, 2017
 */
public class EmailVerificationRequestRepository {

	
	public static final int EMAIL_VERIFICATION_REQUEST_EXPIRATION_TIME_IN_HOURS = 2;
	
	/**
	 * Метод создает запрос на проверку почтового адреса, добавляет в коллецию всех запросов пользователя и 
	 * сохраняет обновленный объект в базу данных
	 * @param user
	 * @return
	 */
	public EmailVerificationRequest createRequest(User user) {
		EmailVerificationRequest evr = new EmailVerificationRequest();
		
		evr.token = UUID.randomUUID().toString();
		evr.created = Calendar.getInstance();
		evr.updated = Calendar.getInstance();
		evr.user = user;
		evr.used = false;
		evr.usedTime = null;
		
		// Set expirationTime in 2 hours
		evr.expiresAt = Calendar.getInstance();
		evr.expiresAt.add(Calendar.HOUR, EMAIL_VERIFICATION_REQUEST_EXPIRATION_TIME_IN_HOURS); // TODO Move to configuration
		
		if (user.emailVerificationRequests == null)
			user.emailVerificationRequests = new ArrayList<EmailVerificationRequest>();
		user.emailVerificationRequests.add(evr);
		
		DB.DS().save(evr);
		DB.DS().merge(user); 
		
		return evr;
	}
	
	
	/**
	 * Метод выполняет поиск в базе по указанному токену
	 * @param token
	 * @return Возвращает токен
	 * @throws DataValidationException Найдены дубликаты
	 * @throws TokenNotFoundException Если токен не найден или 
	 */
	public EmailVerificationRequest getRequest(String token) throws DataValidationException, TokenNotFoundException {
		List<EmailVerificationRequest> tokenRequests = DB.DS().createQuery(EmailVerificationRequest.class).field("token").equal(token).asList();
		if (tokenRequests == null || tokenRequests.size() == 0) {
			throw new TokenNotFoundException("Email validation request not found");
		}
		
		if (tokenRequests.size() > 1) {
			throw new DataValidationException("Found more than one email validation request!");
		}
		
		return tokenRequests.get(0);
	}


	public void validateRequest(EmailVerificationRequest request, User user) throws TokenVerificationException, DataValidationException {
		
		if (request.used)
			throw new TokenVerificationException("Email verificaion token already used");
		
		if (request.expiresAt == null) {
			
			request.used = true;
			request.usedTime = Calendar.getInstance();
			DB.DS().merge(request);
			
			throw new TokenVerificationException("Email verification token without expiredAt date");
		}
		
		if (Calendar.getInstance().compareTo(request.expiresAt) > 0)
			throw new TokenVerificationException("Email verification token is expired");
		
		if (request.user == null) {

			request.used = true;
			request.usedTime = Calendar.getInstance();
			DB.DS().merge(request);
			
			throw new TokenVerificationException("Email verification token without associated user");
		}
		
		if (!user.id.equals(request.user.id))
			throw new TokenVerificationException("Email verification token doesn't belog to submited user");
	
		if (user.status != UserStatus.NOT_VERIFIED)
			throw new DataValidationException("User is not in status NOT_VERIFIED, check the business logic");
	}
	
	/**
	 * Помечает токен для валидации почты использованным
	 * @param request токен
	 * @param email 
	 */
	public void markRequestAsUsed(EmailVerificationRequest request, User user) {
		request.used = true;
		request.usedTime = Calendar.getInstance();
		request.updated = Calendar.getInstance();
		request.modifier = user.email;
		DB.DS().merge(request);
	}
	
}
