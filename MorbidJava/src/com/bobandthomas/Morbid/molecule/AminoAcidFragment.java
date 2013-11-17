package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.Dihedral;
import com.bobandthomas.Morbid.utils.Point3D;

public class AminoAcidFragment extends Fragment {

	/*
	 * C=O c5 NH NAmino C - R CAlpha - rGroup C=O cAmide N n3
	 */
	Atom cAlpha;
	Atom cAmide;
	Atom nAmino;
	Atom rGroup;
	Point3D pC5, pN3; // use points for dihedrals - there will be no C5 on n
						// terminus or N3 on c terminus
	Dihedral dihedralNAlpha;
	Dihedral dihedralAlphaAmide;
	String aaName;
	boolean is3N;
	boolean is5C;
	AminoAcidFragment previous;
	AminoAcidFragment next;

	public Atom getcAlpha() {
		return cAlpha;
	}

	public Atom getcAmide() {
		return cAmide;
	}

	public Atom getnAmino() {
		return nAmino;
	}

	public Dihedral getDihedralNAlpha() {
		return dihedralNAlpha;
	}

	public Dihedral getDihedralAlphaAmide() {
		return dihedralAlphaAmide;
	}

	public boolean isIs3N() {
		return is3N;
	}

	public boolean isIs5C() {
		return is5C;
	}

	public AminoAcidFragment getPrevious() {
		return previous;
	}

	public AminoAcidFragment getNext() {
		return next;
	}

	public String getAaName() {
		return aaName;
	}

	public void setAaName(String aaName) {
		this.aaName = aaName;
	}

	String fieldString(String title, CLoadableItem item, int indent) {

		String s = "";
		if (indent > 0)
			s += "\n";
		for (int i = 0; i < indent + 1; i++)
			s += "    ";
		s += title + ":" + (item == null ? "null" : item.getName());
		return s;
	}

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

	public AminoAcidFragment(Substructure a) {
		super(a);
		reset();
	}

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

	@Override
	public String getName() {
		return "Amino Acid";
	}

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
