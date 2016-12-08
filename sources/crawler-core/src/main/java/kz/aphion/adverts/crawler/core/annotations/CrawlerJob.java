package kz.aphion.adverts.crawler.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import kz.aphion.adverts.persistence.crawler.CrawlerSourceSystemTypeEnum;

/**
 * Аннотация для того чтобы сообщить системе что данный класс является Cralwer'ом.
 * Для того чтобы инициализатор Crawler потоков мог найти нужную группу
 * необходимо использовать значения из Enum классса с перечисленными crawler'ами.
 * 
 * @author artem.demidovich
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CrawlerJob {

	/**
	 * Указывает нужный источник Crawler для работы
	 * @return
	 */
	public CrawlerSourceSystemTypeEnum source();
	
}
