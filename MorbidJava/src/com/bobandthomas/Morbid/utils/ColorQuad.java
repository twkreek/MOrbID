package com.bobandthomas.Morbid.utils;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;

public class ColorQuad extends Color4f {
	static float COLORQUAD_MAX_VALUE = 255.0f;
//	innerColor v = new innerColor();

	public boolean OutOfBounds;

	public ColorQuad() {
		Set(0, 0, 0);
	}

	public ColorQuad(double d, double e, double f) {
		Set((float)d, (float)e, (float)f);
	}

	public ColorQuad(int r, int g, int b) {
		Set(r, g, b);
	}

	public ColorQuad(ColorQuad color) {
		// TODO Auto-generated constructor stub
		Set(color.x, color.y, color.z);
	}
	
	public Color3f Cf()
	{
	 return new Color3f(x,y,z);
	}
	public java.awt.Color getJColor()
	{
		return new java.awt.Color(x,y,z);
	}
	public void setAlpha(double a)
	{
		w = (float) a;
	}

	public ColorQuad BlendHSV(ColorQuad to, double /* Coord */fraction) {
		if (fraction >= 1.0)
			return this;
		if (fraction <= 0.0)
			return to;
		ColorQuad theFrom = RGBtoHSV();
		ColorQuad theTo = to.RGBtoHSV();

		theFrom = (theFrom.multiply(fraction)).plus(theTo
				.multiply(1.0f - fraction));
		return theFrom.HSVtoRGB();
	}

	public ColorQuad BlendRGB(ColorQuad to, double fraction) {
		ColorQuad from = this;
		if (fraction >= 1.0)
			return from;
		if (fraction <= 0.0)
			return to;
		from = (from.multiply(fraction)).plus(to.multiply(1.0f - fraction));
		return from;
	}
	
	public static ColorQuad multiBlend(ColorQuad[] colors, double fraction)
	{
		int slices = colors.length-1;
		if (fraction <= 0)
			return colors[0];
		if (fraction >= 1)
			return colors[slices];
		double scale;
		int min = (int) Math.floor(fraction*slices);
		int max = (int) Math.ceil(fraction*slices);
		if (max >= colors.length) 
			return colors[slices];
		if (min == max) 
			return colors[min];
		scale = (fraction*slices - min)/(max-min);
		return colors[min].BlendRGB(colors[max], scale);
		
	}

	public void Clamp(float f) {
		x = Math.min(x, f);
		y = Math.min(y, f);
		z = Math.min(z, f);
	}

	public ColorQuad div(double /* Coord */c) {
		return new ColorQuad(x / c, y / c, z / c);
	}

	private int getB() {
		return (int) (z * 255);
	}

	private int getG() {
		return (int) (y * 255);
	}

	private int getR() {
		return (int) (x * 255);
	}
	
	boolean getOutOfBounds() {
		return x > 1.0f || y > 1.0f || z > 1.0f;
	}


	long Hash() {
		return (long) (1000 * x * y * z) % 255;
	}

	public static ColorQuad HSVtoRGB(double x, double y, double z) {
		ColorQuad rgb = new ColorQuad();
		if (y == 0.0) {
			rgb.Set(z, z, z);
			return rgb;
		}
		if (x == 1.0)
			x = 0;
		x = x * 6.0f;
		int i = (int) x;
		double /* Coord */f, p, q, t;
		f = x - i;
		p = z * (1 - y);
		q = z * (1 - (y * f));
		t = z * (1 - (y * (1 - f)));
		switch (i) {
		case 0:
			rgb.Set(z, t, p);
			break;
		case 1:
			rgb.Set(q, z, p);
			break;
		case 2:
			rgb.Set(p, z, t);
			break;
		case 3:
			rgb.Set(p, q, z);
			break;
		case 4:
			rgb.Set(t, p, z);
			break;
		case 5:
			rgb.Set(z, p, q);
		}
		return rgb;

	}

