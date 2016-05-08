package kz.aphion.adverts.crawler.entity;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import play.db.jpa.GenericModel;

/**
 * Базовый класс для хранения повторяющихся параметров
 * 
 * 
 * @author artem.demidovich
 *
 */
@MappedSuperclass
public class BaseEntity extends GenericModel {

	private static final long serialVersionUID = -1745416859332958218L;

	public BaseEntity() {
		super();
		Calendar calendar = Calendar.getInstance();
		createdDate = calendar;
		modifiedDate = calendar;
	} 
	
	/**
	 * Дата создания записи
	 */
	@Column(name="created_date")
	private Calendar createdDate;
	
	/**
	 * Дата последнего изменения
	 */
	@Column(name="modified_date")
	private Calendar modifiedDate;
	
	/**
	 * Дата последнего лица изменивщего запись
	 */
	@Column(name="modifier_name")
	private String modifierName;

	
	@PrePersist
	void onCreate() {
		this.setCreatedDate(Calendar.getInstance());
		this.setModifiedDate(Calendar.getInstance());
	}
	
	@PreUpdate
	void onPersist() {
		this.setModifiedDate(Calendar.getInstance());
	 }
	
	
	public Calendar getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Calendar createdDate) {
		this.createdDate = createdDate;
	}

	public Calendar getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Calendar modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public String getModifierName() {
		return modifierName;
	}

	public void setModifierName(String modifierName) {
		this.modifierName = modifierName;
	}
	
}
