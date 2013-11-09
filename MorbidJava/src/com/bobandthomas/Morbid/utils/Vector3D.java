package com.bobandthomas.Morbid.utils;

import toxi.geom.Vec3D;

public class Vector3D extends Point3D {

	@Override
	public Vector3D Add(Point3D p) {
		// TODO Auto-generated method stub
		return (Vector3D) super.Add(p);
	}

	@Override
	public Vector3D Sub(Point3D p) {
		// TODO Auto-generated method stub
		return (Vector3D) super.Sub(p);
	}

	@Override
	public Vector3D Scale(double v) {
		// TODO Auto-generated method stub
		return (Vector3D) super.Scale(v);
	}

	@Override
	Vector3D Mult(Point3D p) {
		// TODO Auto-generated method stub
		return (Vector3D) super.Mult(p);
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
	}

	public Vector3D(Vec3D vec) {
		super(vec);
	}
	static double Dot(Point3D p, Point3D p2)
	{
		double result;
		
		result = p2.x*p.x + p2.y*p.y + p2.z*p.z;
	 	return result;
	}

    public double angle( Vector3D v ) {
        double length1 = Length();
        double length2 = v.Length();
        if ( length1 == 0 || length2 == 0 ) return 0;

        double a = Math.acos( Dot( v ) / ( length1 * length2 ) );
        return ( a / Math.PI ) * 180.0;
    }

}
