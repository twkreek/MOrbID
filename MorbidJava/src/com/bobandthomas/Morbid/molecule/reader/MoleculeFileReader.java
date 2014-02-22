package com.bobandthomas.Morbid.molecule.reader;

import java.io.IOException;

import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.MorbidBufferedReader;

public abstract class MoleculeFileReader {

	protected class Tokenizer {
		int currentIndex;
		int currentPoint;
		String[] ss;
		String str;

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
		
		public int size()
		{
			return ss.length;
		}

		float GetFloatToken() {
			return Float.parseFloat(GetStringToken());
		}

		int GetIntToken() {
			return Integer.parseInt(GetStringToken());
		}

		String GetStringToken() {
			if (currentIndex >= ss.length)
				return null;
			String next = ss[currentIndex];
			// System.out.print("|"+next+"| ");
			currentIndex++;
			return next;
		}

		boolean hasNextToken() {
			return currentIndex < ss.length;
		}

		String peekNextToken() {
			// does not advace the counter;
			return ss[currentIndex];
		}
		
		public String toString()
		{
			return str;
		}

	}

	MorbidBufferedReader br;
	boolean binary;
	String fileName;

	String fileTypeName;
	Molecule molecule;
	MoleculeFileReader() {
		binary = false; //default files are text based;
	}
	public Molecule getMolecule() {
		return molecule;
	}

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

	public void init(String s, Molecule m, MorbidBufferedReader is) {
		fileName = s;
		molecule = m;
		br = is;
	}

	public abstract void Read();

	public abstract void Save();
	public abstract String[] getFileExtensions();

	public void setMolecule(Molecule molecule) {
		this.molecule = molecule;
	}
	
	public abstract boolean Validate();

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
