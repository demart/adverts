package kz.aphion.adverts.persistence.realty.group;

import org.mongodb.morphia.annotations.Entity;

import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

/**
 * Группа объявлений по продаже квартир
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
@Entity("adverts.realty.flat.sell.group")
public class FlatSellRealtyGroup extends RealtyGroup<FlatSellRealty> {
	
}
