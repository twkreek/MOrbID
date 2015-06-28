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
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;

import com.bobandthomas.Morbid.molecule.data.SpatialDataList;
import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.Point3D;

// TODO: Auto-generated Javadoc
/**
 * The Class Molecule.
 * 
 * @author Thomas Kreek
 */
public class Molecule extends CLoadableItem implements Iterable<Atom>, IChangeNotifier {

			/** The atoms. */
			private Substructure	atoms;
		    
    		/** The bonds. */
    		BondList	bonds;
			
			/** The selected list. */
			Substructure	selectedList;
			
			/**
			 * Atoms.
			 * 
			 * @return the substructure
			 */
			public Substructure Atoms() { return atoms; }
			
			/**
			 * Bonds.
			 * 
			 * @return the bond list
			 */
			public BondList Bonds() { return bonds; }
			
			/** The mo. */
			MolecularOrbitalSet mo;
			
			/**
			 * Checks if it has mo.
			 * 
			 * @return true, if successful
			 */
			public boolean hasMO() { return mo != null && mo.nOrbitals > 0; }
//			VibrationSet vibrations;
//			boolean HasVibrations() { return vibrations != null && vibrations.NumRoots > 0; }
			/** The spatial data. */
private SpatialDataList spatialData;

			/** The file name. */
			public String fileName;
			
			/** The name. */
			String name;

			/** The substructures. */
			private SubstructureMap substructures;
			
			/** The prop list. */
			private MoleculePropertyList propList;

			/**
			 * Gets the prop list.
			 * 
			 * @return the prop list
			 */
			public MoleculePropertyList getPropList() {
				return propList;
			}
			
			/**
			 * Adds the property.
			 * 
			 * @param name
			 *            the name
			 * @param value
			 *            the value
			 * @param units
			 *            the units
			 */
			public void addProperty(String name, String value, String units) {
				propList.Add(name, value, units);
			}
			
			/**
			 * Gets the property.
			 * 
			 * @param name
			 *            the name
			 * @return the property
			 */
			public MoleculeProperty getProperty(String name) {
				return propList.getByName(name);
			}
			
			/**
			 * Adds the property.
			 * 
			 * @param arg0
			 *            the arg0
			 * @return true, if successful
			 */
			public boolean addProperty(MoleculeProperty arg0) {
				return propList.add(arg0);
			}

			/** The hydrogens. */
			private SubstructureSet hydrogens;
		  
		    /** The n electrons. */
    		private int nElectrons;
		    
    		/** The empirical. */
    		private Empirical empirical;
		    
		    /**
			 * Instantiates a new molecule.
			 */
    		public Molecule()
			{
				substructures = new SubstructureMap();
				propList = new MoleculePropertyList();
				atoms = new Substructure();
				atoms.registerListener(this);
				atoms.setReParent(true);
				bonds = new BondList();
				bonds.registerListener(this);
				selectedList = new Substructure();
				spatialData = new SpatialDataList();
				substructures.add(new SubstructureSet("All", "All Atoms in the molecule"));
				hydrogens = new SubstructureSet("Hydrogens", "Hydrogens vs Atoms");
				substructures.add(hydrogens);
				empirical = new Empirical(this);		
				
			}

	
		    
			/**
			 * Checks if it has charges.
			 * 
			 * @return true, if successful
			 */
			public boolean HasCharges() { return Atoms().HasCharges(); }
			
			/**
			 * Use default charges.
			 */
			public void useDefaultCharges() { }

			/**
			 * Gets the substructures.
			 * 
			 * @return the substructures
			 */
			public SubstructureMap getSubstructures() {
				return substructures;
			}
			
			/**
			 * Adds the substructure.
			 * 
			 * @param subst
			 *            the subst
			 */
			public void addSubstructure(SubstructureSet subst)
			{
				substructures.add(subst);
			}
			
			/**
			 * Sets the substructures.
			 * 
			 * @param substructures
			 *            the new substructures
			 */
			public void setSubstructures(SubstructureMap substructures) {
				this.substructures = substructures;
			}
			
