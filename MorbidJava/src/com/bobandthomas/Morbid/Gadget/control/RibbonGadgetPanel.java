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

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.Gadget.RibbonGadget;

// TODO: Auto-generated Javadoc
/**
 * The Class RibbonGadgetPanel.
 * 
 * @author Thomas Kreek
 */
public class RibbonGadgetPanel extends GadgetPanel {

	/** The rg. */
	RibbonGadget rg;
	
	/**
	 * Instantiates a new ribbon gadget panel.
	 * 
	 * @param g
	 *            the g
	 */
	public RibbonGadgetPanel(Gadget g) {
		
		super(g, "Ribbons");
		rg = (RibbonGadget) g;
		addColorBy();
		createCheckbox("Cylinders", rg.isCylinders());
		createSlider("Radius", 0,200, (int) (rg.getRadius()*100), true);
		
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Cylinders"))
		{
			rg.setCylinders(value == 1);
		}
		if (label.equals("Radius"))
		{
			rg.setRadius(value/100.0);
		}

	}

}
