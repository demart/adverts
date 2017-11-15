package kz.aphion.adverts.web.api.controllers;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import kz.aphion.adverts.web.api.services.UserAccountService;

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
	
	
	@POST  
	@Path("/create")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response createAccount() {
		return Response.accepted().build();
	}
	
	
	@POST 
	@Path("/forgot/password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response forgotPassword() {
		return Response.accepted().build();
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
