package kz.aphion.adverts.crawler.irr.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.irr.IrrAdvertCategoryType;
import kz.aphion.adverts.persistence.CurrencyType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.types.MapName;
import kz.aphion.adverts.persistence.realty.types.MapType;

import org.joda.time.DateTime;

import play.Logger;


/**
 * Класс с методами для конвертации общий (повторяющихся) частей в объявлениях крыши.
 * Например: фото, сервис данные, служебные данные и так далее
 * @author artem.demidovich
 *
 */
public class CommonMapperUtils {
	
	/**
	 * Конвертируем время публикации объявления
	 * @param date
	 * @return
	 * @throws ParseException
	 */
	public static Calendar convertAddDate (String date) throws ParseException {
		//Формат времени на kn.kz
		SimpleDateFormat irrFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar publishedDate = Calendar.getInstance();
		publishedDate.setTime(irrFormat.parse(date));
		
		return publishedDate;
	}

	public static IrrAdvertCategoryType parseAdvertCategoryType(String categoryIdStr) throws CrawlerException {
		for (IrrAdvertCategoryType type : IrrAdvertCategoryType.values()) {
			if (type.getValue().equals(categoryIdStr))
				return type;
		}
		throw new CrawlerException("Enum value for presented category_id [" + categoryIdStr + "] not found. Please check maybe appeared new category on irr sdie"); 
	}
}
