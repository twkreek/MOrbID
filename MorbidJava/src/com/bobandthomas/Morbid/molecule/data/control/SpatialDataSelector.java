package com.bobandthomas.Morbid.molecule.data.control;

import com.bobandthomas.Morbid.Gadget.GadgetSpatialData;
import com.bobandthomas.Morbid.UI.ControlPanel;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.molecule.data.SpatialDataList;
import com.bobandthomas.Morbid.utils.MorbidEvent;

/**
 * The Class SpatialDataSelector is an embedded control panel that allows selection of the spatial data (SD)
 * to use for this gadget. It contains a dynamic embedded control panel that allows configuration specific
 * to the currently selected SD.
 * 
 * @author Thomas Kreek
 */
public class SpatialDataSelector extends ControlPanel {

	/** The spatial data list. */
	SpatialDataList spatialDataList;
	
	/** The gadget. */
	GadgetSpatialData gadget;
	
	/** The sd. */
	SpatialData sd;
	
	/** The current sdc. */
	SpatialDataControl currentSDC;
	
	
	/**
	 * Instantiates a new spatial data selector.
	 * 
	 * @param gadget
	 *            the gadget that represents the spatial data
	 * @param spatialDataList
	 *            the spatial data list
	 * @param name
	 *            the name
	 * @param parentPanel
	 *            the parent panel
	 */
	public SpatialDataSelector(GadgetSpatialData gadget, SpatialDataList spatialDataList, String name) {
		super(name, false);
		this.gadget = gadget;
		this.spatialDataList = spatialDataList;
		
		sd = gadget.getSpatialData();
		createCombo(spatialDataList, "SpatialData", spatialDataList.indexOf(sd));
		setupSpatialData(sd);
	}
	
	/**
	 * Sets the up the sub panel for the newly selected spatial data object.
	 * 
	 * @param spatial
	 *            the spatial data to represent
	 */
	private void setupSpatialData(SpatialData spatial)
	{
		if (currentSDC != null)
		{
			activePanel.remove(currentSDC);
			currentSDC.unRegisterFromAll();
			activePanel.revalidate();
			activePanel.repaint();
		}
		currentSDC = spatial.getControlPanel();
		currentSDC.registerListener(this);
		sd = spatial;
		gadget.setSpatialData(spatial);
		activePanel.add(currentSDC);
		activePanel.revalidate();
		activePanel.repaint();
		
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("SpatialData"))
		{
			setupSpatialData(spatialDataList.get(value));	
			gadget.markDirty();
		}
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		gadget.markDirty();
		return super.handleNotify(source);
	}
	

}
