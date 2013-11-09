package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.utils.Point3D;

public class SpatialDataCharge extends SpatialData {

	static class SpatialDataType
	{
		static String name = "Charge";
		static public  SpatialData create(Molecule m)
		{
			return new SpatialDataCharge(m);
		}
	}
	public String getName() { return "Charge"; }
	public SpatialDataCharge(Molecule m)
	{
		super(m);
	}
	@Override
	public double Calculate(Point3D pos) {
		double value = 0.0;
		for ( Atom a: molecule.Atoms())
		{

			double distanceSqr = pos.distanceSquared(a.pos);
			value += a.getCharge() /(0.1 + distanceSqr);
				
				
		}
		return value;	
	}
	@Override
	public SpatialDataControl getControlPanel() {
		return new SpatialDataControl(this, "Charge");
	}

}
