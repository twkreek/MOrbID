package com.bobandthomas.Morbid.molecule.data.control;

import com.bobandthomas.Morbid.UI.ControlPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.SpatialData;

public class SpatialDataControl extends ControlPanel {
	Molecule molecule;
	SpatialData sd;
	public SpatialDataControl(SpatialData sd, String name) {
		super(name, false);
		this.sd = sd;
		molecule = sd.getMolecule();
		int[] resolutions = {5,10,30,50,100,200,500};
		int index = 0;
		for (; index < resolutions.length; index++)
			if (sd.sideX == resolutions[index]) break;
				
		this.createSpinner("Resolution", resolutions, index);
		
	}

	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("Resolution"))
		{
			sd.setSize(value);
			return;
		}

	}

}
