package com.bobandthomas.Morbid.utils;


public class BoxType {
	public Point3D min;
	public Point3D max;
	boolean isset;

	public Point3D getMin() {
		return min;
	}
	public void setMin(Point3D min) {
		this.min = min;
	}
	public Point3D getMax() {
		return max;
	}
	public void setMax(Point3D max) {
		this.max = max;
	}
	public BoxType()
	{
		isset = false;
		min = new Point3D(-1,-1,-1);
		max = new Point3D(1,1,1);
	}
	public BoxType(BoxType bt)
	{
		isset = false;
		min = new Point3D(bt.min);
		max = new Point3D(bt.max);
		isset = true;
	}
	public BoxType(int minx, int miny, int maxx, int maxy) 
	{
		isset = true; 
		min = new Point3D( minx, miny, 0.0f);
		max = new Point3D( maxx, maxy, 0.0f); 
	}

	public static BoxType UnitBox()
	{
		BoxType bt = new BoxType();
		bt.isset = true;
		bt.min = new Point3D(-1,-1,-1);
		bt.max = new Point3D(1,1,1);
		return bt;
	}
	public BoxType Scale(double s) {
		BoxType box = new BoxType(this);
		box.min.scale(s);
		box.max.Scale(s);
		return box;
	}
	boolean Contains(BoxType bt)
	{
		if (min.x > bt.min.x)
			return false;
		if (min.y > bt.min.y)
			return false;
		if (min.z > bt.min.z)
			return false;
		if (max.x < bt.max.x)
			return false;
		if (max.y < bt.max.y)
			return false;
		if (max.z < bt.max.z)
			return false;
		return true;
	}
	boolean Contains(Point3D pt)
	{
		if (min.x > pt.x || max.x < pt.x)
			return false;
		if (min.y > pt.y || max.y < pt.y)
			return false;
		if (min.z > pt.z || max.z < pt.z)
			return false;
		return true;

	}

	public Point3D size() { return max.Sub(min); }
	public Point3D center() 
	{
		Point3D p = max.Add(min);
		p = p.Scale(0.5);
		return max.Add(min).Scale(0.5); 
	}
	public BoxType cube()
	{
		BoxType bounds= new BoxType();
		Point3D size = size();
		double /*Coord*/ isize = size.x;
		if (isize < size.y) isize = size.y;
		if (isize < size.z) isize = size.z;
		
		bounds.min.x = bounds.min.y = bounds.min.z = -isize/2;
		bounds.max.x = bounds.max.y = bounds.max.z = isize/2;

		return bounds;

	}


	@SuppressWarnings("unused")
	public void AddBounds(Point3D a)
	{
		Point3D addedPoint = a;
		if (!isset)
		{
			min = new Point3D(a);
			max = new Point3D(a);
			isset = true;
			return;
		}
		if (min.x > a.x ) 
			min.x = a.x ;
	    if (min.y > a.y ) 
			min.y = a.y ;
	    if (min.z > a.z ) 
			min.z = a.z ;

	    if (max.x < a.x ) 
			max.x = a.x ;
	    if (max.y < a.y ) 
			max.y = a.y ;
	    if (max.z < a.z ) 
			max.z = a.z ;

	}

	public void resetBounds()
	{
		max = new Point3D(-1000.0f, -1000.0f, -1000.0f);
		min = new Point3D( 1000.0f,  1000.0f,  1000.0f);
		isset = false;
	}

	public void CalcBounds(Point3D position, double r)
	{
		
		Point3D rad = new Point3D(r,r,r);
		rad.add(position);
		AddBounds(rad);
		rad = new Point3D(-r,-r,-r);
		rad.add(position);
		AddBounds(rad);
	}
	
}
