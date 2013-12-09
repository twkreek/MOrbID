package com.bobandthomas.Morbid.utils;

import java.util.ArrayList;

public class Point3DList extends ArrayList<Point3D> {
	BoundingBox bounds;
	public Point3D getMin() {
		return bounds.getMin();
	}
	public Point3D getMax() {
		return bounds.getMax();
	}
	public Point3D center() {
		return bounds.center();
	}
	public Point3DList()
	{
		bounds = new BoundingBox();
	}
	@Override
	public boolean add(Point3D p) {
		bounds.addPoint(p);
		return super.add(p);
	}

	Point3D Normal()
	{
		Vector3D a = get(0).Sub(get(1));;
		Vector3D b = get(2).Sub(get(1));;
	    
		Vector3D norm = a.Cross(b);
		norm = norm.Scale(1/norm.Length());
	    return norm;
	}
}
