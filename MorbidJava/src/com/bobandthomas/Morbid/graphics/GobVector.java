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

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.LineSegment;
import com.bobandthomas.Morbid.utils.Vector3D;


// TODO: Auto-generated Javadoc
//Vector Gob - start point and end point and color.

/**
 * The Class GobVector.
 * 
 * @author Thomas Kreek
 */
public class GobVector extends Gob {

		/** The End color. */
		public ColorQuad EndColor;
		
		/** The end point. */
		private Vertex endPoint;
		

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.Gob#Type()
		 */
		@Override
		public
		GobType Type() {return GobType.Vector;}
		
		/**
		 * Instantiates a new gob vector.
		 * 
		 * @param vec
		 *            the vec
		 */
		GobVector(LineSegment vec)
		{
			setPoint(new Vertex(vec.from));
			endPoint = new Vertex(vec.to);
		}
		
		/**
		 * Instantiates a new gob vector.
		 * 
		 * @param v1
		 *            the v1
		 * @param v2
		 *            the v2
		 */
		GobVector(Vertex v1, Vertex v2)
		{
			setPoint(v1);
			endPoint = v2;
		}
		
		/**
		 * Gets the end point.
		 * 
		 * @return the end point
		 */
		public Vertex getEndPoint()
		{
			return endPoint;
		}
		
		/**
		 * Sets the end point.
		 * 
		 * @param v
		 *            the new end point
		 */
		public void setEndPoint(Vertex v)
		{
			endPoint = new Vertex(v);
		}
		
		/**
		 * Gets the unit vector.
		 * 
		 * @return the unit vector
		 */
		public Vector3D getUnitVector()
		{
			Vector3D p = getPoint().Sub(endPoint);
			p.Normalize();
			return p;
		}
		
		/**
		 * Instantiates a new gob vector.
		 * 
		 * @param start
		 *            the start
		 * @param end
		 *            the end
		 */
		public GobVector(Point3D  start, Point3D  end)
		{
			super(start);
			endPoint= new Vertex(end);
		}
		
		/**
		 * Gets the center.
		 * 
		 * @return the center
		 */
		public Point3D  getCenter()
		{
			return new Vertex(getPoint().midPoint(endPoint, 0.5));
		}
		
		/**
		 * Gets the length.
		 * 
		 * @return the length
		 */
		public float getLength() {
			return (float) getPoint().distance(endPoint);
		}


};



