package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetFieldLinesPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;

public class GadgetFieldLinesFactory implements GadgetFactory {

	public GadgetFieldLinesFactory() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean canCreate(Molecule m) {
		return m.getSpatialData().size() > 0;
	}

	@Override
	public Gadget createGadget() {
		return new GadgetFieldLines();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new GadgetFieldLinesPanel((GadgetFieldLines) g);
	}

	@Override
	public String toString() {
		return "Gadget FieldLines";
	}

}
