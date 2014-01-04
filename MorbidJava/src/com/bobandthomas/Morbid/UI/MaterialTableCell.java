package com.bobandthomas.Morbid.UI;

import java.awt.Component;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellEditor;

import com.bobandthomas.Morbid.graphics.Material;
import com.bobandthomas.Morbid.graphics.renderers.JPanelPort;

public class MaterialTableCell extends MorbidTableCell {
	Material currentMaterial;
	public class MaterialEditor extends MorbidPanel
	{
		JPanelPort preview;
		JButton colorButton;
		MaterialEditor(Material currentMaterial)
		{
			super("Material");
			preview = new JPanelPort();
			colorButton = createButton("Color");
			colorButton.setBackground(currentMaterial.getColor().getJColor());;
			createSlider("Diffuse", 0, 100, (int) (currentMaterial.getkDiffuse()*100), true); 
			createSlider("Ambient", 0, 100, (int) (currentMaterial.getkAmbient()*100), true); 
			createSlider("Emission", 0, 100, (int) (currentMaterial.getkEmission()*100), true); 
			createSlider("Specular", 0, 100, (int) (currentMaterial.getKSpecular()*100), true);
			createSlider("Shininess", 0, 128, currentMaterial.getSpecularity(), true);
		}	
		JDialog getDialog()
		{
			JDialog dlg = new JDialog(null, Dialog.ModalityType.APPLICATION_MODAL);
			dlg.setLayout(new FlowLayout());
			dlg.add(this);
			dlg.add(preview.getPanel());
			return dlg;
		}
		
		@Override
		public void changeValue(String label, Integer value) {
			if (label.equals("Color"))
			{
				JColorChooser colorChooser;
				JDialog dialog;

				colorChooser = new JColorChooser();
				dialog = JColorChooser.createDialog(colorButton, "Pick a Color", true, // modal
						colorChooser, this, // OK button handler
						null); // no CANCEL button handler
				dialog.setVisible(true);
			
			}
		}
	}

	public class MaterialTableCellEditor extends MCellEditor implements TableCellEditor,
			ActionListener {
		JButton button;
		MaterialEditor colorChooser;
		JDialog dialog;
		protected static final String EDIT = "edit";

		public MaterialTableCellEditor() {
			button = new JButton();
			button.setActionCommand(EDIT);
			button.addActionListener(this);
			button.setBorderPainted(false);

		}

		public void actionPerformed(ActionEvent e) {
			if (EDIT.equals(e.getActionCommand())) {
				// The user has clicked the cell, so
				// bring up the dialog.
				button.setBackground(currentMaterial.getColor().getJColor());
				// Set up the dialog that the button brings up.
				colorChooser = new MaterialEditor(currentMaterial);
				dialog = colorChooser.getDialog();
				dialog.setVisible(true);

				fireEditingStopped(); // Make the renderer reappear.

			} else { // User pressed dialog's "OK" button.
//				currentMaterial.setColor(new ColorQuad(colorChooser.getColor()));
			}
		}

		// Implement the one CellEditor method that AbstractCellEditor doesn't.
		public Object getCellEditorValue() {
			return currentMaterial;
		}

		// Implement the one method defined by TableCellEditor.
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			currentMaterial = (Material) value;
			return button;
		}
	}

	class MaterialCellRenderer extends MCellRenderer {
		JLabel label;
		Border unselectedBorder = null;
		Border selectedBorder = null;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			currentMaterial = (Material) value;
			label = new JLabel();
			label.setOpaque(true);
			label.setBackground(currentMaterial.getColor().getJColor());
			if (isSelected) {
				if (selectedBorder == null) {
					selectedBorder = BorderFactory.createMatteBorder(2, 5, 2,
							5, table.getSelectionBackground());
				}
				label.setBorder(selectedBorder);
			} else {
				if (unselectedBorder == null) {
					unselectedBorder = BorderFactory.createMatteBorder(2, 5, 2,
							5, table.getBackground());
				}
				label.setBorder(unselectedBorder);
			}
			return label;
		}
	}


	public MaterialTableCell() {
		super();
		editor = new MaterialTableCellEditor();
		renderer = new MaterialCellRenderer();
	}

}
