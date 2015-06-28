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
 * The Class SpatialDataCharge.
 * 
 * @author Thomas Kreek
 */
public class SpatialDataCharge extends SpatialData {

	/**
	 * The Class SpatialDataType.
	 * 
	 * @author Thomas Kreek
	 */
	static class SpatialDataType
	{
		
		/**
		 * Creates a.
		 * 
		 * @param m
		 *            the m
		 * @return the spatial data
		 */
		static public  SpatialData create(Molecule m)
		{
			return new SpatialDataCharge(m);
		}
	}
	
	/**
	 * Instantiates a new spatial data charge.
	 * 
	 * @param m
	 *            the m
	 */
	public SpatialDataCharge(Molecule m)
	{
		super(m);
		setName("Charge");
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.SpatialData#Calculate(com.bobandthomas.Morbid.utils.Point3D)
	 */
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
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.SpatialData#getControlPanel()
	 */
	@Override
	public SpatialDataControl getControlPanel() {
		return new SpatialDataControl(this, getName());
	}

}
