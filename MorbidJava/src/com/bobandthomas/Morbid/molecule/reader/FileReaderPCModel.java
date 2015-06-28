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
import java.util.HashMap;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.AtomTypeList;
import com.bobandthomas.Morbid.molecule.Bond;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;
import com.bobandthomas.Morbid.wrapper.Logger;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;

// TODO: Auto-generated Javadoc
/**
 * The Class FileReaderPCModel.
 * 
 * @author Thomas Kreek
 */
public class FileReaderPCModel extends MoleculeFileReader {

	/** The mm2 types. */
	MM2TypeList mm2Types;

	/**
	 * Instantiates a new file reader pc model.
	 */
	public FileReaderPCModel() {
		fileTypeName = "PCModel Files";
		mm2Types = new MM2TypeList();
	}

	/**
	 * The Class MM2Type.
	 * 
	 * @author Thomas Kreek
	 */
	public class MM2Type {
		// TypeMM2 Sym DESCRIPTION AtWt AtNo
		/** The type. */
		int type;
		
		/** The sym. */
		String sym;
		
		/** The description. */
		String description;
		
		/** The At wt. */
		double AtWt;
		
		/** The atomic number. */
		int atomicNumber;

		/**
		 * Instantiates a new m m2 type.
		 */
		public MM2Type() {

		}

		/**
		 * Read item.
		 * 
		 * @param r
		 *            the r
		 */
		void readItem(CSVFileReader r) {
			type = r.getInteger("TypeMM2");
			sym = r.getString("Sym");
			description = r.getString("description");
			AtWt = r.getFloat("AtWt");
			atomicNumber = r.getInteger("AtNo");
		}
	}

	/**
	 * The Class MM2TypeList.
	 * 
	 * @author Thomas Kreek
	 */
	private class MM2TypeList extends HashMap<Integer, MM2Type> {
		
