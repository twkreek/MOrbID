package com.bobandthomas.Morbid.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class MorbidEvent.
 * Morbid Event is and event managed by IChangeNotifier's Listener/Notifier pattern
 * 
 * 
 * @author Thomas Kreek 
 */
public class MorbidEvent {
	
	/** The source object that originated the event */
	IChangeNotifier source;
	
	/** The field that changed [optional]. */
	String field;
	
	/** The old value. [optional] */
	Object oldValue;
	
	/** The new value. [optional]*/
	Object newValue;

	/**
	 * Instantiates a new morbid event.
	 */
	public MorbidEvent() {
	}
	
	/**
	 * Instantiates a new morbid event.
	 * 
	 * @param source
	 *            the object that initiated the event
	 */
	public MorbidEvent(IChangeNotifier source)
	{
		this.source = source;
	}
	
	/**
	 * Sets the change field.
	 * 
	 * @param field
	 *            the field
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 */
	void setChangeField(String field, Object oldValue, Object newValue)
	{
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;	
	}
	
	/**
	 * Gets the source.
	 * 
	 * @return the source
	 */
	public IChangeNotifier getSource() {
		return source;
	}
	
	/**
	 * Sets the source.
	 * 
	 * @param source
	 *            the new source
	 */
	public void setSource(ChangeNotifier source) {
		this.source = source;
	}
	
	/**
	 * Gets the field.
	 * 
	 * @return the field
	 */
	public String getField() {
		return field;
	}
	
	/**
	 * Sets the field.
	 * 
	 * @param field
	 *            the new field
	 */
	public void setField(String field) {
		this.field = field;
	}
	
	/**
	 * Gets the old value.
	 * 
	 * @return the old value
	 */
	public Object getOldValue() {
		return oldValue;
	}
	
	/**
	 * Sets the old value.
	 * 
	 * @param oldValue
	 *            the new old value
	 */
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
	
	/**
	 * Gets the new value.
	 * 
	 * @return the new value
	 */
	public Object getNewValue() {
		return newValue;
	}
	
	/**
	 * Sets the new value.
	 * 
	 * @param newValue
	 *            the new new value
	 */
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
	
	/**
	 * Checks if is type.
	 * Helper function to match type of the source with one the handler
	 * may be interested in.
	 * 
	 * @param obj
	 *            the obj
	 * @return true, if is type
	 */
	@SuppressWarnings("rawtypes")
	public boolean isType(Class obj)
	{
		if (source == null) return false;
		if (source.getClass() == obj)
			return true;
		return false;

	}
	
	/* 
	 * Human readable form of the event including optional parameters
	 */
	public String toString()
	{
		String s = "Event:";
		if (source != null)
			s += " " + source.getClass().getSimpleName();
		if (field != null)
			s += " changed "+ field;
		if (oldValue != null)
			s += " from " + oldValue;
		if (newValue != null)
			s += " to " + newValue;
		return s;
	}

}
