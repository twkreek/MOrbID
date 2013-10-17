package com.bobandthomas.Morbid.utils;

import java.util.ArrayList;

public class Point3DList extends ArrayList<Point3D> {

	Point3D Normal()
	{
		Point3D a = get(0).Sub(get(1));;
		Point3D b = get(2).Sub(get(1));;
	    
		Point3D norm = a.Cross(b);
		norm = norm.Scale(1/norm.Length());
	    return norm;
	}
}
