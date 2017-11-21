package kz.aphion.adverts.web.api.builder.notification;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationEventMessage;
import kz.aphion.adverts.notification.mq.models.NotificationEventSource;
import kz.aphion.adverts.persistence.users.request.EmailVerificationRequest;
import kz.aphion.adverts.web.api.builder.FM;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.core.ParseException;
import freemarker.template.MalformedTemplateNameException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateNotFoundException;

public class EmailVerificationNotificationBuilder {
	
	private static Logger logger = LoggerFactory.getLogger(EmailVerificationNotificationBuilder.class);

	/**
	 * Название файла шаблона в папке шаблонов
	 */
	private static final String EMAIL_TEMPLATE = "email-notification-email-verification-v1.ftlh.html";
	
	
	private NotificationEventMessage notificationMessage = new NotificationEventMessage();
	
	public EmailVerificationNotificationBuilder() {
		notificationMessage.accessToken = "TO_BE_DISCUSSED";
		notificationMessage.callbackParameters = new ArrayList<>(); // Empty
		notificationMessage.callbackQueue = null; // Empty
		notificationMessage.eventId = UUID.randomUUID().toString(); // Without storing on this side
		notificationMessage.eventSource = NotificationEventSource.WEBAPI;
		notificationMessage.eventSourceSystem = "WEB-API V1";
		notificationMessage.parameters = new ArrayList<>(); // Empty
		notificationMessage.channels = new ArrayList<>();
	}
	
	public NotificationEventMessage build(EmailVerificationRequest evr) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		
		NotificationChannel channel = new NotificationChannel();
		channel.uid = UUID.randomUUID().toString();
		channel.type = NotificationChannelType.EMAIL;
		channel.addreseeId = evr.user.email;
		channel.title = "Запрос на подтверждение почтового ящика";
		channel.parameters = null;
		channel.content = buildContent(evr);
		
		notificationMessage.channels.add(channel);
		return notificationMessage;
	}
	

	private String buildContent(EmailVerificationRequest evr) throws TemplateNotFoundException, MalformedTemplateNameException, ParseException, IOException, TemplateException {
		
		Map<String, Object> root = new HashMap<>();
		
		root.put("title", "Запрос на подтверждение почтового ящика");
		root.put("user", evr.user);
		
		root.put("emailVerificationUrl", "test.server.email.verification.url");
		root.put("supportTeamUrl", "test.server.support.team.url");
		root.put("companyName", "Aphion Software"); // ???
		root.put("companyAddress", "Казахстан, г. Астана"); // ???
		root.put("companyContacts", "+7 (701) 551-51-01"); // ??
		
		Template template = FM.getCfg().getTemplate(EMAIL_TEMPLATE);
		
		StringWriter stringWriter = new StringWriter();
		template.process(root, stringWriter);

		String result = stringWriter.toString();
		
		logger.debug("Email Verification Generated Email:\n{}", result);
		
		return result;
	}	
	
}
