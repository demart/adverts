package kz.aphion.adverts.notification.processors;

import java.util.ArrayList;

import javax.jms.JMSException;

import kz.aphion.adverts.notification.mq.models.channel.NotificationChannelMessageCallback;
import kz.aphion.adverts.notification.providers.MongoDbProvider;
import kz.aphion.adverts.notification.utils.CallbackUtils;
import kz.aphion.adverts.persistence.notification.Notification;
import kz.aphion.adverts.persistence.notification.NotificationChannel;
import kz.aphion.adverts.persistence.notification.NotificationChannelProgressStatus;
import kz.aphion.adverts.persistence.notification.NotificationProcessStatus;
import kz.aphion.adverts.persistence.notification.NotificationStatus;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NotificationChannelCallbackProcessor {

	private static Logger logger = LoggerFactory.getLogger(NotificationChannelCallbackProcessor.class);
	
	/**
	 * Метод является входной точкой для обработки запроса
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		NotificationChannelMessageCallback model = NotificationChannelMessageCallback.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to NotificationChannelMessageCallback");
			return;
		}
		
		logger.info("Starting processing callback messages with notificationId {} and channelId {} for status {}", model.notificationId, model.channelId, model.status);
		
		// TODO validate model
		
		Datastore ds = MongoDbProvider.getInstance().getDatastore();
		
		Notification notification = ds.get(Notification.class, new ObjectId(model.notificationId));
		if (notification == null) {
			logger.warn("Notification with Id {} not found, please check why it might happened.", model.notificationId);
			return;
		}
		
		// TODO check deleted record or not
		if (notification.status == NotificationStatus.DELETED) {
			logger.warn("Received callback from sender for deleted subscriptions with id {}", notification.id.toString());
		}
		
		if (notification.channels == null) {
			logger.warn("Received callback from sender but subscriptions with id {} doesnt have any channels", notification.id.toString());
			return;
		}
		
		for (NotificationChannel channel : notification.channels) {
			if (channel.uid.equals(model.channelId)) {
				logger.debug("Found channel {} for callback message", channel.uid);
				
				channel.status = NotificationProcessStatus.valueOf(model.status.toString());
				
				// TODO Необходимо правильно строить статусы, так как затирать все время плохая идея
				// TODO Правильно обновлять главный статус доставлено или нет
				
				NotificationChannelProgressStatus progress = new NotificationChannelProgressStatus();
				progress.additional = model.message;
				progress.status = NotificationProcessStatus.valueOf(model.status.toString());
				progress.system = model.system;
				progress.time = model.time;
				
				if (channel.statuses == null)
					channel.statuses = new ArrayList<NotificationChannelProgressStatus>();
				channel.statuses.add(progress);
			}
		}
		
		ds.merge(notification);
		
		// Отправляем уведомление если источник запрашивает
		if (notification.callback != null && notification.callback.needCallback) {
			logger.debug("Source system requires send callback notificaitons.");

			CallbackUtils.sendCallbackNotification(notification.eventId, notification.callback.callbackQueue, model);
			
		}
		
		logger.info("Finished processing callback messages with notificationId {} and channelId {} for status {}", model.notificationId, model.channelId, model.status);
	}
}
