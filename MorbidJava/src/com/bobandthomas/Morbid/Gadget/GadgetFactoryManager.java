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
package com.bobandthomas.Morbid.Gadget;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.UI.ControlPanelSideBar;
import com.bobandthomas.Morbid.molecule.Molecule;

// TODO: Auto-generated Javadoc
/**
 * The Class GadgetFactoryManager.
 * 
 * @author Thomas Kreek
 */
public class GadgetFactoryManager extends ArrayList<GadgetFactory> {
	
	/** The name map. */
	HashMap<String, GadgetFactory> nameMap;
	
	/** The active list. */
	ArrayList<Gadget> activeList;

	/**
	 * Instantiates a new gadget factory manager.
	 */
	private GadgetFactoryManager() {
		nameMap = new HashMap<String, GadgetFactory>();
		activeList = new ArrayList<Gadget>();
		add(new AtomGadgetFactory());
		add(new BondGadgetFactory());
		add(new DotSurfaceGadgetFactory());
		add(new Surface3DGadgetFactory());
		add(new RibbonGadgetFactory());
		add(new GadgetFieldLinesFactory());
		add(new GadgetHUDFactory());
	}
	
	/**
	 * Gets the one.
	 * 
	 * @return the one
	 */
	public static GadgetFactoryManager getOne()
	{
		return new GadgetFactoryManager();
	}
	
	/**
	 * Gets the creatable list.
	 * 
	 * @param m
	 *            the m
	 * @return the creatable list
	 */
	public ArrayList<GadgetFactory> getCreatableList(Molecule m)
	{
		ArrayList<GadgetFactory> shortList = new ArrayList<GadgetFactory>();
		for (GadgetFactory f : this)
		{
			if (f.canCreate(m))
				shortList.add(f);
		}
		
		return shortList;
		
	}
	
	/* (non-Javadoc)
	 * @see java.util.ArrayList#add(java.lang.Object)
	 */
	public boolean add(GadgetFactory f)
	{
		super.add(f);
		nameMap.put(f.toString(), f);
		return true;
	}
	
	/**
	 * Creates a new gadget.
	 * 
	 * @param f
	 *            the f
	 * @param s
	 *            the s
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public boolean createNewGadget(GadgetFactory f, Scene s, ControlPanelSideBar p)
	{
		if (! f.canCreate(s.GetMolecule()))return false;
		Gadget g = f.createGadget();
		activeList.add(g);
		s.AddGadget(g, g.layer);
		g.markDirty();
		s.markDirty();
		GadgetPanel panel = f.createPanel(g);
		panel.setVisible(true);
		p.addPanel(panel);
		return true;
	}
	
	/**
	 * Creates a new gadget.
	 * 
	 * @param index
	 *            the index
	 * @param s
	 *            the s
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public boolean createNewGadget(int index, Scene s, ControlPanelSideBar p)
	{
		return createNewGadget(get(index), s, p);
	}

	/**
	 * Creates a new gadget.
	 * 
	 * @param name
	 *            the name
	 * @param s
	 *            the s
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public boolean createNewGadget(String name, Scene s, ControlPanelSideBar p)
	{
		return createNewGadget(nameMap.get(name), s, p);
	}
	
	/**
	 * Select new gadget.
	 * 
	 * @param scene
	 *            the scene
	 * @param p
	 *            the p
	 * @return true, if successful
	 */
	public boolean selectNewGadget(Scene scene, ControlPanelSideBar p)
	{
		GadgetFactory f = (GadgetFactory)JOptionPane.showInputDialog(
                p,
                "select a gadget to display",
                "Available Gadgets",
                JOptionPane.PLAIN_MESSAGE,
                null,
                getCreatableList(scene.GetMolecule()).toArray(),
                "");
		if (f == null) return false;
		return createNewGadget(f, scene, p);
	
	}
}
