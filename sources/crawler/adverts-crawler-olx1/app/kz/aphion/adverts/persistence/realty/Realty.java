package kz.aphion.adverts.persistence.realty;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Объявление о недвижимости
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Converters(CalendarConverter.class)
public abstract class Realty extends BaseEntity {

	/**
	 * Вид недвижимости
	 */
	@Property
	public RealtyType type;
	
	/**
	 * Тип операции
	 */
	@Property
	public RealtyOperationType operation;
	
	/**
	 * Цена
	 */
	@Property
	public Long price;
	
	/**
	 * Расположение объекта недвижимости
	 */
	@Embedded
	public RealtyLocation location;
	
	/**
	 * Пользователь опубликовавший объявление
	 */
	@Embedded
	public RealtyPublisher publisher;
	
	/**
	 * Источник объявления
	 */
	@Embedded
	public RealtySource source;
	
	
	/**
	 * Дата публикации объявления (берется время источников)
	 */
	@Property
	public Calendar publishedAt;
	
	/**
	 * Время когда истекает объявление,
	 * Считается что это поле будет регулярно использоваться в выборках.
	 * И даже в случае обновления информации это поле будет обновляться.
	 */
	@Property
	public Calendar expireAt;
	
	/**
	 * Время последнего обновления данных объявления
	 * Может изменяться в двух процессах:
	 * 1. Объявление попало в основной процесс crawler'a
	 * 2. Объявление было обновлено фоновым процессом обновления существующих данных
	 */
	@Property
	public Calendar lastUpdatedAt;
	
	/**
	 * Статус объявления
	 */
	@Property
	public RealtyAdvertStatus status;
	
	
	/**
	 * Флаг показывает есть ли фото у объявления
	 */
	@Property
	public Boolean hasPhoto;
	
	/**
	 * Коллекция фотографий
	 */
	@Embedded
	public List<RealtyPhoto> photos;
	
}
