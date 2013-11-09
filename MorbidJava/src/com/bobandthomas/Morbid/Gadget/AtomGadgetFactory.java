package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.AtomGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class AtomGadgetFactory extends GadgetFactory {

	public AtomGadgetFactory() {
		setName("Atom Gadget");
	}

	public boolean canCreate(Molecule m) {
		return (m.Atoms().size() > 0);
	}

	public Gadget createGadget() {
		return new AtomGadget();
	}

	public GadgetPanel createPanel(Gadget g) {
		return new AtomGadgetPanel((AtomGadget) g);
	}

}
