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
package com.bobandthomas.Morbid.utils;

import javax.vecmath.Point3d;

import toxi.geom.Vec3D;

// TODO: Auto-generated Javadoc
/**
 * The Class Point3D.
 * 
 * @author Thomas Kreek
 */
public class Point3D extends Point3d {

	/**
	 * Sub.
	 * 
	 * @param point1
	 *            the point1
	 * @param point2
	 *            the point2
	 * @return the vector3 d
	 */
	static Vector3D Sub(Point3D point1, Point3D point2)
	{
		Vector3D p = new Vector3D(point1.x-point2.x, point1.y-point2.y, point1.z-point2.z);
		return p;
	}
	
	/**
	 * Checks if is zero.
	 * 
	 * @return true, if is zero
	 */
	public boolean isZero() { return x == 0.0 && y == 0.0 && z == 0.0; } 
	
	/**
	 * Instantiates a new point3 d.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param z1
	 *            the z1
	 */
	public Point3D( double x1, double y1, double z1) 
	{ x = x1; y = y1; z = z1; }
	
	/**
	 * Instantiates a new point3 d.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param z1
	 *            the z1
	 */
	public Point3D(int x1, int y1, int z1) { x = x1; y = y1; z = z1; }

	/**
	 * Instantiates a new point3 d.
	 * 
	 * @param point
	 *            the point
	 */
	public Point3D(Point3D point) {
		x=point.x;
		y=point.y;
		z=point.z;
	}
	
	/**
	 * Instantiates a new point3 d.
	 */
	public Point3D() {
		zero();
	}
	
	/**
	 * Instantiates a new point3 d.
	 * 
	 * @param vec
	 *            the vec
	 */
	public Point3D(Vec3D vec) {
		x=vec.x;
		y=vec.y;
		z=vec.z;
	}
	
	/**
	 * Zero.
	 */
	public void zero() { x = y = z = 0.0;}
	
	/**
	 * I x.
	 * 
	 * @return the int
	 */
	public int iX() { return (int) x; }
	
	/**
	 * I y.
	 * 
	 * @return the int
	 */
	public int iY() { return (int) y; }
	
	/**
	 * I z.
	 * 
	 * @return the int
	 */
	public int iZ() { return (int) z; }
	


	/**
	 * Adds the.
	 * 
	 * @param p
	 *            the p
	 * @return the point3 d
	 */
	public Point3D Add(Vector3D p) { return new Point3D(x+p.x, y+p.y, z+p.z); }
	
	/**
	 * Sub.
	 * 
	 * @param p
	 *            the p
	 * @return the point3 d
	 */
	public Point3D Sub(Vector3D p) { return new Point3D(x-p.x, y-p.y, z-p.z); }
	
	/**
	 * Sub.
	 * 
	 * @param p
	 *            the p
	 * @return the vector3 d
	 */
	public Vector3D Sub(Point3D p) { return new Vector3D(x-p.x, y-p.y, z-p.z); }
	
	/**
	 * Scale.
	 * 
	 * @param v
	 *            the v
	 * @return the point3 d
	 */
	public Point3D Scale (double /*Coord*/ v) { return new Point3D (x * v, y*v, z*v); }
	
	/**
	 * Gets the vector.
	 * 
	 * @return the vector
	 */
	public Vector3D getVector() 
	/*
	 * Gets the vector from this point to the center.  
	 * This should be deprecated.
	 */
	{ 
		//TODO deprecate when possible
		return new Vector3D(x,y,z);}
	
	/**
	 * Mid point.
	 * 
	 * @param endPoint
	 *            the end point
	 * @param fraction
	 *            the fraction
	 * @return the point3 d
	 */
	public Point3D midPoint(Point3D endPoint, double fraction)
	{
		return new Point3D((endPoint.x+x)*fraction, (endPoint.y+y)*fraction, (endPoint.z+z)*fraction );
	}
	
	/**
	 * Sum.
	 * 
	 * @param p
	 *            the p
	 * @return the point3 d
	 */
	public Point3D Sum(Point3D p)
	/*
	 * used to accumulate points to calucalte the center of a cluster.
	 */
	{
		return new Point3D(x+p.x, y+p.y, z+p.z); 
	}
	
	/**
	 * Translate.
	 * 
	 * @param v
	 *            the v
	 * @return the point3 d
	 */
	Point3D Translate (double /*Coord*/ v) { return new Point3D (x+v, y+v, z+v);}
	
	/**
	 * Checks if is equal.
	 * 
	 * @param p
	 *            the p
	 * @return true, if is equal
	 */
	boolean isEqual(Point3D p) { return (x == p.x) && (y == p.y) && (z == p.z); }
	
	/* (non-Javadoc)
	 * @see javax.vecmath.Tuple3d#toString()
	 */
	@Override
	public String toString()
	{
		String s = "";
		s = String.format("{%5.2f, %5.2f, %5.2f}", x, y, z);
		return s;
	}

	public String getSpacedString()
	{
		return String.format("%5.3f %5.3f %5.3f", x,y,z);
	}
}
