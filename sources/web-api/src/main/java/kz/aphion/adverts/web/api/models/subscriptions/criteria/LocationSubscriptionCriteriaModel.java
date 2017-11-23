package kz.aphion.adverts.web.api.models.subscriptions.criteria;

import java.util.List;

import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyLocationSubscriptionCriteria;
import kz.aphion.adverts.web.api.models.geo.GeoLocationModel;
import kz.aphion.adverts.web.api.models.geo.RegionModel;

import org.mongodb.morphia.annotations.Embedded;

/**
 * Модель критерия поиска по местонахождению
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class LocationSubscriptionCriteriaModel {
	// Можем искать по интересующим регионам
	// Можем искать по интересующим ЖК
	// Можем искать по радиусу на карте
	// Можем комбинировать даные условия
	
	/**
	 * Интересующие регионы (районы или еще что-то)
	 */
	public List<RegionModel> regions;
	
	/**
	 * Возможные расположение объекта на карте
	 */
	public List<GeoLocationModel> mapLocations;
	
	
	public static LocationSubscriptionCriteriaModel convertToModel(RealtyLocationSubscriptionCriteria criteria) {
		if (criteria == null)
			return null;
		
		LocationSubscriptionCriteriaModel model = new  LocationSubscriptionCriteriaModel();

		model.regions = RegionModel.convertToModel(criteria.regions);
		model.mapLocations = GeoLocationModel.convertToModel(criteria.mapLocations);
		
		return model;
	}
	
	
}
