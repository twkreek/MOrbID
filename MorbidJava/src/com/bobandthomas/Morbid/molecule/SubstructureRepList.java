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

import java.util.HashMap;
import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;

// TODO: Auto-generated Javadoc
/**
 * The Class SubstructureRepList.
 * 
 * @author Thomas Kreek
 */
public class SubstructureRepList extends CLoadableTable<SubstructureRep> 
{
	
	/** The substructure set. */
	SubstructureSet substructureSet;
	
	/** The rep map. */
	HashMap<Substructure, SubstructureRep> repMap;

	
	/**
	 * Instantiates a new substructure rep list.
	 * 
	 * @param inList
	 *            the in list
	 */
	public SubstructureRepList(SubstructureSet inList) {
		super();
		substructureSet = inList;
		repMap = new HashMap<Substructure, SubstructureRep>();
		for (Substructure s: substructureSet)
		{
			SubstructureRep rep = new SubstructureRep(s,true, new ColorQuad(s.getListColor()));
			repMap.put(s, rep);
			add(rep);
			if (s.getName().equals("other"))
				rep.setVisible(false);
			
		}
	}
	
	/**
	 * Gets the substructure list.
	 * 
	 * @return the substructure list
	 */
	public SubstructureSet getSubstructureList() {
		return substructureSet;
	}
	
	/**
	 * Checks if is visible.
	 * 
	 * @param a
	 *            the a
	 * @return true, if is visible
	 */
	public boolean isVisible(Atom a)
	{
		return repMap.get(substructureSet.getSubstructure(a)).visible;
	}
	
	/**
	 * Checks if is visible.
	 * 
	 * @param s
	 *            the s
	 * @return true, if is visible
	 */
	public boolean isVisible(Substructure s)
	{
		return repMap.get(s).visible;
	}
	
	/**
	 * Gets the color.
	 * 
	 * @param s
	 *            the s
	 * @return the color
	 */
	public ColorQuad getColor(Substructure s)
	{
		return new ColorQuad( repMap.get(s).color);
	}
	
	/**
	 * Gets the color.
	 * 
	 * @param a
	 *            the a
	 * @return the color
	 */
	public ColorQuad getColor(Atom a)
	{
		return new ColorQuad(repMap.get(substructureSet.getSubstructure(a)).color);
		
	}
	
	/**
	 * Gets the.
	 * 
	 * @param s
	 *            the s
	 * @return the substructure rep
	 */
	public SubstructureRep get(Substructure s)
	{
		return repMap.get(s);
	}
	
	/**
	 * Inherit reps.
	 */
	public void inheritReps()
	{
		repMap = new HashMap<Substructure, SubstructureRep>();
		for (Substructure s: substructureSet)
		{
			SubstructureRep rep = new SubstructureRep(s, true, new ColorQuad(s.getListColor()));
			repMap.put(s, rep);
			
		}
		
	}



}
