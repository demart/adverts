package kz.aphion.adverts.crawler.irr.mappers.flat;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.irr.mappers.AbstractAdvertMapper;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертирует объявления о продаже или арнеде квартир
 * 
 * @author denis.krylov
 *
 */
public class FlatRentDataMapper extends AbstractAdvertMapper<FlatRentRealty> {

	private static Logger logger = LoggerFactory.getLogger(FlatRentDataMapper.class);


	public FlatRentDataMapper(FlatRentRealty realty) {
		super(realty);
	}
	
	@Override
	public void mapAdvertData(List<Map<String, Object>> customFields, String description) {
		
		realty.data = new FlatRentData();
		
		realty.type = RealtyType.FLAT;
		realty.operation = RealtyOperationType.RENT;
		
		//приходится передавать отдельно описание.
		//Так как все характеристики лежат в массиве, а текст отдельно
		//а характеристи у каждой категории разные, и соотвественно нужны будут разные мапперы на характеристики.
		if (description != null)
			realty.data.text = description;
		
		//на irr.kz параметры делятся по определенным группам, которые лежат в одном массиве параметров "group_custom_fields"
		//группы представляют собой массивы параметров "custom_fields"
		//Пример параметра: {name="", title="", value=""}
		
		for (Map<String, Object> customField : customFields) {
			for (Entry<String, Object> field : customField.entrySet()) {
				if (field.getKey().equals("custom_fields")) {
					List<Map<String, Object>> allFields = (List<Map<String, Object>>) field.getValue();
					String nameOfField = null;
					
					for (Map<String, Object> oneOfField : allFields) {
						for (Entry<String, Object> keys : oneOfField.entrySet()) {
							if (keys.getKey().equals("name"))
								nameOfField = (String) keys.getValue();
							
							if (keys.getKey().equals("value")) {
								switch (nameOfField) {
									//Количество комнат
									case "rooms":
										String rooms = (String)keys.getValue();
										rooms = rooms.replace(",", ".");
										realty.data.rooms = Float.parseFloat(rooms);
										break;
									
									//Общая площадь	
									case "meters-total":
										String square = (String)keys.getValue();
										square = square.replaceAll(",", ".");
										realty.data.square = Float.parseFloat(square);
										break;
									
									//Жилая площадь
									case "meters-living":
										String livingSquare = (String)keys.getValue();
										livingSquare = livingSquare.replaceAll(",", ".");
										realty.data.squareLiving = Float.parseFloat(livingSquare);
										break;
										
									//Площадь кухни
									case "kitchen":
										String kitchenSquare = (String)keys.getValue();
										kitchenSquare = kitchenSquare.replaceAll(",", ".");
										realty.data.squareKitchen = Float.parseFloat(kitchenSquare);
										break;
										
										//Санузел
										case "toilet":
											String toilet = (String)keys.getValue();
											switch (toilet) {
											case "совмещённый":
												realty.data.lavatoryType = FlatLavatoryType.COMBINED;
												break;
											case "раздельный":
												realty.data.lavatoryType = FlatLavatoryType.SEPARETED;
												break;
											case "2 и более":
												realty.data.lavatoryType = FlatLavatoryType.TWO_AND_MORE;
												break;
											}
											break;
										
									//Материал стен
									case "walltype":
										String wallType = (String)keys.getValue();
										switch (wallType) {
											case "блочный":
											//	realty.data.flatBuildingType = FlatBuildingType.BLOCK;
												break;
											case "брус":
												realty.data.flatBuildingType = FlatBuildingType.OTHER;
												break;
											case "деревянный":
											//	realty.data.flatBuildingType = FlatBuildingType.WOODEN;
												break;
											case "железобетон":
												realty.data.flatBuildingType = FlatBuildingType.OTHER;
												break;
											case "кирпично-монолитный":
												realty.data.flatBuildingType = FlatBuildingType.OTHER;
												break;
											case "кирпичный":
												realty.data.flatBuildingType = FlatBuildingType.BRICK;
												break;
											case "монолит":
												realty.data.flatBuildingType = FlatBuildingType.MONOLITHIC;
												break;
											case "панельный":
												realty.data.flatBuildingType = FlatBuildingType.PANEL;
												break;
											case "саманный":
												realty.data.flatBuildingType = FlatBuildingType.OTHER;
												break;
										}
										break;
										
									//Высота потолков
									case "house-ceiling-height":
										String ceilingHeight = (String)keys.getValue();
										ceilingHeight = ceilingHeight.replace(",", "");
										realty.data.ceilingHeight = Float.parseFloat(ceilingHeight);
										break;
										
									//Этаж
									case "etage":
										String etage = (String)keys.getValue();
										realty.data.flatFloor = Long.parseLong(etage);
										break;
																	
								    //Всего этажей
									case "etage-all":
										String etageAll = (String)keys.getValue();
										realty.data.houseFloorCount = Long.parseLong(etageAll);
										break;
										
									//Год постройки
									case "house-year":
										String houseYear = (String)keys.getValue();
										realty.data.houseYear = Long.parseLong(houseYear);
										break;
										
									//Улица
									case "mapStreet":
										String street = (String)keys.getValue();
										realty.location.streetName = street;
										break;
										
									//Номер дома
									case "mapHouseNr":
										String house = (String)keys.getValue();
										realty.location.houseNumber = house;
										break;
										
									//Телефон
									case "telephone":
										Boolean isTelephone = (Boolean)keys.getValue();
										if (isTelephone)
											realty.data.phoneType = FlatPhoneType.SEPARETED;
										else
											realty.data.phoneType = FlatPhoneType.NO_PHONE;
										break;
										
									//Интернет
									case "internet":
										Boolean isInternet = (Boolean)keys.getValue();
										//TODO придумать как поступить с инетом
										break;
										
									//Балкон
									case "balcony":
										Boolean isBalcony = (Boolean)keys.getValue();
									//	if (isBalcony)
									//		realty.data.balconyType = FlatBalconyType.BALCONY;
										break;
										
									//Период аренды
									case "rent_period":
										String rentPeriod = (String)keys.getValue();
										switch (rentPeriod) {
											case "Долгосрочная":
												realty.data.rentPeriod = FlatRentPeriodType.MONTHLY;
												break;
											case "час":
												realty.data.rentPeriod = FlatRentPeriodType.HOURLY;
												break;
											case "месяц":
												realty.data.rentPeriod = FlatRentPeriodType.MONTHLY;
											    break;
											case "сутки":
												realty.data.rentPeriod = FlatRentPeriodType.DAILY;
											    break;
											case "выходные":
												//TODO добавиь выходные
												realty.data.rentPeriod = FlatRentPeriodType.DAILY;
											    break;
										}
								}
							}
						}
					}
				}
			}
		}
	}
}
