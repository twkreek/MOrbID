package com.bobandthomas.Morbid.molecule.data.control;

import javax.swing.JPanel;

import com.bobandthomas.Morbid.Gadget.GadgetSpatialData;
import com.bobandthomas.Morbid.UI.ControlPanel;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.molecule.data.SpatialDataList;
import com.bobandthomas.Morbid.utils.MorbidEvent;

public class SpatialDataSelector extends ControlPanel {

	SpatialDataList sdl;
	GadgetSpatialData gsd;
	SpatialData sd;
	SpatialDataControl sdc;
	public SpatialDataSelector(GadgetSpatialData gsd, SpatialDataList sdl, String name, JPanel parentPanel) {
		super(name, false);
		if (parentPanel != null)
		{
			this.tempChild = child;
			child = parentPanel;
		}
		this.gsd = gsd;
		this.sdl = sdl;
		sd = gsd.getSpatialData();
//		this.sideBySide();
		createCombo(sdl, "SpatialData", 0);
		setupSpatialData(sd);
//		this.endSideBySide();
	}
	
	private void setupSpatialData(SpatialData spatial)
	{
		if (sdc != null)
		{
			child.remove(sdc);
			sdc.unRegisterFromAll();
		}
		sdc = spatial.getControlPanel(child);
		sdc.registerListener(this);
		sd = spatial;
		child.add(sdc);
		gsd.setSpatialData(spatial);
		
	}

	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("SpatialData"))
		{
			setupSpatialData(sdl.get(value));	
			gsd.markDirty();
		}
	}

	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		gsd.markDirty();
		return super.handleNotify(source);
	}
	

}
