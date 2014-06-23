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

import javax.swing.JDialog;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.UI.TablePanel;
import com.bobandthomas.Morbid.graphics.MaterialList;
import com.bobandthomas.Morbid.molecule.AtomTypeList;
import com.bobandthomas.Morbid.molecule.MoleculePropertyList;
import com.bobandthomas.Morbid.molecule.Substructure;

// TODO: Auto-generated Javadoc
/**
 * The Class GadgetHUDPanel.
 * 
 * @author Thomas Kreek
 */
public class GadgetHUDPanel extends GadgetPanel {


	/**
	 * Instantiates a new gadget hud panel.
	 * 
	 * @param g
	 *            the g
	 * @param name
	 *            the name
	 */
	public GadgetHUDPanel(Gadget g, String name) {
		super(g, name);
		createButton("Property List");
		createButton("Atom List");
		createButton("Materials");
		createButton("Atom Types");
		
	}
	
	

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.control.GadgetPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Property List"))
		{
			TablePanel<MoleculePropertyList> ple = new TablePanel<MoleculePropertyList>(gadget.GetMolecule().getPropList());
			JDialog dlg = ple.doDialog();
			dlg.setVisible(true);
			return;
		}
		if (label.equals("Atom List"))
		{
			TablePanel<Substructure> ple = new TablePanel<Substructure>(gadget.GetMolecule().Atoms());
			JDialog dlg = ple.doDialog();
			dlg.setVisible(true);
			return;
			
		}
		if (label.equals("Materials"))
		{
			TablePanel<MaterialList> ple = new TablePanel<MaterialList>(MaterialList.getOne());
			JDialog dlg = ple.doDialog();
			dlg.setVisible(true);
			return;
			
		}
		if (label.equals("Atom Types"))
		{
			TablePanel<AtomTypeList> ple = new TablePanel<AtomTypeList>(AtomTypeList.getOne());
			JDialog dlg = ple.doDialog();
			dlg.setVisible(true);
			return;
			
		}


	}
}
