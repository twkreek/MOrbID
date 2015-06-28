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

import javax.swing.JLabel;
import javax.swing.JPanel;

// TODO: Auto-generated Javadoc
/**
 * The Class HudPanel.
 * 
 * @author Thomas Kreek
 */
public class HudPanel extends JPanel {
	
	/** The title. */
	JLabel title;
	
	/** The formula. */
	JLabel formula;
	
	/**
	 * Instantiates a new hud panel.
	 */
	public HudPanel()
	{
		setLayout(new BorderLayout());
		//Create new areas
		 title = new JLabel();
		 title.setMinimumSize(new Dimension(200, 20));
		 add(title, BorderLayout.NORTH);
		 title.setText("title is here");
		 title.setOpaque(true);

		 formula = new JLabel();
		 formula.setMinimumSize(new Dimension(200, 20));
		 add(formula, BorderLayout.SOUTH);
		 formula.setText("formula is here");
		 setVisible(true);
	}

}
