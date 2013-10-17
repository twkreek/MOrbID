package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class LabeledCircleGob extends CircleGob {

	public String Label;
		@Override
		public GobType Type() {return GobType.CircleLabeled;}
		
		LabeledCircleGob(Point3D center, float /*Coord*/ rad, String label)
			
		{
			super(center, rad);
			Label = label;
		}
}

