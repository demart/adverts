package kz.aphion.adverts.crawler.irr.mappers.flat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.irr.mappers.AbstractAdvertMapper;
import kz.aphion.adverts.models.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертирует объявления о продаже или арнеде квартир
 * 
 * @author denis.krylov
 *
 */
public class FlatRentDataMapper extends AbstractAdvertMapper<Advert> {

	private static Logger logger = LoggerFactory.getLogger(FlatRentDataMapper.class);


	public FlatRentDataMapper(Advert realty) {
		super(realty);
	}
	
	@Override
	public void mapAdvertData(List<Map<String, Object>> customFields, String description) {
		
		realty.type = AdvertType.REALTY;
		realty.subType = RealtyType.FLAT.toString();
		realty.operation = AdvertOperationType.RENT;
		
		//приходится передавать отдельно описание.
		//Так как все характеристики лежат в массиве, а текст отдельно
		//а характеристи у каждой категории разные, и соотвественно нужны будут разные мапперы на характеристики.
		if (description != null)
			realty.data.put("text", description);
		
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
										realty.data.put("rooms", Float.parseFloat(rooms));
										break;
									
									//Общая площадь	
									case "meters-total":
										String square = (String)keys.getValue();
										square = square.replaceAll(",", ".");
										realty.data.put("square", Float.parseFloat(square));
										break;
									
									//Жилая площадь
									case "meters-living":
										String livingSquare = (String)keys.getValue();
										livingSquare = livingSquare.replaceAll(",", ".");
										realty.data.put("squareLiving", Float.parseFloat(livingSquare));
										break;
										
									//Площадь кухни
									case "kitchen":
										String kitchenSquare = (String)keys.getValue();
										kitchenSquare = kitchenSquare.replaceAll(",", ".");
										realty.data.put("squareKitchen", Float.parseFloat(kitchenSquare));
										break;
										
										//Санузел
										case "toilet":
											String toilet = (String)keys.getValue();
											switch (toilet) {
											case "совмещённый":
												realty.data.put("lavatoryType", FlatLavatoryType.COMBINED);
												break;
											case "раздельный":
												realty.data.put("lavatoryType", FlatLavatoryType.SEPARETED);
												break;
											case "2 и более":
												realty.data.put("lavatoryType", FlatLavatoryType.TWO_AND_MORE);
												break;
											}
											break;
										
									//Материал стен
									case "walltype":
										String wallType = (String)keys.getValue();
										switch (wallType) {
											case "блочный":
												realty.data.put("flatBuildingType", FlatBuildingType.BLOCK);
												break;
											case "брус":
												realty.data.put("flatBuildingType", FlatBuildingType.OTHER);
												break;
											case "деревянный":
												realty.data.put("flatBuildingType", FlatBuildingType.WOODEN);
												break;
											case "железобетон":
												realty.data.put("flatBuildingType", FlatBuildingType.OTHER);
												break;
											case "кирпично-монолитный":
												realty.data.put("flatBuildingType", FlatBuildingType.OTHER);
												break;
											case "кирпичный":
												realty.data.put("flatBuildingType", FlatBuildingType.BRICK);
												break;
											case "монолит":
												realty.data.put("flatBuildingType", FlatBuildingType.MONOLITHIC);
												break;
											case "панельный":
												realty.data.put("flatBuildingType", FlatBuildingType.PANEL);
												break;
											case "саманный":
												realty.data.put("flatBuildingType", FlatBuildingType.OTHER);
												break;
										}
										break;
										
									//Высота потолков
									case "house-ceiling-height":
										String ceilingHeight = (String)keys.getValue();
										ceilingHeight = ceilingHeight.replace(",", "");
										realty.data.put("ceilingHeight", Float.parseFloat(ceilingHeight));
										break;
										
									//Этаж
									case "etage":
										String etage = (String)keys.getValue();
										realty.data.put("flatFloor", Long.parseLong(etage));
										break;
																	
								    //Всего этажей
									case "etage-all":
										String etageAll = (String)keys.getValue();
										realty.data.put("houseFloorCount", Long.parseLong(etageAll));
										break;
										
									//Год постройки
									case "house-year":
										String houseYear = (String)keys.getValue();
										realty.data.put("houseYear", Long.parseLong(houseYear));
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
											realty.data.put("phoneType", FlatPhoneType.SEPARETED);
										else
											realty.data.put("phoneType", FlatPhoneType.NO_PHONE);
										break;
										
									//Интернет
									case "internet":
										Boolean isInternet = (Boolean)keys.getValue();
										//TODO придумать как поступить с инетом
										break;
										
									//Балкон
									case "balcony":
										Boolean isBalcony = (Boolean)keys.getValue();
										//TODO придумать как поступить с балконом
									//	if (isBalcony)
									//		realty.data.balconyType = FlatBalconyType.BALCONY;
										break;
										
									//Период аренды
									case "rent_period":
										String rentPeriod = (String)keys.getValue();
										switch (rentPeriod) {
											case "Долгосрочная":
												realty.data.put("rentPeriod", FlatRentPeriodType.MONTHLY);
												break;
											case "час":
												realty.data.put("rentPeriod", FlatRentPeriodType.HOURLY);
												break;
											case "месяц":
												realty.data.put("rentPeriod", FlatRentPeriodType.MONTHLY);
											    break;
											case "сутки":
												realty.data.put("rentPeriod", FlatRentPeriodType.DAILY);
											    break;
											case "выходные":
												//TODO добавиь выходные
												realty.data.put("rentPeriod", FlatRentPeriodType.DAILY);
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
