package com.bobandthomas.Morbid.utils;

import com.bobandthomas.Morbid.Gadget.AtomGadget;

public class MorbidEvent {
	ChangeNotifier source;
	String field;
	Object oldValue;
	Object newValue;

	public MorbidEvent() {
	}
	public MorbidEvent(ChangeNotifier source)
	{
		this.source = source;
	}
	void setChangeField(String field, Object oldValue, Object newValue)
	{
		this.field = field;
		this.oldValue = oldValue;
		this.newValue = newValue;	
	}
	public ChangeNotifier getSource() {
		return source;
	}
	public void setSource(ChangeNotifier source) {
		this.source = source;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public Object getOldValue() {
		return oldValue;
	}
	public void setOldValue(Object oldValue) {
		this.oldValue = oldValue;
	}
	public Object getNewValue() {
		return newValue;
	}
	public void setNewValue(Object newValue) {
		this.newValue = newValue;
	}
	
	public boolean isType(Class obj)
	{
		if (source == null) return false;
		if (source.getClass() == obj)
			return true;
		return false;

	}

}
