package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.GobPoly;
import com.bobandthomas.Morbid.graphics.GobPoly.GobPolyType;
import com.bobandthomas.Morbid.graphics.Vertex;
import com.bobandthomas.Morbid.utils.MinMax;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;

public class GadgetFieldLines extends GadgetSpatialData {
	int increment = 5;
	double scale = 0.5;

	public double getScale() {
		return scale;
	}


	public void setScale(double scale) {
		this.scale = scale;
		markDirty();
	}


	public int getIncrement() {
		return increment;
	}


	public void setIncrement(int increment) {
		this.increment = increment;
		markDirty();
	}


	public GadgetFieldLines() {
	}
	

	public Vertex[] getVector(int x, int y, int z)
	{
		Vertex segment[] = new Vertex[2];
		Vertex center = new Vertex(getSd().getPoint(x,y,z));
		center.value = getSd().XYZ(x, y, z);
		Vector3D vec = new Vector3D();
		double newValue = 0;
		
		vec.Zero();
		for (int i = x-1; i< x+1; i++)
			for (int j =y-1; j<y+1; j++)
				for (int k = z-1; k< z+1; k++)
				{
					if ( i< 0 || j<0 || k< 0)
						continue;
					Point3D neighbor = getSd().getPoint(i, j, k);
					double dValue = getSd().XYZ(i, j, k) - center.value;;
					newValue += dValue;
					vec  = vec.Add(neighbor.Sub(center).Scale(dValue));
					
				}
		if (vec.Length() < threshold) return null;
		segment[0] = center;
		vec = vec.Scale(scale);
		segment[1] = new Vertex(vec.Add(getSd().getPoint(x, y, z)), newValue);
		return segment;
	}
	@Override
	void Draw(GobList gl) {
		gl.clear();
		getSd().Update();
		MinMax range = getSd().getMinMax();
		GobPoly poly = new GobPoly();
		poly.setMaterial(baseMaterial);
		poly.SetPolyType(GobPolyType.Segments);
		for (int i = 0; i < getSd().Side0(); i+=increment)
			for (int j = 0; j < getSd().Side1(); j+=increment)
				for (int k = 0; k < getSd().Side2(); k +=increment)
				{
					Vertex[] vec= getVector(i,j,k);
					if (vec == null)
						continue;
					vec[0].setColor(getFractionColor(range, vec[0].value));
					poly.AddVertex(vec[0]);
					vec[1].setColor(getFractionColor(range, vec[1].value));
					poly.AddVertex(vec[1]);
				}
					
		gl.add(poly);
	}


	@Override
	public void sceneAdded(Scene s) {
		super.sceneAdded(s);
		setSd(GetMolecule().getSpatialData().getByName("Charge"));
		this.setThreshold(0.2);
	}

}
