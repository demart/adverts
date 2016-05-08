package kz.aphion.adverts.crawler.core.models;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.crawler.entity.UserAgentEntity;
import kz.aphion.adverts.crawler.entity.UserAgentStatusEnum;
import kz.aphion.adverts.crawler.entity.UserAgentTypeEnum;

/**
 * Модель User-Agent
 * 
 * @author artem.demidovich
 *
 */
public class UserAgentModel {

	public Long id;
	
	/**
	 * Название User-Agent
	 */
	public String name;

	/**
	 * Описание User-Agent
	 */
	public String description;
	
	/**
	 * Сам User-Agent
	 */
	public String userAgent;
	
	/**
	 * Тип или предназначение User-Agent
	 */
	public UserAgentTypeEnum type;
	
	/**
	 * Статус User-Agent
	 */
	public UserAgentStatusEnum status;

	
	
	public static UserAgentModel buildFromEntity(UserAgentEntity entity) throws CrawlerException {
		if (entity == null)
			throw new CrawlerException("Can't convert null entity to a model");
		
		UserAgentModel model = new UserAgentModel();
		model.description = entity.getDescription();
		model.id = entity.getId();
		model.name = entity.getName();
		model.status = entity.getStatus();
		model.type = entity.getType();
		model.userAgent = entity.getUserAgent();
		
		return model;
	}
	
	
}
