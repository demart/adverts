package kz.aphion.adverts.subscription.builder.notification.email.realty;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationParameter;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatFurnitureType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatInternetType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRenovationType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertStatus;
import kz.aphion.adverts.persistence.subscription.criteria.SubscriptionCriteria;
import kz.aphion.adverts.subscription.builder.FM;
import kz.aphion.adverts.subscription.builder.notification.email.realty.models.FlatSellRealtyModel;

import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.mapping.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;

import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * Строитель email уведомления для рассылки объявлений об аренде квартир
 * 
 * @author artem.demidovich
 *
 * Created at Nov 9, 2017
 */
public class FlatRentRealtyEmailNotificationChannelBuilder {

	private static Logger logger = LoggerFactory.getLogger(FlatRentRealtyEmailNotificationChannelBuilder.class);
	
	/**
	 * Название файла шаблона в папке шаблонов
	 */
	private static final String EMAIL_TEMPLATE = "email-notification-realty-flat-rent-template-v1.ftlh.html";

	public NotificationChannel build(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) throws TemplateException, IOException {
		NotificationChannel email = new NotificationChannel();
		
		email.type = NotificationChannelType.EMAIL;
		email.addreseeId = subscription.user.email;
		
		email.title = buildTitle(subscription, subscriptionAdverts);
		email.content = buildContent(subscription, subscriptionAdverts);
		email.parameters = buildParameters(subscription, subscriptionAdverts);
		
		return email;
	}
	
	
	private String buildTitle(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) {		
		StringBuilder title = new StringBuilder();

		if (subscriptionAdverts == null || subscriptionAdverts.size() < 1) {
			// Strange, but we have to check
			title.append("Найдены новые объявления об аренде квартир по вашей подписке");
		} else {
			int endwith = subscriptionAdverts.size() % 10;	

			if (endwith == 0 || endwith > 4 || (endwith > 0 && subscriptionAdverts.size() > 10)) { // 10, 13, 28
				title.append("Найдено " + subscriptionAdverts.size() + " новых объявлений об аренде квартир по вашей подписке.");
			}
			
			if (endwith  > 1 && endwith < 5) { // 2, 4, 21
				title.append("Найдено " + subscriptionAdverts.size() + " новых объявления об аренде квартир по вашей подписке.");
			}
			
			if (endwith  == 1 && subscriptionAdverts.size() != 11) { // 2, 4, 21, but not 11
				title.append("Найдено " + subscriptionAdverts.size() + " новое объявление об аренде квартир по вашей подписке.");
			}
		}
		
		// TEMP		
		title.append(" [" + subscription.id + "]");
		
		return title.toString();
	}
	
