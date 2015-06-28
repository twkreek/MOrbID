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

import com.bobandthomas.Morbid.utils.Point3D;

// TODO: Auto-generated Javadoc
//Circle Gob - draws a circle with radius r around point p.
//Color is set through base class's methodpublic class CircleGob extends Gob {

/**
 * The Class CircleGob.
 * 
 * @author Thomas Kreek
 */
public class CircleGob extends Gob {

	/** The r. */
	public float r;

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.Gob#center()
	 */
	@Override
	public Point3D center() {
		return getPoint();
	}

	/**
	 * Instantiates a new circle gob.
	 * 
	 * @param pt
	 *            the pt
	 * @param radius
	 *            the radius
	 */
	public CircleGob(Point3D pt, float /* Coord */radius) {
		super(pt);
		r = radius;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.Gob#Type()
	 */
	@Override
	public GobType Type() {
		return GobType.Circle;
	}
};
