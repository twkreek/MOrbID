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

public class MorbidMenus extends JMenuBar implements ActionListener {
	
	Morbid applet;
	enum MenuItemList
	{
		FileOpen ("File","Open"),
		FileExit ("File","Exit"),
		EditAddGadget("Edit","AddGadget"),
		ViewScreenShot("View", "ScreenShot")
		;
		
		String parent;
		String name;
		MenuItemList(String parent, String name)
		{
			this.parent = parent;
			this.name = name;
		}
	};

	JMenuItem CreateMenuItem(String parent, String name, KeyStroke accel)
	{
		
		JMenuItem a = new JMenuItem(name);
		if (accel != null) a.setAccelerator(accel);
		a.setActionCommand(parent + "|" +name);
		a.addActionListener(this);
		return a;
	}
	
	public MorbidMenus(Morbid applet)
	{
		super();
		this.applet = applet;
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
		if (e.getActionCommand().equals("File|Open"))
		{
			applet.openFile();
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
		
	}
}
