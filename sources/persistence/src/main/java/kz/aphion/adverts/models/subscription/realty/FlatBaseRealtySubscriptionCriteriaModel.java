package kz.aphion.adverts.models.subscription.realty;

import java.util.ArrayList;
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
import kz.aphion.adverts.persistence.realty.ResidentialComplex;
import kz.aphion.adverts.persistence.subscription.Subscription;

import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class FlatBaseRealtySubscriptionCriteriaModel extends BaseRealtySubscriptionCriteriaModel {

	public FlatBaseRealtySubscriptionCriteriaModel(Subscription subscription) {
		super(subscription);
		// TODO Auto-generated constructor stub
	}

//	/**
//	 * Информация о ЖК которые участвуют в поиске
//	 * Если NULL то не важно какие
//	 */
//	public List<ResidentialComplex> residentalComplexs;
//	
//	/**
//	 * Кол-во комнат от
//	 */
//	public Float roomFrom;
//	
//	/**
//	 * Кол-во комнат до
//	 */
//	public Float roomTo;
//	
//	/**
//	 * Типы интересуемых строений
//	 */
//	public List<FlatBuildingType> flatBuildingTypes;
//	
//	/**
//	 * Год постройки дома
//	 */
//	public Long houseYearFrom;
//	
//	/**
//	 * Год постройки дома
//	 */
//	public Long houseYearTo;
//	
//	/**
//	 * Этаж
//	 */
//	public Long flatFloorFrom;
//	
//	/**
//	 * Этаж
//	 */
//	public Long flatFloorTo;
//	
//	/**
//	 * Этажность дома
//	 */
//	public Long houseFloorCountFrom;
//	
//	/**
//	 * Этажность дома
//	 */
//	public Long houseFloorCountTo;
//	
//	/**
//	 * Жилая площадь
//	 */
//	public Float squareLivingFrom;
//	
//	/**
//	 * Жилая площадь
//	 */
//	public Float squareLivingTo;
//	
//	/**
//	 * Площадь кухни
//	 */
//	public Float squareKitchenFrom;
//	
//	/**
//	 * Площадь кухни
//	 */
//	public Float squareKitchenTo;
//	
//	/**
//	 * Высота потолков
//	 */
//	public Float ceilingHeightFrom;
//	
//	/**
//	 * Высота потолков
//	 */
//	public Float ceilingHeightTo;
//	
//	/**
//	 * В приватизированном общежитии
//	 */
//	public List<FlatPrivatizedDormType> privatizedDormTypes;
//	
//	/**
//	 * Состояние квартиры
//	 */
//	public List<FlatRenovationType> renovationTypes;
//	
//	/**
//	 * Телефон
//	 */
//	public List<FlatPhoneType> phoneTypes;
//	
//	/**
//	 * Интернет
//	 */
//	public List<FlatInternetType> internetTypes;
//	
//	/**
//	 * Санузел
//	 */
//	public List<FlatLavatoryType> lavatoryTypes;
//	
//	/**
//	 * Балкон
//	 */
//	public List<FlatBalconyType> balconyTypes;
//	
//	/**
//	 * Остекление балкона
//	 */
//	public List<FlatBalconyGlazingType> balconyGlazingTypes;
//	
//	/**
//	 * Дверь
//	 */
//	public List<FlatDoorType> doorTypes;
//	
//	/**
//	 * Парковка
//	 */
//	public List<FlatParkingType> parkingTypes;
//	
//	/**
//	 * Мебель
//	 */
//	public List<FlatFurnitureType> furnitureTypes;
//	
//	/**
//	 * Тип напольного покрытия
//	 */
//	public List<FlatFloorType> floorTypes;
//	
//	/**
//	 * Безопасность
//	 */
//	public List<FlatSecurityType> securityTypes;
//	
//	/**
//	 * Разное
//	 */
//	public List<FlatMiscellaneousType> miscellaneous;

	public List<String> getResidentialComplexIds() {
		Object obj = getData().get("residentialComplexIds");
		if (obj != null && obj instanceof List) {
			List<String> residentalComplexIds = new ArrayList<String>();
			for (Object objVal : (List<?>)obj) {
				if (objVal != null)
					residentalComplexIds.add(objVal.toString());
			}
		}
		return null;
	}

	public void setResidentialComplexIds(List<String> residentialComplexIds) {
		getData().put("residentialComplexIds", residentialComplexIds);
	}
	
	public List<ResidentialComplex> getResidentialComplexes(Datastore ds) {
		List<String> ids = getResidentialComplexIds();
		if (ids == null || ids.size() < 1)
			return null;
		
		List<ResidentialComplex> complexes = new ArrayList<ResidentialComplex>();
		for (String id : ids) {
			if (id == null || ObjectId.isValid(id) == false)
				continue;
			ResidentialComplex complex = ds.get(ResidentialComplex.class, new ObjectId(id));
			if (complex != null)
				complexes.add(complex);
		}
		
		return complexes;
	}

	public void setResidentialComplexes(List<ResidentialComplex> residentialComplexes) {
		if (residentialComplexes == null) {
			getData().put("residentalComplexIds", null);
			return;
		}
		
		List<String> ids = new ArrayList<String>();
		if (residentialComplexes.size() < 1) { 
			getData().put("residentalComplexIds", ids);
			return;
		}
		
		for (ResidentialComplex residentialComplex : residentialComplexes) {
			if (residentialComplex == null)
				continue;
			
			ids.add(residentialComplex.id.toHexString());
		}
		
		getData().put("residentalComplexIds", ids);
	}

	public Float getRoomFrom() {
		return ModelUtils.getFloatFromObject(getData().get("roomFrom"));
	}

	public void setRoomFrom(Float roomFrom) {
		getData().put("roomFrom", roomFrom);
	}

	public Float getRoomTo() {
		return ModelUtils.getFloatFromObject(getData().get("roomTo"));
	}

	public void setRoomTo(Float roomTo) {
		getData().put("roomTo", roomTo);
	}

	public List<FlatBuildingType> getFlatBuildingTypes() {
		return ModelUtils.getEnumsFromObject(FlatBuildingType.class, getData().get("flatBuildingTypes"));
	}

	public void setFlatBuildingTypes(List<FlatBuildingType> flatBuildingTypes) {
		getData().put("flatBuildingTypes", flatBuildingTypes);	
	}

	public Long getHouseYearFrom() {
		return ModelUtils.getLongFromObject(getData().get("houseYearFrom"));
	}

	public void setHouseYearFrom(Long houseYearFrom) {
		getData().put("houseYearFrom", houseYearFrom);
	}

	public Long getHouseYearTo() {
		return ModelUtils.getLongFromObject(getData().get("houseYearTo"));
	}

	public void setHouseYearTo(Long houseYearTo) {
		getData().put("houseYearTo", houseYearTo);
	}

	public Long getFlatFloorFrom() {
		return ModelUtils.getLongFromObject(getData().get("flatFloorFrom"));
	}

	public void setFlatFloorFrom(Long flatFloorFrom) {
		getData().put("flatFloorFrom", flatFloorFrom);
	}

	public Long getFlatFloorTo() {
		return ModelUtils.getLongFromObject(getData().get("flatFloorTo"));
	}

	public void setFlatFloorTo(Long flatFloorTo) {
		getData().put("flatFloorTo", flatFloorTo);
	}

	public Long getHouseFloorCountFrom() {
		return ModelUtils.getLongFromObject(getData().get("houseFloorCountFrom"));
	}

	public void setHouseFloorCountFrom(Long houseFloorCountFrom) {
		getData().put("houseFloorCountFrom", houseFloorCountFrom);
	}

	public Long getHouseFloorCountTo() {
		return ModelUtils.getLongFromObject(getData().get("houseFloorCountTo"));
	}

	public void setHouseFloorCountTo(Long houseFloorCountTo) {
		getData().put("houseFloorCountTo", houseFloorCountTo);
	}

	public Float getSquareLivingFrom() {
		return ModelUtils.getFloatFromObject(getData().get("squareLivingFrom"));
	}

	public void setSquareLivingFrom(Float squareLivingFrom) {
		getData().put("squareLivingFrom", squareLivingFrom);
	}

	public Float getSquareLivingTo() {
		return ModelUtils.getFloatFromObject(getData().get("squareLivingTo"));
	}

	public void setSquareLivingTo(Float squareLivingTo) {
		getData().put("squareLivingTo", squareLivingTo);
	}

	public Float getSquareKitchenFrom() {
		return ModelUtils.getFloatFromObject(getData().get("squareKitchenFrom"));
	}

	public void setSquareKitchenFrom(Float squareKitchenFrom) {
		getData().put("squareKitchenFrom", squareKitchenFrom);
	}

	public Float getSquareKitchenTo() {
		return ModelUtils.getFloatFromObject(getData().get("squareKitchenTo"));
	}

	public void setSquareKitchenTo(Float squareKitchenTo) {
		getData().put("squareKitchenTo", squareKitchenTo);
	}

	public Float getCeilingHeightFrom() {
		return ModelUtils.getFloatFromObject(getData().get("ceilingHeightFrom"));
	}

	public void setCeilingHeightFrom(Float ceilingHeightFrom) {
		getData().put("ceilingHeightFrom", ceilingHeightFrom);
	}

	public Float getCeilingHeightTo() {
		return ModelUtils.getFloatFromObject(getData().get("ceilingHeightTo"));
	}

	public void setCeilingHeightTo(Float ceilingHeightTo) {
		getData().put("ceilingHeightTo", ceilingHeightTo);
	}

	public List<FlatPrivatizedDormType> getPrivatizedDormTypes() {
		return ModelUtils.getEnumsFromObject(FlatPrivatizedDormType.class, getData().get("privatizedDormTypes"));
	}

	public void setPrivatizedDormTypes(List<FlatPrivatizedDormType> privatizedDormTypes) {
		getData().put("privatizedDormTypes", privatizedDormTypes);
	}

	public List<FlatRenovationType> getRenovationTypes() {
		return ModelUtils.getEnumsFromObject(FlatRenovationType.class, getData().get("renovationTypes"));
	}

	public void setRenovationTypes(List<FlatRenovationType> renovationTypes) {
		getData().put("renovationTypes", renovationTypes);
	}

	public List<FlatPhoneType> getPhoneTypes() {
		return ModelUtils.getEnumsFromObject(FlatPhoneType.class, getData().get("phoneTypes"));
	}

	public void setPhoneTypes(List<FlatPhoneType> phoneTypes) {
		getData().put("phoneTypes", phoneTypes);
	}

	public List<FlatInternetType> getInternetTypes() {
		return ModelUtils.getEnumsFromObject(FlatInternetType.class, getData().get("internetTypes"));
	}

	public void setInternetTypes(List<FlatInternetType> internetTypes) {
		getData().put("internetTypes", internetTypes);
	}

	public List<FlatLavatoryType> getLavatoryTypes() {
		return ModelUtils.getEnumsFromObject(FlatLavatoryType.class, getData().get("lavatoryTypes"));
	}

	public void setLavatoryTypes(List<FlatLavatoryType> lavatoryTypes) {
		getData().put("lavatoryTypes", lavatoryTypes);
	}

	public List<FlatBalconyType> getBalconyTypes() {
		return ModelUtils.getEnumsFromObject(FlatBalconyType.class, getData().get("balconyTypes"));
	}

	public void setBalconyTypes(List<FlatBalconyType> balconyTypes) {
		getData().put("balconyTypes", balconyTypes);
	}

	public List<FlatBalconyGlazingType> getBalconyGlazingTypes() {
		return ModelUtils.getEnumsFromObject(FlatBalconyGlazingType.class, getData().get("balconyGlazingTypes"));
	}

	public void setBalconyGlazingTypes(List<FlatBalconyGlazingType> balconyGlazingTypes) {
		getData().put("balconyGlazingTypes", balconyGlazingTypes);
	}

	public List<FlatDoorType> getDoorTypes() {
		return ModelUtils.getEnumsFromObject(FlatDoorType.class, getData().get("doorTypes"));
	}

	public void setDoorTypes(List<FlatDoorType> doorTypes) {
		getData().put("doorTypes", doorTypes);
	}

	public List<FlatParkingType> getParkingTypes() {
		return ModelUtils.getEnumsFromObject(FlatParkingType.class, getData().get("parkingTypes"));
	}

	public void setParkingTypes(List<FlatParkingType> parkingTypes) {
		getData().put("parkingTypes", parkingTypes);
	}

	public List<FlatFurnitureType> getFurnitureTypes() {
		return ModelUtils.getEnumsFromObject(FlatFurnitureType.class, getData().get("furnitureTypes"));
	}

	public void setFurnitureTypes(List<FlatFurnitureType> furnitureTypes) {
		getData().put("furnitureTypes", furnitureTypes);
	}

	public List<FlatFloorType> getFloorTypes() {
		return ModelUtils.getEnumsFromObject(FlatFloorType.class, getData().get("floorTypes"));
	}

	public void setFloorTypes(List<FlatFloorType> floorTypes) {
		getData().put("floorTypes", floorTypes);
	}

	public List<FlatSecurityType> getSecurityTypes() {
		return ModelUtils.getEnumsFromObject(FlatSecurityType.class, getData().get("securityTypes"));
	}

	public void setSecurityTypes(List<FlatSecurityType> securityTypes) {
		getData().put("securityTypes", securityTypes);
	}

	public List<FlatMiscellaneousType> getMiscellaneous() {
		return ModelUtils.getEnumsFromObject(FlatMiscellaneousType.class, getData().get("miscellaneous"));
	}

	public void setMiscellaneous(List<FlatMiscellaneousType> miscellaneous) {
		getData().put("miscellaneous", miscellaneous);
	}
	
}
