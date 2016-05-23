package kz.aphion.adverts.crawler.kn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import kz.aphion.adverts.persistence.db.ResidentalComplexEntity;

/**
 * 
 * Справочник ЖК для мапинг на внутренний 
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="apartment_complex_kn")
public class KnResidentalComplexEntity {
	
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="apartment_complex_kn_seq")
	@SequenceGenerator(name="apartment_complex_kn_seq",sequenceName="apartment_complex_kn_seq", initialValue=1, allocationSize=1)
	public Long id;
		
	/**
	 * Код ЖК в спарвочнике источника сайта крыша
	 */
	@Column(name="key")
	public String key;
	
	/**
	 * Название ЖК в справочнике
	 */
	@Column(name="name")
	public String name;
	
	/**
	 * Ссылка на ЖК во внутреннем справочнике
	 */
	@ManyToOne
	public ResidentalComplexEntity complex;
	
}
