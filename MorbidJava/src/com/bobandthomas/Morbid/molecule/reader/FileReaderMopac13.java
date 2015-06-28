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
import com.bobandthomas.Morbid.molecule.MolecularOrbitalSet;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.bobandthomas.Morbid.wrapper.Logger;

// TODO: Auto-generated Javadoc
/**
 * The Class FileReaderMopac13.
 * 
 * @author Thomas Kreek
 */
public class FileReaderMopac13 extends MoleculeFileReader {

	/**
	 * Instantiates a new file reader mopac13.
	 */
	public FileReaderMopac13() {
		super();
		binary = true;
		}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Save()
	 */
	@Override
	public void Save() {

	}
	
/*	Debugging for binary fileformat
 * private void sampleBytes(MorbidBufferedReader br, int byteSize, int itemCount) throws IOException
	{
		byte[] next = br.readBytes(itemCount * byteSize);
		int tempi = 1;
		long forward=0;
		int forwardInt = 0;
			for (byte b: next)
		{
			forward = (forward << 8) + b;
			forwardInt = (forwardInt << 8) + b;
			if (tempi % byteSize == 0) 
			{
				
				System.out.println();
				if (byteSize == 4) {
					System.out.println("int forward: " + forwardInt + ", "
							+ Double.longBitsToDouble(forwardInt));
					forwardInt = Integer.reverseBytes(forwardInt);
					System.out.println("int reversed: " + forwardInt + ", "
							+ Double.longBitsToDouble(forwardInt));
				} else {
					System.out.println("Long forward: " + forward + ", "
							+ Double.longBitsToDouble(forward));
					forward = Long.reverseBytes(forward);
					System.out.println("Long reversed: " + forward + ", "
							+ Double.longBitsToDouble(forward));
				}
				forward = 0;
				forwardInt = 0;
				
			}
			tempi++;
			System.out.print(String.format("%02X",b));
		}
	
	}
	*/

	/* (non-Javadoc)
 * @see com.bobandthomas.Morbid.molecule.reader.MoleculeFileReader#Read()
 */
@Override
	public void Read() {
		int i,j;
		int na;
		if (!PreRead()) return;
		MolecularOrbitalSet mo = null;

			try {
				br.readBytes(5);

				na = br.readInt();
		    
			if (na<1)
			{
				return;
			}
			molecule.setMo(mo = new MolecularOrbitalSet((int) br.readInt()));
			molecule.setnElectrons(br.readInt());
			Point3DList pos=new Point3DList();
				for (i=0; i<na; i++)
			{
				pos.add(new Point3D());
				double x = br.readDouble();
				pos.get(i).x =  (float) x;
			}
			for (i=0; i<na; i++)
				pos.get(i).y =  br.readDouble();
			for (i=0; i<na; i++)
				pos.get(i).z = br.readDouble();

			//six byte placeholder - no documentation found
			@SuppressWarnings("unused")
			byte s[] = br.readBytes(6);
			for (i=0; i<na; i++) {
				br.readInt();
				br.readInt();
				
			}

			s = br.readBytes(6);
			Point3DList exp = new Point3DList();
			for (i=0; i<na; i++)
			{
				exp.add(new Point3D());
				exp.get(i).x = (float) br.readDouble();
			}

			for (i=0; i<na; i++)
				// atom[i].expp 
				exp.get(i).y = (float) br.readDouble();
			for (i=0; i<na; i++)
				// atom[i].expd
				exp.get(i).z = (float) br.readDouble();
			for (i=0; i<na; i++){
				int atno = br.readInt();
				Atom at = new Atom(atno);
				at.pos = pos.get(i);
				molecule.AddAtom(at);
			}

			br.readBytes(6);
			for (i=0; i<mo.nOrbitals; i++)
				for (j=0; j<mo.nOrbitals; j++) {
					double dbl = br.readDouble();
					mo.coefficient(j,i, (float) dbl);
				}
			} catch (IOException e) {
				Logger.addMessage(this, e);
			}
		    
			molecule.MakeBondsByDistance();
			mo.ConstructAOList(molecule);
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
		String[] extensions = {"F13"};
		return extensions;
	}

}
