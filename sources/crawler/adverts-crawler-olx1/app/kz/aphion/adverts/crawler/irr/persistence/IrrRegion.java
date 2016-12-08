package kz.aphion.adverts.crawler.irr.persistence;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;


/**
 * Регионы irr
 * 
 * @author denis.krylov
 *
 */
@Entity("adverts.regions.irr")
public class IrrRegion extends BaseEntity {

	/**
	 * Название региона
	 */
	@Property
	public String name;

	/**
	 * Ссылка на регион
	 */
	@Property
	public String key;	
	
	/**
	 * Ссылка на регион
	 */
	@Reference
	public Region region;
	
}
