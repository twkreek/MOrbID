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
package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.utils.CLoadableSet;

// TODO: Auto-generated Javadoc
/**
 * The Class GadgetList.
 * 
 * @author Thomas Kreek
 */
public class GadgetList extends CLoadableSet<Gadget>{
	
	/** The scene. */
	Scene scene;
	
	/**
	 * Instantiates a new gadget list.
	 */
	GadgetList()
	{
	}
	
	/**
	 * Instantiates a new gadget list.
	 * 
	 * @param sc
	 *            the sc
	 */
	GadgetList(Scene sc)
	{
		scene = sc;
	}
	
	/**
	 * Gets the scene.
	 * 
	 * @return the scene
	 */
	Scene GetScene() { return scene; }

	

	/**
	 * Draw.
	 * 
	 * @param gobListSet
	 *            the gob list set
	 * @param lp
	 *            the lp
	 */
	void Draw(GobListSet gobListSet, LayerPosition lp)
	{
		for (Gadget pGadget: this)
		{
			if ((pGadget != null) && (pGadget.getLayer() == lp) && pGadget.isDirty())
			{
					pGadget.DrawGadget(gobListSet.getGadgetGl(pGadget)); //Gadget handles invisibility.
			}
		} 
	}
	
	/**
	 * Invalidate all.
	 */
	void InvalidateAll()
	{
		for  (Gadget g: this)
		{
			g.markDirty();
		}
	 
	}

}
