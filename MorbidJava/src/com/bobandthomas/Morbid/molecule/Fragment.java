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
import java.util.HashMap;
import java.util.Stack;

import com.bobandthomas.Morbid.utils.CLoadableItem;

// TODO: Auto-generated Javadoc
/**
 * The Class Fragment.
 * 
 * @author Thomas Kreek
 */
public abstract class Fragment extends CLoadableItem {

	/**
	 * The Enum MatchType.
	 * 
	 * @author Thomas Kreek
	 */
	public enum MatchType {
		
		/** The atom. */
		ATOM, 
 /** The fragment. */
 FRAGMENT, 
 /** The ring. */
 RING;
	}

	/**
	 * The Enum MatchFragment.
	 * 
	 * @author Thomas Kreek
	 */
	public enum MatchFragment {
		
		/** The carboxyl. */
		CARBOXYL,
		
		/** The carbonyl. */
		CARBONYL,
		
		/** The hydroxyl. */
		HYDROXYL,
		
		/** The amide. */
		AMIDE,
		
		/** The cno. */
		CNO, // carbon or nitrogen or oxygen - all can be ring memebers
		/** The metals. */
 METALS, // Fe, Zn, Co, Ni
		/** The AA terminus. */
 AATerminus, //carboxyl or amide
	}

	/** The substructure. */
	protected Substructure substructure;
	
	/** The map. */
	private HashMap<String, Atom> map = new HashMap<String, Atom>();


	/**
	 * Instantiates a new fragment.
	 * 
	 * @param a
	 *            the a
	 */
	public Fragment(Substructure a) {
		substructure = a;		
	}

/*	public Substructure getSubstructure()
	{
		return substructure;
	}
*/
	/* (non-Javadoc)
 * @see com.bobandthomas.Morbid.utils.CLoadableItem#getName()
 */
public abstract String getName();
	
	/**
	 * Gets the by name.
	 * 
	 * @param name
	 *            the name
	 * @return the by name
	 */
	public Atom getByName(String name) {
		return map.get(name);
	}

	/**
	 * Sets the by name.
	 * 
	 * @param name
	 *            the name
	 * @param a
	 *            the a
	 */
	public void setByName(String name, Atom a) {
		map.put(name, a);
	}


	/**
	 * The Class Matcher.
	 * 
	 * @author Thomas Kreek
	 */
	public class Matcher {
		
		/** The type. */
		MatchType type;
		
		/** The fragment. */
		MatchFragment fragment;
		
		/** The ring origin. */
		Atom ringOrigin;
		
		/** The ring size. */
		int ringSize;
		
		/** The element. */
		Element element;
		
		/** The matched. */
		boolean matched;
		
		/** The match. */
		Atom match;
	
		/**
		 * Instantiates a new matcher.
		 * 
		 * @param frag
		 *            the frag
		 */
		public Matcher(MatchFragment frag) {
			fragment = frag;
			type = MatchType.FRAGMENT;
		}

		/**
		 * Instantiates a new matcher.
		 * 
		 * @param e
		 *            the e
		 */
		public Matcher(Element e) {
			type = MatchType.ATOM;
			element = e;
		}
		
		/**
		 * Instantiates a new matcher.
		 * 
		 * @param a
		 *            the a
		 * @param ringSize
		 *            the ring size
		 */
		public Matcher(Atom a, int ringSize)
		{
			type = MatchType.RING;
			ringOrigin = a;
			this.ringSize = ringSize;
		}
		
		/**
		 * Gets the substructure name.
		 * 
		 * @return the substructure name
		 */
		public String getSubstructureName()
		{
			return substructure.getName();
		}
		

		/**
		 * Checks if is match.
		 * 
		 * @param a
		 *            the a
		 * @return true, if is match
		 */
		public boolean isMatch(Atom a) {
			if (type== MatchType.ATOM)
				return a.isA(element);
			if (type == MatchType.RING)
				return (a == ringOrigin);
			switch (fragment) {
				case CARBOXYL: {
					int oCount = 0;
					for (Atom attached : a.getBonded())
						if (attached.isA(Element.O))
							oCount++;
					//carboxyls have exactly 3 bonds, 2 oxygens.
					if (a.getBonded().length == 3 && oCount == 2) 
					{
						match = a;
						return true;
					}
					return false;

				}
				case AMIDE: {
					int oCount = 0;
					int nCount = 0;
					for (Atom attached : a.getBonded())
					{
						if (attached.isA(Element.O))
							oCount++;
						
						if (attached.isA(Element.N))
							nCount++;
					}
					//carboxyls have exactly 3 bonds, 1 oxygens and 1 nitrogen.
					if (a.getBonded().length == 3 && nCount == 1 && oCount == 1) 
					{
						match = a;
						return true;
					}
					return false;
				}
				case CNO:
				{
					if (a.isA(Element.C) || a.isA(Element.N) || a.isA(Element.O))
							return true;
				}

				default: // fragment
					break;

				}
			return false;
		}

	}

	/**
	 * The Class MatcherList.
	 * 
	 * @author Thomas Kreek
	 */
	class MatcherList extends ArrayList<Matcher>
	{
		
	}

	/**
	 * Do match.
	 * 
	 * @return true, if successful
	 */
	public abstract boolean doMatch();

	/**
	 * Match.
	 * 
	 * @param a
	 *            the a
	 * @return true, if successful
	 */
	public boolean match(Substructure a) {
		substructure = a;
		return doMatch();
	}

	
	/** The matches. */
	Stack<Atom> matches = new Stack<Atom>();
	
	/**
	 * Gets the matches.
	 * 
	 * @return the matches
	 */
	public Stack<Atom> getMatches()
	{
		return matches;
	}
	
	/**
	 * Match.
	 * 
	 * @param a
	 *            the a
	 * @param matchers
	 *            the matchers
	 * @return true, if successful
	 */
	public boolean Match(Atom a, MatcherList matchers)
	{
		if (matches.size() > matchers.size())
			return false; // should never get here
		// we have a match at this level.  
		if (matchers.get(matches.size()).isMatch(a))
		{
			matches.push(a);
			if (matches.size() == matchers.size())
				return true;
			for (Atom bonded : a.getBonded())
			{
				if (!substructure.contains(bonded))
					continue;
				if (matches.contains(bonded))
					continue;
				if (Match(bonded, matchers))
				{
					return true;
				}
			}
			matches.pop();
		}
		return false;
	}
	
}
