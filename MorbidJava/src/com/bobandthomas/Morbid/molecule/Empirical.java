package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bobandthomas.Morbid.utils.CLoadableItem;

public class Empirical extends CLoadableItem
{
	Molecule m;

	HashMap <AtomType, Integer> empirical;
	public Empirical(Molecule mol)
	{
		m = mol;
		empirical = new HashMap<AtomType, Integer>();
	}
	public void reset()
	{
		empirical = new HashMap<AtomType, Integer>();
		for (Atom a: m)
		{
			add(a);
		}
		
	}
	public void add(Atom a)
	{
		
    	Integer count = empirical.get(a.getAtomType());
    	if (count == null)
    	{
    		count = new Integer(0);
    	}
    	count += 1;
    	empirical.put(a.getAtomType(), count);
	}
	String getText(Map.Entry<AtomType, Integer> pairs)
	{
		String text = pairs.getKey().getName() + pairs.getValue() + " ";

		return text;
	}
    public String getFormula()
    {
    	ArrayList<String> strings = new ArrayList<String>();
    	ArrayList<String> first = new ArrayList<String>();
    	first.add("");
    	first.add("");
    	
    	
    	
    	 Iterator<Entry<AtomType, Integer>> it = empirical.entrySet().iterator();
    	    while (it.hasNext()) {
    	        Map.Entry<AtomType, Integer> pairs = (Map.Entry<AtomType, Integer>)it.next();
    	        if (pairs.getKey().isA(Element.C))
    	        {
    	        	first.set(0, getText(pairs));
    	        }
    	        else if (pairs.getKey().isA(Element.H))
    	        {
    	        	first.set(1, getText(pairs));
    	        }
    	        else
    	        {
    	        	strings.add(getText(pairs));
    	        }    	       
    	        it.remove(); // avoids a ConcurrentModificationException
    	    }
    	Collections.sort(strings);
    	first.addAll(strings);
    	
    	String text = "";
    	for (String s: first)
    		text += s;
    	return text;
    }
}