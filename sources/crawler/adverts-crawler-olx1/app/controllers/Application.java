package controllers;

import java.util.Calendar;
import java.util.List;

import kz.aphion.adverts.crawler.core.MongoDBProvider;
import kz.aphion.adverts.crawler.irr.persistence.IrrRegion;
import kz.aphion.adverts.crawler.irr.persistence.IrrRegionEntityTable;
import kz.aphion.adverts.crawler.kn.persistence.KnRegion;
import kz.aphion.adverts.crawler.kn.persistence.KnRegionEntityTable;
import kz.aphion.adverts.crawler.kn.persistence.KnResidentalComplexEntityTable;
import kz.aphion.adverts.crawler.kn.persistence.KnResidentialComplex;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaRegion;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaRegionEntityTable;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaResidentalComplexEntityTable;
import kz.aphion.adverts.crawler.krisha.persistence.KrishaResidentialComplex;
import kz.aphion.adverts.crawler.olx.persistence.OlxRegion;
import kz.aphion.adverts.crawler.olx.persistence.OlxRegionEntityTable;
import kz.aphion.adverts.persistence.Region;
import kz.aphion.adverts.persistence.db.RegionEntity;
import kz.aphion.adverts.persistence.db.ResidentalComplexEntity;
import kz.aphion.adverts.persistence.realty.building.ResidentialComplex;

import org.mongodb.morphia.Datastore;

import play.db.jpa.JPA;
import play.mvc.Controller;

public class Application extends Controller {

    public static void index() {
        render();
    }

    
    public static void importData() throws Exception {
    	// Выгружаем справочник регионов и загружаем в монго
    	System.out.println("Import started");
    	
    	List<RegionEntity> regions = getRegions();
    	System.out.println("Loaded sql regions: " + regions.size());
    	
    	//List<KrishaRegionEntity> krishaRegions = getKrishaRegions();
    	//System.out.println("Loaded sql krisha regions: " + krishaRegions.size());
    	
    	List<KnRegionEntityTable> knRegions = getKnRegions();
    	System.out.println("Loaded sql kn regions: " + knRegions.size());
    	
    	List<OlxRegionEntityTable> olxRegions = getOlxRegions();
    	System.out.println("Loaded sql olx regions: " + olxRegions.size());
    	
    	List<IrrRegionEntityTable> irrRegions = getIrrRegions();
    	System.out.println("Loaded sql irr regions: " + irrRegions.size());
    	
    	List<ResidentalComplexEntity> residentalComplex = getResidentalComplexEntities();
    	System.out.println("Loaded sql residentalComplex: " + residentalComplex.size());
    	
    	List<KnResidentalComplexEntityTable> knResidentalComplex = getKnResidentalComplexEntities();
    	System.out.println("Loaded sql knResidentalComplex: " + knResidentalComplex.size());
    	
    	List<KrishaResidentalComplexEntityTable> krishaResidentalComplex = getKrishaResidentalComplexEntityTables();
    	System.out.println("Loaded sql krishaResidentalComplex: " + krishaResidentalComplex.size());
    	
    	convertRegionEntitiesToRegionRecords(regions);
    	
    	syncKrishaRegions();
    	
    	syncIrrRegions();
    	
    	syncKnRegions();
    	
    	syncOlxRegions();
    	
    	// TODO Sync residentials complexes
    	
    	syncResidentialComplexes();
    	
    	syncKrishaResidentialComplexes();
    	
    	// Clean ResidentialComplex fild External bla bla ba
    	syncKnResidentialComplexes();
    }
    
    private static void syncKnResidentialComplexes() throws Exception {
    	List<KnResidentalComplexEntityTable> residentalComplex = getKnResidentalComplexEntities();
    	System.out.println("Loaded sql kn residentalComplex: " + residentalComplex.size());
    	
    	for (KnResidentalComplexEntityTable rce : residentalComplex) {
    		KnResidentialComplex rc = new KnResidentialComplex();
			rc.key = rce.key;
    		rc.name = rce.name;
			rc.created = Calendar.getInstance();
			rc.modifier = "artem.demidovich";
			rc.region = getRegionByCode(rce.complex.region.id.toString());
			rc.updated = Calendar.getInstance();
			rc.complex = getResidentialComplexByCode(rce.complex.id.toString());
			
			MongoDBProvider.getInstance().getDatastore().save(rc);

		}
    	
    }
    
