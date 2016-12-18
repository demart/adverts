package kz.aphion.adverts.common.models.mq.phones;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.phones.PhoneOwner;
import kz.aphion.adverts.persistence.phones.PhoneSource;
import kz.aphion.adverts.persistence.phones.PhoneSourceCategory;


/**
 * Модель регистрации телефонного номера при передачи через систему очередей
 * 
 * @author artem.demidovich
 *
 */
public class RegisterPhoneModel {
	
	/**
	 * Номер телефона
	 */
	public List<String> phone;
	
	/**
	 * Идентификатор региона
	 */
	public Region region;
	
	/**
	 * Регионы вхождения
	 */
	public List<Region> regions;
	
	/**
	 * Время когда был зафиксирован телефон
	 */
	public Calendar time;
	
	/**
	 * Источник получения телефона
	 */
	public PhoneSource source;
	
	/**
	 * Категория объявления или контента где засветился телефон
	 */
	public PhoneSourceCategory category;
	
	/**
	 * Тип пользователя указавшего телефон
	 */
	public PhoneOwner owner;	
	
}
