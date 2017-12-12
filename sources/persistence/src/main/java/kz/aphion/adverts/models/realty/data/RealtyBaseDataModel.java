package kz.aphion.adverts.models.realty.data;

import java.util.HashMap;

import kz.aphion.adverts.models.ModelUtils;
import kz.aphion.adverts.persistence.adverts.Advert;

/**
 * Базовый класс для информации о недвижимости
 * 
 * @author artem.demidovich
 *
 */
public abstract class RealtyBaseDataModel {

	protected Advert advert;
	
	protected HashMap<String, Object> getData() {
		return advert.data;
	}
	
	/**
	 * Цена
	 */
	//protected Long price;
	
	/**
	 * Общая площадь
	 */
	//protected Float square;
	
	/**
	 * Тип исчисления площади
	 */
	//protected SquareType squareType;
	
	/**
	 * Произвольное описание автора
	 */
	//protected String text;

	
	public Long getPrice() {
		return ModelUtils.getLongFromObject(getData().get("price"));
	}

	public void setPrice(Long price) {
		getData().put("price", price);
	}

	public Float getSquare() {
		return ModelUtils.getFloatFromObject(getData().get("square"));
	}

	public void setSquare(Float square) {
		getData().put("square", square);
	}

	public SquareType getSquareType() {
		return ModelUtils.getEnumFromObject(SquareType.class, getData().get("squareType"));
	}

	public void setSquareType(SquareType squareType) {
		getData().put("square", squareType);
	}

	public String getText() {
		return (String)getData().get("text");
	}

	public void setText(String text) {
		getData().put("text", text);
	}
	
}
