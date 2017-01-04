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
package com.bobandthomas.Morbid.Gadget;

import javax.ws.rs.Path;

import com.bobandthomas.Morbid.Scene;
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


/**
 * The Class BondGadget.
 *
 * @author Thomas Kreek
 */
@Path("/BondGadget")
public class BondGadget extends Gadget {


	/**
	 * The Enum BondColor.
	 *
	 * @author Thomas Kreek
	 */
	public enum BondColor {

		/** The Charge gradient. */
		ChargeGradient,
		/** The Color atom type. */
		ColorAtomType,
		/** The Color bond order. */
		ColorBondOrder;

		/** The values. */
		private static BondColor[] values = null;

		/**
		 * From int.
		 *
		 * @param i
		 *            the i
		 * @return the bond color
		 */
		public static BondColor fromInt(int i) {
			if(BondColor.values == null) {
				BondColor.values = BondColor.values();
			}
			return BondColor.values[i];
		}
	}

	/**
	 * The Enum BondRep.
	 *
	 * @author Thomas Kreek
	 */
	public enum BondRep {

		/** The Cylinder. */
		Cylinder,
		/** The Frames. */
		Frames,
		/** The Lines. */
		Lines;

		/** The values. */
		private static BondRep[] values = null;

		/**
		 * From int.
		 *
		 * @param i
		 *            the i
		 * @return the bond rep
		 */
		public static BondRep fromInt(int i) {
			if(BondRep.values == null) {
				BondRep.values = BondRep.values();
			}
			return BondRep.values[i];
		}
	};

	/** The show hydrogens. */
	private boolean showHydrogens = true;;

	/** The bond scale. */
	private double bondScale;

	/** The color by. */
	private BondColor colorBy;

	/** The i bond scale. */
	private int iBondScale;

	/** The label bo. */
	private boolean labelBO;

	/** The label distance. */
	private boolean labelDistance;

	/** The rep. */
	private BondRep rep;

	/** The show lone pairs. */
	private boolean showLonePairs = true;

	/**
	 * Instantiates a new bond gadget.
	 */
	public BondGadget() {
		super();
		bondScale = 0.05f;
		rep = BondRep.Cylinder;
		colorBy = BondColor.ColorAtomType;
		labelBO = false;
		labelDistance = false;
	}

	/**
	 * Draw bond.
	 *
	 * @param gl
	 *            the gl
	 * @param bond
	 *            the bond
	 */
	public void drawBond(GobList gl, Bond bond)
	{

	}

	/**
	 * Gets the bond colors.
	 *
	 * @param bond
	 *            the bond
	 * @return the bond colors
	 */
	public ColorQuad[] getBondColors(Bond bond)
	{
		ColorQuad[] colors = new ColorQuad[2];
		colors[0] = getAtomColor(bond.atom(0));
		colors[1] = getAtomColor(bond.atom(1));
		return colors;
	}

	/**
	 * Gets the bond scale.
	 *
	 * @return the bond scale
	 */
	public double getBondScale() {
		return bondScale;
	}

