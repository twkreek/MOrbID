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

public abstract class Renderer extends CLoadableItem implements PortChangeListener {



	public enum RenderQuality
	{
		RQ_Fast,
		RQ_Draft,
		RQ_View,
		RQ_Final
	};

		Material statMat;
		RenderQuality quality;
		protected Port port;
		BoundingBox worldBox;
		BoundingBox portBox;
		BoundingBox squaredWorldBox;
		Point3D Scale;
		Point3D Offset;
		Point3D translate;
		// Point3D WorldToPort(Point3D);
		// Point3D PortToWorld(Point3D);
		protected CTM mapCTM;
		protected CTM viewCTM;
		protected CTM ctm;
		protected Material currentMaterial;
		protected LightSourceList currentLights;
		public ColorQuad bgColor;
		protected LightingModel lm;


		double zoom;// { float get() { return zoom; } void set(float value) { zoom = value; Rescale(); } }
		public double getZoom() {
			return zoom;
		}


		public void setZoom(double zoom) {
			this.zoom = zoom;
		}
		private Point3D Translate;// { Point3D get(){ return translate; } void set(Point3D value) {translate = value; Rescale(); }}	

	 	void SetRenderQuality(RenderQuality rq) {quality = rq; }


		boolean SelfDoubleBuffer() { return false; }
		boolean UsesPixelMap() {return false; }




		public abstract void DoRender(GobListSet goblist, LightSourceList LSList, CTM totalCTM);

		abstract void Vector(GobVector g);
		abstract void Arrow(ArrowGob g);
		abstract void String(StringGob g);
		abstract void Label(LabelGob g);
		abstract void Circle(CircleGob g);
		abstract void LabeledCircle(LabeledCircleGob g);
		abstract void Poly(GobPoly g);
		abstract void Indexed(GobIndexed g);
		abstract void Cylinder(CylinderGob g);
		abstract void Sphere(SphereGob g);

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
			Scale = new Point3D();
			Offset = new Point3D();
			translate = new Point3D();


		}


		protected void UseMaterial(Gob gob)
		{
			Material mat = gob.getMaterial();
			{
				if (mat != null)
					statMat = mat;
				else
					statMat = new Material();
				statMat.setColor(gob.getColor());
				currentMaterial = statMat;
			}	
			
		}


		public void SetWorldBox(BoundingBox wb)
		{
			worldBox = new BoundingBox( wb);
		}

		public void Resize()
		{
			if (port!=null)
				portBox = port.GetScreenBounds();
		}
		public void SetPort(Port p)
		{
			port = p;
			if (p == null && port != null)
				port.unRegisterListener(this);
			if (port == null) return;
			port.registerListener(this);
			Resize();
		}


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
			Offset = portBox.size().Scale(1/2);
			mapCTM.identity();
			mapCTM.scale(zoom);
			mapCTM.transl(translate);
			mapCTM.scale(Scale);
			mapCTM.transl(Offset);
		}

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
		@Override
		public void PortChanged(PortChangeEvent change) {
			portBox = port.GetScreenBounds();
			Rescale();
		}

		protected ColorQuad LightPoint(Point3D p, Point3D normal)
		{

			return StaticColorQuad.LiteGray.cq(); // TODO just for now, return something gray.
		}


		public Point3D getTranslate() {
			return Translate;
		}


		public void setTranslate(Point3D translate) {
			Translate = translate;
		}

}
