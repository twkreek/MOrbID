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
/**
 * The Class LabelGob.
 * 
 * @author Thomas Kreek
 * 
 *         A Label is used for 2 dimensional overlay or underlay Points in
 *         quadrants will be mapped to the far edge of that quadrant points in
 *         the center will be mapped to the center of the screen
 */
public class LabelGob extends StringGob {

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.StringGob#Type()
	 */
	@Override
	public GobType Type() {
		return GobType.Label;
	}

	/**
	 * Instantiates a new label gob.
	 * 
	 * @param lbl
	 *            the lbl
	 * @param pt
	 *            the pt
	 */
	public LabelGob(String lbl, Point3D pt) {
		super(lbl, pt);
	}

}