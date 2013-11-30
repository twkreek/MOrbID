package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.CylinderGob;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobVector;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.Bond;
import com.bobandthomas.Morbid.molecule.Element;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

public class BondGadget extends Gadget {


	@Override
	public String getGadgetType() {
		return "Bond Gadget";
	}

	public enum BondColor {
		ColorAtomType, ColorBondOrder, ChargeGradient;
	    private static BondColor[] values = null;
	    public static BondColor fromInt(int i) {
	        if(BondColor.values == null) {
	        	BondColor.values = BondColor.values();
	        }
	        return BondColor.values[i];
	    }
};

	public enum BondRep {
		Lines, Cylinder, Frames;
	    private static BondRep[] values = null;
	    public static BondRep fromInt(int i) {
	        if(BondRep.values == null) {
	        	BondRep.values = BondRep.values();
	        }
	        return BondRep.values[i];
	    }
};

	double bondScale;
	BondColor colorBy;
	BondRep rep;
	boolean labelBO;
	boolean labelDistance;
	private boolean showHydrogens = true;
	boolean showLonePairs = true;

	int iBondScale;
	
	public double getBondScale() {
		return bondScale;
	}

	public void setBondScale(double d) {
		this.bondScale = d;
		markDirty();
	}

	public BondColor getColorBy() {
		return colorBy;
	}

	public void setColorBy(BondColor colorBy) {
		if (this.colorBy != colorBy)
		{
			this.colorBy = colorBy;
			markDirty();
		}
	}

	public BondRep getRep() {
		return rep;
	}

	public void setRep(BondRep rep) {
		if (this.rep != rep)
		{
			this.rep = rep;
			markDirty();
		}
	}

	public boolean isLabelBO() {
		return labelBO;
	}

	public void setLabelBO(boolean labelBO) {
		this.labelBO = labelBO;
		markDirty();
	}

	public boolean isLabelDistance() {
		return labelDistance;
	}

	public void setLabelDistance(boolean labelDistance) {
		this.labelDistance = labelDistance;
		markDirty();
	}



	boolean isShowHydrogens() {
		return showHydrogens;
	}

	void setShowHydrogens(boolean showHydrogens) {
		if (this.showHydrogens != showHydrogens)
		{
			this.showHydrogens = showHydrogens;
			markDirty();
		}
	}

	public BondGadget() {
		super();
		bondScale = 0.05f;
		rep = BondRep.Cylinder;
		colorBy = BondColor.ColorAtomType;
		labelBO = false;
		labelDistance = false;
	}

	@Override
	public void sceneChanged(Scene s) {
		super.sceneChanged(s);

		for (Gadget g : s.gadgetList) {
			if (g.isType(AtomGadget.class))
			{
				g.registerListener(this);
				if (g.isVisible()) {
					AtomGadget pGadget;
					pGadget = (AtomGadget) g;
					if (pGadget.ShowHydrogens == false)
						setShowHydrogens(false);
					if (pGadget.ShowLonePairs == false)
						showLonePairs = false;
				}
			}
		}
	}

	@Override
	public void sceneAdded(Scene s) {
		super.sceneAdded(s);
		sceneChanged(s);
	}
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		if (source.isType(AtomGadget.class))
		{
			AtomGadget pGadget;
			pGadget = (AtomGadget) source.getSource();
			this.setShowHydrogens(pGadget.ShowHydrogens);
			if (pGadget.ShowLonePairs == false)
				showLonePairs = false;
			
		}
		return super.handleNotify(source);
		
		
	}
	public void drawBond(GobList gl, Bond bond)
	{
		
	}
	
	public ColorQuad[] getBondColors(Bond bond)
	{
		ColorQuad[] colors = new ColorQuad[2];
		colors[0] = getAtomColor(bond.atom(0));
		colors[1] = getAtomColor(bond.atom(1));
		return colors;
	}

	@Override
	void Draw(GobList gobList) {
		int i;

		Molecule mol = GetMolecule();
		if (mol == null)
			return;
		if (!isDirty()) return;
		gobList.clear();

			double atomScale = 0.0f;

			atomScale *= 0.5;

			for (i = 0; i < mol.NumBonds(); i++) {
				double ri, rj, length;
				int bondOrder;
				Bond bond = mol.GetBond(i);
				Atom a1 = bond.atom(0);
				Atom a2 = bond.atom(1);

				if (!isShowHydrogens()
						&& (a1.isA(Element.H)|| a2.isA(Element.H)))
					continue;
				if (!showLonePairs
						&& (a1.isA(Element.LP)|| a2.isA(Element.LP)))
					continue;
				
				if (!isAtomVisible(a1) || !isAtomVisible(a2))
					continue;
				
				
				
				
				bondOrder = bond.getNominalBondOrder();
				Point3D ai = new Point3D(a1.Position());
				Point3D aj = new Point3D(a2.Position());
				Vector3D bondVector = ai.Sub(aj);
				Point3D midPoint = ai.midPoint(aj, 0.5);
				length = (float) bondVector.Length();
				ri = a1.Radius();
				rj = a2.Radius();
				if ((length - ((ri + rj) * atomScale)) > 0) {
					ai = ai.Sub(bondVector.Scale(ri * atomScale));
					aj = aj.Add(bondVector.Scale(rj * atomScale));
				} else
					continue;
				
				
				AtomType ati = a1.getAtomType();
				AtomType atj = a2.getAtomType();
				
				if (rep == BondRep.Cylinder) {
					CylinderGob cg = new CylinderGob(ai, midPoint,
							(float) bondScale);
					cg.Color = ati.color;
					cg.setMaterial(ati.mat);
					if (bondOrder == 2)
						cg.SetRadius(cg.getRadius() * 2);
					gobList.add(cg);

					cg = new CylinderGob(aj, midPoint, (float)bondScale);
					cg.Color = atj.color;
					cg.setMaterial(atj.mat);
					if (bondOrder == 2)
						cg.SetRadius(cg.getRadius() * 2);
					gobList.add(cg);

				} else {
					GobVector vg = new GobVector(ai, midPoint);
					vg.Color = ati.color;
					gobList.add(vg);

					vg = new GobVector(aj, midPoint);
					vg.Color = atj.color;
					gobList.add(vg);
				}
				if (labelBO || labelDistance) {
					String str = "";
					String s = "";
					if (labelBO) {

						s = String.valueOf(bond.getNominalBondOrder());
						str += s;
					}
					if (labelDistance) {
						s = String.valueOf(bondVector.Length());
						s= s.substring(0,4);
						if (str.length() > 0)
							str += ", ";
						str += s;
					}
					StringGob sg = new StringGob(str, midPoint);
					sg.Color = baseColor;
					gobList.add(sg);
				}
			}
		markClean();
	}

}