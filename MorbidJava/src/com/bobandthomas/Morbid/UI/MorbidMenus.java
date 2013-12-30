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

public class MorbidMenus extends JMenuBar implements ActionListener {
	
	Morbid applet;
	enum MenuItemList
	{
		FileOpen ("File","Open", 101),
		FileExit ("File","Exit",102),
		SceneAddGadget("Scene","Add Gadget", 201),
		ViewScreenShot("View", "ScreenShot", 401)
		;
		
		String parent;
		String name;
		int ID;
		MenuItemList(String parent, String name, int id)
		{
			this.parent = parent;
			this.name = name;
			ID = id;
		}
		public String getActionString()
		{
			return parent+"|"+name;
		}
	};

	JMenuItem CreateMenuItem(MenuItemList ml, KeyStroke accel)
	{
		
		JMenuItem a = new JMenuItem(ml.name);
		if (accel != null) a.setAccelerator(accel);
		a.setActionCommand(ml.getActionString());
		a.addActionListener(this);
		return a;
	}
	
	HashMap<String, JMenu> menuMap = new HashMap<String, JMenu>();
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
