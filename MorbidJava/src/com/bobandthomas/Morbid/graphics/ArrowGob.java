package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.LineSegment;

public class ArrowGob extends GobVector {

	public int style;
		@Override
		public
		GobType Type()  {return GobType.Arrow;}

		
		public ArrowGob(int tstyle, LineSegment vec)
		{
			super(vec);
			style = tstyle;
		}
		public ArrowGob(int tstyle, Point3D  start, Point3D  end)
		{   
			super(start, end);
			style = tstyle;
		}


		public ArrowGob(Vertex vertex, Vertex vertex2) {
			super(vertex, vertex2);
		}
};

//Arrow Gob - start point and end point and color.


