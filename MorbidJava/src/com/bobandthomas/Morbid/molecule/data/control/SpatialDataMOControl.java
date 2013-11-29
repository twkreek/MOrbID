package com.bobandthomas.Morbid.molecule.data.control;

import javax.swing.JPanel;

import com.bobandthomas.Morbid.molecule.data.SpatialDataMO;

public class SpatialDataMOControl extends SpatialDataControl {
	SpatialDataMO sdmo;

	public SpatialDataMOControl(SpatialDataMO sdmo, String name, JPanel parentPanel) {
		super(sdmo, name, parentPanel);
		
		this.sdmo = sdmo;
		createCombo(molecule.getMo().getMONameList(), "Molecular Orbital", sdmo.getWhichMO());
	}

	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Molecular Orbital"))
			sdmo.setWhichMO(value);

	}


}
