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


// TODO: Auto-generated Javadoc
/**
 * The Class LineSegment.
 * 
 * @author Thomas Kreek
 */
public class LineSegment 
{
	
	/** The from. */
	public Point3D from;
	
	/** The to. */
	public Point3D to;

	/**
	 * Instantiates a new line segment.
	 */
	public LineSegment() {
		from = new Point3D();
		to = new Point3D();
	}
	
	/**
	 * Instantiates a new line segment.
	 * 
	 * @param p
	 *            the p
	 */
	public LineSegment(Point3D p)
	{
		from = new Point3D(0,0,0);
		to = new Point3D(p.Sub(from).Normalize());
	}
	
	/**
	 * Instantiates a new line segment.
	 * 
	 * @param v
	 *            the v
	 */
	public LineSegment (LineSegment v)
	{
		from = new Point3D(v.from);
		to = new Point3D(v.to);
	}
	
	/**
	 * Instantiates a new line segment.
	 * 
	 * @param p1
	 *            the p1
	 * @param p2
	 *            the p2
	 */
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
	/**
	 * Length.
	 * 
	 * @return the double
	 */
	public double Length() 	{ 
		return from.distance(to);
		} // acts like it's a vector
	
	/**
	 * Normalize.
	 * 
	 * @return the line segment
	 */
	public LineSegment Normalize()
	{
	 	double len = Length();
		if (len < 1.0e-10)
			return this;
	 	to.x = (to.x-from.x)/len;
	 	to.y = (to.y-from.y)/len;
	 	to.z = (to.z-from.z)/len;
	 	from.zero();
	 	return this;
	}

}
