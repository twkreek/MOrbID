package com.bobandthomas.Morbid.molecule;

import java.io.IOException;
import java.util.HashMap;

import com.bobandthomas.Morbid.wrapper.CSVFileReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;

public class AtomTypeList {
	static HashMap<Integer, AtomType> byAtNo = new HashMap<Integer, AtomType>();
	static HashMap<String, AtomType> byName = new HashMap<String, AtomType>();
	static boolean initialized = false;
	private AtomTypeList()
	{
	}
		

	public void init()
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
	
	public static void add(AtomType at)
	{
		byAtNo.put(at.GetAtomicNumber(), at);
		byName.put(at.getName(), at);
	}
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
	static private AtomTypeList singleton;

	static AtomTypeList getOne(){
		if (singleton == null)
		{
			singleton = new AtomTypeList();
			singleton.init();
		}
		return singleton;
		}
		
	public static AtomType Get(String s)
	{
		getOne();
		AtomType at = byName.get(s);
		if (at == null)
		{
			at = CreateType(s);
			add(at);
		}
		return at;
		// get atom type by symbol;
	}
	public static AtomType Get(Integer s)
	{
		getOne();
		return byAtNo.get(s);// get atom type by atomic number
	}
}
