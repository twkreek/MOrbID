package com.bobandthomas.Morbid.utils;

public class MinMax {
	public double min;
	public double max;
	public double highestMax;
	boolean dirty;
	public MinMax()
	{
		reset();
	}
	public void reset()
	{
		min = 100000000;
		max = -1000000000;
		highestMax = 0;
		dirty = true;
	}
	public void addValue(double value)
	{
		if (min > value) min = value;
		if (max < value) max = value;
		if (highestMax < Math.abs(value)) highestMax = Math.abs(value);
	}
	public boolean isDirty()
	{
		return dirty;
	}
	
	public void square()
	{
		if (min > 0) return;
		if (-min > max ) max = -min;
		min = -max;
	}
	public void scale(double value)
	{
		min *= value;
		max *= value;
	}
	
	public double getFraction(double point)
	{
		return (point - min)/(max-min);
	}
	public double getCenteredFraction(double point)
	{
		double fraction = (point)/(highestMax);
		return fraction;
	}
	public double size() { return max-min; }
	public double center() { return (max-min)/2; }
	
	/**
	 * @param value
	 * @return true if value is within minmax, false otherwise;
	 */
	public boolean test(double value)
	{
		return (value > min && value < max);
	}
	
	public double getMinSquared()
	{
		return min*min;
	}
	public double getMaxSquared()
	{
		return max*max;
	}


}
