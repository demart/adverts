package kz.aphion.adverts.crawler.kn.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.persistence.CurrencyType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPhoto;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.types.MapName;
import kz.aphion.adverts.persistence.realty.types.MapType;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;
import org.jsoup.select.Elements;

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
		SimpleDateFormat knFormat = new SimpleDateFormat("dd.MM.yyyy");
		date = date.substring(date.lastIndexOf(":") + 2);
		Calendar publishedDate = Calendar.getInstance();
		publishedDate.setTimeZone(TimeZone.getTimeZone("Asia/Almaty"));
		publishedDate.setTime(knFormat.parse(date));
		publishedDate.set(Calendar.HOUR, 23);
		publishedDate.set(Calendar.MINUTE, 59);
		publishedDate.set(Calendar.SECOND, 59);
		
		return publishedDate;
	}

	/**
	 * Разделяем все возможноные телефоны
	 * @param stringWithPhones
	 * @return
	 */
	public static String[] convertPhonesNumber(String stringWithPhones) {
		String[] phones = stringWithPhones.split(",");
		return phones;
	}
	
	/**
	 * Конвертируем id объявления
	 * @param text
	 * @return
	 */
	public static String convertId(String text) {
		return text.substring(text.indexOf(": ") + 2);
	}
	
	/**
	 * Конвертирует изображения
	 * @param linkImages
	 * @return
	 */
	public static List<RealtyPhoto> convertPhotos(ArrayList<String> linkImages) {
		List<RealtyPhoto> photos = new ArrayList<RealtyPhoto>();
			
		for (int i = 0; i < linkImages.size(); i++) {
			RealtyPhoto photo = new RealtyPhoto();
			photo.path = linkImages.get(i);
			photos.add(photo);
		}
		
		return photos;
	}

	/**
	 * Выдергиваем к какому региону относится объявление
	 * @param value
	 * @return
	 */
	public static String getRegionName (String value) {
		String geoName = null;
		//Возможны варианты:
		//1. Акм область, Астана
		//2. Астана, Сарыарка
		//3. Усть-Каменогорск
		if (value.indexOf(",") > 0) {
			if (value.indexOf("область") > 0) {
				geoName = value.substring(value.indexOf(", ") + 2);
			}
			
			else {
				geoName = value.substring(value.indexOf(", ") + 2);
				if (geoName.equals("Алматы"))
					geoName = "Алматинский р-н";
				if (geoName.equals("Есиль"))
					geoName = "Есильский р-н";
			}
		}
		
		else {
			geoName = value;
		}
		
		return geoName;
	}

	/**
	 * Выдергиваем название улицы
	 * @param text
	 * @return
	 */
	public static String getStreetName(String text) {
		String streetName;
		streetName =  text.substring(text.lastIndexOf("м,") + 2);
		if (streetName.lastIndexOf(", дом") > 0) {
			streetName = streetName.substring(0, streetName.lastIndexOf(","));
		}
		return streetName;
	}

	/**
	 * Выдергиваем номер дома
	 * @param text
	 * @return
	 */
	public static String getHouseNumber(String text) {
		String houseNumber = null;
		text = 	text.substring(text.lastIndexOf("м,") + 2);
		if (text.lastIndexOf(", дом") > 0) {
			houseNumber = text.substring(text.lastIndexOf(",") + 5);
		}
		return houseNumber;
	}

	/**
	 * Выдергиваем имя
	 * @param text
	 * @return
	 */
	public static String getRealtorName(String text) {
		String name = null;
		if (text.length() > 0)
			name = text;
		return name;
	}
	
}
