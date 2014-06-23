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

import com.bobandthomas.Morbid.Gadget.DotSurfaceGadget;

// TODO: Auto-generated Javadoc
/**
 * The Class DotSurfaceGadgetPanel.
 * 
 * @author Thomas Kreek
 */
public class DotSurfaceGadgetPanel extends GadgetPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8399439673593429594L;
	
	/** The dsg. */
	DotSurfaceGadget dsg;

	/**
	 * Instantiates a new dot surface gadget panel.
	 * 
	 * @param g
	 *            the g
	 */
	public DotSurfaceGadgetPanel(DotSurfaceGadget g) {
		super(g, "Dot Surface");
		dsg = g;
		
		createEnumCombo(DotSurfaceGadget.DotsColorBy.values(), "Color By", g.getColorBy());
		createSlider("RadiusScale", 0, 200, (int) (dsg.getRadiusScale()*100), true);
	
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {

		super.changeValue(label, value);
		
		if (label.equals("RadiusScale"))
		{
			dsg.setRadiusScale(value/100.0f);
			return;
		}
		if (label.equals("Color By"))
		{
			dsg.setColorBy(DotSurfaceGadget.DotsColorBy.fromInt(value));
			return;
		}
	}	
	

}
