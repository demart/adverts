package kz.aphion.adverts.web.api.models.geo;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyGeoLocation;


/**
 * Возможные расположение объекта на карте
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class GeoLocationModel {
	
	public String longitude;
	
	public String latitude;
	
	/**
	 * Радиус от точки (возмжоно это должен быть int)
	 */
	public Float radus;

	public static List<GeoLocationModel> convertToModel(List<RealtyGeoLocation> mapLocations) {
		if (mapLocations == null || mapLocations.size() == 0)
			return null;
		
		List<GeoLocationModel> models = new ArrayList<GeoLocationModel>();
		
		for (RealtyGeoLocation realtyGeoLocation : mapLocations) {
			GeoLocationModel model = GeoLocationModel.covertToModel(realtyGeoLocation);
			if (model != null)
				models.add(model);
		}
		
		return models;
	}

	private static GeoLocationModel covertToModel(RealtyGeoLocation realtyGeoLocation) {
		if (realtyGeoLocation == null)
			return null;
		
		GeoLocationModel model = new GeoLocationModel();
		
		model.latitude = realtyGeoLocation.latitude;
		model.longitude = realtyGeoLocation.longitude;
		model.radus = realtyGeoLocation.radus;
		
		return model;
	}
}