			/**
			 * Calc bounds.
			 */
			public void CalcBounds() { atoms.CalcBounds(); }
		    
    		/**
			 * Gets the atom.
			 * 
			 * @param i
			 *            the i
			 * @return the atom
			 */
    		public Atom GetAtom(int i) { return atoms.get(i); }
		    
    		/**
			 * Adds the atom.
			 * 
			 * @param a
			 *            the a
			 * @return the int
			 */
    		public int AddAtom(Atom a) { 
		    	getEmpirical().add(a);
		    	if (a.isA(Element.H))
		    		hydrogens.addByName("Hydogen", a);
		    	else
		    		hydrogens.addByName("Heavy", a);
		    	
		    	atoms.add(a); 
		    	return atoms.size(); 
		    	}
		    
    		/**
			 * Num atoms.
			 * 
			 * @return the int
			 */
    		public int NumAtoms() { return atoms.size(); }
		    
    		/**
			 * Center atoms.
			 */
    		public void CenterAtoms() { atoms.centerMolecule(); }
		    
    		/**
			 * Gets the bounds.
			 * 
			 * @return the bounding box
			 */
    		public BoundingBox GetBounds() { return atoms.GetBounds();}
		 
			/**
			 * Gets the bond.
			 * 
			 * @param at1
			 *            the at1
			 * @param at2
			 *            the at2
			 * @return the bond
			 */
			Bond GetBond(int at1, int at2) { return bonds.getBond(at1, at2); }
			
			/**
			 * Adds the bond.
			 * 
			 * @param at1
			 *            the at1
			 * @param at2
			 *            the at2
			 * @return the bond
			 */
			Bond AddBond(int at1, int at2) { Bond b = new Bond(atoms.get(at1), atoms.get(at2)); AddBond(b); return b; }
		    
    		/**
			 * Gets the bond.
			 * 
			 * @param i
			 *            the i
			 * @return the bond
			 */
    		public Bond GetBond(int i) { return bonds.get(i); }
		    
    		/**
			 * Adds the bond.
			 * 
			 * @param b
			 *            the b
			 * @return the int
			 */
    		public int AddBond(Bond b) { bonds.add(b); return 1; } 
		    
    		/**
			 * Num bonds.
			 * 
			 * @return the int
			 */
    		public int NumBonds() { return bonds.size(); }
		    
		    /**
			 * Make bonds by distance.
			 */
    		public void MakeBondsByDistance()
		    {
		    	int i, j;
		    	
		        for (i=0; i< NumAtoms(); i++) {
		           Atom ai = atoms.get(i);
		    	   for (j=i+1; j< NumAtoms(); j++) {
		    			Atom aj = GetAtom(j);
		    			Point3D pi, pj;
		    			pi = new Point3D(ai.Position());
		                pj = new Point3D(aj.Position());
		    			if (pi.Sub(pj).Length() <=
		    				 (ai.Radius() + aj.Radius())+0.1)
		    			{
		    				Bond bond = new Bond(ai,aj);
		                 	AddBond(bond);
		    	        }
		    	   }
		        }

		    }
		    
	    	/** The by x. */
	    	ArrayList<Atom>  byX = null;
	    	
	    	/**
			 * Make sorted list.
			 */
	    	private void makeSortedList()
	    	{
	    		
		    	class AtomComparatorX implements Comparator<Atom>
		    	{

					@Override
					public int compare(Atom arg0, Atom arg1) {
						if (arg0.pos.x < arg1.pos.x)
							return -1;
						else
							return 1;
					}
		    		
		    	}
		    	
		    	byX = new ArrayList<Atom>();
		    	byX.addAll(atoms);
		    	Collections.sort(byX, new AtomComparatorX());
	    	}
	    	
