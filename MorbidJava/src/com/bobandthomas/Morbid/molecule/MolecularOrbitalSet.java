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
package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;

// TODO: Auto-generated Javadoc
/**
 * The Class MolecularOrbitalSet.
 * 
 * @author Thomas Kreek
 */
public class MolecularOrbitalSet extends CLoadableItem {
	
	/** The num filled levels. */
	public int numFilledLevels;
	
	/**
	 * The Enum PrimaryQuantum.
	 * 
	 * @author Thomas Kreek
	 */
	public enum PrimaryQuantum
	{
		
		/** The s. */
		S (0),
		
		/** The p. */
		P (1),
		
		/** The d. */
		D (2), 
		
		/** The f. */
		F (3);
		
		/**
		 * Instantiates a new primary quantum.
		 * 
		 * @param index
		 *            the index
		 */
		PrimaryQuantum (int index)
		{
			
		}
	}
	
	/**
	 * The Enum sOrbital.
	 * 
	 * @author Thomas Kreek
	 */
	public enum sOrbital
	{
		
		/** The s. */
		s(0);
		
		/**
		 * Instantiates a new s orbital.
		 * 
		 * @param index
		 *            the index
		 */
		sOrbital(int index)
		{
			
		}
	}
	
	/**
	 * The Enum pOrb.
	 * 
	 * @author Thomas Kreek
	 */
	public enum pOrb 
	{
		
		/** The px. */
		px (-1),
		
		/** The py. */
		py (0),
		
		/** The pz. */
		pz (1);
		
		/**
		 * Instantiates a new p orb.
		 * 
		 * @param index
		 *            the index
		 */
		pOrb(int index)
		{
			
		}
	} ;
	
	/**
	 * The Enum dOrb.
	 * 
	 * @author Thomas Kreek
	 */
	public enum dOrb{
		
		/** The dx2y2. */
		dx2y2 (-2),
		
		/** The dz2. */
		dz2 (-1),
		
		/** The dxy. */
		dxy (0),
		
		/** The dyz. */
		dyz (1),
		
		/** The dxz. */
		dxz (2);
		 
 		/**
		 * Instantiates a new d orb.
		 * 
		 * @param index
		 *            the index
		 */
 		dOrb(int index)
		{
			
		}
	} ;
	

	/**
	 * The Class AtomicOrbital.
	 * 
	 * @author Thomas Kreek
	 */
	public class AtomicOrbital {
		
		/** The l. */
		public int L;
		
		/** The L1. */
		public PrimaryQuantum L1;
		
		/** The n. */
		public int N;
		
		/** The Ml. */
		public int Ml;
		
		/** The atom number. */
		public int atomNumber;
		
		/** The atom. */
		public Atom atom;
		
		/** The contracted. */
		public double contracted;
		
		/** The zeta. */
		public double zeta;
		
		/** The zeta2. */
		public double[] zeta2 = new double[3];
	};

	/**
	 * The Class MolecularOrbital.
	 * 
	 * @author Thomas Kreek
	 */
	public class MolecularOrbital {
		
		/** The calcd. */
		boolean calcd;
		
		/** The energy. */
		double energy;
		
		/** The degeneracy set. */
		int degeneracySet;
		
		/** The occupied. */
		boolean occupied;
		
		/** The is homo lumo. */
		boolean isHomoLumo;
		
		/**
		 * Instantiates a new molecular orbital.
		 */
		MolecularOrbital()
		{
//		 	dataSet = nullptr;
		 	calcd = false;
			energy = 0.0f;
			degeneracySet = 0;
		}

	};

	/** The molecule. */
	Molecule molecule;
	
	/** The mo. */
	MolecularOrbital[] mo;
	
	/** The ao. */
	public AtomicOrbital[] ao;
	
	/** The coeff array. */
	double[] coeffArray;
	
	/** The has energies. */
	boolean hasEnergies;
	
	/** The n orbitals. */
	public int nOrbitals;
	
	/** The m_n electrons. */
	int m_nElectrons;
	
	/** The homo. */
	public int HOMO;
	
	/** The lumo. */
	int LUMO;
	
	/** The mo names. */
	ArrayList<String> moNames = null;

	/**
	 * Coefficient.
	 * 
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @return the double
	 */
	public double coefficient(int i, int j) {
		return coeffArray[i * nOrbitals + j];
	}

	/**
	 * Coefficient.
	 * 
	 * @param i
	 *            the i
	 * @param j
	 *            the j
	 * @param f
	 *            the f
	 */
	public void coefficient(int i, int j, double f) {
		coeffArray[i * nOrbitals + j] = f;
	}

