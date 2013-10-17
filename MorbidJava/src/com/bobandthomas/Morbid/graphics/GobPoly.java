package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.*;

public class GobPoly extends Gob {

	public enum GobPolyType {
		Arrows, Bezier, BSpline, Closed, Connected, Lines, Nurbs, Points, Polygon, Quads, TMeshFan, TMeshStrip, Triangles
	}

	GobPolyType polyType;
	Point3DList points;
	ColorQuadPalette colors;
	Point3DList normals;
	Point3D pcenter;
	boolean hasNormals;
	boolean hasColors;
	boolean smooth;

	public GobPoly() {
		hasColors = false;
		hasNormals = false;
		polyType = GobPolyType.Closed;
		points = new Point3DList();
		colors = new ColorQuadPalette();
		pcenter = new Point3D();
	}

	@Override
	GobType Type() {
		return GobType.Poly;
	}

	public void SetPolyType(GobPolyType gpt) {
		polyType = gpt;
	}

	GobPolyType GetPolyType() {
		return polyType;
	}

	public void AddPoint(Point3D p, Point3D n1) {
		pcenter = pcenter.Add(p);
		if (hasNormals || !n1.IsZero()) {
			if (normals == null)
				normals = new Point3DList();
			normals.add(n1);
			hasNormals = true;
		}
		points.add(p);
		;
	}

	public void AddPoint(Point3D p) {
		AddPoint(p, new Point3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq) {
		AddPoint(p, cq, new Point3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq, Point3D n1) {
		pcenter = pcenter.Add(p);
		if (hasNormals || !n1.IsZero()) {
			if (normals == null)
				normals = new Point3DList();
			normals.add(n1);
			hasNormals = true;
		}
		hasColors = true;
		colors.add(cq);
		points.add(p);
	}

	@Override
	Point3D center() {
		return pcenter.Scale(1 / points.size());
	}

};
