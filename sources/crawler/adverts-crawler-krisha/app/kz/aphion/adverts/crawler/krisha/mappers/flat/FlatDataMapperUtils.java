package kz.aphion.adverts.crawler.krisha.mappers.flat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;

import kz.aphion.adverts.crawler.krisha.KrishaDataManager;
import kz.aphion.adverts.crawler.krisha.entity.KrishaRegionEntity;
import kz.aphion.adverts.crawler.krisha.entity.KrishaResidentalComplexEntity;
import kz.aphion.adverts.crawler.krisha.mappers.CommonMapperUtils;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;
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
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatSecurityType;
import kz.aphion.adverts.persistence.realty.types.MortgageStatus;
import play.Logger;

public class FlatDataMapperUtils {

	
	public static void mapResidentialComplex(Map<String, Object> advert, ResidentialComplex complex) {
		if (StringUtils.isBlank(complex.externalComplexId))
			return;
		
		
		KrishaResidentalComplexEntity complexEntity = KrishaDataManager.getResidentalComplex(complex.externalComplexId);
		
		if (complexEntity == null) {
			String regionId = (String)advert.get("map.geo_id");
			KrishaRegionEntity krishaRegion = KrishaDataManager.getKrishaRegion(regionId);
			if (krishaRegion != null) {
				complexEntity = KrishaDataManager.getResidentalComplex(complex.externalComplexId,krishaRegion.region.id);
				
				if (complexEntity == null) {
					Logger.error("KrishaResidentalComplex with name [%s] and region.id [%d] not found", complex.externalComplexId, krishaRegion.region.id);
				} else {
					complex.relationId = complexEntity.id;
					complex.name = complexEntity.name;
				}
			} else {
				Logger.error("KrishaResidentialComplex with id [%s] not found!", complex.externalComplexId);
			}
		} else {
			if (complexEntity.complex != null) {
				complex.relationId = complexEntity.id;
				complex.name = complexEntity.name;
			} else {
				Logger.error("KrishaResidentialComplex with id [%s] doens't have link to ResidentialComplex.");
			}
		}
	}
	
	
	public static FlatBuildingType getFlatBuildingType(Entry<String,Object> dataItem) {
		String flatBuilding = CommonMapperUtils.getEntryStringValue(dataItem);
			
		switch (flatBuilding) {
		case "1": // кирпичный
			return FlatBuildingType.BRICK;
		case "2": // панельный
			return FlatBuildingType.PANEL;
		case "3": // монолитный
			return FlatBuildingType.MONOLITHIC;
		case "4": // каркасно-камышитовый
			return FlatBuildingType.FRAME_REED;
		case "0": // иное
			return FlatBuildingType.OTHER;
		default:
			Logger.error("ATTENTION! Found new [data.flat.building] value: " + flatBuilding);
			return FlatBuildingType.UNDEFINED;
		}
	}
	
	
	public static MortgageStatus getFlatMortgageStatus(Entry<String,Object> dataItem) {
		String mortgage = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (mortgage) {
			case "0":
				return MortgageStatus.NO;
			case "1":
				return MortgageStatus.YES;
			default:
				Logger.error("ATTENTION! Found new [data.mortgage] value: " + mortgage);
				return MortgageStatus.UNDEFINED;
		}
	}
	
	public static FlatFurnitureType getFlatFurnitureType(Entry<String, Object> dataItem) {
		String liveFurniture = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (liveFurniture) {
			case "1": // полностью меблирована
				return FlatFurnitureType.FULLY_FURNITURED;
			case "2": // частично меблирована
				return FlatFurnitureType.PARTLY_FURNITURED;
			case "3": // пустая
				return FlatFurnitureType.EMPTY;
			default:
				Logger.error("ATTENTION! Found new [live.furniture] value: " + liveFurniture);
				return FlatFurnitureType.UNDEFINED;
		}
	}
	
