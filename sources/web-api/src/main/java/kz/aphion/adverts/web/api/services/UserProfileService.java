package kz.aphion.adverts.web.api.services;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.persistence.users.User;
import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.users.UserProfileModel;
import kz.aphion.adverts.web.api.repositories.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис для работы с профилем пользователя
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
@Named
@RequestScoped
public class UserProfileService extends BaseSecuredService {

	private static Logger logger = LoggerFactory.getLogger(UserProfileService.class);

	@Inject
	UserRepository repository;

	
	public ResponseWrapper getUserProfile() throws AccessDeniedException, ModelValidationException {
		User user = getUser();
		if (user == null)
			throw new AccessDeniedException("User is not authorized");
		
		UserProfileModel model = UserProfileModel.convertToModel(user);
		
		return ResponseWrapper.with(Status.OK, model, "Operation successfully completed");
	}
	
}
