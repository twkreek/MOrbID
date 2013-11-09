package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class SphereGob extends Gob {


		private double /*Coord*/ r;
		@Override
		public Point3D center()
		{
			return StartPoint;
		}
		public SphereGob(Point3D  center, double sr)
	
		{
			super (center);
			setRadius(sr);
		}
		@Override
		public GobType Type() {return GobType.Sphere;}
		public double /*Coord*/ getRadius() {
			return r;
		}
		public void setRadius(double /*Coord*/ r) {
			this.r = r;
		}
};

