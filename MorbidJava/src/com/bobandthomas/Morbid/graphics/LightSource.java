package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.*;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.ColorQuad;
import javax.media.j3d.Light;


public class LightSource extends CLoadableItem {

		public ColorQuad color;
		public float ambient;// ambient fraction of color that is the ambient light produced from this source
		Vector3D normal; // the normalized position for normals.
		private Point3D location;// the unnormalized position for distance;
		boolean useFalloff;// whether to use distance critereon in calculating light intensity.
		float falloffConstant;// k factor in light intensity falloff.
		Light renderedLight;
		public Light getRenderedLight() {
			return renderedLight;
		}
		public void setRenderedLight(Light renderedLight) {
			this.renderedLight = renderedLight;
		}
		LightSource()
		{
			Init();
		}
		public LightSource(ColorQuad cq)
		{
			Init();
			color = cq;
		}
		public LightSource(ColorQuad cq, Point3D p )
		{
			Init();
			color = cq;
			normal = p.getVector().Normalize();
			location = p;
		}

		boolean CanEdit() {return true;}
		private void Init()
		{
			color = StaticColorQuad.White.cq();
			ambient = 0.1f;
			normal = new Vector3D( 1, 0, 0);
			location = normal;
		}
		public Point3D getLocation() {
			return location;
		}
		public void setLocation(Point3D location) {
			this.location = location;
			normal = location.getVector().Normalize(); 
		}
		public Vector3D getNormal()
		{
			return normal;
		}
	};
