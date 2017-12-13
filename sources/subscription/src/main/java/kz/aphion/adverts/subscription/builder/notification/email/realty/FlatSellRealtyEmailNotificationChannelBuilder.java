package kz.aphion.adverts.subscription.builder.notification.email.realty;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.models.SubscriptionModel;
import kz.aphion.adverts.models.realty.FlatSellAdvertModel;
import kz.aphion.adverts.models.realty.data.FlatRealtyBaseDataModel;
import kz.aphion.adverts.models.subscription.FlatSellSubscriptionModel;
import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationParameter;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertLocation;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;
import kz.aphion.adverts.subscription.builder.FM;
import kz.aphion.adverts.subscription.builder.notification.email.realty.models.FlatSellRealtyModel;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Строитель email уведомления для рассылки объявлений о продаже квартир
 * 
 * @author artem.demidovich
 *
 * Created at Nov 9, 2017
 */
public class FlatSellRealtyEmailNotificationChannelBuilder {

	private static Logger logger = LoggerFactory.getLogger(FlatSellRealtyEmailNotificationChannelBuilder.class);
	
	/**
	 * Название файла шаблона в папке шаблонов
	 */
	private static final String EMAIL_TEMPLATE = "email-notification-realty-flat-sell-template-v1.ftlh.html";

	public NotificationChannel build(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) throws TemplateException, IOException {
		NotificationChannel email = new NotificationChannel();
		
		email.uid = UUID.randomUUID().toString();
		email.type = NotificationChannelType.EMAIL;
		email.addreseeId = subscription.user.email;
		
		FlatSellSubscriptionModel subscriptionModel = new FlatSellSubscriptionModel(subscription);
		
		email.title = buildTitle(subscriptionModel, subscriptionAdverts);
		email.content = buildContent(subscriptionModel, subscriptionAdverts);
		email.parameters = buildParameters(subscriptionModel, subscriptionAdverts);
		
		return email;
	}
	
	private String buildTitle(SubscriptionModel subscription, List<SubscriptionAdvert> subscriptionAdverts) {		
		StringBuilder title = new StringBuilder();

		if (subscriptionAdverts == null || subscriptionAdverts.size() < 1) {
			// Strange, but we have to check
			title.append("Найдены новые объявления о продаже квартир по вашей подписке");
		} else {
			int endwith = subscriptionAdverts.size() % 10;	

			if (endwith == 0 || endwith > 4 || (endwith > 0 && subscriptionAdverts.size() > 10)) { // 10, 13, 28
				title.append("Найдено " + subscriptionAdverts.size() + " новых объявлений о продаже квартир по вашей подписке.");
			}
			
			if (endwith  > 1 && endwith < 5) { // 2, 4, 21
				title.append("Найдено " + subscriptionAdverts.size() + " новых объявления о продаже квартир по вашей подписке.");
			}
			
			if (endwith  == 1 && subscriptionAdverts.size() != 11) { // 2, 4, 21, but not 11
				title.append("Найдено " + subscriptionAdverts.size() + " новое объявление о продаже квартир по вашей подписке.");
			}
		}
		
		// TEMP		
		title.append(" [" + subscription.getSubscription().id + "]");
		
		return title.toString();
	}

