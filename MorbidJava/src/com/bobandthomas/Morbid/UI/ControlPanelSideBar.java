/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
package com.bobandthomas.Morbid.UI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

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

// TODO: Auto-generated Javadoc
/**
 * The Class ControlPanelSideBar.
 * 
 * @author Thomas Kreek
 */
@SuppressWarnings("unused")
public class ControlPanelSideBar extends JPanel {
	
	/** The scene. */
	Scene scene;
	
	/** The glue. */
	Component glue = Box.createVerticalGlue();

	/**
	 * Instantiates a new control panel side bar.
	 * 
	 * @param s
	 *            the s
	 */
	public ControlPanelSideBar(Scene s)
	{
		super();
		scene = s;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setMinimumSize(new Dimension(200, 20));
		
	}
	
	/**
	 * Make default gadgets.
	 */
	public void makeDefaultGadgets()
	{
		Molecule mol = scene.GetMolecule();
	
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
	
	/**
	 * Make default panels.
	 */
	public void makeDefaultPanels()
	{
//		add(new SceneControlPanel(scene, "Scene", this));
		GadgetFactoryManager.getOne().createNewGadget("Atom Gadget", scene, this);
		GadgetFactoryManager.getOne().createNewGadget("Bond Gadget", scene, this);
		GadgetFactoryManager.getOne().createNewGadget("HUD", scene, this);
		
		add(glue);

	}
	
	/**
	 * Adds the panel.
	 * 
	 * @param p
	 *            the p
	 */
	public void addPanel(GadgetPanel p)
	{
		remove(glue);
		add(p);
		p.parent = this;
		add(glue);
		revalidate();
		repaint();

	}


}
