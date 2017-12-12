package kz.aphion.adverts.persistence.adverts;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Embedded;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Объявление
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
@Entity("adverts.adverts")
@Converters(CalendarConverter.class)
public class Advert extends BaseEntity {

	/**
	 * Тип объявления, Глобальный разделитель
	 */
	@Property
	public AdvertType type;
	
	/**
	 * Подгруппа типа объявления
	 * Например 
	 *   для недвижимости: Квартиры, Дома, Офисы
	 *   для авто: легковые, грузовые и т.д.
	 *  Должен быть enum значение зависимости от типа объявления
	 */
	@Property
	public String subType;
	
	/**
	 * Вид операции выполняемый для подтипа объявления
	 * Например: Продажа или Аренда или Обмен для квартир, машин и вещей
	 */
	@Property
	public AdvertOperationType operation;
	
	/**
	 * Расположение объявления
	 */
	@Embedded
	public AdvertLocation location;
	
	/**
	 * Кастомные данные объявления 
	 */
	@Embedded
	public HashMap<String, Object> data;
	
	/**
	 * Информация об авторе объявления
	 */
	@Embedded
	public AdvertPublisher publisher;
	
	/**
	 * Информация об источнике объявления
	 */
	@Embedded
	public AdvertSource source;
	
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
	public AdvertStatus status;
	
	/**
	 * Флаг показывает есть ли фото у объявления
	 */
	@Property
	public Boolean hasPhoto;
	
	/**
	 * Коллекция фотографий
	 */
	@Embedded
	public List<AdvertPhoto> photos;
}
