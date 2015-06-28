package com.bobandthomas.Morbid.utils;

import toxi.geom.Quaternion;

public class Vector4D extends Vector3D {
	double w;
	public double getW() {
		return w;
	}
	public void setW(double w) {
		this.w = w;
	}
	
	Vector4D(Quaternion quat)
	{
		float[] aa = quat.toAxisAngle();
		x = aa[0];
		y=aa[1];
		z=aa[2];
		w=aa[3];
	}
	
}
