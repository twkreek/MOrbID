/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IPropertyAccessor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptor;
import com.bobandthomas.Morbid.utils.IPropertyDescriptorList;
import com.bobandthomas.Morbid.utils.IPropertySetter;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.PropertyAccessor;
import com.bobandthomas.Morbid.utils.MPropertyDescriptorList;

import toxi.geom.mesh.Face;
import toxi.geom.mesh.Mesh3D;
import toxi.volume.ArrayIsoSurface;

// TODO: Auto-generated Javadoc
/**
 * The Class Surface3DGadget.
 * 
 * @author Thomas Kreek
 */
public class Surface3DGadget extends GadgetSpatialData {

	/** The color data. */
	SpatialData colorData;
	
	/** is it polar. */
	boolean polar;
	
	/** is it solid. */
	private boolean solid;
	
	/** The drawing negative. */
	//private boolean drawingNegative;

	/**
	 * Instantiates a new surface3 d gadget.
	 */
	public Surface3DGadget() {
		super();
		threshold = 0;
		colorData = null;
		polar = true;
		solid = true;
		baseMaterial.setkDiffuse(0.001);
		baseMaterial.setkAmbient(0.1);
		baseMaterial.setSpecularity(128);
	}

	/**
	 * Checks if is polar.
	 * 
	 * @return true, if is polar
	 */
	public boolean isPolar() {
		return polar;
	}

	/**
	 * Sets the polar.
	 * 
	 * @param polar
	 *            the new polar
	 */
	public void setPolar(boolean polar) {
		this.polar = polar;
		if (polar)
			setThreshold(0);
		else
			setThresholdFraction(0.5);
		markDirty(new MorbidEvent(this, "polar"));
	}

	/**
	 * Make faces.
	 * 
	 * @param gobMesh
	 *            the gob mesh
	 * @param f
	 *            the f
	 */
	private void makeFaces(GobPoly gobMesh, Face f) {
		Vertex verts[] = new Vertex[3];
		verts[0] = new Vertex(f.a);
		verts[1] = new Vertex(f.b);
		verts[2] = new Vertex(f.c);
		for (Vertex v : verts) {
			v.invertNormal();
		}
		if (colorData != null && !polar) {
			verts[0].setColor(ColorQuad.multiBlend(colors,
					colorData.getClosestValueFraction(new Point3D(f.a))));
			verts[1].setColor(ColorQuad.multiBlend(colors,
					colorData.getClosestValueFraction(new Point3D(f.b))));
			verts[2].setColor(ColorQuad.multiBlend(colors,
					colorData.getClosestValueFraction(new Point3D(f.c))));
		}

		for (Vertex v : verts) {
			gobMesh.AddVertex(v);
		}

	}