	/**
	 * Gets the color by.
	 *
	 * @return the color by
	 */
	public BondColor getColorBy() {
		return colorBy;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#getGadgetType()
	 */
	@Override
	public String getGadgetType() {
		return "Bond Gadget";
	}

	/**
	 * Gets the rep.
	 *
	 * @return the rep
	 */
	public BondRep getRep() {
		return rep;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableItem#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		if (source.isType(AtomGadget.class))
		{
			AtomGadget pGadget;
			pGadget = (AtomGadget) source.getSource();
			setShowHydrogens(pGadget.isShowHydrogens());
			if (pGadget.isShowLonePairs() == false) {
				showLonePairs = false;
			}

		}
		return super.handleNotify(source);


	}

	/**
	 * Checks if is label bo.
	 *
	 * @return true, if is label bo
	 */
	public boolean isLabelBO() {
		return labelBO;
	}

	/**
	 * @return the iBondScale
	 */
	public int getiBondScale() {
		return iBondScale;
	}

	/**
	 * @param iBondScale the iBondScale to set
	 */
	public void setiBondScale(int iBondScale) {
		this.iBondScale = iBondScale;
	}

	/**
	 * @return the showLonePairs
	 */
	public boolean isShowLonePairs() {
		return showLonePairs;
	}

	/**
	 * @param showLonePairs the showLonePairs to set
	 */
	public void setShowLonePairs(boolean showLonePairs) {
		this.showLonePairs = showLonePairs;
	}

	/**
	 * Checks if is label distance.
	 *
	 * @return true, if is label distance
	 */
	public boolean isLabelDistance() {
		return labelDistance;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#sceneChanged(com.bobandthomas.Morbid.Gadget.Scene)
	 */
	@Override
	public void sceneChanged(Scene s) {
		super.sceneChanged(s);

		for (Gadget g : s.getGadgetList()) {
			if (g.isType(AtomGadget.class))
			{
				g.registerListener(this);
				if (g.isVisible()) {
					AtomGadget pGadget;
					pGadget = (AtomGadget) g;
					if (pGadget.isShowHydrogens() == false) {
						setShowHydrogens(false);
					}
					if (pGadget.isShowLonePairs() == false) {
						showLonePairs = false;
					}
				}
			}
		}
	}



	/**
	 * Sets the bond scale.
	 *
	 * @param d
	 *            the new bond scale
	 */
	public void setBondScale(double d) {
		bondScale = d;
		markDirty();
	}

	/**
	 * Sets the color by.
	 *
	 * @param colorBy
	 *            the new color by
	 */
	public void setColorBy(BondColor colorBy) {
		if (this.colorBy != colorBy)
		{
			this.colorBy = colorBy;
			markDirty();
		}
	}

	/**
	 * Sets the label bo.
	 *
	 * @param labelBO
	 *            the new label bo
	 */
	public void setLabelBO(boolean labelBO) {
		this.labelBO = labelBO;
		markDirty();
	}

	/**
	 * Sets the label distance.
	 *
	 * @param labelDistance
	 *            the new label distance
	 */
	public void setLabelDistance(boolean labelDistance) {
		this.labelDistance = labelDistance;
		markDirty();
	}

	/**
	 * Sets the rep.
	 *
	 * @param rep
	 *            the new rep
	 */
	public void setRep(BondRep rep) {
		if (this.rep != rep)
		{
			this.rep = rep;
			markDirty();
		}
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#setScene(com.bobandthomas.Morbid.Gadget.Scene)
	 */
	@Override
	public void setScene(Scene s) {
		super.setScene(s);
		sceneChanged(s);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#Draw(com.bobandthomas.Morbid.graphics.GobList)
	 */
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
					&& (a1.isA(Element.H)|| a2.isA(Element.H))) {
				continue;
			}
			if (!showLonePairs
					&& (a1.isA(Element.LP)|| a2.isA(Element.LP))) {
				continue;
			}

			if (!isAtomVisible(a1) || !isAtomVisible(a2)) {
				continue;
			}




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
			} else {
				continue;
			}


			AtomType ati = a1.getAtomType();
			AtomType atj = a2.getAtomType();

			if (rep == BondRep.Cylinder) {
				CylinderGob cg = new CylinderGob(ai, midPoint,
						(float) bondScale);
				cg.setColor(ati.color);
				cg.setMaterial(ati.mat);
				if (bondOrder == 2) {
					cg.SetRadius(cg.getRadius() * 2);
				}
				gobList.add(cg);

				cg = new CylinderGob(aj, midPoint, (float)bondScale);
				cg.setColor(atj.color);
				cg.setMaterial(atj.mat);
				if (bondOrder == 2) {
					cg.SetRadius(cg.getRadius() * 2);
				}
				gobList.add(cg);

			} else {
				GobVector vg = new GobVector(ai, midPoint);
				vg.setColor(ati.color);
				gobList.add(vg);

				vg = new GobVector(aj, midPoint);
				vg.setColor(atj.color);
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
					if (str.length() > 0) {
						str += ", ";
					}
					str += s;
				}
				StringGob sg = new StringGob(str, midPoint);
				sg.setColor(baseColor);
				gobList.add(sg);
			}
		}
		markClean();
	}

	/**
	 * Checks if is show hydrogens.
	 *
	 * @return true, if is show hydrogens
	 */
	boolean isShowHydrogens() {
		return showHydrogens;
	}

	/**
	 * Sets the show hydrogens.
	 *
	 * @param showHydrogens
	 *            the new show hydrogens
	 */
	void setShowHydrogens(boolean showHydrogens) {
		if (this.showHydrogens != showHydrogens)
		{
			this.showHydrogens = showHydrogens;
			markDirty();
		}
	}

}