package kz.aphion.adverts.crawler.olx.mappers;

import java.util.Calendar;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.olx.OlxAdvertCategoryType;
import kz.aphion.adverts.crawler.olx.OlxDataManager;
import kz.aphion.adverts.crawler.olx.persistence.OlxRegion;
import kz.aphion.adverts.persistence.realty.Realty;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Функции общие для разбора объявлений
 * 
 * @author artem.demidovich
 *
 * Created at May 20, 2016
 */
public class CommonMapperUtils {

	private static Logger logger = LoggerFactory.getLogger(CommonMapperUtils.class);

	
	public static OlxAdvertCategoryType parseAdvertCategoryType(String categoryIdStr) throws CrawlerException {
		Integer categoryId = Integer.parseInt(categoryIdStr);
		for (OlxAdvertCategoryType type : OlxAdvertCategoryType.values()) {
			if (type.getValue() == categoryId)
				return type;
		}
		throw new CrawlerException("Enum value for presented category_id [" + categoryIdStr + "] not found. Please check maybe appeared new category on olx sdie"); 
	}
	
	
	/**
	 * Находит регион OLX по указанному имени и сопоставляет с общим справочником регионов
	 * @param realty Объхект недивжимости
	 * @param regionEntry Название региона
	 * @throws CrawlerException 
	 */
	public static void convertRegion(Realty realty, Entry<String, Object> regionEntry) throws CrawlerException {
		String regionName = (String)regionEntry.getValue();
		OlxRegion olxRegion = OlxDataManager.getOlxRegion(regionName);
		if (olxRegion == null)
			throw new CrawlerException("Can't find olx region by name [" + regionName + "]");
		
		realty.location.externalRegionId = regionName;
		realty.location.region = OlxDataManager.getRegion(olxRegion.region);
		realty.location.regions = OlxDataManager.getRegionsTree(olxRegion.region);
	}
	
	
	/**
	 * Конвертирует специфичное отображение даты на сайте OLX в Calendar
	 * Даты могут быть вида
	 * "Сегодня 01:24",
	 * "18  май",
	 * "Вчера 13:19",
	 * 
	 * @param dateString
	 * @return
	 * @throws CrawlerException 
	 */
	public static Calendar convertCreatedDate(String dateString) throws CrawlerException {
		if (StringUtils.isBlank(dateString))
			throw new CrawlerException("Advert created date field is empty");
		
		Calendar createdDate = Calendar.getInstance();
		createdDate.set(Calendar.HOUR_OF_DAY, 0);
		createdDate.set(Calendar.MINUTE, 0);
		createdDate.set(Calendar.SECOND, 0);
		createdDate.set(Calendar.MILLISECOND, 0);
		
		if (dateString.contains("Сегодня") || dateString.contains("сегодня")) {
			// Сегодня
			String timeStr = dateString
					.replace("Сегодня", "")
					.replace("сегодня", "")
					.trim();
			String[] time = timeStr.split(":");
			//Logger.info("Advert created date was [%s]", createdDate.getTime().toString());
			createdDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
			createdDate.set(Calendar.MINUTE, Integer.parseInt(time[1]));
			//Logger.info("Advert created date now [%s]", createdDate.getTime().toString());
		} else {
			if (dateString.contains("Вчера") || dateString.contains("вчера")) {
				// Вчера
				String timeStr = dateString
						.replace("Вчера", "")
						.replace("вчера", "")
						.trim();
				String[] time = timeStr.split(":");
				createdDate.add(Calendar.DATE, -1); // Потому что вчера
				createdDate.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time[0]));
				createdDate.set(Calendar.MINUTE, Integer.parseInt(time[1]));
			} else {
				// Число + месяц
				dateString = dateString.trim();
				Integer date = Integer.parseInt(dateString.substring(0, dateString.indexOf(" ")));
				String month = dateString.substring(dateString.lastIndexOf(" ")+1, dateString.length());
				
				logger.info("ATTENTION: Created date ["+ dateString +"] was splitted by day [" + date.toString()  +"] and month [" + month + "]");
				
				createdDate.set(Calendar.DATE, date);

				switch (month.replace(".", "").toLowerCase()) {
				case "янв":
					createdDate.set(Calendar.MONTH, 0);
					break;
				case "фев":
					createdDate.set(Calendar.MONTH, 1);
					break;
				case "мар":
					createdDate.set(Calendar.MONTH, 2);
					break;
				case "апр":
					createdDate.set(Calendar.MONTH, 3);
					break;	
				case "май":
					createdDate.set(Calendar.MONTH, 4);
					break;
				case "июн":
					createdDate.set(Calendar.MONTH, 5);
					break;
				case "июл":
					createdDate.set(Calendar.MONTH, 6);
					break;
				case "авг":
					createdDate.set(Calendar.MONTH, 7);
					break;
				case "сен":
					createdDate.set(Calendar.MONTH, 8);
					break;
				case "окт":
					createdDate.set(Calendar.MONTH, 9);
					break;
				case "ноя":
					createdDate.set(Calendar.MONTH, 10);
					break;
				case "дек":
					createdDate.set(Calendar.MONTH, 11);
					break;
				default:
					// Месяцы
					// PARSIT не можем пока это добро так как они специально мутят фигню какую-то
					// 18 Май
					// 20 апр.
					// Так хрен разберешь, причем возвращается только в API, на сайте внутри объявления норм....
					logger.warn("WARINING, Advert created date [%s] cannot be parsed", dateString);
					throw new CrawlerException("Can't parse created date [" + dateString + "]");
				}
			}
		}
		
		//Logger.info("Advert createDate [" + dateString + "] converted to: " + createdDate.getTime().toLocaleString());
		
		return createdDate;
	}


	/**
	 * Конвертирует цену в заголовке в цену объявления
	 * "list_label": "26 000 000 тг.",
	 * @param priceLabel
	 * @return
	 */
	public static Long convertPrice(String priceLabel) {
		String priceLabelPrepared = priceLabel.replace("тг.", "")
				.replace(" ", "").trim();
		// TODO POTENTIAL ISSUE
		if (priceLabelPrepared.indexOf(".") > 0) {
			priceLabelPrepared = priceLabelPrepared.substring(0, priceLabelPrepared.indexOf("."));
			logger.warn("Advert price was with [.] before [" + priceLabel + "] now [" + priceLabelPrepared + "]");
		}
		return Long.parseLong(priceLabelPrepared);
	}
	
	
}
