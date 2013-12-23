package com.bobandthomas.Morbid.utils;

import java.util.ArrayList;
import java.util.EventObject;

// TODO: Auto-generated Javadoc
/**
 * The Class MorbidEvent.
 * Morbid Event is and event managed by IChangeNotifier's Listener/Notifier pattern
 * 
 * 
 * @author Thomas Kreek 
 */
public class MorbidEvent extends EventObject {
	
	/** The source object that originated the event */
	IChangeNotifier source;
	
	/** The object that was changed */
	IChangeNotifier target;
	
	public IChangeNotifier getTarget() {
		return target;
	}

	public void setTarget(IChangeNotifier target) {
		this.target = target;
	}

	/** The field that changed [optional]. */
	String field;
	
	/** The old value. [optional] */
	Object oldValue;
	
	/** The new value. [optional]*/
	Object newValue;
	
	ArrayList<IChangeNotifier> handledList = new ArrayList<IChangeNotifier>();

	
	/**
	 * Instantiates a new morbid event.
	 * 
	 * @param item
	 *            the object that initiated the event
	 */
	public MorbidEvent(IChangeNotifier item)
	{
		super(item);
		this.source = item;
	}
	
	public MorbidEvent(IChangeNotifier source, String field, Object oldValue, Object newValue)
	{
		super(source);
		this.source = source;
		setChangeField(field, oldValue, newValue);
	}
	
	public MorbidEvent(IChangeNotifier source, String field)
	{
		super(source);
		this.source = source;
		setField(field);
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
	public Object getSource() {
		return source;
	}
	
	/**
	 * Sets the source.
	 * 
	 * @param source
	 *            the new source
	 */
	public void setSource(IChangeNotifier source) {
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
	public void handledBy(IChangeNotifier not)
	{
		handledList.add(not);
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
		if (target != null)
			s += " in "+ target.getClass().getSimpleName();
		if (oldValue != null)
			s += " from " + oldValue;
		if (newValue != null)
			s += " to " + newValue;
		for (IChangeNotifier handler : handledList)
		{
			s+= " Handled by: " + handler.getClass().getSimpleName();
		}
		return s;
	}

}
