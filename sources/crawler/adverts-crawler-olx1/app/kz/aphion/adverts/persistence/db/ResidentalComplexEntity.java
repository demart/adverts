package kz.aphion.adverts.persistence.db;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Клас описывает Жылые комплексы в релационном виде
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="apartment_complex")
public class ResidentalComplexEntity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="apartment_complex_seq")
	@SequenceGenerator(name="apartment_complex_seq",sequenceName="apartment_complex_seq", initialValue=1, allocationSize=1)
	public Long id;
	
	/**
	 * Название ЖК
	 */
	@Column(name="name")
	public String complexName;
	
	/**
	 * Адрес объекта (Первая версия)
	 */
	@Column(name="location")
	public String location;
	
	/**
	 * Компания застройщик ЖК
	 */
	@Column(name="company")
	public String companyName;
	
	/**
	 * Изображение ЖК (Только одно почему-то)
	 */
	@Column(name="image_url")
	public String imageUrl;
	
	/**
	 * Регион ЖК
	 */
	@ManyToOne
	public RegionEntity region;
	
	@Column(name="lat")
	public Float latitude;
	
	@Column(name="lon")
	public Float longitude;
	
	
}
