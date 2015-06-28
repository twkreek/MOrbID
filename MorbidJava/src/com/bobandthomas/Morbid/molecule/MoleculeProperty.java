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
package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

// TODO: Auto-generated Javadoc
/**
 * The Class MoleculeProperty.
 * 
 * @author Thomas Kreek
 */
public class MoleculeProperty extends CLoadableItem implements IPropertyAccessor{
	
	/** The Value. */


	String Value;
	
	/**
	 * Gets the value.
	 * 
	 * @return the value
	 */
	public String getValue() {
		return Value;
	}

	/**
	 * Sets the value.
	 * 
	 * @param value
	 *            the new value
	 */
	public void setValue(String value) {
		Value = value;
	}

	/**
	 * Gets the units.
	 * 
	 * @return the units
	 */
	public String getUnits() {
		return Units;
	}

	/**
	 * Sets the units.
	 * 
	 * @param units
	 *            the new units
	 */
	public void setUnits(String units) {
		Units = units;
	}

	/** The num value. */
	Number numValue;
	
	/** The Units. */
	String Units;
	
	/** The type. */
	String type;
	
	/** The is numeric. */
	private boolean isNumeric;
	
	/**
	 * Checks if is numeric.
	 * 
	 * @return true, if is numeric
	 */
	public boolean isNumeric() {
		return isNumeric;
	}

	/**
	 * Instantiates a new molecule property.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 * @param units
	 *            the units
	 */
	public MoleculeProperty(String name, String value, String units) {
		setName(name);
		Value = value;
		Units = units;
		type = value.getClass().toString();
		try 
		{
			numValue = Double.valueOf(value);
			isNumeric = true;
		}
		catch (java.lang.NumberFormatException e)
		{
		  numValue = null;	
		  isNumeric = false;
		}
	}

	/**
	 * Double value.
	 * 
	 * @return the double
	 */
	public Double doubleValue() {
		if (numValue == null)
			return null;
		return numValue.doubleValue();
	}

	/**
	 * Float value.
	 * 
	 * @return the float
	 */
	public Float floatValue() {
		if (numValue == null)
			return null;
		return numValue.floatValue();
	}

	/**
	 * Int value.
	 * 
	 * @return the integer
	 */
	public Integer intValue() {
		if (numValue == null)
			return null;
	return numValue.intValue();
	}

	/** The property descriptor. */
	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false);
			addPropertyDescriptor(1, "Value", String.class, true);
			addPropertyDescriptor(2, "Units", String.class, true);
			addPropertyDescriptor(3, "Number", Number.class, false);
			setPropertyPreferredWidth(0, 300);
			setPropertyPreferredWidth(1, 500);
			setPropertyPreferredWidth(2, 150);
			setPropertyPreferredWidth(3, 50);
				
			
		}

	};
	
	/** The access. */
	IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
		@Override
		public Object getProperty(IPropertyDescriptor ipd) {
			switch (ipd.getIndex()){
			case 0: return  MoleculeProperty.this.getName();
			case 1: return  MoleculeProperty.this.getValue();
			case 2: return  MoleculeProperty.this.getUnits();
			case 3:	return  MoleculeProperty.this.doubleValue();
			}
			return null;
		}
	
		@Override
		public void setProperty(IPropertyDescriptor ipd, Object value) {
			switch (ipd.getIndex()){
			case 0:  MoleculeProperty.this.setName((String) value); return;
			case 1:   MoleculeProperty.this.setValue((String) value); return;
			case 2:   MoleculeProperty.this.setUnits((String) value); return;
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
