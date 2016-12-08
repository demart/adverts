package kz.aphion.adverts.crawler.olx.persistence;

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
 * Регионы OLX
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="region_olx")
public class OlxRegionEntityTable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="region_olx_seq")
	@SequenceGenerator(name="region_olx_seq",sequenceName="region_olx_seq", initialValue=1, allocationSize=1)
	public Long id;
	
	/**
	 * Название региона
	 */
	@Column(name="name", length=255, nullable=false)
	public String name;
	
	/**
	 * Тип региона
	 */
	@Enumerated
	@Column(name="type", nullable=false)
	public RegionType type;

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
	
}
