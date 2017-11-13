package kz.aphion.adverts.subscription.builder.notification.email.realty;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import kz.aphion.adverts.notification.mq.models.NotificationChannel;
import kz.aphion.adverts.notification.mq.models.NotificationChannelType;
import kz.aphion.adverts.notification.mq.models.NotificationParameter;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvert;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtyFlatBaseSubscriptionCriteria;
import kz.aphion.adverts.persistence.subscription.criteria.realty.RealtySellFlatSubscriptionCriteria;
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
		if (subscription.criteria instanceof RealtySellFlatSubscriptionCriteria == false) {
			logger.error("FlatSellRealtyEmailNotificationChannelBuilder was selected by mistake, please check subscription.id: " + subscription.id);
			return null;
		}
		
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
		
		root.put("title", "Найдены новые объявления о продаже квартир");
		
		root.put("totalAdvertsCount", subscription.adverts != null ? subscription.adverts.size() : 0);
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
		
		FlatSellRealty realty = (FlatSellRealty)subscriptionAdvert.advert;
		
		model.title = getAdvertTitle(realty);
		model.shortDescription = getAdvertShortDescription(realty);
		model.description = getAdvertDescription(realty);
		
		model.price = ((Realty)subscriptionAdvert.advert).price;
		
		model.priceText = String.valueOf(Math.round(model.price / 100000) * 0.1);
		
		model.hasImage = ((Realty)subscriptionAdvert.advert).hasPhoto;
		
		if (((Realty)subscriptionAdvert.advert).photos != null && ((Realty)subscriptionAdvert.advert).photos.size() > 0) {
			model.imageUrl = ((Realty)subscriptionAdvert.advert).photos.get(0).path;
		} else {
			model.hasImage = false;
		}
		
		if (((Realty)subscriptionAdvert.advert).publisher != null && ((Realty)subscriptionAdvert.advert).publisher.publisherType != null) {
			switch (((Realty)subscriptionAdvert.advert).publisher.publisherType) {
				case DEVELOPER_COMPANY:
					model.publisher = "Строительная компания";
					break;
				case REALTOR:
					model.publisher = "Риэлтор";
					break;
				case REALTOR_COMPANY:
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
	
	
	private String getAdvertTitle(FlatSellRealty realty) {
		StringBuilder realtyTitle = new StringBuilder();
		
		// Template [ROOM], [ADDRESS], [PRICE]
		
		// [ROOM]
		// 1-комнатная кватрира
		if (realty.data.rooms != null) {
			realtyTitle.append(realty.data.rooms.intValue()).append("-комнатная квартира, ");
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
	
	private String getAdvertShortDescription(FlatSellRealty realty) {
		StringBuilder shortDescription = new StringBuilder();
		
		//TEMPLATE: [REGION TREE], [FLOOR], [SQUARE]
		
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

						if (realty.data != null && (realty.data.flatFloor != null || realty.data.square != null)) {
							shortDescription.append(", "); // because we need to know do we need to add it or not
						}

					}
				}
			}
		}
		
		// [FLOOR]
		if (realty.data != null && realty.data.flatFloor != null) {
			shortDescription.append(realty.data.flatFloor).append(" этаж");
			
			if (realty.data.houseFloorCount != null) {
				shortDescription.append(" из ").append(realty.data.flatFloor);
			}
			
			if (realty.data.square != null) {
				shortDescription.append(", ");
			}
			
		}
		
		// [SQUARE]
		if (realty.data != null && realty.data.square != null) {
			shortDescription.append("площадь – ").append(realty.data.square).append(" м2");
		}
		
		return shortDescription.toString();
	}
	
	private String getAdvertDescription(FlatSellRealty realty) {
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
		if (realty.data.residentalComplex != null) {
			description.append("жил. комплекс ").append(realty.data.residentalComplex.complexName).append(", ");
		}
		
		// Тип дома
		if (realty.data.flatBuildingType != null) {
			switch (realty.data.flatBuildingType) {
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
		if (realty.data.houseYear != null) {
			description.append(realty.data.houseYear).append(" г.п., ");
		}
		
		// Состояние
		if (realty.data.renovationType != null) {
			switch (realty.data.renovationType) {
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
		if (realty.data.squareLiving != null) {
			description.append("жил. площадь ").append(realty.data.squareLiving).append(" кв.м., ");
		}
		
		// кухня площадь
		if (realty.data.squareKitchen != null) {
			description.append("кухня ").append(realty.data.squareLiving).append(" кв.м., ");
		}
		
		// потолки
		if (realty.data.ceilingHeight != null) {
			description.append("потолки ").append(realty.data.ceilingHeight).append(" м., ");
		}
		
		// сан узлы
		if (realty.data.lavatoryType != null) {
			switch (realty.data.lavatoryType) {
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
		if (realty.data.phoneType != null) {
			switch (realty.data.phoneType) {
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
		if (realty.data.internetType != null) {
			switch (realty.data.internetType) {
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
		if (realty.data.furnitureType != null) {
			switch (realty.data.furnitureType) {
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
		if (StringUtils.isNotBlank(realty.data.text)) {
			description.append(realty.data.text);
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
		
		if (subscription.criteria instanceof RealtySellFlatSubscriptionCriteria) {
			
			RealtyFlatBaseSubscriptionCriteria baseCriteria = (RealtyFlatBaseSubscriptionCriteria)subscription.criteria;
			
			// Room
			descr.append("Ищу ");
			descr.append(getDescriptionRoomCount(baseCriteria));
			descr.append(" для покупки, ");
			// Location			
			if (baseCriteria.location != null) {
				descr.append("расположение: ");
				descr.append(getDescriptionLocation(baseCriteria));
				descr.append(", ");
			}
			// Residential Complex
			if (baseCriteria.residentalComplexs != null) {
				descr.append("в ЖК ");
				descr.append(getDescriptionResidentialComplexes(baseCriteria));
				descr.append(", ");
			}
			
			// Square
			if (baseCriteria.squareFrom != null || baseCriteria.squareTo != null) {
				descr.append("c общей площадью ");
				descr.append(getDescriptionSquare(baseCriteria));
				descr.append("квадратных метров, ");
			}
			// House years
			if (baseCriteria.houseYearFrom != null || baseCriteria.houseYearTo != null) {
				descr.append("в доме ");
				descr.append(getDescriptionHouseYear(baseCriteria));
				descr.append(" года постройки, ");
			}
			
			// Floor
			if (baseCriteria.flatFloorFrom != null || baseCriteria.flatFloorTo != null) {
				descr.append("этажи ");
				descr.append(getDescriptionFlatFloor(baseCriteria));
				descr.append(" ");
			}
			
			descr.append(", а так же другие критерии.");
		}
		
		return descr.toString();
	}

	private String getDescriptionRoomCount(RealtyFlatBaseSubscriptionCriteria baseCriteria) {
		if (baseCriteria.roomFrom == null && baseCriteria.roomTo == null) {
			return "квартиру";
		} else {
			if (baseCriteria.roomFrom == baseCriteria.roomTo) {
				return String.valueOf(Math.round(baseCriteria.roomFrom.floatValue())) + "-комнатную квартиру";
			} else {
				if (baseCriteria.roomFrom != null && baseCriteria.roomTo != null) {
					int start = Math.round(baseCriteria.roomFrom.floatValue());
					int end = Math.round(baseCriteria.roomTo.floatValue());
						
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
					if (baseCriteria.roomFrom != null) {
						return String.valueOf(Math.round(baseCriteria.roomFrom.floatValue())) + "-комнатную и больше квартиру";
					}
					if (baseCriteria.roomTo != null) {
						return String.valueOf(Math.round(baseCriteria.roomTo.floatValue())) + "-комнатную и меньше квартиру";
					}	
				}
			}
		}
		return "";
	}
	
	private String getDescriptionLocation(RealtyFlatBaseSubscriptionCriteria baseCriteria) {
		// Не учитывает выбранные объекты на карте
		String regions = "";
		for (Region region : baseCriteria.location.regions) {
			regions += region.name + ", ";
		}
		return StringUtils.removeEnd(regions, ", ");
	}
	
	private Object getDescriptionResidentialComplexes(RealtyFlatBaseSubscriptionCriteria baseCriteria) {
		String rcs = "";
		for (ResidentialComplex rc : baseCriteria.residentalComplexs) {
			rcs += rc.complexName + ", ";
		}
		return StringUtils.removeEnd(rcs, ", ");
	}
	
	private Object getDescriptionSquare(RealtyFlatBaseSubscriptionCriteria baseCriteria) {
		String square = "";
		if (baseCriteria.squareFrom != null) {
			square += "от " + baseCriteria.squareFrom + " ";
		}
		if (baseCriteria.squareTo != null) {
			square += "до " + baseCriteria.squareTo + " ";
		}
		return square;
	}	

	private Object getDescriptionHouseYear(RealtyFlatBaseSubscriptionCriteria baseCriteria) {
		String years = "";
		if (baseCriteria.houseYearFrom != null) {
			years += "от " + baseCriteria.houseYearFrom + " ";
		}
		if (baseCriteria.houseYearTo != null) {
			years += "до " + baseCriteria.houseYearTo + " ";
		}
		return years;
	}
	
	private Object getDescriptionFlatFloor(RealtyFlatBaseSubscriptionCriteria baseCriteria) {
		String floors = "";
		if (baseCriteria.flatFloorFrom != null) {
			floors += "c " + baseCriteria.flatFloorFrom + " ";
		}
		if (baseCriteria.houseYearTo != null) {
			floors += "по " + baseCriteria.flatFloorTo + " ";
		}
		return floors;
	}
	
	private List<NotificationParameter> buildParameters(Subscription subscription, List<SubscriptionAdvert> subscriptionAdverts) {
		// Пока параметров не предусмотрено
		return null;
	}

}