	private String buildContent(FlatSellSubscriptionModel subscriptionModel, List<SubscriptionAdvert> subscriptionAdverts) throws TemplateException, IOException {

		Map<String, Object> root = new HashMap<>();

		// Put global object for access from the template
		root.put("subscription", subscriptionModel.getSubscription());
		root.put("user", subscriptionModel.getUser());
		root.put("notification", subscriptionModel.getNotification());
		
		root.put("subscriptionAdverts", subscriptionAdverts);
		root.put("adverts", buildAdvertModels(subscriptionModel, subscriptionAdverts));
		
		// Add specific objects for predefined fields
		root.put("subscriptionTitle", ""); // ???
		root.put("subscriptionDescriptionText", getSubscriptionDescriptionText((FlatSellSubscriptionModel)subscriptionModel));
		
		root.put("title", "Найдены новые объявления о продаже квартир");
		
		long totalAdvertsCount = DB.DS().createQuery(SubscriptionAdvert.class)
				.field("subscription.id").equal(subscriptionModel.getSubscription().id)
				.field("status").notIn(Arrays.asList(SubscriptionAdvertStatus.DELETED,SubscriptionAdvertStatus.REPLACED))
				.count();
				
		root.put("totalAdvertsCount", totalAdvertsCount);
		root.put("newAdvertsCount", subscriptionAdverts != null ? subscriptionAdverts.size() : 0);
		
		root.put("companyName", "Aphion Software"); // ???
		root.put("companyAddress", "Казахстан, г. Астана"); // ???
		root.put("companyContacts", "+7 (701) 551-51-01"); // ??
		
		// URL TO NAVIGATE ON THE SITE
		root.put("subscriptionUrl", "TO_BE_DISCUSSED"); // ??
		root.put("unsubscribeUrl", "TO_BE_DISCUSSED"); // ??
		
		
		Template template = FM.getCfg().getTemplate(EMAIL_TEMPLATE);
		
		StringWriter stringWriter = new StringWriter();
		template.process(root, stringWriter);

		String result = stringWriter.toString();
		
		logger.debug("Generated Email\n{}", result);
		
		return result;
	}	
	

	private List<FlatSellRealtyModel> buildAdvertModels(SubscriptionModel subscriptionModel, List<SubscriptionAdvert> subscriptionAdverts) {
		
		List<FlatSellRealtyModel> models = new ArrayList<FlatSellRealtyModel>();
		
		for (SubscriptionAdvert subscriptionAdvert : subscriptionAdverts) {
			FlatSellRealtyModel model = buildAdvertModel(subscriptionModel, subscriptionAdvert);
			if (model != null)
				models.add(model);
		}
		
		return models;
	}
	
	private FlatSellRealtyModel buildAdvertModel(SubscriptionModel subscriptionModel, SubscriptionAdvert subscriptionAdvert) {
		FlatSellRealtyModel model = new FlatSellRealtyModel();
		
		// TODO FIX IT TO PROPER IMPL
		
		Advert realty = subscriptionAdvert.advert;
		
		FlatSellAdvertModel realtyModel = new FlatSellAdvertModel(realty);
		
		model.title = getAdvertTitle(realtyModel);
		model.shortDescription = getAdvertShortDescription(realtyModel);
		model.description = getAdvertDescription(realtyModel.getDataModel());
		
		model.price = (Long)realty.data.get("price");
		
		model.priceText = String.valueOf(Math.round(model.price / 100000) * 0.1);
		
		model.hasImage = realty.hasPhoto;
		
		if (realty.photos != null && realty.photos.size() > 0) {
			model.imageUrl = realty.photos.get(0).path;
		} else {
			model.hasImage = false;
		}
		
		if (realty.publisher != null && realty.publisher.type != null) {
			switch (realty.publisher.type) {
				case COMPANY:
					model.publisher = "Строительная компания";
					break;
				case AGENT:
					model.publisher = "Риэлтор";
					break;
				case AGENT_COMPANY:
					model.publisher = "Риэлторская компания";
					break;
				case OWNER:
					model.publisher = "Хозяин";
					break;
				default:
					break;
			}
		}
		
		model.advertUrl = "TO_BE_DISCUSSED_ADVERT_ID_" + subscriptionAdvert.advert.id;
		
		model.subscriptionAdvert = subscriptionAdvert;
		
		return model;
	}
	
	
	private String getAdvertTitle(FlatSellAdvertModel model) {
		StringBuilder realtyTitle = new StringBuilder();
		
		// Template [ROOM], [ADDRESS], [PRICE]
		
		// [ROOM]
		// 1-комнатная кватрира
		Float rooms = model.getDataModel().getRooms();
		if (rooms != null) {
			realtyTitle.append(rooms.intValue()).append("-комнатная квартира, ");
		}
		
		// [ADDRESS]
		AdvertLocation locaion = model.getLocation();
		if (locaion != null) {
			if (StringUtils.isNotBlank(locaion.streetName)){
				realtyTitle.append(locaion.streetName);
				
				if (StringUtils.isNotBlank(locaion.houseNumber)) {
					realtyTitle.append(" ").append(locaion.houseNumber);
				}
				
				if (StringUtils.isNotBlank(locaion.cornerStreetName)) {
					realtyTitle.append(" — ").append(locaion.cornerStreetName);
				}
			}
		}
		
		return realtyTitle.toString().trim();
	}
	
