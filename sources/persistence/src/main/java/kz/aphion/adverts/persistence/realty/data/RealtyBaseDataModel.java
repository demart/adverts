package kz.aphion.adverts.persistence.realty.data;

import kz.aphion.adverts.persistence.realty.SquareType;

/**
 * Базовый класс для информации о недвижимости
 * 
 * @author artem.demidovich
 *
 */
public abstract class RealtyBaseDataModel {
	
	/**
	 * Общая площадь
	 */
	public Float square;
	
	/**
	 * Тип исчисления площади
	 */
	public SquareType squareType;
	
	
	/**
	 * Произвольное описание автора
	 */
	public String text;
	
}
