package kz.aphion.adverts.web.api.search.realty;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
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
import kz.aphion.adverts.web.api.exceptions.IncorrectParameterValueException;
import kz.aphion.adverts.web.api.query.SearchAdvertQuery;
import kz.aphion.adverts.web.api.search.AdvertSearch;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlatSellRealtySeacher implements AdvertSearch {

	private static Logger logger = LoggerFactory.getLogger(FlatSellRealtySeacher.class);
	
	@Override
	public List<Object> search(SearchAdvertQuery query) throws Exception {
		// Построить запрос
		Query<FlatSellRealty> q = builQuery(query.parameters);
		
		// Выполнить
		if (query.limit > 0 && query.limit < 1001) {
			q.limit(query.limit);
		} else {
			q.limit(25);
		}
		
		if (query.offset > 0) {
			q.offset(query.offset);
		}
		
		List result = q.asList();
		// Посмотреть как отдавать результат
		// Вернуть
		
		// TODO Auto-generated method stub
		return result;
	}

	@Override
	public Long count(SearchAdvertQuery query) throws Exception {
		// Построить запрос
		Query<FlatSellRealty> q = builQuery(query.parameters);
		// Выполнить
		Long result = q.countAll();
		
		return result;
	}

	
	private Query<FlatSellRealty> builQuery(HashMap<String, List<String>> parameters) throws Exception {
		Datastore ds = DB.DS();
		
		Query<FlatSellRealty> q = ds.createQuery(FlatSellRealty.class);
		
		for (String key : parameters.keySet()) {
			switch (key) {
				case FlatParameters.PRICE:
					addRangeValue(q, "price", parameters.get(key));
					break;
				case FlatParameters.ROOMS:
					addRangeValue(q, "data.rooms", parameters.get(key));
					break;
				case FlatParameters.HAS_PHOTO:
					addHasPhoto(q, "hasPhoto", parameters.get(key));
					break;
				case FlatParameters.REGIONS:
					addRegions(q, "location.regions.code", parameters.get(key));
					break;
				case FlatParameters.SQUARE:
					addRangeValue(q, "data.square", parameters.get(key));
					break;
				case FlatParameters.SQUARE_LIVING:
					addRangeValue(q, "data.squareLiving", parameters.get(key));
					break;
				case FlatParameters.SQUARE_KITCHEN:
					addRangeValue(q, "data.squareKitchen", parameters.get(key));
					break;
				case FlatParameters.FLAT_FLOOR:
					addRangeValue(q, "data.flatFloor", parameters.get(key));
					break;
				case FlatParameters.CEILING:
					addRangeValue(q, "data.ceiling", parameters.get(key));
					break;
				case FlatParameters.HOUSE_FLOOR_COUNT:
					addRangeValue(q, "data.houseFloorCount", parameters.get(key));
					break;
				case FlatParameters.HOUSE_YEAR:
					addRangeValue(q, "data.houseYear", parameters.get(key));
					break;
				case FlatParameters.PUBLISHER_TYPE:
					addPublusherType(q, "publisher.publisherType", parameters.get(key));
					break;
					
				case FlatParameters.BUILDING_TYPE:
					addEnumType(q, "data.flatBuildingType", parameters.get(key), FlatBuildingType.class);
					break;
				case FlatParameters.PRIVATIZED_DORM_TYPE:
					addEnumType(q, "data.privatizedDormType", parameters.get(key), FlatPrivatizedDormType.class);
					break;
				case FlatParameters.RENOVATION_TYPE:
					addEnumType(q, "data.renovationType", parameters.get(key), FlatRenovationType.class);
					break;
				case FlatParameters.PHONE_TYPE:
					addEnumType(q, "data.phoneType", parameters.get(key), FlatPhoneType.class);
					break;
				case FlatParameters.INTERNET_TYPE:
					addEnumType(q, "data.internetType", parameters.get(key), FlatInternetType.class);
					break;
				case FlatParameters.LAVATORY_TYPE:
					addEnumType(q, "data.lavatoryType", parameters.get(key), FlatLavatoryType.class);
					break;
				case FlatParameters.BALCONY_TYPE:
					addEnumType(q, "data.balconyType", parameters.get(key), FlatBalconyType.class);
					break;
				case FlatParameters.BALCONY_GLAZING_TYPE:
					addEnumType(q, "data.balconyGlazingType", parameters.get(key), FlatBalconyGlazingType.class);
					break;
				case FlatParameters.DOOR_TYPE:
					addEnumType(q, "data.doorType", parameters.get(key), FlatDoorType.class);
					break;
				case FlatParameters.PARKING_TYPE:
					addEnumType(q, "data.parkingType", parameters.get(key), FlatParkingType.class);
					break;
				case FlatParameters.FIRNITURE_TYPE:
					addEnumType(q, "data.furnitureType", parameters.get(key), FlatFurnitureType.class);
					break;
				case FlatParameters.FLOOR_TYPE:
					addEnumType(q, "data.floorType", parameters.get(key), FlatFloorType.class);
					break;
				case FlatParameters.SECURITY_TYPES:
					addEnumType(q, "data.securityTypes", parameters.get(key), FlatSecurityType.class);
					break;
				case FlatParameters.MISCELLANEOUS_TYPES:
					addEnumType(q, "data.miscellaneous", parameters.get(key), FlatMiscellaneousType.class);
					break;
					
				case FlatParameters.FLAT_SELL_MORTGAGE_STATUS:
					addEnumType(q, "data.mortgageStatus", parameters.get(key), MortgageStatus.class);
					break;
					
					
				case FlatParameters.FLAT_RENT_PERIOD:
					addEnumType(q, "data.rentPeriod", parameters.get(key), FlatRentPeriodType.class);
					break;
				case FlatParameters.FLAT_RENT_MISCELLANEOUS_TYPES:
					addEnumType(q, "data.rentMiscellaneous", parameters.get(key), FlatRentMiscellaneousType.class);
					break;
					
				default:
					break;
			}
			
			
		}
	
		return q;
	}
	
	
	private void addEnumType(Query<FlatSellRealty> q, String pathName, List<String> range, Class<?> enumClass) {
		if (range == null || range.size() < 1)
			return;
		try {
			final Method values = enumClass.getMethod("values");
	        java.security.AccessController.doPrivileged(
	            new java.security.PrivilegedAction<Void>() {
	                public Void run() {
	                        values.setAccessible(true);
	                        return null;
	                    }
	                });
	        Object[] ojbects = (Object[])values.invoke(null);
			
	        for (Object object : ojbects) {
				logger.info("value {}", object);
			}
	        
		} catch (Exception ex) {
			logger.error("Error", ex);
		}
	}
	
	
	private void addPublusherType(Query<FlatSellRealty> q, String pathName, List<String> range) {
		if (range == null || range.size() < 1)
			return;
		
		List<RealtyPublisherType> types = new ArrayList<RealtyPublisherType>();
		for (RealtyPublisherType type : RealtyPublisherType.values()) {
			for (String typeStr : range) {
				if (typeStr.equalsIgnoreCase(String.valueOf(type.getValue())))
					types.add(type);
			}
		}
		
		q.and(q.criteria(pathName).hasAnyOf(types));
	}
	
	
	private void addRegions(Query<FlatSellRealty> q, String pathName, List<String> range) {
		if (range == null || range.size() < 1)
			return;
		
		q.and(q.criteria(pathName).hasAnyOf(range));
		
	}
	
	private void addHasPhoto(Query<FlatSellRealty> q, String pathName, List<String> range) throws IncorrectParameterValueException {
		if (range != null && range.size() > 0) {
			Boolean hasPhoto = Boolean.parseBoolean(range.get(0));
			if (hasPhoto != null) {
				q.and(q.criteria("hasPhoto").equal(hasPhoto));
			}
		}
		
	}
	
	private void addRangeValue(Query<FlatSellRealty> q, String pathName, List<String> range) throws IncorrectParameterValueException {
		if (range == null)
			throw new IncorrectParameterValueException(pathName +" rage is incorrect!");
		
		if (range.size() == 1) {
			// Указано только значение от
			Long value = parseValue(range.get(0));
			if (value < 1) {
				// не включать в поиск
				
				
			} else {
				// влючать в поиск как с 
				q.and(q.criteria(pathName).greaterThanOrEq(value));
			}
		} else {
			if (range.size() == 2) {
				Long valueFrom = parseValue(range.get(0));
				Long valueTo = parseValue(range.get(1));
				
				if (valueFrom > 0)
					q.and(q.criteria(pathName).greaterThanOrEq(valueFrom));
				if (valueTo > 0)
					q.and(q.criteria(pathName).lessThanOrEq(valueTo));				
			} else {
				throw new IncorrectParameterValueException(pathName +" rage is incorrect!");				
			}
		}
		
	}
	
	
	private Long parseValue(String value) throws IncorrectParameterValueException {
		if (StringUtils.isBlank(value))
			throw new IncorrectParameterValueException("value is null");
		try {
			return Long.parseLong(value);
		} catch (NumberFormatException ex){
			throw new IncorrectParameterValueException("Incorrect integer value", ex);
		}
	}
	
}
