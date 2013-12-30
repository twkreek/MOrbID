package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EtchedBorder;
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
