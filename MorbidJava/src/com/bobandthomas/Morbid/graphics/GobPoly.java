package com.bobandthomas.Morbid.graphics;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.*;

public class GobPoly extends Gob {

	public enum GobPolyType {
		Arrows,
		Bezier, 
		BSpline, 
		Closed, 
		Connected, 
		Lines, 
		Nurbs, 
		Points, 
		Polygon, 
		Quads, 
		Segments,
		TMeshFan, 
		TMeshStrip, 
		Triangles
	}

	GobPolyType polyType;
	private Point3DList points;
	private ColorQuadPalette colors;
	private ArrayList<Vector3D> normals;
	private VertexList vertexList;
	// Switch to Vertex3List;
	Point3D pcenter;
	private boolean hasNormals;
	private boolean hasColors;
	boolean smooth;

	public GobPoly() {
		hasColors = false;
		hasNormals = false;
		polyType = GobPolyType.Closed;
		points = new Point3DList();
		colors = new ColorQuadPalette();
		pcenter = new Point3D();
		vertexList = new VertexList();
	}

	@Override
	public GobType Type() {
		return GobType.Poly;
	}

	public void SetPolyType(GobPolyType gpt) {
		polyType = gpt;
	}

	public GobPolyType GetPolyType() {
		return polyType;
	}

	public void AddPoint(Point3D p, Vector3D n1) {
		pcenter = pcenter.Sum(p);
		if (hasNormals || !n1.IsZero()) {
			if (normals == null)
				normals = new ArrayList<Vector3D>();
			normals.add(n1);
			hasNormals = true;
		}
		points.add(p);
		;
	}

	public void AddPoint(Point3D p) {
		AddPoint(p, new Vector3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq) {
		AddPoint(p, cq, new Vector3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq, Vector3D n1) {
		pcenter = pcenter.Sum(p);
		if (hasNormals || !n1.IsZero()) {
			if (normals == null)
				normals = new ArrayList<Vector3D>();
			normals.add(n1);
			hasNormals = true;
		}
		hasColors = true;
		colors.add(cq);
		points.add(p);
	}
	public void AddVertex(Vertex vertex) {
		pcenter = pcenter.Sum(vertex);
		if (hasNormals || vertex.hasNormal()) {
			if (normals == null)
				normals = new ArrayList<Vector3D>();
			normals.add(vertex.normal);
			hasNormals = true;
		}
		if (hasColors || vertex.hasColor()) {
			if (colors == null)
				colors = new ColorQuadPalette();
			colors.add(vertex.color);
			hasColors = true;
		}
			
		points.add(vertex);
	}


	@Override
	public Point3D center() {
		return pcenter.Scale(1 / points.size());
	}

	public Point3DList getPoints() {
		return points;
	}

	public void setPoints(Point3DList points) {
		this.points = points;
	}

	public boolean isHasColors() {
		return hasColors;
	}

	public void setHasColors(boolean hasColors) {
		this.hasColors = hasColors;
	}

	public ColorQuadPalette getColors() {
		return colors;
	}

	public void setColors(ColorQuadPalette colors) {
		this.colors = colors;
	}

	public boolean isHasNormals() {
		return hasNormals;
	}

	public void setHasNormals(boolean hasNormals) {
		this.hasNormals = hasNormals;
	}

	public ArrayList<Vector3D> getNormals() {
		return normals;
	}

	public void setNormals(ArrayList<Vector3D> normals) {
		this.normals = normals;
	}

};
