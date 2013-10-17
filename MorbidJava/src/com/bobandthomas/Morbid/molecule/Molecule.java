package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import com.bobandthomas.Morbid.molecule.data.SpatialDataList;
import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;

/**
 * @author Thomas Kreek
 *
 */
public class Molecule extends CLoadableItem implements Iterable<Atom> {

			private AtomList	atoms;
		    BondList	bonds;
			AtomList	selectedList;
			public AtomList Atoms() { return atoms; }
			public BondList Bonds() { return bonds; }
			
//			MolecularOrbitalSet mo;
//			VibrationSet vibrations;
//			boolean HasVibrations() { return vibrations != null && vibrations.NumRoots > 0; }
			private SpatialDataList spatialData;

			public String fileName;
			String name;

			private SubstructureMap substructures;
			private MoleculePropertyList propList;
			private SubstructureSet hydrogens;
		  
		    short numFilledLevels;
		    short nElectrons;
		    public Empirical empirical;
		    
		    public class Empirical extends CLoadableItem
		    {

				HashMap <AtomType, Integer> empirical;
				public Empirical()
				{
					empirical = new HashMap<AtomType, Integer>();
				}
				public void add(Atom a)
				{
					
			    	Integer count = empirical.get(a.getAtomType());
			    	if (count == null)
			    	{
			    		count = new Integer(0);
			    	}
			    	count += 1;
			    	empirical.put(a.getAtomType(), count);
				}
		    	
			    public String getFormula()
			    {
			    	String text = "";
			    	
			    	 Iterator<Entry<AtomType, Integer>> it = empirical.entrySet().iterator();
			    	    while (it.hasNext()) {
			    	        Map.Entry<AtomType, Integer> pairs = (Map.Entry<AtomType, Integer>)it.next();
			    	        
			    	        text = text +pairs.getKey().getName() + "("+pairs.getValue() + ") ";
			    	       
			    	        it.remove(); // avoids a ConcurrentModificationException
			    	    }
			    		
			    	return text;
			    }
		    }

			public Molecule()
			{
				substructures = new SubstructureMap();
				propList = new MoleculePropertyList();
				atoms = new AtomList();
				bonds = new BondList();
				selectedList = new AtomList();
				spatialData = new SpatialDataList();
				substructures.add(new SubstructureSet("All", "All Atoms in the molecule"));
				hydrogens = new SubstructureSet("Hydrogens", "Hydrogens vs Atoms");
				substructures.add(hydrogens);
				empirical = new Empirical();
				
				
			}

	
		    
			public boolean HasCharges() { return Atoms().HasCharges(); }
			public void useDefaultCharges() { }
			public SubstructureMap getSubstructures() {
				return substructures;
			}
			public void addSubstructure(SubstructureSet subst)
			{
				substructures.add(subst);
			}
			public void setSubstructures(SubstructureMap substructures) {
				this.substructures = substructures;
			}
			public MoleculePropertyList getPropList() {
				return propList;
			}
			public void setPropList(MoleculePropertyList propList) {
				this.propList = propList;
			}
			public void CalcBounds() { atoms.CalcBounds(); }
		    public Atom GetAtom(int i) { return atoms.get(i); }
		    public int AddAtom(Atom a) { 
		    	empirical.add(a);
		    	if (a.isA(Element.H))
		    		hydrogens.addByName("Hydogen", a);
		    	else
		    		hydrogens.addByName("Heavy", a);
		    	
		    	atoms.AddAtom(a); 
		    	return atoms.size(); 
		    	}
		    public int NumAtoms() { return atoms.size(); }
		    public void CenterAtoms() { atoms.centerMolecule(); }
		    public BoxType GetBounds() { return atoms.GetBounds();}
		 
			Bond GetBond(int at1, int at2) { return bonds.getBond(at1, at2); }
			Bond AddBond(int at1, int at2) { Bond b = new Bond(atoms.get(at1), atoms.get(at2)); AddBond(b); return b; }
		    public Bond GetBond(int i) { return bonds.get(i); }
		    public int AddBond(Bond b) { bonds.add(b); return 1; } 
		    public int NumBonds() { return bonds.size(); }
		    
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
		    			pi.sub(pj);
		    			if (pi.Length() <=
		    				 (ai.Radius() + aj.Radius())+0.1)
		    			{
		    				Bond bond = new Bond(ai,aj);
		                 	AddBond(bond);
		    	        }
		    	   }
		        }

		    }
		    
	    	ArrayList<Atom>  byX = null;
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
		    			pi.sub(pj);
		    			if (pi.Length() <=
		    				 (ai.Radius() + aj.Radius())+0.1)
		    			{
		    				Bond bond = new Bond(ai,aj);
		                 	AddBond(bond);
		    	        }
		    			
		    		}
		    	}
		    	
		    }
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
			@Override
			public Iterator<Atom> iterator() {
				return atoms.iterator();
			}
			public SpatialDataList getSpatialData() {
				return spatialData;
			}
			public void setSpatialData(SpatialDataList spatialData) {
				this.spatialData = spatialData;
			}
}

