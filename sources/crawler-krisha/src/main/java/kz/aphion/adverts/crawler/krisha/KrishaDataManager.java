package kz.aphion.adverts.crawler.krisha;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.common.DB;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaRegion;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaResidentialComplex;
import kz.aphion.adverts.persistence.Region;

import org.bson.types.ObjectId;
import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с сущностями БД
 * @author artem.demidovich
 *
 */
public class KrishaDataManager {

	private static Logger logger = LoggerFactory.getLogger(KrishaDataManager.class);

	/**
	 * Метод возвращает регион по полученному ID 
	 * @param id
	 * @return
	 */
	public static KrishaRegion getKrishaRegion(String region) {
		Long regionId = Long.valueOf(region);
		
		/*
		List<KrishaRegionEntity> results = (List<KrishaRegionEntity>)JPA.em().createQuery("from KrishaRegionEntity where key = :key")
				.setMaxResults(1)
				.setParameter("key", regionId)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			logger.error("Requested region with id [" + region + "] not found.");
			return null;
		}
		*/
		
		try {
			Query<KrishaRegion> q = DB.DS().createQuery(KrishaRegion.class);
			q.field("key").equal(regionId);
			q.limit(1);
			List<KrishaRegion> results = q.asList();
			if (results.size() > 0) {
				return results.get(0);
			} else {
				logger.error("Requested region with id [" + region + "] not found.");
				return null;
			}

		} catch (Exception e) {
			logger.error("Error", e);
			return null;
		}
		
			
	}
	
	/**
	 * Метод возвращает объект Region по указанному ID в объявлении на крыше
	 * @param krishaRegionId
	 * @return
	 */
	public static Region getRegion(Region krishaRegion) {
		if (krishaRegion == null)
			return null;
	/*	
	 * Пока уберу так как не понятно что будет сохраняться в БДшке
		Region region = new Region();
		region.code = krishaRegion.id.toString();
		region.level = krishaRegion.level;
		region.mapLatitude = krishaRegion.mapLatitude;
		region.mapLongitude = krishaRegion.mapLongitude;
		region.mapZoom = krishaRegion.mapZoom;
		region.name = krishaRegion.name;
		region.type = krishaRegion.type;
		*/
		return krishaRegion;
	}
	
	/**
	 * Возвращает список всех регионов по иерархии.
	 * 	Казахстан
	 * 		Астана
	 * 			Есильский район
	 * @param regionEntity
	 * @return
	 */
	public static List<Region> getRegionsTree(Region regionEntity) {
		if (regionEntity == null)
			return null;
		List<Region> regions = new ArrayList<Region>();
		
		regions = getRegionsRecursive(regions, regionEntity);
		
		return regions;
	}
	
	/**
	 * Проходит рекурсивно по дереву для того чтобы собрать всех родителей в List
	 * @param regions
	 * @param regionEntity
	 * @return
	 */
	private static List<Region> getRegionsRecursive(List<Region> regions, Region regionEntity) {
		if (regionEntity.parent != null) {
			regions = getRegionsRecursive(regions, regionEntity.parent);
			Region region = getRegion(regionEntity);
			regions.add(region);
		}
		return regions;
	}
	
	
	/**
	 * Возвращает ЖК по указанному внешнему ключу
	 * @param complex
	 * @return
	 */
	public static KrishaResidentialComplex getResidentalComplex(String complex) {
		/*
		List<KrishaResidentalComplexEntity> results = (List<KrishaResidentalComplexEntity>)JPA.em().createQuery("from KrishaResidentalComplexEntity where key = :key")
				.setMaxResults(1)
				.setParameter("key", complex)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			logger.error("Requested residental complex with id [" + complex + "] not found.");
			return null;
		}
		*/
		
		try {
			Query<KrishaResidentialComplex> q = DB.DS().createQuery(KrishaResidentialComplex.class);
			q.field("key").equal(complex);
			q.limit(1);
			List<KrishaResidentialComplex> results = q.asList();
			if (results.size() > 0) {
				return results.get(0);
			} else {
				logger.error("Requested residental complex with id [" + complex + "] not found.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error", e);
			return null;
		}
	}
	
	/**
	 * Возвращает ЖК по названию и региону
	 * @param complex
	 * @return
	 */
	public static KrishaResidentialComplex getResidentalComplex(String complex, ObjectId regionId) {
		/*List<KrishaResidentalComplexEntity> results = (List<KrishaResidentalComplexEntity>)JPA.em()
				.createQuery("from KrishaResidentalComplexEntity where name = :key and region.id = :regionId")
				.setMaxResults(1)
				.setParameter("key", complex)
				.setParameter("regionId", regionId)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			logger.error("Requested residental complex with id [" + complex + "] not found.");
			return null;
		}
		*/
		
		try {
			Query<KrishaResidentialComplex> q = DB.DS().createQuery(KrishaResidentialComplex.class);
			q.field("name").equal(complex);
			q.field("region.id").equal(regionId);
			q.limit(1);
			List<KrishaResidentialComplex> results = q.asList();
			if (results.size() > 0) {
				return results.get(0);
			} else {
				logger.error("Requested residental complex with id [" + complex + "] not found.");
				return null;
			}
		} catch (Exception e) {
			logger.error("Error", e);
			return null;
		}
		
	}	
	
}
