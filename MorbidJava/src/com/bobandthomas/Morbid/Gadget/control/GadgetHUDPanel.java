package com.bobandthomas.Morbid.Gadget.control;

import javax.swing.JDialog;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.molecule.UI.PropertyListEditor;
import com.bobandthomas.Morbid.molecule.UI.SubstructureEditor;

public class GadgetHUDPanel extends GadgetPanel {


	public GadgetHUDPanel(Gadget g, String name) {
		super(g, name);
		createButton("Property List");
		createButton("Atom List");
	}
	
	

	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Property List"))
		{
			PropertyListEditor ple = new PropertyListEditor(gadget.GetMolecule().getPropList());
			JDialog dlg = ple.doDialog();
			dlg.setVisible(true);
			return;
		}
		if (label.equals("Atom List"))
		{
			SubstructureEditor ple = new SubstructureEditor(gadget.GetMolecule().Atoms());
			JDialog dlg = ple.doDialog();
			dlg.setVisible(true);
			return;
			
		}


	}
}
