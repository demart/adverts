package kz.aphion.adverts.notification.sender.utils;

import java.util.Calendar;

import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.notification.sender.mq.QueueNameConstants;
import kz.aphion.adverts.notification.sender.mq.models.NotificationChannelMessage;
import kz.aphion.adverts.notification.sender.mq.models.NotificationChannelMessageCallback;
import kz.aphion.adverts.notification.sender.mq.models.NotificationStatus;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class CallbackUtils {

	private static Logger logger = LoggerFactory.getLogger(CallbackUtils.class);
	
	public static final String SYSTEM = "NOTIFCAITON.SENDER";
	
	public static Gson gson  = new Gson();
	
	
	/**
	 * Отправляет callback уведомление с текущим временем.
	 * Если что-то пошло не так то это будет залогировано, но ошибки метод не вернет
	 * @param message
	 * @param status
	 * @param extraMessage 
	 */
	public static void sendCallbackNotification(NotificationChannelMessage message, NotificationStatus status, String extraMessage) {
		logger.debug("Sending callback notifcation.");
		if (message == null) {
			logger.error("Can't send callback because NotificationChannelMessage object is null..");
			return;
		}
		
		NotificationChannelMessageCallback callback = new NotificationChannelMessageCallback();
		callback.channelId = message.channelId;
		callback.notificationId = message.notificationId;
		callback.status = status;
		callback.time = Calendar.getInstance();
		callback.system = SYSTEM;
		callback.message = extraMessage;
		
		String jsonCallaback = gson.toJson(callback);
		
		try {
			MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.MQ_NOTIFICATION_CALLBACK_QUEUE, jsonCallaback);
			logger.debug("Callback was sent. notification.id {}, channel.id {}, status {}.", callback.notificationId, callback.channelId, callback.status);
		} catch (Exception ex) {
			logger.error("Error sending callback notification.", ex);
		}
	}
	
	/**
	 * Отправляет callback уведомление с текущим временем.
	 * Если что-то пошло не так то это будет залогировано, но ошибки метод не вернет
	 * @param message
	 * @param status
	 */
	public static void sendCallbackNotification(NotificationChannelMessage message, NotificationStatus status) {
		sendCallbackNotification(message, status, null);
	}
	
}
