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
 * The Interface IPropertyDescriptorList.
 * 
 * @author Thomas Kreek
 */
public interface IPropertyDescriptorList {
	
	/**
	 * Gets the property count.
	 * 
	 * @return the property count
	 */
	int getPropertyCount();

	/**
	 * Gets the property class.
	 * 
	 * @param index
	 *            the index
	 * @return the property class
	 */
	Class<?> getPropertyClass(int index);

	/**
	 * Gets the property name.
	 * 
	 * @param index
	 *            the index
	 * @return the property name
	 */
	String getPropertyName(int index);

	/**
	 * Checks if is property editable.
	 * 
	 * @param index
	 *            the index
	 * @return true, if is property editable
	 */
	boolean isPropertyEditable(int index);
	
	/**
	 * Gets the property index.
	 * 
	 * @param name
	 *            the name
	 * @return the property index
	 */
	int getPropertyIndex(String name);
	
	/**
	 * Gets the property preferred width.
	 * 
	 * @param index
	 *            the index
	 * @return the property preferred width
	 */
	int getPropertyPreferredWidth(int index);
	
	/**
	 * Gets the descriptor.
	 * 
	 * @param index
	 *            the index
	 * @return the descriptor
	 */
	IPropertyDescriptor getDescriptor(int index);
	
	/**
	 * Gets the descriptor.
	 * 
	 * @param name
	 *            the name
	 * @return the descriptor
	 */
	IPropertyDescriptor getDescriptor(String name);
	
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
	void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e);
	
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
	void addPropertyDescriptor(int i, String n, @SuppressWarnings("rawtypes") Class c,
			boolean e, IPropertySetter ips);
	
}
