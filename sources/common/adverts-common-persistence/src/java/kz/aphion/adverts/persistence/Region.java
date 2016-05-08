package kz.aphion.adverts.persistence;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Справочник регионов, описывает расположение объектов
 * 
 * @author artem.demidovich
 *
 */
@Entity(value="adverts.dic.regions")
public class Region extends BaseEntity {
	
	/**
	 * Код справочника для поиска, пока сам справочник лежит в реляционной структуре
	 */
	@Property
	public String code;
	
	/**
	 * Название региона
	 */
	@Property("name")
	public String name;
	
	/**
	 * Тип региона
	 */
	@Property("type")
	public RegionType type;
	
	/**
	 * Уровень вложенности
	 */
	@Property("level")
	public String level;
	
	/**
	 * Родитель региона
	 */
	@Reference("parent")
	public Region parent;
	
	/**
	 * Приближение на карте
	 */
	@Property("zoom")
	public String mapZoom;
	
	/**
	 * Расположение на карте
	 */
	@Property("latitude")
	public Float mapLatitude;
	
	/**
	 * Расположение на карте
	 */
	@Property("longitude")
	public Float mapLongitude;
	
}
