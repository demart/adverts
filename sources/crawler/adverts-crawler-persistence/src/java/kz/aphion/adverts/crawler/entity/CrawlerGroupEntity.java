package kz.aphion.adverts.crawler.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;


/**
 * Группа Crawler'ов по источникам (донорам)
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="crawler_groups")
public class CrawlerGroupEntity extends BaseEntity {
	
	private static final long serialVersionUID = -3761200914449155054L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="crawler_groups_seq")
	@SequenceGenerator(name="crawler_groups_seq",sequenceName="crawler_groups_seq", initialValue=1, allocationSize=1)
	private Long id;
	
	/**
	 * Название группы Crawler
	 */
	@Column(name="name", length=255, nullable=false)
	private String name;
	
	/**
	 * Алиас crawler группы для ведения логов.
	 * Например:
	 * 	KRISHA-[ASTANA-1-ROOM], 
	 * 	KN-[KAZAKHSTAN-1-ROOM], 
	 * 	IRR-[KAZAKHSTAN-1-2-ROOM],
	 */
	@Column(name="alias", length=50, nullable=false)
	private String alias;	
	
	/**
	 * Описание группы Crawler
	 */
	@Column(name="description", length=500)
	private String description;

	/**
	 * Тип источника (донора) информации 
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="source_system_type")
	private CrawlerSourceSystemTypeEnum sourceSystemType;

	/**
	 * Базовый URL для обращения к API системы источника (донора)
	 */
	@Column(name="source_system_base_url")
	private String sourceSystemBaseUrl;
	
	/**
	 * Статус группы Crawler
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="status")
	private CrawlerGroupStatusEnum status;
	
	/**
	 * Включена ли опция работы через Proxy Servers
	 */
	@Column(name="use_proxy_servers", nullable=false)
	private Boolean useProxyServers;
	
	/**
	 * Включена ли опция работы с исползование разных User-Agent
	 */
	@Column(name="use_user_agents", nullable=false)
	private Boolean useUserAgents;

	/**
	 * Список всех Crawler'ов
	 */
	@OneToMany(mappedBy="crawlerGroup")
	private List<CrawlerEntity> allCrawlers;
	
	/**
	 * Список только активных Crawler'ов
	 */
	@OneToMany(mappedBy="crawlerGroup")
	@Where(clause="status='ACTIVE'")
	private List<CrawlerEntity> activeCrawlers;	
	
	
	// ================================================
	// ================================================
	// ================================================		
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSourceSystemBaseUrl() {
		return sourceSystemBaseUrl;
	}

	public void setSourceSystemBaseUrl(String sourceSystemBaseUrl) {
		this.sourceSystemBaseUrl = sourceSystemBaseUrl;
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

	public CrawlerSourceSystemTypeEnum getSourceSystemType() {
		return sourceSystemType;
	}

	public void setSourceSystemType(CrawlerSourceSystemTypeEnum sourceSystemType) {
		this.sourceSystemType = sourceSystemType;
	}

	public CrawlerGroupStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CrawlerGroupStatusEnum status) {
		this.status = status;
	}

	public Boolean getUseProxyServers() {
		return useProxyServers;
	}

	public void setUseProxyServers(Boolean useProxyServers) {
		this.useProxyServers = useProxyServers;
	}

	public Boolean getUseUserAgents() {
		return useUserAgents;
	}

	public void setUseUserAgents(Boolean useUserAgents) {
		this.useUserAgents = useUserAgents;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public List<CrawlerEntity> getAllCrawlers() {
		return allCrawlers;
	}

	public void setAllCrawlers(List<CrawlerEntity> allCrawlers) {
		this.allCrawlers = allCrawlers;
	}

	public List<CrawlerEntity> getActiveCrawlers() {
		return activeCrawlers;
	}

	public void setActiveCrawlers(List<CrawlerEntity> activeCrawlers) {
		this.activeCrawlers = activeCrawlers;
	}	

}
