package kz.aphion.adverts.crawler.kn.mappers.flat;

import kz.aphion.adverts.persistence.realty.MortgageStatus;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatFurnitureType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlatDataMapperUtils {

	private static Logger logger = LoggerFactory.getLogger(FlatDataMapperUtils.class);
	
	/**
	 * Конвертирует значение этажа из таблицы в тип Long
	 * @param valueOnTable
	 * @return
	 */
	public static Long convertFloor (String valueOnTable) {
		Long floor = null;
		//В общем у кн.кз есть фигня, типа: Этаж	6.7/7 . Тут избавляемся от этого
		if (valueOnTable.indexOf(".") > 0)
			valueOnTable = valueOnTable.substring(valueOnTable.indexOf(".") + 1);
		
		if (valueOnTable.indexOf("/") > 0)
			floor = Long.parseLong(valueOnTable.substring(0, valueOnTable.indexOf("/")));
		else
			floor = Long.parseLong(valueOnTable);
		
		return floor;
	}
	
	/**
	 * Конвертирует значение количество этажей в доме из таблицы в тип Long
	 * @param valueOnTable
	 * @return
	 */
	public static Long convertHouseFloorCount (String valueOnTable) {
		Long houseFloorCount = null;
		if (valueOnTable.indexOf("/") > 0)
			houseFloorCount = Long.parseLong(valueOnTable.substring(valueOnTable.indexOf("/") + 1));
		return houseFloorCount;
	}
	
	/**
	 * Конвертирует значение количество комнат из таблицы в тип Float
	 * @param valueOnTable
	 * @return
	 */
	public static Float convertLiveRooms (String valueOnTable) {
		Float liveRooms;
		liveRooms = Float.parseFloat(valueOnTable);
		return liveRooms;
	}
	
	/**
	 * Конвертирует значение год постройки в тип Long
	 * @param valueOnTable
	 * @return
	 */
	public static Long convertHouseYear (String valueOnTable) {
		Long houseYear;
		houseYear = Long.parseLong(valueOnTable);
		return houseYear;
	}
	
	/**
	 * Выдирает и конвертирует значение в тип Float общей площади из таблицы
	 * @param valueOnTable
	 * @return
	 */
	public static Float convertSquare (String valueOnTable) {
		Float square;
		
		//Возможны случаи:
		//1. 49
		//2. 49/24/10
		//3. 49/10
		//И данное  условие  полностью подходит для получение общей площади
		if (valueOnTable.indexOf("/") > 0) {
			square = Float.parseFloat(valueOnTable.substring(0, valueOnTable.indexOf("/")).replaceAll(",", "."));
		}
		else
			square = Float.parseFloat(valueOnTable.replaceAll(",", "."));
		
		return square;
	}
	
	/**
	 * Выдирает и конвертирует значение в тип Float жилой площади из таблицы
	 * 
	 * @param valueOnTable
	 * @return
	 */
	public static Float convertLivingSquare (String valueOnTable) {
		Float livingSquare = null;
		String tempString = valueOnTable;
		//Бывают случаи, что не всегда указана жилая площадь.
		//Формат 49/24/10; 24 - жилая площадь
		if (tempString.indexOf("/") > 0) {
			tempString = tempString.substring(tempString.indexOf("/") + 1);
			
			if (tempString.indexOf("/") > 0)
				livingSquare = Float.parseFloat(tempString.substring(0, tempString.indexOf("/")).replaceAll(",", "."));
		}
		return livingSquare;
	}
	
	/**
	 * Выдирает и конвертирует значение в тип Float площади кухни из таблицы
	 * @param valueOnTable
	 * @return
	 */
	public static Float  convertKitchenSquare (String valueOnTable) {
		Float kitchenSquare = null;
		String tempString = valueOnTable;
		if (tempString.indexOf("/") > 0) {
			tempString = tempString.substring(tempString.indexOf("/") + 1);
			
			if (tempString.indexOf("/") > 0) {
				if (!tempString.substring(tempString.indexOf("/") + 1).equals("студия"))
					kitchenSquare = Float.parseFloat(tempString.substring(tempString.indexOf("/") + 1).replaceAll(",", "."));
			}
			
			else
				if (!tempString.equals("студия"))
					kitchenSquare = Float.parseFloat(tempString.replaceAll(",", "."));
		}
		return kitchenSquare;
	}
	
	/**
	 * Конвертирует статус залога
	 * @param valueOnTable
	 * @return
	 */
	public static MortgageStatus getFlatMortgageStatus(String valueOnTable) {
		switch (valueOnTable) {
			case "Нет":
				return MortgageStatus.NO;
			case "Да":
				return MortgageStatus.YES;
			default:
				logger.error("ATTENTION! Found new [data.mortgage] value: " + valueOnTable);
				return MortgageStatus.UNDEFINED;
		}
	}
	
	/**
	 * Конвертирует материал стен
	 * @param valueOnTable
	 * @return
	 */
	public static FlatBuildingType getFlatBuildingType(String valueOnTable) {

		switch (valueOnTable) {
			case "кирпичный": // кирпичный
				return FlatBuildingType.BRICK;
			case "панельный": // панельный
				return FlatBuildingType.PANEL;
			case "монолитный": // монолитный
				return FlatBuildingType.MONOLITHIC;
			case "каркасно-камышитовый": // каркасно-камышитовый
				return FlatBuildingType.FRAME_REED;
		//TODO попросить добавить данные типы
			case "шлакоблочный": // шлакоблочный
				return FlatBuildingType.OTHER;
			case "блочный": // блочный
				return FlatBuildingType.OTHER;
			case "деревянный": // деревянный
				return FlatBuildingType.OTHER;
			case "железобетонный": // железобетонный
				return FlatBuildingType.OTHER;
			case "кирпично-ригельный": // кирпично-ригельный
				return FlatBuildingType.OTHER;
			default:
				logger.error("ATTENTION! Found new [data.flat.building] value: " + valueOnTable);
				return FlatBuildingType.UNDEFINED;
		}
	}
	
	//TODO мапинг комплексов
	/*
	public static void mapResidentialComplex(ResidentialComplex complex) {
		if (StringUtils.isBlank(complex.externalComplexId))
			return;
		
			KnResidentalComplexEntity complexEntity = KnDataManager.getResidentalComplex(complex.externalComplexName);
			
			if (complexEntity == null) {
				// Пробуем замапить по Имени и Региону
				// А так как нету региона пока здесь, оставлю это затею
				Logger.error("KrishaResidentialComplex with id [%s] not found!", complex.externalComplexId);
			} else {
				if (complexEntity.complex != null) {
					complex.relationId = complexEntity.id;
					complex.name = complexEntity.name;
				} else {
					Logger.error("KrishaResidentialComplex with id [%s] doens't have link to ResidentialComplex.");
				}
			}
	}
*/
	/**
	 * Конвертирует цену
	 * @param text
	 * @return
	 */
	public static Long convertPrice(String text) {
		Long price;
		if (text.indexOf(" тг.") > 0)
			price = Long.parseLong(text.substring(1, text.indexOf(" тг.")).replaceAll(" ", ""));
		else
			price = Long.parseLong(text.substring(1, text.indexOf(" $")).replaceAll(" ", ""));
		return price;
	}

	/**
	 * Конвертируем тип мебели
	 * @param text
	 * @return
	 */
	public static FlatFurnitureType convertFurniture(String text) {
		if (text.equals("меблированная") || text.equals("мебель"))
			return FlatFurnitureType.FULLY_FURNITURED;
		else
			return FlatFurnitureType.EMPTY;
	}

	/**
	 * Конвертирует название ЖК
	 * @param text
	 * @return
	 */
	public static String convertComplexName(String text) {
        String complexName = null;
        if (text.length() > 0)
        	complexName = text.substring(3);
		return complexName;
	}



}
