package com.bobandthomas.Morbid.UI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

public class MorbidMenus extends JMenuBar implements ActionListener {
	
	enum MenuItemList
	{
		FileOpen ("File","Open"),
		FileExit ("File","Exit"),
		EditAddGadget("Edit","AddGadget")
		;
		
		String parent;
		String name;
		MenuItemList(String parent, String name)
		{
			
		}
	};

	JMenuItem CreateMenuItem(String parent, String name, KeyStroke accel)
	{
		
		JMenuItem a = new JMenuItem(name);
		if (accel != null) a.setAccelerator(accel);
		a.setActionCommand(parent + "|" +name);
		return a;
	}
	
	public MorbidMenus()
	{
		super();
		setVisible(true);
		HashMap<String, JMenu> menuMap = new HashMap<String, JMenu>();
		for (MenuItemList ml: MenuItemList.values() )
		{
			JMenu m = menuMap.get(ml.parent);
			if (m == null)
			{
				m = new JMenu(ml.parent);
				menuMap.put(ml.parent, m);
				add(m);
			}
			m.add(CreateMenuItem(ml.parent, ml.name, null));
		}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Handle Morbid Menu Actions
		
	}
}
