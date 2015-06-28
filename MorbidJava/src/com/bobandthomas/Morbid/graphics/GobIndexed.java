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

import com.bobandthomas.Morbid.utils.*;

// TODO: Auto-generated Javadoc
/**
 * The Class GobIndexed.
 * 
 * @author Thomas Kreek
 */
public class GobIndexed extends GobPoly {

	/** The index table. */
	ArrayList<Integer> indexTable;

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.GobPoly#Type()
	 */
	@Override
	public
	GobType Type() {
		return GobType.Indexed;
	}

	/**
	 * Instantiates a new gob indexed.
	 */
	GobIndexed() {
	}

	/**
	 * Adds the index.
	 * 
	 * @param i
	 *            the i
	 */
	void AddIndex(int i) {
		indexTable.add(i);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.graphics.GobPoly#center()
	 */
	@Override
	public
	Point3D center() {
		return super.center();
	}
};
