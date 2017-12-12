package kz.aphion.adverts.models.realty.data;

import java.util.List;

import kz.aphion.adverts.models.ModelUtils;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.adverts.Advert;

/**
 * Информация о квартире для аренды
 * 
 * @author artem.demidovich
 *
 */
public class FlatRentDataModel extends FlatRealtyBaseDataModel {

	/**
	 * Срок сдачи недвижимости
	 */
	//protected FlatRentPeriodType rentPeriod;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	//protected List<FlatRentMiscellaneousType> rentMiscellaneous;

	public FlatRentDataModel(Advert advert){
		super(advert);
	}	
	
	public FlatRentPeriodType getRentPeriod() {
		return ModelUtils.getEnumFromObject(FlatRentPeriodType.class, getData().get("rentPeriod"));
	}

	public void setRentPeriod(FlatRentPeriodType rentPeriod) {
		getData().put("rentPeriod", rentPeriod);
	}

	public List<FlatRentMiscellaneousType> getRentMiscellaneous() {
		return ModelUtils.getEnumsFromObject(FlatRentMiscellaneousType.class, advert.data.get("rentMiscellaneous"));
	}

	public void setRentMiscellaneous(List<FlatRentMiscellaneousType> rentMiscellaneous) {
		advert.data.put("rentMiscellaneous", rentMiscellaneous);
	}
}
