package kz.aphion.adverts.web.api.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import kz.aphion.adverts.web.api.models.ResponseWrapper;
import kz.aphion.adverts.web.api.models.users.CreateUserAccountModel;
import kz.aphion.adverts.web.api.models.users.ForgotPasswordRequestModel;
import kz.aphion.adverts.web.api.services.UserAccountService;
import kz.aphion.adverts.web.api.utils.EmailUtils;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Котроллер отвечазает за базовые функции в части управления user account'om 
 * @author artem.demidovich
 *
 * Created at Nov 15, 2017
 */
@Named
@RequestScoped
@Path("/v1/user/account")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class UserAccountController {

	private static Logger logger = LoggerFactory.getLogger(UserAccountController.class);

	
	@Inject
	UserAccountService service;
	
	/**
	 * Проверяет свободный ли логин для использования
	 * @param login
	 * @return
	 */
	@GET  
	@Path("/is/login/available")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON) // @Context UriInfo uriInfo,  
	public Response isLoginAvailable(@QueryParam("login") String login) {
		logger.debug("UAC0001: isLoginAvailable: invoked with the login: [{}]", login);
		
		if (StringUtils.isBlank(login)) {
			logger.debug("UAC0002: isLoginAvailable: [{}] is incorrect", login);
			return Response.status(Response.Status.BAD_REQUEST).build();
		}

		ResponseWrapper wrapper = service.isLoginAvailable(login);
		
		logger.debug("UAC0003: isLoginAvailable: login [{}] is free [{}]", login, wrapper.data);
		
		return wrapper.buildResponse();
	}	
	
	@POST  
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount(CreateUserAccountModel model) {
		logger.debug("UAC0010: createAccount: invoked");
		
		if (model == null) {
			logger.debug("UAC0011: createAccount: model is null");
			return Response.status(Response.Status.BAD_REQUEST).entity("model is null").build();
		}
		
		if (StringUtils.isBlank(model.email)) {
			logger.debug("UAC0012: createAccount: model.email [{}] is incorrect", model.email);
			return Response.status(Response.Status.BAD_REQUEST).entity("model.email is incorrect").build();
		}
		
		if (StringUtils.isBlank(model.name)) {
			logger.debug("UAC0013: createAccount: model.name [{}] is incorrect", model.name);
			return Response.status(Response.Status.BAD_REQUEST).entity("model.name is incorrect").build();
		}
		
		if (StringUtils.isBlank(model.password)) {
			logger.debug("UAC0014: createAccount: model.password [{}] is incorrect", model.password);
			return Response.status(Response.Status.BAD_REQUEST).entity("model.password is incorrect").build();
		}
		
		ResponseWrapper result = service.createAccount(model);
		
		logger.debug("UAC0015: createAccount: succesefully processed", model.email);
		
		return result.buildResponse();
	}
	

	
	@POST 
	@Path("/forgot/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgotPassword(ForgotPasswordRequestModel model) {
		logger.debug("UAC0020: forgotPassword: invoked");
		
		// Request validation
		if (model == null) {
			logger.debug("UAC0021: forgotPassword: model is null");
			return ResponseWrapper.with(Status.BAD_REQUEST, false, "Request model is null.").buildResponse();
		}
		
		if (StringUtils.isBlank(model.email)) {
			logger.debug("UAC0022: forgotPassword: model.email [{}] is incorrect ", model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, "Email is incorrect.").buildResponse();
		}
		
		if (!EmailUtils.isValidEmailAddress(model.email)) {
			logger.debug("UAC0023: forgotPassword: model.email [{}] is not valid ", model.email);
			return ResponseWrapper.with(Status.BAD_REQUEST, false, "Invalid email was submitted.").buildResponse();
		}
	
		ResponseWrapper result = service.forgotPassword(model);
		
		logger.debug("UAC0024: forgotPassword: succesefully processed", model.email);
		return result.buildResponse();
	}
	
	@GET 
	@Path("/forgot/password/confirm")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgotPasswordConfirmation() {
		return Response.accepted().build();
	}
	
	@POST 
	@Path("/forgot/password/new")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgotPasswordApplyNewPassword() {
		return Response.accepted().build();
	}
	
	
	// Including social
	@POST  
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login() {
		return Response.accepted().build();
	}

	
	@POST  
	@Path("/logout")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response logout() {
		return Response.accepted().build();
	}
	
	
	@POST  
	@Path("/change/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response changePassword() {
		return Response.accepted().build();
	}
	
	
	@GET 
	@Path("/profile")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response getUserProfile() {
		return Response.accepted().build();
	}	
	
	@POST
	@Path("/profile/update")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response updateUserProfile() {
		return Response.accepted().build();
	}
}
