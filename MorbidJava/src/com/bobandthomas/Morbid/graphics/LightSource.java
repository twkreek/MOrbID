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

import com.bobandthomas.Morbid.utils.*;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.ColorQuad;
import javax.media.j3d.Light;


// TODO: Auto-generated Javadoc
/**
 * The Class LightSource.
 * 
 * @author Thomas Kreek
 */
public class LightSource extends CLoadableItem {

		/** The color. */
		public ColorQuad color;
		
		/** The ambient. */
		public float ambient;// ambient fraction of color that is the ambient light produced from this source
		
		/** The normal. */
		Vector3D normal; // the normalized position for normals.
		
		/** The location. */
		private Point3D location;// the unnormalized position for distance;
		
		/** The use falloff. */
		boolean useFalloff;// whether to use distance critereon in calculating light intensity.
		
		/** The falloff constant. */
		float falloffConstant;// k factor in light intensity falloff.
		
		/** The rendered light. */
		Light renderedLight;
		
		/**
		 * Gets the rendered light.
		 * 
		 * @return the rendered light
		 */
		public Light getRenderedLight() {
			return renderedLight;
		}
		
		/**
		 * Sets the rendered light.
		 * 
		 * @param renderedLight
		 *            the new rendered light
		 */
		public void setRenderedLight(Light renderedLight) {
			this.renderedLight = renderedLight;
		}
		
		/**
		 * Instantiates a new light source.
		 */
		LightSource()
		{
			Init();
		}
		
		/**
		 * Instantiates a new light source.
		 * 
		 * @param cq
		 *            the cq
		 */
		public LightSource(ColorQuad cq)
		{
			Init();
			color = cq;
		}
		
		/**
		 * Instantiates a new light source.
		 * 
		 * @param cq
		 *            the cq
		 * @param p
		 *            the p
		 */
		public LightSource(ColorQuad cq, Point3D p )
		{
			Init();
			color = cq;
			normal = p.getVector().Normalize();
			location = p;
		}

		/**
		 * Can edit.
		 * 
		 * @return true, if successful
		 */
		boolean CanEdit() {return true;}
		
		/**
		 * Initializes the.
		 */
		private void Init()
		{
			color = StaticColorQuad.White.cq();
			ambient = 0.1f;
			normal = new Vector3D( 1, 0, 0);
			location = normal;
		}
		
		/**
		 * Gets the location.
		 * 
		 * @return the location
		 */
		public Point3D getLocation() {
			return location;
		}
		
		/**
		 * Sets the location.
		 * 
		 * @param location
		 *            the new location
		 */
		public void setLocation(Point3D location) {
			this.location = location;
			normal = location.getVector().Normalize(); 
		}
		
		/**
		 * Gets the normal.
		 * 
		 * @return the normal
		 */
		public Vector3D getNormal()
		{
			return normal;
		}
	};
