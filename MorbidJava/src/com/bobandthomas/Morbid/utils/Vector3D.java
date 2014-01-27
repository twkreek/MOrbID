package com.bobandthomas.Morbid.utils;

import javax.vecmath.Vector3f;

import toxi.geom.Vec3D;

public class Vector3D extends Point3D {

	public Point3D Mult(Point3D p) {
		return new Vector3D(p.x * x, p.y * y, p.z * z);
	}

	public Vector3D Add(Vector3D v) {
		return new Vector3D(v.x + x, v.y + y, v.z + z);
	}

	public Vector3D Sub(Vector3D v) {
		return new Vector3D(x - v.x, y - v.y, z - v.z);
	}

	public Point3D Add(Point3D p) {
		return new Point3D(x + p.x, y + p.y, z + p.z);
	}

	public Vector3D(double x1, double y1, double z1) {
		super(x1, y1, z1);
	}

	public Vector3D(int x1, int y1, int z1) {
		super(x1, y1, z1);
	}

	public Vector3D(Point3D n1) {
		super(n1);
	}

	public Vector3D() {
		super();
	}

	public Vector3D Scale(double factor) {
		return new Vector3D(x * factor, y * factor, z * factor);
	}

	public Vector3D(Vec3D vec) {
		super(vec);
	}

	static double Dot(Vector3D p, Vector3D p2) {
		double result;

		result = p2.x * p.x + p2.y * p.y + p2.z * p.z;
		return result;
	}

	public Vector3D Cross(Vector3D p) {
		double /* Coord */x1, y1, z1;

		x1 = y * p.z - z * p.y;
		y1 = z * p.x - x * p.z;
		z1 = x * p.y - y * p.x;
		return new Vector3D(x1, y1, z1);// Cross Product
	}

	public double /* Coord */Dot(Vector3D p) {
		// This is the dot product operator.
		double /* Coord */result;

		result = x * p.x + y * p.y + z * p.z;
		return result;
	}

	public double angle(Vector3D v) {
		double length1 = Length();
		double length2 = v.Length();
		if (length1 == 0 || length2 == 0)
			return 0;

		double a = Math.acos(Dot(v) / (length1 * length2));
		return (a / Math.PI) * 180.0;
	}

	public Vector3D Normalize() {
		double len = Math.sqrt(x * x + y * y + z * z);
		if (len < 1.0e-10)
			return this;
		x = (x / len);
		y = (y / len);
		z = (z / len);
		return this;
	}

	void Clamp(double /* Coord */c) {
		x = Math.min(x, c);
		y = Math.min(y, c);
		z = Math.min(z, c);
	}

	public double /* Coord */Length() {
		return Math.sqrt(x * x + y * y + z * z);
	} // acts like it's a vector

	public Vector3D invert() {
		x = -x;
		y = -y;
		z = -z;
		return this;
	}
	public Vector3D Interpolate(Vector3D endPoint, double fraction)
	{
		return new Vector3D((endPoint.x+x)*fraction, (endPoint.y+y)*fraction, (endPoint.z+z)*fraction );
		
	}

	public Vector3f getVec3f() {
		Vector3f v3f = new Vector3f((float) x, (float) y, (float) z);
		return v3f;
	}
}
