package kz.aphion.adverts.models.realty;

import kz.aphion.adverts.models.AdvertModel;
import kz.aphion.adverts.models.realty.data.FlatSellDataModel;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.realty.RealtyType;

/**
 * Модель поверь объявления для более удобной работы с объявлением о недвижимости
 * @author artem.demidovich
 *
 * Created at Dec 12, 2017
 */
public class FlatSellAdvertModel extends AdvertModel {

	private FlatSellDataModel dataModel;
	
	public FlatSellAdvertModel(Advert advert) {
		super(advert);
		dataModel = new FlatSellDataModel(advert);
	}	
	
	public void setAdvertSubType(RealtyType realtyType){
		super.setSubType(realtyType.toString());
	}

	// TODO: ???
	public RealtyType getAdvertSubType(){
		String subtype = super.getSubType();
		return RealtyType.valueOf(subtype);
	}

	public FlatSellDataModel getDataModel() {
		return dataModel;
	}
	
}
