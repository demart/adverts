package kz.aphion.adverts.persistence.crawler;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Сущность для хранения информации о прокси серверах для скрытого посещения доноров
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.crawler.proxies")
@Converters(CalendarConverter.class)
public class ProxyServer extends BaseEntity {
	
	/**
	 * Название прокси сервера
	 */
	@Property
	public String name;

	/**
	 * Описание прокси сервера
	 */
	@Property
	public String description;
	
	/**
	 * Хост прокси сервера
	 */
	@Property
	public String host;
	
	/**
	 * Порт прокси сервера
	 */
	@Property
	public Integer port;
	
	/**
	 * Тип авторизации прокси сервера
	 */
	@Property
	public ProxyServerAuthTypeEnum authType;
	
	/**
	 * логин авторизации прокси сервера
	 */
	@Property
	public String login;
	
	/**
	 * Пароль прокси сервера
	 */
	@Property
	public String password;

	/**
	 * Статус прокси сервера
	 */
	@Property
	public ProxyServerStatusEnum status;
	
	/**
	 * Недоступность сервера с этого времени
	 */
	@Property
	public Calendar inactiveFrom;
	
	/**
	 * Последная проверка прокси сервера
	 */
	@Property
	public Calendar lastCheck;
	
	/**
	 * Последнее использование прокси сервера
	 */
	@Property
	public Calendar lastUsage;
	
	/**
	 * Кол-во использований прокси сервера
	 */
	@Property
	public Long usageCount;
	
	/**
	 * Тип прокси сервера
	 */
	@Property
	public ProxyServerTypeEnum type;

}
