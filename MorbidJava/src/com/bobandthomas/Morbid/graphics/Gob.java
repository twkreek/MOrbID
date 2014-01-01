package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.utils.*;



/**
 *  Graphical OBject 
 *  The base class for all graphics primitives.
 *  supplies CLoadableItem functions of name and ID and parenting
 *  The base GOB has a center point and a start point.
 * 
 * @author Thomas Kreek
 */
public abstract class Gob extends CLoadableItem {

	
	/** The Start point. */
	private Vertex point = new Vertex();
	/** LOD - level of detail
	 * helps guide optimizations.  log of number of atoms.  scale spheres accordingly.;
	 * 
	 */
	double LOD = 1;	

	/** The material. */
	private Material material;

	/**
	 * Instantiates a new gob.
	 */
	public Gob() {
	}
	
	/**
	 * Instantiates a new gob.
	 * 
	 * @param point
	 *            the center point
	 */
	public Gob(Point3D point) {
		setPoint(new Vertex(point));
	}
	public Gob(Vertex vertex)
	{
		setPoint(new Vertex(vertex));
	}

	/**
	 * Center.
	 * 
	 * @return the point3 d
	 */
	public Point3D center() {
		return new Point3D(getPoint());
	}


	/**
	 * Intersects. (NotImplelmented)
	 * 
	 * @param vector
	 *            the vector
	 * @return the point3 d list
	 */
	Point3DList Intersects(LineSegment vector) {
		return null;
	}

	/**
	 * Type.
	 * 
	 * @return the gob type derived classes should override this 
	 */
	abstract public GobType Type();

	
	/**
	 * Gets the material.
	 * 
	 * @return the material
	 */
	public Material getMaterial() {
		return material;
	}
	
	/**
	 * Sets the material.
	 * 
	 * @param material
	 *            the new material
	 */
	public void setMaterial(Material material) {
		this.material = material;
		setColor(new ColorQuad(material.getColor()));
	}
	
	/**
	 * Gets the level of detail (log of number of atoms).
	 * 
	 * @return the lod
	 */
	public double getLOD() {
		return LOD;
	}
	
	/**
	 * Sets the level of detail (log of number of atoms).
	 * 
	 * @param lOD
	 *            the new lod
	 */
	public void setLOD(double lOD) {
		LOD = lOD;
	}

	public ColorQuad getColor() {
		return getPoint().getColor();
	}

	public void setColor(ColorQuad color) {
		getPoint().setColor(color);
	}

	public Vertex getPoint() {
		return point;
	}

	void setPoint(Vertex startPoint) {
		this.point = startPoint;
	}
	void setPoint(Point3D startPoint)
	{
		this.point = new Vertex(startPoint);
	}

};
