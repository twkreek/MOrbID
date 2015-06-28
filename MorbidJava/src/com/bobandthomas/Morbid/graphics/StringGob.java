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
 * The Class StringGob.
 * 
 * @author Thomas Kreek
 */
public class StringGob extends Gob {

		/** The scale. */
		double scale=1.0;
		
		/**
		 * Instantiates a new string gob.
		 * 
		 * @param s1
		 *            the s1
		 * @param p1
		 *            the p1
		 */
		public StringGob(String s1, Point3D p1)
		{
			super(p1);
			setName(s1);
		}
		
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.graphics.Gob#Type()
		 */
		@Override
		public GobType Type() {return GobType.String;}
		
		/**
		 * Gets the scale.
		 * 
		 * @return the scale
		 */
		public double getScale() {
			return scale;
		}
		
		/**
		 * Sets the scale.
		 * 
		 * @param scale
		 *            the new scale
		 */
		public void setScale(double scale) {
			this.scale = scale;
		}

};


