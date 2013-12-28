package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.event.ChangeEvent;


/**
 * The Class ControlPanel
 *         creates embeddable control panels with a header button, and collapsible
 *         body
 * 
 * @author Thomas Kreek
 * 

 */
public abstract class ControlPanel extends MorbidPanel  {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = -430660293743502924L;
	
	/** The banner. */
	JToggleButton banner;

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
		setMaximumSize(new Dimension(200, 500));
		setMinimumSize(new Dimension(200, 40));
		if (showName)
		{
			banner = new JToggleButton(name);
			banner.setMaximumSize(new Dimension(200,10));
			banner.setMinimumSize(new Dimension(200,10));
			banner.addActionListener(this);
			add(banner, BorderLayout.NORTH);
		}
		
		child = new JPanel(new GridLayout(0,1,0,0));
		child.setVisible(!showName || banner.isSelected());
		add(child, BorderLayout.CENTER);
		if (!showName) createLabel(name); 
	}


	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidPanel#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == banner)
		{
			child.setVisible(banner.isSelected());
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
