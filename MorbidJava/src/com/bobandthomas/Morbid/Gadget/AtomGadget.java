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

import com.bobandthomas.Morbid.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.SubstructureRep;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.Point3D;


/**
 * The Class AtomGadget
 * generates a visual representation of a single atom.
 *
 * @author Thomas Kreek
 */
@Path(value = "/AtomGadget")
public class AtomGadget extends Gadget {


	/**
	 * The Enum AtomColorBy.
	 *
	 * @author Thomas Kreek
	 */
	public enum AtomColorBy {

		/** The Atom color charge. */
		AtomColorCharge(1),
		/** The Atom color monochrome. */
		AtomColorMonochrome(2),
		/** The Atom color substructure. */
		AtomColorSubstructure(3),
		/** The Atom color type. */
		AtomColorType(0);

		/** The values. */
		private static AtomColorBy[] values = null;

		/**
		 * From int.
		 *
		 * @param i
		 *            the i
		 * @return the atom color by
		 */
		public static AtomColorBy fromInt(int i) {
			if (AtomColorBy.values == null) {
				AtomColorBy.values = AtomColorBy.values();
			}
			return AtomColorBy.values[i];
		}

		/**
		 * Instantiates a new atom color by.
		 *
		 * @param i
		 *            the i
		 */
		AtomColorBy(int i) {

		}
	}

	/**
	 * The Enum AtomRepType.
	 *
	 * @author Thomas Kreek
	 */
	public enum AtomRepType {
		/** The Atom frames. */
		AtomFrames(3),
		/*AtomDots(0), */ /** The Atom labels only. */
		AtomLabelsOnly(1),
		/** The Atom spheres. */
		AtomSpheres(2);

		/** The values. */
		private static AtomRepType[] values = null;

		/**
		 * From int.
		 *
		 * @param i
		 *            the i
		 * @return the atom rep type
		 */
		public static AtomRepType fromInt(int i) {
			if (AtomRepType.values == null) {
				AtomRepType.values = AtomRepType.values();
			}
			return AtomRepType.values[i];
		}

		/**
		 * Instantiates a new atom rep type.
		 *
		 * @param i
		 *            the i
		 */
		AtomRepType(int i) {

		}
	}

	/** The Atom scale. */
	private double AtomScale;;

	/** The Color by. */
	private AtomColorBy ColorBy;;

	/** The Labels. */
	private boolean showLabels;

	/** The lod. */
	private double LOD = 1.0;

	/** The Numbers. */
	private boolean showNumbers;

	/** The Rep. */
	private AtomRepType Rep;

	/** The Show charges. */
	private boolean ShowCharges;

	/** The Show hydrogens. */
	private boolean ShowHydrogens;

	/** The Show lone pairs. */
	private boolean ShowLonePairs;

	/** The Show metals. */
	private boolean ShowMetals;

	/** The Show organics. */
	private boolean ShowOrganics;

	/** The Size by charge. */
	private boolean SizeByCharge;

	/**
	 * Instantiates a new atom gadget.
	 */
	public AtomGadget() {
		super();
		layer = LayerPosition.LayerModel;
		AtomScale = 0.7;
		showLabels = false;
		showNumbers = false;
		ShowCharges = false;
		ShowMetals = true;
		ShowOrganics = true;
		Rep = AtomRepType.AtomSpheres;
		ColorBy = AtomColorBy.AtomColorType;
	}

	/**
	 * Gets the atom scale.
	 *
	 * @return the atom scale
	 */
	public double getAtomScale() {
		return AtomScale;
	}

