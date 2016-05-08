package kz.aphion.adverts.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Сущность для описания параметров каждого отдельного  Crawler'a
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="crawler_parameters")
public class CrawlerParameterEntity extends BaseEntity {

	private static final long serialVersionUID = 520599579895608134L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="crawler_parameters_seq")
	@SequenceGenerator(name="crawler_parameters_seq",sequenceName="crawler_parameters_seq", initialValue=1, allocationSize=1)
	private Long id;
	
	/**
	 * Название Crawler.
	 * Например: Выгрузка однокомнатных квартир.
	 * 			Выгрузка офисов и чего нить еще.
	 */
	@Column(name="name", length=255, nullable=false)
	private String name;
	
	/**
	 * Описание параметра Crawler
	 */
	@Column(name="description", length=500)
	private String description;
	
	/**
	 * Crawler к которому относится данный параметр
	 */
	@ManyToOne
	private CrawlerEntity crawler;
	
	/**
	 * Ключ параметра Crawler
	 */
	@Column(name="key", length=50, nullable=false)
	private String key;
	
	/**
	 * Значние параметра Crawler
	 */
	@Column(name="value", length=255)
	private String value;	
	
	/**
	 * Статус-состояние параметра для Crawler'а
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="status", length=255, nullable=false)
	private CrawlerParameterStatusEnum status;

	// ================================================
	// ================================================
	// ================================================	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CrawlerEntity getCrawler() {
		return crawler;
	}

	public void setCrawler(CrawlerEntity crawler) {
		this.crawler = crawler;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public CrawlerParameterStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CrawlerParameterStatusEnum status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
