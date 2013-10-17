package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;


//Vector Gob - start point and end point and color.

public class GobVector extends Gob {

		Point3D EndPoint;
		ColorQuad EndColor;
		

		@Override
		GobType Type() {return GobType.Vector;}
		GobVector(Vector3D vec)
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
		
		Point3D  getCenter()
		{
			return new Point3D(Center);
		}
		public float getLength() {
			return (float) StartPoint.distance(EndPoint);
		}


};



