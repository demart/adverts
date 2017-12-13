package kz.aphion.adverts.subscription.builder.notification.email.realty;

import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.models.realty.data.FlatRealtyBaseDataModel;
import kz.aphion.adverts.models.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatFurnitureType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatInternetType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRenovationType;
import kz.aphion.adverts.models.subscription.realty.FlatBaseRealtySubscriptionCriteriaModel;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

/**
 * Класс помощник для построения уведомлений по квартирам
 * @author artem.demidovich
 *
 * Created at Dec 13, 2017
 */
public class FlatRealtyEmailNotificationChannelBuilderHelper {

	
	public static void addBuildingTypeDescription(FlatRealtyBaseDataModel model, StringBuilder description){
		FlatBuildingType flatBuildingType = model.getFlatBuildingType();
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
	}
	
	public static void addRenovationTypeDescription(FlatRealtyBaseDataModel model, StringBuilder description){
		FlatRenovationType renovationType = model.getRenovationType();
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
	}
	
	
	public static void addLavatoryTypeDescription(FlatRealtyBaseDataModel model, StringBuilder description){
		FlatLavatoryType lavatoryType = model.getLavatoryType();
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
	}
	
	public static void addPhoneTypeDescription(FlatRealtyBaseDataModel model, StringBuilder description){
		FlatPhoneType phoneType = model.getPhoneType();
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
	}
	
	public static void addInternetTypeDescription(FlatRealtyBaseDataModel model, StringBuilder description){
		FlatInternetType internetType = model.getInternetType();
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
	}
	
	public static void addFurnitureTypeDescription(FlatRealtyBaseDataModel model, StringBuilder description) {
		FlatFurnitureType furnitureType = model.getFurnitureType();
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
	}
	
	public static String getDescriptionRoomCount(Float roomFrom, Float roomTo) {
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
	
	public static Object getDescriptionFlatFloor(Long flatFloorFrom, Long flatFloorTo) {
		String floors = "";
		if (flatFloorFrom != null) {
			floors += "c " + flatFloorFrom + " ";
		}
		if (flatFloorTo != null) {
			floors += "по " + flatFloorTo + " ";
		}
		return floors;
	}
	
	public static Object getDescriptionHouseYear(Long houseYearFrom, Long houseYearTo) {
		String years = "";
		if (houseYearFrom != null) {
			years += "от " + houseYearFrom + " ";
		}
		if (houseYearTo != null) {
			years += "до " + houseYearTo + " ";
		}
		return years;
	}
	
	public static String getDescriptionLocation(FlatBaseRealtySubscriptionCriteriaModel model) {
		// Не учитывает выбранные объекты на карте
		String regions = "";
		
		for (String regionId : model.getLocation(DB.DS()).regionIds) {
			Region region = DB.DS().get(Region.class, new ObjectId(regionId));
			if (region != null)
				regions += region.name + ", ";
		}
		return StringUtils.removeEnd(regions, ", ");
	}
	
	public static Object getDescriptionResidentialComplexes(List<ResidentialComplex> complexes) {
		String rcs = "";
		
		for (ResidentialComplex complex : complexes) {
			if (complex != null)
				rcs += complex.complexName + ", ";
		}
		
		return StringUtils.removeEnd(rcs, ", ");
	}
	
	public static Object getDescriptionSquare(Float squareFrom, Float squareTo) {
		String square = "";
		if (squareFrom != null) {
			square += "от " + squareFrom + " ";
		}
		if (squareTo != null) {
			square += "до " + squareTo + " ";
		}
		return square;
	}	


	
}