		/**
		 * Instantiates a new m m2 type list.
		 */
		MM2TypeList() {
			super();
			try {
				CSVFileReader br = ResourceMgr
						.getResourceCSV("data/MM2Types.csv");

				do {
					MM2Type at = new MM2Type();
					at.readItem(br);
					put(at.type, at);
				} while (br.nextLine());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
/*
	private enum TokenTypes
	{
		PCM("{PCM"), Flag("FL"), Atom("AT"), Bond("B"), Close("}");
		TokenTypes(String token)
		{
			
		}		
	}
*/

	/**
 * The Class PCMTokenizer.
 * 
 * @author Thomas Kreek
 */
private class PCMTokenizer {
		
		/** The ss. */
		String [] ss;
		
		/** The current index. */
		int currentIndex;

		/**
		 * Instantiates a new PCM tokenizer.
		 * 
		 * @param arg0
		 *            the arg0
		 */
		public PCMTokenizer(String arg0) {
			currentIndex = 0;
			String stemp []  = arg0.split("[ ,:]");
			int count = 0;
			for (String s :stemp) if (!s.isEmpty()) count++; 
			ss = new String[count];
			count = 0;
			for (String s: stemp) if (!s.isEmpty())
			{
				ss[count++] = s;
			}
		}
		
		/**
		 * Checks if it has next token.
		 * 
		 * @return true, if successful
		 */
		boolean hasNextToken()
		{
			return currentIndex < ss.length;
		}

		/**
		 * Gets the string token.
		 * 
		 * @return the string
		 */
		String GetStringToken() {
			if(currentIndex >= ss.length )
				return null;
			String next = ss[currentIndex];
//				System.out.print("|"+next+"| ");
				currentIndex++;
			return next;
		}
		
		/**
		 * Peek next token.
		 * 
		 * @return the string
		 */
		String peekNextToken()
		{
			// does not advace the counter;
			return ss[currentIndex];
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
		 * Gets the float token.
		 * 
		 * @return the float
		 */
		float GetFloatToken() {
			return Float.parseFloat(GetStringToken());
		}
		

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Save()
	 */
	@Override
	public void Save() {
		// TODO FileSave not implemented

	}

	/**
	 * Gets the atom type.
	 * 
	 * @param token
	 *            the token
	 * @return the atom type
	 */
	AtomType getAtomType(String token) {
		AtomType at;
		if (Character.isDigit(token.charAt(0))) {
			// it's an MM2 type
			int typeNum;
			typeNum = Integer.parseInt(token);
			MM2Type mType = mm2Types.get(typeNum);
			at = AtomTypeList.Get(mType.atomicNumber);

		} else
			at = AtomTypeList.Get(token);
		if (at == null) {
			at = AtomTypeList.CreateType(token);
		}
		return at;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Read()
	 */
	@Override
	public void Read() {
		String line;
		try {
			while ((line = br.readLine()) != null) {
				PCMTokenizer scrap = new PCMTokenizer(line);
				String token = scrap.GetStringToken();
				if (token.equals("{PCM"))
				{
					molecule.setName(line.substring(4));
					continue;
				}
				if (token.equals( "NA"))
				{
					@SuppressWarnings("unused")
					int na;
					na = scrap.GetIntToken();
					continue;
				}
				if (token.equals("FL")) {
					String str = scrap.GetStringToken();
					molecule.getPropList().Add("Flags", str, "");
					continue;
				}
				if (token.equals("AT")) {
					int sequence;
					int typeNum;
					String sym;
					float x, y, z;

					sequence = scrap.GetIntToken();

					sym = scrap.GetStringToken();
					AtomType at = getAtomType(sym);
					Atom atom = new Atom(at);
					atom.setAtomType(at);
					if (Character.isDigit(sym.charAt(0))) {
						// translate PCMODel number scheme to AtomNumber and type
						typeNum = Integer.parseInt(sym);
						MM2Type type = mm2Types.get(typeNum);
						atom.setSubType(type.type);
					}

					x = scrap.GetFloatToken();
					y = scrap.GetFloatToken();
					z = scrap.GetFloatToken();

					atom.setPosition(x, y, z);
					molecule.AddAtom(atom);

					token = scrap.GetStringToken();
					for (;;) {
						if (token == null || token.length() == 0)
							break;
						if (token.equals("B")) {
							do {
								int b1, b2;
								b1 = scrap.GetIntToken();
								b2 = scrap.GetIntToken();
								if (b1 < sequence) {
									b1--;
									Bond bond = new Bond(atom, molecule.GetAtom(b1));
									bond.setNominalBondOrder(b2);
									molecule.AddBond(bond);
								}
								if (!scrap.hasNextToken())
									break;
								token = scrap.peekNextToken();
							} while (token != null && token.length() > 0
									&& Character.isDigit(token.charAt(0)));
							token = scrap.GetStringToken();
							continue;
						} else if (token.charAt(0) == 'C') {
							if (token.length() > 1)
								atom.setCharge(Float.parseFloat(token.substring(1)));
							else
								atom.setCharge(scrap.GetFloatToken());
							continue;
						} else if (token.charAt(0) == 'M') {
							if (token.length() == 1) {
								token = scrap.GetStringToken();
							}
							continue;
						} else if (token.charAt(0) == 'R') {
							if (token.length() == 1) {
								token = scrap.GetStringToken();
							}
							continue;
						}
						
						token = scrap.GetStringToken();
						if (token != null && token.equals("}")) {
							molecule.CalcBounds();
						}
					}
				}
			}
		} catch (NumberFormatException e) {
			Logger.addMessage(this, e);
		} catch (IOException e) {
			Logger.addMessage(this, e);
		}
		molecule.CalcBounds();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Validate()
	 */
	@Override
	public boolean Validate() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#getFileExtensions()
	 */
	@Override
	public String[] getFileExtensions() {
		String [] extensions = {"PCM"};
		return extensions;
	}

}
