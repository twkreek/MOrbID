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

import java.util.Collection;
import java.util.HashMap;

import com.bobandthomas.Morbid.molecule.SubstructureRepList;
import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.ColorQuadPalette;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.Logger.MessageLevel;

// TODO: Auto-generated Javadoc
/**
 * The Class SubstructureSet.
 * 
 * @author Thomas Kreek
 * 
 *         A collection of atom lists, representing one type of substructure
 *         grouping, such as grouping by chain or amino acid type. Each atom
 *         should appear once and only once in each set.
 */
public class SubstructureSet extends CLoadableTable<Substructure> {

	/** The color map. */
	ColorQuadPalette colorMap;
	
	/** The current used color. */
	int currentUsedColor = 0;
	
	/** The default rep. */
	SubstructureRepList defaultRep;
	
	/** The description. */
	String description;
	
	/** The atom map. */
	HashMap<Atom, Substructure> atomMap = new HashMap<Atom,Substructure>();

	/**
	 * Instantiates a new substructure set.
	 * 
	 * @param name
	 *            the name
	 * @param description
	 *            the description
	 */
	public SubstructureSet(String name, String description) {
		setName(name);
		this.setUseByName(true);
		this.description = description;
	}

	/**
	 * Creates a default palette.
	 */
	public void createDefaultPalette()
	{
		colorMap = new ColorQuadPalette();
		setUseByName(true);
		colorMap.setPastelPalette(size());
		currentUsedColor = 0;
		
	}
	
	/**
	 * Match all.
	 */
	public void matchAll()
	{
		for (Substructure s: this)
		{
			Fragment frag = s.getFragment();
			if (s != null)
				frag.doMatch();
		}
	}
	
	/**
	 * Gets the default rep.
	 * 
	 * @return the default rep
	 */
	public SubstructureRepList getDefaultRep()
	{
		if (colorMap == null) createDefaultPalette();
		SubstructureRepList res = new SubstructureRepList(this);
		for (int i = 0; i < size(); i++)
		{
			res.get(this.get(i)).setColor(colorMap.get(i));
		}
		return new SubstructureRepList(this);
	}
	
	/**
	 * Gets the substructure.
	 * 
	 * @param a
	 *            the a
	 * @return the substructure
	 */
	public Substructure getSubstructure(Atom a)
	{
		return atomMap.get(a);
	}


	/**
	 * Gets the description.
	 * 
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the description.
	 * 
	 * @param description
	 *            the new description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#add(int, com.bobandthomas.Morbid.utils.CLoadableItem)
	 */
	@Override
	public void add(int arg0, Substructure arg1) {
		Logger.addMessage(this, "Wrong Add Called -SubstructureList", MessageLevel.ERROR);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Substructure> arg0) {
		Logger.addMessage(this, "Wrong Add Called -SubstructureList", MessageLevel.ERROR);
			return false;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#addAll(int, java.util.Collection)
	 */
	@Override
	public boolean addAll(int arg0, Collection<? extends Substructure> arg1) {
		Logger.addMessage(this, "Wrong Add Called -SubstructureList", MessageLevel.ERROR);
		return false;
	}

	/**
	 * Adds the by name.
	 * 
	 * @param ssName
	 *            the name of the substructure this atom will belong to.
	 * @param a
	 *            the atom to add
	 * @return the substructure
	 */
	public Substructure addByName(String ssName, Atom a) {
		Substructure subst = this.getByName(ssName);
		if (subst == null) {
			subst = new Substructure();
			subst.setName(ssName);
			subst.setReParent(false); // Atoms added to this list are not owned by
									// this list. just keeping references.
			subst.setListColor(new ColorQuad(Math.random(), Math.random(), Math
					.random()));
			add(subst);
		}
		atomMap.put(a,  subst);
		subst.add(a);
		return subst;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#toString()
	 */
	public String toString()
	{
		return getName();
	}
}
