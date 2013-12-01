package com.bobandthomas.Morbid.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import com.bobandthomas.Morbid.Gadget.AtomGadget;
import com.bobandthomas.Morbid.Gadget.BondGadget;
import com.bobandthomas.Morbid.Gadget.DotSurfaceGadget;
import com.bobandthomas.Morbid.Gadget.GadgetFactoryManager;
import com.bobandthomas.Morbid.Gadget.Surface3DGadget;
import com.bobandthomas.Morbid.Gadget.RibbonGadget;
import com.bobandthomas.Morbid.Gadget.Scene;
import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.Gadget.control.AtomGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.BondGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.RibbonGadgetPanel;
import com.bobandthomas.Morbid.Gadget.control.Surface3DGadgetPanel;
import com.bobandthomas.Morbid.molecule.Molecule;
import com.bobandthomas.Morbid.molecule.data.SpatialData;
import com.bobandthomas.Morbid.molecule.data.SpatialDataAccessibleSurface;
import com.bobandthomas.Morbid.molecule.data.SpatialDataCharge;
import com.bobandthomas.Morbid.molecule.data.SpatialDataMO;

@SuppressWarnings("unused")
public class ControlPanelSideBar extends JPanel {
	Scene scene;
	
	Component glue = Box.createVerticalGlue();

	public ControlPanelSideBar(Scene s)
	{
		super();
		scene = s;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setMinimumSize(new Dimension(200, 20));
		
	}
	public void makeDefaultGadgets(Molecule mol)
	{
	
		int resolution = 30;
		if (mol.NumAtoms() > 100)
			resolution = 10;
 		SpatialData data = new SpatialDataCharge(mol);
		data.setSize(resolution);
 		mol.getSpatialData().add(data);

 		data = new SpatialDataAccessibleSurface(mol);
		data.setSize(resolution);
 		mol.getSpatialData().add(data);

 		if (mol.hasMO())
 		{
	 		SpatialDataMO dataMO = new SpatialDataMO(mol);
			dataMO.setSize(resolution);
			dataMO.setWhichMO(24);
	 		mol.getSpatialData().add(dataMO);
 		}
	}
	public void makeDefaultPanels()
	{
		add(new SceneControlPanel(scene, "Scene", this));
		GadgetFactoryManager.getOne().createNewGadget("Atom Gadget", scene, this);
		GadgetFactoryManager.getOne().createNewGadget("Bond Gadget", scene, this);
		GadgetFactoryManager.getOne().createNewGadget("HUD", scene, this);
		
		add(glue);

	}
	
	public void addPanel(GadgetPanel p)
	{
		remove(glue);
		add(p);
		add(glue);
		revalidate();
		repaint();

	}


}
