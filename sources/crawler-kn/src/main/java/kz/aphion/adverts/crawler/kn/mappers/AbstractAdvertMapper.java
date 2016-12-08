package kz.aphion.adverts.crawler.kn.mappers;

import java.text.ParseException;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.kn.KnAdvertCategoryType;
import kz.aphion.adverts.crawler.kn.QueryBuilder;
import kz.aphion.adverts.persistence.SourceSystemType;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyAdvertStatus;
import kz.aphion.adverts.persistence.realty.RealtyLocation;
import kz.aphion.adverts.persistence.realty.RealtyPublisher;
import kz.aphion.adverts.persistence.realty.RealtySource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Абстрактный класс для конвертации различных типов объявлений о недвижимости
 * 
 * @author artem.demidovich
 *
 */
public abstract class AbstractAdvertMapper<T extends Realty> {

	private static Logger logger = LoggerFactory.getLogger(AbstractAdvertMapper.class);
	
	protected T realty;
	
	public AbstractAdvertMapper(T realty) {
		this.realty = realty;
	}
	
	public T mapAdvertObject(String advert, QueryBuilder queryBuilder, KnAdvertCategoryType advertType) throws ParseException, CrawlerException {
		
		realty.status = RealtyAdvertStatus.ACTIVE;
		
		realty.source = new RealtySource();
		realty.source.sourceType = SourceSystemType.KN;
		
		realty.location = new RealtyLocation();
		realty.publisher = new RealtyPublisher();
		
		
		mapAdvertData(advert, queryBuilder, advertType);
		
		return realty;
	}
	
	
	/**
	 * Абстрактный метод реализуемый в наследниках, так как объект DATA для всех видов объявлений разный
	 * @param advert
	 * @param queryBuilder 
	 * @throws ParseException 
	 */
	public abstract void mapAdvertData(String advert, QueryBuilder queryBuilder, KnAdvertCategoryType advertType) throws ParseException, CrawlerException;
	
}
