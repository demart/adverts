package kz.aphion.adverts.crawler.krisha.mappers.flat;

import java.util.ArrayList;
import java.util.Map;
import java.util.Map.Entry;

import kz.aphion.adverts.crawler.krisha.mappers.AbstractAdvertMapper;
import kz.aphion.adverts.crawler.krisha.mappers.CommonMapperUtils;
import kz.aphion.adverts.persistence.realty.RealtyPublisherCompany;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс конвертирует объявления о продаже или арнеде квартир
 * 
 * @author artem.demidovich
 *
 */
public class FlatSellDataMapper extends AbstractAdvertMapper<FlatSellRealty> {

	private static Logger logger = LoggerFactory.getLogger(FlatRentDataMapper.class);
	
	public FlatSellDataMapper(FlatSellRealty realty) {
		super(realty);
	}
	
	@Override
	public void mapAdvertData(Map<String, Object> advert) {
		
		realty.data = new FlatSellData();
		
		// Commented because of changing structure of db
		//realty.data.residentalComplex = new ResidentialComplexLink();
		
		for (Entry<String,Object> dataItem : advert.entrySet()) {
			switch (dataItem.getKey()) {
			case "add_date":
				// NEEDED!
				
				// Так как объявления которые поднимаются в топе изменяют значение
				// на максимальное время в течении этого дня, то опираться на эту дату бесмысленно
				// Поэтому мы попробуем описаться на логику
				// show_till показывает когда объявления будет скрыто в архив и это ровно через 7 дней
				// таким образом можно вычислять время публикации объявления
				
				//String publishTime = (String)dataItem.getValue();
				//Calendar publishedTime = javax.xml.bind.DatatypeConverter.parseDateTime(publishTime);
				//realty.publishedAt = publishedTime;
				break;
			case "email":
				// Пока не используем
				break;
			case "live.rooms":
				//realty.data.rooms = Long.parseLong((String)dataItem.getValue());
				// Плохое место, нужно думать как избежать 1,5 значений
				String rooms = (String)dataItem.getValue();
				rooms = rooms.replace(",", ".");
				realty.data.rooms = Float.parseFloat(rooms);
				break;
			case "price":
				// Не лучшее место чтобы вытаскивать цену, лучше брать из system_data.price-2
				//realty.data.price = Long.parseLong((String)dataItem.getValue());
				break;
			case "price.currency":
				//realty.data.priceCurrency = CommonMapperUtils.getCurrencyType(dataItem);
				break;
			case "mortgage":
				realty.data.mortgageStatus = FlatDataMapperUtils.getFlatMortgageStatus(dataItem);
				break;
			case "flat.building":
				realty.data.flatBuildingType = FlatDataMapperUtils.getFlatBuildingType(dataItem);
				break;
			case "house.year": // Год постройки (сдачи в <nobr>эксплуатацию) *
				//if (dataItem.getValue() instanceof Long) {
				//	realty.data.houseYear = (Long)dataItem.getValue();
				//} else {
					String houseYear = (String)dataItem.getValue();
					houseYear = houseYear.replaceAll("[^\\d]", "");
					realty.data.houseYear = Long.parseLong(houseYear);
				//}
				break;
			case "map.house_num": // Номер дома
				//if (dataItem.getValue() instanceof Long) {
				//	realty.location.houseNumber = ((Long)dataItem.getValue()).toString();
				//} else {
					String houseNumber = (String)dataItem.getValue();
					realty.location.houseNumber = houseNumber;
				//}
				break;
			case "flat.floor": // Этаж
				//if (dataItem.getValue() instanceof Long) {
				//	realty.data.flatFloor = (Long)dataItem.getValue();
				//} else {
					String flatFloor = (String)dataItem.getValue();
					realty.data.flatFloor = Long.parseLong(flatFloor);
				//}
				break;
			case "house.floor_num": // Всего этажей
				//if (dataItem.getValue() instanceof Long) {
				//	realty.data.houseFloorCount = (Long)dataItem.getValue();
				//} else {
					String flatFloorNum = (String)dataItem.getValue();
					realty.data.houseFloorCount = Long.parseLong(flatFloorNum);
				//}
				break;
			case "live.square": // Площадь, м
				//if (dataItem.getValue() instanceof Long) {
				//	realty.data.square = Float.parseFloat(dataItem.getValue().toString());
				//} else {
				//	if (dataItem.getValue() instanceof Float) {
				//		realty.data.square = (Float)dataItem.getValue();
				//	} else {
						String liveSquare = (String)dataItem.getValue();
						realty.data.square = Float.parseFloat(liveSquare);
				//	}
				//}
				break;
			case "live.square_l":
				String liveSquareLiving = (String)dataItem.getValue();
				realty.data.squareLiving = Float.parseFloat(liveSquareLiving);
				break;
			case "live.square_k":
				String liveSquareKitchen = (String)dataItem.getValue();
				realty.data.squareKitchen = Float.parseFloat(liveSquareKitchen);
				break;
			case "live.furniture":
				realty.data.furnitureType = FlatDataMapperUtils.getFlatFurnitureType(dataItem);
				break;				
			case "flat.priv_dorm": // В прив. общежитии
				realty.data.privatizedDormType = FlatDataMapperUtils.getFlatPrivatizedDormType(dataItem);
				break;
			case "flat.flooring": // Пол
				realty.data.floorType = FlatDataMapperUtils.getFlatFlooringType(dataItem);
				break;
			case "flat.parking": // Паркинг
				realty.data.parkingType = FlatDataMapperUtils.getParkingType(dataItem);
			case "flat.renovation": // Состояние
				realty.data.renovationType = FlatDataMapperUtils.getFlatRenovationType(dataItem);
				break;
			case "flat.phone":
				realty.data.phoneType = FlatDataMapperUtils.getFlatPhoneType(dataItem);
				break;
			case "inet.type": // Интернет
				realty.data.internetType = FlatDataMapperUtils.getFlatInternetType(dataItem);
				break;
			case "flat.toilet": // Санузел
				realty.data.lavatoryType = FlatDataMapperUtils.getFlatLavatoryType(dataItem);
				break;
			case "flat.balcony": // Балкон
				realty.data.balconyType = FlatDataMapperUtils.getFlatBalconyType(dataItem);
				break;
			case "flat.balcony_g": // Балкон остеклен
				realty.data.balconyGlazingType = FlatDataMapperUtils.getFlatBalconyGlazingType(dataItem);
				break;
			case "flat.door": // Тип двери
				realty.data.doorType = FlatDataMapperUtils.getFlatDoorTypes(dataItem);
				break;
			case "flat.security": // Безопасность
				realty.data.securityTypes = FlatDataMapperUtils.getFlatSecurityTypes(dataItem);
				break;
			case "flat.options":
				realty.data.miscellaneous = FlatDataMapperUtils.getFlatMiscellaneousTypes(dataItem);
				break;
			case "map.geo_id": // ID региона
				// TODO Нужно конвертировать во внутренний регион
				//if (dataItem.getValue() instanceof Long) {
				//	realty.location.externalRegionId = ((Long)dataItem.getValue()).toString();
				//} else {
					String mapGeoId = dataItem.getValue().toString();
					realty.location.externalRegionId = mapGeoId;
				//}
				break;
			case "house.complex_name": // Название жилого комплекса
				String houseComplexName = (String)dataItem.getValue();
				// TODO SOLVE HOW TO STORE COMPLEX NAME NOW
				//realty.data.residentalComplex.externalComplexName = houseComplexName;
				break;
			case "map.complex":
				String houseComplexId = (String)dataItem.getValue();
				//realty.data.residentalComplex.externalComplexId = houseComplexId;
				FlatDataMapperUtils.mapResidentialComplex(advert, realty.data, houseComplexId);
				break;
			case "map.street": // Улица
				String mapStreet = (String)dataItem.getValue();
				realty.location.streetName = mapStreet;
				break;
			case "map.corner_street": // Пересечение с улицей
				String mapCornerStreet = (String)dataItem.getValue();
				realty.location.cornerStreetName = mapCornerStreet;
				break;
			case "who": // Контактная информация
				realty.publisher.publisherType = CommonMapperUtils.getPublisherType(dataItem);
				break;
			case "who.sub": // Хрен его знает
				// ???
				break;
			case "has_comment": // Кто может комментировать объявление?
				String hasComment = (String)dataItem.getValue();
				switch (hasComment) {
					case "0": // Никто
						// Не используется
						break;
					case "1": // Все
						// Не используется					
						break;
					case "2": // Только зарегистрированные пользователи
						// Не используется					
						break;
					default:				
						break;
					}
				break;
			case "has_change": // Обмен
				String hasChange = CommonMapperUtils.getEntryStringValue(dataItem);
				switch (hasChange) {
				case "0": // Запретить
					// Не используется
					break;
				case "1": // Разрешить
					// Не используется
					break;
				case "false": // Разрешить
					// Не используется
					break;
				case "true": // Разрешить
					// Не используется
					break;
				default:
					logger.error("ATTENTION! Found new [has_change] value: " + hasChange);
					break;
				}
				break;
			case "price_is_actual":
				// Подумать
				break;
			case "advert_rank":
				// Подумать				
				break;
			case "advert_rank_day":
				// Подумать				
				break;			
			case "map.geo_text":
				// Подумать
				break;
			case "map.country":
				// Подумать				
				break;
			case "map.city":
				// Подумать				
				break;
			case "map.region":
				// Подумать
				break;
			case "map.district":
				// Подумать				
				break;
			case "map.microdistrict":
				// Подумать
				break;
			case "map.lat":
				String mapLatitude = (String)dataItem.getValue();
				realty.location.mapLatitude = mapLatitude;
				break;
			case "map.lon":
				String mapLongitude = (String)dataItem.getValue();
				realty.location.mapLongitude = mapLongitude;
				break;
			case "map.zoom":
				if (dataItem.getValue() instanceof Long) {
					realty.location.mapZoom = dataItem.getValue().toString();
				} else {
					String mapZoom = dataItem.getValue().toString();
					realty.location.mapZoom = mapZoom;
				}
				break;
			case "map.type":
				realty.location.mapType = CommonMapperUtils.getMapType(dataItem);
				break;
			case "map.name": // ???
				realty.location.mapName = CommonMapperUtils.getMapName(dataItem);
				break;
			case "map.block.16": // ???
				break;
			case "map.block.14": // ???
				break;
			case "map.block.12": // ???
				break;
			case "map.block.10": // ???
				break;
			case "map.block.8": // ???
				break;
			case "map.block.6": // ???
				break;
			case "map.block.4": // ???
				break;
			case "_price.srch": // ???
				break;
			case "_price.m2_srch": // ???
				break;
				
			case "phone":
				String rawPhones = (String)dataItem.getValue();
				String[] phones = rawPhones.split(",");
				realty.publisher.phones = new ArrayList<String>();
				for (String phone : phones) {
					realty.publisher.phones.add(phone);
				}
				break;
			case "text":
				String text = (String)dataItem.getValue();
				realty.data.text = text;
				break;
			case "ceiling": // Высота потолков
				//if (dataItem.getValue() instanceof Long) {
				//	realty.data.ceilingHeight = Float.parseFloat(dataItem.getValue().toString());
				//} else {
				//	if (dataItem.getValue() instanceof Long) {
				//		realty.data.ceilingHeight = (Float)dataItem.getValue();
				//	} else {
						String ceiling = (String)dataItem.getValue();
						realty.data.ceilingHeight = Float.parseFloat(ceiling);
				//	}
				//}
				break;
				
			case "company.id": // Идентификатор компании
				String companyId = (String)dataItem.getValue();
				realty.publisher.company = new RealtyPublisherCompany();
				realty.publisher.company.externalId = companyId;
				break;
			case"user_id": // ID пользователя внешйней системы
				//Long userId = (Long)dataItem.getValue();
				//realty.publisher.externalUserId = String.valueOf(userId);
				realty.publisher.externalUserId = (String)dataItem.getValue();
				break;
				
			case "advert.type":
				// simple
				break;
			case "send_np":
				// ??
				break;
			case "send_to_market": // Опубликовать в маркете
				// TODO??
				break;
			default:
				logger.error("ATTENTION! Found new data key: " + dataItem.getKey() + " with value: " + dataItem.getValue());
				break;
			}
		}
	}

}