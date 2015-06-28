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

// TODO: Auto-generated Javadoc
/**
 * The Class MinMax.
 * 
 * @author Thomas Kreek
 */
public class MinMax {
	
	/** The min. */
	public double min;
	
	/** The max. */
	public double max;
	
	/** The highest max. */
	public double highestMax;
	
	/** The dirty. */
	boolean dirty;
	
	/**
	 * Instantiates a new min max.
	 */
	public MinMax()
	{
		reset();
	}
	
	/**
	 * Reset.
	 */
	public void reset()
	{
		min = 100000000;
		max = -1000000000;
		highestMax = 0;
		dirty = true;
	}
	
	/**
	 * Adds the value.
	 * 
	 * @param value
	 *            the value
	 */
	public void addValue(double value)
	{
		if (min > value) min = value;
		if (max < value) max = value;
		if (highestMax < Math.abs(value)) highestMax = Math.abs(value);
	}
	
	/**
	 * Checks if is dirty.
	 * 
	 * @return true, if is dirty
	 */
	public boolean isDirty()
	{
		return dirty;
	}
	
	/**
	 * Square.
	 */
	public void square()
	{
		if (min > 0) return;
		if (-min > max ) max = -min;
		min = -max;
	}
	
	/**
	 * Scale.
	 * 
	 * @param value
	 *            the value
	 */
	public void scale(double value)
	{
		min *= value;
		max *= value;
	}
	
	/**
	 * Gets the fraction.
	 * 
	 * @param point
	 *            the point
	 * @return the fraction
	 */
	public double getFraction(double point)
	{
		return (point - min)/(max-min);
	}
	
	/**
	 * Gets the centered fraction.
	 * 
	 * @param point
	 *            the point
	 * @return the centered fraction
	 */
	public double getCenteredFraction(double point)
	{
		double fraction = (point)/(highestMax);
		return fraction;
	}
	
	/**
	 * Size.
	 * 
	 * @return the double
	 */
	public double size() { return max-min; }
	
	/**
	 * Center.
	 * 
	 * @return the double
	 */
	public double center() { return (max-min)/2; }
	
	/**
	 * Test.
	 * 
	 * @param value
	 *            the value
	 * @return true if value is within minmax, false otherwise;
	 */
	public boolean test(double value)
	{
		return (value > min && value < max);
	}
	
	/**
	 * Gets the min squared.
	 * 
	 * @return the min squared
	 */
	public double getMinSquared()
	{
		return min*min;
	}
	
	/**
	 * Gets the max squared.
	 * 
	 * @return the max squared
	 */
	public double getMaxSquared()
	{
		return max*max;
	}


}
