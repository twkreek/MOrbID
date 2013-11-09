package com.bobandthomas.Morbid.molecule.reader;

import java.util.ArrayList;
import java.util.Map;

public class MoleculeFileReaderManager extends ArrayList<MoleculeFileReader> {
	Map<String,MoleculeFileReader> extensionMap;
	public MoleculeFileReaderManager()
	{
		
	}
	@Override
	public boolean add(MoleculeFileReader mr)
	{
		if (!super.add(mr)) return false;
		
		for (String s : mr.getFileExtensions())
		{
			extensionMap.put(s, mr);
		}
		
		return true;
		
	}
	
	public MoleculeFileReader getReader(String extension)
	{
		return extensionMap.get(extension);
	}

}
