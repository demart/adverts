package kz.aphion.adverts.subscription.searcher.impl;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.data.flat.FlatRentRealty;
import kz.aphion.adverts.persistence.subscription.Subscription;
import kz.aphion.adverts.subscription.processors.RealtyAdvertSubscriptionProcessor;
import kz.aphion.adverts.subscription.providers.MongoDbProvider;
import kz.aphion.adverts.subscription.searcher.SubscriptionSearcher;
import kz.aphion.adverts.subscription.searcher.impl.utils.FlatSubscriptionSearcherQueryBuilder;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class FlatRentSubscriptionSearcher implements SubscriptionSearcher {

	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertSubscriptionProcessor.class);

	
	private ObjectId advertId;
	
	private FlatRentRealty realty;
	
	@Override
	public void setAdvertObjectId(String objectId) {
		this.advertId = new ObjectId(objectId);
	}

	@Override
	public BaseEntity getAdvertObject() {
		return realty;
	}
	
	
	@Override
	public List<Subscription> search() {
		try {
			Datastore ds = MongoDbProvider.getInstance().getDatastore();
			
			realty = ds.get(FlatRentRealty.class, advertId);
			if (realty == null) {
				logger.warn("Object with provided Id [" + advertId + "] not found. Possibly already exists newer version.");
				return null;
			}
			
			List<Subscription> result = search(ds, realty);
			if (result != null)
				logger.debug("Found Flat Rent [" + result.size() +  "] subscriptions");
			
			return result;
			
		} catch (Exception ex) {
			logger.error("Error in searching flat rent subscriptions", ex);
			return null;
		}
	}

	private List<Subscription> search(Datastore ds, FlatRentRealty realty) {
		Query<Subscription> q = FlatSubscriptionSearcherQueryBuilder.buidQuery(ds, realty);
		List<Subscription> result = q.asList();
		return result;
		
		/*
		
		// Subscription Base Criteria
		q.and(
			q.criteria("advertType").equal(SubscriptionAdvertType.REALTY),
			q.criteria("startedAt").lessThan(Calendar.getInstance().getTime()),
			q.criteria("expiresAt").greaterThan(Calendar.getInstance().getTime()),
			q.criteria("status").equal(SubscriptionStatus.ACTIVE),
			// Realty Base Fields
			q.criteria("criteria.type").equal(RealtyType.FLAT),
			q.criteria("criteria.operation").equal(RealtyOperationType.RENT)
		);
		
		logger.info("Realty.price: " + realty.price);
		// =====
		// price from
		
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
		
		logger.info("Realty.hasPhoto: " + realty.hasPhoto);
		// =====
		// photo
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
		
		logger.info("Realty.square: " + realty.data.square);
		// =====
		// criteria.squareFrom
		// criteria.squareTo
		if (realty.data.square == null) {
			q.and(
				q.criteria("criteria.squareFrom").doesNotExist(),
				q.criteria("criteria.squareTo").doesNotExist()
			);
		}
		
		if (realty.data.square != null) {
			q.and(
				q.or(
					q.criteria("criteria.squareFrom").doesNotExist(),
					q.criteria("criteria.squareFrom").lessThanOrEq(realty.data.square)
				),
				q.or(
					q.criteria("criteria.squareTo").doesNotExist(),
					q.criteria("criteria.squareTo").greaterThanOrEq(realty.data.square)
				)
			);
		}
		
		
		// =====
		//q.field("criteria.publisherTypes").equal(arg0)
		logger.info("Realty.publisher.publisherType: " + realty.publisher.publisherType);
		if (realty.publisher == null || realty.publisher.publisherType == RealtyPublisherType.UNDEFINED) {
			logger.info("Realty.publisher.publisherType: 1 search option");
			q.and(
				q.or(
					q.criteria("criteria.publisherTypes").doesNotExist(),
					q.criteria("criteria.publisherTypes").hasNoneOf(Arrays.asList(RealtyPublisherType.UNDEFINED.toString()))
				)
			);
		} else {
			logger.info("Realty.publisher.publisherType: 2 search option");
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

		// =====
		//q.field("criteria.location").equal(arg0)
		
		if (realty.location == null) {
			logger.info("Realty.location == null");
			q.and(q.criteria("criteria.location").doesNotExist());
		} else {
			// Если есть расположения объекта
			if (realty.location.regions != null && realty.location.regions.size() > 0) {
				logger.info("Realty.location.regions.target: {}, {}",realty.location.region.code, realty.location.region.name);				
				List<String> regionCodes = new ArrayList<String>();
				for (Region region : realty.location.regions) {
					if (region.code != null) {
						regionCodes.add(region.code);
						logger.info("Realty.location.regions: {}, {}",region.code, region.name);
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
				logger.info("Realty.location.regions == null");
				// Если в объявлении регионов нету, то остается только искать подписки без регионов (т.е. ВСЕ)
				q.and(q.criteria("criteria.location").doesNotExist());
			}
		}
		
		
		
		// TODO Добавить грамонтную работу по локации или регионы или крарты или все вместе с пересечением
		
		
		//q.field("criteria.keywordsType")
		//q.field("criteria.keywords")
		//q.field("criteria.squareType").equal(arg0)
		

		
		// Flat Sell Specific fields
		
		
		// Residential Complex
		if (realty.data.residentalComplex != null && realty.data.residentalComplex.relationId != null) {
			// Если есть ЖК то проверяем подписки где это не указано или где указан именно этот ЖК
			logger.info("Realty.data.residentalComplex: {}, {}",realty.data.residentalComplex.externalComplexId, realty.data.residentalComplex.name);
			q.and(
				q.or(
					q.criteria("criteria.residentalComplexs").doesNotExist(),
					q.criteria("criteria.residentalComplexs.relationId").hasAnyOf(Arrays.asList(realty.data.residentalComplex.relationId))
				)
			);
		} else {
			logger.info("Realty.data.residentalComplex is null");
			// Если нету ЖК или не смогли распарсить в краулере, тогда смотрим подписки где нету ЖК
			q.and(q.criteria("criteria.residentalComplexs").doesNotExist());
		}
		
		// ====================
		// roomFrom;
		logger.info("Realty.data.rooms: {}", realty.data.rooms);
		if (realty.data.rooms != null && realty.data.rooms > 0) {
			logger.info("Realty.data.rooms: search with rooms");
			// roomsFrom
			q.and(
				q.or(
					q.criteria("criteria.roomFrom").doesNotExist(),
					q.criteria("criteria.roomFrom").lessThanOrEq(realty.data.rooms)
				),
		
				// roomsTo
				q.or(
					q.criteria("criteria.roomTo").doesNotExist(),
					q.criteria("criteria.roomTo").greaterThanOrEq(realty.data.rooms)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.rooms: search empty rooms conditions");
			q.and(
				q.criteria("criteria.roomFrom").doesNotExist(),
				q.criteria("criteria.roomTo").doesNotExist()
			);
		}
		
		// ====================
		// houseYearFrom;
		// houseYearTo
		logger.info("Realty.data.houseYear: {}", realty.data.houseYear);
		if (realty.data.houseYear != null && realty.data.houseYear > 0) {
			logger.info("Realty.data.houseYear search with year");
			q.and(
				q.or(
					q.criteria("criteria.houseYearFrom").doesNotExist(),
					q.criteria("criteria.houseYearFrom").lessThanOrEq(realty.data.houseYear)
				),
				q.or(
					q.criteria("criteria.houseYearTo").doesNotExist(),
					q.criteria("criteria.houseYearTo").greaterThanOrEq(realty.data.houseYear)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.houseYear search without year");
			q.and(
					q.criteria("criteria.houseYearFrom").doesNotExist(),
					q.criteria("criteria.houseYearTo").doesNotExist()
			);
		}
		
		// =================
		// flatFloorFrom
		// flatFloorTo
		logger.info("Realty.data.flatFloor: {}", realty.data.flatFloor);
		if (realty.data.flatFloor != null && realty.data.flatFloor > 0) {
			logger.info("Realty.data.flatFloor search with flatFloor");
			q.and(
				q.or(
					q.criteria("criteria.flatFloorFrom").doesNotExist(),
					q.criteria("criteria.flatFloorFrom").lessThanOrEq(realty.data.flatFloor)
					),
				q.or(
					q.criteria("criteria.flatFloorTo").doesNotExist(),
					q.criteria("criteria.flatFloorTo").greaterThanOrEq(realty.data.flatFloor)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.flatFloor search without flatFloor");
			q.and(
				q.criteria("criteria.flatFloorFrom").doesNotExist(),
				q.criteria("criteria.flatFloorTo").doesNotExist()
			);
		}
				
		// =================
		// houseFloorCountFrom
		// houseFloorCountTo
		logger.info("Realty.data.houseFloorCount: {}", realty.data.houseFloorCount);
		if (realty.data.houseFloorCount != null && realty.data.houseFloorCount > 0) {
			logger.info("Realty.data.houseFloorCount search with houseFloorCount");
			q.and(
				q.or(
					q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
					q.criteria("criteria.houseFloorCountFrom").lessThanOrEq(realty.data.houseFloorCount)
				),
				q.or(
					q.criteria("criteria.houseFloorCountTo").doesNotExist(),
					q.criteria("criteria.houseFloorCountTo").greaterThanOrEq(realty.data.houseFloorCount)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.houseFloorCount search without houseFloorCount");
			q.and(
				q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
				q.criteria("criteria.houseFloorCountTo").doesNotExist()
			);
		}

		// =================
		// squareLivingFrom
		// squareLivingTo
		logger.info("Realty.data.squareLiving: {}", realty.data.squareLiving);
		if (realty.data.squareLiving != null && realty.data.squareLiving > 0) {
			logger.info("Realty.data.squareLiving search with squareLiving");
			q.and(
				q.or(
					q.criteria("criteria.squareLivingFrom").doesNotExist(),
					q.criteria("criteria.squareLivingFrom").lessThanOrEq(realty.data.squareLiving)
				),
				q.or(
					q.criteria("criteria.squareLivingTo").doesNotExist(),
					q.criteria("criteria.squareLivingTo").greaterThanOrEq(realty.data.squareLiving)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.squareLiving search without squareLiving");
			q.and(
				q.criteria("criteria.squareLivingFrom").doesNotExist(),
				q.criteria("criteria.squareLivingTo").doesNotExist()
			);
		}
				
		// =================
		// squareKitchenFrom
		// squareKitchenTo
		logger.info("Realty.data.squareKitchen: {}", realty.data.squareKitchen);
		if (realty.data.squareKitchen != null && realty.data.squareKitchen > 0) {
			logger.info("Realty.data.squareKitchen search with squareKitchen");
			q.and(
				q.or(
					q.criteria("criteria.squareKitchenFrom").doesNotExist(),
					q.criteria("criteria.squareKitchenFrom").lessThanOrEq(realty.data.squareKitchen)
				),
				q.or(
					q.criteria("criteria.squareKitchenTo").doesNotExist(),
					q.criteria("criteria.squareKitchenTo").greaterThanOrEq(realty.data.squareKitchen)
				)
			);
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.squareKitchen search without squareKitchen");
			q.and(
				q.criteria("criteria.squareKitchenFrom").doesNotExist(),
				q.criteria("criteria.squareKitchenTo").doesNotExist()
			);
		}
				
		// =================
		// ceilingHeightFrom
		// ceilingHeightTo
		logger.info("Realty.data.ceilingHeight: {}", realty.data.ceilingHeight);
		if (realty.data.ceilingHeight != null && realty.data.ceilingHeight > 0) {
			logger.info("Realty.data.ceilingHeight search with ceilingHeight");
			q.and(
				q.or(
					q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
					q.criteria("criteria.ceilingHeightFrom").lessThanOrEq(realty.data.ceilingHeight)
				),
				q.or(
					q.criteria("criteria.ceilingHeightTo").doesNotExist(),
					q.criteria("criteria.ceilingHeightTo").greaterThanOrEq(realty.data.ceilingHeight)
				)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			logger.info("Realty.data.ceilingHeight search without ceilingHeight");
			q.and(
				q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
				q.criteria("criteria.ceilingHeightTo").doesNotExist()
			);
		}
		
		// ====================
		// flatBuildingTypes;
		logger.info("Realty.data.flatBuildingType: {}", realty.data.flatBuildingType);
		if (realty.data.flatBuildingType != null && realty.data.flatBuildingType != FlatBuildingType.UNDEFINED) {
			logger.info("Realty.data.flatBuildingType search without flatBuildingType");
			q.and(
				q.criteria("criteria.flatBuildingTypes").doesNotExist()
			);
			
		} else {
			logger.info("Realty.data.flatBuildingType search with flatBuildingType");
			q.and(
				q.or(
					q.criteria("criteria.flatBuildingTypes").doesNotExist(),
					q.criteria("criteria.flatBuildingTypes").hasAnyOf(Arrays.asList(realty.data.flatBuildingType))
				)
			);
		}
		
		// =================
		// privatizedDormTypes
		logger.info("Realty.data.privatizedDormType: {}", realty.data.privatizedDormType);
		if (realty.data.privatizedDormType == null || realty.data.privatizedDormType == FlatPrivatizedDormType.UNDEFINED) {
			logger.info("Realty.data.privatizedDormType search without privatizedDormType");
			q.and(
				q.criteria("criteria.privatizedDormTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.privatizedDormType search with privatizedDormType");
			q.and(
				q.or(
					q.criteria("criteria.privatizedDormTypes").doesNotExist(),
					q.criteria("criteria.privatizedDormTypes").hasAnyOf(Arrays.asList(realty.data.privatizedDormType))
				)
			);
		}
		
		// =================
		// renovationTypes
		logger.info("Realty.data.renovationType: {}", realty.data.renovationType);
		if (realty.data.renovationType == null || realty.data.renovationType == FlatRenovationType.UNDEFINED) {
			logger.info("Realty.data.renovationType search without renovationType");
			q.and(
				q.criteria("criteria.renovationTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.renovationType search with renovationType");
			q.and(
				q.or(
					q.criteria("criteria.renovationTypes").doesNotExist(),
					q.criteria("criteria.renovationTypes").hasAnyOf(Arrays.asList(realty.data.renovationType))
				)
			);
		}
		
		// =================
		// phoneTypes
		logger.info("Realty.data.phoneType: {}", realty.data.phoneType);
		if (realty.data.phoneType == null || realty.data.phoneType == FlatPhoneType.UNDEFINED) {
			logger.info("Realty.data.phoneType search without phoneType");
			q.and(
				q.criteria("criteria.phoneTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.phoneType search with phoneType");
			q.and(
				q.or(
					q.criteria("criteria.phoneTypes").doesNotExist(),
					q.criteria("criteria.phoneTypes").hasAnyOf(Arrays.asList(realty.data.phoneType))
				)
			);
		}
		

		// =================
		// internetTypes
		logger.info("Realty.data.internetType: {}", realty.data.internetType);
		if (realty.data.internetType == null || realty.data.internetType == FlatInternetType.UNDEFINED) {
			logger.info("Realty.data.internetType search without internetType");
			q.and(
				q.criteria("criteria.internetTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.internetType search with internetType");
			q.and(
				q.or(
					q.criteria("criteria.internetTypes").doesNotExist(),
					q.criteria("criteria.internetTypes").hasAnyOf(Arrays.asList(realty.data.internetType))					
				)
			);
		}
		
		// =================
		// lavatoryTypes
		logger.info("Realty.data.lavatoryType: {}", realty.data.lavatoryType);
		if (realty.data.lavatoryType == null || realty.data.lavatoryType == FlatLavatoryType.UNDEFINED) {
			logger.info("Realty.data.lavatoryType search without lavatoryType");
			q.and(
				q.criteria("criteria.lavatoryTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.lavatoryType search with lavatoryType");
			q.and(
				q.or(
					q.criteria("criteria.lavatoryTypes").doesNotExist(),
					q.criteria("criteria.lavatoryTypes").hasAnyOf(Arrays.asList(realty.data.lavatoryType))		
				)
			);
		}
		
		// =================
		// balconyTypes
		logger.info("Realty.data.balconyType: {}", realty.data.balconyType);
		if (realty.data.balconyType == null || realty.data.balconyType == FlatBalconyType.UNDEFINED) {
			logger.info("Realty.data.balconyType search without balconyType");
			q.and(
					q.criteria("criteria.balconyTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.balconyType search with balconyType");
			q.and(
				q.or(
					q.criteria("criteria.balconyTypes").doesNotExist(),
					q.criteria("criteria.balconyTypes").hasAnyOf(Arrays.asList(realty.data.balconyType))
				)
			);
		}
		
		// =================
		// balconyGlazingTypes
		logger.info("Realty.data.balconyGlazingType: {}", realty.data.balconyGlazingType);
		if (realty.data.balconyGlazingType == null || realty.data.balconyGlazingType == FlatBalconyGlazingType.UNDEFINED) {
			logger.info("Realty.data.balconyGlazingType search without balconyGlazingType");
			q.and(
				q.criteria("criteria.balconyGlazingTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.balconyGlazingType search with balconyGlazingType");
			q.and(
				q.or(
					q.criteria("criteria.balconyGlazingTypes").doesNotExist(),
					q.criteria("criteria.balconyGlazingTypes").hasAnyOf(Arrays.asList(realty.data.balconyGlazingType))
				)
			);
		}
		
		// =================
		// doorTypes
		logger.info("Realty.data.doorType: {}", realty.data.doorType);
		if (realty.data.doorType == null || realty.data.doorType == FlatDoorType.UNDEFINED) {
			logger.info("Realty.data.doorType search without doorType");
			q.and(
				q.criteria("criteria.doorTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.doorType search with doorType");
			q.and(
				q.or(
					q.criteria("criteria.doorTypes").doesNotExist(),
					q.criteria("criteria.doorTypes").hasAnyOf(Arrays.asList(realty.data.doorType))	
				)
			);
		}
		
		// =================
		// parkingTypes
		logger.info("Realty.data.parkingType: {}", realty.data.parkingType);
		if (realty.data.parkingType == null || realty.data.parkingType == FlatParkingType.UNDEFINED) {
			logger.info("Realty.data.parkingType search without parkingType");
			q.and(
				q.criteria("criteria.parkingTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.parkingType search with parkingType");
			q.and(
				q.or(
					q.criteria("criteria.parkingTypes").doesNotExist(),
					q.criteria("criteria.parkingTypes").hasAnyOf(Arrays.asList(realty.data.parkingType))
				)
			);
		}
		
		// =================
		// furnitureTypes
		logger.info("Realty.data.furnitureType: {}", realty.data.furnitureType);
		if (realty.data.furnitureType == null || realty.data.furnitureType == FlatFurnitureType.UNDEFINED) {
			logger.info("Realty.data.furnitureType search without furnitureType");
			q.and(
				q.criteria("criteria.furnitureTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.furnitureType search with furnitureType");
			q.and(
				q.or(
					q.criteria("criteria.furnitureTypes").doesNotExist(),
					q.criteria("criteria.furnitureTypes").hasAnyOf(Arrays.asList(realty.data.furnitureType))
				)
			);
		}
		
		// =================
		// floorTypes
		logger.info("Realty.data.floorType: {}", realty.data.floorType);
		if (realty.data.floorType == null || realty.data.floorType == FlatFloorType.UNDEFINED) {
			logger.info("Realty.data.floorType search without floorType");
			q.and(
				q.criteria("criteria.floorTypes").doesNotExist()
			);
		} else {
			logger.info("Realty.data.floorType search with floorType");
			q.and(
				q.or(
					q.criteria("criteria.floorTypes").doesNotExist(),
					q.criteria("criteria.floorTypes").hasAnyOf(Arrays.asList(realty.data.floorType))				
				)
			);
		}
		*/
		/*
		// =================
		// securityTypes !!!!!! RECHECK
		if (realty.data.securityTypes == null || realty.data.securityTypes.contains(FlatSecurityType.UNDEFINED)) {
			q.or(
					q.criteria("criteria.securityTypes").doesNotExist(),
					q.criteria("criteria.securityTypes").hasThisOne(FlatSecurityType.UNDEFINED)
			);
		}
		
		if (realty.data.securityTypes != null && !realty.data.securityTypes.contains(FlatSecurityType.UNDEFINED)) {
			q.field("criteria.securityTypes").hasAnyOf(realty.data.securityTypes); // ERROR HERE!
		}
		
		// =================
		// miscellaneous !!!!!! RECHECK
		if (realty.data.miscellaneous == null || realty.data.miscellaneous.contains(FlatMiscellaneousType.UNDEFINED)) {
			q.or(
					q.criteria("criteria.miscellaneous").doesNotExist(),
					q.criteria("criteria.miscellaneous").hasThisOne(FlatMiscellaneousType.UNDEFINED)
			);
		}
		
		if (realty.data.miscellaneous != null && !realty.data.miscellaneous.contains(FlatMiscellaneousType.UNDEFINED)) {
			q.field("criteria.miscellaneous").hasAnyOf(realty.data.miscellaneous); // ERROR HERE!
		}
		
		// =================
		// rentPeriods !!!!!! RECHECK
		if (realty.data.rentPeriod == null || realty.data.rentPeriod == FlatRentPeriodType.UNDEFINED) {
			q.or(
					q.criteria("criteria.rentPeriods").doesNotExist(),
					q.criteria("criteria.rentPeriods").hasThisOne(FlatRentPeriodType.UNDEFINED)
			);
		}
		
		if (realty.data.rentPeriod != null && realty.data.rentPeriod != FlatRentPeriodType.UNDEFINED) {
			q.field("criteria.rentPeriods").hasThisElement(realty.data.rentPeriod);
		}
				
		// =================
		// miscellaneous !!!!!! RECHECK
		if (realty.data.rentMiscellaneous == null || realty.data.rentMiscellaneous.size() == 0) {
			q.field("criteria.rentMiscellaneous").doesNotExist();
		}
		
		if (realty.data.rentMiscellaneous != null && realty.data.rentMiscellaneous.size() != 0) {
			q.field("criteria.rentMiscellaneous").hasAnyOf(realty.data.rentMiscellaneous); // ERROR HERE!
		}
		
		
		List<Subscription> result = q.asList();
		return result;
		*/
	}
	
	
}
