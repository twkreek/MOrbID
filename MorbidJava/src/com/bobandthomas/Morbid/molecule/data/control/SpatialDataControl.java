package com.bobandthomas.Morbid.molecule.data.control;

import com.bobandthomas.Morbid.UI.MorbidPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.MorbidEvent;

public class SpatialDataControl extends MorbidPanel {
	Molecule molecule;
	SpatialData sd;
	public SpatialDataControl(SpatialData sd, String name) {
		super(name);

		this.sd = sd;
		molecule = sd.getMolecule();
		int[] resolutions = {5,10,30,50,100,200,500};				
		this.createSpinner("Resolution", resolutions, sd.sideX);
	}

	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("Resolution"))
		{
			sd.setSize(value);
			notifyChange(new MorbidEvent(this));
			return;
		}

	}

}
