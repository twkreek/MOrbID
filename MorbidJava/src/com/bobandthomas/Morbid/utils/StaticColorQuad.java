package com.bobandthomas.Morbid.utils;
public enum StaticColorQuad {
	Black (0,0,0),
	White (255,255,255),
	LiteGray (200,200,200),
	Red (255,0,0),
	Blue (0,0,255),
	Green (0,255,0),
	Cyan (0,255,255),
	Magenta (255,0,255),
	Yellow (255,255,0),
	DarkGray(50,50,50);
	private final int r;
	private final int g;
	private final int b;
	StaticColorQuad(int r, int g, int b)
	{
		this.r=r; this.g=g; this.b=b;
	}
	public int r() { return r; }
	public int g() { return g; }
	public int b() { return b; }
	
	public ColorQuad cq() { return new ColorQuad(r,g,b); }

}
