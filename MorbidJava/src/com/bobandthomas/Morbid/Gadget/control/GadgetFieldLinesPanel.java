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
package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.GadgetFieldLines;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataSelector;

// TODO: Auto-generated Javadoc
/**
 * The Class GadgetFieldLinesPanel.
 * 
 * @author Thomas Kreek
 */
public class GadgetFieldLinesPanel extends GadgetPanel {
	
	/** The g3d. */
	GadgetFieldLines g3d;
	
	/** The sds. */
	SpatialDataSelector sds;
	
	/** The increments. */
	int increments[]={1,2,5,10};
	
	/**
	 * Instantiates a new gadget field lines panel.
	 * 
	 * @param g
	 *            the g
	 */
	public GadgetFieldLinesPanel(GadgetFieldLines g) {
		super(g, "Field Lines");
		g3d = g;
		
		createSlider("Threshold", 0, 100, (int)(g.getThreshold()*100), true);
		createSlider("Scale", 0, 100, (int)(g3d.getScale()*100), true);
		createSpinner("Increment" , increments, g3d.getIncrement());
		sds = new SpatialDataSelector(g3d, g3d.GetMolecule().getSpatialData(), "Display");
		sds.registerListener(this);
		activePanel.add(sds);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Threshold"))
		{
			g3d.setThreshold(value/100.0f);
			return;
		}
		if (label.equals("Scale"))
		{
			g3d.setScale(value/100.0f);
			return;
		}
		if (label.equals("Increment"))
		{
			g3d.setIncrement(value);
		}

	}

}
