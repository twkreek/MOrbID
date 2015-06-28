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

import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class Point3DList.
 * 
 * @author Thomas Kreek
 */
public class Point3DList extends ArrayList<Point3D> {
	
	/** The bounds. */
	BoundingBox bounds;
	
	/**
	 * Gets the min.
	 * 
	 * @return the min
	 */
	public Point3D getMin() {
		return bounds.getMin();
	}
	
	/**
	 * Gets the max.
	 * 
	 * @return the max
	 */
	public Point3D getMax() {
		return bounds.getMax();
	}
	
	/**
	 * Center.
	 * 
	 * @return the point3 d
	 */
	public Point3D center() {
		return bounds.center();
	}
	
	/**
	 * Instantiates a new point3 d list.
	 */
	public Point3DList()
	{
		bounds = new BoundingBox();
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(Point3D p) {
		bounds.addPoint(p);
		return super.add(p);
	}

	/**
	 * Normal.
	 * 
	 * @return the point3 d
	 */
	Point3D Normal()
	{
		Vector3D a = get(0).Sub(get(1));;
		Vector3D b = get(2).Sub(get(1));;
	    
		Vector3D norm = a.Cross(b);
		norm = norm.Scale(1/norm.Length());
	    return norm;
	}
}
