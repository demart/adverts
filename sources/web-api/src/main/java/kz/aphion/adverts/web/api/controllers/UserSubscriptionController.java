package kz.aphion.adverts.web.api.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.exceptions.DataValidationException;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.exceptions.RecordNotFoundException;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.subscriptions.requests.SubscriptionAdvertsRequestModel;
import kz.aphion.adverts.web.api.models.subscriptions.requests.SubscriptionsRequestModel;
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
	public Response getSubscriptions(SubscriptionsRequestModel model) {
		try {
			logger.debug("USC0001D: getSubscriptions: invoked with the email: [{}]", getUserEmail());

			SubscriptionsRequestModel.validate(model);
			
			ResponseWrapper result = service.getSubscriptions(model);
			logger.debug("USC0002D: getSubscriptions: succesefully completed. User.email [{}]", getUserEmail());
			
			return result.buildResponse();
			
		} catch (AccessDeniedException e) {
			logger.debug("USC0003E: getSubscriptions: Unauthorized exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.FORBIDDEN, false, e.getMessage()).buildResponse();
		} catch (Throwable e) {
			logger.error("USC0004E: getSubscriptions: Exception: " + e.getMessage() + " User.email " + getUserEmail(), e);
			throw e;
		} 
	}
	
	@POST  
	@Path("/{id}/details")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response getSubscriptionDetails(@PathParam("id") String subscriptionId) {
		return null;
		// 20
	}
		
	
	@POST  
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response createSubscription() {
		return null;
		// 30
	}
	
	@POST  
	@Path("/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response updateSubscription() {
		return null;
		// 40
	}
	
	@POST  
	@Path("/update/notification")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response updateSubscriptionNotification() {
		return null;
		// 50
	}
	
	@POST  
	@Path("/update/criteria")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response updateSubscriptionCriteria() {
		return null;
		// 60
	}
	
	
	
	@POST  
	@Path("/advert/list")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response getSubscriptionAdverts(SubscriptionAdvertsRequestModel model) {
		try {
			logger.debug("USC0070D: getSubscriptionAdverts: invoked with the email: [{}]", getUserEmail());
		
			SubscriptionAdvertsRequestModel.validate(model);
			
			ResponseWrapper result = service.getSubscriptionAdverts(model);
			logger.debug("USC0071D: getSubscriptionAdverts: succesefully completed. User.email [{}]", getUserEmail());
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("UPC0072D: getSubscriptionAdverts: Model validation exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (AccessDeniedException e) {
			logger.debug("UPC0073D: getSubscriptionAdverts: Unauthorized exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.FORBIDDEN, false, e.getMessage()).buildResponse();
		} catch (DataValidationException e) {
			logger.error("UPC0074E: getSubscriptionAdverts: Data validation exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (RecordNotFoundException e) {
			logger.error("UPC0075E: getSubscriptionAdverts: Data validation exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (Throwable e) {
			logger.error("UPC0076E: getSubscriptionAdverts: Exception: " + e.getMessage() + " User.email " + getUserEmail(), e);
			throw e;
		}
	}
	
	@POST  
	@Path("/advert/details")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response getSubscriptionAdvertDetails() {
		return null;
	}
	
	
	@POST  
	@Path("/advert/remove")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response removeSubscriptionAdvert() {
		return null;
	}
	
	@POST  
	@Path("/advert/mark/seen")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	@SecuredMethod
	public Response markAsSeenSubscriptionAdvert() {
		return null;
	}
	
}
