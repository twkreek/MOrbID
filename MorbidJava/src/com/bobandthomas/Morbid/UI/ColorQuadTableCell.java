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

// TODO: Auto-generated Javadoc
/**
 * The Class ColorQuadTableCell.
 * 
 * @author Thomas Kreek
 */
public class ColorQuadTableCell extends MorbidTableCell {
	
	/**
	 * The Class ColorEditor.
	 * 
	 * @author Thomas Kreek
	 */
	public class ColorEditor extends MCellEditor implements
			TableCellEditor, ActionListener {
		
		/** The button. */
		JButton button;
		
		/** The color chooser. */
		JColorChooser colorChooser;
		
		/** The dialog. */
		JDialog dialog;
		
		/** The Constant EDIT. */
		protected static final String EDIT = "edit";

		/**
		 * Instantiates a new color editor.
		 */
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

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
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
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidTableCell.MCellEditor#getCellEditorValue()
		 */
		public Object getCellEditorValue() {
			return currentColor;
		}

		// Implement the one method defined by TableCellEditor.
		/* (non-Javadoc)
		 * @see com.bobandthomas.Morbid.UI.MorbidTableCell.MCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
		 */
		public Component getTableCellEditorComponent(JTable table,
				Object value, boolean isSelected, int row, int column) {
			currentColor = (ColorQuad) value;
			return button;
		}
	}

	/**
	 * The Class ColorQuadCellRenderer.
	 * 
	 * @author Thomas Kreek
	 */
	class ColorQuadCellRenderer extends MCellRenderer {
		
		/** The label. */
		JLabel label;
		
		/** The unselected border. */
		Border unselectedBorder = null;
		
		/** The selected border. */
		Border selectedBorder = null;
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.UI.MorbidTableCell.MCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
	 */
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

	/** The current color. */
	ColorQuad currentColor;

	/**
	 * Instantiates a new color quad table cell.
	 */
	public ColorQuadTableCell() {
		super();
		editor = new ColorEditor();
		renderer = new ColorQuadCellRenderer();
	}

}
