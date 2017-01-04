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

import java.util.ArrayList;
import java.util.List;

import toxi.geom.Spline3D;
import toxi.geom.Vec3D;

import com.bobandthomas.Morbid.Scene;
import com.bobandthomas.Morbid.graphics.CylinderGob;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.molecule.AminoAcidFragment;
import com.bobandthomas.Morbid.molecule.Peptide;
import com.bobandthomas.Morbid.molecule.SubstructureMap;
import com.bobandthomas.Morbid.molecule.SubstructureMap.SubstructureType;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.ColorQuadPalette;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Point3DList;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class RibbonGadget.
 * 
 * @author Thomas Kreek
 */
public class RibbonGadget extends Gadget {

	/**
	 * The Class Circle.
	 * 
	 * @author Thomas Kreek
	 */
	class Circle extends Point3DList {
		
		/** The center. */
		Point3D center;
		
		/** The radius. */
		double radius;
		
		/** The v1. */
		Vector3D v1;
		
		/** The v2. */
		Vector3D v2;
		
		/** The n points. */
		int nPoints;
		
		/** The offset. */
		int offset = 0;

		/**
		 * Instantiates a new circle.
		 * 
		 * @param center
		 *            the center
		 * @param vector1
		 *            the vector1
		 * @param vector2
		 *            the vector2
		 * @param r
		 *            the r
		 * @param nPoints
		 *            the n points
		 */
		public Circle(Point3D center, Vector3D vector1, Vector3D vector2,
				double r, int nPoints) {
			v1 = new Vector3D(vector1);
			v2 = new Vector3D(vector2);
			this.center = center;
			radius = r;
			this.nPoints = nPoints;
			calcCircle();
		}

		/**
		 * Instantiates a new circle.
		 * 
		 * @param center
		 *            the center
		 * @param vector1
		 *            the vector1
		 * @param vector2
		 *            the vector2
		 * @param r
		 *            the r
		 * @param nPoints
		 *            the n points
		 */
		public Circle(Vec3D center, Vec3D vector1, Vec3D vector2, double r,
				int nPoints) {
			this.center = new Point3D(center);
			v1 = new Vector3D(vector1);
			v2 = new Vector3D(vector2);
			radius = r;
			this.nPoints = nPoints;
			calcCircle();
		}

		/**
		 * Calc circle.
		 */
		public void calcCircle() {
			v1.Normalize();
			v2.Normalize();
			for (int i = 0; i < nPoints; i++) {
				Point3D p = new Point3D();
				double cos = Math.cos(Math.PI * 2 * i / nPoints);
				double sin = Math.sin(Math.PI * 2 * i / nPoints);
				p.x = center.x + radius * cos * v1.x + radius * sin * v2.x;
				p.y = center.y + radius * cos * v1.y + radius * sin * v2.y;
				p.z = center.z + radius * cos * v1.z + radius * sin * v2.z;
				add(p);
			}

		}

		/**
		 * Gets the point.
		 * 
		 * @param i
		 *            the i
		 * @return the point
		 */
		public Point3D getPoint(int i) {
			return get((i + offset) % nPoints);
		}

		/**
		 * Gets the normal.
		 * 
		 * @param i
		 *            the i
		 * @return the normal
		 */
		public Vector3D getNormal(int i) {
			return get((i + offset) % nPoints).Sub(center).Normalize();
		}
	}

	/** The set. */
	Peptide set;
	
	/** The frags. */
	ArrayList<AminoAcidFragment> frags;
	
	/** The resolution. */
	int resolution = 4;
	
	/** The circle resolution. */
	int circleResolution = 8;
	
	/** The radius. */
	double radius = 0.5;

	/** The cylinders. */
	boolean cylinders = false;
	
	/** The no peptide. */
	boolean noPeptide = false;

