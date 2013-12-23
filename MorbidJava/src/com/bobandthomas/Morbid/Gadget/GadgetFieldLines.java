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
		Vertex center = new Vertex(getSpatialData().getPoint(x,y,z));
		center.value = getSpatialData().XYZ(x, y, z);
		Vector3D vec = new Vector3D();
		double newValue = 0;
		
		vec.zero();
		for (int i = x-1; i< x+1; i++)
			for (int j =y-1; j<y+1; j++)
				for (int k = z-1; k< z+1; k++)
				{
					if ( i< 0 || j<0 || k< 0)
						continue;
					Point3D neighbor = getSpatialData().getPoint(i, j, k);
					double dValue = getSpatialData().XYZ(i, j, k) - center.value;;
					newValue += dValue;
					vec  = vec.Add(neighbor.Sub(center).Scale(dValue));
					
				}
		if (vec.Length() < threshold) return null;
		segment[0] = center;
		vec = vec.Scale(scale);
		segment[1] = new Vertex(vec.Add(getSpatialData().getPoint(x, y, z)), newValue);
		return segment;
	}
	@Override
	void Draw(GobList gl) {
		gl.clear();
		getSpatialData().Update();
		MinMax range = getSpatialData().getMinMax();
		GobPoly poly = new GobPoly();
		poly.setMaterial(baseMaterial);
		poly.SetPolyType(GobPolyType.Segments);
		for (int i = 0; i < getSpatialData().Side0(); i+=increment)
			for (int j = 0; j < getSpatialData().Side1(); j+=increment)
				for (int k = 0; k < getSpatialData().Side2(); k +=increment)
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
	public void setScene(Scene s) {
		super.setScene(s);
		setSpatialData(GetMolecule().getSpatialData().getByName("Charge"));
		this.setThreshold(0.2);
	}

}
