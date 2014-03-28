package com.bobandthomas.Morbid.utils;

public interface IPropertyDescriptorList {
	
	int getPropertyCount();

	Class<?> getPropertyClass(int index);

	String getPropertyName(int index);

	boolean isPropertyEditable(int index);
	
	int getPropertyIndex(String name);
	
	int getPropertyPreferredWidth(int index);
	
	IPropertyDescriptor getDescriptor(int index);
	IPropertyDescriptor getDescriptor(String name);
	
	void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e);
	void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e, IPropertySetter ips);
	
}
