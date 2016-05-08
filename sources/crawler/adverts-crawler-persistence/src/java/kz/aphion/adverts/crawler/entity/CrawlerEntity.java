package kz.aphion.adverts.crawler.entity;

import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

/**
 * Сущность описывающая Crawler'ов в группе.
 * Здесь описываются основные поведения каждого потока вычитывающего информацию с системы источника (донора)
 * Сохраняются состояния работы и обработки.
 * 
 * @author artem.demidovich
 *
 */
@Entity
@Table(name="crawler")
public class CrawlerEntity extends BaseEntity {

	private static final long serialVersionUID = 1477900527613134964L;

	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="crawlers_seq")
	@SequenceGenerator(name="crawlers_seq",sequenceName="crawlers_seq", initialValue=1, allocationSize=1)
	private Long id;
	
	/**
	 * Название Crawler.
	 * Например: Выгрузка однокомнатных квартир.
	 * 			Выгрузка офисов и чего нить еще.
	 */
	@Column(name="name", length=255, nullable=false)
	private String name;
	
	/**
	 * Алиас crawler для ведения логов.
	 * Например:
	 * 	[KRISHA-]ASTANA-1-ROOM, 
	 * 	[KRISHA-]KAZAKHSTAN-1-ROOM, 
	 * 	[KRISHA-]KAZAKHSTAN-1-2-ROOM,
	 */
	@Column(name="alias", length=50, nullable=false)
	private String alias;
	
	/**
	 * Статус Crawler'а
	 */
	@Enumerated(EnumType.STRING)
	@Column(name="status",length=25, nullable=false)
	private CrawlerStatusEnum status;
	
	/**
	 * Crawler группа к которой относится данный параметр
	 */
	@ManyToOne
	private CrawlerGroupEntity crawlerGroup;
	
	/**
	 * Время запуска процесса обработки Crawler'a
	 */
	@Column(name="run_every", length=20, nullable=false)
	private String runEvery;
	
	/**
	 * Последнее время активности данного Cralwer
	 */
	@Column(name="last_usage")
	private Calendar lastUsage;
	
	/**
	 * Время последнего успешного сканирование источника (донора).
	 * Предполагается, что при запуске потока Crawler'а, фиксируется время последней успешной обработки,
	 * для того чтобы можно было понимать до какого времени нужно запрашивать данные в следующий раз.
	 * Возможно необхдодимо хранить время источника (донора)
	 */
	@Column(name="last_source_scanned_time")
	private Calendar lastSourceSystemScannedTime;
	
	/**
	 * Очередь куда нужно отправлять обработанные объявления
	 */
	@Column(name="dest_queue_name",length=25, nullable=false)
	private String destinationQueueName;
	
	/**
	 * Список всех параметров Crawler'a
	 */
	@OneToMany(mappedBy="crawler")
	private List<CrawlerParameterEntity> allParameters;
	
	/**
	 * Список только активных параметров
	 */
	@OneToMany(mappedBy="crawler")
	@Where(clause="status='ACTIVE'")
	private List<CrawlerParameterEntity> activeParameters;

	
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public CrawlerStatusEnum getStatus() {
		return status;
	}

	public void setStatus(CrawlerStatusEnum status) {
		this.status = status;
	}

	public CrawlerGroupEntity getCrawlerGroup() {
		return crawlerGroup;
	}

	public void setCrawlerGroup(CrawlerGroupEntity crawlerGroup) {
		this.crawlerGroup = crawlerGroup;
	}

	public String getRunEvery() {
		return runEvery;
	}

	public void setRunEvery(String runEvery) {
		this.runEvery = runEvery;
	}

	public Calendar getLastUsage() {
		return lastUsage;
	}

	public void setLastUsage(Calendar lastUsage) {
		this.lastUsage = lastUsage;
	}

	public Calendar getLastSourceSystemScannedTime() {
		return lastSourceSystemScannedTime;
	}

	public void setLastSourceSystemScannedTime(Calendar lastSourceSystemScannedTime) {
		this.lastSourceSystemScannedTime = lastSourceSystemScannedTime;
	}

	public String getDestinationQueueName() {
		return destinationQueueName;
	}

	public void setDestinationQueueName(String destinationQueueName) {
		this.destinationQueueName = destinationQueueName;
	}

	public List<CrawlerParameterEntity> getAllParameters() {
		return allParameters;
	}

	public void setAllParameters(List<CrawlerParameterEntity> allParameters) {
		this.allParameters = allParameters;
	}

	public List<CrawlerParameterEntity> getActiveParameters() {
		return activeParameters;
	}

	public void setActiveParameters(List<CrawlerParameterEntity> activeParameters) {
		this.activeParameters = activeParameters;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
