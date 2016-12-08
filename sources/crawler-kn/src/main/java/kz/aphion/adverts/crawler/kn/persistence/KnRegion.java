package kz.aphion.adverts.crawler.kn.persistence;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;


/**
 * Регионы kn
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.regions.kn")
public class KnRegion extends BaseEntity {
	
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
