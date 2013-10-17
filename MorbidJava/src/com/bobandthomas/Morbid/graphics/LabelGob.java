package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

public class LabelGob extends StringGob {
	// Label Gobs, 4 char (including null) strings.
	// they have lower overhead than String Gobs

	@Override
	public GobType Type() {
		return GobType.Label;
	}

	public LabelGob(String lbl, Point3D pt) {
		super(lbl, pt);
	}

	@Override
	public Point3D center() {
		return p;
	}

}