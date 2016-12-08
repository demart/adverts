package kz.aphion.adverts.persistence.realty.group;

import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;

import org.mongodb.morphia.annotations.Entity;

/**
 * Группа объявлений по аренде квартир
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
@Entity("adverts.realty.flat.rent.group")
public class FlatRentRealtyGroup extends RealtyGroup<FlatRentRealty> {

}
