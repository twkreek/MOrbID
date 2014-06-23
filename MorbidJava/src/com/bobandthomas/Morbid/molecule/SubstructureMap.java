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

import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.wrapper.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class SubstructureMap.
 * 
 * @author Thomas Kreek
 * 
 *         SubstructureMap contains a set of AtomListSets that each contain a
 *         set of AtomLists. for any one molecule, there will be only one
 *         substructure map. Each substructure map contains a map of <name,
 *         atomlistset> pairs = these define the type of substructure grouping
 *         Each AtomListSet contains a mapping of <name, atomlist> pairs = these
 *         define the unique membership classes within the above grouping Each
 *         AtomList contains unique assignements of atoms to this membership
 *         class. While atom assignment should be unique, it is not enforced.
 *         example SSMap - singleton contains { "residues", "chains",
 *         "user selection 1"} ALSet for "Residues" would contain {"VAL", "SER",
 *         "LEU", etc) each of these would contain an AtomList which contains
 *         the atoms in those groups..
 */
public class SubstructureMap extends CLoadableSet<SubstructureSet> {
	
	/**
	 * The Enum SubstructureType.
	 * 
	 * @author Thomas Kreek
	 */
	public enum SubstructureType
	{
		 
 		/** The aminoacids. */
 		AMINOACIDS ("AminoAcids"),
		 
 		/** The residues. */
 		RESIDUES ("Residues"),
		 
 		/** The heterostructures. */
 		HETEROSTRUCTURES("HeteroStructures"),
		 
 		/** The resnums. */
 		RESNUMS ("ResNums"),
		 
 		/** The water. */
 		WATER ("Water");
		 
 		/** The name. */
 		String name;
		 
 		/**
		 * Instantiates a new substructure type.
		 * 
		 * @param text
		 *            the text
		 */
 		SubstructureType(String text)
		{
			name = text;
		}
		
		/**
		 * Text.
		 * 
		 * @return the string
		 */
		public String text()
		{
			return name;
		}
	}
	
	/*
	 */
	/**
	 * Instantiates a new substructure map.
	 */
	SubstructureMap()
	{
		this.setUseByName(true);
	}
	
	/**
	 * Gets the.
	 * 
	 * @param type
	 *            the type
	 * @return the substructure set
	 */
	public SubstructureSet get(SubstructureType type)
	{
		return getByName(type.name);
	}
	
	/**
	 * Adds the atom to substructure.
	 * 
	 * @param setName
	 *            the class of substructure grouping
	 * @param subsName
	 *            The specific substructure name
	 * @param at
	 *            Atom to be added.
	 */
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
	 * debugging routine to print all substructure classes and substructures.
	 * 
	 * @param full
	 *            if true, print all atoms in each substructure, too.
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
