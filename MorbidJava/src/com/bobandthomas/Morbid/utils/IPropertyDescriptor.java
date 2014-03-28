package com.bobandthomas.Morbid.utils;

public interface IPropertyDescriptor {

	public Class<?> getClassType();

	public boolean isEditable();

	public IPropertySetter getSetter();
	
	public String getName();
	
	public int getIndex();
	
	public int getPreferredWidth();

}