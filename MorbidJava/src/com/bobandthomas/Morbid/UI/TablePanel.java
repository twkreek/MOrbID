package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;

public class TablePanel<T extends CLoadableTable<?>> extends JPanel {
	TableModel model;

	JTable table;

	public TablePanel(CLoadableTable<?> t) {
		model = t;
		setLayout(new BorderLayout());
		table = new JTable(model);
		setupRenderers();
		add(table.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(table), BorderLayout.CENTER);
	}

	private void setupRenderers()
	{
		table.setDefaultRenderer(ColorQuad.class, new ColorQuadTableCell().getRenderer());
		table.setDefaultEditor(ColorQuad.class, new ColorQuadTableCell().getEditor());
	}

	public JDialog doDialog()
	{
		JDialog dialog = new JDialog();
		dialog.setMinimumSize(new Dimension(300, 200));
		dialog.add(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		return dialog;
	}

}
