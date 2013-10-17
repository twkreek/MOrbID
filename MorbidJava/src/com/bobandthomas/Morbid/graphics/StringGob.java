package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class StringGob extends Gob {


		public String s;
		public Point3D p;
		public StringGob(String s1, Point3D p1)
		{
			s = s1;
			p = p1;
		}
		@Override
		public GobType Type() {return GobType.String;}

		@Override
		public Point3D center()
			{
				return p;
			}
};


