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

import javax.swing.JLabel;

import com.bobandthomas.Morbid.Gadget.Surface3DGadget;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataSelector;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.wrapper.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Surface3DGadgetPanel.
 * 
 * @author Thomas Kreek
 */
public class Surface3DGadgetPanel extends GadgetPanel {

	/** The g3d. */
	Surface3DGadget g3d;
	
	/** The thr label. */
	JLabel thrLabel;
	
	/** The sds. */
	SpatialDataSelector sds;
	
	/**
	 * Instantiates a new surface3 d gadget panel.
	 * 
	 * @param g
	 *            the g
	 */
	public Surface3DGadgetPanel(Surface3DGadget g) {
		super(g, "3D Surface");
		g3d = g;
		createSlider("Threshold", 0, 100, (int)(g.getThresholdFraction()*100), true);
		thrLabel = (JLabel) getByName("Label Threshold");
		setThresholdLabel();
		createCheckbox("Solid Surface", g.isSolid());
		createCheckbox("Polar", g.isPolar());
		sds = new SpatialDataSelector(g3d, g3d.GetMolecule().getSpatialData(), "Display");
		sds.registerListener(this);
		activePanel.add(sds);
		
	}
	
	/**
	 * Sets the threshold label.
	 */
	private void setThresholdLabel()
	{
		thrLabel.setText(String.format("Threshold\n%4.2f %4.2f %4.2f", g3d.getSpatialData().getMin(), g3d.getThreshold(), g3d.getSpatialData().getMax()));
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		
		if (label.equals("Threshold"))
		{
			g3d.setThresholdFraction(value/100.0f);
			Logger.addMessage(g3d, String.format("Fraction: %5.3f, Threshold: %5.3f", g3d.getThresholdFraction(), g3d.getThreshold()));
			setThresholdLabel();
			return;
		}

		if (label.equals("Solid Surface"))
		{
			g3d.setSolid(value == 1);
			return;
		}
		if (label.equals("Polar"))
		{
			g3d.setPolar(value == 1);
			setThresholdLabel();
			
			return;
		}
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		return super.handleNotify(source);
	}


}
