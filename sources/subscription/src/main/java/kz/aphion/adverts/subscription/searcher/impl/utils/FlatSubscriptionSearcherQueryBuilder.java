package kz.aphion.adverts.subscription.searcher.impl.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.models.AdvertModel;
import kz.aphion.adverts.models.realty.FlatRentAdvertModel;
import kz.aphion.adverts.models.realty.FlatSellAdvertModel;
import kz.aphion.adverts.models.realty.data.FlatRealtyBaseDataModel;
import kz.aphion.adverts.models.realty.data.FlatRentDataModel;
import kz.aphion.adverts.models.realty.data.FlatSellDataModel;
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
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentMiscellaneousType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatRentPeriodType;
import kz.aphion.adverts.models.realty.data.flat.types.FlatSecurityType;
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
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FlatSubscriptionSearcherQueryBuilder {

	private static Logger logger = LoggerFactory.getLogger(FlatSubscriptionSearcherQueryBuilder.class);
	
	public static Query<Subscription> buidSellQuery(Datastore ds, Advert realty) {
		logger.debug("Builing Query for FlatSellRealty subscriptions");
		Query<Subscription> q = buildBaseCriteria(ds, AdvertOperationType.SELL);

		FlatSellAdvertModel model = new FlatSellAdvertModel(realty);
		
		// Формируем общие параметры для поиск
		q = buildCommonQuery(q, model, model.getDataModel());
		// В залоге или нет
		q = buildMortgagesStatusQuery(q, model.getDataModel());
		
		logger.debug("Query for FlatSellRealty subscriptions was built");
		return q;
	}
	
	public static Query<Subscription> buidRentQuery(Datastore ds, Advert realty) {
		logger.debug("Builing Query for FlatRentRealty subscriptions");
		Query<Subscription> q = buildBaseCriteria(ds, AdvertOperationType.RENT);
		
		FlatRentAdvertModel model = new FlatRentAdvertModel(realty);
		
		// Формируем общие параметры для поиск
		q = buildCommonQuery(q, model, model.getDataModel());
		// Срок аренды
		// Обязательно должен быть указан в подписках!
		q = buildRentPeriodQuery(q, model.getDataModel());
		// Дополнительные критерии для аренды квартиры или комнаты
		q = buildFlatRentMiscellaneousTypesQuery(q, model.getDataModel());
		
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
	private static Query<Subscription> buildCommonQuery(Query<Subscription> q, AdvertModel realty, FlatRealtyBaseDataModel model) {
		// Цена объекта
		q = buildPriceQuery(q, model);
		// Фотография объекта
		q = buildHasPhotoQuery(q, realty.getAdvert());
		// Площадь объекта
		q = buildSquareQuery(q, model);
		// Жилая площадь
		q = buildSquareLivingQuery(q, model);
		// Площадь кухни
		q = buildSquareKitckenQuery(q, model);
		// Кто опубликовал объявления
		q = buildPublisherTypeQuery(q, realty.getAdvert());
		// В каком регионе (районе) расположен объект
		q = buildLocationRegionsQuery(q, realty.getAdvert());
		// Какой жилой комплекс		
		q = buildResidentialComplexQuery(q, model);
		// Кол-во комнат
		q = buildRoomQuery(q, model);
		// Год постройки дома
		q = buildBuildingYear(q, model);
		// Этаж квартиры
		q = buildFlatFloorQuery(q, model);
		// Этажность дома
		q = buildHouseFloorCountQuery(q, model);
		// Высота потолков
		q = buildCeilingHieght(q, model);
		// Тип здания
		q = buildFlatBuildingTypeQuery(q, model);
		// Приватизиованное общежитие
		q = buildPrivatizedDormTypeQuery(q, model);
		// Ремонта
		q = buildRenovationTypeQuery(q, model);
		// Телефон
		q = buildPhoneType(q, model);
		// Интернет
		q = buildInternetTypeQuery(q, model);
		// Сан. узел
		q = buildLavatoryType(q, model);
		// Балкон
		q = buildBalconyType(q, model);
		// Остекление балкона
		q = buildBalconyGlazingType(q, model);
		// Дверь
		q = buildDoorTypeQuery(q, model);
		// Парковка
		q = buildParkingTypeQuery(q, model);
		// Мебель
		q = buildFurnitureTypeQuery(q, model);
		// Пол
		q = buildFloorTypeQuery(q, model);
		// Безопасность
		q = buildSecurityTypesQuery(q, model);
		// Дополнительно
		q = buildFlatMiscellaneousTypesQuery(q, model);
		
		// TODO Добавить грамонтную работу по локации или регионы или крарты или все вместе с пересечением
		//q.field("criteria.keywordsType")
		//q.field("criteria.keywords")
		//q.field("criteria.squareType").equal(arg0)
		
		return q;
	}
	
	
	private static Query<Subscription> buildMortgagesStatusQuery(Query<Subscription> q, FlatSellDataModel model) {
		MortgageStatus mortgageStatus = model.getMortgageStatus();
		logger.debug("Realty.data.mortgageStatus: {}", mortgageStatus);
		if (mortgageStatus == null || mortgageStatus == MortgageStatus.UNDEFINED) {
			logger.debug("Realty.data.mortgageStatus search without mortgageStatus");
			q.and(
				q.criteria("criteria.mortgageStatuses").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.mortgageStatus search with mortgageStatus");
			q.and(
				q.or(
					q.criteria("criteria.mortgageStatuses").doesNotExist(),
					q.criteria("criteria.mortgageStatuses").hasAnyOf(Arrays.asList(mortgageStatus.toString()))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatRentMiscellaneousTypesQuery(Query<Subscription> q, FlatRentDataModel model){
		List<FlatRentMiscellaneousType> rentMiscellaneous = model.getRentMiscellaneous();
		logger.debug("Realty.data.rentMiscellaneous size: {}", rentMiscellaneous != null ? rentMiscellaneous.size() : 0);
		if (rentMiscellaneous == null) {
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
			
			q.and(
				q.or(
					q.criteria("criteria.rentMiscellaneous").doesNotExist(),
					q.criteria("criteria.rentMiscellaneous").hasAnyOf(rentMiscellaneous)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildRentPeriodQuery(Query<Subscription> q, FlatRentDataModel model) {
		FlatRentPeriodType rentPeriod = model.getRentPeriod();
		logger.debug("Realty.data.rentPeriod: {}", rentPeriod);
		if (rentPeriod == null || rentPeriod == FlatRentPeriodType.UNDEFINED) {
			logger.warn("Realty.data.rentPeriod search without rentPeriod. It should be mandatory field for FlatRentRealty subscription!");
			q.and(
				q.criteria("criteria.rentPeriods").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.rentPeriod search with rentPeriod");
			q.and(
				q.or(
					q.criteria("criteria.rentPeriods").doesNotExist(),
					q.criteria("criteria.rentPeriods").hasAnyOf(Arrays.asList(rentPeriod.toString()))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatMiscellaneousTypesQuery(Query<Subscription> q, FlatRealtyBaseDataModel model){
		List<FlatMiscellaneousType> miscellaneous = model.getMiscellaneous();
		logger.debug("Realty.data.miscellaneous size: {}", miscellaneous != null ? miscellaneous.size() : 0);
		if (miscellaneous == null || (miscellaneous.size() == 1 && miscellaneous.contains(FlatMiscellaneousType.UNDEFINED))) {
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

			q.and(
				q.or(
					q.criteria("criteria.miscellaneous").doesNotExist(),
					q.criteria("criteria.miscellaneous").hasAnyOf(miscellaneous)
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildSecurityTypesQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		List<FlatSecurityType> securityTypes = model.getSecurityTypes();
		logger.debug("Realty.data.securityTypes size: {}", securityTypes != null ? securityTypes.size() : 0);
		if (securityTypes == null || (securityTypes.size() == 1 && securityTypes.contains(FlatSecurityType.UNDEFINED))) {
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

			q.and(
				q.or(
					q.criteria("criteria.securityTypes").doesNotExist(),
					q.criteria("criteria.securityTypes").hasAnyOf(securityTypes)
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFloorTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		FlatFloorType floorType = model.getFloorType();
		logger.debug("Realty.data.floorType: {}", floorType);
		if (floorType == null || floorType == FlatFloorType.UNDEFINED) {
			logger.debug("Realty.data.floorType search without floorType");
			q.and(
				q.criteria("criteria.floorTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.floorType search with floorType");
			q.and(
				q.or(
					q.criteria("criteria.floorTypes").doesNotExist(),
					q.criteria("criteria.floorTypes").hasAnyOf(Arrays.asList(floorType))				
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFurnitureTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		FlatFurnitureType furnitureType = model.getFurnitureType();
		logger.debug("Realty.data.furnitureType: {}", furnitureType);
		if (furnitureType == null || furnitureType == FlatFurnitureType.UNDEFINED) {
			logger.debug("Realty.data.furnitureType search without furnitureType");
			q.and(
				q.criteria("criteria.furnitureTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.furnitureType search with furnitureType");
			q.and(
				q.or(
					q.criteria("criteria.furnitureTypes").doesNotExist(),
					q.criteria("criteria.furnitureTypes").hasAnyOf(Arrays.asList(furnitureType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildParkingTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatParkingType parkingType = model.getParkingType();
		logger.debug("Realty.data.parkingType: {}", parkingType);
		if (parkingType == null || parkingType == FlatParkingType.UNDEFINED) {
			logger.debug("Realty.data.parkingType search without parkingType");
			q.and(
				q.criteria("criteria.parkingTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.parkingType search with parkingType");
			q.and(
				q.or(
					q.criteria("criteria.parkingTypes").doesNotExist(),
					q.criteria("criteria.parkingTypes").hasAnyOf(Arrays.asList(parkingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildDoorTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatDoorType doorType = model.getDoorType();
		logger.debug("Realty.data.doorType: {}", doorType);
		if (doorType == null || doorType == FlatDoorType.UNDEFINED) {
			logger.debug("Realty.doorType search without doorType");
			q.and(
				q.criteria("criteria.doorTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.doorType search with doorType");
			q.and(
				q.or(
					q.criteria("criteria.doorTypes").doesNotExist(),
					q.criteria("criteria.doorTypes").hasAnyOf(Arrays.asList(doorType))	
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBalconyGlazingType(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatBalconyGlazingType balconyGlazingType = model.getBalconyGlazingType();
		logger.debug("Realty.data.balconyGlazingType: {}", balconyGlazingType);
		if (balconyGlazingType == null || balconyGlazingType == FlatBalconyGlazingType.UNDEFINED) {
			logger.debug("Realty.data.balconyGlazingType search without balconyGlazingType");
			q.and(
				q.criteria("criteria.balconyGlazingTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.balconyGlazingType search with balconyGlazingType");
			q.and(
				q.or(
					q.criteria("criteria.balconyGlazingTypes").doesNotExist(),
					q.criteria("criteria.balconyGlazingTypes").hasAnyOf(Arrays.asList(balconyGlazingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildBalconyType(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatBalconyType balconyType = model.getBalconyType();
		logger.debug("Realty.data.balconyType: {}", balconyType);
		if (balconyType == null || balconyType == FlatBalconyType.UNDEFINED) {
			logger.debug("Realty.data.balconyType search without balconyType");
			q.and(
					q.criteria("criteria.balconyTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.balconyType search with balconyType");
			q.and(
				q.or(
					q.criteria("criteria.balconyTypes").doesNotExist(),
					q.criteria("criteria.balconyTypes").hasAnyOf(Arrays.asList(balconyType))
				)
			);
		}
		return q;
	}
	
	
	private static Query<Subscription> buildLavatoryType(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		FlatLavatoryType lavatoryType = model.getLavatoryType();
		logger.debug("Realty.data.lavatoryType: {}", lavatoryType);
		if (lavatoryType == null || lavatoryType == FlatLavatoryType.UNDEFINED) {
			logger.debug("Realty.data.lavatoryType search without lavatoryType");
			q.and(
				q.criteria("criteria.lavatoryTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.lavatoryType search with lavatoryType");
			q.and(
				q.or(
					q.criteria("criteria.lavatoryTypes").doesNotExist(),
					q.criteria("criteria.lavatoryTypes").hasAnyOf(Arrays.asList(lavatoryType))		
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildInternetTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		FlatInternetType internetType = model.getInternetType();
		logger.debug("Realty.data.internetType: {}", internetType);
		if (internetType == null || internetType == FlatInternetType.UNDEFINED) {
			logger.debug("Realty.data.internetType search without internetType");
			q.and(
				q.criteria("criteria.internetTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.internetType search with internetType");
			q.and(
				q.or(
					q.criteria("criteria.internetTypes").doesNotExist(),
					q.criteria("criteria.internetTypes").hasAnyOf(Arrays.asList(internetType))					
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildPhoneType(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatPhoneType phoneType = model.getPhoneType();
		logger.debug("Realty.data.phoneType: {}", phoneType);
		if (phoneType == null || phoneType == FlatPhoneType.UNDEFINED) {
			logger.debug("Realty.data.phoneType search without phoneType");
			q.and(
				q.criteria("criteria.phoneTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.phoneType search with phoneType");
			q.and(
				q.or(
					q.criteria("criteria.phoneTypes").doesNotExist(),
					q.criteria("criteria.phoneTypes").hasAnyOf(Arrays.asList(phoneType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildRenovationTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		FlatRenovationType renovationType = model.getRenovationType();
		logger.debug("Realty.data.renovationType: {}", renovationType);
		if (renovationType == null || renovationType == FlatRenovationType.UNDEFINED) {
			logger.debug("Realty.data.renovationType search without renovationType");
			q.and(
				q.criteria("criteria.renovationTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.renovationType search with renovationType");
			q.and(
				q.or(
					q.criteria("criteria.renovationTypes").doesNotExist(),
					q.criteria("criteria.renovationTypes").hasAnyOf(Arrays.asList(renovationType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildPrivatizedDormTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatPrivatizedDormType privatizedDormType = model.getPrivatizedDormType();
		logger.debug("Realty.data.privatizedDormType: {}", privatizedDormType);
		if (privatizedDormType == null || privatizedDormType == FlatPrivatizedDormType.UNDEFINED) {
			logger.debug("Realty.data.privatizedDormType search without privatizedDormType");
			q.and(
				q.criteria("criteria.privatizedDormTypes").doesNotExist()
			);
		} else {
			logger.debug("Realty.data.privatizedDormType search with privatizedDormType");
			q.and(
				q.or(
					q.criteria("criteria.privatizedDormTypes").doesNotExist(),
					q.criteria("criteria.privatizedDormTypes").hasAnyOf(Arrays.asList(privatizedDormType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildFlatBuildingTypeQuery(Query<Subscription> q, FlatRealtyBaseDataModel model){
		FlatBuildingType flatBuildingType = model.getFlatBuildingType();
		logger.debug("Realty.data.flatBuildingType: {}", flatBuildingType);
		if (flatBuildingType != null && flatBuildingType != FlatBuildingType.UNDEFINED) {
			logger.debug("Realty.data.flatBuildingType search without flatBuildingType");
			q.and(
				q.criteria("criteria.flatBuildingTypes").doesNotExist()
			);
			
		} else {
			logger.debug("Realty.data.flatBuildingType search with flatBuildingType");
			q.and(
				q.or(
					q.criteria("criteria.flatBuildingTypes").doesNotExist(),
					q.criteria("criteria.flatBuildingTypes").hasAnyOf(Arrays.asList(flatBuildingType))
				)
			);
		}
		return q;
	}
	
	private static Query<Subscription> buildCeilingHieght(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Float ceilingHeight = model.getCeilingHeight();
		logger.debug("Realty.data.ceilingHeight: {}", ceilingHeight);
		if (ceilingHeight != null && ceilingHeight > 0) {
			logger.debug("Realty.data.ceilingHeight search with ceilingHeight");
			q.and(
				q.or(
					q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
					q.criteria("criteria.ceilingHeightFrom").lessThanOrEq(ceilingHeight)
				),
				q.or(
					q.criteria("criteria.ceilingHeightTo").doesNotExist(),
					q.criteria("criteria.ceilingHeightTo").greaterThanOrEq(ceilingHeight)
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
	
	private static Query<Subscription> buildSquareKitckenQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Float squareKitchen = model.getSquareKitchen();
		logger.debug("Realty.data.squareKitchen: {}", squareKitchen);
		if (squareKitchen != null && squareKitchen > 0) {
			logger.debug("Realty.data.squareKitchen search with squareKitchen");
			q.and(
				q.or(
					q.criteria("criteria.squareKitchenFrom").doesNotExist(),
					q.criteria("criteria.squareKitchenFrom").lessThanOrEq(squareKitchen)
				),
				q.or(
					q.criteria("criteria.squareKitchenTo").doesNotExist(),
					q.criteria("criteria.squareKitchenTo").greaterThanOrEq(squareKitchen)
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
	
	private static Query<Subscription> buildSquareLivingQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Float squareLiving = model.getSquareLiving();
		logger.debug("Realty.data.squareLiving: {}", squareLiving);
		if (squareLiving != null && squareLiving > 0) {
			logger.debug("Realty.data.squareLiving search with squareLiving");
			q.and(
				q.or(
					q.criteria("criteria.squareLivingFrom").doesNotExist(),
					q.criteria("criteria.squareLivingFrom").lessThanOrEq(squareLiving)
				),
				q.or(
					q.criteria("criteria.squareLivingTo").doesNotExist(),
					q.criteria("criteria.squareLivingTo").greaterThanOrEq(squareLiving)
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
	
	private static Query<Subscription> buildHouseFloorCountQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Long houseFloorCount = model.getHouseFloorCount();
		logger.debug("Realty.data.houseFloorCount: {}", houseFloorCount);
		if (houseFloorCount != null && houseFloorCount > 0) {
			logger.debug("Realty.data.houseFloorCount search with houseFloorCount");
			q.and(
				q.or(
					q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
					q.criteria("criteria.houseFloorCountFrom").lessThanOrEq(houseFloorCount)
				),
				q.or(
					q.criteria("criteria.houseFloorCountTo").doesNotExist(),
					q.criteria("criteria.houseFloorCountTo").greaterThanOrEq(houseFloorCount)
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
	
	private static Query<Subscription> buildFlatFloorQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Long flatFloor = model.getFlatFloor();
		logger.debug("Realty.data.flatFloor: {}", flatFloor);
		if (flatFloor != null && flatFloor > 0) {
			logger.debug("Realty.data.flatFloor search with flatFloor");
			q.and(
				q.or(
					q.criteria("criteria.flatFloorFrom").doesNotExist(),
					q.criteria("criteria.flatFloorFrom").lessThanOrEq(flatFloor)
					),
				q.or(
					q.criteria("criteria.flatFloorTo").doesNotExist(),
					q.criteria("criteria.flatFloorTo").greaterThanOrEq(flatFloor)
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
	
	private static Query<Subscription> buildBuildingYear(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Long houseYear = model.getHouseYear();
		logger.debug("Realty.data.houseYear: {}", houseYear);
		if (houseYear != null && houseYear > 0) {
			logger.debug("Realty.data.houseYear search with year");
			q.and(
				q.or(
					q.criteria("criteria.houseYearFrom").doesNotExist(),
					q.criteria("criteria.houseYearFrom").lessThanOrEq(houseYear)
				),
				q.or(
					q.criteria("criteria.houseYearTo").doesNotExist(),
					q.criteria("criteria.houseYearTo").greaterThanOrEq(houseYear)
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
	
	private static Query<Subscription> buildRoomQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Float rooms = model.getRooms();
		logger.debug("Realty.data.rooms: {}", rooms);
		if (rooms != null && rooms > 0) {
			logger.debug("Realty.data.rooms: search with rooms");
			q.and(
				q.or(
					q.criteria("criteria.roomFrom").doesNotExist(),
					q.criteria("criteria.roomFrom").lessThanOrEq(rooms)
				),
				q.or(
					q.criteria("criteria.roomTo").doesNotExist(),
					q.criteria("criteria.roomTo").greaterThanOrEq(rooms)
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
	
	// TODO Внимание заменил externalComplexId на ObjectId так как поменяли связь в БДшке
	private static Query<Subscription> buildResidentialComplexQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		ResidentialComplex residentalComplex = model.getResidentialComplex(DB.DS());
		
		if (residentalComplex != null && residentalComplex.id != null) {
			// Если есть ЖК то проверяем подписки где это не указано или где указан именно этот ЖК
			logger.debug("Realty.data.residentalComplex: {}, {}", residentalComplex.externalComplexId, residentalComplex.id);
			q.and(
				q.or(
					q.criteria("criteria.residentialComplexIds").doesNotExist(),
					q.criteria("criteria.residentialComplexIds").hasAnyOf(Arrays.asList(residentalComplex.id.toHexString()))
				)
			);
		} else {
			logger.debug("Realty.data.residentialComplex is null");
			// Если нету ЖК или не смогли распарсить в краулере, тогда смотрим подписки где нету ЖК
			q.and(q.criteria("criteria.data.residentialComplexIds").doesNotExist());
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
						q.criteria("criteria.location.regionIds").hasAnyOf(regionCodes)
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
	
	private static Query<Subscription> buildSquareQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Float square = model.getSquare();
		logger.debug("Realty.square: " + square);
		if (square == null) {
			q.and(
				q.criteria("criteria.squareFrom").doesNotExist(),
				q.criteria("criteria.squareTo").doesNotExist()
			);
		} else {
			q.and(
				q.or(
					q.criteria("criteria.squareFrom").doesNotExist(),
					q.criteria("criteria.squareFrom").lessThanOrEq(square)
				),
				q.or(
					q.criteria("criteria.squareTo").doesNotExist(),
					q.criteria("criteria.squareTo").greaterThanOrEq(square)
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
	
	
	private static Query<Subscription> buildPriceQuery(Query<Subscription> q, FlatRealtyBaseDataModel model) {
		Long price = model.getPrice();
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
