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

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

// TODO: Auto-generated Javadoc
/**
 * The Class Substructure.
 * 
 * @author Thomas Kreek Substructure is the base collection of atoms. properties
 *         of the sets, including bounds, charge ranges, defulat color, and a
 *         default fragment representation of this substucture
 */

public class Substructure extends CLoadableTable<Atom> implements ISubstructure, IPropertyAccessor, IChangeNotifier {
		
		/** The fragment. */
		private Fragment fragment;
		
		/** The list color. */
		protected ColorQuad listColor;
		
		/** The bounds. */
		BoundingBox bounds;
		
		/** The charge range. */
		MinMax chargeRange;
		
		/** The m_b dirty charges. */
		boolean m_bDirtyCharges;
		
		/** The m_b has charges. */
		boolean m_bHasCharges;
 		
		 /**
			 * Instantiates a new substructure.
			 */
		 Substructure()
		{
			m_bHasCharges = false;
			bounds = new BoundingBox();
			chargeRange = new MinMax();
			listColor = StaticColorQuad.LiteGray.cq();
			setReParent(false);
		}
 		
		 /* (non-Javadoc)
		  * @see com.bobandthomas.Morbid.utils.CLoadableSet#add(com.bobandthomas.Morbid.utils.CLoadableItem)
		  */
		 @Override
		public boolean add(Atom a)
		{

			super.add(a);
			a.registerListener(this);
			double r = a.Radius();
			bounds.addSphere(a.Position(), r);
			chargeRange.addValue(a.getCharge());

			if (a.getCharge() != 0.0)
			{
				m_bHasCharges = true;
			}
			a.setID(this.size());
			return true;
		} 
		
		/**
		 * Balance charges.
		 */
		public void balanceCharges()
		{
			double midCharge = chargeRange.center();
			for (Atom a: this)
			{
				a.setCharge(a.getCharge() - midCharge);
			}
		}
		
		/**
		 * Calc bounds.
		 */
		public void CalcBounds()
		{
			chargeRange.reset();
			bounds.reset();
			for (Atom at : this)
			{
				chargeRange.addValue(at.getCharge());
				double r = at.Radius();
				bounds.addSphere(at.Position(), r);
			}
		}
		
		/**
		 * Center molecule.
		 */
		public void centerMolecule()
		{
			int i;
			CalcBounds();
			Point3D center = bounds.center();
			for (i=0; i < size(); i ++)
			{
				get(i).Position().sub(center);
			}
			bounds.min.sub(center);
			bounds.max.sub(center);
		}
		
		/**
		 * Gets the bounds.
		 * 
		 * @return the bounding box
		 */
		public BoundingBox GetBounds() {return bounds; }
		
		/**
		 * Gets the charge range.
		 * 
		 * @return the charge range
		 */
		public MinMax getChargeRange()
		{
			return chargeRange;
		}
		
		
		/**
		 * Gets the list color.
		 * 
		 * @return the list color
		 */
		public ColorQuad getListColor() {
			return listColor;
		}

		/**
		 * Checks if it has charges.
		 * 
		 * @return true, if successful
		 */
		public boolean HasCharges() { return m_bHasCharges; }

		/**
		 * Max charge.
		 * 
		 * @return the double
		 */
		public double MaxCharge (){ return chargeRange.max; }
		
		/**
		 * Min charge.
		 * 
		 * @return the double
		 */
		public double MinCharge() {  return chargeRange.min; }
		
		/**
		 * Sets the list color.
		 * 
		 * @param listColor
		 *            the new list color
		 */
		public void setListColor(ColorQuad listColor) {
			this.listColor = listColor;
		}
		
		/**
		 * Gets the fragment.
		 * 
		 * @return the fragment
		 */
		protected Fragment getFragment() {
			return fragment;
		}
		
		/**
		 * Sets the fragment.
		 * 
		 * @param fragment
		 *            the new fragment
		 */
		protected void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}
		
		/*
		 * Support Properties
		 */
		
		/** The property descriptor. */
		static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

			@Override
			public void initialize() {
				addPropertyDescriptor(0, "Name", String.class, false);
				addPropertyDescriptor(1, "Number of Atoms", Integer.class, false);
				addPropertyDescriptor(2, "Color", ColorQuad.class, false);
				
			}

		};
		
		/** The access. */
		IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor){
			@Override
			public Object getProperty(IPropertyDescriptor ipd) {
				switch (ipd.getIndex()){
				case 0: return  Substructure.this.getName();
				case 1: return Substructure.this.size();
				case 2: return Substructure.this.getListColor();
				}
				return null;
			}
		
			@Override
			public void setProperty(IPropertyDescriptor ipd, Object value) {
				switch (ipd.getIndex()){
				case 0:  Substructure.this.setName((String) value); return;
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
