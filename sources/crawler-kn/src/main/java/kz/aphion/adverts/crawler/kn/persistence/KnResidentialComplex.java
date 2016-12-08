package kz.aphion.adverts.crawler.kn.persistence;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * 
 * Справочник ЖК для мапинг на внутренний 
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.residential.complexes.kn")
public class KnResidentialComplex extends BaseEntity {

	/**
	 * Код ЖК в спарвочнике источника сайта крыша
	 */
	@Property
	public String key;
	
	/**
	 * Название ЖК в справочнике
	 */
	@Property
	public String name;
	
	/**
	 * Ссылка на регион к которому принадлежит данный ЖК
	 */
	@Reference
	public Region region;	
	
	/**
	 * Ссылка на ЖК во внутреннем справочнике
	 */
	@Reference
	public ResidentialComplex complex;
	
}
