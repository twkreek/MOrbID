package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

/**
 * @author Thomas Kreek
 *  Substructure is the base collection of atoms. properties of the sets, including bounds,
 *  charge ranges, defulat color, and a default fragment representation of this substucture
 *
 */

public class Substructure extends CLoadableSet<Atom> implements ISubstructure {
		private Fragment fragment;
		protected ColorQuad listColor;
		BoxType bounds;
		MinMax chargeRange;
		boolean m_bDirtyCharges;
		boolean m_bHasCharges;
 		Substructure()
		{
			m_bHasCharges = false;
			bounds = new BoxType();
			chargeRange = new MinMax();
			listColor = StaticColorQuad.LiteGray.cq();
			setReParent(false);
		}
 		@Override
		public boolean add(Atom a)
		{

			super.add(a);
			double r = a.Radius();
			bounds.CalcBounds(a.Position(), r);
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
			bounds.resetBounds();
			for (Atom at : this)
			{
				chargeRange.addValue(at.getCharge());
				double r = at.Radius();
				bounds.CalcBounds(at.Position(), r);
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
		public BoxType GetBounds() {return bounds; }
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
		int NumAtoms() { return this.size(); }


}
