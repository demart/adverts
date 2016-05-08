package kz.aphion.adverts.crawler.core.models;

import java.util.Calendar;
import java.util.List;

import org.mongodb.morphia.annotations.AlsoLoad;

import kz.aphion.adverts.crawler.entity.CrawlerStatusEnum;

/**
 * Модель описания Crawler
 * 
 * @author artem.demidovich
 *
 */
public class CrawlerModel {

	public Long id;
	
	/**
	 * Название Crawler.
	 * Например: Выгрузка однокомнатных квартир.
	 * 			Выгрузка офисов и чего нить еще.
	 */
	public String name;
	
	/**
	 * Алиас crawler для ведения логов.
	 * Например:
	 * 	[KRISHA-]ASTANA-1-ROOM, 
	 * 	[KRISHA-]KAZAKHSTAN-1-ROOM, 
	 * 	[KRISHA-]KAZAKHSTAN-1-2-ROOM,
	 */
	public String alias;
	
	/**
	 * Статус Crawler'а
	 */
	public CrawlerStatusEnum status;
	
	/**
	 * Crawler группа к которой относится данный параметр
	 */
	public CrawlerGroupModel crawlerGroup;
	
	/**
	 * Время запуска процесса обработки Crawler'a
	 */
	public String runEvery;
	
	/**
	 * Последнее время активности данного Cralwer
	 */
	public Calendar lastUsage;
	
	/**
	 * Время последнего успешного сканирование источника (донора).
	 * Предполагается, что при запуске потока Crawler'а, фиксируется время последней успешной обработки,
	 * для того чтобы можно было понимать до какого времени нужно запрашивать данные в следующий раз.
	 * Возможно необхдодимо хранить время источника (донора)
	 */
	public Calendar lastSourceSystemScannedTime;
	
	/**
	 * Очередь куда нужно отправлять обработанные объявления
	 */
	public String destinationQueueName;
	
	/**
	 * Список только активных параметров
	 */
	public List<CrawlerParameterModel> parameters;
	
	
	/**
	 * Возвращает полное имя краулера (уникальный алиас)
	 * @return
	 */
	public String getCralwerFullAlias() {
		return crawlerGroup.alias + ":" + alias;
	}

}
