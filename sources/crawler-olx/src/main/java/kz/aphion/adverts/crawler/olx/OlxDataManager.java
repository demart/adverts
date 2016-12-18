package kz.aphion.adverts.crawler.olx;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.core.MongoDBProvider;
import kz.aphion.adverts.crawler.olx.persistence.OlxRegion;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.RegionType;

import org.mongodb.morphia.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Класс для работы с сущностями БД
 * @author artem.demidovich
 *
 *
 */
public class OlxDataManager {

	private static Logger logger = LoggerFactory.getLogger(OlxDataManager.class);

	/**
	 * Метод возвращает регион по полученному ID 
	 * @param id
	 * @return
	 */
	@Deprecated
	private static OlxRegion getOlxRegion(String regionName) {
		//regionName = regionName.toLowerCase();
		/*
		 List<OlxRegionEntity> results = (List<OlxRegionEntity>)JPA.em().createQuery("from OlxRegionEntity where name = :name")
		 
				.setMaxResults(1)
				.setParameter("name", regionName)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			Logger.error("Requested region with id [" + regionName + "] not found.");
			return null;
		}
		*/
		try {
			Query<OlxRegion> q = MongoDBProvider.getInstance().getDatastore().createQuery(OlxRegion.class);
			q.field("name").equal(regionName);
			q.limit(1);
			List<OlxRegion> results = q.asList();
			if (results.size() > 0) {
				return results.get(0);
			} else {
				logger.error("Requested region with id [" + regionName + "] not found.");
				return null;
			}

		} catch (Exception e) {
			logger.error("Error", e);
			return null;
		}
	}
	
	public static OlxRegion getOlxRegionByType(RegionType regionType, String districtId) {
		try {
			Query<OlxRegion> q = MongoDBProvider.getInstance().getDatastore().createQuery(OlxRegion.class);
			q.field("key").equal(Long.parseLong(districtId));
			q.field("type").equal(regionType);
			q.limit(1);
			List<OlxRegion> results = q.asList();
			if (results.size() > 0) {
				return results.get(0);
			} else {
				logger.warn("Requested region with id [{}] and type [{}]  not found.", districtId, regionType);
				return null;
			}
		} catch (Exception e) {
			logger.error("Error", e);
			return null;
		}
	}
	
	
	/**
	 * Метод возвращает объект Region по указанному ID в объявлении на крыше
	 * @param regionEntity
	 * @return
	 */
	public static Region getRegion(Region region) {
		if (region == null)
			return null;
		
		/*
		Region region = new Region();
		region.code = regionEntity.id.toString();
		region.level = regionEntity.level;
		region.mapLatitude = regionEntity.mapLatitude;
		region.mapLongitude = regionEntity.mapLongitude;
		region.mapZoom = regionEntity.mapZoom;
		region.name = regionEntity.name;
		region.type = regionEntity.type;
		*/
		return region;
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
	
	
}
