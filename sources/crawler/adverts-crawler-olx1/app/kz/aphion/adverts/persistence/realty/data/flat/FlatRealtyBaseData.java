package kz.aphion.adverts.persistence.realty.data.flat;

import java.util.List;

import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;
import kz.aphion.adverts.persistence.realty.data.RealtyBaseData;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBalconyGlazingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBalconyType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatDoorType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatFloorType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatFurnitureType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatInternetType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatParkingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatPrivatizedDormType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRenovationType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatSecurityType;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * Базовая информация о квартире
 * 
 * @author artem.demidovich
 *
 */
public abstract class FlatRealtyBaseData extends RealtyBaseData {
	
	/**
	 * Информация о ЖК
	 */
	@Embedded("residentialComplex")
	public ResidentialComplex residentalComplex;
	
	/**
	 * Кол-во комнат
	 */
	@Property
	public Float rooms;
	
	/**
	 * Тип строения
	 */
	@Property
	public FlatBuildingType flatBuildingType;
	
	/**
	 * Год постройки дома
	 */
	@Property
	public Long houseYear;
	
	/**
	 * Этаж
	 */
	@Property
	public Long flatFloor;
	
	/**
	 * Этажность дома
	 */
	@Property
	public Long houseFloorCount;
	
	/**
	 * Жилая площадь
	 */
	@Property
	public Float squareLiving;
	
	/**
	 * Площадь кухни
	 */
	@Property
	public Float squareKitchen;
	
	/**
	 * В приватизированном общежитии
	 */
	@Property
	public FlatPrivatizedDormType privatizedDormType;
	
	/**
	 * Состояние квартиры
	 */
	@Property
	public FlatRenovationType renovationType;
	
	/**
	 * Телефон
	 */
	@Property
	public FlatPhoneType phoneType;
	
	/**
	 * Интернет
	 */
	@Property
	public FlatInternetType internetType;
	
	/**
	 * Санузел
	 */
	@Property
	public FlatLavatoryType lavatoryType;
	
	/**
	 * Балкон
	 */
	@Property
	public FlatBalconyType balconyType;
	
	/**
	 * Остекление балкона
	 */
	@Property
	public FlatBalconyGlazingType balconyGlazingType;
	
	/**
	 * Дверь
	 */
	@Property
	public FlatDoorType doorType;
	
	/**
	 * Парковка
	 */
	@Property
	public FlatParkingType parkingType;
	
	/**
	 * Мебель
	 */
	@Property
	public FlatFurnitureType furnitureType;
	
	/**
	 * Тип напольного покрытия
	 */
	@Property
	public FlatFloorType floorType;
	
	/**
	 * Высота потолков
	 */
	@Property
	public Float ceilingHeight;
	
	/**
	 * Безопасность
	 */
	@Embedded
	public List<FlatSecurityType> securityTypes;
	
	/**
	 * Разное
	 */
	@Embedded
	public List<FlatMiscellaneousType> miscellaneous;	
}
