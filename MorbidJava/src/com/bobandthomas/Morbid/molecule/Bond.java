package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

public class Bond extends CLoadableItem implements IPropertyAccessor {
	
		public enum BondType
		{
			COVALENT, IONIC, HYDROGEN, METAL_LIGAND;
		};

		Atom atom1;
		Atom atom2;
		long a1;
		long a2;
		String hash;
		int nominalBondOrder;
	    float bondOrder;
		float boundaryFraction; // the ratio of the distance between atom1 center and atom2 center.
		private BondType bondType;

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
			bondType = BondType.COVALENT;
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
		public BondType getBondType() {
			return bondType;
		}
		public double getLength()
		{
			return atom1.Position().Sub(atom2.Position()).Length();
		}


		public void setBondType(BondType bondType) {
			this.bondType = bondType;
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

		static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

			@Override
			public void initialize() {
				addPropertyDescriptor(0, "Index", Integer.class, false);
				addPropertyDescriptor(1, "Atom 1", String.class, false);
				addPropertyDescriptor(2, "Atom 2", String.class, true);
				addPropertyDescriptor(3, "Bond Order", Integer.class, false);
				addPropertyDescriptor(4, "Length", Double.class, false);
				
			}

		};
		IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
			@Override
			public Object getProperty(IPropertyDescriptor ipd) {
				switch (ipd.getIndex()){
				case 0: return getID();
				case 1: return atom1.getName() + a1;
				case 2: return atom2.getName() + a2;
				case 3: return getNominalBondOrder();
				case 4: return getLength();
				}
				return null;
			}
		
			@Override
			public void setProperty(IPropertyDescriptor ipd, Object value) {
				switch (ipd.getIndex()){
					case 0:	 return;
				}
				
			}

		};


		// {{ IAccessorDelegates
		
		public Object getProperty(String name) {
			return access.getProperty(name);
		}


		public void setProperty(String name, Object value) {
			access.setProperty(name, value);
		}


		public Object getProperty(int index) {
			return access.getProperty(index);
		}


		public void setProperty(int index, Object value) {
			access.setProperty(index, value);
		}


		public Object getProperty(IPropertyDescriptor desc) {
			return access.getProperty(desc);
		}


		public void setProperty(IPropertyDescriptor desc, Object value) {
			access.setProperty(desc, value);
		}


		public IPropertyDescriptorList getDescriptors() {
			return access.getDescriptors();
		}
		

		// }}
		

}
