package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class SphereGob extends Gob {


		double /*Coord*/ r;
		@Override
		public Point3D center()
		{
			return StartPoint;
		}
		public SphereGob(Point3D  center, double sr)
	
		{
			super (center);
			r = sr;
		}
		@Override
		public GobType Type() {return GobType.Sphere;}
};

