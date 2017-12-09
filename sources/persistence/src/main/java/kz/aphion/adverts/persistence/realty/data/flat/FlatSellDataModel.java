package kz.aphion.adverts.persistence.realty.data.flat;

import kz.aphion.adverts.persistence.realty.MortgageStatus;

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
	public MortgageStatus mortgageStatus;
	
}
