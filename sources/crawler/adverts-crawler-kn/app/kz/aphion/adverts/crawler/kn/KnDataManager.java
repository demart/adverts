package kz.aphion.adverts.crawler.kn;

import java.util.ArrayList;
import java.util.List;

import kz.aphion.adverts.crawler.kn.entity.KnRegionEntity;
import kz.aphion.adverts.crawler.kn.entity.KnResidentalComplexEntity;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.db.RegionEntity;
import play.Logger;
import play.db.jpa.JPA;

/**
 * Класс для работы с сущностями БД
 * @author artem.demidovich and denis.krylov
 *
 */

public class KnDataManager {

    /**
     * Метод возвращает регион по полученному названию
     * @param regionName
     * @return
     */
	public static KnRegionEntity getKnRegion(String regionName) {
		List<KnRegionEntity> results = (List<KnRegionEntity>)JPA.em().createQuery("from KnRegionEntity where name = :key")
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
	 * @param krishaRegionId
	 * @return
	 */
	public static Region getRegion(RegionEntity knRegion) {
		if (knRegion == null)
			return null;
		
		Region region = new Region();
		region.code = knRegion.id.toString();
		region.level = knRegion.level;
		region.mapLatitude = knRegion.mapLatitude;
		region.mapLongitude = knRegion.mapLongitude;
		region.mapZoom = knRegion.mapZoom;
		region.name = knRegion.name;
		region.type = knRegion.type;
		
		return region;
	}
	
	/**
	 * Метод возвращает ЖК по полченному названию и полученному названию Региона
	 * @param complexName
	 * @return
	 */
	public static KnResidentalComplexEntity getResidentalComplex(String complexName, String regionName) {
		List<KnResidentalComplexEntity> results = (List<KnResidentalComplexEntity>)JPA.em().createQuery("from KnResidentalComplexEntity where name = :key")
				.setParameter("key", complexName)
				.getResultList();
		
		if (regionName.equals("Сарырка"))
			regionName = "Сарыаркинский";
		if (regionName.equals("Алматы"))
			regionName = "Алматинский";
		if (regionName.equals("Есиль"))
			regionName = "Есильский";
		
		String region = regionName + " р-н";
		
		//Тут есть проблемы. Некоторые люди указывают район и ЖК, которые находятся в разных районах
		//И не пойми либо случайно ткнули не на то район или же ЖК.
		//Поэтому коль непонятно где ошиблись, ЖК все равно будет мапится.
		if (results.size() > 0) {
			if (results.size() == 1)
				return results.get(0);
			
			else if (results.size() > 1) {
				for (int i = 0; i < results.size(); i++) {
					if (results.get(i).complex.region.name.equals(regionName)) {
						return results.get(i);
					}
					else if (results.get(i).complex.region.name.equals(region)) {
						return results.get(i);
					}
					
					else {
						Logger.error("Requested residental complex with name [" + complexName + "] not found in SELECTED REGION [" + regionName + "].");
						
					}
	
				  }
			}
			
			} 
		else {
			Logger.error("Requested residental complex with name [" + complexName + "] not found.");
			return null;
		}
		
		return null;
	}
	
}
