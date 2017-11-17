package kz.aphion.adverts.web.api.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

import kz.aphion.adverts.web.api.services.AdvertSearchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Named
@RequestScoped
@Path("/v1/search")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AdvertSearchController {

	private static Logger logger = LoggerFactory.getLogger(AdvertSearchController.class);
	
	@Inject
	AdvertSearchService service;
	
	@GET  
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response searchAdverts(@Context UriInfo uriInfo) {
		logger.info("search adverts was invoked...");
		
		Response response = service.searchAdverts(uriInfo);
		
		logger.info("search adverts finished...");
		return response;
	}
	
	@GET  
	@Path("count")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response advertsCount(@Context UriInfo uriInfo) {
		logger.info("search adverts count was invoked...");
		
		Response response = service.advertsCount(uriInfo);
		
		logger.info("search adverts count finished...");
		return response;
	}
	
}
