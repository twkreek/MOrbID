package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.utils.CLoadableItem;

public abstract class GadgetFactory extends CLoadableItem {
	
	public abstract boolean canCreate(Molecule m);
	public abstract Gadget createGadget();
	public abstract GadgetPanel createPanel(Gadget g);

}
