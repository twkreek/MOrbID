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
// TODO: Auto-generated Javadoc

/**
 * The Enum StaticColorQuad.
 * 
 * @author Thomas Kreek
 */
public enum StaticColorQuad {
	
	/** The Black. */
	Black (0,0,0),
	
	/** The White. */
	White (255,255,255),
	
	/** The Lite gray. */
	LiteGray (200,200,200),
	
	/** The Red. */
	Red (255,0,0),
	
	/** The Blue. */
	Blue (0,0,255),
	
	/** The Green. */
	Green (0,255,0),
	
	/** The Cyan. */
	Cyan (0,255,255),
	
	/** The Magenta. */
	Magenta (255,0,255),
	
	/** The Yellow. */
	Yellow (255,255,0),
	
	/** The Dark gray. */
	DarkGray(50,50,50);
	
	/** The r. */
	private final int r;
	
	/** The g. */
	private final int g;
	
	/** The b. */
	private final int b;
	
	/**
	 * Instantiates a new static color quad.
	 * 
	 * @param r
	 *            the r
	 * @param g
	 *            the g
	 * @param b
	 *            the b
	 */
	StaticColorQuad(int r, int g, int b)
	{
		this.r=r; this.g=g; this.b=b;
	}
	
	/**
	 * R.
	 * 
	 * @return the int
	 */
	public int r() { return r; }
	
	/**
	 * G.
	 * 
	 * @return the int
	 */
	public int g() { return g; }
	
	/**
	 * B.
	 * 
	 * @return the int
	 */
	public int b() { return b; }
	
	/**
	 * Cq.
	 * 
	 * @return the color quad
	 */
	public ColorQuad cq() { return new ColorQuad(r,g,b); }

}
