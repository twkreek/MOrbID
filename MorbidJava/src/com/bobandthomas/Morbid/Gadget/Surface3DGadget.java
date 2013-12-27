package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.Point3D;

import toxi.geom.mesh.Face;
import toxi.geom.mesh.Mesh3D;
import toxi.volume.ArrayIsoSurface;


public class Surface3DGadget extends GadgetSpatialData {
	

	SpatialData colorData;
	boolean polar;
	private boolean solid;
	public Surface3DGadget() {
		super();
		threshold = 0;
		colorData = null;
		polar = true;
		solid = true;
	}
	public boolean isPolar() {
		return polar;
	}
	public void setPolar(boolean polar) {
		this.polar = polar;
		if (polar) setThresholdFraction(0);
		else setThresholdFraction(0.5);
		markDirty(new MorbidEvent(this, "polar"));
	}
	private void makeFaces(GobPoly gobMesh, Face f)
	{
		Vertex verts[] = new Vertex[3];
		verts[0] = new Vertex(f.a);
		verts[1] = new Vertex(f.b);
		verts[2] = new Vertex(f.c);
		
		if (colorData != null)
		{
			verts[0].setColor(ColorQuad.multiBlend(colors, colorData.getClosestValueFraction(new Point3D(f.a))));
			verts[1].setColor(ColorQuad.multiBlend(colors, colorData.getClosestValueFraction(new Point3D(f.b))));
			verts[2].setColor(ColorQuad.multiBlend(colors, colorData.getClosestValueFraction(new Point3D(f.c))));
		}
		
		for (Vertex v : verts)
		{
			gobMesh.AddVertex(v);
		}
		
	}
	
	private void makeContours(GobList gl)
	{

		Mesh3D mesh = null;
		ArrayIsoSurface surf = new ArrayIsoSurface(this.getSpatialData().getVolume());
		mesh = surf.computeSurfaceMesh(mesh, (float) threshold);
		if (polar && threshold > 0) mesh.flipVertexOrder();
		mesh.computeVertexNormals();
				
		GobPoly gobMesh = new GobPoly();
		gobMesh.setMaterial(mat);
		gobMesh.SetPolyType(solid? GobPolyType.Triangles: GobPolyType.Lines);
		if (mesh.getNumFaces() == 0)
			return;

		for (Face f : mesh.getFaces())
		{
			makeFaces(gobMesh, f);
					
		}
		surf.reset();
		gl.add(gobMesh);
		
	}
	@Override
	void Draw(GobList gl) {
			SpatialData sd = getSpatialData();
			if (sd == null) return;
			sd.Update();
			gl.clear();
			//colorData = GetMolecule().getSpatialData().getByName("Charge");
			//colorData.Update();

			mat = new Material (baseMaterial);
			if (polar && sd.isSigned())
				mat.setColor(minusColor);
			else
				mat.setColor(new ColorQuad( 0.3,0.3,0.3 ));
			if (transparent)
				mat.setAlpha(alpha);
			makeContours(gl);
			if (polar && sd.isSigned())
			{
				threshold = - threshold;
				mat = new Material (baseMaterial);
				mat.setColor(plusColor);
				makeContours(gl);
				threshold = -threshold;
				
			}
			markClean();
	}

	@Override
	public void setScene(Scene s) {
		super.setScene(s);
		setSpatialData(GetMolecule().getSpatialData().getByName("Accessible Volume"));
		getSpatialData().Update();

	}
	public boolean isSolid() {
		return solid;
	}
	public void setSolid(boolean solid) {
		if (this.solid != solid)
		{
			this.solid = solid;
			markDirty();
		}
	}
}
