package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.Gadget.RibbonGadget;

public class RibbonGadgetPanel extends GadgetPanel {

	RibbonGadget rg;
	public RibbonGadgetPanel(Gadget g) {
		
		super(g, "Ribbons");
		rg = (RibbonGadget) g;
		addColorBy();
		createCheckbox("Cylinders", rg.isCylinders());
		createSlider("Radius", 0,200, (int) (rg.getRadius()*100));
		
	}

	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("Cylinders"))
		{
			rg.setCylinders(value == 1);
		}
		if (label.equals("Radius"))
		{
			rg.setRadius(value/100.0);
		}

	}

}
