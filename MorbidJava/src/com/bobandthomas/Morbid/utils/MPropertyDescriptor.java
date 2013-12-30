/*
 * 
 */
package com.bobandthomas.Morbid.utils;


/**
 * Contains the properties to describe this data element in a class to map to tables or automation.
 * 
 * @author Thomas Kreek
 */
public class MPropertyDescriptor extends CLoadableItem implements IPropertySetter, IPropertyDescriptor {


	@SuppressWarnings("rawtypes")
	private Class classType;
	private boolean editable;
	private IPropertySetter setter;
	public MPropertyDescriptor(long ID, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e) {
		setID(ID);
		setName(n);
		classType = c;
		editable = e;
		setter = null;
	}

	public MPropertyDescriptor(long ID, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e, IPropertySetter setter) {
		setID(ID);
		setName(n);
		classType = c;
		editable = e;
		this.setter = setter;
	}


	MPropertyDescriptor() {

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptor#getClassType()
	 */
	@Override
	public Class<?> getClassType() {
		return classType;
	}
	
	@Override
	public int getIndex()
	{
		return (int) getID();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptor#getSetter()
	 */
	@Override
	public IPropertySetter getSetter() {
		return setter;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptor#isEditable()
	 */
	@Override
	public boolean isEditable() {
		return editable;
	}

	@Override
	public boolean set(Object obj, Object value) {
		if (setter == null) return false;
		return setter.set(obj, value);	
	}
	@Override
	public Object get(Object obj) {
		if (setter == null) return null;
		return setter.get(obj);
	}


}
