package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;

import toxi.geom.mesh.Face;
import toxi.geom.mesh.Mesh3D;
import toxi.volume.ArrayIsoSurface;


public class Surface3DGadget extends GadgetSpatialData {
	

	public Surface3DGadget() {
		super();
		threshold = 0;
	}
	private void makeFaces(GobPoly gobMesh, Face f, SpatialData charge)
	{
		ColorQuad thisColor = ColorQuad.multiBlend(colors, charge.getClosestValueFraction(new Point3D(f.a)));
		
		gobMesh.AddPoint(new Point3D(f.a), thisColor, new Point3D(f.a.normal).Normalize().invert());
		
		thisColor = ColorQuad.multiBlend(colors, charge.getClosestValueFraction(new Point3D(f.b)));
		
		gobMesh.AddPoint(new Point3D(f.b), thisColor, new Point3D(f.b.normal).Normalize().invert());
		thisColor = ColorQuad.multiBlend(colors, charge.getClosestValueFraction(new Point3D(f.c)));

		gobMesh.AddPoint(new Point3D(f.c), thisColor, new Point3D(f.c.normal).Normalize().invert());
		
	}
	
	private void makeContours(GobList gl)
	{

		Mesh3D mesh = null;
		ArrayIsoSurface surf = new ArrayIsoSurface(this.sd.getVolume());
		mesh = surf.computeSurfaceMesh(mesh, (float) threshold);
		SpatialData charge = GetMolecule().getSpatialData().getByName("Charge");
		charge.Update();
				
		GobPoly gobMesh = new GobPoly();
		gobMesh.setMaterial(mat);
		gobMesh.SetPolyType(GobPolyType.Lines);
		if (mesh.getNumFaces() == 0)
			return;
		ColorQuad[] colors = new ColorQuad[3];
		colors[0] = minusColor;
		colors[1] = baseColor;
		colors[2] = plusColor;

		for (Face f : mesh.getFaces())
		{
			ColorQuad thisColor = ColorQuad.multiBlend(colors, charge.getClosestValueFraction(new Point3D(f.a)));
	
			gobMesh.AddPoint(new Point3D(f.a), thisColor, new Point3D(f.a.normal).Normalize().invert());
			
			thisColor = ColorQuad.multiBlend(colors, charge.getClosestValueFraction(new Point3D(f.b)));
			
			gobMesh.AddPoint(new Point3D(f.b), thisColor, new Point3D(f.b.normal).Normalize().invert());
			thisColor = ColorQuad.multiBlend(colors, charge.getClosestValueFraction(new Point3D(f.c)));

			gobMesh.AddPoint(new Point3D(f.c), thisColor, new Point3D(f.c.normal).Normalize().invert());
			
		
		}
		surf.reset();
		gl.add(gobMesh);
		
	}
	@Override
	void Draw(GobList gl) {
			gl.clear();
			sd = GetMolecule().getSpatialData().getByName("Accessible Volume");
			sd.Update();
			mat = new Material (baseMaterial);
			mat.setColor(new ColorQuad( 0.3,0.3,0.3 ));
			if (transparent)
				mat.setAlpha(alpha);
			makeContours(gl);
			
	}

}
