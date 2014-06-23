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
package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;
import java.util.HashMap;

import com.bobandthomas.Morbid.utils.CLoadableSet;


// TODO: Auto-generated Javadoc
/**
 * The Class BondList.
 * 
 * @author Thomas Kreek
 */
public class BondList extends CLoadableSet<Bond> {
	
	/** The map. */
	HashMap<String, Bond> map = new HashMap <String, Bond>();
	
	/** The next id. */
	int nextID = 0;
	
	/**
	 * Instantiates a new bond list.
	 */
	public BondList() {
		this.setReParent(true);
	}

	/**
	 * Adds the bond.
	 * 
	 * @param b
	 *            the b
	 * @return the long
	 */
	long AddBond(Bond b)
	{
		nextID ++;
		b.setID(nextID);
		add(b);
		map.put(b.getName(), b);
		return nextID;
	}
	
	/**
	 * Gets the bond.
	 * 
	 * @param a1
	 *            the a1
	 * @param a2
	 *            the a2
	 * @return the bond
	 */
	Bond getBond(Atom a1, Atom a2)
	{
		return map.get(Bond.AtNoHash(a1.ID(), a2.ID()));
	}
	
	/**
	 * Gets the bond.
	 * 
	 * @param a1
	 *            the a1
	 * @param a2
	 *            the a2
	 * @return the bond
	 */
	Bond getBond(int a1, int a2)
	{
		return map.get(Bond.AtNoHash(a1,a2));	
	}
	
	/**
	 * Gets the bonds of atom.
	 * 
	 * @param atom
	 *            the atom
	 * @return the bonds of atom
	 */
	Bond[] getBondsOfAtom(Atom atom)
	{
		ArrayList<Bond> bonds = new ArrayList<Bond>();
		for (Bond b : this)
		{
			if ((b.atom1 == atom) || (b.atom2 == atom))
				bonds.add(b);
		}
		return (Bond[]) bonds.toArray();
	}
	
}
