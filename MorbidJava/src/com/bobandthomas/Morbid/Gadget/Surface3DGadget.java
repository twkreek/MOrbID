package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;

import toxi.geom.mesh.Face;
import toxi.geom.mesh.Mesh3D;
import toxi.volume.ArrayIsoSurface;


public class Surface3DGadget extends GadgetSpatialData {
	

	SpatialData colorData;
	boolean polar;
	public Surface3DGadget() {
		super();
		threshold = 0;
		colorData = null;
		polar = true;
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
		ArrayIsoSurface surf = new ArrayIsoSurface(this.getSd().getVolume());
		mesh = surf.computeSurfaceMesh(mesh, (float) threshold);
				
		GobPoly gobMesh = new GobPoly();
		gobMesh.setMaterial(mat);
		gobMesh.SetPolyType(GobPolyType.Lines);
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
			gl.clear();
			if (getSd() == null) return;
			getSd().Update();
			//colorData = GetMolecule().getSpatialData().getByName("Charge");
			//colorData.Update();

			mat = new Material (baseMaterial);
			if (polar)
				mat.setColor(minusColor);
			else
				mat.setColor(new ColorQuad( 0.3,0.3,0.3 ));
			if (transparent)
				mat.setAlpha(alpha);
			makeContours(gl);
			if (polar)
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
	public void sceneAdded(Scene s) {
		super.sceneAdded(s);
		this.setSd(GetMolecule().getSpatialData().getByName("MO"));
		markClean(); //TODO shouldn't have to mark it clean to be able to display
		//filtering out notification for dirty objects may block appropriate events
		notifyChange();
	}
}
