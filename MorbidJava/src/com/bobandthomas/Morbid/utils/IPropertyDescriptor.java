package com.bobandthomas.Morbid.utils;

public interface IPropertyDescriptor {
	Class<?> getPropertyClass(int index);

	String getPropertyName(int index);

	boolean isPropertyEditable(int index);
	
	int getPropertyCount();
	
	int getIndex(String name);
}
