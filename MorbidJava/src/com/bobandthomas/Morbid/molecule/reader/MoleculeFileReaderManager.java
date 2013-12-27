package com.bobandthomas.Morbid.molecule.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.MorbidBufferedReader;

public class MoleculeFileReaderManager extends ArrayList<Class<? extends MoleculeFileReader>> {
	Map<String,Class<? extends MoleculeFileReader>> extensionMap;
	static MoleculeFileReaderManager mfr = null ;
	public static MoleculeFileReaderManager getOne()
	{
		if (mfr == null)
			mfr = new MoleculeFileReaderManager();
		return mfr;
	}
	public MoleculeFileReaderManager()
	{
		extensionMap = new HashMap<String,Class<? extends MoleculeFileReader>>();
		add(FileReaderPDB.class, "pdb");
		add(FileReaderPCModel.class, "pcm");
		add(FileReaderSDF.class, "sdf");
		add(FileReaderMopac13.class, "f13");

	}
	public boolean add(Class<? extends MoleculeFileReader> mr, String extension)
	{
		if (!super.add(mr)) return false;
		
		extensionMap.put(extension, mr);
		
		return true;
		
	}
	
	public MoleculeFileReader getReader(String extension)
	{
		Class<? extends MoleculeFileReader> mfr = extensionMap.get(extension);
		try {
			return mfr.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			Logger.addMessage(this, e);
			e.printStackTrace();
			return null;
		}
	}
	
	public static Molecule readFile(String name, MorbidBufferedReader br, String extension)
	{
		return getOne().readFile(name, br, getOne().getReader(extension));
	}
	private Molecule readFile(String name, MorbidBufferedReader br, MoleculeFileReader reader)
	{
		Molecule m = new Molecule();
		reader.init(name, m, br); 
		reader.Read();
		try {
			br.close();
		} catch (IOException e) {
			Logger.addMessage(this, e);
		}
		return m;
		
	}

}
