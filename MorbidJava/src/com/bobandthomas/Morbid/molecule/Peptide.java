package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

/**
 * @author Thomas Kreek
 *	Peptide is an SubstructureSet that is specific for a 
 *  substructure set of individual amino acids - each atom list
 *  will have one residue on the protein chain. 
 */
public class Peptide extends SubstructureSet {


	/**
	 * @param string 
	 * 
	 */
	ArrayList<AminoAcidFragment> n3list;
	public Peptide(String name, String description) {
		super(name, description);
		n3list = new ArrayList<AminoAcidFragment>();
	}

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

	public ArrayList<AminoAcidFragment> getN3list() {
		return n3list;
	}

	@Override
	public boolean add(Substructure subst) {
		subst.setFragment(new AminoAcidFragment(subst));
		
		return super.add(subst);
	}
	
	public void printMatches()
	{
		for (Substructure subst : this)
		{
			AminoAcidFragment frag = (AminoAcidFragment) subst.getFragment();
			System.out.println(frag.toString());
		}
	}

	public void addByName(String name, String AAname, Atom a)
	{		
		Substructure subst = super.addByName(name, a);
		AminoAcidFragment frag = (AminoAcidFragment) subst.getFragment();
		frag.setAaName(AAname);
	}
	

}
