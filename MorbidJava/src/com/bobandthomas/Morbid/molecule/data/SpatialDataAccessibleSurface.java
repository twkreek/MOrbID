package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.utils.Point3D;

public class SpatialDataAccessibleSurface extends SpatialData {

	public String getName() { return "Accessible Volume"; }
	public SpatialDataAccessibleSurface(Molecule mol) {
		super(mol);
		this.setLogicalRange(0.5, 5);
	}

	@Override
	public double Calculate(Point3D p) {
		double dSquared = 1000000;
		for (Atom a: molecule)
		{
			double dist = a.Position().distanceSquared(p) - a.Radius()*a.Radius();
			if (dist < dSquared) dSquared= dist;
		}
		return Math.sqrt(dSquared);
	}

}
