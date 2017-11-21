package kz.aphion.adverts.persistence.users;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Сущность для хранения токентов аутентификации в системе из различных источников
 * @author artem.demidovich
 *
 * Created at Nov 16, 2017
 */
@Entity("adverts.users.tokens")
@Converters(CalendarConverter.class)
public class UserAccessToken extends BaseEntity  {

	/**
	 * Токен использованный для доступа (уникальный по идее)
	 */
	@Property
	public String token;
	
	/**
	 * Ссылка на пользователя для которого используется этот токен аутентификации
	 */
	@Reference
	public User user;
	
	/**
	 * Какой IP адрес ипользовался при создании записи
	 */
	@Property
	public String createdIP;
	
	/**
	 * Время когда токен использовался последний раз
	 */
	@Property
	public Calendar lastActivityTime;
	
	/**
	 * Какой IP адрес ипользовался последний раз
	 */
	@Property
	public String lastActivityIP;
	
	/**
	 * Время когда истекает токен
	 */
	@Property
	public Calendar expiresAt;
	
	/**
	 * Источник который авторизовался (Web Browser, Mobile App, whatever)
	 */
	@Property
	public String source;
	
	/**
	 * Живой ли еще токен или уже все, вычилсяемое поле
	 * @return
	 */
	public boolean isExpired() {
		if (Calendar.getInstance().compareTo(expiresAt) > 0) {
			return true;
		}
		return false;
	}
}