	/**
	 * Gets the color by.
	 *
	 * @return the color by
	 */
	public AtomColorBy getColorBy() {
		return ColorBy;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#getGadgetType()
	 */
	@Override
	public String getGadgetType() {
		return "Atom Gadget";
	}

	/**
	 * Gets the rep.
	 *
	 * @return the rep
	 */
	public AtomRepType getRep() {
		return Rep;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableItem#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		if (source.getSource().getClass().equals(Atom.class))
		{
			markDirty();
		}
		return super.handleNotify(source);
	}

	/**
	 * Checks if is labels.
	 *
	 * @return true, if is labels
	 */
	public boolean isShowLabels() {
		return showLabels;
	}

	/**
	 * Checks if is show charges.
	 *
	 * @return true, if is show charges
	 */
	public boolean isShowCharges() {
		return ShowCharges;
	}

	/**
	 * Checks if is show hydrogens.
	 *
	 * @return true, if is show hydrogens
	 */
	public boolean isShowHydrogens() {
		return ShowHydrogens;
	}

	/**
	 * Checks if is show metals.
	 *
	 * @return true, if is show metals
	 */
	public boolean isShowMetals() {
		return ShowMetals;
	}

	/**
	 * Sets the atom scale.
	 *
	 * @param atomScale
	 *            the new atom scale
	 */
	public void setAtomScale(double atomScale) {
		AtomScale = atomScale;
		markDirty(new MorbidEvent(this, "AtomScale"));
	}

	/**
	 * Sets the color by.
	 *
	 * @param colorBy
	 *            the new color by
	 */
	public void setColorBy(AtomColorBy colorBy) {
		ColorBy = colorBy;
		markDirty(new MorbidEvent(this, "ColorBy"));
	}

	/**
	 * Sets the labels.
	 *
	 * @param labels
	 *            the new labels
	 */
	public void setShowLabels(boolean labels) {
		showLabels = labels;
		markDirty(new MorbidEvent(this,"ShowLabels"));
	}

	/**
	 * Sets the rep.
	 *
	 * @param rep
	 *            the new rep
	 */
	public void setRep(AtomRepType rep) {
		Rep = rep;
		markDirty(new MorbidEvent(this,"Rep"));
	}

	/**
	 * Sets the show charges.
	 *
	 * @param showCharges
	 *            the new show charges
	 */
	public void setShowCharges(boolean showCharges) {
		ShowCharges = showCharges;
		markDirty();
	}

	/**
	 * Sets the show hydrogens.
	 *
	 * @param showHydrogens
	 *            the new show hydrogens
	 */
	public void setShowHydrogens(boolean showHydrogens) {
		ShowHydrogens = showHydrogens;
		markDirty();
	}

	/**
	 * Sets the show metals.
	 *
	 * @param showMetals
	 *            the new show metals
	 */
	public void setShowMetals(boolean showMetals) {
		ShowMetals = showMetals;
	}



	/**
	 * @return the lOD
	 */
	public double getLOD() {
		return LOD;
	}

	/**
	 * @param lOD the lOD to set
	 */
	public void setLOD(double lOD) {
		LOD = lOD;
	}

	/**
	 * @return the showNumbers
	 */
	public boolean isShowNumbers() {
		return showNumbers;
	}

	/**
	 * @param showNumbers the showNumbers to set
	 */
	public void setShowNumbers(boolean showNumbers) {
		this.showNumbers = showNumbers;
	}

	/**
	 * @return the showLonePairs
	 */
	public boolean isShowLonePairs() {
		return ShowLonePairs;
	}

	/**
	 * @param showLonePairs the showLonePairs to set
	 */
	public void setShowLonePairs(boolean showLonePairs) {
		ShowLonePairs = showLonePairs;
	}

	/**
	 * @return the showOrganics
	 */
	public boolean isShowOrganics() {
		return ShowOrganics;
	}

	/**
	 * @param showOrganics the showOrganics to set
	 */
	public void setShowOrganics(boolean showOrganics) {
		ShowOrganics = showOrganics;
	}

	/**
	 * @return the sizeByCharge
	 */
	public boolean isSizeByCharge() {
		return SizeByCharge;
	}

	/**
	 * @param sizeByCharge the sizeByCharge to set
	 */
	public void setSizeByCharge(boolean sizeByCharge) {
		SizeByCharge = sizeByCharge;
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

		LOD = Math.log(mol.NumAtoms());


		if (isSubstructureFilter() && substructureFilterList != null)
		{
			for (SubstructureRep al: substructureFilterList)
			{
				if (al.isVisible())
				{
					for (Atom a: al.getSubstructure()) {
						renderAtom(a,gobList);
					}
				}
			}

		}
		else
		{
			for (i = 0; i < mol.NumAtoms(); i++) {
				Atom a = mol.GetAtom(i);
				renderAtom(a,gobList);
			}
		}

	}

	/**
	 * Draw box.
	 *
	 * @param pvg
	 *            the pvg
	 * @param aPosition
	 *            the a position
	 * @param sr
	 *            the sr
	 */
	void drawBox(GobPoly pvg, Point3D aPosition, double sr) {
		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y - sr,
				aPosition.z + sr));

		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y - sr,
				aPosition.z - sr));
		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y - sr,
				aPosition.z - sr));
		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y - sr,
				aPosition.z + sr));

		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y - sr,
				aPosition.z + sr));
		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y + sr,
				aPosition.z + sr));
		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y + sr,
				aPosition.z + sr));

		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y - sr,
				aPosition.z + sr));
		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y + sr,
				aPosition.z + sr));
		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y + sr,
				aPosition.z - sr));

		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y - sr,
				aPosition.z - sr));
		pvg.AddPoint(new Point3D(aPosition.x + sr, aPosition.y + sr,
				aPosition.z - sr));
		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y + sr,
				aPosition.z - sr));

		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y - sr,
				aPosition.z - sr));
		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y + sr,
				aPosition.z - sr));
		pvg.AddPoint(new Point3D(aPosition.x - sr, aPosition.y + sr,
				aPosition.z + sr));

	}

	/**
	 * Gets the atom label.
	 *
	 * @param a
	 *            the a
	 * @return the atom label
	 */
	String getAtomLabel(Atom a) {
		String label = "";
		if (showLabels) {
			label += a.getName();
		}
		if (showNumbers) {
			String s = new String();
			s = String.format("G", a.getID());
			label += s;
		}
		if (ShowCharges) {
			String s = new String();
			s = String.format("#.###", a.getCharge());
			label += s;
		}
		return label;
	}

	/**
	 * Gets the atom radius.
	 *
	 * @param at
	 *            the at
	 * @return the atom radius
	 */
	double getAtomRadius(Atom at) {
		double sr = at.Radius() * AtomScale;
		if (SizeByCharge) {
			double fraction;
			fraction = Math.abs(at.getCharge())
					/ Math.abs(GetMolecule().Atoms().MaxCharge()
							- GetMolecule().Atoms().MinCharge()) / 2;
			sr *= fraction;
		}
		return sr;

	}


	/**
	 * Gets the i atom scale.
	 *
	 * @return the i atom scale
	 */
	int getIAtomScale() {
		return (int) (AtomScale * 100);
	}


	/**
	 * Checks if is visible.
	 *
	 * @param a
	 *            the a
	 * @return true, if is visible
	 */
	boolean isVisible(Atom a)
	{
		if (!ShowHydrogens && a.getAtomicNumber() == 1)
			return false;
		if (!ShowLonePairs && a.getAtomicNumber() == 0)
			return false;
		if (!ShowOrganics && a.getAtomicNumber() < 15)
			return false;
		if (!ShowMetals && a.getAtomicNumber() >= 15)
			return false;
		return true;

	}

	/**
	 * Render atom.
	 *
	 * @param a
	 *            the a
	 * @param gobList
	 *            the gob list
	 */
	void renderAtom(Atom a, GobList gobList)
	{
		if (!isVisible(a))
			return;

		ColorQuad theColor;
		String label;
		mat = null;

		Point3D aPosition = new Point3D(a.Position());
		double sr = getAtomRadius(a);
		theColor = getAtomColor(a);
		label = getAtomLabel(a);


		switch (Rep) {
		case AtomSpheres: {
			SphereGob sg = new SphereGob(aPosition, sr);
			sg.setName(a.getName());
			if (showLabels || showNumbers || ShowCharges) {
				sg.setName(label);
			}
			sg.setLOD(LOD);
			sg.setMaterial(mat);
			sg.setColor(theColor);
			gobList.add(sg);

		}
		break;
		case AtomFrames:
			GobPoly pvg = new GobPoly();
			pvg.SetPolyType(GobPolyType.Closed);
			drawBox(pvg, aPosition, sr);
			pvg.setColor(theColor);
			gobList.add(pvg);

			break;
		default:
			break;
		}
		if ((showLabels || showNumbers || ShowCharges)
				) {
			StringGob lbg = new StringGob(label, a.Position());
			lbg.setMaterial(baseMaterial);
			lbg.setColor(theColor.Inverse());
			gobList.add(lbg);
		}
		if (a.isSelected())
		{
			SphereGob sg = new SphereGob(aPosition, sr+0.2);
			sg.setColor(theColor.Inverse());
			sg.getColor().setAlpha(0.8);
			gobList.add(sg);

		}
	}


	/**
	 * Sets the i atom scale.
	 *
	 * @param v
	 *            the new i atom scale
	 */
	void setIAtomScale(int v) {
		AtomScale = v / 100.0f;
	}

}
