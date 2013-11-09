package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class LabelGob extends StringGob {

	@Override
	public GobType Type() {
		return GobType.Label;
	}

	public LabelGob(String lbl, Point3D pt) {
		super(lbl, pt);
	}

}