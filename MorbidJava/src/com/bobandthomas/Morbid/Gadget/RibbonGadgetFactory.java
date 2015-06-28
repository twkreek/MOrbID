/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.RibbonGadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.SubstructureMap;
import com.bobandthomas.Morbid.molecule.SubstructureMap.SubstructureType;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating RibbonGadget objects.
 */
public class RibbonGadgetFactory extends GadgetFactory {

	/**
	 * Instantiates a new ribbon gadget factory.
	 */
	public RibbonGadgetFactory() {
		setName("Ribbons");
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#canCreate(com.bobandthomas.Morbid.molecule.Molecule)
	 */
	@Override
	public boolean canCreate(Molecule m) {
		SubstructureMap ssm = m.getSubstructures();
		if (ssm == null) return false;
		
		if( ssm.get(SubstructureType.RESNUMS) == null) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#createGadget()
	 */
	@Override
	public Gadget createGadget() {
		return new RibbonGadget();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#createPanel(com.bobandthomas.Morbid.Gadget.Gadget)
	 */
	@Override
	public GadgetPanel createPanel(Gadget g) {
		return new RibbonGadgetPanel((RibbonGadget) g);
		}

}
