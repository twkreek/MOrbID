package com.bobandthomas.Morbid.utils;

import javax.vecmath.Point3d;
import toxi.geom.Vec3D;

public class Point3D extends Point3d {

	static Vector3D Sub(Point3D point1, Point3D point2)
	{
		Vector3D p = new Vector3D(point1.x-point2.x, point1.y-point2.y, point1.z-point2.z);
		return p;
	}
	public boolean isZero() { return x == 0.0 && y == 0.0 && z == 0.0; } 
	
	public Point3D( double x1, double y1, double z1) 
	{ x = x1; y = y1; z = z1; }
	public Point3D(int x1, int y1, int z1) { x = x1; y = y1; z = z1; }

	public Point3D(Point3D point) {
		x=point.x;
		y=point.y;
		z=point.z;
	}
	public Point3D() {
		zero();
	}
	public Point3D(Vec3D vec) {
		x=vec.x;
		y=vec.y;
		z=vec.z;
	}
	public void zero() { x = y = z = 0.0;}


	public Point3D Add(Vector3D p) { return new Point3D(x+p.x, y+p.y, z+p.z); }
	public Point3D Sub(Vector3D p) { return new Point3D(x-p.x, y-p.y, z-p.z); }
	public Vector3D Sub(Point3D p) { return new Vector3D(x-p.x, y-p.y, z-p.z); }
	public Point3D Scale (double /*Coord*/ v) { return new Point3D (x * v, y*v, z*v); }
	public Vector3D getVector() 
	/*
	 * Gets the vector from this point to the center.  
	 * This should be deprecated.
	 */
	{ 
		//TODO deprecate when possible
		return new Vector3D(x,y,z);}
	public Point3D midPoint(Point3D endPoint, double fraction)
	{
		return new Point3D((endPoint.x+x)*fraction, (endPoint.y+y)*fraction, (endPoint.z+z)*fraction );
	}
	public Point3D Sum(Point3D p)
	/*
	 * used to accumulate points to calucalte the center of a cluster.
	 */
	{
		return new Point3D(x+p.x, y+p.y, z+p.z); 
	}
	Point3D Translate (double /*Coord*/ v) { return new Point3D (x+v, y+v, z+v);}
	boolean isEqual(Point3D p) { return (x == p.x) && (y == p.y) && (z == p.z); }


}
