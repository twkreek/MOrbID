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

import com.bobandthomas.Morbid.utils.*;



// TODO: Auto-generated Javadoc
/**
 *  Graphical OBject 
 *  The base class for all graphics primitives.
 *  supplies CLoadableItem functions of name and ID and parenting
 *  The base GOB has a center point and a start point.
 * 
 * @author Thomas Kreek
 */
public abstract class Gob extends CLoadableItem {

	
	/** The Start point. */
	private Vertex point = new Vertex();
	/** LOD - level of detail
	 * helps guide optimizations.  log of number of atoms.  scale spheres accordingly.;
	 * 
	 */
	double LOD = 1;	

	/** The material. */
	private Material material;

	/**
	 * Instantiates a new gob.
	 */
	public Gob() {
	}
	
	/**
	 * Instantiates a new gob.
	 * 
	 * @param point
	 *            the center point
	 */
	public Gob(Point3D point) {
		setPoint(new Vertex(point));
	}
	
	/**
	 * Instantiates a new gob.
	 * 
	 * @param vertex
	 *            the vertex
	 */
	public Gob(Vertex vertex)
	{
		setPoint(new Vertex(vertex));
	}

	/**
	 * Center.
	 * 
	 * @return the point3 d
	 */
	public Point3D center() {
		return new Point3D(getPoint());
	}


	/**
	 * Intersects. (NotImplelmented)
	 * 
	 * @param vector
	 *            the vector
	 * @return the point3 d list
	 */
	Point3DList Intersects(LineSegment vector) {
		return null;
	}

	/**
	 * Type.
	 * 
	 * @return the gob type derived classes should override this 
	 */
	abstract public GobType Type();

	
	/**
	 * Gets the material.
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * Sets the material.
	 * 
	 * @param material
	 *            the new material
	 */
	public void setMaterial(Material material) {
		this.material = material;
		setColor(new ColorQuad(material.getColor()));
	}
	
	/**
	 * Gets the level of detail (log of number of atoms).
	 * 
	 * @return the lod
	 */
	public double getLOD() {
		return LOD;
	}
	
	/**
	 * Sets the level of detail (log of number of atoms).
	 * 
	 * @param lOD
	 *            the new lod
	 */
	public void setLOD(double lOD) {
		LOD = lOD;
	}

	/**
	 * Gets the color.
	 * 
	 * @return the color
	 */
	public ColorQuad getColor() {
		return getPoint().getColor();
	}

	/**
	 * Sets the color.
	 * 
	 * @param color
	 *            the new color
	 */
	public void setColor(ColorQuad color) {
		getPoint().setColor(color);
	}

	/**
	 * Gets the Start point.
	 * 
	 * @return the Start point
	 */
	public Vertex getPoint() {
		return point;
	}

	/**
	 * Sets the Start point.
	 * 
	 * @param startPoint
	 *            the new Start point
	 */
	void setPoint(Vertex startPoint) {
		this.point = startPoint;
	}
	
	/**
	 * Sets the Start point.
	 * 
	 * @param startPoint
	 *            the new Start point
	 */
	void setPoint(Point3D startPoint)
	{
		this.point = new Vertex(startPoint);
	}

};
