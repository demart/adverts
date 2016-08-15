package kz.aphion.adverts.web.api.search.realty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.web.api.exceptions.IncorrectParameterValueException;
import kz.aphion.adverts.web.api.providers.MongoDbProvider;
import kz.aphion.adverts.web.api.query.SearchAdvertQuery;
import kz.aphion.adverts.web.api.search.AdvertSearch;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;


public class FlatRentRealtySearcher implements AdvertSearch {

	@Override
	public List<Object> search(SearchAdvertQuery query) throws Exception {
		// Построить запрос
		Query<FlatRentRealty> q = builQuery(query.parameters);
		
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
		Query<FlatRentRealty> q = builQuery(query.parameters);
		// Выполнить
		Long result = q.countAll();
		
		return result;
	}

	
	private Query<FlatRentRealty> builQuery(HashMap<String, List<String>> parameters) throws Exception {
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		
		Query<FlatRentRealty> q = ds.createQuery(FlatRentRealty.class);
		
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
				default:
					break;
			}
			
			
		}
	
		return q;
	}
	
	private void addPublusherType(Query<FlatRentRealty> q, String pathName, List<String> range) {
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
	
	
	private void addRegions(Query<FlatRentRealty> q, String pathName, List<String> range) {
		if (range == null || range.size() < 1)
			return;
		
		q.and(q.criteria(pathName).hasAnyOf(range));
		
	}
	
	private void addHasPhoto(Query<FlatRentRealty> q, String pathName, List<String> range) throws IncorrectParameterValueException {
		if (range != null && range.size() > 0) {
			Boolean hasPhoto = Boolean.parseBoolean(range.get(0));
			if (hasPhoto != null) {
				q.and(q.criteria("hasPhoto").equal(hasPhoto));
			}
		}
		
	}
	
	private void addRangeValue(Query<FlatRentRealty> q, String pathName, List<String> range) throws IncorrectParameterValueException {
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