	public static FlatPrivatizedDormType getFlatPrivatizedDormType(Entry<String, Object> dataItem) {
		String flatPrivDorm = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatPrivDorm) {
			case "0": // Поидее нету такого значения
				return FlatPrivatizedDormType.UNDEFINED;
			case "1": // Да
				return FlatPrivatizedDormType.YES;
			case "2": // Нет
				return FlatPrivatizedDormType.NO;
			default:
				Logger.error("ATTENTION! Found new [flat.priv_dorm] value: " + flatPrivDorm);
				return FlatPrivatizedDormType.UNDEFINED;
			}
	}
	
	public static FlatFloorType getFlatFlooringType(Entry<String, Object> dataItem){
		String flatFlooring = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatFlooring) {
			case "1": // линолеум
				return FlatFloorType.LINOLEUM;
			case "2": // паркет
				return FlatFloorType.PARQUET;
			case "3": // ламинат
				return FlatFloorType.LAMINATE;
			case "4": // дерево
				return FlatFloorType.WOOD;
			case "5": // ковролан
				return FlatFloorType.CARPETING;
			case "6": // плитка
				return FlatFloorType.TILE;
			case "7": // пробковое
				return FlatFloorType.CORK;
			default:
				Logger.error("ATTENTION! Found new [flat.flooring] value: " + flatFlooring);
				return FlatFloorType.UNDEFINED;
		}
	}
	
	
	public static FlatParkingType getParkingType(Entry<String, Object> dataItem) {
		String flatParking = CommonMapperUtils.getEntryStringValue(dataItem);
		
		switch (flatParking) {
		case "1": // паркинг
			return FlatParkingType.PARKING;
		case "2": // гараж
			return FlatParkingType.GARAGE;
		case "3": // рядом охраняемая стоянка
			return FlatParkingType.GUARDED_CAR_PARK;
		default:
			Logger.error("ATTENTION! Found new [flat.parking] value: " + flatParking);
			return FlatParkingType.UNDEFINED;
		}
	}
	
	
	public static FlatRenovationType getFlatRenovationType(Entry<String, Object> dataItem) {
		String flatRenovation = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatRenovation) {
			case "1": // хорошее
				return FlatRenovationType.GOOD;
			case "2": // среднее
				return FlatRenovationType.AVARAGE;
			case "3": // евроремонт
				return FlatRenovationType.EURO;
			case "4": // требует ремонта
				return FlatRenovationType.BAD;
			case "5": // свободная планировка
				return FlatRenovationType.OPEN_PLANNING;
			case "6": // черновая отделка
				return FlatRenovationType.ROUGH_FINISH;
			default:
				Logger.error("ATTENTION! Found new [flat.renovation] value: " + flatRenovation);
				return FlatRenovationType.UNDEFINED;
			}
	}
	
	public static FlatPhoneType getFlatPhoneType(Entry<String, Object> dataItem) {
		String flatPhone = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatPhone) {
			case "1": // отдельный
				return FlatPhoneType.SEPARETED;
			case "2": // блокиратор
				return FlatPhoneType.LOCKING;
			case "3": // есть возможность подключения
				return FlatPhoneType.ABILITY_TO_CONNECT;
			case "4": // нет
				return FlatPhoneType.NO_PHONE;
			default:
				Logger.error("ATTENTION! Found new [flat.phone] value: " + flatPhone);
				return FlatPhoneType.UNDEFINED;
			}
	}
	
	public static FlatBalconyType getFlatBalconyType(Entry<String, Object> dataItem) {
		String flatBalcony = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatBalcony) {
			case "1": // балкон
				return FlatBalconyType.BALCONY;
			case "2": // лоджия
				return FlatBalconyType.LOGGIA;
			case "3": // балкон и лоджия
				return FlatBalconyType.BALCONY_LOGGIA;
			case "4": // несколько балконов или лоджий
				return FlatBalconyType.SEVERAL_BALCONY_OR_LOGGIA;
			default:
				Logger.error("ATTENTION! Found new [flat.balcony] value: " + flatBalcony);
				return FlatBalconyType.UNDEFINED;
			}
	}
	
	public static FlatInternetType getFlatInternetType(Entry<String,Object> dataItem) {
		String inetType = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (inetType) {
		case "1": // ADSL
			return FlatInternetType.ADSL;
		case "2": // через TV кабель
			return FlatInternetType.TV;
		case "3": // проводной
			return FlatInternetType.LAN;
		case "4": // оптика
			return FlatInternetType.OPTIC;
		default:
			Logger.error("ATTENTION! Found new [inet.type] value: " + inetType);
			return FlatInternetType.UNDEFINED;
		}
	}
	
	
	public static FlatLavatoryType getFlatLavatoryType(Entry<String, Object> dataItem) {
		String flatToilet = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatToilet) {
			case "1": // раздельный
				return FlatLavatoryType.SEPARETED;
			case "2": // совмещенный
				return  FlatLavatoryType.COMBINED;
			case "3": // 2 с/у и более
				return FlatLavatoryType.TWO_AND_MORE;
			case "4": // нет
				return FlatLavatoryType.NO_LAVATORY;
			default:
				Logger.error("ATTENTION! Found new [flat.toilet] value: " + flatToilet);
				return FlatLavatoryType.UNDEFINED;
			}
	}
	

	public static FlatBalconyGlazingType getFlatBalconyGlazingType(Entry<String, Object> dataItem) {
		String flatBalconyGlazing = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (flatBalconyGlazing) {
			case "0": // нет
				return FlatBalconyGlazingType.NO;
			case "1": // да
				return FlatBalconyGlazingType.YES;
			default:
				Logger.error("ATTENTION! Found new [flat.balcony_g] value: " + flatBalconyGlazing);
				return FlatBalconyGlazingType.UNDEFINED;
		}
	}
	
	public static FlatDoorType getFlatDoorTypes(Entry<String, Object> doorTypesEntry) {
		if (doorTypesEntry.getValue() != null){
			String flatDoorValue = CommonMapperUtils.getEntryStringValue(doorTypesEntry);
			switch (flatDoorValue) {
			case "1": // деревянная
				return FlatDoorType.WOODEN;
			case "2": // металлическая
				return FlatDoorType.METAL;
			case "3": // бронированная
				return FlatDoorType.SECURED;				
			default:
				Logger.error("ATTENTION! Found new [flat.door] value: " + flatDoorValue);
				return FlatDoorType.UNDEFINED;
			}
		} else {
			return FlatDoorType.UNDEFINED;
		}
	}
	
	public static List<FlatMiscellaneousType> getFlatMiscellaneousTypes(Entry<String,Object> dataItem) {
		Collection<Object> flatOptions = null;
		if (dataItem.getValue() instanceof Map<?,?>) {
			Map<String,Object> flatOptionsMap = (Map<String,Object>)dataItem.getValue();
			flatOptions = flatOptionsMap.values();
		}
		
		if (dataItem.getValue() instanceof List<?>) {
			flatOptions = (List<Object>)dataItem.getValue();
		}
		
		List<FlatMiscellaneousType> miscellaneousTypes = new ArrayList<FlatMiscellaneousType>();
		for (Object flatOptionItem : flatOptions) {
			switch ((String)flatOptionItem.toString()) {
			case "1": // пластиковые окна
				miscellaneousTypes.add(FlatMiscellaneousType.PLASTIC_WINDOWS);
				break;
			case "2": // неугловая
				miscellaneousTypes.add(FlatMiscellaneousType.NOT_CORNER);
				break;
			case "3": // улучшенная
				miscellaneousTypes.add(FlatMiscellaneousType.IMPROVED);
				break;
			case "4": // комнаты изолированы
				miscellaneousTypes.add(FlatMiscellaneousType.SEPARATED_ROOMS);
				break;
			case "5": // кухня-студия
				miscellaneousTypes.add(FlatMiscellaneousType.STUDIO_KITCKEN);
				break;
			case "6": // встроенная кухня
				miscellaneousTypes.add(FlatMiscellaneousType.BUILT_IN_KITCHEN);
				break;
			case "7": // новая сантехника
				miscellaneousTypes.add(FlatMiscellaneousType.NEW_PLUMBING);
				break;
			case "8": // кладовка
				miscellaneousTypes.add(FlatMiscellaneousType.STORAGE);
				break;
			case "9": // счётчики
				miscellaneousTypes.add(FlatMiscellaneousType.COUNTERS);
				break;
			case "10": // тихий двор
				miscellaneousTypes.add(FlatMiscellaneousType.QUIET_COURTYARD);
				break;
			case "11": // кондиционер
				miscellaneousTypes.add(FlatMiscellaneousType.AIR_CONDITIONING);
				break;
			case "12": // удобно под коммерцию
				miscellaneousTypes.add(FlatMiscellaneousType.COMFORTABLE_FOR_COMMERCE);
				break;
			default:
				Logger.error("ATTENTION! Found new [flat.options] value: " + flatOptionItem.toString());
				break;
			}
		}
		return miscellaneousTypes;
		
		/*
		if (flatOptions != null) {
			List<FlatMiscellaneousType> miscellaneousTypes = new ArrayList<FlatMiscellaneousType>();
			for (Entry<String,Object> flatOptionItem : flatOptions.entrySet()) {
				switch ((String)flatOptionItem.getValue()) {
				case "1": // пластиковые окна
					miscellaneousTypes.add(FlatMiscellaneousType.PLASTIC_WINDOWS);
					break;
				case "2": // неугловая
					miscellaneousTypes.add(FlatMiscellaneousType.NOT_CORNER);
					break;
				case "3": // улучшенная
					miscellaneousTypes.add(FlatMiscellaneousType.IMPROVED);
					break;
				case "4": // комнаты изолированы
					miscellaneousTypes.add(FlatMiscellaneousType.SEPARATED_ROOMS);
					break;
				case "5": // кухня-студия
					miscellaneousTypes.add(FlatMiscellaneousType.STUDIO_KITCKEN);
					break;
				case "6": // встроенная кухня
					miscellaneousTypes.add(FlatMiscellaneousType.BUILT_IN_KITCHEN);
					break;
				case "7": // новая сантехника
					miscellaneousTypes.add(FlatMiscellaneousType.NEW_PLUMBING);
					break;
				case "8": // кладовка
					miscellaneousTypes.add(FlatMiscellaneousType.STORAGE);
					break;
				case "9": // счётчики
					miscellaneousTypes.add(FlatMiscellaneousType.COUNTERS);
					break;
				case "10": // тихий двор
					miscellaneousTypes.add(FlatMiscellaneousType.QUIET_COURTYARD);
					break;
				case "11": // кондиционер
					miscellaneousTypes.add(FlatMiscellaneousType.AIR_CONDITIONING);
					break;
				case "12": // удобно под коммерцию
					miscellaneousTypes.add(FlatMiscellaneousType.COMFORTABLE_FOR_COMMERCE);
					break;
				default:
					Logger.error("ATTENTION! Found new [flat.options] value: " + flatOptionItem.getValue());
					break;
				}
			}
			return miscellaneousTypes;
		} else {
			return null;
		}
		*/
	}
	

	public static List<FlatRentMiscellaneousType> getFlatRentMiscellaneousTypes(Entry<String,Object> dataItem) {
		Collection<Object> flatRentOptions = null;
		if (dataItem.getValue() instanceof Map<?,?>) {
			Map<String,Object> flatRentOptionsMap = (Map<String,Object>)dataItem.getValue();
			flatRentOptions = flatRentOptionsMap.values();
		}
		
		if (dataItem.getValue() instanceof List<?>) {
			flatRentOptions = (List<Object>)dataItem.getValue();
		}
		
		List<FlatRentMiscellaneousType> rentMiscellaneous = new ArrayList<FlatRentMiscellaneousType>();
		for (Object flatRentOptionItem : flatRentOptions) {
			switch (flatRentOptionItem.toString()) {
			case "1": // чистая
				rentMiscellaneous.add(FlatRentMiscellaneousType.CLEAN);
				break;
			case "2": // уютная
				rentMiscellaneous.add(FlatRentMiscellaneousType.COMFORTABLE);
				break;
			case "3": // холодильник
				rentMiscellaneous.add(FlatRentMiscellaneousType.REFRIGERATOR);
				break;
			case "4": // стиральная машина-автомат
				rentMiscellaneous.add(FlatRentMiscellaneousType.WASHING_MACHINE);
				break;
			case "5": // кабельное ТВ
				rentMiscellaneous.add(FlatRentMiscellaneousType.CABLE_TV);
				break;
			case "6": // по часам
				rentMiscellaneous.add(FlatRentMiscellaneousType.HOURLY);
				break;
			case "7": // оплата поквартально
				rentMiscellaneous.add(FlatRentMiscellaneousType.QUARTERLY_PAYMENT);
				break;
			case "8": // + квартплата
				rentMiscellaneous.add(FlatRentMiscellaneousType.PLUS_UTILITY_PAYMENT);
				break;
			case "9": // семейным
				rentMiscellaneous.add(FlatRentMiscellaneousType.FAMILY);
				break;
			case "10": // на длительный срок
				rentMiscellaneous.add(FlatRentMiscellaneousType.FOR_A_LONG_TIME);
				break;
			case "11": // удобно под офис
				rentMiscellaneous.add(FlatRentMiscellaneousType.CONVINIENT_FOR_OFFICE);
				break;
			case "12": // телевизор
				rentMiscellaneous.add(FlatRentMiscellaneousType.TV);
				break;
			case "13": // вся бытовая техника
				rentMiscellaneous.add(FlatRentMiscellaneousType.ALL_APPLIANCE);
				break;
			default:
				Logger.error("ATTENTION! Found new [flat.rent_options] value: " + flatRentOptionItem.toString());
				break;
			}
		}
		return rentMiscellaneous;
		
		/*
		if (flatRentOptions != null) {
			List<FlatRentMiscellaneousType> rentMiscellaneous = new ArrayList<FlatRentMiscellaneousType>();
			for (Entry<String,Object> flatRentOptionItem : flatRentOptions.entrySet()) {
				switch ((String)flatRentOptionItem.getValue()) {
				case "1": // чистая
					rentMiscellaneous.add(FlatRentMiscellaneousType.CLEAN);
					break;
				case "2": // уютная
					rentMiscellaneous.add(FlatRentMiscellaneousType.COMFORTABLE);
					break;
				case "3": // холодильник
					rentMiscellaneous.add(FlatRentMiscellaneousType.REFRIGERATOR);
					break;
				case "4": // стиральная машина-автомат
					rentMiscellaneous.add(FlatRentMiscellaneousType.WASHING_MACHINE);
					break;
				case "5": // кабельное ТВ
					rentMiscellaneous.add(FlatRentMiscellaneousType.CABLE_TV);
					break;
				case "6": // по часам
					rentMiscellaneous.add(FlatRentMiscellaneousType.HOURLY);
					break;
				case "7": // оплата поквартально
					rentMiscellaneous.add(FlatRentMiscellaneousType.QUARTERLY_PAYMENT);
					break;
				case "8": // + квартплата
					rentMiscellaneous.add(FlatRentMiscellaneousType.PLUS_UTILITY_PAYMENT);
					break;
				case "9": // семейным
					rentMiscellaneous.add(FlatRentMiscellaneousType.FAMILY);
					break;
				case "10": // на длительный срок
					rentMiscellaneous.add(FlatRentMiscellaneousType.FOR_A_LONG_TIME);
					break;
				case "11": // удобно под офис
					rentMiscellaneous.add(FlatRentMiscellaneousType.CONVINIENT_FOR_OFFICE);
					break;
				case "12": // телевизор
					rentMiscellaneous.add(FlatRentMiscellaneousType.TV);
					break;
				case "13": // вся бытовая техника
					rentMiscellaneous.add(FlatRentMiscellaneousType.ALL_APPLIANCE);
					break;
				default:
					Logger.error("ATTENTION! Found new [flat.rent_options] value: " + flatRentOptionItem.getValue());
					break;
				}
			}
			return rentMiscellaneous;
		} else {
			return null;
		}
		*/
	}
	
	public static List<FlatSecurityType> getFlatSecurityTypes(Entry<String,Object> dataItem) {
		Collection<Object> securityCollections = null;
		if (dataItem.getValue() instanceof Map<?,?>) {
			Map<String,Object> flatSecurityTypesMap = (Map<String,Object>)dataItem.getValue();
			securityCollections = flatSecurityTypesMap.values();
		}
		
		if (dataItem.getValue() instanceof List<?>) {
			securityCollections = (List<Object>)dataItem.getValue();
		}
		
		List<FlatSecurityType> securityTypes = new ArrayList<FlatSecurityType>();
		for (Object object : securityCollections) {
			switch (object.toString()) {
			case "1": // решетки на окнах
				securityTypes.add(FlatSecurityType.BARS_ON_THE_WINDOWS);
				break;
			case "2": // охрана
				securityTypes.add(FlatSecurityType.GUARD);
				break;
			case "3": // домофон
				securityTypes.add(FlatSecurityType.INTERCOM);
				break;
			case "4": // кодовый замок
				securityTypes.add(FlatSecurityType.COMBINATION_LOCK);
				break;
			case "5": // сигнализация
				securityTypes.add(FlatSecurityType.SIGNALING);
				break;
			case "6": // видеонаблюдение
				securityTypes.add(FlatSecurityType.CCTV);
				break;
			case "7": // видеодомофон
				securityTypes.add(FlatSecurityType.VIDEO_INTERCOM);
				break;
			case "8": // консьерж
				securityTypes.add(FlatSecurityType.CONCIERGE);
				break;
			default:
				Logger.error("ATTENTION! Found new [flat.security] value: " + object.toString());
				break;
			}
		}
		
		/*
		if (dataItem instanceof Map<?,?>) {
			Map<String,Object> flatSecurityTypesMap = (Map<String,Object>)dataItem;
			
			List<FlatSecurityType> securityTypes = new ArrayList<FlatSecurityType>();
			for (Entry<String,Object> flatSecurityItem : flatSecurityTypesMap.entrySet()) {
				switch ((String)flatSecurityItem.getValue()) {
				case "1": // решетки на окнах
					securityTypes.add(FlatSecurityType.BARS_ON_THE_WINDOWS);
					break;
				case "2": // охрана
					securityTypes.add(FlatSecurityType.GUARD);
					break;
				case "3": // домофон
					securityTypes.add(FlatSecurityType.INTERCOM);
					break;
				case "4": // кодовый замок
					securityTypes.add(FlatSecurityType.COMBINATION_LOCK);
					break;
				case "5": // сигнализация
					securityTypes.add(FlatSecurityType.SIGNALING);
					break;
				case "6": // видеонаблюдение
					securityTypes.add(FlatSecurityType.CCTV);
					break;
				case "7": // видеодомофон
					securityTypes.add(FlatSecurityType.VIDEO_INTERCOM);
					break;
				case "8": // консьерж
					securityTypes.add(FlatSecurityType.CONCIERGE);
					break;
				default:
					Logger.error("ATTENTION! Found new [flat.security] value: " + flatSecurityItem.getValue());
					break;
				}
			}
			return securityTypes;
		}
		
		if (dataItem instanceof List<?>) {
			List<Object> flatSecurityTypesList = (List<Object>)dataItem; 
			List<FlatSecurityType> securityTypes = new ArrayList<FlatSecurityType>();
			for (Object object : flatSecurityTypesList) {
				switch (object.toString()) {
				case "1": // решетки на окнах
					securityTypes.add(FlatSecurityType.BARS_ON_THE_WINDOWS);
					break;
				case "2": // охрана
					securityTypes.add(FlatSecurityType.GUARD);
					break;
				case "3": // домофон
					securityTypes.add(FlatSecurityType.INTERCOM);
					break;
				case "4": // кодовый замок
					securityTypes.add(FlatSecurityType.COMBINATION_LOCK);
					break;
				case "5": // сигнализация
					securityTypes.add(FlatSecurityType.SIGNALING);
					break;
				case "6": // видеонаблюдение
					securityTypes.add(FlatSecurityType.CCTV);
					break;
				case "7": // видеодомофон
					securityTypes.add(FlatSecurityType.VIDEO_INTERCOM);
					break;
				case "8": // консьерж
					securityTypes.add(FlatSecurityType.CONCIERGE);
					break;
				default:
					Logger.error("ATTENTION! Found new [flat.security] value: " + object.toString());
					break;
				}
			}
			return securityTypes;
		}
		
		*/
		
		
		
		return null;
	}


	public static FlatRentPeriodType getFlatRentPeriodType(Entry<String, Object> dataItem) {
		String rentType = CommonMapperUtils.getEntryStringValue(dataItem);
		switch (rentType) {
			case "1": // посуточно
				return FlatRentPeriodType.DAILY;
			case "2": // помесячно
				return FlatRentPeriodType.MONTHLY;
			case "3": // поквартально
				return FlatRentPeriodType.QUARTERLY;
			case "4": // по часам
				return FlatRentPeriodType.HOURLY;
			default:
				Logger.error("ATTENTION! Found new [rent.period] value: " + rentType);
				return FlatRentPeriodType.UNDEFINED;
		}
	}

}
