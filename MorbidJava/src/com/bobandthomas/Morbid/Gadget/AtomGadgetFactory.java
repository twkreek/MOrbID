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

import com.bobandthomas.Morbid.Gadget.control.AtomGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;


/**
 * A factory for creating AtomGadget objects.
 */
public class AtomGadgetFactory extends GadgetFactory {

	/**
	 * Instantiates a new atom gadget factory.
	 */
	public AtomGadgetFactory() {
		setName("Atom Gadget");
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#canCreate(com.bobandthomas.Morbid.molecule.Molecule)
	 */
	public boolean canCreate(Molecule m) {
		return (m.Atoms().size() > 0);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#createGadget()
	 */
	public Gadget createGadget() {
		return new AtomGadget();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#createPanel(com.bobandthomas.Morbid.Gadget.Gadget)
	 */
	public GadgetPanel createPanel(Gadget g) {
		return new AtomGadgetPanel((AtomGadget) g);
	}

}
