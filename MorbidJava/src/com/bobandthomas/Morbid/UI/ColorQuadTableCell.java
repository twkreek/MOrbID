package com.bobandthomas.Morbid.UI;

import java.awt.Component;
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

import com.bobandthomas.Morbid.utils.ColorQuad;

public class ColorQuadTableCell extends MorbidTableCell {
	public class ColorEditor extends MCellEditor implements
			TableCellEditor, ActionListener {
		JButton button;
		JColorChooser colorChooser;
		JDialog dialog;
		protected static final String EDIT = "edit";

		public ColorEditor() {
			button = new JButton();
			button.setActionCommand(EDIT);
			button.addActionListener(this);
			button.setBorderPainted(false);

			// Set up the dialog that the button brings up.
			colorChooser = new JColorChooser();
			dialog = JColorChooser.createDialog(button, "Pick a Color", true, // modal
					colorChooser, this, // OK button handler
					null); // no CANCEL button handler
		}

		public void actionPerformed(ActionEvent e) {
			if (EDIT.equals(e.getActionCommand())) {
				// The user has clicked the cell, so
				// bring up the dialog.
				button.setBackground(currentColor.getJColor());
				colorChooser.setColor(currentColor.getJColor());
				dialog.setVisible(true);

				fireEditingStopped(); // Make the renderer reappear.

			} else { // User pressed dialog's "OK" button.
				currentColor = new ColorQuad(colorChooser.getColor());
			}
		}

		// Implement the one CellEditor method that AbstractCellEditor doesn't.
		public Object getCellEditorValue() {
			return currentColor;
		}

		// Implement the one method defined by TableCellEditor.
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			currentColor = (ColorQuad) value;
			return button;
		}
	}

	class ColorQuadCellRenderer extends MCellRenderer {
		JLabel label;
		Border unselectedBorder = null;
		Border selectedBorder = null;
	@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			ColorQuad newColor = (ColorQuad) value;
			label = new JLabel();
			label.setOpaque(true);
			label.setBackground(newColor.getJColor());
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

	ColorQuad currentColor;

	public ColorQuadTableCell() {
		super();
		editor = new ColorEditor();
		renderer = new ColorQuadCellRenderer();
	}

}
