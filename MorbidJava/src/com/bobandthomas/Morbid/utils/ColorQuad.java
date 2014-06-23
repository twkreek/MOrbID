/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.utils;

import javax.vecmath.Color3f;
import javax.vecmath.Color4f;


// TODO: Auto-generated Javadoc
/**
 * The Class ColorQuad.
 * 
 * @author Thomas Kreek
 */
public class ColorQuad extends Color4f {
	
	/** The colorquad max value. */
	static float COLORQUAD_MAX_VALUE = 255.0f;


	/**
	 * Instantiates a new color quad.
	 */
	public ColorQuad() {
		Set(0, 0, 0);
	}


	/**
	 * Instantiates a new color quad.
	 * 
	 * @param r
	 *            the red
	 * @param g
	 *            the green
	 * @param b
	 *            the blue
	 */
	public ColorQuad(double r, double g, double b) {
		Set((float)r, (float)g, (float)b);
	}

	/**
	 * Instantiates a new color quad.
	 * 
	 * @param r
	 *            the r
	 * @param g
	 *            the g
	 * @param b
	 *            the b
	 */
	public ColorQuad(int r, int g, int b) {
		Set(r, g, b);
	}

	/**
	 * Instantiates a new color quad from an existing one.
	 * 
	 * @param color
	 *            the color
	 */
	public ColorQuad(ColorQuad color) {
		Set(color.x, color.y, color.z);
	}
	
	/**
	 * Instantiates a new color quad.
	 * 
	 * @param color
	 *            the color
	 */
	public ColorQuad(java.awt.Color color) {
		Set(color.getRed(), color.getGreen(), color.getBlue());
	}
	
	/**
	 * Instantiates a new color quad from a string descriptor.
	 * 
	 * @param color
	 *            the color
	 */
	public ColorQuad(String color)
	{
		// String can either be decimal or hex
		//remove leading and trailing array modifiers
		String temp = color.replace('[', ' ').replace(']', ' ').trim();
		if (temp.charAt(0) == '#')
		{
			//hex
			int r,g,b;
			r = Integer.decode( "#" + temp.substring(1,2));
			g = Integer.decode( '#' + temp.substring(3,4));
			b = Integer.decode( "#" + temp.substring(5,6));
			Set(r,g,b);
			return;
		}
		//decimal
		String[] values = temp.split(",");
		Set(Integer.parseInt(values[0]), Integer.parseInt(values[1]), Integer.parseInt(values[2]));
		
	}
	
	/**
	 * Returns the vecmath.color3f value for compatibility.
	 * 
	 * @return the color3f 
	 */
	public Color3f Cf()
	{
	 return new Color3f(x,y,z);
	}
	
	/**
	 * Gets an awt color for compatibility.
	 * 
	 * @return the j color
	 */
	public java.awt.Color getJColor()
	{
		return new java.awt.Color(x,y,z);
	}
	
	/**
	 * Sets the alpha/transparency.
	 * 
	 * @param a
	 *            the new alpha
	 */
	public void setAlpha(double a)
	{
		w = (float) a;
	}

