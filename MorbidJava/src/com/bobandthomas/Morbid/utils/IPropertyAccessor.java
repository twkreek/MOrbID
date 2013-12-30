package com.bobandthomas.Morbid.utils;


public interface IPropertyAccessor {
	
	Object getProperty(String name);

	void setProperty(String name, Object value);
	
	Object getProperty(int index);

	void setProperty(int index, Object value);
	
	Object getProperty(IPropertyDescriptor desc);
	
	void setProperty(IPropertyDescriptor desc, Object value);
	
	public IPropertyDescriptorList getDescriptors();
	
}