package kz.aphion.adverts.web.api.repositories;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.request.ResetPasswordRequest;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.TokenNotFoundException;
import kz.aphion.adverts.web.api.exceptions.TokenVerificationException;

/**
 * Класс репозиторий для работы с запросами на смену пароля
 * 
 * @author artem.demidovich
 *
 * Created at Nov 21, 2017
 */
public class ResetPasswordRequestRepository {

	public static final int RESET_PASSWORD_REQUEST_EXPIRATION_TIME_IN_HOURS = 2;
	
	/**
	 * Создает запрос на сброс пароля в базе и вовзращает его для дальнейшей работы
	 * @param email - not null
	 * @return
	 */
	public ResetPasswordRequest createRequest(User user) {
		
		ResetPasswordRequest rpr = new ResetPasswordRequest();
		rpr.token = UUID.randomUUID().toString();
		rpr.created = Calendar.getInstance();
		rpr.updated = Calendar.getInstance();
		rpr.user = user;
		rpr.used = false;
		rpr.usedTime = null;
		
		// Set expirationTime in 2 hours
		rpr.expiresAt = Calendar.getInstance();
		rpr.expiresAt.add(Calendar.HOUR, RESET_PASSWORD_REQUEST_EXPIRATION_TIME_IN_HOURS); // TODO Move to configuration
		
		if (user.resetPasswordRequests == null)
			user.resetPasswordRequests = new ArrayList<ResetPasswordRequest>();
		user.resetPasswordRequests.add(rpr);
		
		DB.DS().save(rpr);
		DB.DS().merge(user);
		
		return rpr;
	}
	
	/**
	 * Метод выполняет поиск в базе по указанному токену
	 * @param token
	 * @return Возвращает токен
	 * @throws DataValidationException Найдены дубликаты
	 * @throws TokenNotFoundException Если токен не найден
	 */
	public ResetPasswordRequest getRequest(String token) throws DataValidationException, TokenNotFoundException {
		List<ResetPasswordRequest> tokenRequests = DB.DS().createQuery(ResetPasswordRequest.class).field("token").equalIgnoreCase(token).asList();
		if (tokenRequests == null || tokenRequests.size() == 0) {
			throw new TokenNotFoundException("Reset password request not found");
		}
		
		if (tokenRequests.size() > 1) {
			throw new DataValidationException("Found more than one reset password request!");
		}
		
		return tokenRequests.get(0);
	}
	
	
	public void validateRequest(ResetPasswordRequest request, User user) throws TokenVerificationException {
		
		if (request.used)
			throw new TokenVerificationException("Reset password token already used");
		
		if (request.expiresAt == null) {
			
			request.used = true;
			request.usedTime = Calendar.getInstance();
			DB.DS().merge(request);
			
			throw new TokenVerificationException("Reset password token without expiredAt date");
		}
		
		if (Calendar.getInstance().compareTo(request.expiresAt) > 0)
			throw new TokenVerificationException("Reset password token is expired");
		
		if (request.user == null) {

			request.used = true;
			request.usedTime = Calendar.getInstance();
			DB.DS().merge(request);
			
			throw new TokenVerificationException("Reset password token without associated user");
		}
		
		if (!user.id.equals(request.user.id))
			throw new TokenVerificationException("Reset password token doesn't belog to submited user");
		
	}

	/**
	 * Помечает токен как использованный
	 * @param request
	 * @param user
	 */
	public void markRequestAsUsed(ResetPasswordRequest request, User user) {
		request.used = true;
		request.usedTime = Calendar.getInstance();
		request.updated = Calendar.getInstance();
		request.modifier = user.email;
		DB.DS().merge(request);		
	}
}
