package kz.aphion.adverts.web.api.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.repositories.AdvertRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс сервис по работе с объявлениями
 * @author artem.demidovich
 *
 * Created at Nov 24, 2017
 */
@Named
@RequestScoped
public class AdvertService extends BaseSecuredService {

	private static Logger logger = LoggerFactory.getLogger(AdvertService.class);

	@Inject
	AdvertRepository repository;
	
	public ResponseWrapper getAdvert(String advertId) {
		return null;
	}
	
}
