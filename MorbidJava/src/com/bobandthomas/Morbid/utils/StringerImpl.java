package com.bobandthomas.Morbid.utils;

public class StringerImpl {
	enum Container
	{
		None (" ", " "),
		SQBracket ("[","]"),
		TBracket ("<", ">"),
		Brace ("{", "}"),
		Paren("(", ")");
		String open;
		String close;
		Container (String o, String c)
		{
			open = o;
			close = c;
		}
		
	};
	enum Delim
	{
		Space (" "),
		Comma (", ");
		String delim;
		Delim(String delim)
		{
			this.delim = delim;
		}
	}
	Container c;
	Delim d;
	boolean colorInt; 
	
	String pointFormat;
	String colorFormat;
	
	StringerImpl(Container c, Delim d, boolean colorInt)
	{
		this.c = c;
		this.d = d;
		this.colorInt = colorInt;
		pointFormat = String.format("%s%5.3d%s%5.3d%s%5.3d%s", c.open,d.delim, d.delim, c.close );
		
		if(colorInt)
			colorFormat = String.format("%s%d%s%d%s%d%s", c.open, d.delim, d.delim, c.close);
		else
			colorFormat = String.format("%s%5.3d%s%5.3d%s%5.3d%s", c.open, d.delim, d.delim, c.close);
	}
	String toString(Point3D p){
		return String.format(pointFormat, p.x, p.y, p.z);
	}
	
	String toString(ColorQuad color)
	{
		if (colorInt)
			return String.format(colorFormat, color.getR(), color.getG(), color.getB());
		else
			return String.format(colorFormat, color.x, color.y, color.z);
	}
	

}
