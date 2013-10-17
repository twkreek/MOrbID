package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;

public class Bond extends CLoadableItem {

		Atom atom1;
		Atom atom2;
		long a1;
		long a2;
		String hash;
		int nominalBondOrder;
	    float bondOrder;
		float boundaryFraction; // the ratio of the distance between atom1 center and atom2 center.
//		public boolean IsHBond();
//		public boolean IsSaltBond();
//		public boolean IsMetalBond();
		public Atom atom(int  i) 
		{ 
			{ if (i == 0) return atom1; if (i==1) return atom2; return null;} 
		}


		String AtNoHashThis() { return AtNoHash(a1, a2); }
		static String AtNoHash(long a1, long a2)
		{
			String st = "b";
			st.concat(Long.toString(a1));
			st.concat("-");
			st.concat(Long.toString(a2));

			return st;
		}
	    public Bond(Atom at1, Atom at2)
	    {
	    	atom1 = at1;
	    	atom2 = at2;
	    	at1.addBond(this);
	    	at2.addBond(this);
	    	nominalBondOrder = 0;
	    	bondOrder = 0.0f;
	    	Init();
	    }
		void Init()
		{
			if (atom1 == null || atom2 == null)
			{
				return;
			}
			a1 = atom1.getID();
			a2 = atom2.getID();
			setName(AtNoHash(a1,a2));
		}
		public int getNominalBondOrder() {
			return nominalBondOrder;
		}
		public void setNominalBondOrder(int nominalBondOrder) {
			this.nominalBondOrder = nominalBondOrder;
		}
		public float getBondOrder() {
			return bondOrder;
		}
		public void setBondOrder(float bondOrder) {
			this.bondOrder = bondOrder;
		}
		public float getBoundaryFraction() {
			return boundaryFraction;
		}
		public void setBoundaryFraction(float boundaryFraction) {
			this.boundaryFraction = boundaryFraction;
		}
		
		public Atom getAtom(Atom a)
		{
			if (atom1==a) return atom2;
			return atom1;
		}


}
