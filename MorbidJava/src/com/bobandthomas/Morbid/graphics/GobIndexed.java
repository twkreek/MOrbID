package com.bobandthomas.Morbid.graphics;

import java.util.ArrayList;

import com.bobandthomas.Morbid.utils.*;

public class GobIndexed extends GobPoly {

	ArrayList<Integer> indexTable;

	@Override
	public
	GobType Type() {
		return GobType.Indexed;
	}

	GobIndexed() {
	}

	void AddIndex(int i) {
		indexTable.add(i);
	}

	@Override
	public
	Point3D center() {
		return super.center();
	}
};
