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

import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.StaticColorQuad;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class LightingModel.
 * 
 * @author Thomas Kreek
 */
public class LightingModel extends CLoadableItem {

		/** The Do lighting. */
		private boolean DoLighting;
		
		/** The Do scatter alpha. */
		private boolean DoScatterAlpha;;
		
		/** The Do specularity. */
		private boolean DoSpecularity;
		
		/** The Do diffuse. */
		private boolean  DoDiffuse;
		
		/** The Light front back. */
		boolean  LightFrontBack;
		
		/** The Do depth cue. */
		private boolean  DoDepthCue;
		
		/** The Background. */
		ColorQuad   Background;
		
		/** The Depth cue min. */
		float  DepthCueMin;
		
		/** The Depth cue rate. */
		float  DepthCueRate;
		
		/** The Bounding box. */
		private BoundingBox   BoundingBox;
		
		/**
		 * Depth cue.
		 * 
		 * @param p
		 *            the p
		 * @param cq
		 *            the cq
		 * @return the color quad
		 */
		public ColorQuad DepthCue(Point3D p, ColorQuad cq) { if (isDoDepthCue()) return CalcDepthCue(p, cq); else return cq; }

		/**
		 * Instantiates a new lighting model.
		 */
		public LightingModel()
		{
			setDoLighting(true);
			setDoScatterAlpha(true);
			setDoSpecularity(true);
			setDoDiffuse(true);
			LightFrontBack = false;
			setDoDepthCue(false);
			DepthCueMin = 0.1f;
			DepthCueRate  = 1.00f;
		}
		
		/**
		 * Calc depth cue.
		 * 
		 * @param pos
		 *            the pos
		 * @param cq
		 *            the cq
		 * @return the color quad
		 */
		ColorQuad CalcDepthCue(Point3D pos, ColorQuad cq)
		{
			ColorQuad color;
			double z = pos.z;
			double fraction = (z - getBoundingBox().min.z) * DepthCueRate / (getBoundingBox().max.z - getBoundingBox().min.z + DepthCueMin);
			if (fraction < 0.0)
				fraction = 0.0;
			if (fraction > 1.0)
				fraction = 1.0;
			color = cq.BlendRGB(StaticColorQuad.Black.cq(), fraction);
			return color;
		}
		
		/**
		 * Light point.
		 * 
		 * @param m
		 *            the m
		 * @param lights
		 *            the lights
		 * @param v
		 *            the v
		 * @return the color quad
		 */
		public ColorQuad LightPoint(Material m, LightSourceList lights, Vertex v)
		{
			return LightPoint(m, lights, v, v.getNormal());
		}
		
		/**
		 * Light point.
		 * 
		 * @param m
		 *            the m
		 * @param lightList
		 *            the light list
		 * @param pos
		 *            the pos
		 * @param N1
		 *            the n1
		 * @return the color quad
		 */
		public ColorQuad LightPoint(Material m, LightSourceList lightList, Point3D pos, Point3D N1)
		{

			if (!isDoLighting())
				return DepthCue(pos, m.getColor());

			ColorQuad pColor = StaticColorQuad.Black.cq();
			Vector3D N = new Vector3D(N1);
			N.Normalize();
			int nLights = lightList.size();
			if (nLights == 0)
			{
				return m.getColor();
			}

			for (LightSource L : lightList)
			{    

				/** ambient contribution from each light source */
				ColorQuad ambient = L.color.multiply(L.ambient);
				pColor = pColor.plus(ambient.multiply(m.kAmbient));
				
				
				/** setup dotProduct of light normal with surface normal; */
				double d = L.getNormal().Dot(N);
				if (d > 1.0)
					d = 1.0;
				if (d < 0.0)
					if (LightFrontBack)
						d = Math.abs(d);
					else
						d = 0.0;

				// diffuse contribution
				if (isDoDiffuse() && d>0.0)
				{
				    ColorQuad l = L.color.multiply(m.getDiffuseColor()).multiply(d);
					pColor = pColor.plus(l);
				}

				// specular contribution - Phong model
				if (m.useSpecularity && isDoSpecularity())
				{
					Vector3D V = new Vector3D(0.0f, 0.0f, 1.0f);
					Vector3D ntemp = N.Scale(2*d).Sub(L.getNormal());
					double cosalpha =  Math.abs(ntemp.Dot(V));

					if (cosalpha > 1.0)
						cosalpha = 1.0;
					else
						cosalpha = (float) Math.pow(cosalpha, m.shininess);
					
						/* I = Iaka + IpKd L dot N */
					cosalpha = cosalpha * m.kSpecular;
					ColorQuad specular = L.color.multiply(cosalpha);
					pColor = pColor.plus(specular);
				}
			}
			pColor.clampMax(1.0f);
			pColor = pColor.plus(m.getColor().multiply(m.kEmission));

			return DepthCue(pos, pColor);	
		}

		/**
		 * Gets the bounding box.
		 * 
		 * @return the bounding box
		 */
		public BoundingBox getBoundingBox() {
			return BoundingBox;
		}

		/**
		 * Sets the bounding box.
		 * 
		 * @param boundingBox
		 *            the new bounding box
		 */
		public void setBoundingBox(BoundingBox boundingBox) {
			BoundingBox = boundingBox;
		}

		/**
		 * Checks if is do depth cue.
		 * 
		 * @return true, if is do depth cue
		 */
		public boolean isDoDepthCue() {
			return DoDepthCue;
		}

		/**
		 * Sets the do depth cue.
		 * 
		 * @param doDepthCue
		 *            the new do depth cue
		 */
		public void setDoDepthCue(boolean doDepthCue) {
			DoDepthCue = doDepthCue;
		}

		/**
		 * Checks if is do scatter alpha.
		 * 
		 * @return true, if is do scatter alpha
		 */
		public boolean isDoScatterAlpha() {
			return DoScatterAlpha;
		}

		/**
		 * Sets the do scatter alpha.
		 * 
		 * @param doScatterAlpha
		 *            the new do scatter alpha
		 */
		public void setDoScatterAlpha(boolean doScatterAlpha) {
			DoScatterAlpha = doScatterAlpha;
		}

		/**
		 * Checks if is do lighting.
		 * 
		 * @return true, if is do lighting
		 */
		public boolean isDoLighting() {
			return DoLighting;
		}

		/**
		 * Sets the do lighting.
		 * 
		 * @param doLighting
		 *            the new do lighting
		 */
		public void setDoLighting(boolean doLighting) {
			DoLighting = doLighting;
		}

		/**
		 * Checks if is do specularity.
		 * 
		 * @return true, if is do specularity
		 */
		public boolean isDoSpecularity() {
			return DoSpecularity;
		}

		/**
		 * Sets the do specularity.
		 * 
		 * @param doSpecularity
		 *            the new do specularity
		 */
		public void setDoSpecularity(boolean doSpecularity) {
			DoSpecularity = doSpecularity;
		}

		/**
		 * Checks if is do diffuse.
		 * 
		 * @return true, if is do diffuse
		 */
		public boolean isDoDiffuse() {
			return DoDiffuse;
		}

		/**
		 * Sets the do diffuse.
		 * 
		 * @param doDiffuse
		 *            the new do diffuse
		 */
		public void setDoDiffuse(boolean doDiffuse) {
			DoDiffuse = doDiffuse;
		}


}
