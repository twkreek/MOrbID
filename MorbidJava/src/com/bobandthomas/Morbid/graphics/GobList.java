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


import java.util.Collection;

import com.bobandthomas.Morbid.Scene;
import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.Point3D;

// TODO: Auto-generated Javadoc
/**
 * The Class GobList contains all the graphical objects from a single gadget.
 * 
 * @author Thomas Kreek
 */
public class GobList extends CLoadableSet<Gob> {

		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.CLoadableSet#add(com.bobandthomas.Morbid.utils.CLoadableItem)
		 */
		@Override
	public boolean add(Gob arg0) {
		markDirty();
		return super.add(arg0);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableSet#addAll(java.util.Collection)
	 */
	@Override
	public boolean addAll(Collection<? extends Gob> arg0) {
		markDirty();
		return super.addAll(arg0);
	}

		/** The gadget. */
		Gadget gadget; //the gadget that generates this goblist.
		
		/** The rotate. */
		boolean rotate;
		
		/** The sort. */
		boolean sort;
		
		/** The thectm. */
		CTM thectm;
		
		/** The layer. */
		Scene.LayerPosition layer;
		
		/**
		 * Sets the rotate.
		 * 
		 * @param r
		 *            the new rotate
		 */
		public void setRotate(boolean r) { rotate = r; }
		
		/**
		 * Checks if is rotate.
		 * 
		 * @return true, if successful
		 */
		public boolean IsRotate() { return rotate; }
		
		/**
		 * Sets the sort.
		 * 
		 * @param s
		 *            the s
		 */
		public void SetSort(boolean s) { sort = s; }
		
		/**
		 * Gets the layer.
		 * 
		 * @return the layer
		 */
		public Scene.LayerPosition getLayer() {
			return layer;
		}
		
		/**
		 * Sets the layer.
		 * 
		 * @param layer
		 *            the new layer
		 */
		public void setLayer(Scene.LayerPosition layer) {
			this.layer = layer;
		}

// Convenience functions for creating gobs.



		/**
 * Gets the gadget.
 * 
 * @return the gadget
 */
public Gadget getGadget() {
			return gadget;
		}
		
		/**
		 * Sets the gadget.
		 * 
		 * @param gadget
		 *            the new gadget
		 */
		public void setGadget(Gadget gadget) {
			this.gadget = gadget;
		}
		
		/**
		 * Instantiates a new gob list.
		 */
		public GobList()
		{   
			thectm = new CTM();

			rotate = true;
			notifier.logThis(false);
		}

		/**
		 * Adds the vector.
		 * 
		 * @param from
		 *            the from
		 * @param to
		 *            the to
		 */
		void AddVector(Point3D from, Point3D to)
		{   
			GobVector vg = new GobVector(from,to);
			add( vg );
		}

		/**
		 * Adds the label.
		 * 
		 * @param s
		 *            the s
		 * @param pos
		 *            the pos
		 */
		void AddLabel(String s, Point3D pos)
		{
			LabelGob lg = new LabelGob(s, pos);
			add(lg);
		}

		/**
		 * Adds the string.
		 * 
		 * @param s
		 *            the s
		 * @param pos
		 *            the pos
		 */
		void AddString(String s, Point3D pos)
		{
			StringGob lg = new StringGob(s, pos);
			add(lg);
		}

		/**
		 * Append.
		 * 
		 * @param gl
		 *            the gl
		 * @return the gob list
		 */
		GobList Append(GobList gl)
		{
			addAll(gl);
			return this;
		}

}
