package kz.aphion.adverts.crawler.krisha;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.krisha.entity.KrishaRegionEntity;
import kz.aphion.adverts.crawler.krisha.entity.KrishaResidentalComplexEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.db.RegionEntity;
import play.Logger;
import play.db.jpa.JPA;

/**
 * Класс для работы с сущностями БД
 * @author artem.demidovich
 *
 */
public class KrishaDataManager {

	/**
	 * Метод возвращает регион по полученному ID 
	 * @param id
	 * @return
	 */
	public static KrishaRegionEntity getKrishaRegion(String region) {
		Long regionId = Long.valueOf(region);
		List<KrishaRegionEntity> results = (List<KrishaRegionEntity>)JPA.em().createQuery("from KrishaRegionEntity where key = :key")
				.setMaxResults(1)
				.setParameter("key", regionId)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			Logger.error("Requested region with id [" + region + "] not found.");
			return null;
		}
			
	}
	
	/**
	 * Метод возвращает объект Region по указанному ID в объявлении на крыше
	 * @param krishaRegionId
	 * @return
	 */
	public static Region getRegion(RegionEntity krishaRegion) {
		if (krishaRegion == null)
			return null;
		
		Region region = new Region();
		region.code = krishaRegion.id.toString();
		region.level = krishaRegion.level;
		region.mapLatitude = krishaRegion.mapLatitude;
		region.mapLongitude = krishaRegion.mapLongitude;
		region.mapZoom = krishaRegion.mapZoom;
		region.name = krishaRegion.name;
		region.type = krishaRegion.type;
		
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
	public static List<Region> getRegionsTree(RegionEntity regionEntity) {
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
	private static List<Region> getRegionsRecursive(List<Region> regions, RegionEntity regionEntity) {
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
	public static KrishaResidentalComplexEntity getResidentalComplex(String complex) {
		List<KrishaResidentalComplexEntity> results = (List<KrishaResidentalComplexEntity>)JPA.em().createQuery("from KrishaResidentalComplexEntity where key = :key")
				.setMaxResults(1)
				.setParameter("key", complex)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			Logger.error("Requested residental complex with id [" + complex + "] not found.");
			return null;
		}
	}
	
}
