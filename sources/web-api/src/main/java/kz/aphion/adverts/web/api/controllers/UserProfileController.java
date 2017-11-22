package kz.aphion.adverts.web.api.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.web.api.exceptions.AccessDeniedException;
import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.security.SecuredMethod;
import kz.aphion.adverts.web.api.services.UserProfileService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Named
@RequestScoped
@Path("/v1/user/profile")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserProfileController  extends BaseSecuredController {

	private static Logger logger = LoggerFactory.getLogger(UserProfileController.class);

	@Inject
	UserProfileService service;
	
	@GET  
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@SecuredMethod
	public Response getUserProfile() {
		logger.debug("UPC0001D: getUserProfile: invoked with the email: [{}]", getUserEmail());
		
		try {
			
			ResponseWrapper result = service.getUserProfile();
			logger.debug("UPC0002D: getUserProfile: succesefully completed. User.email [{}]", getUserEmail());
			
			return result.buildResponse();
			
		} catch (AccessDeniedException e) {
			logger.debug("UPC0003E: getUserProfile: Unauthorized exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.FORBIDDEN, false, e.getMessage()).buildResponse();
		} catch (ModelValidationException e) {
			logger.debug("UPC0004D: getUserProfile: Model validation exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		}
	}

}
