package kz.aphion.adverts.notification.utils;

import kz.aphion.adverts.notification.mq.models.NotificationEventMessageCallback;
import kz.aphion.adverts.notification.mq.models.channel.NotificationChannelMessageCallback;
import kz.aphion.adverts.notification.providers.ActiveMqProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CallbackUtils {

	private static Logger logger = LoggerFactory.getLogger(CallbackUtils.class);
	
	public static final String SYSTEM = "NOTIFCAITON";
	
	public static Gson gson  = new Gson();
	
	
	/**
	 * Отправляет callback уведомление с текущим временем.
	 * Если что-то пошло не так то это будет залогировано, но ошибки метод не вернет
	 * @param message
	 * @param status
	 * @param extraMessage 
	 */
	public static void sendCallbackNotification(String eventId, String callbackQueue, NotificationChannelMessageCallback message) {
		logger.debug("Sending callback notifcation to source system.");
		if (message == null) {
			logger.error("Can't send callback because NotificationMessage object is null..");
			return;
		}
		
		NotificationEventMessageCallback callback = new NotificationEventMessageCallback();
		callback.eventId = eventId;
		callback.channelId = message.channelId;
		callback.status = message.status;
		callback.time = message.time;
		callback.system = SYSTEM;
		callback.message = message.message;
		
		String jsonCallaback = gson.toJson(callback);
		
		try {
			ActiveMqProvider.getInstance().sendTextMessageToQueue(callbackQueue, jsonCallaback);
			logger.debug("Callback was sent. event.id {}, channel.id {}, status {}.", eventId, callback.channelId, callback.status);
		} catch (Exception ex) {
			logger.error("Error sending callback notification.", ex);
		}
	}
	
}
