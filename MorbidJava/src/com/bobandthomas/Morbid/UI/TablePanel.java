package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.bobandthomas.Morbid.graphics.Material;
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
		
		for (int i = 0; i< model.getColumnCount(); i++)
		{
			int width = t.getColumnWidth(i);
			if (width == 0)
				break;
			table.getColumnModel().getColumn(i).setPreferredWidth(width);
		}
	}

	private void setupRenderers()
	{
		table.setDefaultRenderer(ColorQuad.class, new ColorQuadTableCell().getRenderer());
		table.setDefaultEditor(ColorQuad.class, new ColorQuadTableCell().getEditor());
		table.setDefaultRenderer(Material.class, new MaterialTableCell().getRenderer());
		table.setDefaultEditor(Material.class, new MaterialTableCell().getEditor());

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
