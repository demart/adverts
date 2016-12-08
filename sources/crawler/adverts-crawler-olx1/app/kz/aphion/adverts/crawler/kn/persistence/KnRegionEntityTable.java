package kz.aphion.adverts.crawler.kn.persistence;

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
 * Регионы kn
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="region_kn")
public class KnRegionEntityTable {

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="region_kn_seq")
	@SequenceGenerator(name="region_kn_seq",sequenceName="region_kn_seq", initialValue=1, allocationSize=1)
	public Long id;
	
	/**
	 * Название региона
	 */
	@Column(name="name", length=255, nullable=false)
	public String name;



	/**
	 * Ссылка на регион
	 */
	@Column(name="key")
	public String key;	
	
	/**
	 * Ссылка на регион
	 */
	@ManyToOne
	public RegionEntity region;
	
	
	
}
