package kz.aphion.adverts.crawler.core.models;

import kz.aphion.adverts.crawler.core.exceptions.CrawlerException;
import kz.aphion.adverts.persistence.crawler.UserAgent;
import kz.aphion.adverts.persistence.crawler.UserAgentStatusEnum;
import kz.aphion.adverts.persistence.crawler.UserAgentTypeEnum;

/**
 * Модель User-Agent
 * 
 * @author artem.demidovich
 *
 */
public class UserAgentModel {

	public String id;
	
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

	
	
	public static UserAgentModel buildFromEntity(UserAgent entity) throws CrawlerException {
		if (entity == null)
			throw new CrawlerException("Can't convert null entity to a model");
		
		UserAgentModel model = new UserAgentModel();
		model.description = entity.description;
		model.id = entity.id.toHexString();
		model.name = entity.name;
		model.status = entity.status;
		model.type = entity.type;
		model.userAgent = entity.userAgent;
		
		return model;
	}
	
	
}
