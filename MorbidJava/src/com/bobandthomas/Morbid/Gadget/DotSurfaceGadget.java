package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.molecule.Atom;
import com.bobandthomas.Morbid.molecule.AtomType;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.ColorQuadPalette;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

public class DotSurfaceGadget extends Gadget {

	@Override
	public String getGadgetType()
	{
		return "Dot Surface Gadget";
	}

	public enum DotsColorBy {
		Monochrome(0), AtomType(1), DataValue(2);
		DotsColorBy(int i)
		{
			
		}
		private static DotsColorBy[] values = null;

		public static DotsColorBy fromInt(int i) {
			if (DotsColorBy.values == null) {
				DotsColorBy.values = DotsColorBy.values();
			}
			return DotsColorBy.values[i];
		}
};



	double ancr;
	double solvent;
	double radiusScale;
	DotsColorBy colorBy;
	ColorQuadPalette colorList;
	SpatialData sp;
	
	public double getAncr() {
		return ancr;
	}


	public void setAncr(double ancr) {
		this.ancr = ancr;
	}


	public double getSolvent() {
		return solvent;
	}


	public void setSolvent(double solvent) {
		this.solvent = solvent;
	}


	public double getRadiusScale() {
		return radiusScale;
	}


	public void setRadiusScale(double radiusScale) {
		this.radiusScale = radiusScale;
		markDirty();
	}


	public DotsColorBy getColorBy() {
		return colorBy;
	}


	public void setColorBy(DotsColorBy colorBy) {
		this.colorBy = colorBy;
	}


	public DotSurfaceGadget() {
		super();
		ancr = 0.1f;
		solvent = 1.4f;
		radiusScale = 1.0;

		colorBy = DotsColorBy.AtomType;
	}


	@Override
	void Draw(GobList gl) {

		if (!fixGobList(gl))
			return;
		boolean point[] = new boolean[2];
		int i, j, kk;

		double sliceRadius[], sphRadius[];
		double d, rsq, dy;
		double xoff[] = new double[2];

		double zm, ym;
		Molecule mol = GetMolecule();
		GobPoly ppg = new GobPoly();
		ppg.SetPolyType(GobPolyType.Points);
		ppg.Color = baseColor;
		// sp =
		// SpatialDataTypeList::GetGlobalSpatialDataTypeList().GetByID(ID("Chrg")).Create(mol);

		sliceRadius = new double[mol.NumAtoms()];
		sphRadius = new double[mol.NumAtoms()];
		
		colorList = new ColorQuadPalette(mol.NumAtoms());
		MinMax zminmax = new MinMax();
		MinMax yminmax = new MinMax();
		MinMax charge = new MinMax();
		i=0;
		for(Atom a: mol.Atoms())
		{
			double c = a.getCharge();
			charge.addValue(c);
			zminmax.addValue(a.Position().z);
			yminmax.addValue(a.Position().y);
			

			sphRadius[i] = a.Radius() * radiusScale;
			AtomType at = a.getAtomType();
			if (at != null && at.color != null)
				colorList.add(at.color);
			else
				colorList.add(StaticColorQuad.White.cq());

			i++;
		}
		/* leave a little extra space around the molecule */
		zminmax.min += -2.25 - solvent - ancr;
		zminmax.max += 2.25 + solvent;
		yminmax.min += -2.25 - solvent - ancr;
		yminmax.max += 2.25 + solvent;

		for (zm = zminmax.min; zm <= zminmax.max; zm += ancr) {
			/* find the radius of the atoms' intersections with the zPLane */
			for (i = 0; i < mol.NumAtoms(); i++) {
				Atom a = mol.GetAtom(i);
				sliceRadius[i] = 0.0;

				if ((a.Position().z - sphRadius[i]) < zm
						&& (a.Position().z + sphRadius[i]) > zm) {
					sliceRadius[i] = Math.sqrt(sphRadius[i] * sphRadius[i]
							- (a.Position().z - zm) * (a.Position().z - zm));
				}
			}
			/* move a ym plane through the range yminmax.min - yminmax.max */
			for (ym = yminmax.min; ym <= yminmax.max; ym += ancr) {
				for (i = 0; i < mol.NumAtoms(); i++) {
					/* skip atoms that don't even intersect the ym plane */
					Atom a = mol.GetAtom(i);
					if (ym < (a.Position().y - sliceRadius[i])
							|| ym > (a.Position().y + sliceRadius[i]))
						continue;
					d = Math.sqrt((sliceRadius[i] * sliceRadius[i])
							- ((a.Position().y - ym) * (a.Position().y - ym)));
					xoff[0] = a.Position().x - d;
					xoff[1] = a.Position().x + d;

					point[0] = true;
					point[1] = true;
					/*
					 * see if either point is within the radius of any other
					 * atom
					 */
					for (j = 0; j < mol.NumAtoms(); j++) {
						if (j == i)
							continue;

						Atom aj = mol.GetAtom(j);
						rsq = sliceRadius[j] * sliceRadius[j];
						dy = (aj.Position().y - ym) * (aj.Position().y - ym);

						if ((dy + (aj.Position().x - xoff[0])
								* (aj.Position().x - xoff[0])) < rsq)
							point[0] = false;
						if ((dy + (aj.Position().x - xoff[1])
								* (aj.Position().x - xoff[1])) < rsq)
							point[1] = false;
						if (!(point[0] || point[1]))
							break;
					} /* for j = 0; */
					for (kk = 0; kk < 2; kk++) {
						if (!(point[0] || point[1]))
							break;

						if (point[kk]) {
							Point3D cppoint = new Point3D(xoff[kk],
									ym, zm);
							ColorQuad cpcolor = baseColor;
							
							if (colorBy == DotsColorBy.AtomType)
								cpcolor = colorList.get(i);
							else if (colorBy == DotsColorBy.DataValue) {
								double c = sp.Calculate(cppoint);
								cpcolor = plusColor.BlendHSV(minusColor,
										(c - charge.min)
												/ (charge.max - charge.min));
							}

							ppg.AddPoint(cppoint, cpcolor);
							/*
							 * { gl.Add(ppg); ppg = new GobPoly();
							 * ppg.SetPolyType(GobPolyType.Points); ppg.Color =
							 * color; ppg.AddPoint(cppoint, cpcolor); }
							 */
						}
					}
				} /* for i = 0 to mol.atoms_.n */
			} /* for ym = yminmax.min ... */
		} /* for zm = zminmax.min ... */
		gl.add(ppg);
		markClean();
	}


}
