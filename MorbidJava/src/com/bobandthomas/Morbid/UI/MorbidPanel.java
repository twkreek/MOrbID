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

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.border.CompoundBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.bobandthomas.Morbid.utils.ChangeNotifier;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.wrapper.Logger;

/**
 * The Class MorbidPanel.
 *        base level panel for control panels.  Contains convenience functions to create
 *        basic controls consisten with overall Morbid look and feel.  This panel is used in dialogs
 *        as well as the side panel.  
 *        All controls use this class as a listener. 
 *        Derived classes override ChangeValue to handle events from controls they created
 *          
 * @author Thomas Kreek
 * 
 */
public abstract class MorbidPanel extends JPanel implements ChangeListener,
ItemListener, ActionListener, IChangeNotifier  {

	/** The map of component(control) to the string. */
	HashMap<JComponent, String> map;
	
	/** by name lookup for controls. */
	HashMap<String, JComponent> byName;
	
	/** The current active panel that contains the controls. */
	protected JPanel activePanel;
	
	/** The panelStack - used when making sub panels. */
	protected Stack<JPanel> panelStack = new Stack<JPanel>()
			{
				@Override
				/** pushes the current panel onto the stack, and sets the actve panel to "newPanel" */
				public JPanel push(JPanel newPanel)
				{
					super.push(activePanel);
					activePanel = newPanel;
					return newPanel;
					
				}
				@Override
				/** sets the active panel to the pop of the stack, and returns the active panel */
				public JPanel pop()
				{
					return (activePanel =  super.pop());					
				}
			};

	/**
	 * Instantiates a new morbid panel.
	 * 
	 * @param name
	 *            the name of the panel
	 */
	public MorbidPanel(String name) {
		map = new HashMap<JComponent, String>();
		byName = new HashMap<String, JComponent>();

		setBorder(new CompoundBorder());
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		activePanel = this;
		//This layout can be replaced in the constructor of the subclass

	}

	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 * 
	 * Handles checkbox and button messages
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().getClass().equals(JCheckBox.class)) {
			JCheckBox source = (JCheckBox) e.getSource();
			handlePanelChange(map.get(source), source.isSelected() ? 1 : 0);
			return;
		}	
		if (e.getSource().getClass().equals(JButton.class)) {
			/* buttongs must always fire, so null lastValue will always be different.
			 *  this bypasses the code that prevents duplicate firing
			*/
			lastValue = null; 
			JButton source = (JButton) e.getSource();
			handlePanelChange(map.get(source), source.isSelected() ? 1 : 0);
			return;
		}	
	}

	/* (non-Javadoc)
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 * 
	 * handles combo boxes
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void itemStateChanged(ItemEvent e) {
		
		if (e.getSource().getClass().equals(JComboBox.class)){
			JComboBox es = (JComboBox) e.getSource();
			String name = map.get(es);
			if (es == null)
				return;
			handlePanelChange(name, es.getSelectedIndex());
		}
	}

	/* (non-Javadoc)
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 * 
	 * Handles sliders and spinners
	 */
	@Override
	public void stateChanged(ChangeEvent e) {
		if (e.getSource().getClass().equals(JSlider.class)) {
			JSlider source = (JSlider) e.getSource();
			if (!source.getValueIsAdjusting()) {
				handlePanelChange(map.get(source), source.getValue());
			}
			return;
		}
		if (e.getSource().getClass().equals(JSpinner.class)) {
			JSpinner source = (JSpinner) e.getSource();
				handlePanelChange(map.get(source), (Integer) source.getValue());
			return;
		}

	}

	/**
	 * Register ths new control with the maps.
	 * 
	 * @param component
	 *            the component
	 * @param name
	 *            the name
	 */
	public void registerAdd(JComponent component, String name) {
		map.put(component, name);
		byName.put(name, component);
		component.setAlignmentY(Component.TOP_ALIGNMENT);
		component.setAlignmentX(Component.LEFT_ALIGNMENT);
		activePanel.add(component);
		
	}
	
	/**
	 * Gets the control by name.
	 * 
	 * @param s
	 *            the s
	 * @return the by name
	 */
	public JComponent getByName(String s)
	{
		return byName.get(s);
	}
	
	/**
	 * Side by side.
	 * begin a sub panel with horizontal layout
	 */
	public void sideBySide()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		panelStack.push(activePanel);
		activePanel = panel;
	}
	
	/**
	 * End side by side layout.
	 */
	public void endSideBySide()
	{
		
		panelStack.peek().add(activePanel);
		activePanel = panelStack.pop();
	}

	/**
	 * Creates a label component .
	 * 
	 * @param label
	 *            the text of the label
	 * @return the label control
	 */
	public JLabel createLabel(String label) {
		JLabel lblShow = new JLabel(label);
		lblShow.setAlignmentY(Component.TOP_ALIGNMENT);
		lblShow.setAlignmentX(Component.LEFT_ALIGNMENT);
		activePanel.add(lblShow);
		return lblShow;

	}
	
	/**
	 * Creates a button.
	 * 
	 * @param label
	 *            the label text
	 * @return the Jbutton control
	 */
	public JButton createButton(String label) {
		JButton button = new JButton(label);
		button.setMaximumSize(new Dimension(100,20));
		button.addActionListener(this);
		registerAdd(button, label);
		return button;
	}
	
	/**
	 * Creates a spinner.
	 * 
	 * @param label
	 *            the label text
	 * @param inputRange
	 *            the input range - a set of numeric choices
	 * @param defaultIndex
	 *            the default index  - Not Yet Implemented
	 */
	public void createSpinner(String label, int[] inputRange, int defaultIndex)
	{
		ArrayList<Integer> range = new ArrayList<Integer>();
		for (int i : inputRange )
		{
			range.add(new Integer(i));
		}
        SpinnerModel snl = new SpinnerListModel(range);
        JSpinner spnList = new JSpinner(snl);	
        spnList.setValue(defaultIndex);
 //       sideBySide();
        activePanel.add(new JLabel(label));
 //       endSideBySide();
        
        spnList.addChangeListener(this);
        registerAdd(spnList, label);
	}


	/**
	 * Creates a checkbox.
	 * 
	 * @param label
	 *            the label text
	 * @param value
	 *            the initial value
	 * @return the checkbox control
	 */
	public JCheckBox createCheckbox(String label, boolean value) {
		JCheckBox checkBox = new JCheckBox(label);
		checkBox.setSelected(value);
		checkBox.addActionListener(this);
		registerAdd(checkBox, label);
		return checkBox;

	}

	/**
	 * Creates a slider.
	 * 
	 * @param label
	 *            the label
	 * @param min
	 *            the min value
	 * @param max
	 *            the max value
	 * @param value
	 *            the value starting
	 * @param labeled
	 *            whether or not the slider should be labeled
	 */
	public void createSlider(String label, int min, int max, int value, boolean labeled) {
		JLabel sliderLabel = new JLabel(label);
		sliderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		if (labeled) activePanel.add(sliderLabel);
		registerAdd(sliderLabel, "Label "+label);

		JSlider slider = new JSlider();
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setMinorTickSpacing((min-max)/8);
		slider.setMajorTickSpacing((min - max) / 4);
		slider.setPaintTicks(true);
		slider.setValue(value);
		slider.setPaintLabels(true);
	
		slider.addChangeListener(this);
		registerAdd(slider, label);
	}

	/**
	 * Creates an enum combo.
	 * 
	 * @param <T>
	 *            the generic type
	 * @param values
	 *            the values in the combo
	 * @param label
	 *            the label text 
	 * @param defaultValue
	 *            the default value
	 */
	public <T extends Enum<?>> void createEnumCombo(T[] values, String label,
			T defaultValue) {
		JComboBox<T> enumCombo = new JComboBox<T>();
		enumCombo.setModel(new DefaultComboBoxModel<T>(values));
		enumCombo.setSelectedItem(defaultValue);
		enumCombo.addItemListener(this);
		registerAdd(enumCombo, label);
	}
	
	/**
	 * Creates a combo from a List.
	 * 
	 * @param values
	 *            the values
	 * @param label
	 *            the label
	 * @param defaultValue
	 *            the default value
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createCombo(List values, String label,
			int defaultValue) {
		JComboBox combo = new JComboBox();
		combo.setModel(new DefaultComboBoxModel(values.toArray()));
		combo.setSelectedIndex(defaultValue);
		combo.addItemListener(this);
		registerAdd(combo, label);
	}
	
	/**
	 * ChangeValue is the callback function that allows derived classes to respond to 
	 * the controls they  created.  The label is the name given to the control 
	 * at creation time, the value is the index of the new value
	 * 
	 * @param label
	 *            the label
	 * @param value
	 *            the value
	 */
	public abstract void changeValue(String label, Integer value);
	
	/** The last label. */
	String lastLabel = new String();
	
	/** The last value. */
	Integer lastValue = new Integer(-1);
	
	/**
	 * initial panel change handler - removes duplicate calls,
	 * then calls changeValue.
	 * 
	 * @param label
	 *            the label
	 * @param value
	 *            the value
	 * @return true, if processing should continue.
	 */
	public boolean handlePanelChange(String label, Integer value)
	// return false if we should stop processing;
	{
		// don't fire duplicate events.
		if (lastLabel.equals(label) && lastValue == value)
			return false;
		if (label == null)
		{
			Logger.addMessage("label Null in MorbidPanel" + this.toString());
			return false;
		}
		changeValue(label,value);
		lastLabel = label;
		lastValue = value;
		return true;
	}

	// {{ Delegate IChangeNotifier
	/** The notifier. */
	IChangeNotifier notifier = new ChangeNotifier(this);

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#getNotifyList()
	 */
	public IChangeNotifier[] getNotifyList() {
		return notifier.getNotifyList();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#registerListener(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void registerListener(IChangeNotifier listener) {
		notifier.registerListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#unRegisterListener(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void unRegisterListener(IChangeNotifier listener) {
		notifier.unRegisterListener(listener);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#unRegisterFromAll()
	 */
	public void unRegisterFromAll() {
		notifier.unRegisterFromAll();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#registerNotifier(com.bobandthomas.Morbid.utils.IChangeNotifier)
	 */
	public void registerNotifier(IChangeNotifier notifier) {
		notifier.registerNotifier(notifier);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#notifyChange()
	 */
	public void notifyChange() {
		notifier.notifyChange();
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#notifyChange(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	public void notifyChange(MorbidEvent source) {
		notifier.notifyChange(source);
	}

	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.IChangeNotifier#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	public MorbidEvent handleNotify(MorbidEvent source) {
		return notifier.handleNotify(source);
	}
	// }}
	
}
