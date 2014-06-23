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

import com.bobandthomas.Morbid.molecule.Peptide;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.SubstructureSet;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.SubstructureMap.SubstructureType;
import com.bobandthomas.Morbid.wrapper.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class FileReaderPDB.
 * 
 * @author Thomas Kreek
 */
public class FileReaderPDB extends MoleculeFileReader {

	/** The chains. */
	SubstructureSet chains;
	
	/** The residues. */
	SubstructureSet residues;
	
	/** The res nums. */
	Peptide resNums;
	
	/** The water. */
	SubstructureSet water;
	
	/** The hetero structures. */
	SubstructureSet heteroStructures;



	/**
	 * Instantiates a new file reader pdb.
	 */
	public FileReaderPDB(){
		fileTypeName = "Protein Databank Files";
	}
	
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Save()
	 */
	@Override
	public void Save() {
		// TODO file save - not implemented

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Validate()
	 */
	@Override
	public boolean Validate() {
		return false;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Read()
	 */
	@Override
	public void Read()
	{
		if (!PreRead())
			return;

		ReadPDBFile(molecule);

	}
	
	/**
	 * Read het atom record.
	 * 
	 * @param mol
	 *            the mol
	 * @param line
	 *            the line
	 */
	void ReadHetAtomRecord(Molecule mol, String line)
	{
	}
	
	/**
	 * Tok.
	 * 
	 * @param s
	 *            the s
	 * @param start
	 *            the start
	 * @param width
	 *            the width
	 * @return the string
	 */
	String tok(String s, int start, int width)
	{	
		// from 1indexed to 0 indexted.
		return s.substring(start-1,width).trim();
	}
	
	/**
	 * Read atom record.
	 * 
	 * @param mol
	 *            the mol
	 * @param line
	 *            the line
	 */
	void ReadAtomRecord(Molecule mol, String line)
	{
/*
			String [] s = line.split(" ");
			ArrayList<String>  tokens = new ArrayList<String>();
			for (String st: s)
			{
				if (!st.equals(""))
					tokens.add(st);
			}
			
 */
		/*		 1 -  6        Record name     "ATOM  "                                            

		 7 - 11        Integer         Atom serial number.                   

		13 - 16        Atom            Atom name.                            

		17             Character       Alternate location indicator.         

		18 - 20        Residue name    Residue name.                         

		22             Character       Chain identifier.                     

		23 - 26        Integer         Residue sequence number.              

		27             AChar           Code for insertion of residues.       

		31 - 38        Real(8.3)       Orthogonal coordinates for X in Angstroms.                       

		39 - 46        Real(8.3)       Orthogonal coordinates for Y in Angstroms.                            

		47 - 54        Real(8.3)       Orthogonal coordinates for Z in Angstroms.                            

		55 - 60        Real(6.2)       Occupancy.                            

		61 - 66        Real(6.2)       Temperature factor (Default = 0.0).                   

		73 - 76        LString(4)      Segment identifier, left-justified.   

		77 - 78        LString(2)      Element symbol, right-justified.      

		79 - 80        LString(2)      Charge on the atom.    
		*/
		try {
			double x, y, z;
			Integer seq;
			boolean isHetero = tok(line, 1,6).equals("HETATM");
			String tempIndex = tok(line, 7,11);
			seq = Integer.parseInt(tempIndex);
			
			String qualifiedname = tok(line, 13,16);
			x = Double.parseDouble(tok(line, 31,38));
			y = Double.parseDouble(tok(line, 39,46));
			z = Double.parseDouble(tok(line, 47,54));
			String symbol = tok(line, 77,78);
			if (symbol.length() > 1)
			{
				char[] sym = symbol.toCharArray();
				sym[1] = Character.toLowerCase(sym[1]);
				symbol = new String(sym);
			}
			Atom atom = new Atom(symbol);
			atom.setName(qualifiedname);
			if (atom.getAtomicNumber() == 0)
				return;
			atom.setPosition(x, y, z);
			atom.setID(seq);
			
			String res = tok(line, 18, 20);
			residues.addByName(res, atom);
			String chain = tok(line, 22,22);
			chains.addByName(chain, atom);
			String resNum = tok(line, 23,26);
			if (!isHetero) resNums.addByName(resNum+ ":" +chain, res, atom);
			water.addByName((res.equals("HOH")? "water": "other"), atom);
			heteroStructures.addByName((res.equals("HEM") ? "Heme" + chain : "other"), atom);

			molecule.AddAtom(atom);
		}
		catch (java.lang.NumberFormatException e)
		{
			e.printStackTrace();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Read header record.
	 * 
	 * @param mol
	 *            the mol
	 * @param line
	 *            the line
	 */
	void ReadHeaderRecord(Molecule mol, String line)
	{
		line.trim();
		molecule.getPropList().Add("HeaderType", line.substring(7,40).trim(), null);
		molecule.getPropList().Add(line.substring(0,7), line.substring(7,7+40).trim(), null);
		molecule.getPropList().Add("Date", line.substring(47,47+10).trim(), null);
		molecule.getPropList().Add("PDB Code", line.substring(57,57+10).trim(), null);
	}

	/**
	 * Read pdb file.
	 * 
	 * @param molecule
	 *            the molecule
	 */
	void ReadPDBFile(Molecule molecule)
	{
		
		molecule.fileName = fileName;
	
		
		chains = new SubstructureSet("Chains", "Individual Chains within the molecule");
		molecule.getSubstructures().add(chains);
		residues = new SubstructureSet("Residues", "Grouped by amino acid type");
		molecule.getSubstructures().add(residues);
		resNums = new Peptide("ResNums", "individual amino acids along the peptide");
		molecule.getSubstructures().add(resNums);
		water = new SubstructureSet(SubstructureType.WATER.text(), "Water molecules or the remainder");
		molecule.getSubstructures().add(water);
		heteroStructures = new SubstructureSet(SubstructureType.HETEROSTRUCTURES.text(), "Fragments in the structure that are not peptides");
		molecule.getSubstructures().add(heteroStructures);
		
		
		String line;
		try {
			while ( (line = br.readLine()) != null)
			{

				if (line.length() == 0)
					break;
				String token = line.substring(0,6).trim();
				if (token.length() == 0)
					break;
				if (token.equals("COMPND") ||
					token.equals("SOURCE") ||
						token.equals("AUTHOR"))
				{
					String seq = line.substring(7, 7+3);
					line = line.trim();
					token.toLowerCase();
					molecule.getPropList().Add(token+seq, line.substring(10).trim(), null);
				}
				else if (token.equals("HEADER"))
				{
					ReadHeaderRecord(molecule, line);
				}
				else if (token.equals("TITLE"))
				{
					//String seq = line.substring(7, 7+3);
					line = line.trim();
					token.toLowerCase();
					if (molecule.getName() != null)
						line = molecule.getName() + line.substring(10).trim();
					else
						line = line.substring(10).trim();
					molecule.setName(line);
				}
				else if (token.equals("ATOM"))
				{
					ReadAtomRecord(molecule, line);
				}
				else if (token.equals( "HETATM"))
				{
					ReadAtomRecord(molecule, line);
				}
			}
		} catch (IOException e) {
			Logger.addMessage(this, e);
		}
		molecule.MakeBondsByDistanceClustered();
		resNums.matchAll();
		resNums.printMatches();
		molecule.getSubstructures().printList(false);

		return;
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#getFileExtensions()
	 */
	@Override
	public String[] getFileExtensions() {
		String [] extensions = {"PDB"};
		return extensions;
	}
}
