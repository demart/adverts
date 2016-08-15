package kz.aphion.adverts.subscription.live.models.realty.flat;

import java.util.List;

import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRealtyBaseData;
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
import kz.aphion.adverts.persistence.realty.types.SquareType;
import kz.aphion.adverts.subscription.live.models.realty.RealtyBaseSubscriptionCriteria;

public class RealtyFlatBaseSubscriptionCriteria extends RealtyBaseSubscriptionCriteria {

	public List<Long> residentialComplexes;
	
	/**
	 * Общая площадь от
	 */
	public Float squareFrom;
	
	/**
	 * Общая площадь до
	 */
	public Float squareTo;
	
	/**
	 * Тип исчисления площади
	 */
	public SquareType squareType;
	
	/**
	 * Кол-во комнат от
	 */
	public Float roomFrom;
	
	/**
	 * Кол-во комнат до
	 */
	public Float roomTo;
	
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
	 * Типы интересуемых строений
	 */
	public List<FlatBuildingType> flatBuildingTypes;
	
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
	
	
	public boolean isAdvertBelongsToQuery(FlatRealtyBaseData data) {
		if (checkResidentialComplex(data) == false)
			return false;
		if (checkSquare(data) == false)
			return false;
		if (checkSquareLiving(data) == false)
			return false;
		if (checkSquareKitchen(data) == false)
			return false;
		if (checkRoom(data) == false)
			return false;
		if (checkHouseYear(data) == false)
			return false;
		if (checkFlatFloor(data) == false)
			return false;
		if (checkHouseFloorCount(data) == false)
			return false;
		if (checkCeiling(data) == false)
			return false;
		if (checkEnums(flatBuildingTypes, data.flatBuildingType) == false)
			return false;
		if (checkEnums(privatizedDormTypes, data.privatizedDormType) == false)
			return false;
		if (checkEnums(renovationTypes, data.renovationType) == false)
			return false;
		if (checkEnums(phoneTypes, data.phoneType) == false)
			return false;
		if (checkEnums(internetTypes, data.internetType) == false)
			return false;
		if (checkEnums(lavatoryTypes, data.lavatoryType) == false)
			return false;
		if (checkEnums(balconyTypes, data.balconyType) == false)
			return false;
		if (checkEnums(balconyGlazingTypes, data.balconyGlazingType) == false)
			return false;
		if (checkEnums(doorTypes, data.doorType) == false)
			return false;
		if (checkEnums(parkingTypes, data.parkingType) == false)
			return false;
		if (checkEnums(furnitureTypes, data.furnitureType) == false)
			return false;
		if (checkEnums(floorTypes, data.floorType) == false)
			return false;
		//if (checkEnums(securityTypes, data.securityTypes) == false)
		//	return false;
		//if (checkEnums(miscellaneous, data.miscellaneous) == false)
		//	return false;
//		
//		if (checkIntercepts(squareFrom, squareTo, data.square) == false)
//			return false;
//		
		return true;
	}
	
	
	public boolean checkResidentialComplex(FlatRealtyBaseData data) {
		if (residentialComplexes != null) {
			
			if (data.residentalComplex == null)
				return false;
			
			for (Long complexCode : residentialComplexes) {
				if (complexCode.equals(data.residentalComplex.relationId))
					return true;
			}
			
			return false;
			
		}
		
		return true;
	}
	
	
//	
//	public boolean checkIntercepts(Number valueFrom, Number valueTo, Number advertValue) {
//		if (valueFrom != null) {
//			if (advertValue == null || advertValue <  1)
//				return false;
//			if (advertValue < valueFrom)
//				return false;
//		}
//		
//		if (valueTo != null) {
//			if (advertValue == null || advertValue < 1)
//				return false;
//			if (advertValue > valueTo)
//				return false;
//		}
//					
//		return true;
//	}
	
