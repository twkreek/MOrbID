package com.bobandthomas.Morbid.molecule;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bobandthomas.Morbid.utils.CLoadableItem;

public class Empirical extends CLoadableItem
{

	HashMap <AtomType, Integer> empirical;
	public Empirical()
	{
		empirical = new HashMap<AtomType, Integer>();
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
	
    public String getFormula()
    {
    	String text = "";
    	
    	 Iterator<Entry<AtomType, Integer>> it = empirical.entrySet().iterator();
    	    while (it.hasNext()) {
    	        Map.Entry<AtomType, Integer> pairs = (Map.Entry<AtomType, Integer>)it.next();
    	        
    	        text = text +pairs.getKey().getName() + "("+pairs.getValue() + ") ";
    	       
    	        it.remove(); // avoids a ConcurrentModificationException
    	    }
    		
    	return text;
    }
}