package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

import kz.aphion.adverts.persistence.realty.ResidentialComplex;
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
 * Базовые критерии для квартир, не зависимо от продажи или аренды
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
public class RealtyFlatBaseSubscriptionCriteriaModel extends RealtyBaseSubscriptionCriteriaModel {

	/**
	 * Информация о ЖК которые участвуют в поиске
	 * Если NULL то не важно какие
	 */
	public List<ResidentialComplex> residentalComplexs;
	
	/**
	 * Кол-во комнат от
	 */
	public Float roomFrom;
	
	/**
	 * Кол-во комнат до
	 */
	public Float roomTo;
	
	/**
	 * Типы интересуемых строений
	 */
	public List<FlatBuildingType> flatBuildingTypes;
	
	/**
	 * Год постройки дома
	 */
	public Long houseYearFrom;
	
	/**
	 * Год постройки дома
	 */
	public Long houseYearTo;
	
	/**
	 * Этаж
	 */
	public Long flatFloorFrom;
	
	/**
	 * Этаж
	 */
	public Long flatFloorTo;
	
	/**
	 * Этажность дома
	 */
	public Long houseFloorCountFrom;
	
	/**
	 * Этажность дома
	 */
	public Long houseFloorCountTo;
	
	/**
	 * Жилая площадь
	 */
	public Float squareLivingFrom;
	
	/**
	 * Жилая площадь
	 */
	public Float squareLivingTo;
	
	/**
	 * Площадь кухни
	 */
	public Float squareKitchenFrom;
	
	/**
	 * Площадь кухни
	 */
	public Float squareKitchenTo;
	
	/**
	 * Высота потолков
	 */
	public Float ceilingHeightFrom;
	
	/**
	 * Высота потолков
	 */
	public Float ceilingHeightTo;
	
	/**
	 * В приватизированном общежитии
	 */
	public List<FlatPrivatizedDormType> privatizedDormTypes;
	
	/**
	 * Состояние квартиры
	 */
	public List<FlatRenovationType> renovationTypes;
	
	/**
	 * Телефон
	 */
	public List<FlatPhoneType> phoneTypes;
	
	/**
	 * Интернет
	 */
	public List<FlatInternetType> internetTypes;
	
	/**
	 * Санузел
	 */
	public List<FlatLavatoryType> lavatoryTypes;
	
	/**
	 * Балкон
	 */
	public List<FlatBalconyType> balconyTypes;
	
	/**
	 * Остекление балкона
	 */
	public List<FlatBalconyGlazingType> balconyGlazingTypes;
	
	/**
	 * Дверь
	 */
	public List<FlatDoorType> doorTypes;
	
	/**
	 * Парковка
	 */
	public List<FlatParkingType> parkingTypes;
	
	/**
	 * Мебель
	 */
	public List<FlatFurnitureType> furnitureTypes;
	
	/**
	 * Тип напольного покрытия
	 */
	public List<FlatFloorType> floorTypes;
	
	/**
	 * Безопасность
	 */
	public List<FlatSecurityType> securityTypes;
	
	/**
	 * Разное
	 */
	public List<FlatMiscellaneousType> miscellaneous;
	
}