	    	/**
			 * Find closest atom.
			 * 
			 * @param p
			 *            the p
			 * @return the atom
			 */
	    	public Atom findClosestAtom(Point3D p)
	    	{

	    		makeSortedList();
		    	Atom closest = null;
		    	double distSquared = 20;
		    	boolean foundStart = false;
		    	for (Atom ai: byX)
		    	{	
		    		if (ai.x() > p.x + distSquared)
		    			break;
		    		if (foundStart || (foundStart = ai.x() < p.x - distSquared))
		    			continue;
		    		if (ai.y() > p.y + distSquared)
		    			continue;
		    		if (ai.z() > p.z + distSquared)
		    			continue;
		    		
		    		double dist = ai.Position().distanceSquared(p);
		    		if (dist < distSquared)
		    		{
		    			distSquared = dist;
		    			closest = ai;
		    		}
		    	}
		    	return closest;
	    	}
		    
    		/**
			 * Make bonds by distance clustered.
			 */
    		public void MakeBondsByDistanceClustered()
		    {

		    	makeSortedList();
		    	for (int i = 0; i< byX.size(); i++)
		    	{
		    		Atom ai = byX.get(i);
		    		Point3D pi = ai.Position();
		    		for (int j = i+1; j< byX.size(); j++)	
		    		{
		    			Atom aj = byX.get(j);
		    			
		    			if (aj.pos.x > ai.pos.x + 3)
		    				break;
		    			if (aj.pos.x < ai.pos.x - 3)
		    				continue;
		    			Point3D pj;
		    			pi = new Point3D(ai.Position());
		                pj = new Point3D(aj.Position());
		    			if (pi.Sub(pj).Length() <=
		    				 (ai.Radius() + aj.Radius())+0.1)
		    			{
		    				Bond bond = new Bond(ai,aj);
		                 	AddBond(bond);
		    	        }
		    			
		    		}
		    	}
		    	
		    }
		    
    		/**
			 * Make tiny molecule.
			 */
    		public void makeTinyMolecule()
		    {
		    	Atom at1 = new Atom("O");
		    	at1.setPosition(0.0, 1.0, 0.0);
		    	at1.setCharge(0.4);
		    	AddAtom(at1);
		    	
		    	Atom at2 = new Atom("C");
		    	at2.setPosition(0.0, 0.0, 0.0);
		    	at2.setCharge(0.1);
		    	AddAtom(at2);
		    	
		    	Bond bo = new Bond(at1, at2);
		    	bo.setNominalBondOrder(2);
		    	AddBond(bo);
		    	
		    	at1 = new Atom("S");
		    	at1.setPosition(-1.0, 0, 0.5);
		    	at1.setCharge(-0.4);
		    	AddAtom(at1);
		    	
		    	AddBond(new Bond(at1, at2));
		    	
		    	at1 = new Atom("N");
		    	at1.setPosition(1.0, 1.0, 0);
		    	at2.setCharge(-0.1);
		    	AddAtom(at1);
		    	
		    	AddBond(new Bond(at1, at2));
		    	
		    }
			
			/* (non-Javadoc)
			 * @see java.lang.Iterable#iterator()
			 */
			@Override
			public Iterator<Atom> iterator() {
				return atoms.iterator();
			}
			
			/**
			 * Gets the spatial data.
			 * 
			 * @return the spatial data
			 */
			public SpatialDataList getSpatialData() {
				return spatialData;
			}
			
			/**
			 * Sets the spatial data.
			 * 
			 * @param spatialData
			 *            the new spatial data
			 */
			public void setSpatialData(SpatialDataList spatialData) {
				this.spatialData = spatialData;
			}
			
			/**
			 * Gets the mo.
			 * 
			 * @return the mo
			 */
			public MolecularOrbitalSet getMo() {
				return mo;
			}
			
			/**
			 * Sets the mo.
			 * 
			 * @param mo
			 *            the new mo
			 */
			public void setMo(MolecularOrbitalSet mo) {
				this.mo = mo;
			}
			
			/**
			 * Gets the n electrons.
			 * 
			 * @return the n electrons
			 */
			public int getnElectrons() {
				return nElectrons;
			}
			
			/**
			 * Sets the n electrons.
			 * 
			 * @param i
			 *            the new n electrons
			 */
			public void setnElectrons(int i) {
				this.nElectrons = i;
			}
			
			/**
			 * Gets the empirical.
			 * 
			 * @return the empirical
			 */
			public Empirical getEmpirical() {
				return empirical;
			}
}


