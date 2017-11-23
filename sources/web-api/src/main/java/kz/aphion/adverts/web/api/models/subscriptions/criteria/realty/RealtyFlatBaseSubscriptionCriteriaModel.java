package kz.aphion.adverts.web.api.models.subscriptions.criteria.realty;

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
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyBaseSubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyFlatBaseSubscriptionCriteria;

/**
 * Базовые критерии для подписок по квартирам
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class RealtyFlatBaseSubscriptionCriteriaModel extends RealtyBaseSubscriptionCriteriaModel {

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
	
	
	public void fillModel(RealtyFlatBaseSubscriptionCriteria criteria) {
		if (criteria == null)
			return;
		
		this.roomFrom = criteria.roomFrom;
		this.roomTo = criteria.roomTo;
		this.flatBuildingTypes = criteria.flatBuildingTypes;
		this.houseYearFrom = criteria.houseYearFrom;
		this.houseYearTo = criteria.houseYearTo;
		this.flatFloorFrom = criteria.flatFloorFrom;
		this.flatFloorTo = criteria.flatFloorTo;
		this.houseFloorCountFrom = criteria.houseFloorCountFrom;
		this.houseFloorCountTo = criteria.houseFloorCountTo;
		this.squareLivingFrom = criteria.squareLivingFrom;
		this.squareLivingTo = criteria.squareLivingTo;
		this.squareKitchenFrom = criteria.squareKitchenFrom;
		this.squareKitchenTo = criteria.squareKitchenTo;
		this.ceilingHeightFrom = criteria.ceilingHeightFrom;
		this.ceilingHeightTo = criteria.ceilingHeightTo;
		this.privatizedDormTypes = criteria.privatizedDormTypes;
		this.renovationTypes = criteria.renovationTypes;
		this.phoneTypes = criteria.phoneTypes;
		this.internetTypes = criteria.internetTypes;
		this.lavatoryTypes = criteria.lavatoryTypes;
		this.balconyTypes = criteria.balconyTypes;
		this.balconyGlazingTypes = criteria.balconyGlazingTypes;
		this.doorTypes = criteria.doorTypes;
		this.parkingTypes = criteria.parkingTypes;
		this.furnitureTypes = criteria.furnitureTypes;
		this.floorTypes = criteria.floorTypes;
		this.securityTypes=  criteria.securityTypes;
		this.miscellaneous = criteria.miscellaneous;
		
		this.fillModel((RealtyBaseSubscriptionCriteria)criteria);
	}
	
}
