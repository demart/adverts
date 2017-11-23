package kz.aphion.adverts.web.api.models.geo;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;

/**
 * Жилые комплексы
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
public class ResidentialComplexModel {
	/**
	 * Название ЖК
	 */
	public String complexName;
	
	/**
	 * Адрес объекта (Первая версия)
	 */
	public String location;
	
	/**
	 * Компания застройщик ЖК
	 */
	public String companyName;
	
	/**
	 * Изображение ЖК (Только одно почему-то)
	 */
	public String imageUrl;
	
	/**
	 * Регион ЖК
	 */
	public RegionModel region;
	
	
	public Float latitude;
	
	public Float longitude;
	
	
	// ====================================
	// ====================================
	

	/**
	 * Внешний идентифкатор ЖК (для сверки)
	 * 
	 * Используется только когда объект сохраняется в конкретном объявлении,
	 * в будущем должен умереть
	 */
	public String externalComplexId;
	
	/**
	 * Внешнее название ЖК (для сверки)
	 * 
	 * Используется только когда объект сохраняется в конкретном объявлении,
	 * в будущем должен умереть
	 */
	public String externalComplexName;
	
	
	public static List<ResidentialComplexModel> convertToModel(List<ResidentialComplex> complexes) {
		if (complexes == null || complexes.size() == 0) 
			return null;
		
		List<ResidentialComplexModel> models = new ArrayList<ResidentialComplexModel>();
		
		for (ResidentialComplex residentialComplex : complexes) {
			ResidentialComplexModel model = ResidentialComplexModel.convertToModel(residentialComplex);
			if (model != null)
				models.add(model);
		}
		
		return models;
	}


	public static ResidentialComplexModel convertToModel(ResidentialComplex residentialComplex) {
		if (residentialComplex == null) 
			return null;
		
		ResidentialComplexModel model = new ResidentialComplexModel();
		
		model.companyName = residentialComplex.companyName;
		model.complexName = residentialComplex.complexName;
		model.externalComplexId = residentialComplex.externalComplexId;
		model.externalComplexName = residentialComplex.externalComplexName;
		model.imageUrl = residentialComplex.imageUrl;
		model.latitude = residentialComplex.latitude;
		model.location = residentialComplex.location;
		model.longitude = residentialComplex.longitude;
		model.region = RegionModel.convertToModel(residentialComplex.region);
		
		return model;
	}
	
	
}
