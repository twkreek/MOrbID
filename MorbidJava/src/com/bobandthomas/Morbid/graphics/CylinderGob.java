package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class CylinderGob extends GobVector {

	private float r;

	public CylinderGob(Point3D start, Point3D end, float /* Coord */rad) {
		super(start, end);
		r = rad;
	}

	@Override
	GobType Type() {
		return GobType.Cylinder;
	}

	public void SetRadius(float /* Coord */rad) {
		r = rad;
	}

	public float getRadius() {
		return r;
	}

}
