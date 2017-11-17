package kz.aphion.adverts.web.api.models.users;

import kz.aphion.adverts.persistence.users.UserRegistrationChannel;
import kz.aphion.adverts.persistence.users.UserType;

/**
 * Модель создания нового аккаунта пользователя
 * @author artem.demidovich
 *
 * Created at Nov 16, 2017
 */
public class CreateUserAccountModel {

	public String name;
	
	public UserRegistrationChannel registrationChannel;
	
	public String accessToken;
	
	public String email;
	
	public String password;
	
	public String phone;
	
	public UserType type;
	
}
