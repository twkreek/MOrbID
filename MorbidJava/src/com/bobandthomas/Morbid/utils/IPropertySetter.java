package com.bobandthomas.Morbid.utils;

// TODO: Auto-generated Javadoc
/**
 * The Interface IPropertySetter provides a uniform set/get from a static property definition
 * 
 * @author Thomas Kreek
 */
public interface IPropertySetter {
	
	/**
	 * Gets the value.
	 * 
	 * @param obj
	 *            the obj
	 * @return the object
	 */
	public Object get(Object obj);
	
	/**
	 * Sets the value specified by the instance.
	 * 
	 * @param obj
	 *            the obj
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean  set(Object obj, Object value);
}
