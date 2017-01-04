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
package com.bobandthomas.Morbid.graphics.renderers;

import com.bobandthomas.Morbid.graphics.ArrowGob;
import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.CircleGob;
import com.bobandthomas.Morbid.graphics.CylinderGob;
import com.bobandthomas.Morbid.graphics.Gob;
import com.bobandthomas.Morbid.graphics.GobIndexed;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobVector;
import com.bobandthomas.Morbid.graphics.LabelGob;
import com.bobandthomas.Morbid.graphics.LabeledCircleGob;
import com.bobandthomas.Morbid.graphics.LightSourceList;
import com.bobandthomas.Morbid.graphics.LightingModel;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.SphereGob;
import com.bobandthomas.Morbid.graphics.StringGob;
import com.bobandthomas.Morbid.graphics.renderers.Port.PortChangeEvent;
import com.bobandthomas.Morbid.graphics.renderers.Port.PortChangeListener;
import com.bobandthomas.Morbid.utils.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Renderer.
 * 
 * @author Thomas Kreek
 */
public abstract class Renderer extends CLoadableItem implements PortChangeListener {



	/**
	 * The Enum RenderQuality.
	 * 
	 * @author Thomas Kreek
	 */
	public enum RenderQuality
	{
		
		/** The R q_ fast. */
		RQ_Fast,
		
		/** The R q_ draft. */
		RQ_Draft,
		
		/** The R q_ view. */
		RQ_View,
		
		/** The R q_ final. */
		RQ_Final
	};

		/** The stat mat. temporary place hoder for default material */
		Material statMat;
		
		/** The render quality. */
		RenderQuality quality;
		
		/** The port. */
		protected Port port;
		
		/** The world box. */
		BoundingBox worldBox;
		
		/** The port box. */
		BoundingBox portBox;
		
		/** The squared world box. */
		BoundingBox squaredWorldBox;
		
		/** The Scale. */
		Point3D Scale;
		
		/** The Offset. */
		Point3D Offset;
		
		/** The translate. */
		Point3D translate;
		// Point3D WorldToPort(Point3D);
		// Point3D PortToWorld(Point3D);
		/** The map ctm. */
		protected CTM mapCTM;
		
		/** The view ctm. */
		protected CTM viewCTM;
		
		/** The current ctm. */
		protected CTM ctm;
		
		/** The current material. */
		protected Material currentMaterial;
		
		/** The current lights. */
		protected LightSourceList currentLights;
		
		/** The bg color. */
		public ColorQuad bgColor;
		
		/** The lm. */
		protected LightingModel lm;


		/** The zoom. */
		double zoom;// { float get() { return zoom; } void set(float value) { zoom = value; Rescale(); } }
		
		/**
		 * Gets the zoom.
		 * 
		 * @return the zoom
		 */
		public double getZoom() {
			return zoom;
		}


		/**
		 * Sets the zoom.
		 * 
		 * @param zoom
		 *            the new zoom
		 */
		public void setZoom(double zoom) {
			this.zoom = zoom;
		}
		
		/** The Translate. */
		private Point3D Translate;// { Point3D get(){ return translate; } void set(Point3D value) {translate = value; Rescale(); }}	

	 	/**
		 * Sets the render quality.
		 * 
		 * @param rq
		 *            the rq
		 */
	 	void SetRenderQuality(RenderQuality rq) {quality = rq; }


		/**
		 * Self double buffer.
		 * 
		 * @return true, if successful
		 */
		boolean SelfDoubleBuffer() { return false; }
		
		/**
		 * Uses pixel map.
		 * 
		 * @return true, if successful
		 */
		boolean UsesPixelMap() {return false; }




		/**
		 * Do render.  Primary render loop. Called every time the scene is redrawn.  
		 * takes goblist set containing all visual elements, the ligh sources
		 * and the coordinate transformation matrix, if used. CTM is used only if renderer is managing viewing transforms directly.
		 * 
		 * @param goblist
		 *            the goblist
		 * @param LSList
		 *            the LS list
		 * @param totalCTM
		 *            the total ctm
		 */
		public abstract void DoRender(GobListSet goblist, LightSourceList LSList, CTM totalCTM);

		/**
		 * Vector.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Vector(GobVector g);
		
		/**
		 * Arrow.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Arrow(ArrowGob g);
		
		/**
		 * String.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void String(StringGob g);
		
		/**
		 * Label.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Label(LabelGob g);
		
		/**
		 * Circle.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Circle(CircleGob g);
		
		/**
		 * Labeled circle.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void LabeledCircle(LabeledCircleGob g);
		
		/**
		 * Poly.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Poly(GobPoly g);
		
		/**
		 * Indexed.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Indexed(GobIndexed g);
		
		/**
		 * Cylinder.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Cylinder(CylinderGob g);
		
		/**
		 * Sphere.
		 * 
		 * @param g
		 *            the g
		 */
		abstract void Sphere(SphereGob g);

		/**
		 * Instantiates a new renderer.
		 */
		public Renderer()
		{
			quality = RenderQuality.RQ_View;
			port = null;
			zoom = 1.0f;
			bgColor = StaticColorQuad.White.cq();

			mapCTM = new CTM();
			viewCTM = new CTM();
			ctm = new CTM();
			
			lm = new LightingModel();
			worldBox = new BoundingBox();
			squaredWorldBox = new BoundingBox();
			Scale = new Point3D(1, 1, 1);
			Offset = new Point3D();
			translate = new Point3D();


		}


