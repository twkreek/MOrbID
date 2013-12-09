package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.PropertyDescriptoList;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

/**
 * @author Thomas Kreek
 *  Substructure is the base collection of atoms. properties of the sets, including bounds,
 *  charge ranges, defulat color, and a default fragment representation of this substucture
 *
 */

public class Substructure extends CLoadableSet<Atom> implements ISubstructure, IPropertyAccessor {
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
		
		static IPropertyDescriptor propertyDescriptor = new PropertyDescriptoList<MoleculeProperty>(){

			@Override
			public void initialize() {
				add(0, "Name", String.class, false);
				add(1, "Number of Atoms", Integer.class, false);
				
			}

		};
		IPropertyAccessor access = new PropertyAccessor(propertyDescriptor){
			@Override
			public Object getProperty(int index) {
				switch (index){
				case 0: return  Substructure.this.getName();
				case 1: return Substructure.this.size();
				}
				return null;
			}
		
			@Override
			public void setProperty(int index, Object value) {
				switch (index){
				case 0:  Substructure.this.setName((String) value); return;
				}
				
			}
		};

		public Object getProperty(int index) {
			return access.getProperty(index);
		}

		public int getPropertyCount() {
			return access.getPropertyCount();
		}

		public void setProperty(int index, Object value) {
			access.setProperty(index, value);
		}

		public int getIndex(String name) {
			return access.getIndex(name);
		}

		public Class<?> getPropertyClass(int index) {
			return access.getPropertyClass(index);
		}

		public String getPropertyName(int index) {
			return access.getPropertyName(index);
		}

		public boolean isPropertyEditable(int index) {
			return access.isPropertyEditable(index);
		}

		public Object getProperty(String name) {
			return access.getProperty(name);
		}

		public void setProperty(String name, Object value) {
			access.setProperty(name, value);
		}

}
