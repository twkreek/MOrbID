package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.BondGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class BondGadgetFactory extends GadgetFactory {

	public BondGadgetFactory() {
		setName("Bond Gadget");
	}

	@Override
	public boolean canCreate(Molecule m) {
		return m.Bonds().size() > 0;
	}

	@Override
	public Gadget createGadget() {
		return new BondGadget();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new BondGadgetPanel((BondGadget) g);
	}

}
