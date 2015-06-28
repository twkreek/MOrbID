/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
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
	
	/** used to identify specialized listener requirements. */
	public Class<?> speciallistener = null;
	
	/** The source object that originated the event. */
	IChangeNotifier source;
	
	/** The object that was changed. */
	IChangeNotifier target;
	
	/**
	 * Gets the object that was changed.
	 * 
	 * @return the object that was changed
	 */
	public IChangeNotifier getTarget() {
		return target;
	}

	/**
	 * Sets the object that was changed.
	 * 
	 * @param target
	 *            the new object that was changed
	 */
	public void setTarget(IChangeNotifier target) {
		this.target = target;
	}

	/** The field that changed [optional]. */
	String field;
	
	/** The old value. [optional] */
	Object oldValue;
	
	/** The new value. [optional]*/
	Object newValue;
	
	/** The handled list. */
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
	
	/**
	 * Instantiates a new morbid event.
	 * 
	 * @param source
	 *            the source
	 * @param field
	 *            the field
	 * @param oldValue
	 *            the old value
	 * @param newValue
	 *            the new value
	 */
	public MorbidEvent(IChangeNotifier source, String field, Object oldValue, Object newValue)
	{
		super(source);
		this.source = source;
		setChangeField(field, oldValue, newValue);
	}
	
	/**
	 * Instantiates a new morbid event.
	 * 
	 * @param source
	 *            the source
	 * @param field
	 *            the field
	 */
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
	
	/**
	 * Handled by.
	 * 
	 * @param not
	 *            the not
	 */
	public void handledBy(IChangeNotifier not)
	{
		handledList.add(not);
	}
	
	/* 
	 * Human readable form of the event including optional parameters
	 */
	/* (non-Javadoc)
	 * @see java.util.EventObject#toString()
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
