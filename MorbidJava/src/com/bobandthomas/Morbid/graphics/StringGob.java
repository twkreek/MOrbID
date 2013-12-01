package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class StringGob extends Gob {

		double scale=1.0;
		public StringGob(String s1, Point3D p1)
		{
			setName(s1);
			Center = p1;
		}
		@Override
		public GobType Type() {return GobType.String;}
		public double getScale() {
			return scale;
		}
		public void setScale(double scale) {
			this.scale = scale;
		}

};


