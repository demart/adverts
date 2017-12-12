package kz.aphion.adverts.models.realty.data;

import kz.aphion.adverts.models.ModelUtils;
import kz.aphion.adverts.persistence.adverts.Advert;


/**
 * Информация о квартире для продажи
 * 
 * @author artem.demidovich
 *
 */
public class FlatSellDataModel extends FlatRealtyBaseDataModel {

	/**
	 * В залоге или нет
	 */
	//public MortgageStatus mortgageStatus;
	
	public FlatSellDataModel(Advert advert){
		this.advert = advert;
	}

	public MortgageStatus getMortgageStatus() {
		return ModelUtils.getEnumFromObject(MortgageStatus.class, getData().get("mortgageStatus"));
	}

	public void setMortgageStatus(MortgageStatus mortgageStatus) {
		getData().put("mortgageStatus", mortgageStatus);
	}	
	
	
}
