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

import com.bobandthomas.Morbid.Gadget.GadgetFactoryManager;
import com.bobandthomas.Morbid.Gadget.Scene;

// TODO: Auto-generated Javadoc
/**
 * The Class SceneControlPanel.
 * 
 * @author Thomas Kreek
 */
public class SceneControlPanel extends ControlPanel {

	/** The scene. */
	Scene scene;
	
	/** The bar. */
	ControlPanelSideBar bar;
	
	/**
	 * Instantiates a new scene control panel.
	 * 
	 * @param sc
	 *            the sc
	 * @param name
	 *            the name
	 * @param bar
	 *            the bar
	 */
	public SceneControlPanel(Scene sc, String name, ControlPanelSideBar bar) {
		super(name, true);
		scene = sc;
		this.bar = bar;
		createButton("Add Gadget");
		createLabel("________________");
		banner.setSelected(true);
		activePanel.setVisible(banner.isSelected());

	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#changeValue(java.lang.String, java.lang.Integer)
	 */
	@Override
	public void changeValue(String label, Integer value) {
		if (label.equals("Zoom"))
			scene.setZoom(value/100.0);
		if (label.equals("Add Gadget"))
			GadgetFactoryManager.getOne().selectNewGadget(scene, bar);
			

	}

}
