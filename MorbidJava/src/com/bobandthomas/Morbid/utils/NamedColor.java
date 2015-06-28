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

import java.awt.Color;

import javax.vecmath.Color3f;

// TODO: Auto-generated Javadoc
/**
 * The Class NamedColor.
 * 
 * @author Thomas Kreek
 */
public class NamedColor extends CLoadableItem implements IPropertyAccessor {
	
	/** The color. */
	ColorQuad color;

	/**
	 * Sets the.
	 * 
	 * @param r
	 *            the r
	 * @param g
	 *            the g
	 * @param b
	 *            the b
	 */
	public void Set(double r, double g, double b) {
		color.Set(r, g, b);
	}

	/**
	 * Sets the.
	 * 
	 * @param inR
	 *            the in r
	 * @param inG
	 *            the in g
	 * @param inB
	 *            the in b
	 */
	public void Set(int inR, int inG, int inB) {
		color.Set(inR, inG, inB);
	}
	
	/**
	 * Sets the.
	 * 
	 * @param cq
	 *            the cq
	 */
	public void Set(ColorQuad cq)
	{
		color.Set(cq.x, cq.y, cq.z);
	}

	/**
	 * Cf.
	 * 
	 * @return the color3f
	 */
	public Color3f Cf() {
		return color.Cf();
	}

	/**
	 * Gets the j color.
	 * 
	 * @return the j color
	 */
	public Color getJColor() {
		return color.getJColor();
	}

	/**
	 * Sets the.
	 * 
	 * @param arg0
	 *            the arg0
	 */
	public final void set(Color arg0) {
		color.set(arg0);
	}

	/**
	 * Sets the.
	 * 
	 * @param arg0
	 *            the arg0
	 * @param arg1
	 *            the arg1
	 * @param arg2
	 *            the arg2
	 * @param arg3
	 *            the arg3
	 */
	public final void set(float arg0, float arg1, float arg2, float arg3) {
		color.set(arg0, arg1, arg2, arg3);
	}

	/**
	 * Sets the alpha.
	 * 
	 * @param a
	 *            the new alpha
	 */
	public void setAlpha(double a) {
		color.setAlpha(a);
	}
	
	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public ColorQuad getColor()
	{
		return color;
	}
	
	/** The property descriptor. */
	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "ID", Number.class, true);
			addPropertyDescriptor(2, "Color", ColorQuad.class, true);

			
		}

	};
	
	/** The access. */
	IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
		@Override
		public Object getProperty(IPropertyDescriptor ipd) {
			switch (ipd.getIndex()){
			case 0: return  NamedColor.this.getName();
			case 1: return  NamedColor.this.getID();
			case 2: return  NamedColor.this.getColor();
			}
			return null;
		}
	
		@Override
		public void setProperty(IPropertyDescriptor ipd, Object value) {
			switch (ipd.getIndex()){
			case 0:  NamedColor.this.setName((String) value); return;
			case 1:  NamedColor.this.setID((Long) value); return;
			case 2:  NamedColor.this.Set((ColorQuad) value); return;
			case 3:	 return;
			}
			
		}

	};

	// {{ IAccessorDelegates

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(java.lang.String)
	 */
	public Object getProperty(String name) {
		return access.getProperty(name);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(java.lang.String, java.lang.Object)
	 */
	public void setProperty(String name, Object value) {
		access.setProperty(name, value);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(int)
	 */
	public Object getProperty(int index) {
		return access.getProperty(index);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(int, java.lang.Object)
	 */
	public void setProperty(int index, Object value) {
		access.setProperty(index, value);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor)
	 */
	public Object getProperty(IPropertyDescriptor desc) {
		return access.getProperty(desc);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor, java.lang.Object)
	 */
	public void setProperty(IPropertyDescriptor desc, Object value) {
		access.setProperty(desc, value);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getDescriptors()
	 */
	public IPropertyDescriptorList getDescriptors() {
		return access.getDescriptors();
	}
	

	// }}

}
