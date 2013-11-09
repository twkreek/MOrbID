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

public abstract class MorbidPanel extends JPanel implements ChangeListener,
ItemListener, ActionListener  {

	HashMap<JComponent, String> map;
	protected JPanel child;
	protected JPanel tempChild;

	public MorbidPanel(String name) {
		map = new HashMap<JComponent, String>();

		setBorder(new CompoundBorder());
		setLayout(new BorderLayout());
		child = new JPanel(new GridLayout(0,1,0,0)); // by default child is a vertical panel;
		add(child);
		//This layout can be replaced in the constructor of the subclass

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource().getClass().equals(JCheckBox.class)) {
			JCheckBox source = (JCheckBox) e.getSource();
			handlePanelChange(map.get(source), source.isSelected() ? 1 : 0);
			return;
		}	
		if (e.getSource().getClass().equals(JButton.class)) {
			JButton source = (JButton) e.getSource();
			handlePanelChange(map.get(source), source.isSelected() ? 1 : 0);
			return;
		}	
	}

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

	public void register(JComponent component, String name) {
		map.put(component, name);
	}
	public void sideBySide()
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(0,2));
		tempChild = child;
		child = panel;
	}
	public void endSideBySide()
	{
		tempChild.add(child);
		child = tempChild;
	}

	public void createLabel(String label) {
		JLabel lblShow = new JLabel(label);
		lblShow.setAlignmentY(Component.TOP_ALIGNMENT);
		lblShow.setHorizontalAlignment(SwingConstants.LEFT);
		child.add(lblShow);

	}
	public void createButton(String label) {
		JButton button = new JButton(label);
		button.setAlignmentY(Component.TOP_ALIGNMENT);
		button.setHorizontalAlignment(SwingConstants.CENTER);
		button.setMaximumSize(new Dimension(100,20));
		register(button, label);
		button.addActionListener(this);
		child.add(button);
	}
	
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


	public void createCheckbox(String label, boolean value) {
		JCheckBox checkBox = new JCheckBox(label);
		checkBox.setHorizontalAlignment(SwingConstants.LEFT);
		checkBox.setSelected(value);
		checkBox.addActionListener(this);
		child.add(checkBox);
		register(checkBox, label);

	}

	public void createSlider(String label, int min, int max, int value, boolean labeled) {
		JLabel sliderLabel = new JLabel(label);
		sliderLabel.setHorizontalAlignment(SwingConstants.LEFT);
		if (labeled) child.add(sliderLabel);

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

	public <T extends Enum<?>> void createEnumCombo(T[] values, String label,
			T defaultValue) {
		JComboBox<T> enumCombo = new JComboBox<T>();
		enumCombo.setModel(new DefaultComboBoxModel<T>(values));
		enumCombo.setSelectedItem(defaultValue);
		enumCombo.addItemListener(this);
		register(enumCombo, label);
		child.add(enumCombo);
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void createCombo(ArrayList values, String label,
			int defaultValue) {
		JComboBox combo = new JComboBox();
		combo.setModel(new DefaultComboBoxModel(values.toArray()));
		combo.setSelectedItem(defaultValue);
		combo.addItemListener(this);
		register(combo, label);
		child.add(combo);
	}
	public abstract void changeValue(String label, Integer value);
	String lastLabel = new String();
	Integer lastValue = new Integer(-1);
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
	

}
