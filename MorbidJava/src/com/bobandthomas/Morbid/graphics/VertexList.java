package com.bobandthomas.Morbid.graphics;


import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.Point3D;



public class VertexList extends ArrayList<Vertex> {
	BoundingBox bounds;
	boolean hasNormals;
	boolean hasColors;
	public boolean hasNormals() {
		return hasNormals;
	}
	public boolean hasColors() {
		return hasColors;
	}
	public Point3D getMin() {
		return bounds.getMin();
	}
	public Point3D getMax() {
		return bounds.getMax();
	}
	public Point3D getCenter() {
		return bounds.center();
	}
	public VertexList()
	{
		bounds = new BoundingBox();
	}
	@Override
	public boolean add(Vertex p) {
		if (p.hasColor()) hasColors = true;
		if (p.hasNormal()) hasNormals = true;
		bounds.addPoint(p);
		return super.add(p);
	}
	

}
