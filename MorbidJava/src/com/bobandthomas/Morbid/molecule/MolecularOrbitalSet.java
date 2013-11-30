package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.CLoadableItem;

public class MolecularOrbitalSet extends CLoadableItem {
	
	public int numFilledLevels;
	public enum PrimaryQuantum
	{
		S (0),
		P (1),
		D (2), 
		F (3);
		PrimaryQuantum (int index)
		{
			
		}
	}
	public enum sOrbital
	{
		s(0);
		sOrbital(int index)
		{
			
		}
	}
	public enum pOrb 
	{
		px (-1),
		py (0),
		pz (1);
		pOrb(int index)
		{
			
		}
	} ;
	public enum dOrb{
		dx2y2 (-2),
		dz2 (-1),
		dxy (0),
		dyz (1),
		dxz (2);
		 dOrb(int index)
		{
			
		}
	} ;
	

	public class AtomicOrbital {
		public int L;
		public PrimaryQuantum L1;
		public int N;
		public int Ml;
		public int atomNumber;
		public Atom atom;
		public double contracted;
		public double zeta;
		public double[] zeta2 = new double[3];
	};

	public class MolecularOrbital {
		boolean calcd;
		double energy;
		int degeneracySet;
		boolean occupied;
		boolean isHomoLumo;
		MolecularOrbital()
		{
//		 	dataSet = nullptr;
		 	calcd = false;
			energy = 0.0f;
			degeneracySet = 0;
		}

	};

	Molecule molecule;
	MolecularOrbital[] mo;
	public AtomicOrbital[] ao;
	double[] coeffArray;
	boolean hasEnergies;
	public int nOrbitals;
	int m_nElectrons;
	public int HOMO;
	int LUMO;
	ArrayList<String> moNames = null;

	public double coefficient(int i, int j) {
		return coeffArray[i * nOrbitals + j];
	}

	public void coefficient(int i, int j, double f) {
		coeffArray[i * nOrbitals + j] = f;
	}

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
	public ArrayList<String> getMONameList()
	{
		moNames = new ArrayList<String>();
		for (int i = 0; i < nOrbitals; i ++)
		{
			moNames.add(GetMOName(i));
		}
			
		return moNames;
	}
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




