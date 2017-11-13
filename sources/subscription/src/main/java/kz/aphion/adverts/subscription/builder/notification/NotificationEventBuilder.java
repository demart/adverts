package kz.aphion.adverts.subscription.builder.notification;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationEventMessage;
import kz.aphion.adverts.notification.mq.models.NotificationEventSource;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.subscription.builder.FM;
import kz.aphion.adverts.subscription.builder.notification.email.EmailNotificationChannelBuilder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Класс билдер, подготавливает сообщение для отправки в очередь уведомлений для последующей
 * рассылки
 * @author artem.demidovich
 *
 * Created at Mar 6, 2017
 */
public class NotificationEventBuilder {

	private static Logger logger = LoggerFactory.getLogger(NotificationEventBuilder.class);

	private NotificationEventMessage notificationMessage = new NotificationEventMessage();
	
	public NotificationEventBuilder() {
		notificationMessage.accessToken = "TO_BE_DISCUSSED";
		notificationMessage.callbackParameters = new ArrayList<>(); // Empty
		notificationMessage.callbackQueue = null; // Empty
		notificationMessage.eventId = UUID.randomUUID().toString(); // Without storing on this side
		notificationMessage.eventSource = NotificationEventSource.SUBSCRIPTION;
		notificationMessage.eventSourceSystem = "SUBSCRIPTION V1";
		notificationMessage.parameters = new ArrayList<>(); // Empty
		notificationMessage.channels = new ArrayList<>();
	}
	
	public void setUserId(String userId) {
		notificationMessage.userId = userId; // User from Subscription
	}
	
	public void addChannel(NotificationChannel channel) {
		// TODO CHECK IF NOT EXISTS THE SAME CHANNEL OR IT CAN BE MORE THEN ONE?
		
		if (channel == null)
			return;
		
		notificationMessage.channels.add(channel);
	}
	
	public NotificationEventMessage build() {
		return notificationMessage;
	}
	
	
	/**
	 * Метод формирует сообщение для выбранного канала. Используется шаблонизатор для формирования сообщения
	 * @param channelType
	 * @param subscription
	 * @param subscriptionAdverts
	 */
	public void addChannel(NotificationChannelType channelType, Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) {
		
		// Взять шаблон для указанного типа
		// Взять данные конент и подписку
		// Взять маркетинговый контент
		// Сохранить результат шаблонизирования

		// TODO ADD TEMPLATE ENGINE
		// TODO ADD TEMPLATE STORAGE
		
		// Информация о пользователе
		// Информации о подписке
		// Информация о кол-ве объявлений в подписке
		// Информация о новых объявлениях
		
		switch (channelType) {
			case EMAIL:
				try {
					this.addChannel(new EmailNotificationChannelBuilder().build(subscription, subscriptionAdverts));
				} catch (TemplateException | IOException e) {
					logger.error("Couldn't build email notificaiton, please check template", e);
				}
				// Как можно больше полезной информации + ссылки на переходы
				break;

			case ANDROID:
			case IOS:
			case WINDOWS_PHONE:
				// Тут главное мини инфа и правильно открыть пуш в приложении
				logger.warn("Mobile notificaion event builder doesn't support yet...");
				break;
				
			case CHROME:
			case FIREFOX:
			case OPERA:
			case SAFARI:
				// Тут мини инфра и правильно открыть ссылку по клику
				logger.warn("Web Browser Push notificaion event builder doesn't support yet...");
				break;
				
			case MACOS:
			case ONEINBOX:
			case SMS:
			case WEBSOCKET:
				// Тут мини инфра и ссылка на переход
				logger.warn("Exotic notificaion event builder doesn't support yet...");
				break;						
		default:
			break;
		}
		
	
	}
	
	
   public static void main(String[] args) throws Exception {
		// Create the root hash. We use a Map here, but it could be a JavaBean too.
		Map<String, Object> root = new HashMap<>();

		// Put string "user" into the root
		root.put("name", "Big Joe");
		root.put("url", "http://blablalba");
		root.put("title", "Super Mega Link");

		Template temp = FM.getCfg().getTemplate("email-notification-template-v2.ftlh.html");
		Writer out = new OutputStreamWriter(System.out);
		temp.process(root, out);
   }
	
}
