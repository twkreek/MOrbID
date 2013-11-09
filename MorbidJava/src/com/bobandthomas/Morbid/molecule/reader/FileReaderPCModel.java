package com.bobandthomas.Morbid.molecule.reader;

import java.io.IOException;
import java.util.HashMap;

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.AtomTypeList;
import com.bobandthomas.Morbid.molecule.Bond;
import com.bobandthomas.Morbid.wrapper.CSVFileReader;
import com.bobandthomas.Morbid.wrapper.ResourceMgr;

public class FileReaderPCModel extends MoleculeFileReader {

	MM2TypeList mm2Types;

	public FileReaderPCModel() {
		fileTypeName = "PCModel Files";
		mm2Types = new MM2TypeList();
	}

	public class MM2Type {
		// TypeMM2 Sym DESCRIPTION AtWt AtNo
		int type;
		String sym;
		String description;
		double AtWt;
		int atomicNumber;

		public MM2Type() {

		}

		void readItem(CSVFileReader r) {
			type = r.getInteger("TypeMM2");
			sym = r.getString("Sym");
			description = r.getString("description");
			AtWt = r.getFloat("AtWt");
			atomicNumber = r.getInteger("AtNo");
		}
	}

	private class MM2TypeList extends HashMap<Integer, MM2Type> {
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
	private enum TokenTypes
	{
		PCM("{PCM"), Flag("FL"), Atom("AT"), Bond("B"), Close("}");
		TokenTypes(String token)
		{
			
		}
		
	}

	private class PCMTokenizer {
		String str;
		String [] ss;
		int currentPoint;
		int currentIndex;

		public PCMTokenizer(String arg0) {
			str = arg0;
			currentPoint = 0;
			currentIndex = 0;
			System.out.println();
			String stemp []  = arg0.split("[ ,:]");
			int count = 0;
			for (String s :stemp) if (!s.isEmpty()) count++; 
			ss = new String[count];
			count = 0;
			for (String s: stemp) if (!s.isEmpty())
			{
				ss[count++] = s;
//				System.out.print(s +",  ");
			}
			System.out.println();
		}
		
		boolean hasNextToken()
		{
			return currentIndex < ss.length;
		}

		String GetStringToken() {
			if(currentIndex >= ss.length )
				return null;
			String next = ss[currentIndex];
//				System.out.print("|"+next+"| ");
				currentIndex++;
			return next;
		}
		String peekNextToken()
		{
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

	@Override
	public void Save() {
		// TODO Auto-generated method stub

	}

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

	@Override
	public void Read() {
		int na;
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
					int subType = 0;
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		molecule.CalcBounds();
	}

	@Override
	public boolean Validate() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String[] getFileExtensions() {
		String [] extensions = {"PCM"};
		return extensions;
	}

}
