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

import toxi.geom.Vec3D;

import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class Vertex.
 * 
 * @author Thomas Kreek
 */
public class Vertex extends Point3D {
	
	/** The normal. */
	Vector3D normal;
	
	/** The color. */
	ColorQuad color;
	
	/** The value. */
	public double value; 
	
	/**
	 * Sets the point.
	 * 
	 * @param p
	 *            the new point
	 */
	public void setPoint(Point3D p)
	{
		x=p.x;
		y=p.y;
		z=p.z;
	}
	
	/**
	 * Gets the normal.
	 * 
	 * @return the normal
	 */
	public Vector3D getNormal() {
		return normal;
	}

	/**
	 * Sets the normal.
	 * 
	 * @param normal
	 *            the new normal
	 */
	public void setNormal(Vector3D normal) {
		this.normal = normal;
	}
	
	/**
	 * Invert normal.
	 * 
	 * @return the vertex
	 */
	public Vertex invertNormal()
	{
		normal = normal.invert();
		return this;
	}

	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public ColorQuad getColor() {
		return color;
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(ColorQuad color) {
		this.color = color;
	}
	
	/**
	 * Checks if it has normal.
	 * 
	 * @return true, if successful
	 */
	public boolean hasNormal()
	{
		return normal!= null;
	}
	
	/**
	 * Checks if it has color.
	 * 
	 * @return true, if successful
	 */
	public boolean hasColor()
	{
		return color!=null;
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param z1
	 *            the z1
	 */
	public Vertex(double x1, double y1, double z1) {
		super(x1, y1, z1);
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param x1
	 *            the x1
	 * @param y1
	 *            the y1
	 * @param z1
	 *            the z1
	 */
	public Vertex(int x1, int y1, int z1) {
		super(x1, y1, z1);
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param n1
	 *            the n1
	 */
	public Vertex(Point3D n1) {
		super(n1);
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param n1
	 *            the n1
	 * @param value
	 *            the value
	 */
	public Vertex(Point3D n1, double value) {
		super(n1);
		this.value = value;
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param n1
	 *            the n1
	 * @param normal
	 *            the normal
	 */
	public Vertex(Point3D n1, Vector3D normal) {
		super(n1);
		this.normal = normal;
	}
	
	/**
	 * Instantiates a new vertex.
	 * 
	 * @param p
	 *            the p
	 * @param cq
	 *            the cq
	 * @param n
	 *            the n
	 */
	public Vertex(Point3D p, ColorQuad cq, Vector3D n)
	{
		super(p);
		this.normal = n;
		this.color = cq;
	
	}
	
	/**
	 * Instantiates a new vertex.
	 * 
	 * @param v
	 *            the v
	 */
	public Vertex(toxi.geom.mesh.Vertex v)
	{
		super(new Point3D(v));
		normal = new Vector3D(v).Normalize().invert();
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param n1
	 *            the n1
	 * @param cq
	 *            the cq
	 * @param value
	 *            the value
	 */
	public Vertex(Point3D n1, ColorQuad cq, double value) {
		super(n1);
		this.value = value;
		this.color = cq;
	}

	/**
	 * Instantiates a new vertex.
	 */
	public Vertex() {
	}

	/**
	 * Instantiates a new vertex.
	 * 
	 * @param vec
	 *            the vec
	 */
	public Vertex(Vec3D vec) {
		super(vec);
	}

	

}
