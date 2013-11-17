package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.UI.Logger;
import com.bobandthomas.Morbid.utils.CLoadableSet;

/**
 * @author Thomas Kreek
 *
 * SubstructureMap contains a set of AtomListSets that each contain a set of AtomLists. 
 * for any one molecule, there will be only one substructure map.
 * Each substructure map contains a map of <name, atomlistset> pairs = these define the type of substructure grouping
 * Each AtomListSet contains a mapping of <name, atomlist> pairs = these define the unique membership classes within the above grouping
 * Each AtomList contains unique assignements of atoms to this membership class. While atom assignment should be unique, it is not enforced.
 * example
 * SSMap - singleton
 * contains { "residues", "chains", "user selection 1"}
 * ALSet for "Residues" would contain {"VAL", "SER", "LEU", etc)
 * each of these would contain an AtomList which contains the atoms in those groups..
 */
public class SubstructureMap extends CLoadableSet<SubstructureSet> {
	
	public enum SubstructureType
	{
		 AMINOACIDS ("AminoAcids"),
		 RESIDUES ("Residues"),
		 HETEROSTRUCTURES("HeteroStructures"),
		 RESNUMS ("ResNums"),
		 WATER ("Water");
		 String name;
		 SubstructureType(String text)
		{
			name = text;
		}
		public String text()
		{
			return name;
		}
	}
	
	/*
	 */
	SubstructureMap()
	{
		this.setUseByName(true);
	}
	
	/**
	 * @param setName the class of substructure grouping 
	 * @param subsName The specific substructure name
	 * @param at	Atom to be added.
	 */
	public SubstructureSet get(SubstructureType type)
	{
		return getByName(type.name);
	}
	public void addAtomToSubstructure(String setName, String subsName, Atom at)
	{
		SubstructureSet al = this.getByName(setName);
		if (al == null)
		{
			al = new SubstructureSet(setName, "default");
			add(al);
		}
		al.addByName(subsName, at);
	}
	/**
	 * debugging routine to print all substructure classes and substructures
	 * @param full if true, print all atoms in each substructure, too.
	 */
	public void printList(boolean full)
	{
		String text = "SubstructureMap\n";
		for (SubstructureSet als : this)
		{
			text += "     "+ als.getName()+ "\n";
			for (Substructure al: als)
			{
				text+="             "+ al.getName()+ "\n";
				if (full)
				{
					int atomcount = 0;
					for (Atom a: al)
					{
						if (atomcount >= 10)
						{
							atomcount = 0;
							System.out.println();
						}
						atomcount++;
						text += a.getName() +":"+ a.getID() +", ";
					}
					text += "\n";
				}
			}
		}
		Logger.addMessage(this, text);
	}

}
