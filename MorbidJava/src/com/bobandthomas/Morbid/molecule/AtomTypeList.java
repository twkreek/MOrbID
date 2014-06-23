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

import java.io.IOException;
import java.util.HashMap;

import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;

// TODO: Auto-generated Javadoc
/**
 * The Class AtomTypeList.
 * 
 * @author Thomas Kreek
 */
public class AtomTypeList extends CLoadableTable<AtomType>{
	
	/** The by at no. */
	static HashMap<Integer, AtomType> byAtNo = new HashMap<Integer, AtomType>();
	
	/** The by name. */
	static HashMap<String, AtomType> byName = new HashMap<String, AtomType>();
	
	/** The initialized. */
	static boolean initialized = false;
	
	/**
	 * Instantiates a new atom type list.
	 */
	private AtomTypeList()
	{
	}
		

	/**
	 * Initializes the.
	 */
	private void init()
	{
	
			if (initialized) return;
			
			try {
				CSVFileReader br = ResourceMgr.getResourceCSV("data/AtomTypes.csv");
				
				do
				{
					AtomType at = new AtomType();
					at.readItem(br);
					add(at);
				} while (br.nextLine());
			} catch (IOException e) {
				e.printStackTrace();
				// if read fails. use static enum
				for (Element att : Element.values())
				{
				
					AtomType at = att.getAtomType();
					add(at);
				}
			}
			
			initialized = true;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#add(com.bobandthomas.Morbid.utils.CLoadableItem)
	 */
	public boolean add(AtomType at)
	{
		super.add(at);
		byAtNo.put(at.GetAtomicNumber(), at);
		byName.put(at.getName(), at);
		return true;
	}
	
	/**
	 * Creates a type.
	 * 
	 * @param atName
	 *            the at name
	 * @return the atom type
	 */
	public static AtomType CreateType(String atName)
	{

		AtomType at;
		at = Get(atName);
		if (at != null)
			return at;
		
		// ok, here we don't have any idea what kind of atom this is, but from the name
		// more information will follow so create the atomtype and assemble a new type;
		at = new AtomType(); 
		if (atName.length() > 0)
			at.setName(atName);

		at.SetAtomicNumber(1000+at.GetAtomicNumber());
		return at;

	}
	
	/** The singleton. */
	static private AtomTypeList singleton;

	/**
	 * Gets the one.
	 * 
	 * @return the one
	 */
	public static AtomTypeList getOne(){
		if (singleton == null)
		{
			singleton = new AtomTypeList();
			singleton.init();
		}
		return singleton;
		}
		
	/**
	 * Gets the.
	 * 
	 * @param s
	 *            the s
	 * @return the atom type
	 */
	public static AtomType Get(String s)
	{
		getOne();
		AtomType at = byName.get(s);
		if (at == null)
		{
			at = CreateType(s);
			getOne().add(at);
		}
		return at;
		// get atom type by symbol;
	}
	
	/**
	 * Gets the.
	 * 
	 * @param s
	 *            the s
	 * @return the atom type
	 */
	public static AtomType Get(Integer s)
	{
		getOne();
		return byAtNo.get(s);// get atom type by atomic number
	}
}
