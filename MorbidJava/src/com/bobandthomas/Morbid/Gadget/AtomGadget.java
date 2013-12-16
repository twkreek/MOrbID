package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.SubstructureRep;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;

public class AtomGadget extends Gadget {

	@Override
	public String getGadgetType() {
		return "Atom Gadget";
	}

	public enum AtomColorBy {
		AtomColorType(0), AtomColorCharge(1), AtomColorMonochrome(2),AtomColorSubstructure(3);
		AtomColorBy(int i) {

		}

		private static AtomColorBy[] values = null;

		public static AtomColorBy fromInt(int i) {
			if (AtomColorBy.values == null) {
				AtomColorBy.values = AtomColorBy.values();
			}
			return AtomColorBy.values[i];
		}
	};

	public enum AtomRepType {
		/*AtomDots(0), */ AtomLabelsOnly(1), AtomSpheres(2), AtomFrames(3);
		AtomRepType(int i) {

		}

		private static AtomRepType[] values = null;

		public static AtomRepType fromInt(int i) {
			if (AtomRepType.values == null) {
				AtomRepType.values = AtomRepType.values();
			}
			return AtomRepType.values[i];
		}
	};

	double AtomScale;

	AtomColorBy ColorBy;
	boolean Labels;
	boolean Numbers;
	AtomRepType Rep;
	boolean ShowCharges;
	boolean ShowHydrogens;
	boolean ShowLonePairs;
	boolean ShowMetals;
	boolean ShowOrganics;
	boolean SizeByCharge;
	double LOD = 1.0;

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

	public double getAtomScale() {
		return AtomScale;
	}

	public void setAtomScale(double atomScale) {
		AtomScale = atomScale;
		markDirty();
	}

	public AtomColorBy getColorBy() {
		return ColorBy;
	}

	public void setColorBy(AtomColorBy colorBy) {
		ColorBy = colorBy;
		markDirty();
	}

	public boolean isLabels() {
		return Labels;
	}

	public void setLabels(boolean labels) {
		Labels = labels;
		markDirty();
	}

	public AtomRepType getRep() {
		return Rep;
	}

	public void setRep(AtomRepType rep) {
		Rep = rep;
		markDirty();
	}

	public boolean isShowMetals() {
		return ShowMetals;
	}

	public void setShowMetals(boolean showMetals) {
		ShowMetals = showMetals;
	}

	public boolean isShowCharges() {
		return ShowCharges;
	}

	public void setShowCharges(boolean showCharges) {
		ShowCharges = showCharges;
		markDirty();
	}

	public boolean isShowHydrogens() {
		return ShowHydrogens;
	}

	public void setShowHydrogens(boolean showHydrogens) {
		ShowHydrogens = showHydrogens;
		markDirty();
	}



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
			sg.Color = theColor;
			sg.setLOD(LOD);
			sg.setMaterial(mat);
			gobList.add(sg);

		}
		break;
		case AtomFrames:
			GobPoly pvg = new GobPoly();
			pvg.SetPolyType(GobPolyType.Closed);
			drawBox(pvg, aPosition, sr);
			pvg.Color = theColor;
			gobList.add(pvg);

			break;
		default:
			break;
		}
		if ((Labels || Numbers || ShowCharges)
				) {
			StringGob lbg = new StringGob(label, a.Position());
			lbg.setMaterial(baseMaterial);
			lbg.Color = theColor.Inverse();
			gobList.add(lbg);
		}
		if (a.isSelected())
		{
			SphereGob sg = new SphereGob(aPosition, sr+0.2);
			sg.Color = theColor.Inverse();
			sg.Color.setAlpha(0.8);
			gobList.add(sg);
			
		}
	}


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

	int getIAtomScale() {
		return (int) (AtomScale * 100);
	}


	void setIAtomScale(int v) {
		AtomScale = v / 100.0f;
	}

}