	/**
	 * Instantiates a new ribbon gadget.
	 */
	public RibbonGadget() {
		set = null;
		frags = null;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#getGadgetType()
	 */
	@Override
	public String getGadgetType() {
		return "Ribbon Gadget";
	}

	/**
	 * Gets the resolution.
	 * 
	 * @return the resolution
	 */
	public int getResolution() {
		return resolution;
	}

	/**
	 * Sets the resolution.
	 * 
	 * @param resolution
	 *            the new resolution
	 */
	public void setResolution(int resolution) {
		this.resolution = resolution;
		markDirty();
	}

	/**
	 * Gets the radius.
	 * 
	 * @return the radius
	 */
	public double getRadius() {
		return radius;
	}

	/**
	 * Sets the radius.
	 * 
	 * @param radius
	 *            the new radius
	 */
	public void setRadius(double radius) {
		this.radius = radius;
		markDirty();
	}

	/**
	 * Gets the circle resolution.
	 * 
	 * @return the circle resolution
	 */
	public int getCircleResolution() {
		return circleResolution;
	}

	/**
	 * Sets the circle resolution.
	 * 
	 * @param circleResolution
	 *            the new circle resolution
	 */
	public void setCircleResolution(int circleResolution) {
		this.circleResolution = circleResolution;
		markDirty();
	}

	/**
	 * Checks if is cylinders.
	 * 
	 * @return true, if is cylinders
	 */
	public boolean isCylinders() {
		return cylinders;
	}

	/**
	 * Sets the cylinders.
	 * 
	 * @param cylinders
	 *            the new cylinders
	 */
	public void setCylinders(boolean cylinders) {
		this.cylinders = cylinders;
		markDirty();
	}

	/**
	 * The Class RibbonPoints.
	 * 
	 * @author Thomas Kreek
	 */
	class RibbonPoints {

		/** The spline steps. */
		int splineSteps = 0;
		
		/** The alpha points. */
		ArrayList<Vec3D> alphaPoints;
		
		/** The amino points. */
		ArrayList<Vec3D> aminoPoints;
		
		/** The beta points. */
		ArrayList<Vec3D> betaPoints;
		
		/** The frag colors. */
		ColorQuadPalette fragColors;

		/** The alpha s points. */
		List<Vec3D> alphaSPoints;
		
		/** The amino s points. */
		List<Vec3D> aminoSPoints;
		
		/** The beta s points. */
		List<Vec3D> betaSPoints;
		
		/** The frag s colors. */
		ColorQuadPalette fragSColors;

		/**
		 * Instantiates a new ribbon points.
		 */
		RibbonPoints() {
			alphaPoints = new ArrayList<Vec3D>();
			aminoPoints = new ArrayList<Vec3D>();
			betaPoints = new ArrayList<Vec3D>();
			fragColors = new ColorQuadPalette();

		}

		/**
		 * Gets the alpha.
		 * 
		 * @param i
		 *            the i
		 * @return the alpha
		 */
		Vec3D getAlpha(int i) {
			return alphaPoints.get(i);
		}

		/**
		 * Gets the beta.
		 * 
		 * @param i
		 *            the i
		 * @return the beta
		 */
		Vec3D getBeta(int i) {
			return betaPoints.get(i);
		}

		/**
		 * Gets the amino.
		 * 
		 * @param i
		 *            the i
		 * @return the amino
		 */
		Vec3D getAmino(int i) {
			return aminoPoints.get(i);
		}

		/**
		 * Gets the color.
		 * 
		 * @param i
		 *            the i
		 * @return the color
		 */
		ColorQuad getColor(int i) {
			return fragColors.get(i);
		}

		/**
		 * Gets the size.
		 * 
		 * @return the size
		 */
		int getSize() {
			return alphaPoints.size();
		}

		/**
		 * Gets the s alpha.
		 * 
		 * @param i
		 *            the i
		 * @return the s alpha
		 */
		Vec3D getSAlpha(int i) {
			return alphaSPoints.get(i);
		}

		/**
		 * Gets the s beta.
		 * 
		 * @param i
		 *            the i
		 * @return the s beta
		 */
		Vec3D getSBeta(int i) {
			return betaSPoints.get(i);
		}

		/**
		 * Gets the s amino.
		 * 
		 * @param i
		 *            the i
		 * @return the s amino
		 */
		Vec3D getSAmino(int i) {
			return aminoSPoints.get(i);
		}

		/**
		 * Gets the s color.
		 * 
		 * @param i
		 *            the i
		 * @return the s color
		 */
		ColorQuad getSColor(int i) {
			return fragSColors.get(i);
		}

		/**
		 * Gets the spline slize.
		 * 
		 * @return the spline slize
		 */
		int getSplineSlize() {
			return alphaSPoints.size();
		}

		/**
		 * Do spline.
		 * 
		 * @param splineSteps
		 *            the spline steps
		 */
		public void doSpline(int splineSteps) {
			Spline3D alphaSpline = new Spline3D(alphaPoints);
			alphaSPoints = alphaSpline.computeVertices(resolution);

			Spline3D betaSpline = new Spline3D(betaPoints);
			betaSPoints = betaSpline.computeVertices(resolution);

			Spline3D aminoSpline = new Spline3D(aminoPoints);
			aminoSPoints = aminoSpline.computeVertices(resolution);

			fragSColors = interpolateColors(fragColors, resolution);

		}

		/**
		 * Interpolate colors.
		 * 
		 * @param fragColors
		 *            the frag colors
		 * @param resolution
		 *            the resolution
		 * @return the color quad palette
		 */
		private ColorQuadPalette interpolateColors(ColorQuadPalette fragColors,
				int resolution) {

			ColorQuadPalette colors = new ColorQuadPalette();
			for (int i = 0; i < (fragColors.size() - 1); i++) {
				ColorQuad c1 = fragColors.get(i);
				ColorQuad c2 = fragColors.get(i + 1);
				for (int j = 0; j < resolution; j++) {
					float fraction = (j % resolution) * 1.0f / resolution;
					colors.add(c1.BlendRGB(c2, fraction));
				}

			}
			colors.add(fragColors.get(fragColors.size() - 1));
			return colors;
		}

		/**
		 * Setup lists.
		 * 
		 * @param currentFragment
		 *            the current fragment
		 * @param chainType
		 *            the chain type
		 */
		void SetupLists(AminoAcidFragment currentFragment, int chainType) {
			alphaPoints = new ArrayList<Vec3D>();
			aminoPoints = new ArrayList<Vec3D>();
			betaPoints = new ArrayList<Vec3D>();
			fragColors = new ColorQuadPalette();

			
			do {
				Point3D pAlpha;
				pAlpha = currentFragment.getcAlpha().Position();
				Point3D pBeta = currentFragment.getcAmide().Position();
				Point3D pAmino = currentFragment.getnAmino().Position();
				Vec3D point;
				point = new Vec3D((float) pAlpha.x, (float) pAlpha.y,
						(float) pAlpha.z);
				alphaPoints.add(point);
				point = new Vec3D((float) pAmino.x, (float) pAmino.y,
						(float) pAmino.z);
				aminoPoints.add(point);
				point = new Vec3D((float) pBeta.x, (float) pBeta.y,
						(float) pBeta.z);
				betaPoints.add(point);
				fragColors.add(getAtomColor(currentFragment.getcAlpha()));

				currentFragment = currentFragment.getNext();
			} while (currentFragment != null);

		}
	}

	/**
	 * Draw cylinders.
	 * 
	 * @param gl
	 *            the gl
	 */
	void DrawCylinders(GobList gl) {
		for (AminoAcidFragment aa : frags) {
			RibbonPoints ribbon = new RibbonPoints();
			ribbon.SetupLists(aa, 0);
			ribbon.doSpline(resolution);

			Vec3D lastPoint = null;

			for (int i = 0; i < ribbon.getSplineSlize(); i++) {
				Vec3D vec = ribbon.getSAlpha(i);
				if (lastPoint == null) {
					lastPoint = vec;
					continue;
				}
				Vec3D vec2 = vec.add(vec.sub(lastPoint).scale((float) 0.5));
				CylinderGob gob = new CylinderGob(new Point3D(vec2),
						new Point3D(lastPoint), (float) radius);
		//		baseMaterial.setColor(ribbon.getSColor(i));
				gob.setMaterial(baseMaterial);
				gob.setColor(ribbon.getSColor(i));
				gl.add(gob);
				lastPoint = vec;

			}

		}
	}

	/**
	 * Compute orthogonals.
	 * 
	 * @param center
	 *            the center
	 * @param line
	 *            the line
	 * @param startingPoint
	 *            the starting point
	 * @return the vector3 d[]
	 */
	Vector3D[] computeOrthogonals(Point3D center, Vector3D line,
			Point3D startingPoint) {
		Vector3D[] vectors = new Vector3D[2];
		if (startingPoint == null) {
			startingPoint = new Point3D(0, 0, 0);
			Vector3D temp = startingPoint.Sub(center).Normalize();
			temp = temp.Cross(line);
			startingPoint = temp.Add(center);
		}

		line.Normalize();
		Vector3D vecMid = startingPoint.Sub(center).Normalize();
		Vector3D cross1 = vecMid.Cross(line).Normalize();
		vecMid.Scale(0.5);
		vectors[0] = vecMid;
		vectors[1] = cross1;

		return vectors;
	}

	/**
	 * Tessalate circular patch.
	 * 
	 * @param gob
	 *            the gob
	 * @param circles
	 *            the circles
	 * @param colors
	 *            the colors
	 */
	void tessalateCircularPatch(GobPoly gob, Circle[] circles,
			ColorQuad[] colors) {
		for (int i0 = 0; i0 < circles[0].size(); i0++) {
			/*
			 * p(0,0) - P(1,0) Vec0 | \ | p(0,1) - P(1,1) Vec1
			 */
			int i1 = (i0 + 1) % circles[0].size();

			gob.AddPoint(circles[0].get(i0), colors[0],
					circles[0].getNormal(i0));
			gob.AddPoint(circles[0].get(i1), colors[0],
					circles[0].getNormal(i1));
			gob.AddPoint(circles[1].get(i1), colors[1],
					circles[1].getNormal(i1));

			gob.AddPoint(circles[0].get(i0), colors[0],
					circles[0].getNormal(i0));
			gob.AddPoint(circles[1].get(i1), colors[1],
					circles[1].getNormal(i1));
			gob.AddPoint(circles[1].get(i0), colors[1],
					circles[1].getNormal(i0));

		}

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#Draw(com.bobandthomas.Morbid.graphics.GobList)
	 */
	@Override
	void Draw(GobList gl) {
		gl.clear();
		if (cylinders)
			DrawCylinders(gl);
		else
			DrawRibbons(gl);
	}

	/**
	 * Draw ribbons.
	 * 
	 * @param gl
	 *            the gl
	 */
	void DrawRibbons(GobList gl) {
		if (noPeptide)
			return;
		for (AminoAcidFragment aa : frags) {
			RibbonPoints ribbon = new RibbonPoints();
			ribbon.SetupLists(aa, 0);
			ribbon.doSpline(resolution);

			Circle[] circles = new Circle[2];
			ColorQuad[] colors = new ColorQuad[2];
			GobPoly gob = new GobPoly();
			gob.SetPolyType(GobPolyType.Triangles);
			gob.setMaterial(baseMaterial);
			for (int i = 0; i < ribbon.getSplineSlize(); i++) {
				Vec3D p = ribbon.getSAlpha(i);
				/**vector pointing in the direction of N-C=O
				*/
				Vec3D line = ribbon.getSBeta(i).sub(ribbon.getSAmino(i)); 
				Vec3D midLigandsPoint = ribbon.getSAmino(i)
						.add(ribbon.getSBeta(i)).scale(0.5f);
				Vec3D vecMid = midLigandsPoint.sub(p).normalize();
				Vec3D cross1 = vecMid.cross(line).normalize();
				vecMid.scale((float) 0.5);
				circles[0] = circles[1];
				circles[1] = new Circle(ribbon.getSAlpha(i), cross1, vecMid,
						radius, circleResolution);

				colors[0] = colors[1];
				colors[1] = ribbon.getSColor(i);
				if (i > 0) {
					tessalateCircularPatch(gob, circles, colors);
				}
			}

			gl.add(gob);
		}

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#setScene(com.bobandthomas.Morbid.Gadget.Scene)
	 */
	@Override
	public void setScene(Scene s) {
		super.setScene(s);
		if (set == null) {
			SubstructureMap ssm = molecule.getSubstructures();
			set = (Peptide) ssm.get(SubstructureType.RESNUMS);
			if (set == null)
				noPeptide = true;
			else
				frags = set.getN3list();
		}
	}

}