	private String buildContent(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) throws TemplateException, IOException {

		Map<String, Object> root = new HashMap<>();

		// Put global object for access from the template
		root.put("subscription", subscription);
		root.put("user", subscription.user);
		root.put("notification", subscription.notification);
		
		root.put("subscriptionAdverts", subscriptionAdverts);
		root.put("adverts", buildAdvertModels(subscription, subscriptionAdverts));
		
		// Add specific objects for predefined fields
		root.put("subscriptionTitle", ""); // ???
		root.put("subscriptionDescriptionText", getSubscriptionDescriptionText(subscription));
		
		root.put("title", "Найдены новые объявления об аренде квартир");
		
		long totalAdvertsCount = DB.DS().createQuery(SubscriptionAdvert.class)
				.field("subscription.id").equal(subscription.id)
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
	
	private List<FlatSellRealtyModel> buildAdvertModels(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) {
		
		List<FlatSellRealtyModel> models = new ArrayList<FlatSellRealtyModel>();
		
		for (SubscriptionAdvert subscriptionAdvert : subscriptionAdverts) {
			FlatSellRealtyModel model = buildAdvertModel(subscription, subscriptionAdvert);
			if (model != null)
				models.add(model);
		}
		
		return models;
	}
	
	private FlatSellRealtyModel buildAdvertModel(Subscription subscription, SubscriptionAdvert subscriptionAdvert) {
		FlatSellRealtyModel model = new FlatSellRealtyModel();
		
		// TODO FIX IT TO PROPER IMPL
		
		Advert realty = subscriptionAdvert.advert;
		
		model.title = getAdvertTitle(realty);
		model.shortDescription = getAdvertShortDescription(realty);
		model.description = getAdvertDescription(realty);
		
		model.price = realty.price;
		
		model.priceText = String.valueOf(model.price);
		
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
	
	
	private String getAdvertTitle(Advert realty) {
		StringBuilder realtyTitle = new StringBuilder();
		
		// Template [ROOM], [ADDRESS]
		
		// [ROOM]
		// 1-комнатная кватрира
		Float rooms = (Float)realty.data.get("rooms");
		if (rooms != null) {
			realtyTitle.append(rooms.intValue()).append("-комнатная квартира, ");
		}
		
		// [ADDRESS]
		if (realty.location != null) {
			if (StringUtils.isNotBlank(realty.location.streetName)){
				realtyTitle.append(realty.location.streetName);
				
				if (StringUtils.isNotBlank(realty.location.houseNumber)) {
					realtyTitle.append(" ").append(realty.location.houseNumber);
				}
				
				if (StringUtils.isNotBlank(realty.location.cornerStreetName)) {
					realtyTitle.append(" — ").append(realty.location.cornerStreetName);
				}
			}
		}
		
		return realtyTitle.toString().trim();
	}
	
	private String getAdvertShortDescription(Advert realty) {
		StringBuilder shortDescription = new StringBuilder();
		
		//TEMPLATE: [REGION TREE], [FLOOR], [SQUARE]
		Long flatFloor = (Long)realty.data.get("flatFloor");
		Long houseFloorCount = (Long)realty.data.get("houseFloorCount");
		Float square = (Float)realty.data.get("square");
		
		// [REGION TREE]
		if (realty.location != null) {
			if (realty.location.regions != null && realty.location.regions.size() > 0) {
				Region region = realty.location.regions.get(0);
				if (region != null) {
					if (StringUtils.isNotBlank(region.name)) {
						shortDescription.append(region.name);
						
						if (realty.location.regions.size() > 1) {
							Region regionLevel2 = realty.location.regions.get(1);
							
							if (regionLevel2 != null) {
								if (StringUtils.isNotBlank(regionLevel2.name)) {
									shortDescription.append(", ").append(regionLevel2.name);
								}
							}
						}

						if (realty.data != null && (flatFloor != null || square != null)) {
							shortDescription.append(", "); // because we need to know do we need to add it or not
						}

					}
				}
			}
		}
		
		// [FLOOR]
		if (realty.data != null && flatFloor != null) {
			shortDescription.append(flatFloor).append(" этаж");
			
			if (houseFloorCount != null) {
				shortDescription.append(" из ").append(flatFloor);
			}
			
			if (square != null) {
				shortDescription.append(", ");
			}
			
		}
		
		// [SQUARE]
		if (realty.data != null && square != null) {
			shortDescription.append("площадь – ").append(square).append(" м2");
		}
		
		return shortDescription.toString();
	}
	
	private String getAdvertDescription(Advert realty) {
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
		if (realty.data.get("residentalComplex") != null) {
			Mapper mapper = new Mapper();
			mapper.getConverters().addConverter(CalendarConverter.class);
			ResidentialComplex residentalComplex = (DBObject)realty.data.get("residentalComplex") != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)realty.data.get("residentalComplex"), mapper.createEntityCache()) : null;
			if (residentalComplex != null)
				description.append("жил. комплекс ").append(residentalComplex.complexName).append(", ");
		}
		
		// Тип дома
		FlatBuildingType flatBuildingType = FlatBuildingType.valueOf((String)realty.data.get("flatBuildingType"));
		if (flatBuildingType != null) {
			switch (flatBuildingType) {
				case BLOCK:
					description.append("блочный, ");
					break;
				case BRICK:
					description.append("кирпичный, ");
					break;
				case FRAME_REED:
					description.append("каркасно-камишитовый, ");
					break;
				case MONOLITHIC:
					description.append("монолитный, ");
					break;
				case PANEL:
					description.append("панельный, ");
					break;
				case WOODEN:
					description.append("деревянный, ");
					break;
					
				case OTHER:
				case UNDEFINED:
				default:
					break;
			}
		}
		
		// Год постройки
		Long houseYear = (Long)realty.data.get("houseYear");
		if (houseYear != null) {
			description.append(houseYear).append(" г.п., ");
		}
		
		// Состояние
		FlatRenovationType renovationType = FlatRenovationType.valueOf((String)realty.data.get("renovationType"));
		if (renovationType != null) {
			switch (renovationType) {
				case AVARAGE:
					description.append("среднее, ");
					break;
				case BAD:
					description.append("требует ремонта, ");
					break;
				case EURO:
					description.append("евроремонт, ");
					break;
				case GOOD:
					description.append("хорошее, ");
					break;
				case OPEN_PLANNING:
					description.append("свободная планировка, ");
					break;
				case ROUGH_FINISH:
					description.append("черновая отделка, ");
					break;
				case UNDEFINED:
				default:
					break;
			}
		}
		
		// жил площадь
		Float squareLiving = (Float)realty.data.get("squareLiving");
		if (squareLiving != null) {
			description.append("жил. площадь ").append(squareLiving).append(" кв.м., ");
		}
		
		// кухня площадь
		Float squareKitchen = (Float)realty.data.get("squareKitchen");
		if (squareKitchen != null) {
			description.append("кухня ").append(squareLiving).append(" кв.м., ");
		}
		
		// потолки
		Float ceilingHeight = (Float)realty.data.get("ceilingHeight");
		if (ceilingHeight != null) {
			description.append("потолки ").append(ceilingHeight).append(" м., ");
		}
		
		// сан узлы
		FlatLavatoryType lavatoryType = FlatLavatoryType.valueOf((String)realty.data.get("lavatoryType"));
		if (lavatoryType != null) {
			switch (lavatoryType) {
				case COMBINED:
					description.append("санузел совмещенный, ");
					break;
				case NO_LAVATORY:
					description.append("нет санузла, ");
					break;
				case SEPARETED:
					description.append("санузел раздельный, ");
					break;
				case TWO_AND_MORE:
					description.append("санузел 2 с/у и более, ");
					break;
					
				case UNDEFINED:
				default:
					break;
			}
		}
		
		// телефон
		FlatPhoneType phoneType = FlatPhoneType.valueOf((String)realty.data.get("phoneType"));
		if (phoneType != null) {
			switch (phoneType) {
				case ABILITY_TO_CONNECT:
					description.append("телефон: есть возможность подключения, ");
					break;
				case LOCKING:
					description.append("телефон: блокиратор, ");
					break;
				case SEPARETED:
					description.append("телефон: отдельный, ");
					break;
					
				case NO_PHONE:
				case UNDEFINED:
				default:
					break;
			}
		}
		
		// интернет
		FlatInternetType internetType = FlatInternetType.valueOf((String)realty.data.get("internetType"));
		if (internetType != null) {
			switch (internetType) {
				case ADSL:
					description.append("интернет ADSL, ");
					break;
				case LAN:
					description.append("проводной интернет, ");
					break;
				case OPTIC:
					description.append("интернет оптика, ");
					break;
				case TV:
					description.append("интернет через TV кабель, ");
					break;
				
				case UNDEFINED:
				default:
					break;
			}
		}
		
		// мебель
		FlatFurnitureType furnitureType = FlatFurnitureType.valueOf((String)realty.data.get("furnitureType"));
		if (furnitureType != null) {
			switch (furnitureType) {
				case EMPTY:
					description.append("пустая, ");
					break;
				case FULLY_FURNITURED:
					description.append("полностью меблирована, ");
					break;
				case PARTLY_FURNITURED:
					description.append("частично меблирована, ");
					break;
				
				case UNDEFINED:
				default:
					break;
			}
		}
		
		// коменты
		String text = (String)realty.data.get("text");
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
	private String getSubscriptionDescriptionText(Subscription subscription) {
		// Ищу квартиру 1-2-3 компнатную в городе Астана, с общей площадью от 50 до 100 квадратных метров, в доме от 1940 до 2301 года постройки, первый и последний этаж не предлагать
		// Что, Чего, Где, Площадь, Этажность, Цена, Доп опции
		
		// TODO Сделать правильный формат описания 
		StringBuilder descr = new StringBuilder();
			
		// Room
		descr.append("Ищу ");
		descr.append(getDescriptionRoomCount(subscription.criteria));
		descr.append(" для аренды");
		// Rent Periods
		if (subscription.criteria.data.get("rentPeriods")!=null) {
			descr.append(getDescriptionRentPeriods(subscription.criteria));
		}
		descr.append(", ");
		// Location			
		if (subscription.criteria.location != null) {
			descr.append("расположение: ");
			descr.append(getDescriptionLocation(subscription.criteria));
			descr.append(", ");
		}
		// Residential Complex
		if (subscription.criteria.data.get("residentalComplexs") != null) {
			descr.append("в ЖК ");
			descr.append(getDescriptionResidentialComplexes(subscription.criteria));
			descr.append(", ");
		}
		
		// Square
		Float squareFrom = Float.valueOf((String)subscription.criteria.data.get("squareFrom"));
		Float squareTo = Float.valueOf((String)subscription.criteria.data.get("squareTo"));
		if (squareFrom != null || squareTo != null) {
			descr.append("c общей площадью ");
			descr.append(getDescriptionSquare(subscription.criteria));
			descr.append("квадратных метров, ");
		}
		// House years
		Long houseYearFrom = Long.valueOf((String)subscription.criteria.data.get("houseYearFrom"));
		Long houseYearTo = Long.valueOf((String)subscription.criteria.data.get("houseYearTo"));
		if (houseYearFrom != null || houseYearTo != null) {
			descr.append("в доме ");
			descr.append(getDescriptionHouseYear(subscription.criteria));
			descr.append(" года постройки, ");
		}
		
		// Floor
		Long flatFloorFrom = Long.valueOf((String)subscription.criteria.data.get("flatFloorFrom"));
		Long flatFloorTo = Long.valueOf((String)subscription.criteria.data.get("flatFloorTo"));
		if (flatFloorFrom != null || flatFloorTo != null) {
			descr.append("этажи ");
			descr.append(getDescriptionFlatFloor(subscription.criteria));
			descr.append(" ");
		}
		
		descr.append(", а так же другие критерии.");
		
		
		return descr.toString();
	}

	private Object getDescriptionRentPeriods(SubscriptionCriteria baseCriteria) {
		String rp = ((List<Object>)baseCriteria.data.get("rentPeriods")).size() > 0 ?  " (" : "";
		
		for (Object flatRentPeriodTypeStr : (List<Object>)baseCriteria.data.get("rentPeriods")) {
			FlatRentPeriodType flatRentPeriodType = FlatRentPeriodType.valueOf((String)flatRentPeriodTypeStr);
			switch (flatRentPeriodType) {
				case DAILY:
					rp += "посуточно, ";
					break;
				case MONTHLY:
					rp += "помесячной, ";
					break;
				case HOURLY:
					rp += "почасово, ";
					break;
				case QUARTERLY:
					rp += "поквартально, ";
					break;
				case UNDEFINED:
					break;
				default:
					break;
			}
		}
		
		return StringUtils.removeEnd(rp, ", ") + ")";
	}


	private String getDescriptionRoomCount(SubscriptionCriteria baseCriteria) {
		Float roomFrom = Float.valueOf((String)baseCriteria.data.get("roomFrom"));
		Float roomTo = Float.valueOf((String)baseCriteria.data.get("roomTo"));
		
		if (roomFrom == null && roomTo == null) {
			return "квартиру";
		} else {
			if (roomFrom == roomTo) {
				return String.valueOf(Math.round(roomFrom.floatValue())) + "-комнатную квартиру";
			} else {
				if (roomFrom != null && roomTo != null) {
					int start = Math.round(roomFrom.floatValue());
					int end = Math.round(roomTo.floatValue());
						
					String startEnd = "";
					for (int i=start; i<end; i++) {
						if (i == start) {
							startEnd += i;
						} else {
							startEnd += "-"+ i;
						}
					}
					return startEnd + "-комнатную квартиру";
				} else {
					// странные кейсы, возможно не должно существовать вовсе
					if (roomFrom != null) {
						return String.valueOf(Math.round(roomFrom.floatValue())) + "-комнатную и больше квартиру";
					}
					if (roomTo != null) {
						return String.valueOf(Math.round(roomTo.floatValue())) + "-комнатную и меньше квартиру";
					}	
				}
			}
		}
		return "";
	}
	
	private String getDescriptionLocation(SubscriptionCriteria baseCriteria) {
		// Не учитывает выбранные объекты на карте
		String regions = "";
		for (Region region : baseCriteria.location.regions) {
			regions += region.name + ", ";
		}
		return StringUtils.removeEnd(regions, ", ");
	}
	
	private Object getDescriptionResidentialComplexes(SubscriptionCriteria baseCriteria) {
		String rcs = "";
		for (Object rc : (List<Object>)baseCriteria.data.get("residentalComplexs")) {
			Mapper mapper = new Mapper();
			mapper.getConverters().addConverter(CalendarConverter.class);
			ResidentialComplex residentalComplex = (DBObject)rc != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)rc, mapper.createEntityCache()) : null;
			if (residentalComplex != null)
				rcs += residentalComplex.complexName + ", ";
		}
		return StringUtils.removeEnd(rcs, ", ");
	}
	
	private Object getDescriptionSquare(SubscriptionCriteria baseCriteria) {
		Float squareFrom = Float.valueOf((String)baseCriteria.data.get("squareFrom"));
		Float squareTo = Float.valueOf((String)baseCriteria.data.get("squareTo"));
		
		String square = "";
		if (squareFrom != null) {
			square += "от " + squareFrom + " ";
		}
		if (squareTo != null) {
			square += "до " + squareTo + " ";
		}
		return square;
	}	

	private Object getDescriptionHouseYear(SubscriptionCriteria baseCriteria) {
		Long houseYearFrom = Long.valueOf((String)baseCriteria.data.get("houseYearFrom"));
		Long houseYearTo = Long.valueOf((String)baseCriteria.data.get("houseYearTo"));
		String years = "";
		if (houseYearFrom != null) {
			years += "от " + houseYearFrom + " ";
		}
		if (houseYearTo != null) {
			years += "до " + houseYearTo + " ";
		}
		return years;
	}
	
	private Object getDescriptionFlatFloor(SubscriptionCriteria baseCriteria) {
		Long flatFloorFrom = Long.valueOf((String)baseCriteria.data.get("flatFloorFrom"));
		Long flatFloorTo = Long.valueOf((String)baseCriteria.data.get("flatFloorTo"));
		String floors = "";
		if (flatFloorFrom != null) {
			floors += "c " + flatFloorFrom + " ";
		}
		if (flatFloorTo != null) {
			floors += "по " + flatFloorTo + " ";
		}
		return floors;
	}

	private List<NotificationParameter> buildParameters(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) {
		// Пока параметров не предусмотрено
		return null;
	}
	
}
