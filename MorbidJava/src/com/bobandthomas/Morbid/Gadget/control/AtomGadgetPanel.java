package com.bobandthomas.Morbid.Gadget.control;

import com.bobandthomas.Morbid.Gadget.AtomGadget;

import javax.swing.JCheckBox;
import com.bobandthomas.Morbid.Gadget.AtomGadget.AtomRepType;
import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.molecule.SubstructureMap;

public class AtomGadgetPanel extends GadgetPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4307146117791598304L;
	/**
	 * Create the panel.
	 */
	AtomGadget ag;
	JCheckBox chckbxlabels;
	public AtomGadgetPanel(AtomGadget a) {
		super((Gadget) a, "Atom Gadget");
		ag = a;
		createLabel("Show");
		createCheckbox("Labels", ag.isLabels());
		
		createCheckbox("Metals", ag.isShowMetals());
		
		createCheckbox("Hydrogens", ag.isShowHydrogens());
		
		addColorBy();
		addFilterBy();
				
		createSlider("RadiusScale", 0, 400, (int) (ag.getAtomScale()*100), true);
	}
	@Override
	public void changeValue(String label, Integer value) {
		super.changeValue(label,value);
		if (label.equals("Labels"))
		{
			ag.setLabels(value == 1);
			return;
		}
		
		if (label.equals("Metals"))
		{
			ag.setShowMetals(value == 1);
			return;
		}
		if (label.equals("Hydrogens"))
		{
			ag.setShowHydrogens(value == 1);
			return;
		}
		
		if (label.equals("RadiusScale"))
		{
			ag.setAtomScale(value/100.0f);
			return;
		}
		
		if (label.equals("Atom Rep"))
		{
			ag.setRep(AtomRepType.fromInt(value));
			return;
		}
		if (label.equals("Substructure Filter"))
		{
			SubstructureMap ssm = ag.GetMolecule().getSubstructures();
			
			if (ag.isSubstructureFilter() == (value == 1))
				return; // don't change it again
			ag.setSubstructureFilter(value == 1);
			if (value != 1)
				return;
			
			
			SubstructureSetChooser dlg = new SubstructureSetChooser(ssm, null);
			ag.setSubstructureFilterList(dlg.selectSubstructureRep(ssm));
			
			SubstructureRepListEditor rle = new SubstructureRepListEditor(ssm, ag.getSubstructureFilterList());
			rle.setVisible(true);
			

		}
	}
		

}
