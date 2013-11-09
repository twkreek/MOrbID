package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.DotSurfaceGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class DotSurfaceGadgetFactory extends GadgetFactory {

	public DotSurfaceGadgetFactory() {
		setName("Dot Surface Gadget");
	}

	@Override
	public boolean canCreate(Molecule m) {
		return m.Atoms().size() > 0;
	}

	@Override
	public Gadget createGadget() {
		return new DotSurfaceGadget();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new DotSurfaceGadgetPanel((DotSurfaceGadget) g);
	}

}