	public boolean checkEnums(List<? extends Enum> values, List<? extends Enum> advertValues) {
		if (values != null){
			if (advertValues == null)
				return false;
			
			for (Enum enumValue : values) {
				for (Enum advertEnumValue : advertValues) {
					
				}
				
			}
			
			return false;
		}
			
			
		return true;
	}
	
	
	public boolean checkEnums(List<? extends Enum> values, Enum value) {
		if (values != null){
			if (value == null)
				return false;
			
			for (Enum enumValue : values) {
				if (enumValue.equals(value))
					return true;
			}
			
			return false;
		}
			
			
		return true;
	}
	

	public boolean checkCeiling(FlatRealtyBaseData data) {
		if (ceilingHeightFrom != null) {
			if (data.ceilingHeight == null || data.ceilingHeight < 1)
				return false;
			if (data.ceilingHeight < ceilingHeightFrom)
				return false;
		}
		
		if (ceilingHeightTo != null) {
			if (data.ceilingHeight == null || data.ceilingHeight < 1)
				return false;
			if (data.ceilingHeight > ceilingHeightTo)
				return false;
		}
					
		return true;
	}
	
	public boolean checkSquareKitchen(FlatRealtyBaseData data) {
		if (squareKitchenFrom != null) {
			if (data.squareKitchen == null || data.squareKitchen < 1)
				return false;
			if (data.squareKitchen < squareKitchenFrom)
				return false;
		}
		
		if (squareKitchenTo != null) {
			if (data.squareKitchen == null || data.squareKitchen < 1)
				return false;
			if (data.squareKitchen > squareKitchenTo)
				return false;
		}
					
		return true;
	}
	
	public boolean checkSquareLiving(FlatRealtyBaseData data) {
		if (squareLivingFrom != null) {
			if (data.squareLiving == null || data.squareLiving < 1)
				return false;
			if (data.squareLiving < squareLivingFrom)
				return false;
		}
		
		if (squareLivingTo != null) {
			if (data.squareLiving == null || data.squareLiving < 1)
				return false;
			if (data.squareLiving > squareLivingTo)
				return false;
		}
					
		return true;
	}
	
	public boolean checkFlatFloor(FlatRealtyBaseData data){
		if (flatFloorFrom != null) {
			if (data.flatFloor == null || data.flatFloor < 1)
				return false;
			if (data.flatFloor < flatFloorFrom)
				return false;
		}
		
		if (flatFloorTo != null) {
			if (data.flatFloor == null || data.flatFloor < 1)
				return false;
			if (data.flatFloor > flatFloorTo)
				return false;
		}
		
		return true;
	}
	
	public boolean checkHouseFloorCount(FlatRealtyBaseData data) {
		if (houseFloorCountFrom != null) {
			if (data.houseFloorCount == null || data.houseFloorCount < 1)
				return false;
			if (data.houseFloorCount < houseFloorCountFrom)
				return false;
		}
		
		if (houseFloorCountTo != null) {
			if (data.houseFloorCount == null || data.houseFloorCount < 1)
				return false;
			if (data.houseFloorCount > houseFloorCountTo)
				return false;
		}
					
		return true;
	}
	
	
	public boolean checkHouseYear(FlatRealtyBaseData data) {
		if (houseYearFrom != null) {
			if (data.houseYear == null || data.houseYear < 1)
				return false;
			if (data.houseYear < houseYearFrom)
				return false;
		}
		
		if (houseYearTo != null) {
			if (data.houseYear == null || data.houseYear < 1)
				return false;
			if (data.houseYear > houseYearTo)
				return false;
		}
					
		return true;
	}
	
	public boolean checkRoom(FlatRealtyBaseData data) {
		if (roomFrom != null) {
			if (data.rooms == null || data.rooms < 1)
				return false;
			if (data.rooms < roomFrom)
				return false;
		}
			
		if (roomTo != null) {
			if (data.rooms == null || data.rooms < 1)
				return false;
			if (data.rooms > roomTo)
				return false;
		}
		
		return true;
	}
	
	public boolean checkSquare(FlatRealtyBaseData data) {
		if (squareFrom != null) {
			if (data.square == null || data.square < 1)
				return false;
			if (data.square < squareFrom)
				return false;
		}
		
		if (squareTo != null) {
			if (data.square == null || data.square < 1)
				return false;
			if (data.square > squareTo)
				return false;
		}
					
		return true;
	}
	
}
