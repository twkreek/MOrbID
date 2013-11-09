package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.Surface3DGadget;

public class Surface3DGadgetPanel extends GadgetPanel {
	Surface3DGadget g3d;
	public Surface3DGadgetPanel(Surface3DGadget g) {
		super(g, "3D Surface");
		g3d = g;
		createSlider("Threshold", 0, 100, (int)(g.getThresholdFraction()*100), true);
		child.add(g3d.getSd().getControlPanel());
		
	}

	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		
		if (label.equals("Threshold"))
		{
			g3d.setThresholdFraction(value/100.0f);
			System.out.println(g3d.getThresholdFraction() + ", " + g3d.getThreshold());
			return;
		}

	}

}
