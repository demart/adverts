package kz.aphion.adverts.persistence.realty.group;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.Realty;

import org.mongodb.morphia.annotations.Reference;

/**
 * Класс описывает отдельное объявление в группе
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class RealtyGroupItem<T extends Realty> extends BaseEntity {
	
	/**
	 * Ссылка на объявление
	 */
	@Reference
	public T advertId;
	
	/**
	 * Вес объявления в этой группе
	 */
	public int wight;

}
