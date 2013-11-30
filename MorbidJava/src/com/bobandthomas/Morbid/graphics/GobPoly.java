package com.bobandthomas.Morbid.graphics;

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
	private VertexList vertexList;
	boolean smooth;

	public GobPoly() {
		polyType = GobPolyType.Closed;
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
		vertexList.add(new Vertex(p,n1));
	}

	public void AddPoint(Point3D p) {
		AddPoint(p, new Vector3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq) {
		AddPoint(p, cq, new Vector3D(0, 0, 0));
	}

	public void AddPoint(Point3D p, ColorQuad cq, Vector3D n1) {
		vertexList.add(new Vertex(p,cq,n1));
	}
	public void AddVertex(Vertex vertex) {
		vertexList.add(vertex);
		
		}


	@Override
	public Point3D center() {
		return vertexList.getCenter();
	}
	
	public int size()
	{
		return vertexList.size();
	}
	
	public VertexList getVertices()
	{
		return vertexList;
	}

	public boolean isHasColors() {
		return vertexList.hasColors;
	}

	public boolean isHasNormals() {
		return vertexList.hasNormals;
	}


};
