package com.bobandthomas.Morbid.Gadget.control;

import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeListener;
import javax.swing.JComponent;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.UI.ControlPanel;
import com.bobandthomas.Morbid.molecule.SubstructureSet;

public abstract class GadgetPanel extends ControlPanel implements ChangeListener,
		ItemListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -430660293743502924L;
	HashMap<JComponent, String> map;
	Gadget gadget;
	JPanel child;
	JToggleButton banner;

	public GadgetPanel(Gadget g, String name) {
		super(name);
		gadget = g;
		createCheckbox("Visible", gadget.isVisible());
		createSlider("Transparency", 0, 100, 50);
		createCheckbox("Transparent", gadget.isTransparent());
	}

	public abstract void changeValue(String label, Integer value);
	public void handlePanelChange(String label, Integer value)
	{
		super.handlePanelChange(label, value);
		if (label.equals("Visible"))
		{
			gadget.setVisible(value == 1);
		}
		if(label.equals("Transparency"))
		{
			gadget.setTransparency(value/100.0);
		}
		if (label.equals("Transparent"))
		{
			gadget.setTransparent(value == 1);
		}
		if (label.equals("Color by"))
		{
			gadget.setCurrentColorOption(value);
		}
		if (label.equals("SubstructureFilter"))
		{
			SubstructureSet s = subs.get(value);
			if (s.getName().equals("All"))
				gadget.setSubstructureFilter(false);
			else
			{
				gadget.setSubstructureFilter(true);
				gadget.setSubstructureFilterList(s.getDefaultRep());
			}
		}
		changeValue(label,value);
	}
	public void addColorBy()
	{
		this.createLabel("Color By");
		createCombo(gadget.getColorOptions(true), "Color by", 0);
	}
	ArrayList<SubstructureSet> subs = new ArrayList<SubstructureSet>();
	public void addFilterBy()
	{
		subs = new ArrayList<SubstructureSet>();
		for (SubstructureSet s : gadget.GetMolecule().getSubstructures())
		{
			subs.add(s);
		}
		createCombo(subs, "SubstructureFilter", 0);
		
	}

}
