package com.bobandthomas.Morbid.utils;

import javax.vecmath.Point3d;
import toxi.geom.Vec3D;

public class Point3D extends Point3d {

	static Vector3D Sub(Point3D p1, Point3D p2)
	{
		Vector3D p = new Vector3D(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z);
		return p;
	}
	public boolean IsZero() { return x == 0.0 && y == 0.0 && z == 0.0; } 
	
	public Point3D( double /*Coord*/ x1, double /*Coord*/ y1, double /*Coord*/ z1) { x = x1; y = y1; z = z1; }
	public Point3D(int x1, int y1, int z1) { x = x1; y = y1; z = z1; }

	public Point3D(Point3D n1) {
		x=n1.x;
		y=n1.y;
		z=n1.z;
	}
	public Point3D() {
		Zero();
	}
	public Point3D(Vec3D vec) {
		x=vec.x;
		y=vec.y;
		z=vec.z;
	}
	public void Set(double /*Coord*/ x1, double /*Coord*/ y1, double /*Coord*/ z1) { x = x1; y = y1; z = z1; }
	public void Zero() { x = y = z = 0.0;}


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
	Point3D Mult( Point3D p) { return new Point3D(p.x * x, p.y * y, p.z * z); }


}
