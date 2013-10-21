package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.*;
import com.bobandthomas.Morbid.utils.CLoadableItem;

public abstract class Renderer extends CLoadableItem {

	public enum RenderQuality
	{
		RQ_Fast,
		RQ_Draft,
		RQ_View,
		RQ_Final
	};

		Material statMat;
		RenderQuality quality;
		Port port;
		BoxType worldBox;
		BoxType portBox;
		BoxType squaredWorldBox;
		Point3D Scale;
		Point3D Offset;
		Point3D translate;
		// Point3D WorldToPort(Point3D);
		// Point3D PortToWorld(Point3D);
		CTM mapCTM;
		CTM viewCTM;
		CTM ctm;
		Material currentMaterial;
		LightSourceList currentLights;
		public ColorQuad bgColor;
		LightingModel m_lm;


		double zoom;// { float get() { return zoom; } void set(float value) { zoom = value; Rescale(); } }
		public double getZoom() {
			return zoom;
		}


		public void setZoom(double zoom) {
			this.zoom = zoom;
		}
		Point3D Translate;// { Point3D get(){ return translate; } void set(Point3D value) {translate = value; Rescale(); }}	
		LightingModel lm;

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
			
			m_lm = new LightingModel();
			worldBox = new BoxType();
			squaredWorldBox = new BoxType();
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
				statMat.setColor(gob.Color);
				currentMaterial = statMat;
			}	
			
		}


		protected void SetWorldBox(BoxType wb)
		{
			worldBox = new BoxType( wb);
		}

		protected void Resize()
		{
			if (port!=null)
				portBox = port.GetScreenBounds();
		}
		protected void SetPort(Port p)
		{
			port = p;
			if (port == null) return;
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
		protected ColorQuad LightPoint(Point3D p, Point3D normal)
		{

			return StaticColorQuad.LiteGray.cq(); // TODO just for now, return something gray.
		}

}