    private static void syncResidentialComplexes() throws Exception {
    	List<ResidentalComplexEntity> residentalComplex = getResidentalComplexEntities();
    	System.out.println("Loaded sql residentalComplex: " + residentalComplex.size());
    	
    	for (ResidentalComplexEntity rce : residentalComplex) {
			ResidentialComplex rc = new ResidentialComplex();
			rc.companyName = rce.companyName;
			rc.complexName = rce.complexName;
			rc.created = Calendar.getInstance();
			rc.imageUrl = rce.imageUrl;
			rc.latitude = rce.latitude;
			rc.location = rce.location;
			rc.longitude = rce.longitude;
			rc.modifier = "artem.demidovich";
			rc.region = getRegionByCode(rce.region.id.toString());
			rc.updated = Calendar.getInstance();
			rc.externalComplexId = rce.id.toString(); // to know how to map

			MongoDBProvider.getInstance().getDatastore().save(rc);

		}
    	
    }
    
    private static void syncKrishaResidentialComplexes() throws Exception {
    	List<KrishaResidentalComplexEntityTable> residentalComplex = getKrishaResidentalComplexEntityTables();
    	System.out.println("Loaded sql krisha residentalComplex: " + residentalComplex.size());
    	
    	for (KrishaResidentalComplexEntityTable rce : residentalComplex) {
    		KrishaResidentialComplex rc = new KrishaResidentialComplex();
			rc.key = rce.key;
    		rc.name = rce.name;
			rc.created = Calendar.getInstance();
			rc.modifier = "artem.demidovich";
			rc.region = getRegionByCode(rce.region.id.toString());
			rc.updated = Calendar.getInstance();
			rc.complex = getResidentialComplexByCode(rce.complex.id.toString());
			
			MongoDBProvider.getInstance().getDatastore().save(rc);

		}
    	
    }
    

    private static void convertRegionEntitiesToRegionRecords(List<RegionEntity> regions) throws Exception {
    	List<RegionEntity> rootRegions = getRootRegions();
    	System.out.println("Loaded sql root regions: " + rootRegions.size());
    	
    	for (RegionEntity regionEntity : rootRegions) {
			saveRecursive(null, regionEntity);
		}
    	
    	System.out.println("All regions synchronized");
    	
	}
    
    
    private static void saveRecursive(Region parentRegion, RegionEntity regionEntity) throws Exception {
    	if (regionEntity == null)
    		return;
    	
    	Region region = new Region();
    	region.code = regionEntity.id.toString();
    	region.created = Calendar.getInstance();
    	region.level = regionEntity.level;
    	region.mapLatitude = regionEntity.mapLatitude;
    	region.mapLongitude = regionEntity.mapLongitude;
    	region.mapZoom = regionEntity.mapZoom;
    	region.modifier = "artem.demidovich";
    	region.name = regionEntity.name;
    	region.parent = parentRegion;
    	region.updated = Calendar.getInstance();
    	region.type = regionEntity.type;
    	
    	MongoDBProvider.getInstance().getDatastore().save(region);
    	
    	for (RegionEntity child : regionEntity.regions) {
			saveRecursive(region, child);
		}
    	
    }
    
    private static void syncKrishaRegions() throws Exception {
    	List<KrishaRegionEntityTable> krishaRegions = getKrishaRegions();
    	System.out.println("Loaded sql krisha regions: " + krishaRegions.size());
    
    	Datastore ds = MongoDBProvider.getInstance().getDatastore();
    	
    	for (KrishaRegionEntityTable krishaRegionEntity : krishaRegions) {
    		String code = krishaRegionEntity.region.id.toString();
    		Region mainRegion = getRegionByCode(code);
    		if (mainRegion == null) {
    			System.out.println("Very bad, found krisha region not mapped to Region Dic: " + code);
    			continue;
    		} else {
    			
    			KrishaRegion krishaRegion = new KrishaRegion();
    			krishaRegion.alias = krishaRegionEntity.alias;
    			krishaRegion.created = Calendar.getInstance();
    			krishaRegion.key = krishaRegionEntity.key;
    			krishaRegion.level = krishaRegionEntity.level;
    			krishaRegion.mapLatitude = krishaRegionEntity.mapLatitude;
    			krishaRegion.mapLongitude = krishaRegionEntity.mapLongitude;
    			krishaRegion.mapZoom = krishaRegionEntity.mapZoom;
    			krishaRegion.modifier = "artem.demidovich";
    			krishaRegion.name = krishaRegionEntity.name;
    			krishaRegion.region = mainRegion;
    			krishaRegion.type = krishaRegionEntity.type;
    			krishaRegion.updated = Calendar.getInstance();
    			
    			ds.save(krishaRegion);
    			
    		}
		}
    	
    }
    
    
    private static void syncIrrRegions() throws Exception {
    	List<IrrRegionEntityTable> irrRegions = getIrrRegions();
    	System.out.println("Loaded sql irr regions: " + irrRegions.size());
    
    	Datastore ds = MongoDBProvider.getInstance().getDatastore();
    	
    	for (IrrRegionEntityTable irrRegionEntity : irrRegions) {
    		String code = irrRegionEntity.region.id.toString();
    		Region mainRegion = getRegionByCode(code);
    		if (mainRegion == null) {
    			System.out.println("Very bad, found irr region not mapped to Region Dic: " + code);
    			continue;
    		} else {
    			
    			IrrRegion region = new IrrRegion();
    			region.created = Calendar.getInstance();
    			region.key = irrRegionEntity.key;
    			region.name = irrRegionEntity.name;
    			region.region = mainRegion;
    			region.updated = Calendar.getInstance();
    			
    			ds.save(region);
    			
    		}
		}
    	
    }
    

