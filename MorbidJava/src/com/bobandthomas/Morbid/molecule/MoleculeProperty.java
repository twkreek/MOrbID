package com.bobandthomas.Morbid.molecule;

public class MoleculeProperty extends Number {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6989520355060226641L;
	String Name;
	String Value;
	Number numValue;
	String Units;
	String type;
	
	public MoleculeProperty(String name, String value, String units) {
		Name = name;
		Value = value;
		Units = units;
		type = value.getClass().toString();
		try 
		{
			numValue = Double.valueOf(value);
		}
		catch (java.lang.NumberFormatException e)
		{
		  numValue = null;	
		}
	}

	@Override
	public double doubleValue() {
		return numValue.doubleValue();
	}

	@Override
	public float floatValue() {
		return numValue.floatValue();
	}

	@Override
	public int intValue() {
		return numValue.intValue();
	}

	@Override
	public long longValue() {
		return numValue.longValue();
	}

}
