package kz.aphion.adverts.notification.processors;

import java.util.ArrayList;
import java.util.Calendar;

import javax.jms.JMSException;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.common.MQ;
import kz.aphion.adverts.common.mq.QueueNameConstants;
import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationEventMessage;
import kz.aphion.adverts.notification.mq.models.NotificationParameter;
import kz.aphion.adverts.notification.mq.models.channel.NotificationChannelMessage;
import kz.aphion.adverts.persistence.notification.Notification;
import kz.aphion.adverts.persistence.notification.NotificationCallback;
import kz.aphion.adverts.persistence.notification.NotificationChannelProgressStatus;
import kz.aphion.adverts.persistence.notification.NotificationChannelType;
import kz.aphion.adverts.persistence.notification.NotificationEventSource;
import kz.aphion.adverts.persistence.notification.NotificationProcessStatus;
import kz.aphion.adverts.persistence.notification.NotificationStatus;
import kz.aphion.adverts.persistence.users.User;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс обработчик поступающих событий на отправку уведомлений
 * @author artem.demidovich
 *
 * Created at Aug 16, 2016
 */
public class NotificationEventProcessor {
	
	private static Logger logger = LoggerFactory.getLogger(NotificationSubscriptionProcessor.class);
	
	/**
	 * Метод является входной точкой для обработки запроса
	 * @param message
	 * @throws Exception 
	 * @throws JMSException 
	 */
	public void processMessage(String message) throws JMSException, Exception {
		NotificationEventMessage model = NotificationEventMessage.parseModel(message);
		if (model == null) {
			// Не смогли извлечь, ругаемся и приступаем к следующему сообщению
			logger.warn("Can't process message [" + message + "] Message does not belong to NotificationEventProcessor");
			return;
		}
		
		// TODO Check access token
		// TODO Validate model
		
		Datastore ds = DB.DS();
		
		if (isExistEventId(ds, model.eventId)) {
			logger.warn("Can't process notification eventId, because it is already exists");
			// TODO отправить callback со статусом ошибка если колбэк предусмотер
			return ;
		}
		
		// Сохранить объекты в базе
		Notification notification = createNotification(ds, model);
		// Сохраняем
		ds.save(notification);
		
		// TODO поддерживать двух фазные транзакции
		// Разослать уведомление по необходимым каналам		
		for (kz.aphion.adverts.persistence.notification.NotificationChannel channel : notification.channels) {
			try {
				String jsonMessage = convertChannelToMessage(notification, channel);
				switch (channel.type) {
					case ANDROID:
					case IOS:
					case WINDOWS_PHONE:	
						MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_PUSH_QUEUE.getValue(), jsonMessage);
						addProcessedStatus(channel);
						break;
					
					case CHROME:
					case FIREFOX:
					case OPERA:
					case SAFARI:
						MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_BROWSER_QUEUE.getValue(), jsonMessage);
						addProcessedStatus(channel);
						break;
						
					case EMAIL:
						MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_EMAIL_QUEUE.getValue(), jsonMessage);
						addProcessedStatus(channel);
						break;
						
					case SMS:
						MQ.INSTANCE.sendTextMessageToQueue(QueueNameConstants.NOTIFICATION_SMS_QUEUE.getValue(), jsonMessage);
						addProcessedStatus(channel);
						break;
						
					case MACOS:
						logger.warn("MACOS notification type not supported");
						break;
					case ONEINBOX:
						logger.warn("ONEINBOX notification type not supported");
						break;
					case WEBSOCKET:
						logger.warn("WEBSOCKET notification type not supported");
						break;
					
					default:
						logger.warn("{} notification type not supported", channel.type.toString());
						break;
				}
			} catch (Exception ex) {
				logger.error("Error trying to send message to notificaiton sender queues", ex);
			}
		}

		// Обновляем с учетом отправки в очереди
		ds.save(notification);
		
	}

	
	private void addProcessedStatus(kz.aphion.adverts.persistence.notification.NotificationChannel channel) {
		channel.status = NotificationProcessStatus.PROCESSED;
		NotificationChannelProgressStatus status = new NotificationChannelProgressStatus();
		status.status = NotificationProcessStatus.PROCESSED;
		status.system = "NOTIFICATION";
		status.time = Calendar.getInstance();
		channel.statuses.add(status);
	}
	
	private String convertChannelToMessage(Notification notification, kz.aphion.adverts.persistence.notification.NotificationChannel channel) {
		NotificationChannelMessage message = new NotificationChannelMessage();
		
		message.notificationId = notification.id.toString();
		message.channelId = channel.uid;
		
		message.type = kz.aphion.adverts.notification.mq.models.NotificationChannelType.valueOf(channel.type.toString());
		message.addreseeId = channel.addresseeId;
		
		message.title = channel.title;
		message.content = channel.body;
		
		if (channel.parameters != null) {
			message.parameters = new ArrayList<NotificationParameter>();
			for (kz.aphion.adverts.persistence.notification.NotificationParameter param : channel.parameters) {
				NotificationParameter p = new NotificationParameter();
				p.key = param.key;
				p.value = param.value;
				message.parameters.add(p);
			}
		}
		
		String jsonMessage = NotificationChannelMessage.toJson(message);
		return jsonMessage;
	}
	
	
	private Notification createNotification(Datastore ds, NotificationEventMessage notificationEvent) {
		Notification notification = new Notification();
		
		notification.eventId = notificationEvent.eventId;
		// TODO Поменять на норм корвертацию
		notification.eventSource = NotificationEventSource.valueOf(notificationEvent.eventSource.toString());
		notification.eventSourceSystem = notificationEvent.eventSourceSystem;
		notification.status = NotificationStatus.ACTIVE;
		notification.progressStatus =  NotificationProcessStatus.CREATED;
		
		notification.created = Calendar.getInstance();
		notification.updated = Calendar.getInstance();

		try {
			notification.user = getUser(ds, notificationEvent.userId);
		} catch (Exception ex) {
			logger.warn("Can't parse id [" + notificationEvent.userId + "] or find record, will be skipped.");
		}
		
		// Callback
		if (StringUtils.isNotBlank(notificationEvent.callbackQueue)) {
			notification.callback = new NotificationCallback();
			notification.callback.needCallback = true;
			notification.callback.callbackQueue = notificationEvent.callbackQueue;
			
			if (notificationEvent.callbackParameters != null) {
				notification.callback.parameters = new ArrayList<kz.aphion.adverts.persistence.notification.NotificationParameter>();
				for (NotificationParameter parameter : notificationEvent.callbackParameters) {
					kz.aphion.adverts.persistence.notification.NotificationParameter np = new kz.aphion.adverts.persistence.notification.NotificationParameter();
					np.key = parameter.key;
					np.value = parameter.value;
					notification.callback.parameters.add(np);
				}
			}
		}
		
		// channels
		notification.channels = new ArrayList<kz.aphion.adverts.persistence.notification.NotificationChannel>();
		for (NotificationChannel channel : notificationEvent.channels) {
			kz.aphion.adverts.persistence.notification.NotificationChannel nch = new kz.aphion.adverts.persistence.notification.NotificationChannel();
			
			nch.uid = channel.uid; //UUID.randomUUID().toString();
			nch.type = NotificationChannelType.valueOf(channel.type.toString());
			nch.addresseeId = channel.addreseeId;
			nch.title = channel.title;
			nch.body = channel.content;
			nch.createdAt = Calendar.getInstance();
			nch.updatedAt = nch.createdAt;
			
			nch.status = NotificationProcessStatus.CREATED;
			nch.statuses = new ArrayList<NotificationChannelProgressStatus>();
			NotificationChannelProgressStatus nchps = new NotificationChannelProgressStatus();
			nchps.status = NotificationProcessStatus.CREATED;
			nchps.system = "NOTIFICATION";
			nchps.time = nch.createdAt;
			nch.statuses.add(nchps);
			
			notification.channels.add(nch);
		}
		
		return notification;
	}
	
	
	
	private User getUser(Datastore ds, String userId) {
		if (StringUtils.isBlank(userId)) {
			logger.debug("UserId is null, skipping this step.");
			return null;
		}
		User user = ds.get(User.class, new ObjectId(userId));
		return user;
	}


	private Boolean isExistEventId(Datastore ds, String eventId) {
		// TODO проверить есть ли уже такое событие или нет, если есть то завершаем
		return false;
	}
	
}
