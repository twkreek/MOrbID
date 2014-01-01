package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.LineSegment;
import com.bobandthomas.Morbid.utils.Vector3D;


//Vector Gob - start point and end point and color.

public class GobVector extends Gob {

		public ColorQuad EndColor;
		private Vertex endPoint;
		

		@Override
		public
		GobType Type() {return GobType.Vector;}
		GobVector(LineSegment vec)
		{
			setPoint(new Vertex(vec.from));
			endPoint = new Vertex(vec.to);
		}
		GobVector(Vertex v1, Vertex v2)
		{
			setPoint(v1);
			endPoint = v2;
		}
		
		public Vertex getEndPoint()
		{
			return endPoint;
		}
		public void setEndPoint(Vertex v)
		{
			endPoint = new Vertex(v);
		}
		
		public Vector3D getUnitVector()
		{
			Vector3D p = getPoint().Sub(endPoint);
			p.Normalize();
			return p;
		}
		
		public GobVector(Point3D  start, Point3D  end)
		{
			super(start);
			endPoint= new Vertex(end);
		}
		
		public Point3D  getCenter()
		{
			return new Vertex(getPoint().midPoint(endPoint, 0.5));
		}
		public float getLength() {
			return (float) getPoint().distance(endPoint);
		}


};



