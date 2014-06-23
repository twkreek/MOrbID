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

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.Gadget.control.AtomGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
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
public class AtomGadget extends Gadget {

	/**
	 * A factory for creating AtomGadget objects.
	 */
	public class AtomGadgetFactory extends GadgetFactory {

		/**
		 * Instantiates a new atom gadget factory.
		 */
		public AtomGadgetFactory() {
			setName("Atom Gadget");
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#canCreate(com.bobandthomas.Morbid.molecule.Molecule)
		 */
		public boolean canCreate(Molecule m) {
			return (m.Atoms().size() > 0);
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#createGadget()
		 */
		public Gadget createGadget() {
			return new AtomGadget();
		}

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.Gadget.GadgetFactory#createPanel(com.bobandthomas.Morbid.Gadget.Gadget)
		 */
		public GadgetPanel createPanel(Gadget g) {
			return new AtomGadgetPanel((AtomGadget) g);
		}

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

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#getGadgetType()
	 */
	@Override
	public String getGadgetType() {
		return "Atom Gadget";
	}

	/**
	 * The Enum AtomColorBy.
	 * 
	 * @author Thomas Kreek
	 */
	public enum AtomColorBy {
		
		/** The Atom color type. */
		AtomColorType(0), 
 /** The Atom color charge. */
 AtomColorCharge(1), 
 /** The Atom color monochrome. */
 AtomColorMonochrome(2),
/** The Atom color substructure. */
AtomColorSubstructure(3);
		
		/**
		 * Instantiates a new atom color by.
		 * 
		 * @param i
		 *            the i
		 */
		AtomColorBy(int i) {

		}

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
	};

	/**
	 * The Enum AtomRepType.
	 * 
	 * @author Thomas Kreek
	 */
	public enum AtomRepType {
		/*AtomDots(0), */ /** The Atom labels only. */
		AtomLabelsOnly(1), 
 /** The Atom spheres. */
 AtomSpheres(2), 
 /** The Atom frames. */
 AtomFrames(3);
		
		/**
		 * Instantiates a new atom rep type.
		 * 
		 * @param i
		 *            the i
		 */
		AtomRepType(int i) {

		}

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
	};

	/** The Atom scale. */
	double AtomScale;

	/** The Color by. */
	AtomColorBy ColorBy;
	
	/** The Labels. */
	boolean Labels;
	
	/** The Numbers. */
	boolean Numbers;
	
	/** The Rep. */
	AtomRepType Rep;
	
	/** The Show charges. */
	boolean ShowCharges;
	
	/** The Show hydrogens. */
	boolean ShowHydrogens;
	
	/** The Show lone pairs. */
	boolean ShowLonePairs;
	
	/** The Show metals. */
	boolean ShowMetals;
	
	/** The Show organics. */
	boolean ShowOrganics;
	
	/** The Size by charge. */
	boolean SizeByCharge;
	
	/** The lod. */
	double LOD = 1.0;

	/**
	 * Instantiates a new atom gadget.
	 */
	public AtomGadget() {
		super();
		layer = LayerPosition.LayerModel;
		AtomScale = 0.7;
		Labels = false;
		Numbers = false;
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
	 * Gets the color by.
	 * 
	 * @return the color by
	 */
	public AtomColorBy getColorBy() {
		return ColorBy;
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
	 * Checks if is labels.
	 * 
	 * @return true, if is labels
	 */
	public boolean isLabels() {
		return Labels;
	}

	/**
	 * Sets the labels.
	 * 
	 * @param labels
	 *            the new labels
	 */
	public void setLabels(boolean labels) {
		Labels = labels;
		markDirty(new MorbidEvent(this,"ShowLabels"));
	}

	/**
	 * Gets the rep.
	 * 
	 * @return the rep
	 */
	public AtomRepType getRep() {
		return Rep;
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
	 * Checks if is show metals.
	 * 
	 * @return true, if is show metals
	 */
	public boolean isShowMetals() {
		return ShowMetals;
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
	 * Checks if is show charges.
	 * 
	 * @return true, if is show charges
	 */
	public boolean isShowCharges() {
		return ShowCharges;
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
	 * Checks if is show hydrogens.
	 * 
	 * @return true, if is show hydrogens
	 */
	public boolean isShowHydrogens() {
		return ShowHydrogens;
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
	 * Gets the atom label.
	 * 
	 * @param a
	 *            the a
	 * @return the atom label
	 */
	String getAtomLabel(Atom a) {
		String label = "";
		if (Labels) {
			label += a.getName();
		}
		if (Numbers) {
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
		ColorQuad theColor;
		String label;
		mat = null;
		if (!isVisible(a))
			return;

		Point3D aPosition = new Point3D(a.Position());
		double sr = getAtomRadius(a);
		theColor = getAtomColor(a);
		label = getAtomLabel(a);
		
		
		switch (Rep) {
		case AtomSpheres: {
			SphereGob sg = new SphereGob(aPosition, sr);
			sg.setName(a.getName());
			if (Labels || Numbers || ShowCharges) {
				sg.setName(label);
			}
			sg.setColor(theColor);
			sg.setLOD(LOD);
			sg.setMaterial(mat);
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
		if ((Labels || Numbers || ShowCharges)
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
			for (SubstructureRep al: this.substructureFilterList)
			{
				if (al.isVisible())
				{
					for (Atom a: al.getSubstructure())
						renderAtom(a,gobList);
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
	 * Gets the i atom scale.
	 * 
	 * @return the i atom scale
	 */
	int getIAtomScale() {
		return (int) (AtomScale * 100);
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
