package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;

public class AtomList extends CLoadableSet<Atom> {
		boolean m_bDirtyCharges;
		boolean m_bHasCharges;
		BoxType bounds;
		MinMax chargeRange;
 		int NumAtoms() { return this.size(); }
		public BoxType GetBounds() {return bounds; }
		public boolean HasCharges() { return m_bHasCharges; }
		public MinMax getChargeRange()
		{
			return chargeRange;
		}
		public double MinCharge() {  return chargeRange.min; }
		public double MaxCharge (){ return chargeRange.max; }
		public void balanceCharges()
		{
			double midCharge = chargeRange.center();
			for (Atom a: this)
			{
				a.setCharge(a.getCharge() - midCharge);
			}
		}
		
		
		AtomList()
		{
			m_bHasCharges = false;
			bounds = new BoxType();
			chargeRange = new MinMax();
			
		}

		public int AddAtom(Atom a)
		{

			double r = a.Radius();
			bounds.CalcBounds(a.Position(), r);
			chargeRange.addValue(a.getCharge());

			if (a.getCharge() != 0.0)
			{
				m_bHasCharges = true;
			}
			a.setID(this.size());
			add(a);
			return (int) a.getID();
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

}
