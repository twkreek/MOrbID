package com.bobandthomas.Morbid.utils;

import javax.vecmath.Point3d;
import javax.vecmath.Vector3f;

import toxi.geom.Vec3D;

public class Point3D extends Point3d {

	static Point3D Sub(Point3D p1, Point3D p2)
	{
		Point3D p = new Point3D(p1.x-p2.x, p1.y-p2.y, p1.z-p2.z);
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
	public Vector3f getVec3f()
	{
		Vector3f v3f = new Vector3f((float)x, (float)y, (float)z);
		return v3f;
	}
	public void Set(double /*Coord*/ x1, double /*Coord*/ y1, double /*Coord*/ z1) { x = x1; y = y1; z = z1; }
	public void Zero() { x = y = z = 0.0;}


	public Point3D Add(Point3D p) { return new Point3D(x+p.x, y+p.y, z+p.z); }
	public Point3D Sub(Point3D p) { return new Point3D(x-p.x, y-p.y, z-p.z); }
	public Point3D Scale (double /*Coord*/ v) { return new Point3D (x * v, y*v, z*v); }
	public Point3D Cross (Point3D p)
	{
		double /*Coord*/ x1, y1, z1;
		
		x1 = y * p.z - z * p.y;
		y1 = z * p.x - x * p.z;
		z1 = x * p.y - y * p.x;
	 	return new Point3D(x1, y1, z1);// Cross Product
	}
	public double /*Coord*/ Dot(Point3D p)
	{                        
		// This is the dot product operator.
		double /*Coord*/ result;
		
		result = x*p.x + y*p.y + z*p.z;
	 	return result;
	}
	Point3D Translate (double /*Coord*/ v) { return new Point3D (x+v, y+v, z+v);}
	boolean isEqual(Point3D p) { return (x == p.x) && (y == p.y) && (z == p.z); }
	Point3D Mult( Point3D p) { return new Point3D(p.x * x, p.y * y, p.z * z); }
	public Point3D invert()
	{
		x = -x;
		y= -y;
		z= -z;
		return this;
	}

	void Clamp(double /*Coord*/ c) { x = Math.min(x,c); y = Math.min(y,c); z = Math.min(z,c);}

	public double /*Coord*/ Length() 	{ return Math.sqrt(x*x +y*y + z*z); } // acts like it's a vector
	public Point3D Normalize()
	{
	 	double len = Math.sqrt(x*x+y*y+z*z);
		if (len < 1.0e-10)
			return this;
	 	x = (x/len);
	 	y = (y/len);
	 	z = (z/len);
	 	return this;
	}


}
