package kz.aphion.adverts.persistence.subscription.criteria.realty;

import java.util.List;

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
 * Базовые критерии для квартир, не зависимо от продажи или аренды
 * 
 * @author artem.demidovich
 *
 * Created at Aug 11, 2016
 */
@Embedded
public class RealtyFlatBaseSubscriptionCriteria extends RealtyBaseSubscriptionCriteria {

	/**
	 * Кол-во комнат от
	 */
	@Property
	public Float roomFrom;
	
	/**
	 * Кол-во комнат до
	 */
	@Property
	public Float roomTo;
	
	/**
	 * Типы интересуемых строений
	 */
	@Embedded
	public List<FlatBuildingType> flatBuildingTypes;
	
	/**
	 * Год постройки дома
	 */
	@Property
	public Long houseYearFrom;
	/**
	 * Год постройки дома
	 */
	@Property
	public Long houseYearTo;
	
	/**
	 * Этаж
	 */
	@Property
	public Long flatFloorFrom;
	
	/**
	 * Этаж
	 */
	@Property
	public Long flatFloorTo;
	
	/**
	 * Этажность дома
	 */
	@Property
	public Long houseFloorCountFrom;
	
	/**
	 * Этажность дома
	 */
	@Property
	public Long houseFloorCountTo;
	
	/**
	 * Жилая площадь
	 */
	@Property
	public Float squareLivingFrom;
	
	/**
	 * Жилая площадь
	 */
	@Property
	public Float squareLivingTo;
	
	/**
	 * Площадь кухни
	 */
	@Property
	public Float squareKitchenFrom;
	
	/**
	 * Площадь кухни
	 */
	@Property
	public Float squareKitchenTo;
	
	/**
	 * Высота потолков
	 */
	@Property
	public Float ceilingHeightFrom;
	
	/**
	 * Высота потолков
	 */
	@Property
	public Float ceilingHeightTo;
	
	/**
	 * В приватизированном общежитии
	 */
	@Embedded
	public List<FlatPrivatizedDormType> privatizedDormTypes;
	
	/**
	 * Состояние квартиры
	 */
	@Embedded
	public List<FlatRenovationType> renovationTypes;
	
	/**
	 * Телефон
	 */
	@Embedded
	public List<FlatPhoneType> phoneTypes;
	
	/**
	 * Интернет
	 */
	@Embedded
	public List<FlatInternetType> internetTypes;
	
	/**
	 * Санузел
	 */
	@Embedded
	public List<FlatLavatoryType> lavatoryTypes;
	
	/**
	 * Балкон
	 */
	@Embedded
	public List<FlatBalconyType> balconyTypes;
	
	/**
	 * Остекление балкона
	 */
	@Embedded
	public List<FlatBalconyGlazingType> balconyGlazingTypes;
	
	/**
	 * Дверь
	 */
	@Embedded
	public List<FlatDoorType> doorTypes;
	
	/**
	 * Парковка
	 */
	@Embedded
	public List<FlatParkingType> parkingTypes;
	
	/**
	 * Мебель
	 */
	@Embedded
	public List<FlatFurnitureType> furnitureTypes;
	
	/**
	 * Тип напольного покрытия
	 */
	@Embedded
	public List<FlatFloorType> floorTypes;
	
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
