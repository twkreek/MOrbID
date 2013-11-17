package com.bobandthomas.Morbid.molecule;

import java.util.Collection;
import java.util.HashMap;

import com.bobandthomas.Morbid.UI.Logger;
import com.bobandthomas.Morbid.UI.Logger.MessageLevel;
import com.bobandthomas.Morbid.molecule.SubstructureRepList;
import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.ColorQuadPalette;

/**
 * @author Thomas Kreek
 * 
 * A collection of atom lists, representing one type of substructure grouping, such as grouping by
 * chain or amino acid type.  
 * Each atom should appear once and only once in each set.
 * 
 *
 */
public class SubstructureSet extends CLoadableSet<Substructure> {

	ColorQuadPalette colorMap;
	int currentUsedColor = 0;
	SubstructureRepList defaultRep;
	String description;
	HashMap<Atom, Substructure> atomMap = new HashMap<Atom,Substructure>();

	public SubstructureSet(String name, String description) {
		setName(name);
		this.setUseByName(true);
		this.description = description;
	}

	public void createDefaultPalette()
	{
		colorMap = new ColorQuadPalette();
		setUseByName(true);
		for (int i = 0; i < size(); i++) {
			ColorQuad cq = new ColorQuad((i % 7) / 7.1, (i % 3 + 1) / 4.0,
					(i % 2) / 2.1);
			colorMap.add(cq);
		}
		currentUsedColor = 0;
		
	}
	
	public void matchAll()
	{
		for (Substructure s: this)
		{
			Fragment frag = s.getFragment();
			if (s != null)
				frag.doMatch();
		}
	}
	
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
	public Substructure getSubstructure(Atom a)
	{
		return atomMap.get(a);
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public void add(int arg0, Substructure arg1) {
		Logger.addMessage(this, "Wrong Add Called -SubstructureList", MessageLevel.ERROR);
	}

	@Override
	public boolean addAll(Collection<? extends Substructure> arg0) {
		Logger.addMessage(this, "Wrong Add Called -SubstructureList", MessageLevel.ERROR);
			return false;
	}

	@Override
	public boolean addAll(int arg0, Collection<? extends Substructure> arg1) {
		Logger.addMessage(this, "Wrong Add Called -SubstructureList", MessageLevel.ERROR);
		return false;
	}

	/**
	 * @param ssName
	 *            the name of the substructure this atom will belong to.
	 * @param a
	 *            the atom to add
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
	public String toString()
	{
		return getName();
	}
}
