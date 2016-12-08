package kz.aphion.adverts.persistence.crawler;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.CalendarConverter;

import org.mongodb.morphia.annotations.Converters;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Сущность описывающая Crawler'ов в группе.
 * Здесь описываются основные поведения каждого потока вычитывающего информацию с системы источника (донора)
 * Сохраняются состояния работы и обработки.
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.crawler")
@Converters(CalendarConverter.class)
public class Crawler extends BaseEntity {
	
	/**
	 * Название Crawler.
	 * Например: Выгрузка однокомнатных квартир.
	 * 			Выгрузка офисов и чего нить еще.
	 */
	@Property
	public String name;
	
	/**
	 * Алиас crawler для ведения логов.
	 * Например:
	 * 	[KRISHA-]ASTANA-1-ROOM, 
	 * 	[KRISHA-]KAZAKHSTAN-1-ROOM, 
	 * 	[KRISHA-]KAZAKHSTAN-1-2-ROOM,
	 */
	@Property
	public String alias;
	
	/**
	 * Статус Crawler'а
	 */
	@Property
	public CrawlerStatusEnum status;
	
	/**
	 * Crawler группа к которой относится данный параметр
	 */
	@Reference
	public CrawlerGroup crawlerGroup;
	
	/**
	 * Время запуска процесса обработки Crawler'a
	 */
	@Property
	public String runEvery;
	
	/**
	 * Последнее время активности данного Cralwer
	 */
	@Property
	public Calendar lastUsage;
	
	/**
	 * Время последнего успешного сканирование источника (донора).
	 * Предполагается, что при запуске потока Crawler'а, фиксируется время последней успешной обработки,
	 * для того чтобы можно было понимать до какого времени нужно запрашивать данные в следующий раз.
	 * Возможно необхдодимо хранить время источника (донора)
	 */
	@Property
	public Calendar lastSourceSystemScannedTime;
	
	/**
	 * Очередь куда нужно отправлять обработанные объявления
	 */
	@Property
	public String destinationQueueName;
	
	/**
	 * @TODO DELETE
	 * Список всех параметров Crawler'a
	 */
	@Embedded
	public List<CrawlerParameter> parameters;
	
}
