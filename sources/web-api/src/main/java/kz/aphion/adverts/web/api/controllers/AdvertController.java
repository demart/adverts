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

import kz.aphion.adverts.web.api.exceptions.ModelValidationException;
import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.services.AdvertService;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Контроллер операций с объектом объявления
 * @author artem.demidovich
 *
 * Created at Nov 24, 2017
 */

@Named
@RequestScoped
@Path("/v1/advert")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdvertController extends BaseSecuredController {

	private static Logger logger = LoggerFactory.getLogger(AdvertController.class);

	@Inject
	AdvertService service;
	
	@POST  
	@Path("/{type}/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) 
	public Response getAdvert(@PathParam("id") String advertId) {
		try {
			logger.debug("AC00001D: getAdvert: invoked with id [{}] and email: [{}]", advertId, getUserEmail());

			if (StringUtils.isBlank(advertId))
				throw new ModelValidationException("model.advertId is null or empty");
			if (ObjectId.isValid(advertId))
				throw new ModelValidationException("model.advertId is incorrect");
			
			ResponseWrapper result = service.getAdvert(advertId);
			logger.debug("AC00002D: getAdvert: succesefully completed, advertId [{}]. User.email [{}]", advertId, getUserEmail());
			
			return result.buildResponse();
			
		} catch (ModelValidationException e) {
			logger.debug("AC00003D: getAdvert: Model validation exception: [{}], User.email [{}]", e.getMessage(), getUserEmail());
			return ResponseWrapper.with(Status.BAD_REQUEST, false, e.getMessage()).buildResponse();
		} catch (Throwable e) {
			logger.error("AC00004E: getAdvert: Exception: " + e.getMessage() + " User.email " + getUserEmail(), e);
			throw e;
		} 
	}
}
