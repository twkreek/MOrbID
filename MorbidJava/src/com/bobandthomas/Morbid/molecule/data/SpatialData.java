package com.bobandthomas.Morbid.molecule.data;

import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataControl;
import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.wrapper.CProgressIndicator;

public abstract class SpatialData extends CubeArray {

	@Override
	public double getMin() {
		return logicalRange != null ? logicalRange.min : super.getMin();
	}

	@Override
	public double getMax() {
		return logicalRange != null ? logicalRange.max : super.getMax();
	}

	@Override
	public MinMax getMinMax() {
		return logicalRange != null ? logicalRange : super.getMinMax();
	}

	static class SpatialDataType {
		static String name;

		static public SpatialData create(Molecule m) {
			return null;
		}
	}

	Molecule molecule;
	public Molecule getMolecule() {
		return molecule;
	}

	String sizeName;
	String typeName;

	short type;
	boolean isSigned;
	MinMax logicalRange;

	String GetUnits() {
		return "";
	}
	public abstract SpatialDataControl getControlPanel();

	public void Update() {
		if (!isDirty())
			return;
		Resize();
		CalculateAll();
		markClean();
	}

	public abstract double Calculate(Point3D p);

	SpatialData(Molecule mol) {
		super(10); // default 10x10x10
		molecule = mol;
		typeName = "None";
		sizeName = "10x10x10";
		markDirty();
		BoxType bounds = molecule.GetBounds().cube();
		bounds.min = bounds.min.Scale(1.5);
		bounds.max = bounds.max.Scale(1.5);
		SetBounds(bounds);
		setName("Spatial Data");
		logicalRange = null;
	}

	/**
	 * Defines a range that is meaningful in the physical world, so that thresholds remain within realistic values
	 * For instance, a solvent accessible surface may have solvent size vaules that range from zero to the several hundred
	 * angstrom, depending on the structure, but only values in the range of real solvent sizes are useful.
	 * @param min
	 * @param max
	 */
	void setLogicalRange(double min, double max) {
		logicalRange = new MinMax();
		logicalRange.addValue(min);
		logicalRange.addValue(max);
	}

	short GetType() {
		return type;
	}
	
	public double getClosestValueFraction(Point3D p)
	{
		return getMinMax().getFraction((getClosestValue(p)));
	}

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
