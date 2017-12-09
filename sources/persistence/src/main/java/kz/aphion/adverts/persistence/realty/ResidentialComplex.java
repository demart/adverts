package kz.aphion.adverts.persistence.realty;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.Region;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

/**
 * Клас описывает Жылые комплексы в релационном виде
 * 
 * @author artem.demidovich
 *
 */
@Entity("adverts.residential.complexes")
@Embedded
public class ResidentialComplex extends BaseEntity {
	
	/**
	 * Название ЖК
	 */
	@Property
	public String complexName;
	
	/**
	 * Адрес объекта (Первая версия)
	 */
	@Property
	public String location;
	
	/**
	 * Компания застройщик ЖК
	 */
	@Property
	public String companyName;
	
	/**
	 * Изображение ЖК (Только одно почему-то)
	 */
	@Property
	public String imageUrl;
	
	/**
	 * Регион ЖК
	 */
	@Reference
	public Region region;
	
	@Property
	public Float latitude;
	
	@Property
	public Float longitude;
	
	
	// ====================================
	// ====================================
	

	/**
	 * Внешний идентифкатор ЖК (для сверки)
	 * 
	 * Используется только когда объект сохраняется в конкретном объявлении,
	 * в будущем должен умереть
	 */
	@Property
	public String externalComplexId;
	
	/**
	 * Внешнее название ЖК (для сверки)
	 * 
	 * Используется только когда объект сохраняется в конкретном объявлении,
	 * в будущем должен умереть
	 */
	@Property
	public String externalComplexName;
	
	
}
