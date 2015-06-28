/*
 * 
 */
package com.bobandthomas.Morbid.utils;


// TODO: Auto-generated Javadoc
/**
 * Contains the properties to describe this data element in a class to map to tables or automation.
 * 
 * @author Thomas Kreek
 */
public class MPropertyDescriptor extends CLoadableItem implements IPropertySetter, IPropertyDescriptor {


	/** The class type. */
	@SuppressWarnings("rawtypes")
	private Class classType;
	
	/** The editable. */
	private boolean editable;
	
	/** The setter. */
	private IPropertySetter setter;
	
	/** The preferred width. */
	private int preferredWidth = 0;
	
	/**
	 * Instantiates a new m property descriptor.
	 * 
	 * @param ID
	 *            the id
	 * @param n
	 *            the n
	 * @param c
	 *            the c
	 * @param e
	 *            the e
	 */
	public MPropertyDescriptor(long ID, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e) {
		setID(ID);
		setName(n);
		classType = c;
		editable = e;
		setter = null;
	}

	/**
	 * Instantiates a new m property descriptor.
	 * 
	 * @param ID
	 *            the id
	 * @param n
	 *            the n
	 * @param c
	 *            the c
	 * @param e
	 *            the e
	 * @param setter
	 *            the setter
	 */
	public MPropertyDescriptor(long ID, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e, IPropertySetter setter) {
		setID(ID);
		setName(n);
		classType = c;
		editable = e;
		this.setter = setter;
	}


	/**
	 * Instantiates a new m property descriptor.
	 */
	MPropertyDescriptor() {

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptor#getClassType()
	 */
	@Override
	public Class<?> getClassType() {
		return classType;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptor#getIndex()
	 */
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

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertySetter#set(java.lang.Object, java.lang.Object)
	 */
	@Override
	public boolean set(Object obj, Object value) {
		if (setter == null) return false;
		return setter.set(obj, value);	
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertySetter#get(java.lang.Object)
	 */
	@Override
	public Object get(Object obj) {
		if (setter == null) return null;
		return setter.get(obj);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptor#getPreferredWidth()
	 */
	@Override
	public int getPreferredWidth() {
		return preferredWidth;
	}

	/**
	 * Sets the preferred width.
	 * 
	 * @param width
	 *            the new preferred width
	 */
	public void setPreferredWidth( int width)
	{
		preferredWidth = width;
	}
}
