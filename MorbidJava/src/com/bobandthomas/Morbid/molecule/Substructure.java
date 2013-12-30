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

/**
 * @author Thomas Kreek
 *  Substructure is the base collection of atoms. properties of the sets, including bounds,
 *  charge ranges, defulat color, and a default fragment representation of this substucture
 *
 */

public class Substructure extends CLoadableTable<Atom> implements ISubstructure, IPropertyAccessor, IChangeNotifier {
		private Fragment fragment;
		protected ColorQuad listColor;
		BoundingBox bounds;
		MinMax chargeRange;
		boolean m_bDirtyCharges;
		boolean m_bHasCharges;
 		Substructure()
		{
			m_bHasCharges = false;
			bounds = new BoundingBox();
			chargeRange = new MinMax();
			listColor = StaticColorQuad.LiteGray.cq();
			setReParent(false);
		}
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
		public void balanceCharges()
		{
			double midCharge = chargeRange.center();
			for (Atom a: this)
			{
				a.setCharge(a.getCharge() - midCharge);
			}
		}
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
		public BoundingBox GetBounds() {return bounds; }
		public MinMax getChargeRange()
		{
			return chargeRange;
		}
		
		
		public ColorQuad getListColor() {
			return listColor;
		}

		public boolean HasCharges() { return m_bHasCharges; }

		public double MaxCharge (){ return chargeRange.max; }
		public double MinCharge() {  return chargeRange.min; }
		public void setListColor(ColorQuad listColor) {
			this.listColor = listColor;
		}
		protected Fragment getFragment() {
			return fragment;
		}
		protected void setFragment(Fragment fragment) {
			this.fragment = fragment;
		}
		
		/*
		 * Support Properties
		 */
		
		static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList(){

			@Override
			public void initialize() {
				addPropertyDescriptor(0, "Name", String.class, false);
				addPropertyDescriptor(1, "Number of Atoms", Integer.class, false);
				addPropertyDescriptor(2, "Color", ColorQuad.class, false);
				
			}

		};
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
