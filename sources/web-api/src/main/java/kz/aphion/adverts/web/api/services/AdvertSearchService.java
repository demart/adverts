package kz.aphion.adverts.web.api.services;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import kz.aphion.adverts.web.api.exceptions.IncorrectParameterValueException;
import kz.aphion.adverts.web.api.exceptions.MissingRequiredParameterException;
import kz.aphion.adverts.web.api.query.SearchAdvertQuery;
import kz.aphion.adverts.web.api.query.SearchAdvertsQueryParser;
import kz.aphion.adverts.web.api.search.AdvertSearch;
import kz.aphion.adverts.web.api.search.AdvertSearchFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Сервис для поиска объявлений по заданным параметрам
 * @author artem.demidovich
 *
 * Created at Aug 14, 2016
 */

@Named
@RequestScoped
public class AdvertSearchService {

	private static Logger logger = LoggerFactory.getLogger(AdvertSearchService.class);
	

	@Inject
	SearchAdvertsQueryParser queryParser;
	
	public Response searchAdverts(UriInfo uriInfo) {
		try {
		
			SearchAdvertQuery query = queryParser.parseQuery(uriInfo);
			query.validateQuery();
			
			AdvertSearch searcher = AdvertSearchFactory.getAdvetSearcher(query);
			List<Object> result = searcher.search(query);
			
			return Response.ok(result).build(); 
			
		} catch (IncorrectParameterValueException e) {
			logger.error("Incorrect parameter in the request", e);
			return Response.status(Status.PRECONDITION_FAILED).entity(e).build();
		} catch (MissingRequiredParameterException e) {
			logger.error("Incorrect parameter in the request", e);
			return Response.status(Status.PRECONDITION_FAILED).entity(e).build();
		} catch (Exception e) {
			logger.error("Internal error", e);
			return Response.status(Status.PRECONDITION_FAILED).entity(e).build();
		}
	}
	
	public Response advertsCount(UriInfo uriInfo) {
		try {
		
			SearchAdvertQuery query = queryParser.parseQuery(uriInfo);
			query.validateQuery();
			
			AdvertSearch searcher = AdvertSearchFactory.getAdvetSearcher(query);
			Long result = searcher.count(query);
			
			return Response.ok(result).build(); 
			
		} catch (IncorrectParameterValueException e) {
			logger.error("Incorrect parameter in the request", e);
			return Response.status(Status.PRECONDITION_FAILED).entity(e).build();
		} catch (MissingRequiredParameterException e) {
			logger.error("Incorrect parameter in the request", e);
			return Response.status(Status.PRECONDITION_FAILED).entity(e).build();
		} catch (Exception e) {
			logger.error("Incorrect parameter in the request", e);
			return Response.status(Status.PRECONDITION_FAILED).entity(e).build();
		}
	}
	
}
