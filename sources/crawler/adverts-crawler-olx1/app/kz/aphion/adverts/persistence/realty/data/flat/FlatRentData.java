package kz.aphion.adverts.persistence.realty.data.flat;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;

/**
 * Информация о квартире для аренды
 * 
 * @author artem.demidovich
 *
 */
public class FlatRentData extends FlatRealtyBaseData {

	/**
	 * Срок сдачи недвижимости
	 */
	@Property
	public FlatRentPeriodType rentPeriod;
	
	/**
	 * Разные допольнительные опции для аренды
	 */
	@Embedded
	public List<FlatRentMiscellaneousType> rentMiscellaneous;
	
}
