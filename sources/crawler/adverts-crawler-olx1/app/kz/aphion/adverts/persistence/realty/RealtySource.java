package kz.aphion.adverts.persistence.realty;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.SourceSystemType;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

/**
 * Источник объявления о недвижимости
 * 
 * @author artem.demidovich
 *
 */
@Entity
public class RealtySource extends BaseEntity {

	/**
	 * Внешний идентификатор объявления из системы источника
	 */
	@Property
	public String externalAdvertId;
	
	/**
	 * Ссылка на объявление
	 */
	@Property
	public String originalAdvertLink;
	
	/**
	 * Тип или информационная система источника 
	 */
	@Property
	public SourceSystemType sourceType;
	
	/**
	 * Версия данных в источнике информации
	 * На данный момент необходима только для krisha, 
	 * при реализации интеграции со следующими источниками будет ясно
	 */
	@Property
	public String sourceDataVersion;
	
}