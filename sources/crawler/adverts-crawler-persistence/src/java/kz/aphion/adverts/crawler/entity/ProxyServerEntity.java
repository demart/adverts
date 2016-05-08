package kz.aphion.adverts.crawler.entity;

import java.util.Calendar;

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
 * Сущность для хранения информации о прокси серверах для скрытого посещения доноров
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="crawler_proxies")
public class ProxyServerEntity extends BaseEntity {
	
	private static final long serialVersionUID = -2318424654766332158L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="crawler_proxies_seq")
	@SequenceGenerator(name="crawler_proxies_seq",sequenceName="crawler_proxies_seq", initialValue=1, allocationSize=1)
	private Long id;
	
	/**
	 * Название прокси сервера
	 */
	@Column(name="name", length=255, nullable=false)
	private String name;

	/**
	 * Описание прокси сервера
	 */
	@Column(name="description", length=500)
	private String description;
	
	/**
	 * Хост прокси сервера
	 */
	@Column(name="host", length=500, nullable=false)
	private String host;
	
	/**
	 * Порт прокси сервера
	 */
	@Column(name="port", length=500, nullable=false)
	private Integer port;
	
	/**
	 * Тип авторизации прокси сервера
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="auth_type", length=50, nullable=false)
	private ProxyServerAuthTypeEnum authType;
	
	/**
	 * логин авторизации прокси сервера
	 */
	@Column(name="login", length=50)
	private String login;
	
	/**
	 * Пароль прокси сервера
	 */
	@Column(name="password", length=50)
	private String password;

	/**
	 * Статус прокси сервера
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="status", length=20, nullable=false)
	private ProxyServerStatusEnum status;
	
	/**
	 * Недоступность сервера с этого времени
	 */
	@Column(name="inactive_from")
	private Calendar inactiveFrom;
	
	/**
	 * Последная проверка прокси сервера
	 */
	@Column(name="last_check")
	private Calendar lastCheck;
	
	/**
	 * Последнее использование прокси сервера
	 */
	@Column(name="last_usage")
	private Calendar lastUsage;
	
	/**
	 * Кол-во использований прокси сервера
	 */
	@Column(name="usage_count")
	private Long usageCount;
	
	/**
	 * Тип прокси сервера
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="type", length=20, nullable=false)
	private ProxyServerTypeEnum type;

	
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

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public ProxyServerAuthTypeEnum getAuthType() {
		return authType;
	}

	public void setAuthType(ProxyServerAuthTypeEnum authType) {
		this.authType = authType;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public ProxyServerStatusEnum getStatus() {
		return status;
	}

	public void setStatus(ProxyServerStatusEnum status) {
		this.status = status;
	}

	public Calendar getInactiveFrom() {
		return inactiveFrom;
	}

	public void setInactiveFrom(Calendar inactiveFrom) {
		this.inactiveFrom = inactiveFrom;
	}

	public Calendar getLastCheck() {
		return lastCheck;
	}

	public void setLastCheck(Calendar lastCheck) {
		this.lastCheck = lastCheck;
	}

	public Calendar getLastUsage() {
		return lastUsage;
	}

	public void setLastUsage(Calendar lastUsage) {
		this.lastUsage = lastUsage;
	}

	public Long getUsageCount() {
		return usageCount;
	}

	public void setUsageCount(Long usageCount) {
		this.usageCount = usageCount;
	}

	public ProxyServerTypeEnum getType() {
		return type;
	}

	public void setType(ProxyServerTypeEnum type) {
		this.type = type;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
}
