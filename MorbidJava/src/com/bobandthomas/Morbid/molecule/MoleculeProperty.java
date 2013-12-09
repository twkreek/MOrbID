package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.PropertyDescriptoList;

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

	static IPropertyDescriptor propertyDescriptor = new PropertyDescriptoList<MoleculeProperty>(){

		@Override
		public void initialize() {
			add(0, "Name", String.class, false);
			add(1, "Value", String.class, true);
			add(2, "Units", String.class, true);
			add(3, "Number", Number.class, false);
			
		}

	};
	IPropertyAccessor access = new PropertyAccessor(propertyDescriptor){
		@Override
		public Object getProperty(int index) {
			switch (index){
			case 0: return  MoleculeProperty.this.getName();
			case 1: return  MoleculeProperty.this.getValue();
			case 2: return  MoleculeProperty.this.getUnits();
			case 3:	return  MoleculeProperty.this.doubleValue();
			}
			return null;
		}
	
		@Override
		public void setProperty(int index, Object value) {
			switch (index){
			case 0:  MoleculeProperty.this.setName((String) value); return;
			case 1:   MoleculeProperty.this.setValue((String) value); return;
			case 2:   MoleculeProperty.this.setUnits((String) value); return;
			case 3:	 return;
			}
			
		}
	};

	public Object getProperty(int index) {
		return access.getProperty(index);
	}

	public int getPropertyCount() {
		return access.getPropertyCount();
	}

	public void setProperty(int index, Object value) {
		access.setProperty(index, value);
	}

	public int getIndex(String name) {
		return access.getIndex(name);
	}

	public Class<?> getPropertyClass(int index) {
		return access.getPropertyClass(index);
	}

	public String getPropertyName(int index) {
		return access.getPropertyName(index);
	}

	public boolean isPropertyEditable(int index) {
		return access.isPropertyEditable(index);
	}

	public Object getProperty(String name) {
		return access.getProperty(name);
	}

	public void setProperty(String name, Object value) {
		access.setProperty(name, value);
	}
	
}
