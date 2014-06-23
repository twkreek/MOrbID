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
 * The Class ColorQuadPalette.
 * 
 * @author Thomas Kreek
 */
public class ColorQuadPalette extends ArrayList<ColorQuad> {

	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	@Override
	public boolean add(ColorQuad e) {
		// TODO Auto-generated method stub
		return super.add(e);
	}

	/**
	 * Instantiates a new color quad palette.
	 */
	public ColorQuadPalette() {
		super();
	}
	
	/**
	 * Sets the pastel palette.
	 * 
	 * @param size
	 *            the new pastel palette
	 */
	public void setPastelPalette(int size)
	{
			for (int k = 0; k < size; k++) {
				int i =(int)( Math.random()*100);
				ColorQuad cq = new ColorQuad((i % 7) / 7.1, (i % 3 + 1) / 4.0,
						(i % 4) / 3.1);
				add(cq);
			
		}
	}

}
