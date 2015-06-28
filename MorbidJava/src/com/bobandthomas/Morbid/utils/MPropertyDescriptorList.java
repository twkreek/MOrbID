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

import java.util.ArrayList;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class MPropertyDescriptorList.
 * 
 * @author Thomas Kreek
 */
public abstract class MPropertyDescriptorList implements IPropertyDescriptorList
{


	/** The list. */
	ArrayList<MPropertyDescriptor> list = new ArrayList<MPropertyDescriptor>();
	
	/** The name index. */
	HashMap<String, MPropertyDescriptor> nameIndex = new HashMap<String,MPropertyDescriptor>();
	
	/**
	 * Instantiates a new m property descriptor list.
	 */
	public MPropertyDescriptorList()
	{
//		this.setUseByName(true);
		initialize();
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#getPropertyCount()
	 */
	@Override
	public int getPropertyCount() {return list.size(); }
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getValue(java.lang.String)
	 */
	/**
	 * Initialize.
	 */
	public abstract void initialize();
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getType(int)
	 */
	public Class<?> getPropertyClass(int index)
	{
		return list.get((int) index).getClassType();
	}
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#getName(int)
	 */
	public String getPropertyName(int index)
	{
		return list.get(index).getName();
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#getPropertyPreferredWidth(int)
	 */
	@Override
	public int getPropertyPreferredWidth(int index) {
		return list.get(index).getPreferredWidth();
	}
	
	/**
	 * Sets the property preferred width.
	 * 
	 * @param index
	 *            the index
	 * @param width
	 *            the width
	 */
	public void setPropertyPreferredWidth(int index, int width)
	{
		list.get(index).setPreferredWidth(width);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.IPropertyAccess#isEditable(int)
	 */
	public boolean isPropertyEditable(int index)
	{
		return list.get(index).isEditable();
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#getPropertyIndex(java.lang.String)
	 */
	@Override
	public int getPropertyIndex(String name)
	{
		return (int) nameIndex.get(name).getIndex();
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#addPropertyDescriptor(int, java.lang.String, java.lang.Class, boolean)
	 */
	@Override
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e)
	{
		MPropertyDescriptor mtc = new MPropertyDescriptor(i, n,c,e);
		nameIndex.put(n, mtc);
		list.add(mtc);
	}	
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#addPropertyDescriptor(int, java.lang.String, java.lang.Class, boolean, com.bobandthomas.Morbid.utils.IPropertySetter)
	 */
	public void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e, IPropertySetter ips)
	{
		MPropertyDescriptor mtc = new MPropertyDescriptor(i, n,c,e, ips);
		nameIndex.put(n, mtc);
		list.add(mtc);
	}	
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#getDescriptor(int)
	 */
	@Override
	public IPropertyDescriptor getDescriptor(int index) {
		return list.get((int) index);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyDescriptorList#getDescriptor(java.lang.String)
	 */
	@Override
	public IPropertyDescriptor getDescriptor(String name)
	{
		return nameIndex.get(name);
	}

	

}
