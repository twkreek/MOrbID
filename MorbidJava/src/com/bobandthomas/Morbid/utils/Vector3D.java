package com.bobandthomas.Morbid.utils;


public class Vector3D extends Point3D {
	public Point3D from;
	public Point3D to;

	public Vector3D() {
		from = new Point3D();
		to = new Point3D();
	}
	public Vector3D(Point3D p)
	{
		from = new Point3D(0,0,0);
		to = new Point3D(p.Normalize());
	}
	public Vector3D (Vector3D v)
	{
		from = new Point3D(v.from);
		to = new Point3D(v.to);
	}
	public Vector3D(Point3D p1, Point3D p2)
	{
		from = new Point3D(p1);
		to = new Point3D(p2);
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

	
	Point3D IntersectWithSphere(Point3D center, double radius)
	{
	 	Point3D point1 = from.Sub(center);
	 	Point3D point2 = to.Sub(from);
	 	double  v = Dot(point1,point2);
	 	double disc = radius * radius - (Dot(point1, point2) - v*v);
	 	if (disc < 0)
	 		return center;
	 	double d = Math.sqrt(disc);
	 	Point3D thePoint = from.Add(point2.Scale(v - d));
	 	return thePoint; 		
	}
	public double Length() 	{ 
		return from.distance(to);
		} // acts like it's a vector
	public Vector3D Normalize()
	{
	 	double len = Length();
		if (len < 1.0e-10)
			return this;
	 	to.x = (to.x-from.x)/len;
	 	to.y = (to.y-from.y)/len;
	 	to.z = (to.z-from.z)/len;
	 	from.Zero();
	 	return this;
	}

}
