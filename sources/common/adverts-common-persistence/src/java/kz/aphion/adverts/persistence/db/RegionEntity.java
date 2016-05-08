package kz.aphion.adverts.persistence.db;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import kz.aphion.adverts.persistence.RegionType;

/**
 * Описание таблицы регионов в реляционном виде
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="region")
public class RegionEntity {
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="region_seq")
	@SequenceGenerator(name="region_seq",sequenceName="region_seq", initialValue=1, allocationSize=1)
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
	 * Родитель региона
	 */
	@ManyToOne
	public RegionEntity parent;
	
	/**
	 * Регионы
	 */
	@OneToMany(mappedBy="parent")
	public List<RegionEntity> regions;
	
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
