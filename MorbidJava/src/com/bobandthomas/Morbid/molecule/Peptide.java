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

import com.bobandthomas.Morbid.wrapper.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class Peptide.
 * 
 * @author Thomas Kreek Peptide is an SubstructureSet that is specific for a
 *         substructure set of individual amino acids - each atom list will have
 *         one residue on the protein chain.
 */
public class Peptide extends SubstructureSet {


	/** The n3list. */
	ArrayList<AminoAcidFragment> n3list;
	
	/**
	 * Instantiates a new peptide.
	 * 
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 */
	public Peptide(String name, String description) {
		super(name, description);
		n3list = new ArrayList<AminoAcidFragment>();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.SubstructureSet#matchAll()
	 */
	@Override
	public void matchAll() {
		super.matchAll();
		for (Substructure s : this)
		{
			AminoAcidFragment frag = ((AminoAcidFragment) s.getFragment());
			if (frag.is3N)
				n3list.add(frag);
		}
	}

	/**
	 * Gets the n3list.
	 * 
	 * @return the n3list
	 */
	public ArrayList<AminoAcidFragment> getN3list() {
		return n3list;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#add(com.bobandthomas.Morbid.utils.CLoadableItem)
	 */
	@Override
	public boolean add(Substructure subst) {
		subst.setFragment(new AminoAcidFragment(subst));
		
		return super.add(subst);
	}
	
	/**
	 * Prints the matches.
	 */
	public void printMatches()
	{
		for (Substructure subst : this)
		{
			AminoAcidFragment frag = (AminoAcidFragment) subst.getFragment();
			Logger.addMessage(frag, frag.toString());
		}
	}

	/**
	 * Adds the by name.
	 * 
	 * @param name
	 *            the name
	 * @param AAname
	 *            the a aname
	 * @param a
	 *            the a
	 */
	public void addByName(String name, String AAname, Atom a)
	{		
		Substructure subst = super.addByName(name, a);
		AminoAcidFragment frag = (AminoAcidFragment) subst.getFragment();
		frag.setAaName(AAname);
	}
	

}
