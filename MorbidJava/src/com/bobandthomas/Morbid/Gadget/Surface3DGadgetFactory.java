package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.Surface3DGadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class Surface3DGadgetFactory extends GadgetFactory {

	public Surface3DGadgetFactory() {
		setName("Surface 3D");
	}

	@Override
	public boolean canCreate(Molecule m) {
		return m.getSpatialData().size() > 0;
	}

	@Override
	public Gadget createGadget() {
		return new Surface3DGadget();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new Surface3DGadgetPanel((Surface3DGadget) g);
	}

}
