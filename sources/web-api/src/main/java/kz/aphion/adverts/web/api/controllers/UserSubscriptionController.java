package kz.aphion.adverts.web.api.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.subscriptions.requests.UserSubscriptionsRequestModel;
import kz.aphion.adverts.web.api.security.SecuredMethod;
import kz.aphion.adverts.web.api.services.UserSubscriptionService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Контроллер отвечает за работу с подписками пользователей
 * @author artem.demidovich
 *
 * Created at Nov 22, 2017
 */
@Named
@RequestScoped
@Path("/v1/subscription")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserSubscriptionController  extends BaseSecuredController {

	private static Logger logger = LoggerFactory.getLogger(UserSubscriptionController.class);

	@Inject
	UserSubscriptionService service;
	
	/**
	 * Возвразает список подписок пользователя
	 * @return
	 */
	@POST  
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response getUserSubscriptions(UserSubscriptionsRequestModel model) {
		try {
			logger.debug("USC0001D: getUserSubscriptions: invoked with the email: [{}]", getUserEmail());

			UserSubscriptionsRequestModel.validate(model);
			
			ResponseWrapper result = service.getUserSubscriptions(model);
			logger.debug("USC0002D: getUserSubscriptions: succesefully completed. User.email [{}]", getUserEmail());
			
			return result.buildResponse();
			
		} catch (AccessDeniedException e) {
			logger.debug("USC0003E: getUserSubscriptions: Unauthorized exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.FORBIDDEN, false, e.getMessage()).buildResponse();
		} catch (Throwable e) {
			logger.error("USC0004E: getUserSubscriptions: Exception: " + e.getMessage() + " User.email " + getUserEmail(), e);
			throw e;
		} 
	}
	

}
