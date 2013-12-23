package com.bobandthomas.Morbid.Gadget;

import java.util.ArrayList;
import java.util.List;

import toxi.geom.Spline3D;
import toxi.geom.Vec3D;

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

public class RibbonGadget extends Gadget {
	
	class Circle extends Point3DList
	{
		Point3D center;
		double radius;
		Vector3D v1;
		Vector3D v2;
		int nPoints;
		int offset = 0;
		/**
		 * @param center
		 * @param vector1
		 * @param vector2
		 * @param r
		 * @param nPoints
		 */
		public Circle(Point3D center, Vector3D vector1, Vector3D vector2, double r, int nPoints)
		{
			v1 = new Vector3D(vector1);
			v2 = new Vector3D(vector2);
			this.center = center;
			radius = r;
			this.nPoints = nPoints;
			calcCircle();
		}
		public Circle(Vec3D center, Vec3D vector1, Vec3D vector2, double r, int nPoints)
		{
			this.center = new Point3D(center);
			v1 = new Vector3D(vector1);
			v2 = new Vector3D(vector2);
			radius = r;
			this.nPoints = nPoints;
			calcCircle();
		}

		public void calcCircle()
		{
			v1.Normalize();
			v2.Normalize();
				for (int i = 0; i< nPoints; i++)
			{
				Point3D p = new Point3D();
				double cos = Math.cos(Math.PI * 2* i/nPoints);
				double sin = Math.sin(Math.PI * 2* i/nPoints);
				p.x = center.x + radius*cos * v1.x  + radius*sin *v2.x;
				p.y = center.y + radius*cos * v1.y  + radius*sin *v2.y;
				p.z = center.z + radius*cos * v1.z  + radius*sin *v2.z;
				add(p);
			}
			
		}
		public Point3D getPoint(int i)
		{
			return get((i + offset) % nPoints);
		}
		public Vector3D getNormal(int i)
		{
			return get((i + offset) % nPoints).Sub(center).Normalize();
		}
	}

	Peptide set;
	ArrayList<AminoAcidFragment> frags;
	int resolution = 2;
	int circleResolution = 8;
	double radius = 0.5;

	boolean cylinders = false;
	boolean noPeptide = false;
	public RibbonGadget() {
		set = null;
		frags = null;
	}

	@Override
	public String getGadgetType() {
		return "Ribbon Gadget";
	}
	
	public int getResolution() {
		return resolution;
	}

	public void setResolution(int resolution) {
		this.resolution = resolution;
		markDirty();
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
		markDirty();
	}

	public int getCircleResolution() {
		return circleResolution;
	}

	public void setCircleResolution(int circleResolution) {
		this.circleResolution = circleResolution;
		markDirty();
	}

	public boolean isCylinders() {
		return cylinders;
	}

	public void setCylinders(boolean cylinders) {
		this.cylinders = cylinders;
		markDirty();
	}
	class RibbonPoints
	{

		int splineSteps = 0;
		ArrayList<Vec3D> alphaPoints;
		ArrayList<Vec3D> aminoPoints;
		ArrayList<Vec3D> betaPoints;
		ColorQuadPalette fragColors;
		
		List<Vec3D> alphaSPoints;
		List<Vec3D> aminoSPoints;
		List<Vec3D> betaSPoints;
		ColorQuadPalette fragSColors;
		
		RibbonPoints()
		{
			alphaPoints = new ArrayList<Vec3D>();
			aminoPoints = new ArrayList<Vec3D>();
			betaPoints = new ArrayList<Vec3D>();
			fragColors = new ColorQuadPalette();
			
		}
		Vec3D getAlpha(int i) { return alphaPoints.get(i); }
		Vec3D getBeta(int i) { return betaPoints.get(i); }
		Vec3D getAmino(int i) { return aminoPoints.get(i); }
		ColorQuad getColor(int i) { return fragColors.get(i); }
		int getSize() {return alphaPoints.size(); }
		
		Vec3D getSAlpha(int i) { return alphaSPoints.get(i); }
		Vec3D getSBeta(int i) { return betaSPoints.get(i); }
		Vec3D getSAmino(int i) { return aminoSPoints.get(i); }
		ColorQuad getSColor(int i) { return fragSColors.get(i); }
		int getSplineSlize() {return alphaSPoints.size(); }
		public void doSpline(int splineSteps)
		{
			Spline3D alphaSpline = new Spline3D(alphaPoints);
			alphaSPoints = alphaSpline.computeVertices(resolution);
			
			Spline3D betaSpline = new Spline3D(betaPoints);
			betaSPoints = betaSpline.computeVertices(resolution);
			
			Spline3D aminoSpline = new Spline3D(aminoPoints);
			aminoSPoints = aminoSpline.computeVertices(resolution);
		
			
			fragSColors = interpolateColors(fragColors, resolution);
		
		}
		
