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

import com.bobandthomas.Morbid.Gadget.AtomGadget;

import javax.swing.JCheckBox;

import com.bobandthomas.Morbid.Gadget.AtomGadget.AtomRepType;
import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.molecule.SubstructureMap;
import com.bobandthomas.Morbid.molecule.UI.SubstructureRepListEditor;
import com.bobandthomas.Morbid.molecule.UI.SubstructureSetChooser;


/**
 * The Class AtomGadgetPanel.
 * Java Swing control panel
 * 
 * @author Thomas Kreek
 */
public class AtomGadgetPanel extends GadgetPanel {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -4307146117791598304L;
	/**
	 * Create the panel.
	 */
	AtomGadget ag;
	
	/** The chckbxlabels. */
	JCheckBox chckbxlabels;
	
	/**
	 * Instantiates a new atom gadget panel.
	 * 
	 * @param a
	 *            the a
	 */
	public AtomGadgetPanel(AtomGadget a) {
		super((Gadget) a, "Atom Gadget");
		ag = a;
		createLabel("Show");
		createCheckbox("Labels", ag.isLabels());
		
		createCheckbox("Metals", ag.isShowMetals());
		
		createCheckbox("Hydrogens", ag.isShowHydrogens());
		
		addColorBy();
		addFilterBy();
				
		createSlider("RadiusScale", 0, 400, (int) (ag.getAtomScale()*100), true);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label,value);
		if (label.equals("Labels"))
		{
			ag.setLabels(value == 1);
			return;
		}
		
		if (label.equals("Metals"))
		{
			ag.setShowMetals(value == 1);
			return;
		}
		if (label.equals("Hydrogens"))
		{
			ag.setShowHydrogens(value == 1);
			return;
		}
		
		if (label.equals("RadiusScale"))
		{
			ag.setAtomScale(value/100.0f);
			return;
		}
		
		if (label.equals("Atom Rep"))
		{
			ag.setRep(AtomRepType.fromInt(value));
			return;
		}
		if (label.equals("Substructure Filter"))
		{
			SubstructureMap ssm = ag.GetMolecule().getSubstructures();
			
			if (ag.isSubstructureFilter() == (value == 1))
				return; // don't change it again
			ag.setSubstructureFilter(value == 1);
			if (value != 1)
				return;
			
			
			SubstructureSetChooser dlg = new SubstructureSetChooser(ssm, null);
			ag.setSubstructureFilterList(dlg.selectSubstructureRep(ssm));
			
			SubstructureRepListEditor rle = new SubstructureRepListEditor(ssm, ag.getSubstructureFilterList());
			rle.setVisible(true);
			

		}
	}
		

}
