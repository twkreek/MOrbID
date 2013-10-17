package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.AtomGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class AtomGadgetFactory implements GadgetFactory {

	public AtomGadgetFactory() {
	}

	@Override
	public boolean canCreate(Molecule m) {
		return (m.Atoms().size() > 0);
	}

	@Override
	public Gadget createGadget() {
		return new AtomGadget();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new AtomGadgetPanel((AtomGadget) g);
	}

	@Override
	public String toString() {
		return "Atom Gadget";
	}

}
