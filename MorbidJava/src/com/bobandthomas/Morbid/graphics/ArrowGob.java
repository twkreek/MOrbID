package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

public class ArrowGob extends GobVector {

	public int style;
		@Override
		GobType Type()  {return GobType.Arrow;}

		
		ArrowGob(int tstyle, Vector3D vec)
		{
			super(vec);
			style = tstyle;
		}
		ArrowGob(int tstyle, Point3D  start, Point3D  end)
		{   
			super(start, end);
			style = tstyle;
			StartPoint = start;
			EndPoint = end;
		}
};

//Arrow Gob - start point and end point and color.


