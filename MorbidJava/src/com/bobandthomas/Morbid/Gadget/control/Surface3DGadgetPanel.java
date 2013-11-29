package com.bobandthomas.Morbid.Gadget.control;

import javax.swing.JLabel;

import com.bobandthomas.Morbid.Gadget.Surface3DGadget;
import com.bobandthomas.Morbid.UI.Logger;
import com.bobandthomas.Morbid.molecule.data.control.SpatialDataSelector;
import com.bobandthomas.Morbid.utils.MorbidEvent;

public class Surface3DGadgetPanel extends GadgetPanel {
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		return super.handleNotify(source);
	}

	Surface3DGadget g3d;
	JLabel thrLabel;
	SpatialDataSelector sds;
	public Surface3DGadgetPanel(Surface3DGadget g) {
		super(g, "3D Surface");
		g3d = g;
		createSlider("Threshold", 0, 100, (int)(g.getThresholdFraction()*100), true);
		thrLabel = (JLabel) getByName("Label Threshold");
		setThresholdLabel();
		createCheckbox("Solid Surface", g.isSolid());
		createCheckbox("Polar", g.isPolar());
		sds = new SpatialDataSelector(g3d, g3d.GetMolecule().getSpatialData(), "Choose Spatial Data", child);
		sds.registerListener(this);
		child.add(sds);
		
	}
	private void setThresholdLabel()
	{
		thrLabel.setText(String.format("Threshold\n%4.2f %4.2f %4.2f", g3d.getSd().getMin(), g3d.getThreshold(), g3d.getSd().getMax()));
	}

	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label, value);
		
		if (label.equals("Threshold"))
		{
			g3d.setThresholdFraction(value/100.0f);
			Logger.addMessage(g3d, String.format("Fraction: %5.3f, Threshold: %5.3f", g3d.getThresholdFraction(), g3d.getThreshold()));
			setThresholdLabel();
			return;
		}

		if (label.equals("Solid Surface"))
		{
			g3d.setSolid(value == 1);
			return;
		}
		if (label.equals("Polar"))
		{
			g3d.setPolar(value == 1);
			setThresholdLabel();
			
			return;
		}
	}

}
