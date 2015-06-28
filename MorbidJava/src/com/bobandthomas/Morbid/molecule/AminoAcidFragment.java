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

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.Dihedral;
import com.bobandthomas.Morbid.utils.Point3D;


/**
 * AminoAcidFragment.
 * This is a templated fragment that: 
 * 1) finds a match for an amino acid, given an N-Terminus
 * 2) returns amino acid specific data, once a match is found
 * 
 * @author Thomas Kreek
 */
public class AminoAcidFragment extends Fragment {

	/*
	 * C=O c5 NH NAmino C - R CAlpha - rGroup C=O cAmide N n3
	 */
	/** The c alpha. */
	Atom cAlpha;
	
	/** The c amide. */
	Atom cAmide;
	
	/** The n amino. */
	Atom nAmino;
	
	/** The r group. */
	Atom rGroup;
	
	/** The p n3. */
	Point3D pC5, pN3; // use points for dihedrals - there will be no C5 on n
						// terminus or N3 on c terminus
	/** The dihedral n alpha. */
						Dihedral dihedralNAlpha;
	
	/** The dihedral alpha amide. */
	Dihedral dihedralAlphaAmide;
	
	/** The aa name. */
	String aaName;
	
	/** The is3 n. */
	boolean is3N;
	
	/** The is5 c. */
	boolean is5C;
	
	/** The previous. */
	AminoAcidFragment previous;
	
	/** The next. */
	AminoAcidFragment next;

	/**
	 * Gets the c alpha.
	 * 
	 * @return the c alpha
	 */
	public Atom getcAlpha() {
		return cAlpha;
	}

	/**
	 * Gets the c amide.
	 * 
	 * @return the c amide
	 */
	public Atom getcAmide() {
		return cAmide;
	}

	/**
	 * Gets the n amino.
	 * 
	 * @return the n amino
	 */
	public Atom getnAmino() {
		return nAmino;
	}

	/**
	 * Gets the dihedral n alpha.
	 * 
	 * @return the dihedral n alpha
	 */
	public Dihedral getDihedralNAlpha() {
		return dihedralNAlpha;
	}

	/**
	 * Gets the dihedral alpha amide.
	 * 
	 * @return the dihedral alpha amide
	 */
	public Dihedral getDihedralAlphaAmide() {
		return dihedralAlphaAmide;
	}

	/**
	 * Checks if is is3 n.
	 * 
	 * @return true, if is is3 n
	 */
	public boolean isIs3N() {
		return is3N;
	}

	/**
	 * Checks if is is5 c.
	 * 
	 * @return true, if is is5 c
	 */
	public boolean isIs5C() {
		return is5C;
	}

	/**
	 * Gets the previous.
	 * 
	 * @return the previous
	 */
	public AminoAcidFragment getPrevious() {
		return previous;
	}

	/**
	 * Gets the next.
	 * 
	 * @return the next
	 */
	public AminoAcidFragment getNext() {
		return next;
	}

	/**
	 * Gets the aa name.
	 * 
	 * @return the aa name
	 */
	public String getAaName() {
		return aaName;
	}

	/**
	 * Sets the aa name.
	 * 
	 * @param aaName
	 *            the new aa name
	 */
	public void setAaName(String aaName) {
		this.aaName = aaName;
	}

	/**
	 * Field string.
	 * 
	 * @param title
	 *            the title
	 * @param item
	 *            the item
	 * @param indent
	 *            the indent
	 * @return the string
	 */
	String fieldString(String title, CLoadableItem item, int indent) {

		String s = "";
		if (indent > 0)
			s += "\n";
		for (int i = 0; i < indent + 1; i++)
			s += "    ";
		s += title + ":" + (item == null ? "null" : item.getName());
		return s;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableItem#toString()
	 */
	public String toString() {
		String s = getName() + "(" + substructure.getName() + ") " + aaName;
		s += fieldString("CA", cAlpha, 1);
		s += fieldString("CCarb", cAmide, 0);
		s += fieldString("NAmino", nAmino, 0);
		s += fieldString("rCarbon", rGroup, 1);
		s += fieldString("previous", (previous==null? null :previous.substructure), 1);
		s += fieldString("next", (next == null? null :next.substructure), 0);
		s += "\n";
		return s;
	}

	/**
	 * Instantiates a new amino acid fragment.
	 * 
	 * @param a
	 *            the a
	 */
	public AminoAcidFragment(Substructure a) {
		super(a);
		reset();
	}

	/**
	 * Reset.
	 */
	public void reset() {
		cAlpha = null;
		cAmide = null;
		nAmino = null;
		rGroup = null;
		pN3 = null;
		pC5 = null;
		is3N = false;
		is5C = false;
		previous = null;
		next = null;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.Fragment#getName()
	 */
	@Override
	public String getName() {
		return "Amino Acid";
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.Fragment#doMatch()
	 */
	@Override
	public boolean doMatch() {
		MatcherList ml = new MatcherList();
		ml.add(new Matcher(Element.N));
		ml.add(new Matcher(Element.C));
		ml.add(new Matcher(MatchFragment.AMIDE));
		MatcherList ml2 = new MatcherList();
		ml2.add(new Matcher(Element.N));
		ml2.add(new Matcher(Element.C));
		ml2.add(new Matcher(MatchFragment.CARBOXYL));
		boolean isAmide = false;
		boolean isCarboxyl = false;

		if (!(isAmide = Match(substructure.get(0), ml))
				&& !(isCarboxyl = Match(substructure.get(0), ml2)))
		{
			// no amino acid found. this is an error condition. 
			return false;
		}
		nAmino = matches.get(0);
		cAlpha = matches.get(1);
		cAmide = matches.get(2);

		for (Atom a : nAmino.getBonded())
			if (!matches.contains(a) && !a.isA(Element.H)) {
				// find the carbon in the next substructure.
				pC5 = new Point3D(a.Position());
				previous = (AminoAcidFragment) ((SubstructureSet) substructure.getParentSet())
						.getSubstructure(a).getFragment();
				break;
			}

		if (pC5 == null) {
			// this is an N terminus. there is no adjacent carbon,
			// previous will be null
			pC5 = new Point3D(nAmino.Position().Sub(cAlpha.Position()));
			is3N = true;
		}

		for (Atom a : cAlpha.getBonded()) {
			// What's attached to the c alpha?
			if (!matches.contains(a)) {
				rGroup = a;
				break;
			}
		}

		for (Atom a : cAmide.getBonded())
			if (!matches.contains(a) && a.isA(Element.N)) {
				pN3 = new Point3D(a.Position());
				if (isAmide)
					next = (AminoAcidFragment) ((SubstructureSet) substructure.getParentSet())
							.getSubstructure(a).getFragment();
					if (next == this)
						next = null;
				if (isCarboxyl)
					next = null;
			}
		if (pN3 == null) {
			// this is a C terminus. there is no adjacent nitrogen.
			pN3 = new Point3D(cAmide.Position().Sub(cAlpha.Position()));
			is5C = true;
		}

		dihedralNAlpha = new Dihedral(pC5, nAmino.Position(),
				cAlpha.Position(), cAmide.Position());
		dihedralAlphaAmide = new Dihedral(nAmino.Position(), cAlpha.Position(),
				cAmide.Position(), pN3);
		dihedralNAlpha.dihedral();
		dihedralAlphaAmide.dihedral();
		return true;
	}

}
