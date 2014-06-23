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

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;
import javax.swing.JComponent;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.UI.ControlPanel;
import com.bobandthomas.Morbid.molecule.SubstructureSet;

// TODO: Auto-generated Javadoc
/**
 * The Class GadgetPanel.
 * 
 * @author Thomas Kreek
 */
public abstract class GadgetPanel extends ControlPanel implements ChangeListener,
		ItemListener {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -430660293743502924L;
	
	/** The map. */
	HashMap<JComponent, String> map;
	
	/** The gadget. */
	Gadget gadget;
	
	/** The banner. */
	JToggleButton banner;

	/**
	 * Instantiates a new gadget panel.
	 * 
	 * @param g
	 *            the g
	 * @param name
	 *            the name
	 */
	public GadgetPanel(Gadget g, String name) {
		super(name, true);
		gadget = g;
		createCheckbox("Visible", gadget.isVisible());
		createCheckbox("Transparent", gadget.isTransparent());
		createSlider("Transparency", 0, 100, 50, false);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	public void changeValue(String label, Integer value)
	{
		if (label.equals("Visible"))
		{
			gadget.setVisible(value == 1);
		}
		if(label.equals("Transparency"))
		{
			gadget.setTransparency(value/100.0);
		}
		if (label.equals("Transparent"))
		{
			gadget.setTransparent(value == 1);
		}
		if (label.equals("Color by"))
		{
			gadget.setCurrentColorOption(value);
		}
		if (label.equals("SubstructureFilter"))
		{
			SubstructureSet s = subs.get(value);
			if (s.getName().equals("All"))
				gadget.setSubstructureFilter(false);
			else
			{
				gadget.setSubstructureFilter(true);
				gadget.setSubstructureFilterList(s.getDefaultRep());
			}
		}
	
	}
	
	/**
	 * Adds the color by.
	 */
	public void addColorBy()
	{
		this.createLabel("Color By");
		createCombo(gadget.getColorOptions(true), "Color by", 0);
	}
	
	/** The subs. */
	ArrayList<SubstructureSet> subs = new ArrayList<SubstructureSet>();
	
	/**
	 * Adds the filter by.
	 */
	public void addFilterBy()
	{
		subs = new ArrayList<SubstructureSet>();
		for (SubstructureSet s : gadget.GetMolecule().getSubstructures())
		{
			subs.add(s);
		}
		createCombo(subs, "SubstructureFilter", 0);	
	}
	
}
