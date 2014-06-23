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

import java.util.HashMap;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.utils.CLoadableSet;

// TODO: Auto-generated Javadoc
/**
 * The Class GobListSet.
 * 
 * @author Thomas Kreek
 */
public class GobListSet extends CLoadableSet<GobList> {
	
	/** The map. */
	HashMap<Gadget, GobList> map = new HashMap<Gadget, GobList>();
	
	/**
	 * Instantiates a new gob list set.
	 */
	public GobListSet()
	{
		super();
	//	setUseByName(true);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#clear()
	 */
	@Override
	public void clear() {
		super.clear();
		map.clear();
		}
	
	/**
	 * Sets the rotate.
	 * 
	 * @param r
	 *            the r
	 */
	public void SetRotate(boolean r) { 
		for ( GobList gl: this)
			gl.setRotate(r);
		}
	
	/**
	 * Sets the sort.
	 * 
	 * @param s
	 *            the s
	 */
	public void SetSort(boolean s){
		
		for ( GobList gl: this)
			gl.SetSort(s);
	}
	
	/**
	 * Creates a gadget gl.
	 * 
	 * @param g
	 *            the g
	 */
	public void createGadgetGL(Gadget g)
	{
		GobList gl = new GobList();
		gl.setName(g.getName());
		gl.setLayer(g.getLayer());
		map.put(g, gl);
		add(gl);
	}
	
	/**
	 * Gets the gadget gl.
	 * 
	 * @param g
	 *            the g
	 * @return the gadget gl
	 */
	public GobList getGadgetGl(Gadget g)
	{
		return map.get(g);
	}

}
