package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public interface GadgetFactory {
	
	public boolean canCreate(Molecule m);
	public Gadget createGadget();
	public GadgetPanel createPanel(Gadget g);

}
