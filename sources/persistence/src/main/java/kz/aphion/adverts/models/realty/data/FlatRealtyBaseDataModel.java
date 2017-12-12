package kz.aphion.adverts.models.realty.data;

import java.util.List;

import kz.aphion.adverts.models.ModelUtils;
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
import kz.aphion.adverts.models.realty.data.flat.types.FlatSecurityType;
import kz.aphion.adverts.persistence.CalendarConverter;
import kz.aphion.adverts.persistence.adverts.Advert;
import kz.aphion.adverts.persistence.realty.ResidentialComplex;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.mapping.Mapper;

import com.mongodb.DBObject;

/**
 * Базовая информация о квартире
 * 
 * @author artem.demidovich
 *
 */
public class FlatRealtyBaseDataModel extends RealtyBaseDataModel {
	
	/**
	 * Информация о ЖК
	 */
	//protected ResidentialComplex residentialComplex;
	
	/**
	 * Кол-во комнат
	 */
	//protected Float rooms;
	
	/**
	 * Тип строения
	 */
	//protected FlatBuildingType flatBuildingType;
	
	/**
	 * Год постройки дома
	 */
	//protected Long houseYear;
	
	/**
	 * Этаж
	 */
	//protected Long flatFloor;
	
	/**
	 * Этажность дома
	 */
	//protected Long houseFloorCount;
	
	/**
	 * Жилая площадь
	 */
	//protected Float squareLiving;
	
	/**
	 * Площадь кухни
	 */
	//protected Float squareKitchen;
	
	/**
	 * В приватизированном общежитии
	 */
	//protected FlatPrivatizedDormType privatizedDormType;
	
	/**
	 * Состояние квартиры
	 */
	//protected FlatRenovationType renovationType;
	
	/**
	 * Телефон
	 */
	//protected FlatPhoneType phoneType;
	
	/**
	 * Интернет
	 */
	//protected FlatInternetType internetType;
	
	/**
	 * Санузел
	 */
	//protected FlatLavatoryType lavatoryType;
	
	/**
	 * Балкон
	 */
	//protected FlatBalconyType balconyType;
	
	/**
	 * Остекление балкона
	 */
	//protected FlatBalconyGlazingType balconyGlazingType;
	
	/**
	 * Дверь
	 */
	//protected FlatDoorType doorType;
	
	/**
	 * Парковка
	 */
	//protected FlatParkingType parkingType;
	
	/**
	 * Мебель
	 */
	//protected FlatFurnitureType furnitureType;
	
	/**
	 * Тип напольного покрытия
	 */
	//protected FlatFloorType floorType;
	
	/**
	 * Высота потолков
	 */
	//protected Float ceilingHeight;
	
	/**
	 * Безопасность
	 */
	//protected List<FlatSecurityType> securityTypes;
	
	public FlatRealtyBaseDataModel(Advert advert) {
		super(advert);
	}

	/**
	 * Разное
	 */
	//protected List<FlatMiscellaneousType> miscellaneous;	
	
	
	public ResidentialComplex getResidentialComplex(Datastore db) {
		Mapper mapper = new Mapper();
		mapper.getConverters().addConverter(CalendarConverter.class);
		ResidentialComplex residentialComplex = (DBObject)getData().get("residentialComplex") != null ? mapper.fromDBObject(db, ResidentialComplex.class, (DBObject)getData().get("residentialComplex"), mapper.createEntityCache()) : null;
		
		return residentialComplex;
	}

	public void setResidentialComplex(ResidentialComplex residentialComplex) {
		Mapper mapper = new Mapper();
		mapper.getConverters().addConverter(CalendarConverter.class);
		DBObject complexDBO = mapper.toDBObject(residentialComplex);
		
		getData().put("residentialComplex", complexDBO);
	}

	public Float getRooms() {
		return ModelUtils.getFloatFromObject(getData().get("rooms"));
	}

	public void setRooms(Float rooms) {
		getData().put("rooms", rooms);
	}

	public FlatBuildingType getFlatBuildingType() {
		return ModelUtils.getEnumFromObject(FlatBuildingType.class, getData().get("flatBuildingType"));
	}

	public void setFlatBuildingType(FlatBuildingType flatBuildingType) {
		getData().put("flatBuildingType", flatBuildingType);
	}

	public Long getHouseYear() {
		return ModelUtils.getLongFromObject(getData().get("houseYear"));
	}

	public void setHouseYear(Long houseYear) {
		getData().put("houseYear", houseYear);
	}

	public Long getFlatFloor() {
		return ModelUtils.getLongFromObject(getData().get("flatFloor"));
	}

	public void setFlatFloor(Long flatFloor) {
		getData().put("flatFloor", flatFloor);
	}

