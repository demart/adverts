package kz.aphion.adverts.models;

import java.util.ArrayList;
import java.util.List;

public class ModelUtils {

	public static Float getFloatFromObject(Object object) {
		if (object == null)
			return null;
		
		if (object instanceof Float)
			return (Float)object;
		
		if (object instanceof String) {
			try {
				return Float.parseFloat((String)object);
			} catch (NumberFormatException e) { 
			}
		}
		
		return null;
	}
	
	public static Long getLongFromObject(Object object) {
		if (object == null)
			return null;
		
		if (object instanceof Long)
			return (Long)object;
		
		if (object instanceof String) {
			try {
				return Long.parseLong((String)object);
			} catch (NumberFormatException e) { 
			}
		}
		
		return null;
	}
	
	public static <T extends Enum<T>> List<T> getEnumsFromObject(Class<T> c, Object list){
		if (list == null)
			return null;
		
		List<T> listOfTypedValues = new ArrayList<T>();
		if (list instanceof List) {
			List<?> listOfValues = (List<?>)list;
			for (Object object : listOfValues) {
				if (object != null) {
					T val = getEnumFromObject(c, object);
					if (val != null)
						listOfTypedValues.add(val);
				}
			}
			return listOfTypedValues;
		}
		
		return null;
	}
	
	public static <T extends Enum<T>> T getEnumFromObject(Class<T> c, Object string) {
		if (string == null) 
			return null;
		
		if (!(string instanceof String))
			string = string.toString();
		
		return getEnumFromString(c, (String)string);
	}
	
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
	    if( c != null && string != null ) {
	        try {
	            return Enum.valueOf(c, string.trim().toUpperCase());
	        } catch(IllegalArgumentException ex) {
	        }
	    }
	    
	    return null;
	}
	
}