	private String getAdvertShortDescription(FlatSellAdvertModel model) {
		StringBuilder shortDescription = new StringBuilder();
		
		//TEMPLATE: [REGION TREE], [FLOOR], [SQUARE]
		
		Long flatFloor = model.getDataModel().getFlatFloor();
		Long houseFloorCount = model.getDataModel().getHouseFloorCount();
		Float square = model.getDataModel().getSquare();
		AdvertLocation location = model.getLocation();
		
		// [REGION TREE]
		if (location != null) {
			if (location.regions != null && location.regions.size() > 0) {
				Region region = location.regions.get(0);
				if (region != null) {
					if (StringUtils.isNotBlank(region.name)) {
						shortDescription.append(region.name);
						
						if (location.regions.size() > 1) {
							Region regionLevel2 = location.regions.get(1);
							
							if (regionLevel2 != null) {
								if (StringUtils.isNotBlank(regionLevel2.name)) {
									shortDescription.append(", ").append(regionLevel2.name);
								}
							}
						}

						if (flatFloor != null || square != null) {
							shortDescription.append(", "); // because we need to know do we need to add it or not
						}

					}
				}
			}
		}
		
		// [FLOOR]
		if (flatFloor != null) {
			shortDescription.append(flatFloor).append(" этаж");
			
			if (houseFloorCount != null)
				shortDescription.append(" из ").append(houseFloorCount);
			
			if (square != null)
				shortDescription.append(", ");
		}
		
		// [SQUARE]
		if (square != null)
			shortDescription.append("площадь – ").append(square).append(" м2");
		
		return shortDescription.toString();
	}
	