	/**
	 * Make contours.
	 * 
	 * @param gl
	 *            the gl
	 */
	private void makeContours(GobList gl) {

		Mesh3D mesh = null;
		ArrayIsoSurface surf = new ArrayIsoSurface(this.getSpatialData()
				.getVolume());
		mesh = surf.computeSurfaceMesh(mesh, (float) threshold);

		GobPoly gobMesh = new GobPoly();
		gobMesh.setMaterial(mat);
		gobMesh.SetPolyType(solid ? GobPolyType.Triangles : GobPolyType.Lines);
		if (mesh.getNumFaces() == 0)
			return;

		for (Face f : mesh.getFaces()) {
			makeFaces(gobMesh, f);

		}
		surf.reset();
		gl.add(gobMesh);

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetSpatialData#Draw(com.bobandthomas.Morbid.graphics.GobList)
	 */
	@Override
	void Draw(GobList gl) {
		SpatialData sd = getSpatialData();
		if (sd == null)
			return;
		sd.Update();
		gl.clear();

		mat = new Material(baseMaterial);
		if (transparent)
			mat.setAlpha(alpha);
		if (polar && sd.isSigned())
			mat.setColor(minusColor);
		else
			mat.setColor(new ColorQuad(0.3, 0.3, 0.3));
		makeContours(gl);
		if (polar && sd.isSigned()) {
			threshold = -threshold;
			mat = new Material(baseMaterial);
			mat.setColor(plusColor);
			makeContours(gl);
			threshold = -threshold;

		}
		markClean();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#setScene(com.bobandthomas.Morbid.Gadget.Scene)
	 */
	@Override
	public void setScene(Scene s) {
		super.setScene(s);
		setSpatialData(GetMolecule().getSpatialData().getByName(
				"Accessible Volume"));
		getSpatialData().Update();

		colorData = GetMolecule().getSpatialData().getByName("Charge");
		colorData.Update();
	}

	/**
	 * Checks if is solid.
	 * 
	 * @return true, if is solid
	 */
	public boolean isSolid() {
		return solid;
	}

	/**
	 * Sets the solid.
	 * 
	 * @param solid
	 *            the new solid
	 */
	public void setSolid(boolean solid) {
		if (this.solid != solid) {
			this.solid = solid;
			markDirty();
		}
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.GadgetSpatialData#setSpatialData(com.bobandthomas.Morbid.molecule.data.SpatialData)
	 */
	@Override
	public void setSpatialData(SpatialData sd) {
		super.setSpatialData(sd);
		setPolar(sd.isSigned());

	}

	/** The property descriptor. */
	static IPropertyDescriptorList propertyDescriptor = new MPropertyDescriptorList() {

		@Override
		public void initialize() {
			addPropertyDescriptor(0, "Name", String.class, false,
					new IPropertySetter() {
						public String get(Object obj) {
							return ((Surface3DGadget) obj).getName();
						}

						public boolean set(Object obj, Object v) {
							((Surface3DGadget) obj).setName((String) v);
							return true;
						}
					});
			addPropertyDescriptor(1, "Polar", Boolean.class, true);
			addPropertyDescriptor(2, "Threshold", Double.class, true);
			addPropertyDescriptor(3, "Threshold%", Double.class, true);
			addPropertyDescriptor(4, "Spatial Data Type", String.class, false);

		}

	};
	
	/** The access. */
	IPropertyAccessor access = new PropertyAccessor(this, propertyDescriptor) {
		@Override
		public Object getProperty(IPropertyDescriptor ipd) {
			switch (ipd.getIndex()) {
			case 0:
				return Surface3DGadget.this.getName();
			case 1:
				return Surface3DGadget.this.isPolar();
			case 2:
				return Surface3DGadget.this.getThreshold();
			case 3:
				return Surface3DGadget.this.getThresholdFraction();
			case 4:
				return Surface3DGadget.this.getSpatialData().toString();
			}
			return null;
		}

		@Override
		public void setProperty(IPropertyDescriptor ipd, Object value) {
			switch (ipd.getIndex()) {
			case 0:
				Surface3DGadget.this.setName((String) value);
				return;
			case 1:
				Surface3DGadget.this.setPolar((Boolean) value);
				return;
			case 2:
				Surface3DGadget.this.setThreshold((Double) value);
				return;
			case 3:
				Surface3DGadget.this.setThresholdFraction((Double) value);
				return;
			case 4:
			}

		}
	};

	// {{ IAccessorDelegates

	/**
	 * Gets the property.
	 * 
	 * @param name
	 *            the name
	 * @return the property
	 */
	public Object getProperty(String name) {
		return access.getProperty(name);
	}

	/**
	 * Sets the property.
	 * 
	 * @param name
	 *            the name
	 * @param value
	 *            the value
	 */
	public void setProperty(String name, Object value) {
		access.setProperty(name, value);
	}

	/**
	 * Gets the property.
	 * 
	 * @param index
	 *            the index
	 * @return the property
	 */
	public Object getProperty(int index) {
		return access.getProperty(index);
	}

	/**
	 * Sets the property.
	 * 
	 * @param index
	 *            the index
	 * @param value
	 *            the value
	 */
	public void setProperty(int index, Object value) {
		access.setProperty(index, value);
	}

	/**
	 * Gets the property.
	 * 
	 * @param desc
	 *            the desc
	 * @return the property
	 */
	public Object getProperty(IPropertyDescriptor desc) {
		return access.getProperty(desc);
	}

	/**
	 * Sets the property.
	 * 
	 * @param desc
	 *            the desc
	 * @param value
	 *            the value
	 */
	public void setProperty(IPropertyDescriptor desc, Object value) {
		access.setProperty(desc, value);
	}

	/**
	 * Gets the descriptors.
	 * 
	 * @return the descriptors
	 */
	public IPropertyDescriptorList getDescriptors() {
		return access.getDescriptors();
	}

	// }}

}
