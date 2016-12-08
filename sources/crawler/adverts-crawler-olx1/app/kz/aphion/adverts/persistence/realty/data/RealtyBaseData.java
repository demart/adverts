package kz.aphion.adverts.persistence.realty.data;

import kz.aphion.adverts.persistence.realty.types.SquareType;

import org.mongodb.morphia.annotations.Property;

/**
 * Базовый класс для информации о недвижимости
 * 
 * @author artem.demidovich
 *
 */
public abstract class RealtyBaseData {
	
	/**
	 * Общая площадь
	 */
	@Property
	public Float square;
	
	/**
	 * Тип исчисления площади
	 */
	@Property
	public SquareType squareType;
	
	
	/**
	 * Произвольное описание автора
	 */
	@Property
	public String text;
	
}
