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
package com.bobandthomas.Morbid.graphics;


import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.Point3D;



// TODO: Auto-generated Javadoc
/**
 * The Class VertexList.
 * 
 * @author Thomas Kreek
 */
public class VertexList extends ArrayList<Vertex> {
	
	/** The bounds. */
	BoundingBox bounds;
	
	/** The has normals. */
	boolean hasNormals;
	
	/** The has colors. */
	boolean hasColors;
	
	/**
	 * Checks if it has normals.
	 * 
	 * @return true, if successful
	 */
	public boolean hasNormals() {
		return hasNormals;
	}
	
	/**
	 * Checks if it has colors.
	 * 
	 * @return true, if successful
	 */
	public boolean hasColors() {
		return hasColors;
	}
	
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
	 * Gets the center.
	 * 
	 * @return the center
	 */
	public Point3D getCenter() {
		return bounds.center();
	}
	
	/**
	 * Instantiates a new vertex list.
	 */
	public VertexList()
	{
		bounds = new BoundingBox();
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(Vertex p) {
		if (p.hasColor()) hasColors = true;
		if (p.hasNormal()) hasNormals = true;
		bounds.addPoint(p);
		return super.add(p);
	}
	

}