	ColorQuad HSVtoRGB() {
		ColorQuad rgb = new ColorQuad();
		if (y == 0.0) {
			rgb.Set(z, z, z);
			return rgb;
		}
		if (x == 1.0)
			x = 0;
		x = x * 6.0f;
		int i = (int) x;
		double /* Coord */f, p, q, t;
		f = x - i;
		p = z * (1 - y);
		q = z * (1 - (y * f));
		t = z * (1 - (y * (1 - f)));
		switch (i) {
		case 0:
			rgb.Set(z, t, p);
			break;
		case 1:
			rgb.Set(q, z, p);
			break;
		case 2:
			rgb.Set(p, z, t);
			break;
		case 3:
			rgb.Set(p, q, z);
			break;
		case 4:
			rgb.Set(t, p, z);
			break;
		case 5:
			rgb.Set(z, p, q);
		}
		return rgb;

	}

	public ColorQuad Inverse() {
		ColorQuad c = new ColorQuad((int) COLORQUAD_MAX_VALUE - getR(),
				(int) COLORQUAD_MAX_VALUE - getG(), (int) COLORQUAD_MAX_VALUE - getB());
		return c;
	}


	boolean isEqual(ColorQuad cq) {
		return (getR() == cq.getR()) && (getG() == cq.getG()) && (getB() == cq.getB());
	}

	long MatchQuality(ColorQuad color) {
		return (((long) getR() - color.getR()) * ((long) getR() - color.getR())
				+ ((long) getG() - color.getG()) * ((long) getG() - color.getG())
				+ ((long) getB() - color.getB()) * ((long) getB() - color.getB()));
	}

	public ColorQuad multiply(ColorQuad fraction) {
		ColorQuad c = new ColorQuad(fraction.x * x, fraction.y * y, fraction.z * z);
		return c;
	}

	public ColorQuad multiply(double /* Coord */c) {
		return new ColorQuad(x * c, y * c, z * c);
	}


	private int getInt(double r)
	{
		return (int) (r/COLORQUAD_MAX_VALUE);
	}
	public ColorQuad plus(ColorQuad p) {
		ColorQuad c = new ColorQuad(x + p.x, y + p.y, z + p.z);
		return c;
	}

	public ColorQuad minus(ColorQuad p) {
		ColorQuad c = new ColorQuad(x - p.x, y - p.y, z - p.z);
		return c;
	}
	
	ColorQuad RGBtoHSV() {
		double /* Coord */maxNum, minNum;
		double /* Coord */h = 0, s = 0, v;
		double /* Coord */r = x;
		double /* Coord */g = y;
		double /* Coord */b = z;

		maxNum = Math.max(Math.max(r, g), b);
		minNum = Math.min(Math.min(r, g), b);
		v = maxNum;
		if (maxNum != 0)
			s = (maxNum - minNum) / maxNum;
		else
			return new ColorQuad();

		double /* Coord */rc, gc, bc;
		rc = (maxNum - r) / (maxNum - minNum);
		gc = (maxNum - g) / (maxNum - minNum);
		bc = (maxNum - b) / (maxNum - minNum);

		if (r == maxNum)
			h = bc - gc;
		else if (g == maxNum)
			h = 2 + rc - bc;
		else if (b == maxNum)
			h = 4 + gc - rc;

		h = h / 6.0f;
		if (h < 0)
			h += 1.0f;

		return new ColorQuad(h, s, v);

	}

	void Set(double d, double e, double f) {
		x = (float) d;
		y = (float) e;
		z = (float) f;
	}

	void Set(int inR, int inG, int inB) {
		Set(inR / COLORQUAD_MAX_VALUE, inG / COLORQUAD_MAX_VALUE, inB / COLORQUAD_MAX_VALUE);
	}

	void Set(Point3D p) {
		Set(p.x, p.y, p.z);
	}

	private void setB(int value) {
		z = Math.min(value, 255) / 255.0f;
	}

	private void setG(int value) {
		y = Math.min(value, 255) / 255.0f;
	}

	private void setR(int value) {
		x = Math.min(value, 255) / 255.0f;
	}

	String ToString() {
		return "<ColorQuad R=\"" + getR() + "\" G=\"" + getG() + "\" B\"" + getB() + "\"/>";
	}
}
