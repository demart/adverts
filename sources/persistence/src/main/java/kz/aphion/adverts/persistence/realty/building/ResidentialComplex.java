package kz.aphion.adverts.persistence.realty.building;

import java.util.List;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.NotSaved;
import org.mongodb.morphia.annotations.Property;


/**
 * Жилой комплекс
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.residental.complex")
public class ResidentialComplex {

	/**
	 * Внешний идентифкатор ЖК (для сверки)
	 */
	@Property
	public String externalComplexId;
	
	/**
	 * Внешнее название ЖК (для сверки)
	 */
	@Property
	public String externalComplexName;
	
	/**
	 * Идентификатор записи в релационном таблице (пока так)
	 */
	@Property("relationId")
	public Long relationId;
	
	/**
	 * Название ЖК
	 */
	@Property
	public String name;
	
	/**
	 * Список домов в этом жилом комплексе
	 */
	@NotSaved
	public List<Building> buildings;
	
}
