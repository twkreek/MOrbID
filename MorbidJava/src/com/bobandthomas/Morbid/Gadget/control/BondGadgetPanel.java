package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.BondGadget;
import com.bobandthomas.Morbid.Gadget.BondGadget.BondColor;
import com.bobandthomas.Morbid.Gadget.BondGadget.BondRep;
import com.bobandthomas.Morbid.Gadget.Gadget;

public class BondGadgetPanel extends GadgetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7698093079935344034L;

	/**
	 * Create the panel.
	 */
	BondGadget bg;
	public BondGadgetPanel (BondGadget gadget) {
		super((Gadget) gadget, "BondGadget");
		bg = gadget;
		createCheckbox("Label Bonds", bg.isLabelBO());
				
		createEnumCombo(BondRep.values(), "Bond Rep", bg.getRep());
		createEnumCombo(BondColor.values(), "Bond Color", bg.getColorBy());
				
		createSlider("Bond Radius", 0, 200, (int) (bg.getBondScale()*1000));
	}
	
	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("Label Bonds"))
		{
			bg.setLabelBO(value == 1 ? true: false);
			return;
		}
		if (label.equals("Bond radius"))
		{
			bg.setBondScale(value/1000.0);
			return;
		}
		if (label.equals("Bond Rep"))
		{
			bg.setRep(BondRep.fromInt(value));
			return;
		}
		if (label.equals("Bond Color"))
		{
			bg.setColorBy(BondColor.fromInt(value));
			return;
		}
	}

}
