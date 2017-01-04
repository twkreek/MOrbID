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

import javax.vecmath.Vector3f;

import toxi.geom.Quaternion;
import toxi.geom.ReadonlyVec3D;
import toxi.geom.Vec3D;

// TODO: Auto-generated Javadoc
/**
 * The Class Vector3D.
 * 
 * @author Thomas Kreek
 */
public class Vector3D extends Point3D {

	/**
	 * Mult.
	 * 
	 * @param p
	 *            the p
	 * @return the point3 d
	 */
	public Point3D Mult(Point3D p) {
		return new Vector3D(p.x * x, p.y * y, p.z * z);
	}
	
	static Vector3D X () {return new Vector3D(1,0,0);}
	static Vector3D Y () {return new Vector3D(0,1,0);}
	static Vector3D Z () {return new Vector3D(0,0,1);}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.Point3D#Add(com.bobandthomas.Morbid.utils.Vector3D)
	 */
	public Vector3D Add(Vector3D v) {
		return new Vector3D(v.x + x, v.y + y, v.z + z);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.Point3D#Sub(com.bobandthomas.Morbid.utils.Vector3D)
	 */
	public Vector3D Sub(Vector3D v) {
		return new Vector3D(x - v.x, y - v.y, z - v.z);
	}

	/**
	 * Adds the.
	 * 
	 * @param p
	 *            the p
	 * @return the point3 d
	 */
	public Point3D Add(Point3D p) {
		return new Point3D(x + p.x, y + p.y, z + p.z);
	}

	/**
	 * Instantiates a new vector3 d.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param z1
	 *            the z1
	 */
	public Vector3D(double x1, double y1, double z1) {
		super(x1, y1, z1);
	}

	/**
	 * Instantiates a new vector3 d.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param z1
	 *            the z1
	 */
	public Vector3D(int x1, int y1, int z1) {
		super(x1, y1, z1);
	}

	/**
	 * Instantiates a new vector3 d.
	 * 
	 * @param n1
	 *            the n1
	 */
	public Vector3D(Point3D n1) {
		super(n1);
	}

	/**
	 * Instantiates a new vector3 d.
	 */
	public Vector3D() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.Point3D#Scale(double)
	 */
	public Vector3D Scale(double factor) {
		return new Vector3D(x * factor, y * factor, z * factor);
	}

	/**
	 * Instantiates a new vector3 d.
	 * 
	 * @param vec
	 *            the vec
	 */
	public Vector3D(Vec3D vec) {
		super(vec);
	}

	/**
	 * Dot.
	 * 
	 * @param p
	 *            the p
	 * @param p2
	 *            the p2
	 * @return the double
	 */
	static double Dot(Vector3D p, Vector3D p2) {
		double result;

		result = p2.x * p.x + p2.y * p.y + p2.z * p.z;
		return result;
	}

	/**
	 * Cross.
	 * 
	 * @param p
	 *            the p
	 * @return the vector3 d
	 */
	public Vector3D Cross(Vector3D p) {
		double /* Coord */x1, y1, z1;

		x1 = y * p.z - z * p.y;
		y1 = z * p.x - x * p.z;
		z1 = x * p.y - y * p.x;
		return new Vector3D(x1, y1, z1);// Cross Product
	}

	/**
	 * Dot.
	 * 
	 * @param p
	 *            the p
	 * @return the double
	 */
	public double /* Coord */Dot(Vector3D p) {
		// This is the dot product operator.
		double /* Coord */result;

		result = x * p.x + y * p.y + z * p.z;
		return result;
	}

	/**
	 * Angle.
	 * 
	 * @param v
	 *            the v
	 * @return the double
	 */
	public double angle(Vector3D v) {
		double length1 = Length();
		double length2 = v.Length();
		if (length1 == 0 || length2 == 0)
			return 0;

		double a = Math.acos(Dot(v) / (length1 * length2));
		return (a / Math.PI) * 180.0;
	}

	/**
	 * Normalize.
	 * 
	 * @return the vector3 d
	 */
	public Vector3D Normalize() {
		double len = Math.sqrt(x * x + y * y + z * z);
		if (len < 1.0e-10)
			return this;
		x = (x / len);
		y = (y / len);
		z = (z / len);
		return this;
	}

	/**
	 * Clamp.
	 * 
	 * @param c
	 *            the c
	 */
	void Clamp(double /* Coord */c) {
		x = Math.min(x, c);
		y = Math.min(y, c);
		z = Math.min(z, c);
	}

	/**
	 * Length.
	 * 
	 * @return the double
	 */
	public double /* Coord */Length() {
		return Math.sqrt(x * x + y * y + z * z);
	} // acts like it's a vector

	/**
	 * Invert.
	 * 
	 * @return the vector3 d
	 */
	public Vector3D invert() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	
	/**
	 * Interpolate.
	 * 
	 * @param endPoint
	 *            the end point
	 * @param fraction
	 *            the fraction
	 * @return the vector3 d
	 */
	public Vector3D Interpolate(Vector3D endPoint, double fraction)
	{
		return new Vector3D((endPoint.x+x)*fraction, (endPoint.y+y)*fraction, (endPoint.z+z)*fraction );
		
	}

	/**
	 * Gets the vec3f.
	 * 
	 * @return the vec3f
	 */
	public Vector3f getVec3f() {
		Vector3f v3f = new Vector3f((float) x, (float) y, (float) z);
		return v3f;
	}
	
	/*
	 * returns the quaternion that rotates the vector 'start' to align with the current vector.
	 */
	Vector4D rotateTo(Vector3D start)
	{
			// creates the transform matrix to rotate the object to vector in g
			Vector3D  v = Normalize();
			Vec3D unitV = new Vec3D();
			unitV.set((float) v.x, (float) v.y, (float) v.z);
			Quaternion q = Quaternion.getAlignmentQuat(unitV, (ReadonlyVec3D) start.Normalize().getVec3f());
			return new Vector4D(q);
	}
}
