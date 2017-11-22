package kz.aphion.adverts.web.api;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import kz.aphion.adverts.web.api.controllers.AdvertSearchController;
import kz.aphion.adverts.web.api.controllers.UserAccountController;
import kz.aphion.adverts.web.api.controllers.UserProfileController;
import kz.aphion.adverts.web.api.security.SecuredMethodFilter;

/**
 * Base Application class for REST API
 * 
 * Here must be registered all rest controllers
 * 
 * @author artem.demidovich
 *
 */
@ApplicationPath("/rest")
public class RestApplication extends Application {
	
	public Set<Class<?>> getClasses() {
		return new HashSet<Class<?>>(
				Arrays.asList(
						// Filters & Annotations
						SecuredMethodFilter.class, // Filter to protect secured methods
						
						// User Management
						UserAccountController.class,
						UserProfileController.class,
						
						// Advert Search
						AdvertSearchController.class
						
				)
		);
	}
	
}

