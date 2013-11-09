package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.LineSegment;


//Vector Gob - start point and end point and color.

public class GobVector extends Gob {

		public Point3D EndPoint;
		public ColorQuad EndColor;
		

		@Override
		public
		GobType Type() {return GobType.Vector;}
		GobVector(LineSegment vec)
		{
			StartPoint = vec.from;
			EndPoint = vec.to;
			Center = (StartPoint.Add(EndPoint)).Scale(0.5f);
		}
		
		public Point3D getUnitVector()
		{
			Point3D p = StartPoint.Sub(EndPoint);
			p.Normalize();
			return p;
		}
		
		public GobVector(Point3D  start, Point3D  end)
		{

			StartPoint = start;
			EndPoint = end;
			Center = new Point3D();
			Center.add(StartPoint, EndPoint);
			Center.scale(0.5);
		}
		
		public Point3D  getCenter()
		{
			return new Point3D(Center);
		}
		public float getLength() {
			return (float) StartPoint.distance(EndPoint);
		}


};



