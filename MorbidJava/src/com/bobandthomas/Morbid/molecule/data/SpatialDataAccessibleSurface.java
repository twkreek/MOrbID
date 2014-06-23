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
package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.utils.Point3D;

// TODO: Auto-generated Javadoc
/**
 * The Class SpatialDataAccessibleSurface.
 * 
 * @author Thomas Kreek
 */
public class SpatialDataAccessibleSurface extends SpatialData {

	/**
	 * Instantiates a new spatial data accessible surface.
	 * 
	 * @param mol
	 *            the mol
	 */
	public SpatialDataAccessibleSurface(Molecule mol) {
		super(mol);
		this.setLogicalRange(0.5, 5);
		setName("Accessible Volume");
		isSigned = false;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.SpatialData#Calculate(com.bobandthomas.Morbid.utils.Point3D)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.SpatialData#getControlPanel()
	 */
	@Override
	public SpatialDataControl getControlPanel() {
		return new SpatialDataControl(this, getName());
	}

}
