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


package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

// TODO: Auto-generated Javadoc
/**
 * The Class GadgetFieldLines.
 * 
 * @author Thomas Kreek
 */
public class GadgetFieldLines extends GadgetSpatialData {
	
	/** The increment. */
	int increment = 5;
	
	/** The scale. */
	double scale = 0.5;

	/**
	 * Gets the scale.
	 * 
	 * @return the scale
	 */
	public double getScale() {
		return scale;
	}


	/**
	 * Sets the scale.
	 * 
	 * @param scale
	 *            the new scale
	 */
	public void setScale(double scale) {
		this.scale = scale;
		markDirty();
	}


	/**
	 * Gets the increment.
	 * 
	 * @return the increment
	 */
	public int getIncrement() {
		return increment;
	}


	/**
	 * Sets the increment.
	 * 
	 * @param increment
	 *            the new increment
	 */
	public void setIncrement(int increment) {
		this.increment = increment;
		markDirty();
	}


	/**
	 * Instantiates a new gadget field lines.
	 */
	public GadgetFieldLines() {
	}
	

	/**
	 * Gets the vector.
	 * 
	 * @param x
	 *            the x
	 * @param y
	 *            the y
	 * @param z
	 *            the z
	 * @return the vector
	 */
	public Vertex[] getVector(int x, int y, int z)
	{
		Vertex segment[] = new Vertex[2];
		Vertex center = new Vertex(getSpatialData().getPoint(x,y,z));
		center.value = getSpatialData().XYZ(x, y, z);
		Vector3D vec = new Vector3D();
		double newValue = 0;
		
		vec.zero();
		for (int i = x-1; i< x+1; i++)
			for (int j =y-1; j<y+1; j++)
				for (int k = z-1; k< z+1; k++)
				{
					if ( i< 0 || j<0 || k< 0)
						continue;
					Point3D neighbor = getSpatialData().getPoint(i, j, k);
					double dValue = getSpatialData().XYZ(i, j, k) - center.value;;
					newValue += dValue;
					vec  = vec.Add(neighbor.Sub(center).Scale(dValue));
					
				}
		if (vec.Length() < threshold) return null;
		segment[0] = center;
		vec = vec.Scale(scale);
		segment[1] = new Vertex(vec.Add(getSpatialData().getPoint(x, y, z)), newValue);
		return segment;
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetSpatialData#Draw(com.bobandthomas.Morbid.graphics.GobList)
	 */
	@Override
	void Draw(GobList gl) {
		gl.clear();
		getSpatialData().Update();
		MinMax range = getSpatialData().getMinMax();
		GobPoly poly = new GobPoly();
		poly.setMaterial(baseMaterial);
		poly.SetPolyType(GobPolyType.Segments);
		for (int i = 0; i < getSpatialData().Side0(); i+=increment)
			for (int j = 0; j < getSpatialData().Side1(); j+=increment)
				for (int k = 0; k < getSpatialData().Side2(); k +=increment)
				{
					Vertex[] vec= getVector(i,j,k);
					if (vec == null)
						continue;
					vec[0].setColor(getFractionColor(range, vec[0].value));
					poly.AddVertex(vec[0]);
					vec[1].setColor(getFractionColor(range, vec[1].value));
					poly.AddVertex(vec[1]);
				}
					
		gl.add(poly);
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#setScene(com.bobandthomas.Morbid.Gadget.Scene)
	 */
	@Override
	public void setScene(Scene s) {
		super.setScene(s);
		setSpatialData(GetMolecule().getSpatialData().getByName("Charge"));
		this.setThreshold(0.2);
	}

}
