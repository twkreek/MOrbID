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
 * The Class BoundingBox.
 * 
 * @author Thomas Kreek The Class BoxType. maintains the 3 dimensional bounding
 *         box of all the points that were added to it.
 */
public class BoundingBox {
	
	/** The min. */
	public Point3D min;
	
	/** The max. */
	public Point3D max;
	
	/** The isset. */
	boolean isset;

	/**
	 * Gets the min.
	 *
	 * @return the min
	 */
	public Point3D getMin() {
		return min;
	}
	
	/**
	 * Sets the min.
	 *
	 * @param min the new min
	 */
	public void setMin(Point3D min) {
		this.min = min;
	}
	
	/**
	 * Gets the max.
	 *
	 * @return the max
	 */
	public Point3D getMax() {
		return max;
	}
	
	/**
	 * Sets the max.
	 *
	 * @param max the new max
	 */
	public void setMax(Point3D max) {
		this.max = max;
	}
	
	/**
	 * Instantiates a new Bounding box.
	 */
	public BoundingBox()
	{
		reset();
	}
	
	/**
	 * Copy constructor Instantiates a new bounding box.
	 *
	 * @param bt CoxType to copy from.
	 */
	public BoundingBox(BoundingBox bt)
	{
		isset = false;
		min = new Point3D(bt.min);
		max = new Point3D(bt.max);
		isset = true;
	}
	
	/**
	 * Instantiates a new bounding box.
	 *
	 * @param minx the minx
	 * @param miny the miny
	 * @param maxx the maxx
	 * @param maxy the maxy
	 */
	public BoundingBox(int minx, int miny, int maxx, int maxy) 
	{
		isset = true; 
		min = new Point3D( minx, miny, 0.0f);
		max = new Point3D( maxx, maxy, 0.0f); 
	}

	/**
	 * Unit box.
	 *
	 * @return unit box
	 */
	public static BoundingBox UnitBox()
	{
		BoundingBox bt = new BoundingBox();
		bt.isset = true;
		bt.min = new Point3D(-1,-1,-1);
		bt.max = new Point3D(1,1,1);
		return bt;
	}
	
	/**
	 * Scale.
	 *
	 * @param s the scale factor
	 * @return the scaled bounding box
	 */
	public BoundingBox Scale(double s) {
		BoundingBox box = new BoundingBox(this);
		box.min.scale(s);
		box.max.Scale(s);
		return box;
	}
	
	/**
	 * Contains.
	 *
	 * @param boundingBox the intersecting bounding box
	 * @return true, if contained
	 */
	boolean Contains(BoundingBox boundingBox)
	{
		if (min.x > boundingBox.min.x)
			return false;
		if (min.y > boundingBox.min.y)
			return false;
		if (min.z > boundingBox.min.z)
			return false;
		if (max.x < boundingBox.max.x)
			return false;
		if (max.y < boundingBox.max.y)
			return false;
		if (max.z < boundingBox.max.z)
			return false;
		return true;
	}
	
	/**
	 * Contains.
	 * 
	 * @param pt
	 *            the point
	 * @return true, if point is inside bounding box
	 */
	boolean Contains(Point3D pt)
	{
		if (min.x > pt.x || max.x < pt.x)
			return false;
		if (min.y > pt.y || max.y < pt.y)
			return false;
		if (min.z > pt.z || max.z < pt.z)
			return false;
		return true;

	}

	/**
	 * Size.
	 *
	 * @return the point3 d
	 */
	public Point3D size() { return max.Sub(min); }
	
	/**
	 * Center.
	 *
	 * @return the point3 d
	 */
	public Point3D center() 
	{
		return max.midPoint(min, 0.5);
	}
	
	/**
	 * Cube.
	 *
	 * @return the bounding box with squared bounds 
	 * it makes all edges equal to the longest
	 */
	public BoundingBox cube()
	{
		BoundingBox bounds= new BoundingBox();
		Point3D size = size();
		double /*Coord*/ isize = size.x;
		if (isize < size.y) isize = size.y;
		if (isize < size.z) isize = size.z;
		
		bounds.min.x = bounds.min.y = bounds.min.z = -isize/2;
		bounds.max.x = bounds.max.y = bounds.max.z = isize/2;

		return bounds;

	}


	/**
	 * Adds a new point to the bounds box.
	 * 
	 * @param a
	 *            the new point
	 */
	public void addPoint(Point3D a)
	{
		if (!isset)
		{
			min = new Point3D(a);
			max = new Point3D(a);
			isset = true;
			return;
		}
		if (min.x > a.x ) 
			min.x = a.x ;
	    if (min.y > a.y ) 
			min.y = a.y ;
	    if (min.z > a.z ) 
			min.z = a.z ;

	    if (max.x < a.x ) 
			max.x = a.x ;
	    if (max.y < a.y ) 
			max.y = a.y ;
	    if (max.z < a.z ) 
			max.z = a.z ;

	}

	/**
	 * Reset bounds.
	 */
	public void reset()
	{
		max = new Point3D(-1000.0f, -1000.0f, -1000.0f);
		min = new Point3D( 1000.0f,  1000.0f,  1000.0f);
		isset = false;
	}

	/**
	 * adds a Sphere to the bounding box bounds are epxanded by the radius of
	 * the sphere.
	 * 
	 * @param position
	 *            the position
	 * @param r
	 *            the radius
	 */
	public void addSphere(Point3D position, double r)
	{
		
		Point3D rad = new Point3D(r,r,r);
		rad.add(position);
		addPoint(rad);
		rad = new Point3D(-r,-r,-r);
		rad.add(position);
		addPoint(rad);
	}

	/**
	 * Gets the width.
	 * 
	 * @return the width
	 */
	public double getWidth() {
		return max.x -min.x;
	}
	
	/**
	 * Gets the height.
	 * 
	 * @return the height
	 */
	public double getHeight() {
		return max.y -min.y;
	}
	
}
