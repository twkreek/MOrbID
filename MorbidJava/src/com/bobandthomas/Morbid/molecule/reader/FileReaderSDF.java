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

import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Bond;
import com.bobandthomas.Morbid.molecule.MoleculeProperty;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.wrapper.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class FileReaderSDF.
 * 
 * @author Thomas Kreek
 */
public class FileReaderSDF extends MoleculeFileReader {

	/**
	 * Instantiates a new file reader sdf.
	 */
	public FileReaderSDF() {
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Save()
	 */
	@Override
	public void Save() {

	}
	
	/**
	 * Read partial charges.
	 */
	void readPartialCharges ()
	{
		Tokenizer t = getNextLine();
		int nCharges = t.GetIntToken();
		for (int i = 0; i < nCharges; i++)
		{
			t = getNextLine();
			int atomID = t.GetIntToken();
			float charge = t.GetFloatToken();
			molecule.Atoms().get(atomID-1).setCharge(charge);
		}
		molecule.CalcBounds();
	}
	
	/**
	 * Read property.
	 * 
	 * @param property
	 *            the property
	 * @return the molecule property
	 */
	MoleculeProperty readProperty(String property)
	{
		property.replace('_', ' ');
		if (property.startsWith("PUBCHEM "))
			property = property.substring("PUBCHEM ".length());
		String value = "";
		for (Tokenizer t = getNextLine(); t.size() != 0; t = getNextLine())
		{
			value += t.toString() + ", ";
		}
		System.out.println(value);
		MoleculeProperty mp = new MoleculeProperty(property, value, "");
		molecule.addProperty(mp);
		return mp;

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Read()
	 */
	@Override
	public void Read() {
		if (!PreRead()) return;
		int natoms;
		int nbonds;
		String line;
		try {
			line = br.readLine(); // name
			molecule.setName(line);
			line = br.readLine(); // undetermined
			line = br.readLine();
			line = br.readLine(); // natoms and nbonds
			Tokenizer t = new Tokenizer(line);
			natoms = t.GetIntToken();
			nbonds = t.GetIntToken();
			for (int i = 0; i< natoms;  i++)
			{
				line = br.readLine();
				t = new Tokenizer(line);
				Point3D p = new Point3D(t.GetFloatToken(), t.GetFloatToken(), t.GetFloatToken());
				Atom atom = new Atom(t.GetStringToken());
				atom.setPosition(p.x,  p.y,  p.z);
				molecule.AddAtom(atom);
			}
			for (int i = 0; i< nbonds;  i++)
			{
				line = br.readLine();
				t = new Tokenizer(line);
				Bond bond = new Bond(molecule.Atoms().get(t.GetIntToken()-1), molecule.Atoms().get(t.GetIntToken()-1));
				bond.setNominalBondOrder(t.GetIntToken());
				molecule.AddBond(bond);
			}
			
			line = br.readLine();
			do 
			{
				t = new Tokenizer(line);
				if (t.size() <= 0)
				{
					line = br.readLine();
					continue;
				}
				if (t.GetStringToken().equals(">"))
				{
					String property = t.GetStringToken();
					property = property.replace('<', ' ').replace('>', ' ').trim();
					property = property.replace('_', ' ');

					if (property.equals("PUBCHEM MMFF94 PARTIAL CHARGES"))
						readPartialCharges();
					else
					{
						readProperty(property);
					}
				}
				line = br.readLine();
			} while (line != null);
			
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
		String[] extensions = {"SDF"};
		return extensions;
	}

}
