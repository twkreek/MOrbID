package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.Point3D;

/**
 * @author Thomas Kreek
 * 
 * A Label is used for 2 dimensional overlay or underlay
 * Points in quadrants will be mapped to the far edge of that quadrant
 * points in the center will be mapped to the center of the screen
 *
 */
public class LabelGob extends StringGob {

	@Override
	public GobType Type() {
		return GobType.Label;
	}

	public LabelGob(String lbl, Point3D pt) {
		super(lbl, pt);
	}

}