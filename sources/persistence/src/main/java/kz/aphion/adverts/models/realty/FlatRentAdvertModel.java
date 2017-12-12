package kz.aphion.adverts.models.realty;

import kz.aphion.adverts.models.AdvertModel;
import kz.aphion.adverts.models.realty.data.FlatRentDataModel;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.realty.RealtyType;

/**
 * Модель поверь объявления для более удобной работы с объявлением об аренде недвижимости
 * @author artem.demidovich
 *
 * Created at Dec 12, 2017
 */
public class FlatRentAdvertModel extends AdvertModel {

	private FlatRentDataModel dataModel;
	
	public FlatRentAdvertModel(Advert advert) {
		super(advert);
		dataModel = new FlatRentDataModel(advert);
	}	
	
	public void setAdvertSubType(RealtyType realtyType){
		super.setSubType(realtyType.toString());
	}

	// TODO: ???
	public RealtyType getAdvertSubType(){
		String subtype = super.getSubType();
		return RealtyType.valueOf(subtype);
	}

	public FlatRentDataModel getDataModel() {
		return dataModel;
	}
	
}
