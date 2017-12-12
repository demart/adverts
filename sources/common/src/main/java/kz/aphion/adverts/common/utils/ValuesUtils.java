package kz.aphion.adverts.common.utils;

public class ValuesUtils {
	
	/**
	 * Метод корректно сравнивает значения Float учитывая null и другие варианты
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static Boolean isFloatsEqual(Float oldValue, Float newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		
		if (Math.abs(oldValue - newValue) < 0.0001)
			return true;
		
		return false;
	}
	
	/**
	 * Сравнивает два текстовых поля, при этом учитывая null и выполняя для каждого текста trim
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static Boolean isStringsEqual(String oldValue, String newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		
		return oldValue.trim().equals(newValue.trim());
	}
	
	/**
	 * Сравнивает два Boolean при этом учитывает NULL значения
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static Boolean isBooleansEqual(Boolean oldValue, Boolean newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		return oldValue == newValue;
	}
	
	/**
	 * Сравнивает два Long значение при этом учитывает NULL значения
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public static Boolean isLongsEqual(Long oldValue, Long newValue) {
		if (oldValue == null && newValue == null)
			return true;
		if ((oldValue == null && newValue != null) || (oldValue != null && newValue == null))
			return false;
		return oldValue.equals(newValue);
	}
	
}
