package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.molecule.data.CubeArray;
import com.bobandthomas.Morbid.molecule.data.SpatialData;

public abstract class GadgetSpatialData extends Gadget {
	
        SpatialData sd;
		public SpatialData getSd() {
			return sd;
		}
		public void setSd(SpatialData sd) {
			this.sd = sd;
		}

		double threshold;
		double thresholdFraction;
		public double getThreshold() {
			return threshold;
		}
		public void setThreshold(double threshold) {
			this.threshold = threshold;
			thresholdFraction = sd.getMinMax().getFraction(threshold);
			markDirty();
		}
		public double getThresholdFraction() {
			return thresholdFraction;
		}
		public void setThresholdFraction(double thresholdFraction) {
			this.thresholdFraction = thresholdFraction;
			threshold = thresholdFraction *(sd.getMax() - sd.getMin()) + sd.getMin();
			markDirty();
		}

		boolean squared;
		CubeArray.DRAWPLANE currentPlane;
		int SideX() {return sd.sideX; }
		int SideY() {return sd.sideY; }
		int SideZ() { return sd.sideX; }
		boolean Squared; 
		void Resize(int ix, int iy, int iz)
		{
			sd.Resize(ix, iy, iz);

		}
		GadgetSpatialData()
		{
			super();
		}
		void SetSpatialData(SpatialData spd) { sd = spd; setName(getName() + spd.getName()); }


	@Override
	public String getGadgetType() {
			return "Gadget Spatial Data";
	}

	@Override
	abstract void Draw(GobList gl);

}
