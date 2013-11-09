package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.molecule.data.CubeArray;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.IChangeNotifier;

public abstract class GadgetSpatialData extends Gadget {

	private SpatialData sd;

	CubeArray.DRAWPLANE currentPlane;
	boolean squared;
	boolean Squared;

	double threshold;
	double thresholdFraction;

	GadgetSpatialData() {
		super();
	}

	@Override
	public String getGadgetType() {
		return "Gadget Spatial Data";
	}

	public SpatialData getSd() {
		return sd;
	}

	public double getThreshold() {
		return threshold;
	}

	public double getThresholdFraction() {
		return thresholdFraction;
	}

	@Override
	public boolean  handleNotify(IChangeNotifier source) {
		if (!super.handleNotify(source)) return false;
		if (source == sd)
			markDirty();
		return false;
	}

	public void setSd(SpatialData sd) {
		if (sd != null)
			sd.unRegisterListener(this);
		this.sd = sd;
		sd.registerListener(this);
	}

	public void setThreshold(double threshold) {
		this.threshold = threshold;
		thresholdFraction = sd.getMinMax().getFraction(threshold);
		markDirty();
	}

	public void setThresholdFraction(double thresholdFraction) {
		this.thresholdFraction = thresholdFraction;
		threshold = thresholdFraction * (sd.getMax() - sd.getMin())
				+ sd.getMin();
		markDirty();
	}

	@Override
	abstract void Draw(GobList gl);

	void Resize(int size) {
		sd.setSize(size);

	}

	void SetSpatialData(SpatialData spd) {
		sd = spd;
		setName(getName() + spd.getName());
	}

	int SideX() {
		return sd.sideX;
	}

	int SideY() {
		return sd.sideY;
	}

	int SideZ() {
		return sd.sideX;
	}

}