		private ColorQuadPalette interpolateColors(ColorQuadPalette fragColors, int resolution)
		{
			
			ColorQuadPalette colors = new ColorQuadPalette();
			for (int i = 0; i < (fragColors.size()-1); i++)
			{
				ColorQuad c1 = fragColors.get(i);
				ColorQuad c2 = fragColors.get(i+1);
				for (int j = 0; j< resolution; j++)
				{
					float fraction =  (j%resolution)*1.0f/resolution;
					colors.add(c1.BlendRGB(c2, fraction));
				}
				
				
			}	
			colors.add(fragColors.get(fragColors.size()-1));
			return colors;
		}
	void SetupLists(AminoAcidFragment currentFragment, int chainType)
		{
			do
			{
				Point3D pAlpha;
				pAlpha = currentFragment.getcAlpha().Position();
				Point3D pBeta = currentFragment.getcAmide().Position();
				Point3D pAmino = currentFragment.getnAmino().Position();
				Vec3D point;
				point =new Vec3D((float) pAlpha.x, (float) pAlpha.y, (float) pAlpha.z);
				alphaPoints.add(point);
				point=new Vec3D((float) pAmino.x, (float) pAmino.y, (float) pAmino.z);
				aminoPoints.add(point);
				point=new Vec3D((float) pBeta.x, (float) pBeta.y, (float) pBeta.z);
				betaPoints.add(point);
				fragColors.add(getAtomColor(currentFragment.getcAlpha()));
				
				currentFragment = currentFragment.getNext();
			} while (currentFragment!= null);
			
		}
	}
	
	
	void DrawCylinders(GobList gl)
	{
		for (AminoAcidFragment aa : frags)
		{
			RibbonPoints ribbon = new RibbonPoints();
			ribbon.SetupLists(aa, 0);
			ribbon.doSpline(resolution);

						
			Vec3D lastPoint = null;
			
			for (int i = 0; i < ribbon.getSplineSlize(); i ++)
			{
				Vec3D vec = ribbon.getSAlpha(i);
				if (lastPoint == null)
				{
					lastPoint = vec;
					continue;
				}
				Vec3D vec2 = vec.add(vec.sub(lastPoint).scale((float)0.5));
				CylinderGob gob = new CylinderGob(new Point3D(vec2), new Point3D(lastPoint), (float) radius);
				baseMaterial.setColor(ribbon.getSColor(i));
				gob.setMaterial(baseMaterial);
				gl.add(gob);
				lastPoint = vec;
				
			}
			
		}
	}

	Vector3D[] computeOrthogonals(Point3D center, Vector3D line, Point3D startingPoint)
	{
		Vector3D[] vectors = new Vector3D[2];
		if (startingPoint == null)
		{
			startingPoint = new Point3D(0,0,0);
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
    void tessalateCircularPatch(GobPoly gob, Circle[] circles, ColorQuad[] colors)
    {
		for (int i0 = 0; i0 < circles[0].size(); i0++)
		{
			/*
			 *    p(0,0) - P(1,0) Vec0
			 *     |    \   |
			 *    p(0,1) - P(1,1)  Vec1
			 */
			int i1 = (i0+1)%circles[0].size();

			gob.AddPoint(circles[0].get(i0), colors[0], circles[0].getNormal(i0));
			gob.AddPoint(circles[0].get(i1), colors[0], circles[0].getNormal(i1));
			gob.AddPoint(circles[1].get(i1), colors[1], circles[1].getNormal(i1));

			gob.AddPoint(circles[0].get(i0), colors[0], circles[0].getNormal(i0));
			gob.AddPoint(circles[1].get(i1), colors[1], circles[1].getNormal(i1));
			gob.AddPoint(circles[1].get(i0), colors[1], circles[1].getNormal(i0));

		}
   	
    }
@Override
	void Draw(GobList gl)
		{
			gl.clear();
			if (cylinders)
				DrawCylinders(gl);
			else
				DrawRibbons(gl);
		}
	void DrawRibbons(GobList gl) {
		if (noPeptide) return;
		for (AminoAcidFragment aa : frags)
		{
			RibbonPoints ribbon = new RibbonPoints();
			ribbon.SetupLists(aa, 0);
			ribbon.doSpline(resolution);


			Circle[] circles = new Circle[2];
			ColorQuad[] colors = new ColorQuad[2];
			GobPoly gob = new GobPoly();
			gob.SetPolyType(GobPolyType.Triangles);
			gob.setMaterial(baseMaterial);
			for (int i = 0; i < ribbon.getSplineSlize(); i++)
			{
				Vec3D p = ribbon.getSAlpha(i);
				
				
				Vec3D line = ribbon.getSBeta(i).sub(ribbon.getSAmino(i)); //vector pointing in the direction of N c=o
				Vec3D midLigandsPoint = ribbon.getSAmino(i).add(ribbon.getSBeta(i)).scale(0.5f);  
				Vec3D vecMid = midLigandsPoint.sub(p).normalize();
				Vec3D cross1 = vecMid.cross(line).normalize();
				vecMid.scale((float) 0.5);
				circles[0] =circles[1];
				circles[1] = new Circle(ribbon.getSAlpha(i), cross1, vecMid, radius, circleResolution);
				
				colors[0] = colors[1];
				colors[1] = ribbon.getSColor(i);
				if (i>0)
				{
					tessalateCircularPatch(gob,circles,colors);
				}
			}
			
			gl.add(gob);
		}
		
	}
	@Override
	public void setScene(Scene s) {
		super.setScene(s);
		if (set == null)
		{
			SubstructureMap ssm = molecule.getSubstructures();
			set = (Peptide) ssm.get(SubstructureType.RESNUMS);
			if (set == null)
				noPeptide = true;
			else
				frags = set.getN3list();
		}
	}


}
