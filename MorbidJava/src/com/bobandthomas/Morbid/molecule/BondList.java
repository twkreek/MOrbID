package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;
import java.util.HashMap;

import com.bobandthomas.Morbid.utils.CLoadableSet;


public class BondList extends CLoadableSet<Bond> {
	HashMap<String, Bond> map = new HashMap <String, Bond>();
	int nextID = 0;
	public BondList() {
		this.setReParent(true);
	}

	long AddBond(Bond b)
	{
		nextID ++;
		b.setID(nextID);
		add(b);
		map.put(b.getName(), b);
		return nextID;
	}
	Bond getBond(Atom a1, Atom a2)
	{
		return map.get(Bond.AtNoHash(a1.ID(), a2.ID()));
	}
	Bond getBond(int a1, int a2)
	{
		return map.get(Bond.AtNoHash(a1,a2));	
	}
	
	Bond[] getBondsOfAtom(Atom atom)
	{
		ArrayList<Bond> bonds = new ArrayList<Bond>();
		for (Bond b : this)
		{
			if ((b.atom1 == atom) || (b.atom2 == atom))
				bonds.add(b);
		}
		return (Bond[]) bonds.toArray();
	}
	
}
