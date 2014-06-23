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

import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.MorbidBufferedReader;

// TODO: Auto-generated Javadoc
/**
 * The Class MoleculeFileReader.
 * 
 * @author Thomas Kreek
 */
public abstract class MoleculeFileReader {

	/**
	 * The Class Tokenizer.
	 * 
	 * @author Thomas Kreek
	 */
	protected class Tokenizer {
		
		/** The current index. */
		int currentIndex;
		
		/** The current point. */
		int currentPoint;
		
		/** The ss. */
		String[] ss;
		
		/** The str. */
		String str;

		/**
		 * Instantiates a new tokenizer.
		 * 
		 * @param arg0
		 *            the arg0
		 */
		public Tokenizer(String arg0) {
			str = arg0;
			currentPoint = 0;
			currentIndex = 0;
			String stemp[] = arg0.split("[ ,:]");
			int count = 0;
			for (String s : stemp)
				if (!s.isEmpty())
					count++;
			ss = new String[count];
			count = 0;
			for (String s : stemp)
				if (!s.isEmpty()) {
					ss[count++] = s;
					// System.out.print(s +",  ");
				}
		}
		
		/**
		 * Size.
		 * 
		 * @return the int
		 */
		public int size()
		{
			return ss.length;
		}

		/**
		 * Gets the float token.
		 * 
		 * @return the float
		 */
		float GetFloatToken() {
			return Float.parseFloat(GetStringToken());
		}

		/**
		 * Gets the int token.
		 * 
		 * @return the int
		 */
		int GetIntToken() {
			return Integer.parseInt(GetStringToken());
		}

		/**
		 * Gets the string token.
		 * 
		 * @return the string
		 */
		String GetStringToken() {
			if (currentIndex >= ss.length)
				return null;
			String next = ss[currentIndex];
			// System.out.print("|"+next+"| ");
			currentIndex++;
			return next;
		}

		/**
		 * Checks if it has next token.
		 * 
		 * @return true, if successful
		 */
		boolean hasNextToken() {
			return currentIndex < ss.length;
		}

		/**
		 * Peek next token.
		 * 
		 * @return the string
		 */
		String peekNextToken() {
			// does not advace the counter;
			return ss[currentIndex];
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		public String toString()
		{
			return str;
		}

	}

	/** The br. */
	MorbidBufferedReader br;
	
	/** The binary. */
	boolean binary;
	
	/** The file name. */
	String fileName;

	/** The file type name. */
	String fileTypeName;
	
	/** The molecule. */
	Molecule molecule;
	
	/**
	 * Instantiates a new molecule file reader.
	 */
	MoleculeFileReader() {
		binary = false; //default files are text based;
	}
	
	/**
	 * Gets the molecule.
	 * 
	 * @return the molecule
	 */
	public Molecule getMolecule() {
		return molecule;
	}

	/**
	 * Gets the next line.
	 * 
	 * @return the next line
	 */
	public Tokenizer getNextLine()
	{
		Tokenizer t = null;
		try {
			t = new Tokenizer(br.readLine());
		} catch (IOException e) {
			Logger.addMessage(this, e);
		}
		return t;
	}

	/**
	 * Initializes the.
	 * 
	 * @param s
	 *            the s
	 * @param m
	 *            the m
	 * @param is
	 *            the is
	 */
	public void init(String s, Molecule m, MorbidBufferedReader is) {
		fileName = s;
		molecule = m;
		br = is;
	}

	/**
	 * Read.
	 */
	public abstract void Read();

	/**
	 * Save.
	 */
	public abstract void Save();
	
	/**
	 * Gets the file extensions.
	 * 
	 * @return the file extensions
	 */
	public abstract String[] getFileExtensions();

	/**
	 * Sets the molecule.
	 * 
	 * @param molecule
	 *            the new molecule
	 */
	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}
	
	/**
	 * Validate.
	 * 
	 * @return true, if successful
	 */
	public abstract boolean Validate();

	/**
	 * Pre read.
	 * 
	 * @return true, if successful
	 */
	protected boolean PreRead() {
		if (fileName == null || fileName.length() == 0)
			return false;
		if (molecule == null) {
			molecule = new Molecule();
		}
		br.setReaderBinary(binary);
		return true;
	}

}
