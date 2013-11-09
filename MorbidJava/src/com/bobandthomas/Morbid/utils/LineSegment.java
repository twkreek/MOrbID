package com.bobandthomas.Morbid.utils;


public class LineSegment 
{
	public Point3D from;
	public Point3D to;

	public LineSegment() {
		from = new Point3D();
		to = new Point3D();
	}
	public LineSegment(Point3D p)
	{
		from = new Point3D(0,0,0);
		to = new Point3D(p.Normalize());
	}
	public LineSegment (LineSegment v)
	{
		from = new Point3D(v.from);
		to = new Point3D(v.to);
	}
	public LineSegment(Point3D p1, Point3D p2)
	{
		from = new Point3D(p1);
		to = new Point3D(p2);
	}

	
	/*Point3D IntersectWithSphere(Point3D center, double radius)
	{
	 	Point3D point1 = from.Sub(center);
	 	Point3D point2 = to.Sub(from);
	 	double  v = Point3D.Dot(point1,point2);
	 	double disc = radius * radius - (Dot(point1, point2) - v*v);
	 	if (disc < 0)
	 		return center;
	 	double d = Math.sqrt(disc);
	 	Point3D thePoint = from.Add(point2.Scale(v - d));
	 	return thePoint; 		
	} */
	public double Length() 	{ 
		return from.distance(to);
		} // acts like it's a vector
	public LineSegment Normalize()
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