	/**
	 * Instantiates a new molecular orbital set.
	 * 
	 * @param orbCount
	 *            the orb count
	 */
	public MolecularOrbitalSet(int orbCount) {

		nOrbitals = orbCount; 

		mo = new MolecularOrbital[nOrbitals];
		ao = new AtomicOrbital[nOrbitals];
		for (int i = 0; i < orbCount; i++)
		{
			mo[i] = new MolecularOrbital();
			ao[i] = new AtomicOrbital();
		}
		coeffArray = new double[nOrbitals*nOrbitals];
		m_nElectrons = 0;
	}
	
	/**
	 * Gets the mo name.
	 * 
	 * @param moNumber
	 *            the mo number
	 * @return the string
	 */
	String GetMOName(int moNumber)
	{
		String name = new String("unimplemented");
		String s = "";
		if (hasEnergies)
			s = String.format("MO %3d: %5.4feV ", moNumber, mo[moNumber].energy);
		else
			s = String.format("MO %3d: ", moNumber);
		name = s;
		if (m_nElectrons > 0)
		{
			if (moNumber < m_nElectrons /2)
				name += "occ";
			else if (moNumber == m_nElectrons /2)
				name += "HOMO";
			else if (moNumber == (m_nElectrons /2 + 1))
				name += "LUMO";
			else
				name += "un";
		}
		return name;
	}
	
	/**
	 * Gets the MO name list.
	 * 
	 * @return the MO name list
	 */
	public ArrayList<String> getMONameList()
	{
		moNames = new ArrayList<String>();
		for (int i = 0; i < nOrbitals; i ++)
		{
			moNames.add(GetMOName(i));
		}
			
		return moNames;
	}
	
	/**
	 * Update mo list.
	 */
	public void UpdateMOList()
	{
		for (int i = 0; i < nOrbitals; i++)
		{
			if (m_nElectrons > 0)
			{
				if (i < m_nElectrons /2)
					mo[i].occupied = true;
				else if (i == m_nElectrons /2)
				{
					mo[i].occupied = true;
					mo[i].isHomoLumo = true;
					HOMO = i;
				}
				else if (i == (m_nElectrons /2 + 1))
				{
					mo[i].occupied = false;
					mo[i].isHomoLumo = true;
					LUMO = i;
				}
				else
					mo[i].occupied = false;
			}
			
		}
	}
	
	/**
	 * Construct ao list.
	 * 
	 * @param mol
	 *            the mol
	 */
	public void ConstructAOList(Molecule mol)
	{   
		int i;
		molecule = mol;
		AtomType aType;
			
		UpdateMOList();
		int orbCount = 0;
		for (i=0; i < mol.NumAtoms(); i++)
	    {   
			Atom a = mol.GetAtom(i);
	    	aType = a.atomType;
	     	if (aType.ns > 0)
	     	{
	     		ao[orbCount].atomNumber = i;
	     		ao[orbCount].atom = a;
				ao[orbCount].N = aType.ns;
				ao[orbCount].L = 0; // S - Orbital
				ao[orbCount].Ml= 0;
				ao[orbCount].zeta = (float) aType.zS;
				ao[orbCount].contracted = 0;
				ao[orbCount].zeta2[0] = 0;
				ao[orbCount].zeta2[1] = 0;
				ao[orbCount].zeta2[2] = 0; 
	     		orbCount ++;
	     	}
	     	if (aType.np> 0)
	     	{         
	     		int j;
	     		for (j = 0; j < 3; j++)
	     		{   
	     			ao[orbCount].N = aType.np;
	     			ao[orbCount].L = 1; // P = Orbital;
	     			ao[orbCount].Ml = j-1; // orientation;
	     			ao[orbCount].atomNumber = i;
		     		ao[orbCount].atom = a;
					ao[orbCount].zeta = (float) aType.zP;
					ao[orbCount].contracted = 0;
					ao[orbCount].zeta2[0] = 0;
					ao[orbCount].zeta2[1] = 0;
					ao[orbCount].zeta2[2] = 0; 
		     		orbCount++;
	     		}
	     	}
	     	if (aType.nd> 0)
	     	{
	     		int j;
	     		for (j = 0; j < 5; j++)
	     		{   
	     			ao[orbCount].N = aType.nd;
	     			ao[orbCount].L = 2; // D = Orbital;
	     			ao[orbCount].Ml = j-2; // orientation;
	     			ao[orbCount].atomNumber = i; 
		     		ao[orbCount].atom = a;
					ao[orbCount].zeta = (float) aType.zD;
					ao[orbCount].contracted = 0;
					ao[orbCount].zeta2[0] = 0;
					ao[orbCount].zeta2[1] = 0;
					ao[orbCount].zeta2[2] = 0; 
	     			orbCount++;
	     		}
	     	}	
	    } 
	}
}




