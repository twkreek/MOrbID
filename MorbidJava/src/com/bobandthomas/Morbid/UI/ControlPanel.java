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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ChangeEvent;


// TODO: Auto-generated Javadoc
/**
 * The Class ControlPanel creates embeddable control panels with a header
 * button, and collapsible body.
 * 
 * @author Thomas Kreek
 */
public abstract class ControlPanel extends MorbidPanel  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -430660293743502924L;
	
	/** The banner. */
	JToggleButton banner;
	
	/** The parent. */
	public JPanel parent = null;

	/**
	 * Instantiates a new control panel.
	 * 
	 * @param name
	 *            the name
	 * @param showName
	 *            whether to show the name in a button bar
	 */
	public ControlPanel(String name, boolean showName) {
		super(name);
		setLayout(new BorderLayout());
		setMaximumSize(new Dimension(200, 500));
		setMinimumSize(new Dimension(200, 25));
		if (showName)
		{
			banner = new JToggleButton(name);
			banner.setMaximumSize(new Dimension(200,20));
			banner.setMinimumSize(new Dimension(200,20));
			banner.addActionListener(this);
			add(banner, BorderLayout.NORTH);
		}
		
		activePanel = new MorbidPanel(name + "child"){
			public void changeValue(String label, Integer value)
			{
				
			}

		};
		activePanel.setVisible(!showName || banner.isSelected());
		activePanel.setBorder( BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
		add(activePanel, BorderLayout.CENTER);
		if (!showName) createLabel(name); 
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == banner)
		{
			activePanel.setVisible(banner.isSelected());
			if (parent != null)
			{
				parent.invalidate();
				parent.repaint();
			}
			return;
		}
		super.actionPerformed(e);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#stateChanged(javax.swing.event.ChangeEvent)
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		super.stateChanged(e);

	}


}
