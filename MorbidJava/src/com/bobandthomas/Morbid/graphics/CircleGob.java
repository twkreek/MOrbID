package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

//Circle Gob - draws a circle with radius r around point p.
//Color is set through base class's methodpublic class CircleGob extends Gob {

public class CircleGob extends Gob {

	public float r;

	@Override
	Point3D center() {
		return StartPoint;
	}

	CircleGob(Point3D pt, float /* Coord */radius) {
		super(pt);
		r = radius;
	}

	@Override
	GobType Type() {
		return GobType.Circle;
	}
};
