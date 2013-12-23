package com.bobandthomas.Morbid.molecule.data;

import javax.swing.JPanel;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.utils.Point3D;

public class SpatialDataAccessibleSurface extends SpatialData {

	public SpatialDataAccessibleSurface(Molecule mol) {
		super(mol);
		this.setLogicalRange(0.5, 5);
		setName("Accessible Volume");
		isSigned = false;
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
	@Override
	public SpatialDataControl getControlPanel(JPanel parentPanel) {
		return new SpatialDataControl(this, getName(), parentPanel);
	}

}