	private String getAdvertDescription(FlatRealtyBaseDataModel model) {
		StringBuilder description = new StringBuilder();
		
		// ЖК
		// Тип дома
		// Год постройки
		// Состояние
		// жил площадь
		// кухня площадь
		// потолки
		// сан узлы
		// телефон
		// интернет
		// мебель
		// коменты
		
		// ЖК
		ResidentialComplex complex = model.getResidentialComplex(DB.DS());
		if (complex != null) {
			description.append("жил. комплекс ").append(complex.complexName).append(", ");
		}
		
		// Тип дома
		FlatRealtyEmailNotificationChannelBuilderHelper.addBuildingTypeDescription(model, description);
		
		// Год постройки
		Long houseYear = model.getHouseYear();
		if (houseYear != null) {
			description.append(houseYear).append(" г.п., ");
		}
		
		// Состояние
		FlatRealtyEmailNotificationChannelBuilderHelper.addRenovationTypeDescription(model, description);
		
		// жил площадь
		Float squareLiving = model.getSquareLiving();
		if (squareLiving != null) {
			description.append("жил. площадь ").append(squareLiving).append(" кв.м., ");
		}
		
		// кухня площадь
		Float squareKitchen = model.getSquareKitchen();
		if (squareKitchen != null) {
			description.append("кухня ").append(squareLiving).append(" кв.м., ");
		}
		
		// потолки
		Float ceilingHeight = model.getCeilingHeight();
		if (ceilingHeight != null) {
			description.append("потолки ").append(ceilingHeight).append(" м., ");
		}
		
		// сан узлы
		FlatRealtyEmailNotificationChannelBuilderHelper.addLavatoryTypeDescription(model, description);
		
		// телефон
		FlatRealtyEmailNotificationChannelBuilderHelper.addPhoneTypeDescription(model, description);
		
		// интернет
		FlatRealtyEmailNotificationChannelBuilderHelper.addInternetTypeDescription(model, description);
		
		// мебель
		FlatRealtyEmailNotificationChannelBuilderHelper.addFurnitureTypeDescription(model, description);
		
		// коменты
		String text = model.getText();
		if (StringUtils.isNotBlank(text)) {
			description.append(text);
		}
		
		return description.toString();
	}
	
	
	/**
	 * Метод возвращает текстовое описание подписки человека, понятное человеку :)
	 * @param subscription
	 * @return
	 */
	private String getSubscriptionDescriptionText(FlatSellSubscriptionModel subscriptionModel) {
		// Ищу квартиру 1-2-3 компнатную в городе Астана, с общей площадью от 50 до 100 квадратных метров, в доме от 1940 до 2301 года постройки, первый и последний этаж не предлагать
		// Что, Чего, Где, Площадь, Этажность, Цена, Доп опции
		
		// TODO Сделать правильный формат описания 
		
		StringBuilder descr = new StringBuilder();

		// Room
		Float roomFrom = subscriptionModel.getCriteriaModel().getRoomFrom();
		Float roomTo = subscriptionModel.getCriteriaModel().getRoomTo();
		descr.append("Ищу ");
		descr.append(FlatRealtyEmailNotificationChannelBuilderHelper.getDescriptionRoomCount(roomFrom, roomTo));
		descr.append(" для покупки, ");
		// Location			
		if (subscriptionModel.getCriteriaModel().getLocation(DB.DS()) != null) {
			descr.append("расположение: ");
			descr.append(FlatRealtyEmailNotificationChannelBuilderHelper.getDescriptionLocation(subscriptionModel.getCriteriaModel()));
			descr.append(", ");
		}
		// Residential Complex
		List<ResidentialComplex> complexes = subscriptionModel.getCriteriaModel().getResidentialComplexes(DB.DS());
		if (complexes != null) {
			descr.append("в ЖК ");
			descr.append(FlatRealtyEmailNotificationChannelBuilderHelper.getDescriptionResidentialComplexes(complexes));
			descr.append(", ");
		}
		// Square
		Float squareFrom = subscriptionModel.getCriteriaModel().getSquareFrom();
		Float squareTo = subscriptionModel.getCriteriaModel().getSquareTo();
		if (squareFrom != null || squareTo != null) {
			descr.append("c общей площадью ");
			descr.append(FlatRealtyEmailNotificationChannelBuilderHelper.getDescriptionSquare(squareFrom, squareTo));
			descr.append("квадратных метров, ");
		}
		// House years
		Long houseYearFrom = subscriptionModel.getCriteriaModel().getHouseYearFrom();
		Long houseYearTo = subscriptionModel.getCriteriaModel().getHouseYearTo();
		if (houseYearFrom != null || houseYearTo != null) {
			descr.append("в доме ");
			descr.append(FlatRealtyEmailNotificationChannelBuilderHelper.getDescriptionHouseYear(houseYearFrom, houseYearTo));
			descr.append(" года постройки, ");
		}
		
		// Floor
		Long flatFloorFrom = subscriptionModel.getCriteriaModel().getFlatFloorFrom();
		Long flatFloorTo = subscriptionModel.getCriteriaModel().getFlatFloorTo();
		if (flatFloorFrom != null || flatFloorTo != null) {
			descr.append("этажи ");
			descr.append(FlatRealtyEmailNotificationChannelBuilderHelper.getDescriptionFlatFloor(flatFloorFrom, flatFloorTo));
			descr.append(" ");
		}
		
		descr.append(", а так же другие критерии.");
		
		return descr.toString();
	}
	
	private List<NotificationParameter> buildParameters(SubscriptionModel subscription, List<SubscriptionAdvert> subscriptionAdverts) {
		// Пока параметров не предусмотрено
		return null;
	}

}
