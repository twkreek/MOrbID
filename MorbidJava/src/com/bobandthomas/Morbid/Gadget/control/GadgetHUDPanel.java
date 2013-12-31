package com.bobandthomas.Morbid.Gadget.control;

import javax.swing.JDialog;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.UI.TablePanel;
import com.bobandthomas.Morbid.graphics.MaterialList;
import com.bobandthomas.Morbid.molecule.AtomTypeList;
import com.bobandthomas.Morbid.molecule.MoleculePropertyList;
import com.bobandthomas.Morbid.molecule.Substructure;

public class GadgetHUDPanel extends GadgetPanel {


	public GadgetHUDPanel(Gadget g, String name) {
		super(g, name);
		createButton("Property List");
		createButton("Atom List");
		createButton("Materials");
		createButton("Atom Types");
		
	}
	
	

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
