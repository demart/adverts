package kz.aphion.adverts.persistence.adverts;

import java.util.List;

import kz.aphion.adverts.persistence.Region;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Расположение объявления
 * 
 * @author artem.demidovich
 *
 * Created at Dec 7, 2017
 */
public class AdvertLocation {

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
	 * Улица
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
