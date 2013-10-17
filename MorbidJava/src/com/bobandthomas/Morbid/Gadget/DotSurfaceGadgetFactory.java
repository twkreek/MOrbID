package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.DotSurfaceGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class DotSurfaceGadgetFactory implements GadgetFactory {

	public DotSurfaceGadgetFactory() {
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

	@Override
	public String toString() {
		return "Dot Surface Gadget";
	}

}
