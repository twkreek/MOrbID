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
package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.UI.CProgressIndicator;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;

// TODO: Auto-generated Javadoc
/**
 * The Class SpatialData.
 * 
 * @author Thomas Kreek
 */
public abstract class SpatialData extends CubeArray implements IChangeNotifier {

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.CubeArray#getMin()
	 */
	@Override
	public double getMin() {
		return logicalRange != null ? logicalRange.min : super.getMin();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.CubeArray#getMax()
	 */
	@Override
	public double getMax() {
		return logicalRange != null ? logicalRange.max : super.getMax();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.molecule.data.CubeArray#getMinMax()
	 */
	@Override
	public MinMax getMinMax() {
		return logicalRange != null ? logicalRange : super.getMinMax();
	}

	/**
	 * The Class SpatialDataType.
	 * 
	 * @author Thomas Kreek
	 */
	static class SpatialDataType {
		
		/** The name. */
		static String name;

		/**
		 * Creates a.
		 * 
		 * @param m
		 *            the m
		 * @return the spatial data
		 */
		static public SpatialData create(Molecule m) {
			return null;
		}
	}

	/** The molecule. */
	Molecule molecule;
	
	/**
	 * Gets the molecule.
	 * 
	 * @return the molecule
	 */
	public Molecule getMolecule() {
		return molecule;
	}

	/** The size name. */
	String sizeName;
	
	/** The type name. */
	String typeName;

	/** The type. */
	short type;
	
	/** The is signed. */
	boolean isSigned;
	
	/**
	 * Checks if is signed.
	 * 
	 * @return true, if is signed
	 */
	public boolean isSigned() {
		return isSigned;
	}

	/** The logical range. */
	MinMax logicalRange;

	/**
	 * Gets the units.
	 * 
	 * @return the string
	 */
	String GetUnits() {
		return "";
	}
	
	/**
	 * Gets the control panel.
	 * 
	 * @return the control panel
	 */
	public abstract SpatialDataControl getControlPanel();

	/**
	 * Update.
	 */
	public void Update() {
		if (!isDirty())
			return;
		Resize();
		CalculateAll();
		markClean();
	}

	/**
	 * Calculate.
	 * 
	 * @param p
	 *            the p
	 * @return the double
	 */
	public abstract double Calculate(Point3D p);

	/**
	 * Instantiates a new spatial data.
	 * 
	 * @param mol
	 *            the mol
	 */
	SpatialData(Molecule mol) {
		super(10); // default 10x10x10
		molecule = mol;
		typeName = "None";
		sizeName = "10x10x10";
		BoundingBox bounds = molecule.GetBounds().cube();
		bounds.min = bounds.min.Scale(1.5);
		bounds.max = bounds.max.Scale(1.5);
		SetBounds(bounds);
		setName("Spatial Data");
		logicalRange = null;
		isSigned = true;
	}

	/**
	 * Defines a range that is meaningful in the physical world, so that
	 * thresholds remain within realistic values For instance, a solvent
	 * accessible surface may have solvent size vaules that range from zero to
	 * the several hundred angstrom, depending on the structure, but only values
	 * in the range of real solvent sizes are useful.
	 * 
	 * @param min
	 *            the min
	 * @param max
	 *            the max
	 */
	void setLogicalRange(double min, double max) {
		logicalRange = new MinMax();
		logicalRange.addValue(min);
		logicalRange.addValue(max);
	}

	/**
	 * Gets the type.
	 * 
	 * @return the short
	 */
	short GetType() {
		return type;
	}
	
	/**
	 * Gets the closest value fraction.
	 * 
	 * @param p
	 *            the p
	 * @return the closest value fraction
	 */
	public double getClosestValueFraction(Point3D p)
	{
		return getMinMax().getFraction((getClosestValue(p)));
	}

	/**
	 * Calculate all.
	 */
	public void CalculateAll() {
		int i, j, k;
		ResetMinMax();
		CProgressIndicator cpi = new CProgressIndicator(getName());
		cpi.SetMax(sideX);
		Point3D pos = new Point3D();
		for (i = 0; i < sideX; i++) {
			pos.x = X(i);
			cpi.SetValue(i);
			if (cpi.IsCanceled())
				break;
			for (j = 0; j < sideY; j++) {
				pos.y = Y(j);
				for (k = 0; k < sideZ; k++) {
					pos.z = Z(k);
					double value = Calculate(pos);
					Set(i, j, k, value);
				}
			}
			String s = new String();
			s = String.format("Min:%4.2f Max%4.2f", getMin(), getMax());
			cpi.SetText(s);
		}
		if (cpi.IsCanceled())
			return;
		markClean();
	}

}
