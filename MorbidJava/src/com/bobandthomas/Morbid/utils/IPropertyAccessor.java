package com.bobandthomas.Morbid.utils;


public interface IPropertyAccessor extends IPropertyDescriptor {
	
	Object getProperty(String name);

	void setProperty(String name, Object value);
	
	Object getProperty(int index);

	void setProperty(int index, Object value);
	
	void addProperty(String name, Object value);

}