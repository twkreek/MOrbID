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
package com.bobandthomas.Morbid.molecule.data.control;

import com.bobandthomas.Morbid.UI.MorbidPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.MorbidEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class SpatialDataControl.
 * 
 * @author Thomas Kreek
 */
public class SpatialDataControl extends MorbidPanel {
	
	/** The molecule. */
	Molecule molecule;
	
	/** The sd. */
	SpatialData sd;
	
	/**
	 * Instantiates a new spatial data control.
	 * 
	 * @param sd
	 *            the sd
	 * @param name
	 *            the name
	 */
	public SpatialDataControl(SpatialData sd, String name) {
		super(name);

		this.sd = sd;
		molecule = sd.getMolecule();
		int[] resolutions = {5,10,30,50,100,200,500};				
		createSpinner("Resolution", resolutions, sd.sideX);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("Resolution"))
		{
			sd.setSize(value);
			notifyChange(new MorbidEvent(this));
			return;
		}

	}

}
