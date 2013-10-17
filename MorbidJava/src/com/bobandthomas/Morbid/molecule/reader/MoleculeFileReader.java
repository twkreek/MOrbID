package com.bobandthomas.Morbid.molecule.reader;

import java.io.BufferedReader;
import java.io.IOException;

import com.bobandthomas.Morbid.molecule.Molecule;

public abstract class MoleculeFileReader {

	MoleculeFileReader() {

	}

	Molecule molecule;
	String fileName;
	String fileTypeName;
	String FileTypeFilter;
	BufferedReader br;

	public void init(String s, Molecule m, BufferedReader is) {
		fileName = s;
		molecule = m;
		br = is;
	}

	protected boolean PreRead() {
		if (fileName == null || fileName.length() == 0)
			return false;
		if (molecule == null) {
			molecule = new Molecule();
		}
		return true;
	}

	public abstract void Save();

	public abstract void Read();

	public abstract boolean Validate();
	
	public Tokenizer getNextLine()
	{
		Tokenizer t = null;
		try {
			t = new Tokenizer(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return t;
	}

	protected class Tokenizer {
		String str;
		String[] ss;
		int currentPoint;
		int currentIndex;

		public Tokenizer(String arg0) {
			str = arg0;
			currentPoint = 0;
			currentIndex = 0;
			System.out.println();
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
			System.out.println();
		}
		
		public int size()
		{
			return ss.length;
		}

		boolean hasNextToken() {
			return currentIndex < ss.length;
		}

		String GetStringToken() {
			if (currentIndex >= ss.length)
				return null;
			String next = ss[currentIndex];
			// System.out.print("|"+next+"| ");
			currentIndex++;
			return next;
		}

		String peekNextToken() {
			// does not advace the counter;
			return ss[currentIndex];
		}

		int GetIntToken() {
			return Integer.parseInt(GetStringToken());
		}

		float GetFloatToken() {
			return Float.parseFloat(GetStringToken());
		}

	}

}
