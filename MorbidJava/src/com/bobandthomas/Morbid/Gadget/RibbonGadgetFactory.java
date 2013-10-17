package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.RibbonGadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.SubstructureMap;
import com.bobandthomas.Morbid.molecule.SubstructureMap.SubstructureType;

public class RibbonGadgetFactory implements GadgetFactory {

	public RibbonGadgetFactory() {
	}

	@Override
	public boolean canCreate(Molecule m) {
		SubstructureMap ssm = m.getSubstructures();
		if (ssm == null) return false;
		
		if( ssm.get(SubstructureType.RESNUMS) == null) return false;
		return true;
	}

	@Override
	public Gadget createGadget() {
		return new RibbonGadget();
	}

	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new RibbonGadgetPanel((RibbonGadget) g);
		}

	@Override
	public String toString() {
		return "Ribbons";
	}

}
