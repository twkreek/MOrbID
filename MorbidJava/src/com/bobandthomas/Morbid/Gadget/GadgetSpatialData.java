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
import com.bobandthomas.Morbid.molecule.data.CubeArray;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.MorbidEvent;


// TODO: Auto-generated Javadoc
/**
 *         The Class GadgetSpatialData is an abstract class that supports all
 *         gadgets that use spatial data - 3D sampling of field data, such as charge,
 *         molecular orbitals, and volume.  This provides management for the SpatialData object
 *         as well as the polarity and fractions to display. * 
 * @author Thomas Kreek
 * 

 */
public abstract class GadgetSpatialData extends Gadget {

	/** The sd. */
	private SpatialData sd;

	/** The current plane. */
	CubeArray.DRAWPLANE currentPlane;
	
	/** The squared. */
	boolean squared;
	
	/** The Squared. */
	boolean Squared;

	/** The threshold. */
	double threshold;
	
	/** The threshold fraction. */
	double thresholdFraction;

	/**
	 * Instantiates a new gadget spatial data.
	 */
	GadgetSpatialData() {
		super();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#getGadgetType()
	 */
	@Override
	public String getGadgetType() {
		return "Gadget Spatial Data";
	}

	/**
	 * Gets the spatial data displayed by this gadget.
	 * 
	 * @return the spatial data
	 */
	public SpatialData getSpatialData() {
		return sd;
	}

	/**
	 * Gets the threshold.
	 * 
	 * @return the threshold
	 */
	public double getThreshold() {
		return threshold;
	}

	/**
	 * Gets the threshold fraction.
	 * 
	 * @return the threshold fraction
	 */
	public double getThresholdFraction() {
		return thresholdFraction;
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent  handleNotify(MorbidEvent event) {
		if (event.getSource() == sd)
			markDirty();
		return super.handleNotify(event);
	}

	/**
	 * Sets the spatial data.
	 * 
	 * @param sd
	 *            the new spatial data
	 */
	public void setSpatialData(SpatialData sd) {
		if (sd != null)
			sd.unRegisterListener(this);
		this.sd = sd;
		sd.registerListener(this);
	}

	/**
	 * Sets the threshold.
	 * 
	 * @param threshold
	 *            the new threshold
	 */
	public void setThreshold(double threshold) {
		double old = this.threshold;
		this.threshold = threshold;
		thresholdFraction = sd.getMinMax().getFraction(threshold);
		markDirty(new MorbidEvent(this, "threshold", old, threshold));
	}

	/**
	 * Sets the threshold fraction.
	 * 
	 * @param thresholdFraction
	 *            the new threshold fraction
	 */
	public void setThresholdFraction(double thresholdFraction) {
		double old = this.thresholdFraction;
		this.thresholdFraction = thresholdFraction;
		threshold = thresholdFraction * (sd.getMax() - sd.getMin())
				+ sd.getMin();
		markDirty(new MorbidEvent(this, "thresholdFraction", old, thresholdFraction));
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.Gadget.Gadget#Draw(com.bobandthomas.Morbid.graphics.GobList)
	 */
	@Override
	abstract void Draw(GobList gl);

	/**
	 * Resize.
	 * 
	 * @param size
	 *            the size
	 */
	void Resize(int size) {
		sd.setSize(size);

	}

	/**
	 * Side x.
	 * 
	 * @return the int
	 */
	int SideX() {
		return sd.sideX;
	}

	/**
	 * Side y.
	 * 
	 * @return the int
	 */
	int SideY() {
		return sd.sideY;
	}

	/**
	 * Side z.
	 * 
	 * @return the int
	 */
	int SideZ() {
		return sd.sideX;
	}

}
