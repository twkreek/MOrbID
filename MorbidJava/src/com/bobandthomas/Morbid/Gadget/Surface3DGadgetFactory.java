package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.Surface3DGadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class Surface3DGadgetFactory implements GadgetFactory {

	public Surface3DGadgetFactory() {
		// TODO Auto-generated constructor stub
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

	@Override
	public String toString() {
		return "Surface 3D";
	}

}
