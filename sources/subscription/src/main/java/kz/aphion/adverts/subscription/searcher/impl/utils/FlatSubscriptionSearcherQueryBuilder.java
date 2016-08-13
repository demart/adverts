package kz.aphion.adverts.subscription.searcher.impl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.realty.Realty;
import kz.aphion.adverts.persistence.realty.RealtyPublisherType;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRealtyBaseData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellData;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBalconyGlazingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBalconyType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatBuildingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatDoorType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatFloorType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatFurnitureType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatInternetType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatLavatoryType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatParkingType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatPhoneType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatPrivatizedDormType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRenovationType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.persistence.realty.data.flat.types.FlatSecurityType;
import kz.aphion.adverts.persistence.realty.types.MortgageStatus;
import kz.aphion.adverts.persistence.realty.types.RealtyOperationType;
import kz.aphion.adverts.persistence.realty.types.RealtyType;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.persistence.subscription.SubscriptionAdvertType;
import kz.aphion.adverts.persistence.subscription.SubscriptionStatus;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlatSubscriptionSearcherQueryBuilder {

	private static Logger logger = LoggerFactory.getLogger(FlatSubscriptionSearcherQueryBuilder.class);
	
	public static Query<Subscription> buidQuery(Datastore ds, FlatSellRealty realty) {
		logger.debug("Builing Query for FlatSellRealty subscriptions");
		Query<Subscription> q = buildBaseCriteria(ds, RealtyOperationType.SELL);

		// Формируем общие параметры для поиск
		q = buildCommonQuery(q, realty, realty.data);
		// В залоге или нет
		q = buildMortgagesStatusQuery(q, realty.data);
		
		logger.debug("Query for FlatSellRealty subscriptions was built");
		return q;
	}
	
	public static Query<Subscription> buidQuery(Datastore ds, FlatRentRealty realty) {
		logger.debug("Builing Query for FlatRentRealty subscriptions");
		Query<Subscription> q = buildBaseCriteria(ds, RealtyOperationType.RENT);
		
		// Формируем общие параметры для поиск
		q = buildCommonQuery(q, realty, realty.data);
		// Срок аренды
		// Обязательно должен быть указан в подписках!
		q = buildRentPeriodQuery(q, realty.data);
		// Дополнительные критерии для аренды квартиры или комнаты
		q = buildFlatRentMiscellaneousTypesQuery(q, realty.data);
		
		logger.debug("Query for FlatRentRealty subscriptions was built");
		return q;
	}
	
	
	private static Query<Subscription> buildBaseCriteria(Datastore ds, RealtyOperationType type) {
		Query<Subscription> q = ds.createQuery(Subscription.class);
		
		// Отключаем валидацию, так как мы используем Generic типы которые невозможно проверить
		q = q.disableValidation();
		
		q.and(
			q.criteria("advertType").equal(SubscriptionAdvertType.REALTY),
			q.criteria("startedAt").lessThan(Calendar.getInstance().getTime()),
			q.criteria("expiresAt").greaterThan(Calendar.getInstance().getTime()),
			q.criteria("status").equal(SubscriptionStatus.ACTIVE),
			q.criteria("criteria.type").equal(RealtyType.FLAT),
			q.criteria("criteria.operation").equal(type)
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
	private static Query<Subscription> buildCommonQuery(Query<Subscription> q, Realty realty, FlatRealtyBaseData data) {
		// Цена объекта
		q = buildPriceQuery(q, realty);
		// Фотография объекта
		q = buildHasPhotoQuery(q, realty);
		// Площадь объекта
		q = buildSquareQuery(q, data);
		// Жилая площадь
		q = buildSquareLivingQuery(q, data);
		// Площадь кухни
		q = buildSquareKitckenQuery(q, data);
		// Кто опубликовал объявления
		q = buildPublisherTypeQuery(q, realty);
		// В каком регионе (районе) расположен объект
		q = buildLocationRegionsQuery(q, realty);
		// Какой жилой комплекс		
		q = buildResidentialComplexQuery(q, data);
		// Кол-во комнат
		q = buildRoomQuery(q, data);
		// Год постройки дома
		q = buildBuildingYear(q, data);
		// Этаж квартиры
		q = buildFlatFloorQuery(q, data);
		// Этажность дома
		q = buildHouseFloorCountQuery(q, data);
		// Высота потолков
		q = buildCeilingHieght(q, data);
		// Тип здания
		q = buildFlatBuildingTypeQuery(q, data);
		// Приватизиованное общежитие
		q = buildPrivatizedDormTypeQuery(q, data);
		// Ремонта
		q = buildRenovationTypeQuery(q, data);
		// Телефон
		q = buildPhoneType(q, data);
		// Интернет
		q = buildInternetTypeQuery(q, data);
		// Сан. узел
		q = buildLavatoryType(q, data);
		// Балкон
		q = buildBalconyType(q, data);
		// Остекление балкона
		q = buildBalconyGlazingType(q, data);
		// Дверь
		q = buildDoorTypeQuery(q, data);
		// Парковка
		q = buildParkingTypeQuery(q, data);
		// Мебель
		q = buildFurnitureTypeQuery(q, data);
		// Пол
		q = buildFloorTypeQuery(q, data);
		// Безопасность
		q = buildSecurityTypesQuery(q, data);
		// Дополнительно
		q = buildFlatMiscellaneousTypesQuery(q, data);
		
		// TODO Добавить грамонтную работу по локации или регионы или крарты или все вместе с пересечением
		//q.field("criteria.keywordsType")
		//q.field("criteria.keywords")
		//q.field("criteria.squareType").equal(arg0)
		
		return q;
	}
	
	
	private static Query<Subscription> buildMortgagesStatusQuery(Query<Subscription> q, FlatSellData data) {
		logger.debug("Realty.data.mortgageStatus: {}", data.mortgageStatus);
		if (data.mortgageStatus == null || data.mortgageStatus == MortgageStatus.UNDEFINED) {
			logger.debug("Realty.data.mortgageStatus search without mortgageStatus");
			q.and(
				q.criteria("criteria.mortgageStatuses").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.mortgageStatus search with mortgageStatus");
			q.and(
				q.or(
					q.criteria("criteria.mortgageStatuses").doesNotExist(),
					q.criteria("criteria.mortgageStatuses").hasAnyOf(Arrays.asList(data.mortgageStatus.toString()))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatRentMiscellaneousTypesQuery(Query<Subscription> q, FlatRentData data){
		logger.debug("Realty.data.rentMiscellaneous size: {}", data.rentMiscellaneous != null ? data.rentMiscellaneous.size() : 0);
		if (data.rentMiscellaneous == null) {
			logger.debug("Realty.data.rentMiscellaneous search without rentMiscellaneous");
			q.and(
				q.criteria("criteria.rentMiscellaneous").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.rentMiscellaneous search with rentMiscellaneous");
			// Так как много элементов может быть у квартиры и так же много у подписки
			// нужно выбрать оптимальный и простой способ сравнения
			// Например можно использовать или, так как человек сокрее всего хочет просто
			// понимать есть ли домофон или видо домофон
			
			List<String> rentMiscellaneous = new ArrayList<String>();
			for (FlatRentMiscellaneousType type : data.rentMiscellaneous)
				rentMiscellaneous.add(type.toString());
			
			q.and(
				q.or(
					q.criteria("criteria.rentMiscellaneous").doesNotExist(),
					q.criteria("criteria.rentMiscellaneous").hasAnyOf(rentMiscellaneous)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildRentPeriodQuery(Query<Subscription> q, FlatRentData data) {
		logger.debug("Realty.data.rentPeriod: {}", data.rentPeriod);
		if (data.rentPeriod == null || data.rentPeriod == FlatRentPeriodType.UNDEFINED) {
			logger.warn("Realty.data.rentPeriod search without rentPeriod. It should be mandatory field for FlatRentRealty subscription!");
			q.and(
				q.criteria("criteria.rentPeriods").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.rentPeriod search with rentPeriod");
			q.and(
				q.or(
					q.criteria("criteria.rentPeriods").doesNotExist(),
					q.criteria("criteria.rentPeriods").hasAnyOf(Arrays.asList(data.rentPeriod.toString()))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatMiscellaneousTypesQuery(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.miscellaneous size: {}", data.miscellaneous != null ? data.miscellaneous.size() : 0);
		if (data.miscellaneous == null || (data.miscellaneous.size() == 1 && data.miscellaneous.contains(FlatMiscellaneousType.UNDEFINED))) {
			logger.debug("Realty.data.miscellaneous search without miscellaneous");
			q.and(
				q.criteria("criteria.miscellaneous").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.miscellaneous search with miscellaneous");
			// Так как много элементов может быть у квартиры и так же много у подписки
			// нужно выбрать оптимальный и простой способ сравнения
			// Например можно использовать или, так как человек сокрее всего хочет просто
			// понимать есть ли домофон или видо домофон

			List<String> miscellaneous = new ArrayList<String>();
			for (FlatMiscellaneousType type : data.miscellaneous)
				miscellaneous.add(type.toString());
			
			q.and(
				q.or(
					q.criteria("criteria.miscellaneous").doesNotExist(),
					q.criteria("criteria.miscellaneous").hasAnyOf(miscellaneous)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildSecurityTypesQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.securityTypes size: {}", data.securityTypes != null ? data.securityTypes.size() : 0);
		if (data.securityTypes == null || (data.securityTypes.size() == 1 && data.securityTypes.contains(FlatSecurityType.UNDEFINED))) {
			logger.debug("Realty.data.securityTypes search without securityTypes");
			q.and(
				q.criteria("criteria.securityTypes").doesNotExist()	
			);
		} else {
			logger.debug("Realty.data.securityTypes search with securityTypes");
			// Так как много элементов может быть у квартиры и так же много у подписки
			// нужно выбрать оптимальный и простой способ сравнения
			// Например можно использовать или, так как человек сокрее всего хочет просто
			// понимать есть ли домофон или видо домофон

			List<String> securityTypes = new ArrayList<String>();
			for (FlatSecurityType type : data.securityTypes)
				securityTypes.add(type.toString());
			
			q.and(
				q.or(
					q.criteria("criteria.securityTypes").doesNotExist(),
					q.criteria("criteria.securityTypes").hasAnyOf(securityTypes)
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFloorTypeQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.floorType: {}", data.floorType);
		if (data.floorType == null || data.floorType == FlatFloorType.UNDEFINED) {
			logger.debug("Realty.data.floorType search without floorType");
			q.and(
				q.criteria("criteria.floorTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.floorType search with floorType");
			q.and(
				q.or(
					q.criteria("criteria.floorTypes").doesNotExist(),
					q.criteria("criteria.floorTypes").hasAnyOf(Arrays.asList(data.floorType))				
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFurnitureTypeQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.furnitureType: {}", data.furnitureType);
		if (data.furnitureType == null || data.furnitureType == FlatFurnitureType.UNDEFINED) {
			logger.debug("Realty.data.furnitureType search without furnitureType");
			q.and(
				q.criteria("criteria.furnitureTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.furnitureType search with furnitureType");
			q.and(
				q.or(
					q.criteria("criteria.furnitureTypes").doesNotExist(),
					q.criteria("criteria.furnitureTypes").hasAnyOf(Arrays.asList(data.furnitureType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildParkingTypeQuery(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.parkingType: {}", data.parkingType);
		if (data.parkingType == null || data.parkingType == FlatParkingType.UNDEFINED) {
			logger.debug("Realty.data.parkingType search without parkingType");
			q.and(
				q.criteria("criteria.parkingTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.parkingType search with parkingType");
			q.and(
				q.or(
					q.criteria("criteria.parkingTypes").doesNotExist(),
					q.criteria("criteria.parkingTypes").hasAnyOf(Arrays.asList(data.parkingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildDoorTypeQuery(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.doorType: {}", data.doorType);
		if (data.doorType == null || data.doorType == FlatDoorType.UNDEFINED) {
			logger.debug("Realty.data.doorType search without doorType");
			q.and(
				q.criteria("criteria.doorTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.doorType search with doorType");
			q.and(
				q.or(
					q.criteria("criteria.doorTypes").doesNotExist(),
					q.criteria("criteria.doorTypes").hasAnyOf(Arrays.asList(data.doorType))	
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBalconyGlazingType(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.balconyGlazingType: {}", data.balconyGlazingType);
		if (data.balconyGlazingType == null || data.balconyGlazingType == FlatBalconyGlazingType.UNDEFINED) {
			logger.debug("Realty.data.balconyGlazingType search without balconyGlazingType");
			q.and(
				q.criteria("criteria.balconyGlazingTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.balconyGlazingType search with balconyGlazingType");
			q.and(
				q.or(
					q.criteria("criteria.balconyGlazingTypes").doesNotExist(),
					q.criteria("criteria.balconyGlazingTypes").hasAnyOf(Arrays.asList(data.balconyGlazingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBalconyType(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.balconyType: {}", data.balconyType);
		if (data.balconyType == null || data.balconyType == FlatBalconyType.UNDEFINED) {
			logger.debug("Realty.data.balconyType search without balconyType");
			q.and(
					q.criteria("criteria.balconyTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.balconyType search with balconyType");
			q.and(
				q.or(
					q.criteria("criteria.balconyTypes").doesNotExist(),
					q.criteria("criteria.balconyTypes").hasAnyOf(Arrays.asList(data.balconyType))
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildLavatoryType(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.lavatoryType: {}", data.lavatoryType);
		if (data.lavatoryType == null || data.lavatoryType == FlatLavatoryType.UNDEFINED) {
			logger.debug("Realty.data.lavatoryType search without lavatoryType");
			q.and(
				q.criteria("criteria.lavatoryTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.lavatoryType search with lavatoryType");
			q.and(
				q.or(
					q.criteria("criteria.lavatoryTypes").doesNotExist(),
					q.criteria("criteria.lavatoryTypes").hasAnyOf(Arrays.asList(data.lavatoryType))		
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildInternetTypeQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.internetType: {}", data.internetType);
		if (data.internetType == null || data.internetType == FlatInternetType.UNDEFINED) {
			logger.debug("Realty.data.internetType search without internetType");
			q.and(
				q.criteria("criteria.internetTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.internetType search with internetType");
			q.and(
				q.or(
					q.criteria("criteria.internetTypes").doesNotExist(),
					q.criteria("criteria.internetTypes").hasAnyOf(Arrays.asList(data.internetType))					
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildPhoneType(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.phoneType: {}", data.phoneType);
		if (data.phoneType == null || data.phoneType == FlatPhoneType.UNDEFINED) {
			logger.debug("Realty.data.phoneType search without phoneType");
			q.and(
				q.criteria("criteria.phoneTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.phoneType search with phoneType");
			q.and(
				q.or(
					q.criteria("criteria.phoneTypes").doesNotExist(),
					q.criteria("criteria.phoneTypes").hasAnyOf(Arrays.asList(data.phoneType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildRenovationTypeQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.renovationType: {}", data.renovationType);
		if (data.renovationType == null || data.renovationType == FlatRenovationType.UNDEFINED) {
			logger.debug("Realty.data.renovationType search without renovationType");
			q.and(
				q.criteria("criteria.renovationTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.renovationType search with renovationType");
			q.and(
				q.or(
					q.criteria("criteria.renovationTypes").doesNotExist(),
					q.criteria("criteria.renovationTypes").hasAnyOf(Arrays.asList(data.renovationType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildPrivatizedDormTypeQuery(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.privatizedDormType: {}", data.privatizedDormType);
		if (data.privatizedDormType == null || data.privatizedDormType == FlatPrivatizedDormType.UNDEFINED) {
			logger.debug("Realty.data.privatizedDormType search without privatizedDormType");
			q.and(
				q.criteria("criteria.privatizedDormTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.privatizedDormType search with privatizedDormType");
			q.and(
				q.or(
					q.criteria("criteria.privatizedDormTypes").doesNotExist(),
					q.criteria("criteria.privatizedDormTypes").hasAnyOf(Arrays.asList(data.privatizedDormType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatBuildingTypeQuery(Query<Subscription> q, FlatRealtyBaseData data){
		logger.debug("Realty.data.flatBuildingType: {}", data.flatBuildingType);
		if (data.flatBuildingType != null && data.flatBuildingType != FlatBuildingType.UNDEFINED) {
			logger.debug("Realty.data.flatBuildingType search without flatBuildingType");
			q.and(
				q.criteria("criteria.flatBuildingTypes").doesNotExist()
			);
			
		} else {
			logger.debug("Realty.data.flatBuildingType search with flatBuildingType");
			q.and(
				q.or(
					q.criteria("criteria.flatBuildingTypes").doesNotExist(),
					q.criteria("criteria.flatBuildingTypes").hasAnyOf(Arrays.asList(data.flatBuildingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildCeilingHieght(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.ceilingHeight: {}", data.ceilingHeight);
		if (data.ceilingHeight != null && data.ceilingHeight > 0) {
			logger.debug("Realty.data.ceilingHeight search with ceilingHeight");
			q.and(
				q.or(
					q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
					q.criteria("criteria.ceilingHeightFrom").lessThanOrEq(data.ceilingHeight)
				),
				q.or(
					q.criteria("criteria.ceilingHeightTo").doesNotExist(),
					q.criteria("criteria.ceilingHeightTo").greaterThanOrEq(data.ceilingHeight)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.ceilingHeight search without ceilingHeight");
			q.and(
				q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
				q.criteria("criteria.ceilingHeightTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildSquareKitckenQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.squareKitchen: {}", data.squareKitchen);
		if (data.squareKitchen != null && data.squareKitchen > 0) {
			logger.debug("Realty.data.squareKitchen search with squareKitchen");
			q.and(
				q.or(
					q.criteria("criteria.squareKitchenFrom").doesNotExist(),
					q.criteria("criteria.squareKitchenFrom").lessThanOrEq(data.squareKitchen)
				),
				q.or(
					q.criteria("criteria.squareKitchenTo").doesNotExist(),
					q.criteria("criteria.squareKitchenTo").greaterThanOrEq(data.squareKitchen)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.squareKitchen search without squareKitchen");
			q.and(
				q.criteria("criteria.squareKitchenFrom").doesNotExist(),
				q.criteria("criteria.squareKitchenTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildSquareLivingQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.squareLiving: {}", data.squareLiving);
		if (data.squareLiving != null && data.squareLiving > 0) {
			logger.debug("Realty.data.squareLiving search with squareLiving");
			q.and(
				q.or(
					q.criteria("criteria.squareLivingFrom").doesNotExist(),
					q.criteria("criteria.squareLivingFrom").lessThanOrEq(data.squareLiving)
				),
				q.or(
					q.criteria("criteria.squareLivingTo").doesNotExist(),
					q.criteria("criteria.squareLivingTo").greaterThanOrEq(data.squareLiving)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.squareLiving search without squareLiving");
			q.and(
				q.criteria("criteria.squareLivingFrom").doesNotExist(),
				q.criteria("criteria.squareLivingTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildHouseFloorCountQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.houseFloorCount: {}", data.houseFloorCount);
		if (data.houseFloorCount != null && data.houseFloorCount > 0) {
			logger.debug("Realty.data.houseFloorCount search with houseFloorCount");
			q.and(
				q.or(
					q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
					q.criteria("criteria.houseFloorCountFrom").lessThanOrEq(data.houseFloorCount)
				),
				q.or(
					q.criteria("criteria.houseFloorCountTo").doesNotExist(),
					q.criteria("criteria.houseFloorCountTo").greaterThanOrEq(data.houseFloorCount)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.houseFloorCount search without houseFloorCount");
			q.and(
				q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
				q.criteria("criteria.houseFloorCountTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatFloorQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.flatFloor: {}", data.flatFloor);
		if (data.flatFloor != null && data.flatFloor > 0) {
			logger.debug("Realty.data.flatFloor search with flatFloor");
			q.and(
				q.or(
					q.criteria("criteria.flatFloorFrom").doesNotExist(),
					q.criteria("criteria.flatFloorFrom").lessThanOrEq(data.flatFloor)
					),
				q.or(
					q.criteria("criteria.flatFloorTo").doesNotExist(),
					q.criteria("criteria.flatFloorTo").greaterThanOrEq(data.flatFloor)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.flatFloor search without flatFloor");
			q.and(
				q.criteria("criteria.flatFloorFrom").doesNotExist(),
				q.criteria("criteria.flatFloorTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBuildingYear(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.houseYear: {}", data.houseYear);
		if (data.houseYear != null && data.houseYear > 0) {
			logger.debug("Realty.data.houseYear search with year");
			q.and(
				q.or(
					q.criteria("criteria.houseYearFrom").doesNotExist(),
					q.criteria("criteria.houseYearFrom").lessThanOrEq(data.houseYear)
				),
				q.or(
					q.criteria("criteria.houseYearTo").doesNotExist(),
					q.criteria("criteria.houseYearTo").greaterThanOrEq(data.houseYear)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.houseYear search without year");
			q.and(
				q.criteria("criteria.houseYearFrom").doesNotExist(),
				q.criteria("criteria.houseYearTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildRoomQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.data.rooms: {}", data.rooms);
		if (data.rooms != null && data.rooms > 0) {
			logger.debug("Realty.data.rooms: search with rooms");
			q.and(
				q.or(
					q.criteria("criteria.roomFrom").doesNotExist(),
					q.criteria("criteria.roomFrom").lessThanOrEq(data.rooms)
				),
				q.or(
					q.criteria("criteria.roomTo").doesNotExist(),
					q.criteria("criteria.roomTo").greaterThanOrEq(data.rooms)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.debug("Realty.data.rooms: search empty rooms conditions");
			q.and(
				q.criteria("criteria.roomFrom").doesNotExist(),
				q.criteria("criteria.roomTo").doesNotExist()
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildResidentialComplexQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		if (data.residentalComplex != null && data.residentalComplex.relationId != null) {
			// Если есть ЖК то проверяем подписки где это не указано или где указан именно этот ЖК
			logger.debug("Realty.data.residentalComplex: {}, {}", data.residentalComplex.externalComplexId, data.residentalComplex.name);
			q.and(
				q.or(
					q.criteria("criteria.residentalComplexs").doesNotExist(),
					q.criteria("criteria.residentalComplexs.relationId").hasAnyOf(Arrays.asList(data.residentalComplex.relationId))
				)
			);
		} else {
			logger.debug("Realty.data.residentalComplex is null");
			// Если нету ЖК или не смогли распарсить в краулере, тогда смотрим подписки где нету ЖК
			q.and(q.criteria("criteria.residentalComplexs").doesNotExist());
		}
		return q;
	}
	
	
	private static Query<Subscription> buildLocationRegionsQuery(Query<Subscription> q, Realty realty){
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
	
	
	private static Query<Subscription> buildPublisherTypeQuery(Query<Subscription> q, Realty realty){
		logger.debug("Realty.publisher.publisherType: " + (realty.publisher != null? realty.publisher.publisherType : null));
		if (realty.publisher == null || realty.publisher.publisherType == RealtyPublisherType.UNDEFINED) {
			logger.debug("Realty.publisher.publisherType: is null");
			q.and(
				q.or(
					q.criteria("criteria.publisherTypes").doesNotExist(),
					q.criteria("criteria.publisherTypes").hasNoneOf(Arrays.asList(RealtyPublisherType.UNDEFINED.toString()))
				)
			);
		} else {
			logger.debug("Realty.publisher.publisherType: not null");
			q.and(
				q.or(
					q.criteria("criteria.publisherTypes").doesNotExist(),
					q.and(
						q.criteria("criteria.publisherTypes").hasNoneOf(Arrays.asList(RealtyPublisherType.UNDEFINED.toString())),
						q.criteria("criteria.publisherTypes").hasAnyOf(Arrays.asList(realty.publisher.publisherType.toString()))
					)
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildSquareQuery(Query<Subscription> q, FlatRealtyBaseData data) {
		logger.debug("Realty.square: " + data.square);
		if (data.square == null) {
			q.and(
				q.criteria("criteria.squareFrom").doesNotExist(),
				q.criteria("criteria.squareTo").doesNotExist()
			);
		} else {
			q.and(
				q.or(
					q.criteria("criteria.squareFrom").doesNotExist(),
					q.criteria("criteria.squareFrom").lessThanOrEq(data.square)
				),
				q.or(
					q.criteria("criteria.squareTo").doesNotExist(),
					q.criteria("criteria.squareTo").greaterThanOrEq(data.square)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildHasPhotoQuery(Query<Subscription> q, Realty realty){
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
	
	
	private static Query<Subscription> buildPriceQuery(Query<Subscription> q, Realty realty) {
		logger.debug("Realty.price: " + realty.price);
		if (realty.price == null) {
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
					q.criteria("criteria.priceFrom").lessThanOrEq(realty.price)
				),
				q.or(
					q.criteria("criteria.priceTo").doesNotExist(),
					q.criteria("criteria.priceTo").greaterThanOrEq(realty.price)
				)
			);
		}
		return q;
	}
	
	
}
