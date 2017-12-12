package kz.aphion.adverts.subscription.searcher.impl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.models.realty.data.MortgageStatus;
import kz.aphion.adverts.models.realty.data.flat.types.FlatBalconyGlazingType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatBalconyType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatDoorType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatFloorType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatFurnitureType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatInternetType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatMiscellaneousType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatParkingType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatPrivatizedDormType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRenovationType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatSecurityType;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.adverts.AdvertOperationType;
import kz.aphion.adverts.persistence.adverts.AdvertPublisherType;
import kz.aphion.adverts.persistence.adverts.AdvertType;
import kz.aphion.adverts.persistence.realty.RealtyType;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.DBObject;

public class FlatSubscriptionSearcherQueryBuilder {

	private static Logger logger = LoggerFactory.getLogger(FlatSubscriptionSearcherQueryBuilder.class);
	
	public static Query<Subscription> buidSellQuery(Datastore ds, Advert realty) {
		logger.debug("Builing Query for FlatSellRealty subscriptions");
		Query<Subscription> q = buildBaseCriteria(ds, AdvertOperationType.SELL);

		// Формируем общие параметры для поиск
		q = buildCommonQuery(q, realty);
		// В залоге или нет
		q = buildMortgagesStatusQuery(q, realty.data);
		
		logger.debug("Query for FlatSellRealty subscriptions was built");
		return q;
	}
	
