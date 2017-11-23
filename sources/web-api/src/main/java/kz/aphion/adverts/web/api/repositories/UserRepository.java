package kz.aphion.adverts.web.api.repositories;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.persistence.users.UserRegistrationChannel;
import kz.aphion.adverts.persistence.users.UserStatus;
import kz.aphion.adverts.persistence.users.UserType;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.EmailAlreadyUsedException;
import kz.aphion.adverts.web.api.exceptions.RecordNotFoundException;
import kz.aphion.adverts.web.api.exceptions.WrongPasswordException;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Репозитрий для работы с записями пользователей
 * 
 * @author artem.demidovich
 *
 * Created at Nov 20, 2017
 */
public class UserRepository {
	
	private static Logger logger = LoggerFactory.getLogger(UserRepository.class);
	
	/**
	 * Метод выполянет поиск в базе указанного пользователя. 
	 * @param email
	 * @return запись пользователя
	 * @throws DataValidationException Если по какой-то причине оказалось две записи с одним email
	 * @throws RecordNotFoundException Если пользователь не найден
	 */
	public User getUser(String email) throws DataValidationException, RecordNotFoundException  {
		List<User> users = DB.DS().createQuery(User.class).field("email").equalIgnoreCase(email).asList();
		
		if (users == null || users.size() == 0) {
			throw new RecordNotFoundException("User not found");
		}
		
		if (users.size() > 1) {
			// TODO Strange 
			logger.error("USRR001E: Error, found more than one email [{}], which should be unqiue!", email);
			throw new DataValidationException("Duplicate user records found!");
		}
		
		User user = users.get(0);
		if (user == null) {
			logger.error("USRR002E: Error, user email [{}] is null in non null collection!", email);
			throw new DataValidationException("User user not found!");
		}
		
		return user;
	}
	
	
	/**
	 * Создает учетку пользователя
	 * @param type Тип пользователя
	 * @param channel Канал регистрации пользователя (местный или соц. сеть)
	 * @param name Имя пользователя
	 * @param email уникальный идентификатор
	 * @param password пароль
	 * @return Учетную запись пользователя со статусом NOT_VEFIFIED
	 * @throws DataValidationException В случае попытки использовать существующий email или социальную регистрацию без социального токена 
	 * @throws EmailAlreadyUsedException 
	 */
	public User createUser(UserType type, UserRegistrationChannel channel, String name, String email, String password, String accessToken) throws DataValidationException, EmailAlreadyUsedException {

		if (!isUserLoginAvailable(email))
			throw new EmailAlreadyUsedException("User login already exists.");
				
		User user = new User();

		user.type = type == null ? UserType.USER : type;
		user.channel = channel == null ? UserRegistrationChannel.STANDARD : channel;
		user.company = null;
		user.name = name;
		user.email = email;
		
		if (user.channel == UserRegistrationChannel.STANDARD) {
			user.password = DigestUtils.md5Hex(password);
		} else {
			
			if (StringUtils.isBlank(accessToken))
				throw new DataValidationException("Not found social access token which is required for social registration");

			user.accessToken = accessToken;
		}
		
		user.status = UserStatus.NOT_VERIFIED;
		user.created = Calendar.getInstance();
		user.updated = user.created;
		user.lastActivityTime = user.created;
		
		DB.DS().save(user);
		
		return user;
	}

	
	/**
	 * Обновляет у пользователя время последней активности
	 * @param user
	 * @param lastAcitivyTime
	 */
	public void updateUserLastActivityTime(User user, Calendar lastAcitivyTime) {
		lastAcitivyTime = lastAcitivyTime == null ? Calendar.getInstance() : lastAcitivyTime;
		
		// Update database
		Query<User> query = DB.DS().createQuery(User.class).field("id").equal(user.id);
		UpdateOperations<User> ops = DB.DS().createUpdateOperations(User.class).set("lastActivityTime", new CalendarConverter().encode(lastAcitivyTime));
		DB.DS().update(query, ops);
		
		// Update field locally
		user.lastActivityTime = Calendar.getInstance();
	}
	
	/**
	 * Активация пользователя происходит после валиадации почты 
	 * @param user
	 */
	public void activeUser(User user) {
		user.status = UserStatus.ACTIVE;
		user.updated = Calendar.getInstance();
		user.modifier = user.email;
		DB.DS().merge(user);
	}

	
	/**
	 * Метод проверяет есть ли указанный емаил в базе пользователей или нет
	 * @param email
	 * @return
	 */
	public boolean isUserLoginAvailable(String login) {
		long found = DB.DS().createQuery(User.class).field("email").equalIgnoreCase(login).countAll();
		return found > 0 ? false : true;
	}
	
	/**
	 * Сбрасываем пароль на null
	 * @param user
	 */
	public void resetPassword(User user) {
		// Update user
		user.lastActivityTime = Calendar.getInstance();
		user.password = null; // Сбрасываем для того чтобы при входе сразу его заменить, а также для нас указатель
		DB.DS().merge(user);
	}
	
	/**
	 * Change password, checks if old password is equals with stored password in db.
	 * @param user
	 * @param oldPassword
	 * @param newPassword
	 * @throws WrongPasswordException If oldPassword doesn't match with stored password in db 
	 */
	public void changePassword(User user, String oldPassword, String newPassword) throws WrongPasswordException {
		
		// Check if exists old password
		if (!StringUtils.isBlank(oldPassword)) {
			String oldPasswordHex = DigestUtils.md5Hex(oldPassword);
			if (!StringUtils.equals(user.password, oldPasswordHex))
				throw new WrongPasswordException("Old password is incorrect");
		}
		
		// Update password field in database
		String newPasswordHex = DigestUtils.md5Hex(newPassword);
		Query<User> query = DB.DS().createQuery(User.class).field("id").equal(user.id);
		UpdateOperations<User> ops = DB.DS().createUpdateOperations(User.class).set("password", newPasswordHex);
		DB.DS().update(query, ops);
		
		// update local cached instance
		user.password = newPasswordHex;
	}


}
