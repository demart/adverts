package kz.aphion.adverts.persistence.realty.data.flat;

import java.util.List;

import kz.aphion.adverts.persistence.realty.ResidentialComplex;
import kz.aphion.adverts.persistence.realty.data.RealtyBaseDataModel;
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

/**
 * Базовая информация о квартире
 * 
 * @author artem.demidovich
 *
 */
public abstract class FlatRealtyBaseDataModel extends RealtyBaseDataModel {
	
	/**
	 * Информация о ЖК
	 */
	public ResidentialComplex residentalComplex;
	
	/**
	 * Кол-во комнат
	 */
	public Float rooms;
	
	/**
	 * Тип строения
	 */
	public FlatBuildingType flatBuildingType;
	
	/**
	 * Год постройки дома
	 */
	public Long houseYear;
	
	/**
	 * Этаж
	 */
	public Long flatFloor;
	
	/**
	 * Этажность дома
	 */
	public Long houseFloorCount;
	
	/**
	 * Жилая площадь
	 */
	public Float squareLiving;
	
	/**
	 * Площадь кухни
	 */
	public Float squareKitchen;
	
	/**
	 * В приватизированном общежитии
	 */
	public FlatPrivatizedDormType privatizedDormType;
	
	/**
	 * Состояние квартиры
	 */
	public FlatRenovationType renovationType;
	
	/**
	 * Телефон
	 */
	public FlatPhoneType phoneType;
	
	/**
	 * Интернет
	 */
	public FlatInternetType internetType;
	
	/**
	 * Санузел
	 */
	public FlatLavatoryType lavatoryType;
	
	/**
	 * Балкон
	 */
	public FlatBalconyType balconyType;
	
	/**
	 * Остекление балкона
	 */
	public FlatBalconyGlazingType balconyGlazingType;
	
	/**
	 * Дверь
	 */
	public FlatDoorType doorType;
	
	/**
	 * Парковка
	 */
	public FlatParkingType parkingType;
	
	/**
	 * Мебель
	 */
	public FlatFurnitureType furnitureType;
	
	/**
	 * Тип напольного покрытия
	 */
	public FlatFloorType floorType;
	
	/**
	 * Высота потолков
	 */
	public Float ceilingHeight;
	
	/**
	 * Безопасность
	 */
	public List<FlatSecurityType> securityTypes;
	
	/**
	 * Разное
	 */
	public List<FlatMiscellaneousType> miscellaneous;	
}
