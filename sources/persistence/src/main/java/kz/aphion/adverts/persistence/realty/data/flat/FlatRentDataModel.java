package kz.aphion.adverts.persistence.realty.data.flat;

import java.util.List;

import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

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
	public FlatRentPeriodType rentPeriod;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	public List<FlatRentMiscellaneousType> rentMiscellaneous;
	
}
