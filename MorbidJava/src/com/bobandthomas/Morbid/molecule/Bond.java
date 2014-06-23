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

import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

// TODO: Auto-generated Javadoc
/**
 * The Class Bond.
 * 
 * @author Thomas Kreek
 */
public class Bond extends CLoadableItem implements IPropertyAccessor {
	
		/**
		 * The Enum BondType.
		 * 
		 * @author Thomas Kreek
		 */
		public enum BondType
		{
			
			/** The covalent. */
			COVALENT, 
 /** The ionic. */
 IONIC, 
 /** The hydrogen. */
 HYDROGEN, 
 /** The metal ligand. */
 METAL_LIGAND;
		};

		/** The atom1. */
		Atom atom1;
		
		/** The atom2. */
		Atom atom2;
		
		/** The a1. */
		long a1;
		
		/** The a2. */
		long a2;
		
		/** The hash. */
		String hash;
		
		/** The nominal bond order. */
		int nominalBondOrder;
	    
    	/** The bond order. */
    	float bondOrder;
		
		/** The boundary fraction. */
		float boundaryFraction; // the ratio of the distance between atom1 center and atom2 center.
		
		/** The bond type. */
		private BondType bondType;

		/**
		 * Atom.
		 * 
		 * @param i
		 *            the i
		 * @return the atom
		 */
		public Atom atom(int  i) 
		{ 
			{ if (i == 0) return atom1; if (i==1) return atom2; return null;} 
		}


		/**
		 * At no hash this.
		 * 
		 * @return the string
		 */
		String AtNoHashThis() { return AtNoHash(a1, a2); }
		
		/**
		 * At no hash.
		 * 
		 * @param a1
		 *            the a1
		 * @param a2
		 *            the a2
		 * @return the string
		 */
		static String AtNoHash(long a1, long a2)
		{
			String st = "b";
			st.concat(Long.toString(a1));
			st.concat("-");
			st.concat(Long.toString(a2));

			return st;
		}
	    
    	/**
		 * Instantiates a new bond.
		 * 
		 * @param at1
		 *            the at1
		 * @param at2
		 *            the at2
		 */
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
		
		/**
		 * Initializes the.
		 */
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
		
		/**
		 * Gets the nominal bond order.
		 * 
		 * @return the nominal bond order
		 */
		public int getNominalBondOrder() {
			return nominalBondOrder;
		}
		
		/**
		 * Sets the nominal bond order.
		 * 
		 * @param nominalBondOrder
		 *            the new nominal bond order
		 */
		public void setNominalBondOrder(int nominalBondOrder) {
			this.nominalBondOrder = nominalBondOrder;
		}
		
		/**
		 * Gets the bond order.
		 * 
		 * @return the bond order
		 */
		public float getBondOrder() {
			return bondOrder;
		}
		
		/**
		 * Sets the bond order.
		 * 
		 * @param bondOrder
		 *            the new bond order
		 */
		public void setBondOrder(float bondOrder) {
			this.bondOrder = bondOrder;
		}
		
		/**
		 * Gets the bond type.
		 * 
		 * @return the bond type
		 */
		public BondType getBondType() {
			return bondType;
		}
		
		/**
		 * Gets the length.
		 * 
		 * @return the length
		 */
		public double getLength()
		{
			return atom1.Position().Sub(atom2.Position()).Length();
		}


		/**
		 * Sets the bond type.
		 * 
		 * @param bondType
		 *            the new bond type
		 */
		public void setBondType(BondType bondType) {
			this.bondType = bondType;
		}


		/**
		 * Gets the boundary fraction.
		 * 
		 * @return the boundary fraction
		 */
		public float getBoundaryFraction() {
			return boundaryFraction;
		}
		
		/**
		 * Sets the boundary fraction.
		 * 
		 * @param boundaryFraction
		 *            the new boundary fraction
		 */
		public void setBoundaryFraction(float boundaryFraction) {
			this.boundaryFraction = boundaryFraction;
		}
		
		/**
		 * Gets the atom.
		 * 
		 * @param a
		 *            the a
		 * @return the atom
		 */
		public Atom getAtom(Atom a)
		{
			if (atom1==a) return atom2;
			return atom1;
		}

		/** The property descriptor. */
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
		
		/** The access. */
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
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(java.lang.String)
		 */
		public Object getProperty(String name) {
			return access.getProperty(name);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(java.lang.String, java.lang.Object)
		 */
		public void setProperty(String name, Object value) {
			access.setProperty(name, value);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(int)
		 */
		public Object getProperty(int index) {
			return access.getProperty(index);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(int, java.lang.Object)
		 */
		public void setProperty(int index, Object value) {
			access.setProperty(index, value);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor)
		 */
		public Object getProperty(IPropertyDescriptor desc) {
			return access.getProperty(desc);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#setProperty(com.bobandthomas.Morbid.utils.IPropertyDescriptor, java.lang.Object)
		 */
		public void setProperty(IPropertyDescriptor desc, Object value) {
			access.setProperty(desc, value);
		}


		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IPropertyAccessor#getDescriptors()
		 */
		public IPropertyDescriptorList getDescriptors() {
			return access.getDescriptors();
		}
		

		// }}
		

}
