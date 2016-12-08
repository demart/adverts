package kz.aphion.adverts.crawler.olx.persistence;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.RegionType;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Регионы OLX
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.regions.olx")
public class OlxRegion extends BaseEntity {
	
	/**
	 * Название региона
	 */
	@Property
	public String name;
	
	/**
	 * Тип региона
	 */
	@Property
	public RegionType type;

	/**
	 * Ссылка на регион
	 */
	@Property
	public Long key;	
	
	/**
	 * Ссылка на регион
	 */
	@Reference
	public Region region;
	
}
