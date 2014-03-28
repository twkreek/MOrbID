package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

public class MoleculeProperty extends CLoadableItem implements IPropertyAccessor{
	
	/**
	 * 
	 */


	String Value;
	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

	public String getUnits() {
		return Units;
	}

	public void setUnits(String units) {
		Units = units;
	}

	Number numValue;
	String Units;
	String type;
	private boolean isNumeric;
	
	public boolean isNumeric() {
		return isNumeric;
	}

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

	public Double doubleValue() {
		if (numValue == null)
			return null;
		return numValue.doubleValue();
	}

	public Float floatValue() {
		if (numValue == null)
			return null;
		return numValue.floatValue();
	}

	public Integer intValue() {
		if (numValue == null)
			return null;
	return numValue.intValue();
	}

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

	public Object getProperty(String name) {
		return access.getProperty(name);
	}

	public void setProperty(String name, Object value) {
		access.setProperty(name, value);
	}

	public Object getProperty(int index) {
		return access.getProperty(index);
	}

	public void setProperty(int index, Object value) {
		access.setProperty(index, value);
	}

	public Object getProperty(IPropertyDescriptor desc) {
		return access.getProperty(desc);
	}

	public void setProperty(IPropertyDescriptor desc, Object value) {
		access.setProperty(desc, value);
	}

	public IPropertyDescriptorList getDescriptors() {
		return access.getDescriptors();
	}
	

	// }}

}
