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
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.utils.CLoadableItem;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Gadget objects.
 */
public abstract class GadgetFactory extends CLoadableItem {
	
	/**
	 * Can create.
	 * 
	 * @param m
	 *            the m
	 * @return true, if successful
	 */
	public abstract boolean canCreate(Molecule m);
	
	/**
	 * Creates a new Gadget object.
	 * 
	 * @return the gadget
	 */
	public abstract Gadget createGadget();
	
	/**
	 * Creates a new Gadget object.
	 * 
	 * @param g
	 *            the g
	 * @return the gadget panel
	 */
	public abstract GadgetPanel createPanel(Gadget g);

}
