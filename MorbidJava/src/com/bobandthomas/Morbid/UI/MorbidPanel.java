package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
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

/**
 * The Class MorbidPanel.
 * 
 * @author Thomas Kreek
 * 
 *        base level panel for control panels.  Contains convenience functions to create
 *        basic controls consisten with overall Morbid L&F.  This panel is used in dialogs
 *        as well as the side panel.  
 *        All controls use this class as a listener. 
 *        Derived classes override ChangeValue to handle events from controls they created
 */
public abstract class MorbidPanel extends JPanel implements ChangeListener,
ItemListener, ActionListener, IChangeNotifier  {

	/** The map of component(control) to the string */
	HashMap<JComponent, String> map;
	
	/** by name lookup for controls. */
	HashMap<String, JComponent> byName;
	
	/** The child panel that contains the controls. */
	protected JPanel child;
	
	/** The temp child - used when making sub panels. */
	protected JPanel tempChild;

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
		setLayout(new BorderLayout());
		child = new JPanel(new GridLayout(0,1,0,0)); // by default child is a vertical panel;
		add(child);
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
	public void register(JComponent component, String name) {
		map.put(component, name);
		byName.put(name, component);
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
		tempChild = child;
		child = panel;
	}
	
	/**
	 * End side by side layout
	 */
	public void endSideBySide()
	{
		
		tempChild.add(child);
		child = tempChild;
	}

	/**
	 * Creates a label component 
	 * 
	 * @param label
	 *            the text of the label
	 * @return the label control
	 */
	public JLabel createLabel(String label) {
		JLabel lblShow = new JLabel(label);
		lblShow.setAlignmentY(Component.TOP_ALIGNMENT);
		lblShow.setHorizontalAlignment(SwingConstants.LEFT);
		child.add(lblShow);
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
		button.setAlignmentY(Component.TOP_ALIGNMENT);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setMaximumSize(new Dimension(100,20));
		register(button, label);
		button.addActionListener(this);
		child.add(button);
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
//TODO        spnList.setValue(defaultIndex);
        sideBySide();
        child.add(new JLabel(label));
        child.add(spnList);
        endSideBySide();
        
        spnList.addChangeListener(this);
        register(spnList, label);
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
		checkBox.setHorizontalAlignment(SwingConstants.LEFT);
		checkBox.setSelected(value);
		checkBox.addActionListener(this);
		child.add(checkBox);
		register(checkBox, label);
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
		if (labeled) child.add(sliderLabel);
		register(sliderLabel, "Label "+label);

		JSlider slider = new JSlider();
		slider.setMinimum(min);
		slider.setMaximum(max);
		slider.setMinorTickSpacing((min-max)/8);
		slider.setMajorTickSpacing((min - max) / 4);
		slider.setPaintTicks(true);
		slider.setValue(value);
		slider.setPaintLabels(true);
		child.add(slider);

		slider.addChangeListener(this);
		register(slider, label);
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
		register(enumCombo, label);
		child.add(enumCombo);
	}
	
	/**
	 * Creates a combo from a List
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
		combo.setSelectedItem(defaultValue);
		combo.addItemListener(this);
		register(combo, label);
		child.add(combo);
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
			System.out.println("label Null");
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
