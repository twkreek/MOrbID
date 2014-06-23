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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bobandthomas.Morbid.utils.CLoadableItem;

// TODO: Auto-generated Javadoc
/**
 * The Class Empirical.
 * 
 * @author Thomas Kreek
 */
public class Empirical extends CLoadableItem
{
	
	/** The m. */
	Molecule m;

	/** The empirical. */
	HashMap <AtomType, Integer> empirical;
	
	/**
	 * Instantiates a new empirical.
	 * 
	 * @param mol
	 *            the mol
	 */
	public Empirical(Molecule mol)
	{
		m = mol;
		empirical = new HashMap<AtomType, Integer>();
	}
	
	/**
	 * Reset.
	 */
	public void reset()
	{
		empirical = new HashMap<AtomType, Integer>();
		for (Atom a: m)
		{
			add(a);
		}
		
	}
	
	/**
	 * Adds the.
	 * 
	 * @param a
	 *            the a
	 */
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
	
	/**
	 * Gets the text.
	 * 
	 * @param pairs
	 *            the pairs
	 * @return the text
	 */
	String getText(Map.Entry<AtomType, Integer> pairs)
	{
		String text = pairs.getKey().getName() + pairs.getValue() + " ";

		return text;
	}
    
    /**
	 * Gets the formula.
	 * 
	 * @return the formula
	 */
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