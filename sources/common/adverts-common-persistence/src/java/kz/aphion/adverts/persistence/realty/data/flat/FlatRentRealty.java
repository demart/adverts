package kz.aphion.adverts.persistence.realty.data.flat;

import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.group.FlatRentRealtyGroup;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Reference;

/**
 * Коллекция объектов по аренде квартир
 * @author artem.demidovich
 *
 */
@Entity("adverts.realty.flat.rent")
public class FlatRentRealty extends Realty {


	public FlatRentRealty() {
		super();
		this.operation = RealtyOperationType.RENT;
		this.type = RealtyType.FLAT;
	}
	
	/**
	 * Данные об арнеде квартиры
	 */
	@Embedded
	public FlatRentData data;
	
	/**
	 * Ссылка на группу объявлений по аренде.
	 * По умолчанию каждое объявление должно принадлежать какой-то группе, даже если это одно объявление
	 */
	@Reference
	public FlatRentRealtyGroup group;	
}