	public Long getHouseFloorCount() {
		return ModelUtils.getLongFromObject(getData().get("houseFloorCount"));
	}

	public void setHouseFloorCount(Long houseFloorCount) {
		getData().put("houseFloorCount", houseFloorCount);
	}

	public Float getSquareLiving() {
		return ModelUtils.getFloatFromObject(getData().get("squareLiving"));
	}

	public void setSquareLiving(Float squareLiving) {
		getData().put("squareLiving", squareLiving);
	}

	public Float getSquareKitchen() {
		return ModelUtils.getFloatFromObject(getData().get("squareKitchen"));
	}

	public void setSquareKitchen(Float squareKitchen) {
		getData().put("squareKitchen", squareKitchen);
	}

	public FlatPrivatizedDormType getPrivatizedDormType() {
		return ModelUtils.getEnumFromObject(FlatPrivatizedDormType.class, getData().get("privatizedDormType"));
	}

	public void setPrivatizedDormType(FlatPrivatizedDormType privatizedDormType) {
		getData().put("privatizedDormType", privatizedDormType);
	}

	public FlatRenovationType getRenovationType() {
		return ModelUtils.getEnumFromObject(FlatRenovationType.class, getData().get("renovationType"));
	}

	public void setRenovationType(FlatRenovationType renovationType) {
		getData().put("renovationType", renovationType);
	}

	public FlatPhoneType getPhoneType() {
		return ModelUtils.getEnumFromObject(FlatPhoneType.class, getData().get("phoneType"));
	}

	public void setPhoneType(FlatPhoneType phoneType) {
		getData().put("phoneType", phoneType);
	}

	public FlatInternetType getInternetType() {
		return ModelUtils.getEnumFromObject(FlatInternetType.class, getData().get("internetType"));
	}

	public void setInternetType(FlatInternetType internetType) {
		getData().put("internetType", internetType);
	}

	public FlatLavatoryType getLavatoryType() {
		return ModelUtils.getEnumFromObject(FlatLavatoryType.class, getData().get("lavatoryType"));
	}

	public void setLavatoryType(FlatLavatoryType lavatoryType) {
		getData().put("lavatoryType", lavatoryType);
	}

	public FlatBalconyType getBalconyType() {
		return ModelUtils.getEnumFromObject(FlatBalconyType.class, getData().get("balconyType"));
	}

	public void setBalconyType(FlatBalconyType balconyType) {
		getData().put("balconyType", balconyType);
	}

	public FlatBalconyGlazingType getBalconyGlazingType() {
		return ModelUtils.getEnumFromObject(FlatBalconyGlazingType.class, getData().get("balconyGlazingType"));
	}

	public void setBalconyGlazingType(FlatBalconyGlazingType balconyGlazingType) {
		getData().put("balconyGlazingType", balconyGlazingType);
	}

	public FlatDoorType getDoorType() {
		return ModelUtils.getEnumFromObject(FlatDoorType.class, getData().get("doorType"));
	}

	public void setDoorType(FlatDoorType doorType) {
		getData().put("doorType", doorType);
	}

	public FlatParkingType getParkingType() {
		return ModelUtils.getEnumFromObject(FlatParkingType.class, getData().get("parkingType"));
	}

	public void setParkingType(FlatParkingType parkingType) {
		getData().put("parkingType", parkingType);
	}

	public FlatFurnitureType getFurnitureType() {
		return ModelUtils.getEnumFromObject(FlatFurnitureType.class, getData().get("furnitureType"));
	}

	public void setFurnitureType(FlatFurnitureType furnitureType) {
		getData().put("furnitureType", furnitureType);
	}

	public FlatFloorType getFloorType() {
		return ModelUtils.getEnumFromObject(FlatFloorType.class, getData().get("floorType"));
	}

	public void setFloorType(FlatFloorType floorType) {
		getData().put("floorType", floorType);
	}

	public Float getCeilingHeight() {
		return ModelUtils.getFloatFromObject(getData().get("ceilingHeight"));
	}

	public void setCeilingHeight(Float ceilingHeight) {
		getData().put("ceilingHeight", ceilingHeight);
	}

	public List<FlatSecurityType> getSecurityTypes() {
		return ModelUtils.getEnumsFromObject(FlatSecurityType.class, advert.data.get("securityTypes"));
	}

	public void setSecurityTypes(List<FlatSecurityType> securityTypes) {
		getData().put("securityTypes", securityTypes);
	}

	public List<FlatMiscellaneousType> getMiscellaneous() {
		return ModelUtils.getEnumsFromObject(FlatMiscellaneousType.class, advert.data.get("miscellaneous"));
	}

	public void setMiscellaneous(List<FlatMiscellaneousType> miscellaneous) {
		getData().put("miscellaneous", miscellaneous);
	}

}
