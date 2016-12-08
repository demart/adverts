package kz.aphion.adverts.crawler.krisha.persistence;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.RegionType;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Регионы крыши
 * 
 * @author artem.demidovich
 *
 */
@Entity(value="adverts.regions.krisha")
public class KrishaRegion extends BaseEntity {
	
	/**
	 * Название региона
	 */
	@Property
	public String name;

	/**
	 * Название региона
	 */
	@Property
	public String alias;
	
	/**
	 * Тип региона
	 */
	@Property
	public RegionType type;
	
	/**
	 * Уровень вложенности
	 */
	@Property
	public String level;

	/**
	 * Ссылка на регион
	 */
	@Property
	public Long key;	
	
	/**
	 * Ссылка на регион
	 */
	@Reference
	public Region region;
	
	/**
	 * Приближение на карте
	 */
	@Property
	public String mapZoom;
	
	/**
	 * Расположение на карте
	 */
	@Property
	public Float mapLatitude;
	
	/**
	 * Расположение на карте
	 */
	@Property
	public Float mapLongitude;
	
}