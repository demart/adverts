package kz.aphion.adverts.web.api.repositories;

import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserAccessToken;

import org.apache.commons.lang.StringUtils;

import freemarker.template.utility.NullArgumentException;

/**
 * Репозиторий для работы с токенами доступа в систему
 * @author artem.demidovich
 *
 * Created at Nov 19, 2017
 */
public class UserAccessTokenRepository {

	/**
	 * Время жизни сессии если указали запомни меня
	 */
	public static final int REMEMBERME_SESSION_LIFETIME_IN_YEARS = 1; // Years
	
	/**
	 * Время жизни сессии если не указывали запмони меня
	 */
	public static final int DEFAULT_SESSION_LIFETIME_IN_MINUTES = 60; // Minutes
	
	/**
	 * Создает новый токен авторизации для указанного пользователя
	 * @param user пользователь
	 * @param rememberMe нужно ли создать долгоживущий токен
	 * @param source источник отправиший запрос
	 * @return
	 */
	public UserAccessToken generateNewAccessToken(User user, Boolean rememberMe, String source) {
		UserAccessToken uat = new UserAccessToken();
		uat.token = UUID.randomUUID().toString();
		uat.user = user;

		uat.expiresAt = Calendar.getInstance(); 
		if (rememberMe != null && rememberMe == true) {
			uat.expiresAt.add(Calendar.YEAR, REMEMBERME_SESSION_LIFETIME_IN_YEARS);
		} else {
			uat.expiresAt.add(Calendar.MINUTE, DEFAULT_SESSION_LIFETIME_IN_MINUTES);
		}
		
		uat.lastActivityIP = ""; // TODO Add client IP (take from header
		uat.lastActivityTime = Calendar.getInstance();
		
		uat.source = source;
		
		uat.created = Calendar.getInstance();
		uat.createdIP = ""; // TODO Add client IP (take from header
		uat.modifier = user.email;
		uat.updated = Calendar.getInstance();
		
		DB.DS().save(uat);
		
		return uat;
	}
	
	
	/**
	 * Метод выполняет поиск токена доступа
	 * @param token токен доступа
	 * @return
	 * @throws Exception в случае нахождения более одной записи в базе с тем же самым токеном 
	 */
	public UserAccessToken getAccesToken(String token) throws Exception {
		if (StringUtils.isBlank(token))
			throw new NullArgumentException("token agrument is null");
		
		 List<UserAccessToken> tokens = DB.DS().createQuery(UserAccessToken.class).field("token").equal(token).asList();
	
		if (tokens == null || tokens.size() == 0)
			return null;
		
		if (tokens.size() > 1) {
			throw new Exception("Found more than 1 UserAccessToken, check the token: " + token);
		}
		
		return tokens.get(0);
	}
	
	
	public void prolongateSessionLifeTime(UserAccessToken uat) {
		// Prolongate session
		Calendar lastActivityTime = uat.lastActivityTime;
		uat.lastActivityTime = Calendar.getInstance();
		Long secondsDiff = ChronoUnit.SECONDS.between(lastActivityTime.toInstant(), uat.lastActivityTime.toInstant());
		uat.expiresAt.add(Calendar.SECOND, secondsDiff.intValue());
		DB.DS().merge(uat);
		
		// Set last activity time for user accounts
		uat.user.lastActivityTime = uat.lastActivityTime;
		DB.DS().merge(uat.user);

	}
	
}
