package kz.aphion.adverts.crawler.olx;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.olx.persistence.OlxRegionEntityTable;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.db.RegionEntity;
import play.Logger;
import play.db.jpa.JPA;

/**
 * Класс для работы с сущностями БД
 * @author artem.demidovich
 *
 *
 */
public class OlxDataManager {

	/**
	 * Метод возвращает регион по полученному ID 
	 * @param id
	 * @return
	 */
	public static OlxRegionEntityTable getOlxRegion(String regionName) {
		//regionName = regionName.toLowerCase();
		List<OlxRegionEntityTable> results = (List<OlxRegionEntityTable>)JPA.em().createQuery("from OlxRegionEntity where name = :name")
				.setMaxResults(1)
				.setParameter("name", regionName)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			Logger.error("Requested region with id [" + regionName + "] not found.");
			return null;
		}
			
	}
	
	/**
	 * Метод возвращает объект Region по указанному ID в объявлении на крыше
	 * @param regionEntity
	 * @return
	 */
	public static Region getRegion(RegionEntity regionEntity) {
		if (regionEntity == null)
			return null;
		
		Region region = new Region();
		region.code = regionEntity.id.toString();
		region.level = regionEntity.level;
		region.mapLatitude = regionEntity.mapLatitude;
		region.mapLongitude = regionEntity.mapLongitude;
		region.mapZoom = regionEntity.mapZoom;
		region.name = regionEntity.name;
		region.type = regionEntity.type;
		
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
	
	
}
