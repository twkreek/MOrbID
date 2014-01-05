package com.bobandthomas.Morbid.graphics;

import toxi.geom.Vec3D;

import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Vector3D;

public class Vertex extends Point3D {
	Vector3D normal;
	ColorQuad color;
	public double value; 
	
	public void setPoint(Point3D p)
	{
		x=p.x;
		y=p.y;
		z=p.z;
	}
	
	public Vector3D getNormal() {
		return normal;
	}

	public void setNormal(Vector3D normal) {
		this.normal = normal;
	}
	
	public Vertex invertNormal()
	{
		normal = normal.invert();
		return this;
	}

	public ColorQuad getColor() {
		return color;
	}

	public void setColor(ColorQuad color) {
		this.color = color;
	}
	
	public boolean hasNormal()
	{
		return normal!= null;
	}
	public boolean hasColor()
	{
		return color!=null;
	}

	public Vertex(double x1, double y1, double z1) {
		super(x1, y1, z1);
	}

	public Vertex(int x1, int y1, int z1) {
		super(x1, y1, z1);
	}

	public Vertex(Point3D n1) {
		super(n1);
	}

	public Vertex(Point3D n1, double value) {
		super(n1);
		this.value = value;
	}

	public Vertex(Point3D n1, Vector3D normal) {
		super(n1);
		this.normal = normal;
	}
	public Vertex(Point3D p, ColorQuad cq, Vector3D n)
	{
		super(p);
		this.normal = n;
		this.color = cq;
	
	}
	public Vertex(toxi.geom.mesh.Vertex v)
	{
		super(new Point3D(v));
		normal = new Vector3D(v).Normalize().invert();
	}

	public Vertex(Point3D n1, ColorQuad cq, double value) {
		super(n1);
		this.value = value;
		this.color = cq;
	}

	public Vertex() {
	}

	public Vertex(Vec3D vec) {
		super(vec);
	}

	

}
