package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class HudPanel extends JPanel {
	JLabel title;
	JLabel formula;
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