	/**
	 * Blend HSV - linear interpolator between two colors in HSV space this
	 * produces colors "around" the color wheel.
	 * 
	 * @param to
	 *            the to
	 * @param fraction
	 *            the fraction
	 * @return the color quad
	 */
	public ColorQuad BlendHSV(ColorQuad to, double fraction) {
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

	/**
	 * performs a linear interpolation between two colors in RGB space.
	 * 
	 * 
	 * @param to
	 *            the to
	 * @param fraction
	 *            the fraction
	 * @return the color quad
	 */
	public ColorQuad BlendRGB(ColorQuad to, double fraction) {
		ColorQuad from = this;
		if (fraction >= 1.0)
			return from;
		if (fraction <= 0.0)
			return to;
		from = (from.multiply(fraction)).plus(to.multiply(1.0f - fraction));
		return from;
	}
	
	/**
	 * Multi blend - creates a series of linear color blends across all colors in the array, and returns
	 * the color corresponding to the fraction of the total space across.
	 * 
	 * @param colors
	 *            an array of  the colors
	 * @param fraction
	 *            the fraction
	 * @return the color quad
	 */
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

	/**
	 * Clamp.
	 * 
	 * @param f
	 *            the max value
	 */
	public void Clamp(float f) {
		x = Math.min(x, f);
		y = Math.min(y, f);
		z = Math.min(z, f);
	}

	/**
	 * divides all elements by the fraction c.
	 * 
	 * @param c
	 *            the c
	 * @return the color quad
	 */
	public ColorQuad div(double c) {
		return new ColorQuad(x / c, y / c, z / c);
	}

	/**
	 * Gets the blue component (int).
	 * 
	 * @return blue
	 */
	private int getB() {
		return (int) (z * 255);
	}

	/**
	 * Gets the green component (int).
	 * 
	 * @return green
	 */
	private int getG() {
		return (int) (y * 255);
	}

	/**
	 * Gets the red component. 
	 * 
	 * @return red
	 */
	private int getR() {
		return (int) (x * 255);
	}
	
	/**
	 * Gets whether elements are > 1.
	 * 
	 * @return the out of bounds
	 */
	boolean getOutOfBounds() {
		return x > 1.0f || y > 1.0f || z > 1.0f;
	}


	/**
	 * Hash.
	 * 
	 * @return the long
	 */
	long Hash() {
		return (long) (1000 * x * y * z) % 255;
	}


	/**
	 * HSV to rgb. conversion
	 * 
	 * @param h
	 *            the h
	 * @param s
	 *            the s
	 * @param v
	 *            the v
	 * @return the color quad in RGB space
	 */
	public static ColorQuad HSVtoRGB(double h, double s, double v) {
		ColorQuad rgb = new ColorQuad();
		if (s == 0.0) {
			rgb.Set(v, v, v);
			return rgb;
		}
		if (h == 1.0)
			h = 0;
		h = h * 6.0f;
		int i = (int) h;
		double f, p, q, t;
		f = h - i;
		p = v * (1 - s);
		q = v * (1 - (s * f));
		t = v * (1 - (s * (1 - f)));
		switch (i) {
		case 0:
			rgb.Set(v, t, p);
			break;
		case 1:
			rgb.Set(q, v, p);
			break;
		case 2:
			rgb.Set(p, v, t);
			break;
		case 3:
			rgb.Set(p, q, v);
			break;
		case 4:
			rgb.Set(t, p, v);
			break;
		case 5:
			rgb.Set(v, p, q);
		}
		return rgb;

	}

	/**
	 * HS vto rgb.
	 * 
	 * @return the color quad
	 */
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
		double f, p, q, t;
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

	/**
	 * Inverse.
	 * 
	 * @return the color quad of the inverse color
	 */
	public ColorQuad Inverse() {
		ColorQuad c = new ColorQuad((int) COLORQUAD_MAX_VALUE - getR(),
				(int) COLORQUAD_MAX_VALUE - getG(), (int) COLORQUAD_MAX_VALUE - getB());
		return c;
	}


	/**
	 * Checks if two colors match exactly (rounded to integer).
	 * 
	 * @param cq
	 *            the cq
	 * @return true, if is equal
	 */
	boolean isEqual(ColorQuad cq) {
		return (getR() == cq.getR()) && (getG() == cq.getG()) && (getB() == cq.getB());
	}

	/**
	 * Match quality.
	 * 
	 * @param color
	 *            the color
	 * @return the long
	 */
	long MatchQuality(ColorQuad color) {
		return (((long) getR() - color.getR()) * ((long) getR() - color.getR())
				+ ((long) getG() - color.getG()) * ((long) getG() - color.getG())
				+ ((long) getB() - color.getB()) * ((long) getB() - color.getB()));
	}

	/**
	 * Multiply two colors - r*r, g*g, b*b.
	 * 
	 * @param fraction
	 *            the fraction
	 * @return the color quad
	 */
	public ColorQuad multiply(ColorQuad fraction) {
		ColorQuad c = new ColorQuad(fraction.x * x, fraction.y * y, fraction.z * z);
		return c;
	}

	/**
	 * Multiply color by a scaling fraction.
	 * 
	 * @param c
	 *            the c
	 * @return the color quad
	 */
	public ColorQuad multiply(double c) {
		return new ColorQuad(x * c, y * c, z * c);
	}


	/**
	 * adds colors element by element - .
	 * 
	 * @param p
	 *            the p
	 * @return the color quad
	 */
	public ColorQuad plus(ColorQuad p) {
		ColorQuad c = new ColorQuad(x + p.x, y + p.y, z + p.z);
		c.Clamp(1.0f);
		return c;
	}

	/**
	 * Minus - subtracts one color for another.
	 * 
	 * @param p
	 *            the p
	 * @return the color quad
	 */
	public ColorQuad minus(ColorQuad p) {
		ColorQuad c = new ColorQuad(x - p.x, y - p.y, z - p.z);
		return c;
	}
		
	/**
	 * RG bto hsv.
	 * 
	 * @return a ColorQuad with HSV colors in it.
	 */
	ColorQuad RGBtoHSV() {
		double maxNum, minNum;
		double h = 0, s = 0, v;
		double r = x;
		double g = y;
		double b = z;

		maxNum = Math.max(Math.max(r, g), b);
		minNum = Math.min(Math.min(r, g), b);
		v = maxNum;
		if (maxNum != 0)
			s = (maxNum - minNum) / maxNum;
		else
			return new ColorQuad();

		double rc, gc, bc;
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


	/**
	 * Sets the color to the initial values.
	 * 
	 * @param r
	 *            the r
	 * @param g
	 *            the g
	 * @param b
	 *            the b
	 */
	public void Set(double r, double g, double b) {
		x = (float) r;
		y = (float) g;
		z = (float) b;
	}

	/**
	 * Sets the color to these initial int values (0-255).
	 * 
	 * @param inR
	 *            the in r
	 * @param inG
	 *            the in g
	 * @param inB
	 *            the in b
	 */
	public void Set(int inR, int inG, int inB) {
		Set(inR / COLORQUAD_MAX_VALUE, inG / COLORQUAD_MAX_VALUE, inB / COLORQUAD_MAX_VALUE);
	}


	/**
	 * To string.
	 * 
	 * @return the string
	 */
	String ToString() {
		return "<ColorQuad R=\"" + getR() + "\" G=\"" + getG() + "\" B\"" + getB() + "\"/>";
	}
}
