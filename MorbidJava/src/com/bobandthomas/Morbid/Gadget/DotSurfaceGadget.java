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

// TODO: Auto-generated Javadoc
/**
 * The Class DotSurfaceGadget.
 * 
 * @author Thomas Kreek
 */
public class DotSurfaceGadget extends Gadget {

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#getGadgetType()
	 */
	@Override
	public String getGadgetType()
	{
		return "Dot Surface Gadget";
	}

	/**
	 * The Enum DotsColorBy.
	 * 
	 * @author Thomas Kreek
	 */
	public enum DotsColorBy {
		
		/** The Monochrome. */
		Monochrome(0), 
 /** The Atom type. */
 AtomType(1), 
 /** The Data value. */
 DataValue(2);
		
		/**
		 * Instantiates a new dots color by.
		 * 
		 * @param i
		 *            the i
		 */
		DotsColorBy(int i)
		{
			
		}
		
		/** The values. */
		private static DotsColorBy[] values = null;

		/**
		 * From int.
		 * 
		 * @param i
		 *            the i
		 * @return the dots color by
		 */
		public static DotsColorBy fromInt(int i) {
			if (DotsColorBy.values == null) {
				DotsColorBy.values = DotsColorBy.values();
			}
			return DotsColorBy.values[i];
		}
};



	/** The ancr. */
	double ancr;
	
	/** The solvent. */
	double solvent;
	
	/** The radius scale. */
	double radiusScale;
	
	/** The color by. */
	DotsColorBy colorBy;
	
	/** The color list. */
	ColorQuadPalette colorList;
	
	/** The sp. */
	SpatialData sp;
	
	/**
	 * Gets the ancr.
	 * 
	 * @return the ancr
	 */
	public double getAncr() {
		return ancr;
	}


	/**
	 * Sets the ancr.
	 * 
	 * @param ancr
	 *            the new ancr
	 */
	public void setAncr(double ancr) {
		this.ancr = ancr;
	}


	/**
	 * Gets the solvent.
	 * 
	 * @return the solvent
	 */
	public double getSolvent() {
		return solvent;
	}


	/**
	 * Sets the solvent.
	 * 
	 * @param solvent
	 *            the new solvent
	 */
	public void setSolvent(double solvent) {
		this.solvent = solvent;
	}


	/**
	 * Gets the radius scale.
	 * 
	 * @return the radius scale
	 */
	public double getRadiusScale() {
		return radiusScale;
	}


	/**
	 * Sets the radius scale.
	 * 
	 * @param radiusScale
	 *            the new radius scale
	 */
	public void setRadiusScale(double radiusScale) {
		this.radiusScale = radiusScale;
		markDirty();
	}


	/**
	 * Gets the color by.
	 * 
	 * @return the color by
	 */
	public DotsColorBy getColorBy() {
		return colorBy;
	}


	/**
	 * Sets the color by.
	 * 
	 * @param colorBy
	 *            the new color by
	 */
	public void setColorBy(DotsColorBy colorBy) {
		this.colorBy = colorBy;
	}


	/**
	 * Instantiates a new dot surface gadget.
	 */
	public DotSurfaceGadget() {
		super();
		ancr = 0.1f;
		solvent = 1.4f;
		radiusScale = 1.0;

		colorBy = DotsColorBy.AtomType;
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#Draw(com.bobandthomas.Morbid.graphics.GobList)
	 */
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
		ppg.setColor(baseColor);
		// sp =
		// SpatialDataTypeList::GetGlobalSpatialDataTypeList().GetByID(ID("Chrg")).Create(mol);

		sliceRadius = new double[mol.NumAtoms()];
		sphRadius = new double[mol.NumAtoms()];
		
		colorList = new ColorQuadPalette();
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
