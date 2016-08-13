package kz.aphion.adverts.persistence.realty.data.flat;

import org.mongodb.morphia.annotations.Property;

import kz.aphion.adverts.persistence.realty.types.MortgageStatus;

/**
 * Информация о квартире для продажи
 * 
 * @author artem.demidovich
 *
 */
public class FlatSellData extends FlatRealtyBaseData {

	/**
	 * В залоге или нет
	 */
	@Property
	public MortgageStatus mortgageStatus;
	
}
