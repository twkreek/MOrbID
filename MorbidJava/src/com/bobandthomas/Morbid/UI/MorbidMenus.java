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

import java.awt.AWTException;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import com.bobandthomas.Morbid.Morbid;
import com.bobandthomas.Morbid.Gadget.GadgetFactoryManager;

// TODO: Auto-generated Javadoc
/**
 * The Class MorbidMenus.
 * 
 * @author Thomas Kreek
 */
public class MorbidMenus extends JMenuBar implements ActionListener {
	
	/** The applet. */
	Morbid applet;
	
	/**
	 * The Enum MenuItemList.
	 * 
	 * @author Thomas Kreek
	 */
	enum MenuItemList
	{
		
		/** The File open. */
		FileOpen ("File","Open", 101),
		
		/** The File exit. */
		FileExit ("File","Exit",102),
		
		/** The Scene add gadget. */
		SceneAddGadget("Scene","Add Gadget", 201),
		
		/** The View screen shot. */
		ViewScreenShot("View", "ScreenShot", 401)
		;
		
		/** The parent. */
		String parent;
		
		/** The name. */
		String name;
		
		/** The id. */
		int ID;
		
		/**
		 * Instantiates a new menu item list.
		 * 
		 * @param parent
		 *            the parent
		 * @param name
		 *            the name
		 * @param id
		 *            the id
		 */
		MenuItemList(String parent, String name, int id)
		{
			this.parent = parent;
			this.name = name;
			ID = id;
		}
		
		/**
		 * Gets the action string.
		 * 
		 * @return the action string
		 */
		public String getActionString()
		{
			return parent+"|"+name;
		}
	};

	/**
	 * Creates a menu item.
	 * 
	 * @param ml
	 *            the ml
	 * @param accel
	 *            the accel
	 * @return the j menu item
	 */
	JMenuItem CreateMenuItem(MenuItemList ml, KeyStroke accel)
	{
		
		JMenuItem a = new JMenuItem(ml.name);
		if (accel != null) a.setAccelerator(accel);
		a.setActionCommand(ml.getActionString());
		a.addActionListener(this);
		return a;
	}
	
	/** The menu map. */
	HashMap<String, JMenu> menuMap = new HashMap<String, JMenu>();
	
	/**
	 * Instantiates a new morbid menus.
	 * 
	 * @param applet
	 *            the applet
	 */
	public MorbidMenus(Morbid applet)
	{
		super();
		this.applet = applet;
		for (MenuItemList ml: MenuItemList.values() )
		{
			JMenu m = menuMap.get(ml.parent);
			if (m == null)
			{
				m = new JMenu(ml.parent);
				menuMap.put(ml.parent, m);
				add(m);
			}
			m.add(CreateMenuItem(ml, null));
		}
	}
	
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("File|Open"))
		{
			applet.openFile();
			return;
		}
		if (e.getActionCommand().equals("View|ScreenShot"))
		{
			try {
				Robot robot = new Robot();
		        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd hh mm ss a");
		        Calendar now = Calendar.getInstance();
		        BufferedImage screenShot = robot.createScreenCapture(new Rectangle(
		                Toolkit.getDefaultToolkit().getScreenSize()));
		            ImageIO.write(screenShot, "JPG",
		                    new File("C:\\..." + formatter.format(now.getTime())
		                            + ".jpg"));

			} catch (AWTException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
	        } catch (IOException e2) {
			}
			return;
		}
		if (e.getActionCommand().equals("Scene|Add Gadget"))
		{
			GadgetFactoryManager.getOne().selectNewGadget(applet.getScene(), applet.getGadgetPanel());
			return;
		}
		
	}
}