    private static void syncKnRegions() throws Exception {
    	List<KnRegionEntityTable> regions = getKnRegions();
    	System.out.println("Loaded sql kn regions: " + regions.size());
    
    	Datastore ds = MongoDBProvider.getInstance().getDatastore();
    	
    	for (KnRegionEntityTable knRegionEntity : regions) {
    		String code = knRegionEntity.region.id.toString();
    		Region mainRegion = getRegionByCode(code);
    		if (mainRegion == null) {
    			System.out.println("Very bad, found kn region not mapped to Region Dic: " + code);
    			continue;
    		} else {
    			
    			KnRegion region = new KnRegion();
    			region.created = Calendar.getInstance();
    			region.key = knRegionEntity.key;
    			region.name = knRegionEntity.name;
    			region.region = mainRegion;
    			region.updated = Calendar.getInstance();
    			
    			ds.save(region);
    			
    		}
		}
    	
    }
    
    private static void syncOlxRegions() throws Exception {
    	List<OlxRegionEntityTable> regions = getOlxRegions();
    	System.out.println("Loaded sql olx regions: " + regions.size());
    
    	Datastore ds = MongoDBProvider.getInstance().getDatastore();
    	
    	for (OlxRegionEntityTable olxRegionEntity : regions) {
    		String code = olxRegionEntity.region.id.toString();
    		Region mainRegion = getRegionByCode(code);
    		if (mainRegion == null) {
    			System.out.println("Very bad, found olx region not mapped to Region Dic: " + code);
    			continue;
    		} else {
    			
    			OlxRegion region = new OlxRegion();
    			region.created = Calendar.getInstance();
    			region.key = olxRegionEntity.key;
    			region.name = olxRegionEntity.name;
    			region.region = mainRegion;
    			region.updated = Calendar.getInstance();
    			region.type = olxRegionEntity.type;
    			
    			ds.save(region);
    			
    		}
		}
    }
    
    
    private static Region getRegionByCode(String code) throws Exception {
    	Datastore ds = MongoDBProvider.getInstance().getDatastore();
    	List<Region> foundRegions = ds.find(Region.class, "code", code).asList();
		if (foundRegions.size() > 0)
			return foundRegions.get(0);
		return null;
    }
    
    private static ResidentialComplex getResidentialComplexByCode(String code) throws Exception {
    	Datastore ds = MongoDBProvider.getInstance().getDatastore();
    	List<ResidentialComplex> foundRegions = ds.find(ResidentialComplex.class, "externalComplexId", code).asList();
		if (foundRegions.size() > 0)
			return foundRegions.get(0);
		return null;
    }
    
    
    private static List<RegionEntity> getRootRegions() {
    	return JPA.em().createQuery("from RegionEntity where parent is null").getResultList();
    }


	private static List<IrrRegionEntityTable> getIrrRegions() {
    	return JPA.em().createQuery("from IrrRegionEntityTable").getResultList();
    }
    
    private static List<OlxRegionEntityTable> getOlxRegions() {
    	return JPA.em().createQuery("from OlxRegionEntityTable").getResultList();
    }
    
    private static List<KnRegionEntityTable> getKnRegions() {
    	return JPA.em().createQuery("from KnRegionEntityTable").getResultList();
    }
    
    private static List<KrishaRegionEntityTable> getKrishaRegions() {
    	return JPA.em().createQuery("from KrishaRegionEntityTable").getResultList();
    }
    
    private static List<RegionEntity> getRegions() {
    	return JPA.em().createQuery("from RegionEntity").getResultList();
    }
    
    private static List<ResidentalComplexEntity> getResidentalComplexEntities() {
    	return JPA.em().createQuery("from ResidentalComplexEntity").getResultList();
    }
    
    private static List<KnResidentalComplexEntityTable> getKnResidentalComplexEntities() {
    	return JPA.em().createQuery("from KnResidentalComplexEntityTable").getResultList();
    }
    
    private static List<KrishaResidentalComplexEntityTable> getKrishaResidentalComplexEntityTables() {
    	return JPA.em().createQuery("from KrishaResidentalComplexEntityTable").getResultList();
    }
}