package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetHUDPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class GadgetHUDFactory extends GadgetFactory {

	public GadgetHUDFactory() {
		setName("HUD");
	}

	@Override
	public boolean canCreate(Molecule m) {
		return true;
	}

	@Override
	public Gadget createGadget() {
		return new GadgetHUD();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new GadgetHUDPanel(g, g.getName());
	}

}