		/**
		 * Use material.
		 * 
		 * @param gob
		 *            the gob
		 */
		protected void UseMaterial(Gob gob)
		{
			Material mat = gob.getMaterial();
			{
				if (mat != null)
					statMat = new Material(mat);
				else
					statMat = new Material();
				statMat.setColor(gob.getColor());
				currentMaterial = statMat;
			}	
			
		}


		/**
		 * Sets the world box.
		 * 
		 * @param wb
		 *            the wb
		 */
		public void SetWorldBox(BoundingBox wb)
		{
			worldBox = new BoundingBox( wb);
		}

		/**
		 * Resize.
		 */
		public void Resize()
		{
			if (port!=null)
				portBox = port.GetScreenBounds();
		}
		
		/**
		 * Sets the port.
		 * 
		 * @param p
		 *            the p
		 */
		public void SetPort(Port p)
		{
			port = p;
			if (p == null && port != null)
				port.unRegisterListener((PortChangeListener) this);
			if (port == null) return;
			port.registerListener((PortChangeListener) this);
			Rescale();
		}


		/**
		 * Rescale.
		 */
		public void Rescale()
		{
			if (port == null)
				return;
			portBox = port.GetScreenBounds();
			
			Scale.x = portBox.size().x / worldBox.size().x;
			Scale.y = portBox.size().y / worldBox.size().y;
			Scale.z = portBox.size().z / worldBox.size().z;

			Scale.z = Scale.x = Scale.y = Math.min (Scale.x, Scale.y);

			portBox.min.z = Scale.z * worldBox.min.z * 1.5f;
			portBox.max.z = Scale.z * worldBox.max.z * 1.5f;

			double yRatio, xRatio, ratio;
			xRatio = worldBox.size().x / portBox.size().x; 
			yRatio = worldBox.size().y / portBox.size().y;
			squaredWorldBox.min = worldBox.min;
			squaredWorldBox.max = worldBox.max;

			if (xRatio > yRatio )
			{
				ratio = xRatio/yRatio;
				squaredWorldBox.min.y *= ratio;
				squaredWorldBox.max.y *= ratio;
			}
			else
			{
				ratio = yRatio/xRatio;
				squaredWorldBox.min.x *= ratio;
				squaredWorldBox.max.x *= ratio;

			}
		 
			port.SetScreenBounds(portBox);
			Offset = portBox.size().Scale(0.5);
			mapCTM.identity();
			mapCTM.scale(zoom);
			mapCTM.transl(translate);
			mapCTM.scale(Scale);
			mapCTM.transl(Offset);
		}

		/**
		 * Dispatch - steps through the gobList and calls the rendering function for each type
		 * of Gob
		 * 
		 * @param gobList
		 *            the gob list
		 */
		protected void Dispatch(GobList gobList)
		{
			if (gobList.IsRotate())
				ctm = mapCTM.Mul(viewCTM);
			else
				ctm = mapCTM;
			
			for (Gob gob :gobList)
			{
				UseMaterial(gob);
				switch (gob.Type())
				{
				case	String:
					String((StringGob) gob);
					break;
				case	Label:
					Label((LabelGob) gob);
					break;
				case	Arrow:
					Arrow((ArrowGob) gob);
					break;
				case	Circle:
					Circle((CircleGob) gob);
					break;
				case	CircleLabeled:
					LabeledCircle((LabeledCircleGob) gob);
					break;
				case	Cylinder:
					Cylinder((CylinderGob) gob);
					break;
				case	Sphere:
					Sphere((SphereGob) gob);
					break;
				case	Vector:
					Vector((GobVector) gob);
					break;
				case	Indexed:
					Indexed((GobIndexed) gob);
					break;
				case	Poly:
					Poly((GobPoly) gob);
				default:
					break;

				}
			}

		}
		
		/**
		 * Dispatch (Gob) - calls the redner routine for a single type of gob.
		 * 
		 * @param gob
		 *            the gob to be rendered
		 */
		protected void Dispatch(Gob gob)
		{
				UseMaterial(gob);
				switch (gob.Type())
				{
				case	String:
					String((StringGob) gob);
					break;
				case	Label:
					Label((LabelGob) gob);
					break;
				case	Arrow:
					Arrow((ArrowGob) gob);
					break;
				case	Circle:
					Circle((CircleGob) gob);
					break;
				case	CircleLabeled:
					LabeledCircle((LabeledCircleGob) gob);
					break;
				case	Cylinder:
					Cylinder((CylinderGob) gob);
					break;
				case	Sphere:
					Sphere((SphereGob) gob);
					break;
				case	Vector:
					Vector((GobVector) gob);
					break;
				case	Indexed:
					Indexed((GobIndexed) gob);
					break;
				case	Poly:
					Poly((GobPoly) gob);
				default:
					break;

				}
		}
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.utils.IMorbidListener#handleEvent(com.bobandthomas.Morbid.utils.MorbidEvent)
		 */
		@Override
		public void handleEvent(PortChangeEvent change) {
			portBox = port.GetScreenBounds();
			Resize();
			Rescale();
		}

		/**
		 * Light point. given a point and its normal, return its lighted color
		 * according to the renderer.
		 * default is all points are grey
		 * 
		 * @param p
		 *            the p
		 * @param normal
		 *            the normal
		 * @return the color quad
		 */
		protected ColorQuad LightPoint(Point3D p, Point3D normal)
		{

			return StaticColorQuad.LiteGray.cq(); // TODO just for now, return something gray.
		}


		/**
		 * Gets the translate.
		 * 
		 * @return the translate
		 */
		public Point3D getTranslate() {
			return Translate;
		}


		/**
		 * Sets the translate.
		 * 
		 * @param translate
		 *            the new translate
		 */
		public void setTranslate(Point3D translate) {
			Translate = translate;
		}

}
