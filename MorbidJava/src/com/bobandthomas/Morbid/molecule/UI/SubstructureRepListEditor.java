package com.bobandthomas.Morbid.molecule.UI;

import java.awt.BorderLayout;

import javax.swing.DefaultCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import com.bobandthomas.Morbid.UI.ColorQuadTableCell;
import com.bobandthomas.Morbid.molecule.SubstructureMap;
import com.bobandthomas.Morbid.molecule.SubstructureRepList;

public class SubstructureRepListEditor extends SubstructureSetChooser {
	
	JTable repTable;

	public SubstructureRepListEditor(SubstructureMap ssm,
			SubstructureRepList rep) {
		super(ssm, rep);
		repTable = new JTable(rep);
		repTable.getColumnModel().getColumn(2).setCellRenderer(new ColorQuadTableCell().getRenderer());
		repTable.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(new JCheckBox()));
		add(repTable, BorderLayout.CENTER);
		
	}

}
