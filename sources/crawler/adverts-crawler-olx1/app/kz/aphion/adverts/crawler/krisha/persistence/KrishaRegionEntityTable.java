package kz.aphion.adverts.crawler.krisha.persistence;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import kz.aphion.adverts.persistence.RegionType;
import kz.aphion.adverts.persistence.db.RegionEntity;

/**
 * Регионы крыши
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="region_krisha")
public class KrishaRegionEntityTable {

	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="region_krisha_seq")
	@SequenceGenerator(name="region_krisha_seq",sequenceName="region_krisha_seq", initialValue=1, allocationSize=1)
	public Long id;
	
	/**
	 * Название региона
	 */
	@Column(name="name", length=255, nullable=false)
	public String name;

	/**
	 * Название региона
	 */
	@Column(name="alias", length=100)
	public String alias;
	
	/**
	 * Тип региона
	 */
	@Enumerated
	@Column(name="type", nullable=false)
	public RegionType type;
	
	/**
	 * Уровень вложенности
	 */
	@Column(name="level")
	public String level;

	/**
	 * Ссылка на регион
	 */
	@Column(name="key")
	public Long key;	
	
	/**
	 * Ссылка на регион
	 */
	@ManyToOne
	public RegionEntity region;
	
	/**
	 * Приближение на карте
	 */
	@Column(name="zoom")
	public String mapZoom;
	
	/**
	 * Расположение на карте
	 */
	@Column(name="latitude")
	public Float mapLatitude;
	
	/**
	 * Расположение на карте
	 */
	@Column(name="longitude")
	public Float mapLongitude;
	
}
