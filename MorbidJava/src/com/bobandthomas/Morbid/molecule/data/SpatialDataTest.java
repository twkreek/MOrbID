package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.utils.Point3D;

public class SpatialDataTest extends SpatialData {
	
	public SpatialDataTest(Molecule molecule) {
		super(molecule);
	}

	@Override
	public String getName()
	{
		return "Test Cube";
	}

	@Override
	public double Calculate(Point3D p) {
		
		double val=0.0;
		if (!molecule.HasCharges())
			return p.Length();
		for (Atom a : molecule.Atoms())
		{
			
			double distanceSqr = p.distanceSquared(a.pos);
			val += a.getCharge() /(0.1 + distanceSqr);
		}
		return val;
	}

}
