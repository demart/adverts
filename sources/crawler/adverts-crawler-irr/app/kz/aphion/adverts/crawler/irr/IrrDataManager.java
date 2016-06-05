package kz.aphion.adverts.crawler.irr;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.irr.entity.IrrRegionEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.db.RegionEntity;
import play.Logger;
import play.db.jpa.JPA;

/**
 * Класс для работы с сущностями БД
 * @author artem.demidovich and denis.krylov
 *
 */

public class IrrDataManager {

    /**
     * Метод возвращает регион по полученному названию
     * @param regionName
     * @return
     */
	public static IrrRegionEntity getIrrRegion(String regionName) {
		List<IrrRegionEntity> results = (List<IrrRegionEntity>)JPA.em().createQuery("from IrrRegionEntity where key = :key")
				.setMaxResults(1)
				.setParameter("key", regionName)
				.getResultList();
		if (results.size() > 0) {
			return results.get(0);
		} else {
			Logger.error("Requested region with name [" + regionName + "] not found.");
			return null;
		}
			
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
	 * Метод возвращает объект Region по указанному ID в объявлении на крыше
	 * @param irrRegionId
	 * @return
	 */
	public static Region getRegion(RegionEntity irrRegion) {
		if (irrRegion == null)
			return null;
		
		Region region = new Region();
		region.code = irrRegion.id.toString();
		region.level = irrRegion.level;
		region.mapLatitude = irrRegion.mapLatitude;
		region.mapLongitude = irrRegion.mapLongitude;
		region.mapZoom = irrRegion.mapZoom;
		region.name = irrRegion.name;
		region.type = irrRegion.type;
		
		return region;
	}
}
