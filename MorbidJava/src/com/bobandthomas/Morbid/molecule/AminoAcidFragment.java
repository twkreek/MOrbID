package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.Dihedral;
import com.bobandthomas.Morbid.utils.Point3D;


public class AminoAcidFragment extends Fragment {
	/*
ID	sym sy3 Amino-Acid	Charge	Hydro	Mwt
1	A	Ala	L-Alanine	0	0.310	71.08
2	C	Cys	L-Cysteine	0	1.540	103.14
3	D	Asp	L-Aspartic acid	-1	-0.770	115.09
4	E	Glu	L-Glutamic acid	-1	-0.640	129.12
5	F	Phe	L-Phenylalanine	0	1.790	147.18
6	G	Gly	Glycine	0	0.000	57.05
7	H	His	L-Histidine	1	0.130	137.14
8	I	Ile	L-Isoleucine	0	1.800	113.16
9	K	Lys	L-Lysine	1	-0.990	128.17
10	L	Leu	L-Leucine	0	1.700	113.16
11	M	Met	L-Methionine	0	1.230	131.20
12	N	Asn	L-Asparagine	0	-0.600	114.10
13	P	Pro	L-Proline	0	0.720	97.12
14	Q	Gln	L-Glutamine	0	-0.220	128.13
15	R	Arg	L-Arginine	1	-1.010	156.19
	
	
	*/
	
	/*
	 *    C=O      c5
	 *    NH       NAmino
	 *    C - R    CAlpha - rGroup
	 *    C=O      cAmide
	 *    N		   n3
	 */
	Atom cAlpha;
	Atom cAmide;
	Atom nAmino;
	Atom rGroup;
	Point3D pC5, pN3; // use points for dihedrals - there will be no C5 on n terminus or N3 on c terminus
	Dihedral dihedralNAlpha;
	Dihedral dihedralAlphaAmide;
	String aaName;
	boolean is3N;
	boolean is5C;
	Substructure previous;
	Substructure next;
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
	public Substructure getPrevious() {
		return previous;
	}
	public AminoAcidFragment getNext()
	{
		if (next != null)
			return (AminoAcidFragment) next.getFragment();
		return null;
	}
	public String getAaName() {
		return aaName;
	}

	public void setAaName(String aaName) {
		this.aaName = aaName;
	}
	String fieldString(String title, CLoadableItem item, int indent)
	{
		
		String s = "";
		if (indent > 0)
			s += "\n";
		for (int i =0; i< indent+1; i++)
			s+= "    ";
		s+= title + ":" + (item ==null? "null" : item.getName());
		return s;
	}
	public String toString()
	{
		String s = getName() +" " + aaName;
		s+= fieldString("CA", cAlpha, 1);
		s+= fieldString("CCarb", cAmide, 0);
		s+= fieldString("NAmino", nAmino, 0);
		s+= fieldString("rCarbon", rGroup, 1);
		s+= fieldString("previous", previous, 1);
		s+= fieldString("next", next, 0);
		s+= "\n";
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
		previous= null;
		next = null;
	}
	@Override
	public String getName() {
		return "Amino Acid";
	}


	@Override
	public boolean doMatch()
	{
		MatcherList ml = new MatcherList();
		ml.add(new Matcher(Element.N));
		ml.add(new Matcher(Element.C));
		ml.add(new Matcher(MatchFragment.AMIDE));
		MatcherList ml2 = new MatcherList();
		ml2.add(new Matcher(Element.N));
		ml2.add(new Matcher(Element.C));
		ml2.add(new Matcher(MatchFragment.CARBOXYL));
		boolean amide = false;
		boolean carboxyl = false;
	
		if ((amide = Match(substructure.get(0), ml)) || ( carboxyl = Match(substructure.get(0), ml2)))
		{
			nAmino = matches.get(0);
			cAlpha = matches.get(1);
			cAmide = matches.get(2);
			
			for (Atom a: cAlpha.getBonded())
			{
				if (!matches.contains(a))
				{
					rGroup = a;
					break;
				}
			}
			for (Atom a: nAmino.getBonded())
				if (!matches.contains(a) && !a.isA(Element.H))
				{
					pC5 = new Point3D(a.Position());
					next = ((Peptide) substructure.getParentSet()).getSubstructure(a);
				}
			if (pC5 == null) 
			{
				// this is an N terminus. there is no adjacent carbon,
				pC5 = new Point3D(nAmino.Position().Sub(cAlpha.Position()));
				is3N = true; 
			}
			for (Atom a: cAmide.getBonded())
				if (!matches.contains(a) && !a.isA(Element.H))
				{
					pN3 = new Point3D(a.Position());
					if (amide)
						next = ((Peptide) substructure.getParentSet()).getSubstructure(a);
					if (carboxyl)
						next = null;
				}
			if (pN3 == null) 
			{
				// this is a C terminus. there is no adjacent nitrogen.
				pN3 = new Point3D(cAmide.Position().Sub(cAlpha.Position()));
				is5C = true;
			}

			dihedralNAlpha = new Dihedral(pC5, nAmino.Position(), cAlpha.Position(), cAmide.Position());
			dihedralAlphaAmide = new Dihedral(nAmino.Position(), cAlpha.Position(), cAmide.Position(), pN3);
			dihedralNAlpha.dihedral();
			dihedralAlphaAmide.dihedral();
			return true;
		}
		
		return false;
	}

}
