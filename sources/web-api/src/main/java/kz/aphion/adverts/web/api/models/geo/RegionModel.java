package kz.aphion.adverts.web.api.models.geo;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.RegionType;

/**
 * Модель региона
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class RegionModel {
	/**
	 * Код справочника для поиска, пока сам справочник лежит в реляционной структуре
	 */
	public String code;
	
	/**
	 * Название региона
	 */
	public String name;
	
	/**
	 * Тип региона
	 */
	public RegionType type;
	
	/**
	 * Уровень вложенности
	 */
	public String level;
	
	/**
	 * Родитель региона
	 */
	public RegionModel parent;
	
	/**
	 * Приближение на карте
	 */
	public String mapZoom;
	
	/**
	 * Расположение на карте
	 */
	public Float mapLatitude;
	
	/**
	 * Расположение на карте
	 */
	public Float mapLongitude;
	
	public static List<RegionModel> convertToModel(List<Region> regions) {
		if (regions == null || regions.size() == 0)
			return null;
		
		List<RegionModel> models = new ArrayList<RegionModel>();

		for (Region region : regions) {
			RegionModel model = RegionModel.convertToModel(region);
			if (model != null)
				models.add(model);
		}
		
		return models;
	}
	
	
	public static RegionModel convertToModel(Region region) {
		if (region == null) 
			return null;
		
		RegionModel model = new RegionModel();
		model.code = region.code;
		model.level = region.level;
		model.mapLatitude = region.mapLatitude;
		model.mapLongitude = region.mapLongitude;
		model.mapZoom = region.mapZoom;
		model.name = region.name;
		model.parent = region.parent != null ? RegionModel.convertToModel(region.parent) : null;
		model.type = region.type;
		
		return model;
	}
	
}
