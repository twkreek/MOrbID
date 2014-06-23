/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.utils;

// TODO: Auto-generated Javadoc
/**
 * The Class PropertyAccessor.
 * 
 * @author Thomas Kreek
 */
public abstract class PropertyAccessor implements IPropertyAccessor {

	/** The descriptor list. */
	IPropertyDescriptorList descriptorList;
	
	/** The parent. */
	Object parent;

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getDescriptors()
	 */
	public IPropertyDescriptorList getDescriptors()
	{
		return descriptorList;
	}
	
	/**
	 * Gets the descriptor.
	 * 
	 * @param index
	 *            the index
	 * @return the descriptor
	 */
	public IPropertyDescriptor getDescriptor(int index) {
		return descriptorList.getDescriptor(index);
	}

	/**
	 * Adds the property descriptor.
	 * 
	 * @param i
	 *            the i
	 * @param n
	 *            the n
	 * @param c
	 *            the c
	 * @param e
	 *            the e
	 * @param ips
	 *            the ips
	 */
	public void addPropertyDescriptor(int i, String n, Class<?> c, boolean e,
			IPropertySetter ips) {
		descriptorList.addPropertyDescriptor(i, n, c, e, ips);
	}

	/**
	 * Adds the property descriptor.
	 * 
	 * @param i
	 *            the i
	 * @param n
	 *            the n
	 * @param c
	 *            the c
	 * @param e
	 *            the e
	 */
	public void addPropertyDescriptor(int i, String n,
			@SuppressWarnings("rawtypes") Class c, boolean e) {
		descriptorList.addPropertyDescriptor(i, n, c, e);
	}

	/**
	 * Gets the property count.
	 * 
	 * @return the property count
	 */
	public int getPropertyCount() {
		return descriptorList.getPropertyCount();
	}

	/**
	 * Gets the property index.
	 * 
	 * @param name
	 *            the name
	 * @return the property index
	 */
	public int getPropertyIndex(String name) {
		return descriptorList.getPropertyIndex(name);
	}

	/**
	 * Instantiates a new property accessor.
	 * 
	 * @param parent
	 *            the parent
	 * @param desc
	 *            the desc
	 */
	public PropertyAccessor(Object parent, IPropertyDescriptorList desc) {
		descriptorList = desc;
		this.parent = parent;
	}

	/**
	 * Gets the.
	 * 
	 * @param parent
	 *            the parent
	 * @param index
	 *            the index
	 * @return the object
	 */
	public Object get(Object parent, int index) {
		IPropertySetter setter;
		if ((setter = descriptorList.getDescriptor(index).getSetter()) == null)
			return null;
		return setter.get(parent);
	}

	/**
	 * Sets the.
	 * 
	 * @param parent
	 *            the parent
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 * @return true, if successful
	 */
	public boolean set(Object parent, int index, Object value) {
		IPropertySetter setter;
		if ((setter = descriptorList.getDescriptor(index).getSetter()) == null)
			return false;
		setter.set(parent, value);
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(java.lang.String)
	 */
	@Override
	public Object getProperty(String name) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(name);
		if (descriptor.getSetter() != null) {
			Object obj = descriptor.getSetter().get(parent);
			if (obj != null)
				return obj;
		}
		return getProperty(descriptorList.getDescriptor(name));
	};

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(java.lang.String, java.lang.Object)
	 */
	@Override
	public void setProperty(String name, Object value) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(name);
		if (descriptor.getSetter() != null) {
			if (descriptor.getSetter().set(parent, value))
				return;

		}
		setProperty(descriptorList.getDescriptor(name), value);

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(int)
	 */
	@Override
	public Object getProperty(int index) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(index);
		if (descriptor.getSetter() != null) {
			Object obj = descriptor.getSetter().get(parent);
			if (obj != null)
				return obj;
		}
		return getProperty(descriptorList.getDescriptor(index));
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(int, java.lang.Object)
	 */
	@Override
	public void setProperty(int index, Object value) {
		IPropertyDescriptor descriptor = descriptorList.getDescriptor(index);
		if (descriptor.getSetter() != null) {
			if (descriptor.getSetter().set(parent, value))
				return;

		}
		setProperty(descriptorList.getDescriptor(index), value);
	}

}
