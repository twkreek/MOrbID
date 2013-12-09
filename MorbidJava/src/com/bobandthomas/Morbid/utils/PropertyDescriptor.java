/*
 * 
 */
package com.bobandthomas.Morbid.utils;


/**
 * @author Thomas Kreek
 * Contains the properties to describe this data element in a table.
 */
public class PropertyDescriptor extends CLoadableItem {


	@SuppressWarnings("rawtypes")
	private Class classType;
	private boolean editable;
	public Class<?> getClassType() {
		return classType;
	}

	public boolean isEditable() {
		return editable;
	}


	PropertyDescriptor() {

	}

	public PropertyDescriptor(int ID, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e) {
		setID(ID);
		setName(n);
		classType = c;
		editable = e;
	}

}
