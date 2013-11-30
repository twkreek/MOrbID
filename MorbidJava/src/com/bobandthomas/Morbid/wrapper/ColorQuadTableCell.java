package com.bobandthomas.Morbid.wrapper;

import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;

import com.bobandthomas.Morbid.utils.ColorQuad;

public class ColorQuadTableCell extends MorbidTableCell {

	ColorQuad currentColor;
	JLabel label;
    Border unselectedBorder = null;
    Border selectedBorder = null;
    public ColorQuadTableCell() {
		super();
		setOpaque(true);
		label = new JLabel();
	}

	@Override
	public Object getCellEditorValue() {
		// TODO Color Table getCellEditor
		return null;
	}

	@Override
	public Component getTableCellEditorComponent(JTable arg0, Object arg1,
			boolean arg2, int arg3, int arg4) {
		// TODO ColorTable getTableCellEditor
		return null;
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
        ColorQuad newColor = (ColorQuad)value;
        label = new JLabel();
        label.setOpaque(true);
         label.setBackground(newColor.getJColor());
            if (isSelected) {
                if (selectedBorder == null) {
                    selectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getSelectionBackground());
                }
                setBorder(selectedBorder);
            } else {
                if (unselectedBorder == null) {
                    unselectedBorder = BorderFactory.createMatteBorder(2,5,2,5,
                                              table.getBackground());
                }
                setBorder(unselectedBorder);
            }
        return label;
	}

}
