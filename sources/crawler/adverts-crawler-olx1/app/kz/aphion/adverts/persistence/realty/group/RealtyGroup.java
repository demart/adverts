package kz.aphion.adverts.persistence.realty.group;

import java.util.List;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Reference;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.Realty;

/**
 * Группа для схожих объявлений о недвижимости по определенным признакам
 * 
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class RealtyGroup<T extends Realty> extends BaseEntity {

	/**
	 * Список объявлений в группе
	 */
	@Embedded
	public List<RealtyGroupItem<T>> adverts;
	
	/**
	 * Лучшее объявлении в группе
	 */
	@Reference
	public T advert;
	
	
}
