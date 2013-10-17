package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.DotSurfaceGadget;

public class DotSurfaceGadgetPanel extends GadgetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8399439673593429594L;
	DotSurfaceGadget dsg;

	public DotSurfaceGadgetPanel(DotSurfaceGadget g) {
		super(g, "Dot Surface");
		dsg = g;
		
		createEnumCombo(DotSurfaceGadget.DotsColorBy.values(), "Color By", g.getColorBy());
		createSlider("RadiusScale", 0, 200, (int) (dsg.getRadiusScale()*100));
	
	}
	@Override
	public void changeValue(String label, Integer value) {
		
		if (label.equals("RadiusScale"))
		{
			dsg.setRadiusScale(value/100.0f);
			return;
		}
		if (label.equals("Color By"))
		{
			dsg.setColorBy(DotSurfaceGadget.DotsColorBy.fromInt(value));
			return;
		}
	}	
	

}
