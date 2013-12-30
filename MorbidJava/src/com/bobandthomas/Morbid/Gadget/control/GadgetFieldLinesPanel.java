package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.GadgetFieldLines;

public class GadgetFieldLinesPanel extends GadgetPanel {
	GadgetFieldLines g3d;
	int increments[]={1,2,5,10};
	public GadgetFieldLinesPanel(GadgetFieldLines g) {
		super(g, "Field Lines");
		g3d = g;

		int increments[]={1,2,5,10};
		
		createSlider("Threshold", 0, 100, (int)(g.getThreshold()*100), true);
		createSlider("Scale", 0, 100, (int)(g3d.getScale()*100), true);
		createSpinner("Increment" , increments, 1);
	}

	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		if (label.equals("Threshold"))
		{
			g3d.setThreshold(value/100.0f);
			return;
		}
		if (label.equals("Scale"))
		{
			g3d.setScale(value/100.0f);
			return;
		}
		if (label.equals("Increment"))
		{
			g3d.setIncrement(value);
		}

	}

}
