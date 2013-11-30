package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.*;

public class Gob extends CLoadableItem {

	Point3D Center;
	Point3D StartPoint;
	double LOD = 1;	// helps guide optimizations.  log of number of atoms.  scale spheres accordingly.;

	public ColorQuad Color;

	private Material material;

	public Gob() {
		Color = new ColorQuad();
	}
	public Gob(Point3D point) {
		Color = new ColorQuad();
		StartPoint = new Point3D(point);
		Center = new Point3D(point);
	}

	public Point3D center() {
		return new Point3D(Center);
	}

	/* TODO (intersects not implemented */
	Point3DList Intersects(LineSegment vector) {
		return null;
	}

	public GobType Type() {
		return GobType.Gob;
	}
	public Material getMaterial() {
		return material;
	}
	public void setMaterial(Material material) {
		this.material = material;
		Color = new ColorQuad(material.getColor());
	}
	public double getLOD() {
		return LOD;
	}
	public void setLOD(double lOD) {
		LOD = lOD;
	}

};
