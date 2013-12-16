package com.bobandthomas.Morbid.utils;

public interface IPropertyDescriptor {
	Class<?> getPropertyClass(int index);

	String getPropertyName(int index);

	boolean isPropertyEditable(int index);
	
	int getPropertyCount();
	
	int getPropertyIndex(String name);
	
	void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e);
}
