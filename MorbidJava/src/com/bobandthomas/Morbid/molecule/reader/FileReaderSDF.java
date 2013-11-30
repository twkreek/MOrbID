package com.bobandthomas.Morbid.molecule.reader;

import java.io.IOException;

import com.bobandthomas.Morbid.UI.Logger;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Bond;
import com.bobandthomas.Morbid.utils.Point3D;

public class FileReaderSDF extends MoleculeFileReader {

	public FileReaderSDF() {
	}

	@Override
	public void Save() {

	}
	
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

					if (property.equals("PUBCHEM_MMFF94_PARTIAL_CHARGES"))
						readPartialCharges();
					else
					{
						property.replace('_', ' ');
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

	@Override
	public boolean Validate() {
		return false;
	}

	@Override
	public String[] getFileExtensions() {
		String[] extensions = {"SDF"};
		return extensions;
	}

}
