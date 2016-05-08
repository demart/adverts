package kz.aphion.adverts.persistence.realty;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;
import kz.aphion.adverts.persistence.realty.types.MapName;
import kz.aphion.adverts.persistence.realty.types.MapType;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Расположение объекта недвижимости
 * 
 * @author artem.demidovich
 *
 */
@Entity
public class RealtyLocation extends BaseEntity {

	/**
	 * Принадлежит к региону
	 */
	@Embedded
	public Region region;
	
	/**
	 * Список регионов к которому принадлежит объявление.
	 * Например:
	 * 	Казахстан
	 * 		Акмолинская область
	 * 			Щучинск
	 * 				3 Микрорайон
	 * Нужно для того чтобы можно было легко искать по любому из ровней, хоть в Казахстану, хоть по Щучинску
	 */
	@Embedded
	public List<Region> regions;
	
	/**
	 * Внешний идентификатор региона в системе источника (для сверки)
	 */
	@Property
	public String externalRegionId;
	
	/**
	 * Улица на которой стоит здание
	 */
	@Property
	public String streetName;
	
	/**
	 * Номер дома
	 */
	@Property
	public String houseNumber;
	
	/**
	 * Пересекается с улицей
	 */
	@Property
	public String cornerStreetName;
	
	/**
	 * Подъезд
	 */
	@Property
	public String porch;
	
	/**
	 * Название карты
	 */
	@Property
	public MapName mapName;
	
	/**
	 * Тип карты (гибрид, спуткик или еще что-то)
	 */
	@Property
	public MapType mapType;
	
	/**
	 * Приближение на карте
	 */
	@Property
	public String mapZoom;	
	
	/**
	 * Геопозиция
	 */
	@Property
	public String mapLatitude;
	
	/**
	 * Геопозиция
	 */
	@Property
	public String mapLongitude;
	
}
