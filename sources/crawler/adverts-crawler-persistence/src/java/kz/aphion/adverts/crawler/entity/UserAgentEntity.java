package kz.aphion.adverts.crawler.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * 
 * Сущность для хранения информации о доступных User-Agent. 
 * Используется для маскировки User-Agent при вызовах в Crawlerах
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="crawler_user_agents")
public class UserAgentEntity extends BaseEntity {
	
	private static final long serialVersionUID = 2158345800090202404L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="crawler_user_agent_seq")
	@SequenceGenerator(name="crawler_user_agent_seq",sequenceName="crawler_user_agent_seq", initialValue=1, allocationSize=1)
	private Long id;
	
	/**
	 * Название User-Agent
	 */
	@Column(name="name", length=255, nullable=false)
	private String name;

	/**
	 * Описание User-Agent
	 */
	@Column(name="description", length=500)
	private String description;
	
	/**
	 * Сам User-Agent
	 */
	@Column(name="user_agent", length=500, nullable=false)
	private String userAgent;
	
	/**
	 * Тип или предназначение User-Agent
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="type", length=20, nullable=false)
	private UserAgentTypeEnum type;
	
	/**
	 * Статус User-Agent
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="status", length=20, nullable=false)
	private UserAgentStatusEnum status;

	// ================================================
	// ================================================
	// ================================================	
	
	public Long getId() {
		return id;
	}

	public String getUserAgent() {
		return userAgent;
	}

	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
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

	public UserAgentTypeEnum getType() {
		return type;
	}

	public void setType(UserAgentTypeEnum type) {
		this.type = type;
	}

	public UserAgentStatusEnum getStatus() {
		return status;
	}

	public void setStatus(UserAgentStatusEnum status) {
		this.status = status;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
