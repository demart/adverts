package kz.aphion.adverts.subscription.searcher.impl;

import java.util.List;

import kz.aphion.adverts.persistence.BaseEntity;
import kz.aphion.adverts.persistence.realty.data.flat.FlatSellRealty;
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


/**
 * Класс отвечает за поиск дубликатов объявлений
 * @author artem.demidovich
 *
 * Created at Jun 12, 2016
 */
public class FlatSellSubscriptionSearcher implements SubscriptionSearcher {

	private static Logger logger = LoggerFactory.getLogger(RealtyAdvertSubscriptionProcessor.class);
	
	
	private ObjectId advertId;
	
	private FlatSellRealty realty;
	
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
			
			realty = ds.get(FlatSellRealty.class, advertId);
			if (realty == null) {
				logger.warn("Object with provided Id [" + advertId + "] not found. Possibly already exists newer version.");
				return null;
			}
			
			List<Subscription> result = search(ds, realty);
			if (result != null)
				logger.debug("Found Flat Sell [" + result.size() +  "] subscriptions");
			
			return result;
			
		} catch (Exception ex) {
			logger.error("Error in searching flat sell subscriptions", ex);
			return null;
		}
	}
	
	private List<Subscription> search(Datastore ds, FlatSellRealty realty) {
		Query<Subscription> q = FlatSubscriptionSearcherQueryBuilder.buidQuery(ds, realty);
		
		List<Subscription> result = q.asList();
		return result;
		
		/*
		q.disableValidation();
		
		// Subscription Base Criteria
		q.field("advertType").equal(SubscriptionAdvertType.REALTY);
		q.field("startedAt").lessThan(Calendar.getInstance());
		q.field("expiresAt").greaterThan(Calendar.getInstance());
		q.field("status").equal(SubscriptionStatus.ACTIVE);
		 		
		// Realty Base Fields
		q.field("criteria.type").equal(RealtyType.FLAT);
		q.field("criteria.operation").equal(RealtyOperationType.SELL);
		
		
		q.and(
				q.criteria("advertType").equal(SubscriptionAdvertType.REALTY),
				q.criteria("startedAt").lessThan(Calendar.getInstance()),
				q.criteria("expiresAt").greaterThan(Calendar.getInstance()),
				q.criteria("status").equal(SubscriptionStatus.ACTIVE),
				 		
				// Realty Base Fields
				q.criteria("criteria.type").equal(RealtyType.FLAT),
				q.criteria("criteria.operation").equal(RealtyOperationType.SELL)		
		);
		
		
		q.and(
			// =====
			// price from
			q.or(
					q.criteria("criteria.priceFrom").doesNotExist(),
					q.criteria("criteria.priceFrom").lessThanOrEq(realty.price)
				),
			
			// =====
			// price to
			q.or(
					q.criteria("criteria.priceTo").doesNotExist(),
					q.criteria("criteria.priceTo").greaterThanOrEq(realty.price)
				)
		);
		
		
		// =====
		// photo
		if (realty.hasPhoto == null || realty.hasPhoto == false) {
			q.and(q.or(
					q.criteria("criteria.hasPhoto").doesNotExist(),
					q.criteria("criteria.hasPhoto").equal(realty.hasPhoto)
			));
		}
		if (realty.hasPhoto != null && realty.hasPhoto) {
			q.and(
					q.criteria("criteria.hasPhoto").equal(realty.hasPhoto)
			);
		}
		
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
			// squareFrom
			q.and(
				q.or(
						q.criteria("criteria.squareFrom").doesNotExist(),
						q.criteria("criteria.squareFrom").lessThanOrEq(realty.data.square)
				),
				
				// squareTo
				q.or(
						q.criteria("criteria.squareTo").doesNotExist(),
						q.criteria("criteria.squareTo").greaterThanOrEq(realty.data.square)
				)
			);
		}

		// =====
		//q.field("criteria.publisherTypes").equal(arg0)
		if (realty.publisher == null || realty.publisher.publisherType == RealtyPublisherType.UNDEFINED) {
			q.and(q.or(
					q.criteria("criteria.publisherTypes").doesNotExist(),
					q.criteria("criteria.publisherTypes").hasThisOne(RealtyPublisherType.UNDEFINED)
			));
		}
		
		if (realty.publisher != null && realty.publisher.publisherType != RealtyPublisherType.UNDEFINED) {
			q.and(q.criteria("criteria.publisherTypes").hasThisElement(realty.publisher.publisherType));
		}
		
		
		// =====
		//q.field("criteria.location").equal(arg0)
		
		if (realty.location == null) {
			q.and(
					q.criteria("criteria.location").doesNotExist()
			);
		} else {
			// Если есть расположения объекта
			if (realty.location.regions != null && realty.location.regions.size() > 0) {
			
				List<String> regionCodes = new ArrayList<String>();
				for (Region region : realty.location.regions) {
					if (region.code != null) {
						regionCodes.add(region.code);
						logger.warn("Found Region.code: " + region.code);
					} else {
						logger.warn("Found Region.code with null code value");	
					}
				}
				
				q.and(
						q.or(
								q.criteria("criteria.location").doesNotExist(),
								q.criteria("criteria.location.regions.code").hasAnyOf(regionCodes)	
						)
				);
				
			} else {
				// Если в объявлении регионов нету, то остается только искать подписки без регионов (т.е. ВСЕ)
				q.and(
						q.criteria("criteria.location").doesNotExist()
				);
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
			q.and(
					q.or(
							q.criteria("criteria.residentalComplexs").doesNotExist(),
							q.criteria("criteria.residentalComplexs.relationId").hasThisElement(realty.data.residentalComplex.relationId)
					)
			);
		} else {
			// Если нету ЖК или не смогли распарсить в краулере, тогда смотрим подписки где нету ЖК
			q.and(
					q.criteria("criteria.residentalComplexs").doesNotExist()
			);
		}
		
		// ====================
		// roomFrom;
		if (realty.data.rooms != null && realty.data.rooms > 0) {
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
			q.and(
					q.criteria("criteria.roomFrom").doesNotExist(),
					q.criteria("criteria.roomTo").doesNotExist()
			);
		}
		/*
		// ====================
		// flatBuildingTypes;
		
		if (realty.data.flatBuildingType != null && realty.data.flatBuildingType != FlatBuildingType.UNDEFINED) {
			q.or(
					q.criteria("criteria.flatBuildingTypes").doesNotExist(),
					q.criteria("criteria.flatBuildingTypes").hasThisElement(realty.data.flatBuildingType));
		} else {
			q.field("criteria.flatBuildingTypes").doesNotExist();
		}
		
		// ====================
		// houseYearFrom;
		// houseYearTo
		if (realty.data.houseYear != null && realty.data.houseYear > 0) {
			// roomsFrom
			q.or(
					q.criteria("criteria.houseYearFrom").doesNotExist(),
					q.criteria("criteria.houseYearFrom").lessThanOrEq(realty.data.houseYear)
			);
			
			// roomsTo
			q.or(
					q.criteria("criteria.houseYearTo").doesNotExist(),
					q.criteria("criteria.houseYearTo").greaterThanOrEq(realty.data.houseYear)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			q.and(
					q.criteria("criteria.houseYearFrom").doesNotExist(),
					q.criteria("criteria.houseYearTo").doesNotExist()
			);
		}
		
		// =================
		// flatFloorFrom
		// flatFloorTo
		if (realty.data.flatFloor != null && realty.data.flatFloor > 0) {
			// roomsFrom
			q.or(
					q.criteria("criteria.flatFloorFrom").doesNotExist(),
					q.criteria("criteria.flatFloorFrom").lessThanOrEq(realty.data.flatFloor)
			);
			
			// roomsTo
			q.or(
					q.criteria("criteria.flatFloorTo").doesNotExist(),
					q.criteria("criteria.flatFloorTo").greaterThanOrEq(realty.data.flatFloor)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			q.and(
					q.criteria("criteria.flatFloorFrom").doesNotExist(),
					q.criteria("criteria.flatFloorTo").doesNotExist()
			);
		}
		
		// =================
		// houseFloorCountFrom
		// houseFloorCountTo
		if (realty.data.houseFloorCount != null && realty.data.houseFloorCount > 0) {
			// roomsFrom
			q.or(
					q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
					q.criteria("criteria.houseFloorCountFrom").lessThanOrEq(realty.data.houseFloorCount)
			);
			
			// roomsTo
			q.or(
					q.criteria("criteria.houseFloorCountTo").doesNotExist(),
					q.criteria("criteria.houseFloorCountTo").greaterThanOrEq(realty.data.houseFloorCount)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			q.and(
					q.criteria("criteria.houseFloorCountFrom").doesNotExist(),
					q.criteria("criteria.houseFloorCountTo").doesNotExist()
			);
		}
				
		// =================
		// squareLivingFrom
		// squareLivingTo
		if (realty.data.squareLiving != null && realty.data.squareLiving > 0) {
			// roomsFrom
			q.or(
					q.criteria("criteria.squareLivingFrom").doesNotExist(),
					q.criteria("criteria.squareLivingFrom").lessThanOrEq(realty.data.squareLiving)
			);
			
			// roomsTo
			q.or(
					q.criteria("criteria.squareLivingTo").doesNotExist(),
					q.criteria("criteria.squareLivingTo").greaterThanOrEq(realty.data.squareLiving)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			q.and(
					q.criteria("criteria.squareLivingFrom").doesNotExist(),
					q.criteria("criteria.squareLivingTo").doesNotExist()
			);
		}
		
		// =================
		// squareKitchenFrom
		// squareKitchenTo
		if (realty.data.squareKitchen != null && realty.data.squareKitchen > 0) {
			// roomsFrom
			q.or(
					q.criteria("criteria.squareKitchenFrom").doesNotExist(),
					q.criteria("criteria.squareKitchenFrom").lessThanOrEq(realty.data.squareKitchen)
			);
			
			// roomsTo
			q.or(
					q.criteria("criteria.squareKitchenTo").doesNotExist(),
					q.criteria("criteria.squareKitchenTo").greaterThanOrEq(realty.data.squareKitchen)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			q.and(
					q.criteria("criteria.squareKitchenFrom").doesNotExist(),
					q.criteria("criteria.squareKitchenTo").doesNotExist()
			);
		}
		
		// =================
		// ceilingHeightFrom
		// ceilingHeightTo
		if (realty.data.ceilingHeight != null && realty.data.ceilingHeight > 0) {
			// roomsFrom
			q.or(
					q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
					q.criteria("criteria.ceilingHeightFrom").lessThanOrEq(realty.data.ceilingHeight)
			);
			
			// roomsTo
			q.or(
					q.criteria("criteria.ceilingHeightTo").doesNotExist(),
					q.criteria("criteria.ceilingHeightTo").greaterThanOrEq(realty.data.ceilingHeight)
			);
			
		} else {
			// Если нету информации о комнатах, то ищем подписки там где это никак не задавали
			q.and(
					q.criteria("criteria.ceilingHeightFrom").doesNotExist(),
					q.criteria("criteria.ceilingHeightTo").doesNotExist()
			);
		}
		
		// =================
		// privatizedDormTypes
		if (realty.data.privatizedDormType == null || realty.data.privatizedDormType == FlatPrivatizedDormType.UNDEFINED) {
			q.or(
					q.criteria("criteria.privatizedDormTypes").doesNotExist(),
					q.criteria("criteria.privatizedDormTypes").hasThisOne(FlatPrivatizedDormType.UNDEFINED)
			);
		}
		
		if (realty.data.privatizedDormType != null && realty.data.privatizedDormType != FlatPrivatizedDormType.UNDEFINED) {
			q.field("criteria.privatizedDormTypes").hasThisElement(realty.data.privatizedDormType);
		}
		
		// =================
		// renovationTypes
		if (realty.data.renovationType == null || realty.data.renovationType == FlatRenovationType.UNDEFINED) {
			q.or(
					q.criteria("criteria.renovationTypes").doesNotExist(),
					q.criteria("criteria.renovationTypes").hasThisOne(FlatRenovationType.UNDEFINED)
			);
		}
		
		if (realty.data.renovationType != null && realty.data.renovationType != FlatRenovationType.UNDEFINED) {
			q.field("criteria.renovationTypes").hasThisElement(realty.data.renovationType);
		}
		
		// =================
		// phoneTypes
		if (realty.data.phoneType == null || realty.data.phoneType == FlatPhoneType.UNDEFINED) {
			q.or(
					q.criteria("criteria.phoneTypes").doesNotExist(),
					q.criteria("criteria.phoneTypes").hasThisOne(FlatPhoneType.UNDEFINED)
			);
		}
		
		if (realty.data.phoneType != null && realty.data.phoneType != FlatPhoneType.UNDEFINED) {
			q.field("criteria.phoneTypes").hasThisElement(realty.data.phoneType);
		}
		
		// =================
		// internetTypes
		if (realty.data.internetType == null || realty.data.internetType == FlatInternetType.UNDEFINED) {
			q.or(
					q.criteria("criteria.internetTypes").doesNotExist(),
					q.criteria("criteria.internetTypes").hasThisOne(FlatInternetType.UNDEFINED)
			);
		}
		
		if (realty.data.internetType != null && realty.data.internetType != FlatInternetType.UNDEFINED) {
			q.field("criteria.internetTypes").hasThisElement(realty.data.internetType);
		}
		
		// =================
		// lavatoryTypes
		if (realty.data.lavatoryType == null || realty.data.lavatoryType == FlatLavatoryType.UNDEFINED) {
			q.or(
					q.criteria("criteria.lavatoryTypes").doesNotExist(),
					q.criteria("criteria.lavatoryTypes").hasThisOne(FlatLavatoryType.UNDEFINED)
			);
		}
		
		if (realty.data.lavatoryType != null && realty.data.lavatoryType != FlatLavatoryType.UNDEFINED) {
			q.field("criteria.lavatoryTypes").hasThisElement(realty.data.lavatoryType);
		}
		
		// =================
		// balconyTypes
		if (realty.data.balconyType == null || realty.data.balconyType == FlatBalconyType.UNDEFINED) {
			q.or(
					q.criteria("criteria.balconyTypes").doesNotExist(),
					q.criteria("criteria.balconyTypes").hasThisOne(FlatBalconyType.UNDEFINED)
			);
		}
		
		if (realty.data.balconyType != null && realty.data.balconyType != FlatBalconyType.UNDEFINED) {
			q.field("criteria.balconyTypes").hasThisElement(realty.data.balconyType);
		}
		
		// =================
		// balconyGlazingTypes
		if (realty.data.balconyGlazingType == null || realty.data.balconyGlazingType == FlatBalconyGlazingType.UNDEFINED) {
			q.or(
					q.criteria("criteria.balconyGlazingTypes").doesNotExist(),
					q.criteria("criteria.balconyGlazingTypes").hasThisOne(FlatBalconyGlazingType.UNDEFINED)
			);
		}
		
		if (realty.data.balconyGlazingType != null && realty.data.balconyGlazingType != FlatBalconyGlazingType.UNDEFINED) {
			q.field("criteria.balconyGlazingTypes").hasThisElement(realty.data.balconyGlazingType);
		}
		
		// =================
		// doorTypes
		if (realty.data.doorType == null || realty.data.doorType == FlatDoorType.UNDEFINED) {
			q.or(
					q.criteria("criteria.doorTypes").doesNotExist(),
					q.criteria("criteria.doorTypes").hasThisOne(FlatDoorType.UNDEFINED)
			);
		}
		
		if (realty.data.doorType != null && realty.data.doorType != FlatDoorType.UNDEFINED) {
			q.field("criteria.doorTypes").hasThisElement(realty.data.doorType);
		}
		
		// =================
		// parkingTypes
		if (realty.data.parkingType == null || realty.data.parkingType == FlatParkingType.UNDEFINED) {
			q.or(
					q.criteria("criteria.parkingTypes").doesNotExist(),
					q.criteria("criteria.parkingTypes").hasThisOne(FlatParkingType.UNDEFINED)
			);
		}
		
		if (realty.data.parkingType != null && realty.data.parkingType != FlatParkingType.UNDEFINED) {
			q.field("criteria.parkingTypes").hasThisElement(realty.data.parkingType);
		}
		
		// =================
		// furnitureTypes
		if (realty.data.furnitureType == null || realty.data.furnitureType == FlatFurnitureType.UNDEFINED) {
			q.or(
					q.criteria("criteria.furnitureTypes").doesNotExist(),
					q.criteria("criteria.furnitureTypes").hasThisOne(FlatFurnitureType.UNDEFINED)
			);
		}
		
		if (realty.data.furnitureType != null && realty.data.furnitureType != FlatFurnitureType.UNDEFINED) {
			q.field("criteria.furnitureTypes").hasThisElement(realty.data.furnitureType);
		}
		
		// =================
		// floorTypes
		if (realty.data.floorType == null || realty.data.floorType == FlatFloorType.UNDEFINED) {
			q.or(
					q.criteria("criteria.floorTypes").doesNotExist(),
					q.criteria("criteria.floorTypes").hasThisOne(FlatFloorType.UNDEFINED)
			);
		}
		
		if (realty.data.floorType != null && realty.data.floorType != FlatFloorType.UNDEFINED) {
			q.field("criteria.floorTypes").hasThisElement(realty.data.floorType);
		}
		
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
		// mortgageStatuses !!!!!! RECHECK
		if (realty.data.mortgageStatus == null || realty.data.mortgageStatus == MortgageStatus.UNDEFINED) {
			q.or(
					q.criteria("criteria.mortgageStatuses").doesNotExist(),
					q.criteria("criteria.mortgageStatuses").hasThisOne(MortgageStatus.UNDEFINED)
			);
		}
		
		if (realty.data.mortgageStatus != null && realty.data.mortgageStatus != MortgageStatus.UNDEFINED) {
			q.field("criteria.mortgageStatuses").hasThisElement(realty.data.mortgageStatus);
		}
			
		List<Subscription> result = q.asList();
		return result;
		*/
	}
	
	
}
