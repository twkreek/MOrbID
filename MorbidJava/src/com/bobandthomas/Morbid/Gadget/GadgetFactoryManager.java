package com.bobandthomas.Morbid.Gadget;

import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.Gadget.control.GadgetPanel;
import com.bobandthomas.Morbid.UI.ControlPanelSideBar;
import com.bobandthomas.Morbid.molecule.Molecule;

public class GadgetFactoryManager extends ArrayList<GadgetFactory> {
	HashMap<String, GadgetFactory> nameMap;
	ArrayList<Gadget> activeList;

	private GadgetFactoryManager() {
		nameMap = new HashMap<String, GadgetFactory>();
		activeList = new ArrayList<Gadget>();
		add(new AtomGadgetFactory());
		add(new BondGadgetFactory());
		add(new DotSurfaceGadgetFactory());
		add(new Surface3DGadgetFactory());
		add(new RibbonGadgetFactory());
		add(new GadgetFieldLinesFactory());
	}
	
	public static GadgetFactoryManager getOne()
	{
		return new GadgetFactoryManager();
	}
	
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
	
	public boolean add(GadgetFactory f)
	{
		super.add(f);
		nameMap.put(f.toString(), f);
		return true;
	}
	
	public boolean createNewGadget(GadgetFactory f, Scene s, ControlPanelSideBar p)
	{
		if (! f.canCreate(s.GetMolecule()))return false;
		Gadget g = f.createGadget();
		activeList.add(g);
		s.AddGadget(g, LayerPosition.LayerModel);
		g.markDirty();
		s.markDirty();
		GadgetPanel panel = f.createPanel(g);
		panel.setVisible(true);
		p.addPanel(panel);
		return true;
	}
	public boolean createNewGadget(int index, Scene s, ControlPanelSideBar p)
	{
		return createNewGadget(get(index), s, p);
	}

	public boolean createNewGadget(String name, Scene s, ControlPanelSideBar p)
	{
		return createNewGadget(nameMap.get(name), s, p);
	}
	
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
