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

import com.bobandthomas.Morbid.Gadget.BondGadget;
import com.bobandthomas.Morbid.Gadget.BondGadget.BondColor;
import com.bobandthomas.Morbid.Gadget.BondGadget.BondRep;
import com.bobandthomas.Morbid.Gadget.Gadget;

// TODO: Auto-generated Javadoc
/**
 * The Class BondGadgetPanel.
 * 
 * @author Thomas Kreek
 */
public class BondGadgetPanel extends GadgetPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -7698093079935344034L;

	/**
	 * Create the panel.
	 */
	BondGadget bg;
	
	/**
	 * Instantiates a new bond gadget panel.
	 * 
	 * @param gadget
	 *            the gadget
	 */
	public BondGadgetPanel (BondGadget gadget) {
		super((Gadget) gadget, "BondGadget");
		bg = gadget;
		sideBySide();
		createCheckbox("Order", bg.isLabelBO());
		createCheckbox("Length", bg.isLabelDistance());
		endSideBySide();
				
		createEnumCombo(BondRep.values(), "Bond Rep", bg.getRep());
		createEnumCombo(BondColor.values(), "Bond Color", bg.getColorBy());
				
		createSlider("Bond Radius", 0, 200, (int) (bg.getBondScale()*1000), true);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Order"))
		{
			bg.setLabelBO(value == 1);
			return;
		}
		if (label.equals("Length"))
		{
			bg.setLabelDistance(value == 1);
		}
		if (label.equals("Bond Radius"))
		{
			bg.setBondScale(value/1000.0);
			return;
		}
		if (label.equals("Bond Rep"))
		{
			bg.setRep(BondRep.fromInt(value));
			return;
		}
		if (label.equals("Bond Color"))
		{
			bg.setColorBy(BondColor.fromInt(value));
			return;
		}
	}

}
