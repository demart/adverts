package kz.aphion.adverts.persistence.url;

import java.util.Calendar;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Класс сущность необходим для формирования коротких URL и посчета перехода по ним.
 * Пока клас считает только кол-во переходов и выполняет функцию shortener и не может отслеживать
 * всю историю переходов
 * 
 * @author artem.demidovich
 *
 * Created at Nov 13, 2017
 */
@Entity("adverts.url.shortener")
@Converters(CalendarConverter.class)
public class UrlShortener extends BaseEntity {

	/**
	 * URL ключ для сокращения 
	 */
	@Property
	public String key;
	
	/**
	 * Оригинальный URL для перехода
	 */
	@Property
	public String targetUrl;
	
	/**
	 * Количество переходов всего
	 */
	@Property
	public Integer counter;
	
	/**
	 * Время последнего перехода
	 */
	@Property
	public Calendar lastUsedTime;
	
	/**
	 * Время когда время жизни ссылки истечет  
	 */
	@Property
	public Calendar expirationTime;
	
	/**
	 * Источник создавший короткий линк
	 * Нужно бы превратить в ENUM или справочник
	 * 1) Рассылка EMAIL
	 * 2) Переходы на внешние сайты
	 * 3) Переходы внутри сайта
	 */
	@Property
	public String source;
	
	/**
	 * Идентификатор источника для отслеживания
	 */
	@Property
	public String sourceId;
	
}
