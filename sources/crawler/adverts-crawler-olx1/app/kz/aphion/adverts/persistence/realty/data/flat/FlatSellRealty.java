package kz.aphion.adverts.persistence.realty.data.flat;

import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.group.FlatSellRealtyGroup;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * Коллекция объектов по продаже квартир
 * @author artem.demidovich
 *
 */
@Entity("adverts.realty.flat.sell")
public class FlatSellRealty extends Realty {

	public FlatSellRealty() {
		super();
		this.operation = RealtyOperationType.SELL;
		this.type = RealtyType.FLAT;
	}
	
	/**
	 * Данные квартир на продажу
	 */
	@Embedded
	public FlatSellData data;
	
	/**
	 * Ссылка на группу объявлений по продаже.
	 * По умолчанию каждое объявление должно принадлежать какой-то группе, даже если это одно объявление
	 */
	@Reference
	public FlatSellRealtyGroup group;
}
