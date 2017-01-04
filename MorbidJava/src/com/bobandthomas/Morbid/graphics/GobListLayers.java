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

import com.bobandthomas.Morbid.Scene.LayerPosition;

// TODO: Auto-generated Javadoc
/**
 * The Class GobListLayers.
 * 
 * @author Thomas Kreek
 */
public class GobListLayers extends ArrayList<GobListSet> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 4414636376801414307L;
	
	/**
	 * Instantiates a new gob list layers.
	 */
	
	public GobListLayers(){
		
	}
	
	/**
	 * Gets the.
	 * 
	 * @param lp
	 *            the lp
	 * @return the gob list set
	 */
	public GobListSet get(LayerPosition lp)
	{
		return this.get(lp.ordinal());
	}
	
	/**
	 * Put.
	 * 
	 * @param lp
	 *            the lp
	 * @param gl
	 *            the gl
	 */
	public void put(LayerPosition lp, GobListSet gl) {
		this.add(lp.ordinal(), gl);
	}
	

}
