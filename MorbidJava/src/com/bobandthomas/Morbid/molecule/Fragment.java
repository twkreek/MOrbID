package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public abstract class Fragment {

	public enum MatchType {
		ATOM, FRAGMENT, RING;
	}

	public enum MatchFragment {
		CARBOXYL,
		CARBONYL,
		HYDROXYL,
		AMIDE,
		CNO, // carbon or nitrogen or oxygen - all can be ring memebers
		METALS, // Fe, Zn, Co, Ni
		AATerminus, //carboxyl or amide
	}

	protected Substructure substructure;
	private HashMap<String, Atom> map = new HashMap<String, Atom>();


	public Fragment(Substructure a) {
		substructure = a;		
	}

	public Substructure getSubstructure()
	{
		return substructure;
	}
	public abstract String getName();
	public Atom getByName(String name) {
		return map.get(name);
	}

	public void setByName(String name, Atom a) {
		map.put(name, a);
	}


	public class Matcher {
		MatchType type;
		MatchFragment fragment;
		Atom ringOrigin;
		int ringSize;
		Element element;
		boolean matched;
		Atom match;
	
		public Matcher(MatchFragment frag) {
			fragment = frag;
			type = MatchType.FRAGMENT;
		}

		public Matcher(Element e) {
			type = MatchType.ATOM;
			element = e;
		}
		public Matcher(Atom a, int ringSize)
		{
			type = MatchType.RING;
			ringOrigin = a;
			this.ringSize = ringSize;
		}
		

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

	class MatcherList extends ArrayList<Matcher>
	{
		
	}

	public abstract boolean doMatch();

	public boolean match(Substructure a) {
		substructure = a;
		return doMatch();
	}

	
	Stack<Atom> matches = new Stack<Atom>();
	public Stack<Atom> getMatches()
	{
		return matches;
	}
	
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
