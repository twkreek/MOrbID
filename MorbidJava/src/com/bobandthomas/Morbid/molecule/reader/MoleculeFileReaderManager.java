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
package com.bobandthomas.Morbid.molecule.reader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.MorbidBufferedReader;

// TODO: Auto-generated Javadoc
/**
 * The Class MoleculeFileReaderManager.
 * 
 * @author Thomas Kreek
 */
public class MoleculeFileReaderManager extends ArrayList<Class<? extends MoleculeFileReader>> {
	
	/** The extension map. */
	Map<String,Class<? extends MoleculeFileReader>> extensionMap;
	
	/** The mfr. */
	static MoleculeFileReaderManager mfr = null ;
	
	/**
	 * Gets the one.
	 * 
	 * @return the one
	 */
	public static MoleculeFileReaderManager getOne()
	{
		if (mfr == null)
			mfr = new MoleculeFileReaderManager();
		return mfr;
	}
	
	/**
	 * Instantiates a new molecule file reader manager.
	 */
	public MoleculeFileReaderManager()
	{
		extensionMap = new HashMap<String,Class<? extends MoleculeFileReader>>();
		add(FileReaderPDB.class, "pdb");
		add(FileReaderPCModel.class, "pcm");
		add(FileReaderSDF.class, "sdf");
		add(FileReaderMopac13.class, "f13");

	}
	
	/**
	 * Adds the.
	 * 
	 * @param mr
	 *            the mr
	 * @param extension
	 *            the extension
	 * @return true, if successful
	 */
	public boolean add(Class<? extends MoleculeFileReader> mr, String extension)
	{
		if (!super.add(mr)) return false;
		
		extensionMap.put(extension, mr);
		
		return true;
		
	}
	
	/**
	 * Gets the reader.
	 * 
	 * @param extension
	 *            the extension
	 * @return the reader
	 */
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
	
	/**
	 * Read file.
	 * 
	 * @param name
	 *            the name
	 * @param br
	 *            the br
	 * @param extension
	 *            the extension
	 * @return the molecule
	 */
	public static Molecule readFile(String name, MorbidBufferedReader br, String extension)
	{
		return getOne().readFile(name, br, getOne().getReader(extension));
	}
	
	/**
	 * Read file.
	 * 
	 * @param name
	 *            the name
	 * @param br
	 *            the br
	 * @param reader
	 *            the reader
	 * @return the molecule
	 */
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