	public static Query<Subscription> buidRentQuery(Datastore ds, Advert realty) {
		logger.debug("Builing Query for FlatRentRealty subscriptions");
		Query<Subscription> q = buildBaseCriteria(ds, AdvertOperationType.RENT);
		
		// Формируем общие параметры для поиск
		q = buildCommonQuery(q, realty);
		// Срок аренды
		// Обязательно должен быть указан в подписках!
		q = buildRentPeriodQuery(q, realty.data);
		// Дополнительные критерии для аренды квартиры или комнаты
		q = buildFlatRentMiscellaneousTypesQuery(q, realty.data);
		
		logger.debug("Query for FlatRentRealty subscriptions was built");
		return q;
	}
	
	
	private static Query<Subscription> buildBaseCriteria(Datastore ds, AdvertOperationType type) {
		Query<Subscription> q = ds.createQuery(Subscription.class);
		
		// Отключаем валидацию, так как мы используем Generic типы которые невозможно проверить
		q = q.disableValidation();
		
		q.and(
			q.criteria("advertType").equal(AdvertType.REALTY),
			q.criteria("advertSubType").equal(RealtyType.FLAT),
			q.criteria("operationType").equal(type),
			q.criteria("startedAt").lessThan(Calendar.getInstance().getTime()),
			q.criteria("expiresAt").greaterThan(Calendar.getInstance().getTime()),
			q.criteria("status").equal(SubscriptionStatus.ACTIVE)
		);
		
		return q;
	}
	
	
	/**
	 * Строит критерии для поиска подписок по общим полям не зависимо от аренды или продажи недвижимости
	 * @param q запрос
	 * @param realty общий объект недвижимости
	 * @param data общий объект данных о недвижимости
	 * @return
	 */
	private static Query<Subscription> buildCommonQuery(Query<Subscription> q, Advert realty) {
		// Цена объекта
		q = buildPriceQuery(q, realty);
		// Фотография объекта
		q = buildHasPhotoQuery(q, realty);
		// Площадь объекта
		q = buildSquareQuery(q, realty.data);
		// Жилая площадь
		q = buildSquareLivingQuery(q, realty.data);
		// Площадь кухни
		q = buildSquareKitckenQuery(q, realty.data);
		// Кто опубликовал объявления
		q = buildPublisherTypeQuery(q, realty);
		// В каком регионе (районе) расположен объект
		q = buildLocationRegionsQuery(q, realty);
		// Какой жилой комплекс		
		q = buildResidentialComplexQuery(q, realty.data);
		// Кол-во комнат
		q = buildRoomQuery(q, realty.data);
		// Год постройки дома
		q = buildBuildingYear(q, realty.data);
		// Этаж квартиры
		q = buildFlatFloorQuery(q, realty.data);
		// Этажность дома
		q = buildHouseFloorCountQuery(q, realty.data);
		// Высота потолков
		q = buildCeilingHieght(q, realty.data);
		// Тип здания
		q = buildFlatBuildingTypeQuery(q, realty.data);
		// Приватизиованное общежитие
		q = buildPrivatizedDormTypeQuery(q, realty.data);
		// Ремонта
		q = buildRenovationTypeQuery(q, realty.data);
		// Телефон
		q = buildPhoneType(q, realty.data);
		// Интернет
		q = buildInternetTypeQuery(q, realty.data);
		// Сан. узел
		q = buildLavatoryType(q, realty.data);
		// Балкон
		q = buildBalconyType(q, realty.data);
		// Остекление балкона
		q = buildBalconyGlazingType(q, realty.data);
		// Дверь
		q = buildDoorTypeQuery(q, realty.data);
		// Парковка
		q = buildParkingTypeQuery(q, realty.data);
		// Мебель
		q = buildFurnitureTypeQuery(q, realty.data);
		// Пол
		q = buildFloorTypeQuery(q, realty.data);
		// Безопасность
		q = buildSecurityTypesQuery(q, realty.data);
		// Дополнительно
		q = buildFlatMiscellaneousTypesQuery(q, realty.data);
		
		// TODO Добавить грамонтную работу по локации или регионы или крарты или все вместе с пересечением
		//q.field("criteria.keywordsType")
		//q.field("criteria.keywords")
		//q.field("criteria.squareType").equal(arg0)
		
		return q;
	}
	
	
	private static Query<Subscription> buildMortgagesStatusQuery(Query<Subscription> q, HashMap<String, Object> data) {
		MortgageStatus mortgageStatus = data.get("mortgageStatus") != null ? MortgageStatus.valueOf((String)data.get("mortgageStatus")) : null;
		logger.debug("Realty.data.mortgageStatus: {}", mortgageStatus);
		if (mortgageStatus == null || mortgageStatus == MortgageStatus.UNDEFINED) {
			logger.debug("Realty.data.mortgageStatus search without mortgageStatus");
			q.and(
				q.criteria("criteria.data.mortgageStatuses").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.mortgageStatus search with mortgageStatus");
			q.and(
				q.or(
					q.criteria("criteria.data.mortgageStatuses").doesNotExist(),
					q.criteria("criteria.data.mortgageStatuses").hasAnyOf(Arrays.asList(mortgageStatus.toString()))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatRentMiscellaneousTypesQuery(Query<Subscription> q, HashMap<String, Object> data){
		List<String> rentMiscellaneous = (List<String>) data.get("rentMiscellaneous");
		logger.debug("Realty.data.rentMiscellaneous size: {}", rentMiscellaneous != null ? rentMiscellaneous.size() : 0);
		if (rentMiscellaneous == null) {
			logger.debug("Realty.data.rentMiscellaneous search without rentMiscellaneous");
			q.and(
				q.criteria("criteria.data.rentMiscellaneous").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.rentMiscellaneous search with rentMiscellaneous");
			// Так как много элементов может быть у квартиры и так же много у подписки
			// нужно выбрать оптимальный и простой способ сравнения
			// Например можно использовать или, так как человек сокрее всего хочет просто
			// понимать есть ли домофон или видо домофон
			
			q.and(
				q.or(
					q.criteria("criteria.data.rentMiscellaneous").doesNotExist(),
					q.criteria("criteria.data.rentMiscellaneous").hasAnyOf(rentMiscellaneous)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildRentPeriodQuery(Query<Subscription> q, HashMap<String, Object> data) {
		FlatRentPeriodType rentPeriod = data.get("rentPeriod") != null ? FlatRentPeriodType.valueOf((String)data.get("rentPeriod")) : null;
		logger.debug("Realty.data.rentPeriod: {}", rentPeriod);
		if (rentPeriod == null || rentPeriod == FlatRentPeriodType.UNDEFINED) {
			logger.warn("Realty.data.rentPeriod search without rentPeriod. It should be mandatory field for FlatRentRealty subscription!");
			q.and(
				q.criteria("criteria.data.rentPeriods").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.rentPeriod search with rentPeriod");
			q.and(
				q.or(
					q.criteria("criteria.data.rentPeriods").doesNotExist(),
					q.criteria("criteria.data.rentPeriods").hasAnyOf(Arrays.asList(rentPeriod.toString()))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatMiscellaneousTypesQuery(Query<Subscription> q, HashMap<String, Object> data){
		List<String> miscellaneous = (List<String>) data.get("miscellaneous");
		logger.debug("Realty.data.miscellaneous size: {}", miscellaneous != null ? miscellaneous.size() : 0);
		if (miscellaneous == null || (miscellaneous.size() == 1 && miscellaneous.contains(FlatMiscellaneousType.UNDEFINED))) {
			logger.debug("Realty.data.miscellaneous search without miscellaneous");
			q.and(
				q.criteria("criteria.data.miscellaneous").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.miscellaneous search with miscellaneous");
			// Так как много элементов может быть у квартиры и так же много у подписки
			// нужно выбрать оптимальный и простой способ сравнения
			// Например можно использовать или, так как человек сокрее всего хочет просто
			// понимать есть ли домофон или видо домофон

			q.and(
				q.or(
					q.criteria("criteria.data.miscellaneous").doesNotExist(),
					q.criteria("criteria.data.miscellaneous").hasAnyOf(miscellaneous)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildSecurityTypesQuery(Query<Subscription> q, HashMap<String, Object> data) {
		List<String> securityTypes = (List<String>) data.get("securityTypes");
		logger.debug("Realty.data.securityTypes size: {}", securityTypes != null ? securityTypes.size() : 0);
		if (securityTypes == null || (securityTypes.size() == 1 && securityTypes.contains(FlatSecurityType.UNDEFINED))) {
			logger.debug("Realty.data.securityTypes search without securityTypes");
			q.and(
				q.criteria("criteria.data.securityTypes").doesNotExist()	
			);
		} else {
			logger.debug("Realty.data.securityTypes search with securityTypes");
			// Так как много элементов может быть у квартиры и так же много у подписки
			// нужно выбрать оптимальный и простой способ сравнения
			// Например можно использовать или, так как человек сокрее всего хочет просто
			// понимать есть ли домофон или видо домофон

			q.and(
				q.or(
					q.criteria("criteria.data.securityTypes").doesNotExist(),
					q.criteria("criteria.data.securityTypes").hasAnyOf(securityTypes)
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFloorTypeQuery(Query<Subscription> q, HashMap<String, Object> data) {
		FlatFloorType floorType = data.get("floorType") != null ? FlatFloorType.valueOf((String)data.get("floorType")) : null;
		logger.debug("Realty.data.floorType: {}", floorType);
		if (floorType == null || floorType == FlatFloorType.UNDEFINED) {
			logger.debug("Realty.data.floorType search without floorType");
			q.and(
				q.criteria("criteria.data.floorTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.floorType search with floorType");
			q.and(
				q.or(
					q.criteria("criteria.data.floorTypes").doesNotExist(),
					q.criteria("criteria.data.floorTypes").hasAnyOf(Arrays.asList(floorType))				
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFurnitureTypeQuery(Query<Subscription> q, HashMap<String, Object> data) {
		FlatFurnitureType furnitureType = data.get("furnitureType") != null ? FlatFurnitureType.valueOf((String)data.get("furnitureType")) : null;
		logger.debug("Realty.data.furnitureType: {}", furnitureType);
		if (furnitureType == null || furnitureType == FlatFurnitureType.UNDEFINED) {
			logger.debug("Realty.data.furnitureType search without furnitureType");
			q.and(
				q.criteria("criteria.data.furnitureTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.furnitureType search with furnitureType");
			q.and(
				q.or(
					q.criteria("criteria.data.furnitureTypes").doesNotExist(),
					q.criteria("criteria.data.furnitureTypes").hasAnyOf(Arrays.asList(furnitureType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildParkingTypeQuery(Query<Subscription> q, HashMap<String, Object> data){
		FlatParkingType parkingType = data.get("parkingType") != null ? FlatParkingType.valueOf((String)data.get("parkingType")) : null;
		logger.debug("Realty.data.parkingType: {}", parkingType);
		if (parkingType == null || parkingType == FlatParkingType.UNDEFINED) {
			logger.debug("Realty.data.parkingType search without parkingType");
			q.and(
				q.criteria("criteria.data.parkingTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.parkingType search with parkingType");
			q.and(
				q.or(
					q.criteria("criteria.data.parkingTypes").doesNotExist(),
					q.criteria("criteria.data.parkingTypes").hasAnyOf(Arrays.asList(parkingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildDoorTypeQuery(Query<Subscription> q, HashMap<String, Object> data){
		FlatDoorType doorType = data.get("doorType") != null ? FlatDoorType.valueOf((String)data.get("doorType")) : null;
		logger.debug("Realty.data.doorType: {}", doorType);
		if (doorType == null || doorType == FlatDoorType.UNDEFINED) {
			logger.debug("Realty.data.doorType search without doorType");
			q.and(
				q.criteria("criteria.doorTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.doorType search with doorType");
			q.and(
				q.or(
					q.criteria("criteria.doorTypes").doesNotExist(),
					q.criteria("criteria.doorTypes").hasAnyOf(Arrays.asList(doorType))	
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBalconyGlazingType(Query<Subscription> q, HashMap<String, Object> data){
		FlatBalconyGlazingType balconyGlazingType = data.get("balconyGlazingType") != null ? FlatBalconyGlazingType.valueOf((String)data.get("balconyGlazingType")) : null;
		logger.debug("Realty.data.balconyGlazingType: {}", balconyGlazingType);
		if (balconyGlazingType == null || balconyGlazingType == FlatBalconyGlazingType.UNDEFINED) {
			logger.debug("Realty.data.balconyGlazingType search without balconyGlazingType");
			q.and(
				q.criteria("criteria.data.balconyGlazingTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.balconyGlazingType search with balconyGlazingType");
			q.and(
				q.or(
					q.criteria("criteria.data.balconyGlazingTypes").doesNotExist(),
					q.criteria("criteria.data.balconyGlazingTypes").hasAnyOf(Arrays.asList(balconyGlazingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBalconyType(Query<Subscription> q, HashMap<String, Object> data){
		FlatBalconyType balconyType = data.get("balconyType") != null ? FlatBalconyType.valueOf((String)data.get("balconyType")) : null;
		logger.debug("Realty.data.balconyType: {}", balconyType);
		if (balconyType == null || balconyType == FlatBalconyType.UNDEFINED) {
			logger.debug("Realty.data.balconyType search without balconyType");
			q.and(
					q.criteria("criteria.data.balconyTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.balconyType search with balconyType");
			q.and(
				q.or(
					q.criteria("criteria.data.balconyTypes").doesNotExist(),
					q.criteria("criteria.data.balconyTypes").hasAnyOf(Arrays.asList(balconyType))
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildLavatoryType(Query<Subscription> q, HashMap<String, Object> data) {
		FlatLavatoryType lavatoryType = data.get("lavatoryType") != null ? FlatLavatoryType.valueOf((String)data.get("lavatoryType")) : null;
		logger.debug("Realty.data.lavatoryType: {}", lavatoryType);
		if (lavatoryType == null || lavatoryType == FlatLavatoryType.UNDEFINED) {
			logger.debug("Realty.data.lavatoryType search without lavatoryType");
			q.and(
				q.criteria("criteria.data.lavatoryTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.lavatoryType search with lavatoryType");
			q.and(
				q.or(
					q.criteria("criteria.data.lavatoryTypes").doesNotExist(),
					q.criteria("criteria.data.lavatoryTypes").hasAnyOf(Arrays.asList(lavatoryType))		
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildInternetTypeQuery(Query<Subscription> q, HashMap<String, Object> data) {
		FlatInternetType internetType = data.get("internetType") != null ? FlatInternetType.valueOf((String)data.get("internetType")) : null;
		logger.debug("Realty.data.internetType: {}", internetType);
		if (internetType == null || internetType == FlatInternetType.UNDEFINED) {
			logger.debug("Realty.data.internetType search without internetType");
			q.and(
				q.criteria("criteria.data.internetTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.internetType search with internetType");
			q.and(
				q.or(
					q.criteria("criteria.data.internetTypes").doesNotExist(),
					q.criteria("criteria.data.internetTypes").hasAnyOf(Arrays.asList(internetType))					
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildPhoneType(Query<Subscription> q, HashMap<String, Object> data){
		FlatPhoneType phoneType = data.get("phoneType") != null ? FlatPhoneType.valueOf((String)data.get("phoneType")) : null;
		logger.debug("Realty.data.phoneType: {}", phoneType);
		if (phoneType == null || phoneType == FlatPhoneType.UNDEFINED) {
			logger.debug("Realty.data.phoneType search without phoneType");
			q.and(
				q.criteria("criteria.data.phoneTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.phoneType search with phoneType");
			q.and(
				q.or(
					q.criteria("criteria.data.phoneTypes").doesNotExist(),
					q.criteria("criteria.data.phoneTypes").hasAnyOf(Arrays.asList(phoneType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildRenovationTypeQuery(Query<Subscription> q, HashMap<String, Object> data) {
		FlatRenovationType renovationType = data.get("renovationType") != null ? FlatRenovationType.valueOf((String)data.get("renovationType")) : null;
		logger.debug("Realty.data.renovationType: {}", renovationType);
		if (renovationType == null || renovationType == FlatRenovationType.UNDEFINED) {
			logger.debug("Realty.data.renovationType search without renovationType");
			q.and(
				q.criteria("criteria.data.renovationTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.renovationType search with renovationType");
			q.and(
				q.or(
					q.criteria("criteria.data.renovationTypes").doesNotExist(),
					q.criteria("criteria.data.renovationTypes").hasAnyOf(Arrays.asList(renovationType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildPrivatizedDormTypeQuery(Query<Subscription> q, HashMap<String, Object> data){
		FlatPrivatizedDormType privatizedDormType = data.get("privatizedDormType") != null ? FlatPrivatizedDormType.valueOf((String)data.get("privatizedDormType")) : null;
		logger.debug("Realty.data.privatizedDormType: {}", privatizedDormType);
		if (privatizedDormType == null || privatizedDormType == FlatPrivatizedDormType.UNDEFINED) {
			logger.debug("Realty.data.privatizedDormType search without privatizedDormType");
			q.and(
				q.criteria("criteria.data.privatizedDormTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.privatizedDormType search with privatizedDormType");
			q.and(
				q.or(
					q.criteria("criteria.data.privatizedDormTypes").doesNotExist(),
					q.criteria("criteria.data.privatizedDormTypes").hasAnyOf(Arrays.asList(privatizedDormType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatBuildingTypeQuery(Query<Subscription> q, HashMap<String, Object> data){
		FlatBuildingType flatBuildingType = data.get("flatBuildingType") != null ? FlatBuildingType.valueOf((String)data.get("flatBuildingType")) : null;
		logger.debug("Realty.data.flatBuildingType: {}", flatBuildingType);
		if (flatBuildingType != null && flatBuildingType != FlatBuildingType.UNDEFINED) {
			logger.debug("Realty.data.flatBuildingType search without flatBuildingType");
			q.and(
				q.criteria("criteria.data.flatBuildingTypes").doesNotExist()
			);
			
		} else {
			logger.debug("Realty.data.flatBuildingType search with flatBuildingType");
			q.and(
				q.or(
					q.criteria("criteria.data.flatBuildingTypes").doesNotExist(),
					q.criteria("criteria.data.flatBuildingTypes").hasAnyOf(Arrays.asList(flatBuildingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildCeilingHieght(Query<Subscription> q, HashMap<String, Object> data) {
		Float ceilingHeight = data.get("ceilingHeight") != null ? Float.valueOf((String)data.get("ceilingHeight")) : null;
		logger.debug("Realty.data.ceilingHeight: {}", ceilingHeight);
		if (ceilingHeight != null && ceilingHeight > 0) {
			logger.debug("Realty.data.ceilingHeight search with ceilingHeight");
			q.and(
				q.or(
					q.criteria("criteria.data.ceilingHeightFrom").doesNotExist(),
					q.criteria("criteria.data.ceilingHeightFrom").lessThanOrEq(ceilingHeight)
				),
				q.or(
					q.criteria("criteria.data.ceilingHeightTo").doesNotExist(),
					q.criteria("criteria.data.ceilingHeightTo").greaterThanOrEq(ceilingHeight)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.ceilingHeight search without ceilingHeight");
			q.and(
				q.criteria("criteria.data.ceilingHeightFrom").doesNotExist(),
				q.criteria("criteria.data.ceilingHeightTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildSquareKitckenQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Float squareKitchen = data.get("squareKitchen") != null ? Float.valueOf((String)data.get("squareKitchen")) : null;
		logger.debug("Realty.data.squareKitchen: {}", squareKitchen);
		if (squareKitchen != null && squareKitchen > 0) {
			logger.debug("Realty.data.squareKitchen search with squareKitchen");
			q.and(
				q.or(
					q.criteria("criteria.data.squareKitchenFrom").doesNotExist(),
					q.criteria("criteria.data.squareKitchenFrom").lessThanOrEq(squareKitchen)
				),
				q.or(
					q.criteria("criteria.data.squareKitchenTo").doesNotExist(),
					q.criteria("criteria.data.squareKitchenTo").greaterThanOrEq(squareKitchen)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.squareKitchen search without squareKitchen");
			q.and(
				q.criteria("criteria.data.squareKitchenFrom").doesNotExist(),
				q.criteria("criteria.data.squareKitchenTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildSquareLivingQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Float squareLiving = data.get("squareLiving") != null ? Float.valueOf((String)data.get("squareLiving")) : null;
		logger.debug("Realty.data.squareLiving: {}", squareLiving);
		if (squareLiving != null && squareLiving > 0) {
			logger.debug("Realty.data.squareLiving search with squareLiving");
			q.and(
				q.or(
					q.criteria("criteria.data.squareLivingFrom").doesNotExist(),
					q.criteria("criteria.data.squareLivingFrom").lessThanOrEq(squareLiving)
				),
				q.or(
					q.criteria("criteria.data.squareLivingTo").doesNotExist(),
					q.criteria("criteria.data.squareLivingTo").greaterThanOrEq(squareLiving)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.squareLiving search without squareLiving");
			q.and(
				q.criteria("criteria.data.squareLivingFrom").doesNotExist(),
				q.criteria("criteria.data.squareLivingTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildHouseFloorCountQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Long houseFloorCount = data.get("houseFloorCount") != null ? Long.valueOf((String)data.get("houseFloorCount")) : null;
		logger.debug("Realty.data.houseFloorCount: {}", houseFloorCount);
		if (houseFloorCount != null && houseFloorCount > 0) {
			logger.debug("Realty.data.houseFloorCount search with houseFloorCount");
			q.and(
				q.or(
					q.criteria("criteria.data.houseFloorCountFrom").doesNotExist(),
					q.criteria("criteria.data.houseFloorCountFrom").lessThanOrEq(houseFloorCount)
				),
				q.or(
					q.criteria("criteria.data.houseFloorCountTo").doesNotExist(),
					q.criteria("criteria.data.houseFloorCountTo").greaterThanOrEq(houseFloorCount)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.houseFloorCount search without houseFloorCount");
			q.and(
				q.criteria("criteria.data.houseFloorCountFrom").doesNotExist(),
				q.criteria("criteria.data.houseFloorCountTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatFloorQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Long flatFloor = data.get("flatFloor") != null ? Long.valueOf((String)data.get("flatFloor")) : null;
		logger.debug("Realty.data.flatFloor: {}", flatFloor);
		if (flatFloor != null && flatFloor > 0) {
			logger.debug("Realty.data.flatFloor search with flatFloor");
			q.and(
				q.or(
					q.criteria("criteria.data.flatFloorFrom").doesNotExist(),
					q.criteria("criteria.data.flatFloorFrom").lessThanOrEq(flatFloor)
					),
				q.or(
					q.criteria("criteria.data.flatFloorTo").doesNotExist(),
					q.criteria("criteria.data.flatFloorTo").greaterThanOrEq(flatFloor)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.flatFloor search without flatFloor");
			q.and(
				q.criteria("criteria.data.flatFloorFrom").doesNotExist(),
				q.criteria("criteria.data.flatFloorTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBuildingYear(Query<Subscription> q, HashMap<String, Object> data) {
		Long houseYear = data.get("houseYear") != null ? Long.valueOf((String)data.get("houseYear")) : null;
		logger.debug("Realty.data.houseYear: {}", houseYear);
		if (houseYear != null && houseYear > 0) {
			logger.debug("Realty.data.houseYear search with year");
			q.and(
				q.or(
					q.criteria("criteria.data.houseYearFrom").doesNotExist(),
					q.criteria("criteria.data.houseYearFrom").lessThanOrEq(houseYear)
				),
				q.or(
					q.criteria("criteria.data.houseYearTo").doesNotExist(),
					q.criteria("criteria.data.houseYearTo").greaterThanOrEq(houseYear)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.houseYear search without year");
			q.and(
				q.criteria("criteria.data.houseYearFrom").doesNotExist(),
				q.criteria("criteria.data.houseYearTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildRoomQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Float rooms = data.get("rooms") != null ? Float.valueOf((String)data.get("rooms")) : null;
		logger.debug("Realty.data.rooms: {}", rooms);
		if (rooms != null && rooms > 0) {
			logger.debug("Realty.data.rooms: search with rooms");
			q.and(
				q.or(
					q.criteria("criteria.data.roomFrom").doesNotExist(),
					q.criteria("criteria.data.roomFrom").lessThanOrEq(rooms)
				),
				q.or(
					q.criteria("criteria.data.roomTo").doesNotExist(),
					q.criteria("criteria.data.roomTo").greaterThanOrEq(rooms)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.rooms: search empty rooms conditions");
			q.and(
				q.criteria("criteria.data.roomFrom").doesNotExist(),
				q.criteria("criteria.data.roomTo").doesNotExist()
			);
		}
		return q;
	}
	
	// TODO Внимание заменил externalComplexId на ObjectId так как поменяли связь в БДшке
	private static Query<Subscription> buildResidentialComplexQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Mapper mapper = new Mapper();
		mapper.getConverters().addConverter(CalendarConverter.class);
		ResidentialComplex residentalComplex = (DBObject)data.get("residentalComplex") != null ? mapper.fromDBObject(DB.DS(), ResidentialComplex.class, (DBObject)data.get("residentalComplex"), mapper.createEntityCache()) : null;
		
		if (residentalComplex != null && residentalComplex.id != null) {
			// Если есть ЖК то проверяем подписки где это не указано или где указан именно этот ЖК
			logger.debug("Realty.data.residentalComplex: {}, {}", residentalComplex.externalComplexId, residentalComplex.id);
			q.and(
				q.or(
					q.criteria("criteria.data.residentalComplexs").doesNotExist(),
					q.criteria("criteria.data.residentalComplexs.id").hasAnyOf(Arrays.asList(residentalComplex.id))
				)
			);
		} else {
			logger.debug("Realty.data.residentalComplex is null");
			// Если нету ЖК или не смогли распарсить в краулере, тогда смотрим подписки где нету ЖК
			q.and(q.criteria("criteria.data.residentalComplexs").doesNotExist());
		}
		return q;
	}
	
	
	private static Query<Subscription> buildLocationRegionsQuery(Query<Subscription> q, Advert realty){
		if (realty.location == null) {
			logger.debug("Realty.location == null");
			q.and(q.criteria("criteria.location").doesNotExist());
		} else {
			// Если есть расположения объекта
			if (realty.location.regions != null && realty.location.regions.size() > 0) {
				logger.debug("Realty.location.regions.target: {}, {}",realty.location.region.code, realty.location.region.name);				
				List<String> regionCodes = new ArrayList<String>();
				for (Region region : realty.location.regions) {
					if (region.code != null) {
						regionCodes.add(region.code);
						logger.debug("Realty.location.regions: {}, {}",region.code, region.name);
					} else {
						logger.warn("Found Region.code with null code value");	
					}
				}

				q.and(
					q.or(
						q.criteria("criteria.location").doesNotExist(),
						q.criteria("criteria.location.regions").doesNotExist(),
						q.criteria("criteria.location.regions.code").hasAnyOf(regionCodes)
					)
				);

			} else {
				logger.debug("Realty.location.regions == null");
				// Если в объявлении регионов нету, то остается только искать подписки без регионов (т.е. ВСЕ)
				q.and(q.criteria("criteria.location").doesNotExist());
			}
		}
		return q;
	}
	
	
	private static Query<Subscription> buildPublisherTypeQuery(Query<Subscription> q, Advert realty){
		logger.debug("Realty.publisher.publisherType: " + (realty.publisher != null? realty.publisher.type : null));
		if (realty.publisher == null || realty.publisher.type == AdvertPublisherType.UNDEFINED) {
			logger.debug("Realty.publisher.publisherType: is null");
			q.and(
				q.or(
					q.criteria("criteria.publisherTypes").doesNotExist(),
					q.criteria("criteria.publisherTypes").hasNoneOf(Arrays.asList(AdvertPublisherType.UNDEFINED.toString()))
				)
			);
		} else {
			logger.debug("Realty.publisher.publisherType: not null");
			q.and(
				q.or(
					q.criteria("criteria.publisherTypes").doesNotExist(),
					q.and(
						q.criteria("criteria.publisherTypes").hasNoneOf(Arrays.asList(AdvertPublisherType.UNDEFINED.toString())),
						q.criteria("criteria.publisherTypes").hasAnyOf(Arrays.asList(realty.publisher.type.toString()))
					)
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildSquareQuery(Query<Subscription> q, HashMap<String, Object> data) {
		Float square = data.get("square") != null ? Float.valueOf((String)data.get("square")) : null;
		logger.debug("Realty.square: " + square);
		if (square == null) {
			q.and(
				q.criteria("criteria.data.squareFrom").doesNotExist(),
				q.criteria("criteria.data.squareTo").doesNotExist()
			);
		} else {
			q.and(
				q.or(
					q.criteria("criteria.data.squareFrom").doesNotExist(),
					q.criteria("criteria.data.squareFrom").lessThanOrEq(square)
				),
				q.or(
					q.criteria("criteria.data.squareTo").doesNotExist(),
					q.criteria("criteria.data.squareTo").greaterThanOrEq(square)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildHasPhotoQuery(Query<Subscription> q, Advert realty){
		logger.debug("Realty.hasPhoto: " + realty.hasPhoto);
		if (realty.hasPhoto == null || realty.hasPhoto == false) {
			q.and(
				q.or(
					q.criteria("criteria.hasPhoto").doesNotExist(),
					q.criteria("criteria.hasPhoto").equal(realty.hasPhoto)
				)
			);
		}
		if (realty.hasPhoto != null && realty.hasPhoto) {
			q.and(
				q.or(
					q.criteria("criteria.hasPhoto").doesNotExist(),
					q.criteria("criteria.hasPhoto").equal(realty.hasPhoto)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildPriceQuery(Query<Subscription> q, Advert realty) {
		Long price = (Long)realty.data.get("price");
		logger.debug("Realty.price: " + price);
		if (price == null) {
			// Если нет цены в объявлении то нам подходят подписки без указания цены
			q.and(
				q.criteria("criteria.priceFrom").doesNotExist(),
				q.criteria("criteria.priceTo").doesNotExist()
			);
		} else {
			//Если цена есть в объявлении
			q.and(
				q.or(
					q.criteria("criteria.priceFrom").doesNotExist(),
					q.criteria("criteria.priceFrom").lessThanOrEq(price)
				),
				q.or(
					q.criteria("criteria.priceTo").doesNotExist(),
					q.criteria("criteria.priceTo").greaterThanOrEq(price)
				)
			);
		}
		return q;
	}
	
	
}
