package kz.aphion.adverts.persistence.adverts;

import kz.aphion.adverts.persistence.SourceSystemType;

import org.mongodb.morphia.annotations.Property;

/**
 * Мета информация об источнике объявления 
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public class AdvertSource {

	/**
	 * Тип или информационная система источника 
	 */
	@Property
	public SourceSystemType type;
	
	/**
	 * Внешний идентификатор объявления из системы источника
	 */
	@Property
	public String externalId;
	
	/**
	 * Ссылка на объявление
	 */
	@Property
	public String originalLink;
	
	/**
	 * Версия данных в источнике информации
	 * На данный момент необходима только для krisha, 
	 * при реализации интеграции со следующими источниками будет ясно
	 */
	@Property
	public String dataVersion;
}
