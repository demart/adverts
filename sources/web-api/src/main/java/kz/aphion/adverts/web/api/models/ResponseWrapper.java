package kz.aphion.adverts.web.api.models;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * Базовый класс ответов на вызовы API
 * @author artem.demidovich
 *
 * Created at Nov 16, 2017
 */
public class ResponseWrapper {

	/**
	 * Данные может быть любой объект
	 */
	public Object data;
	
	/**
	 * Результат выполнения запроса
	 */
	public Status status = Status.OK;
	
	/**
	 * Сообщения связанные с выполнением запроса
	 */
	public String message;
	
	
	public Response buildResponse() {
		return Response.status(this.status).entity(this).type(MediaType.APPLICATION_JSON_TYPE).build();
	}
	
	public static ResponseWrapper with(Response.Status status, Object data, String message) {
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.status = status;
		wrapper.data = data;
		wrapper.message = message;
		return wrapper;
	}
	
	public static ResponseWrapper with(Response.Status status, Object data) {
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.status = status;
		wrapper.data = data;
		return wrapper;
	}	
	
	public static ResponseWrapper with(Response.Status status) {
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.status = status;
		return wrapper;
	}
	
	public static ResponseWrapper with(Object data) {
		ResponseWrapper wrapper = new ResponseWrapper();
		wrapper.data = data;
		return wrapper;
	}
	
}
