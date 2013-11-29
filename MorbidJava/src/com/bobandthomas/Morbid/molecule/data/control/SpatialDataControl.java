package com.bobandthomas.Morbid.molecule.data.control;

import javax.swing.JPanel;

import com.bobandthomas.Morbid.UI.ControlPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.utils.MorbidEvent;

public class SpatialDataControl extends ControlPanel {
	Molecule molecule;
	SpatialData sd;
	public SpatialDataControl(SpatialData sd, String name, JPanel parentPanel) {
		super(name, false);
		if (parentPanel != null)
		{
			this.tempChild = child;
			child = parentPanel;
		}

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
			notifyChange(new MorbidEvent(this));
			return;
		}

	}

}
