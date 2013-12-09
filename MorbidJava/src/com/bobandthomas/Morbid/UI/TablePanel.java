package com.bobandthomas.Morbid.UI;

import java.awt.BorderLayout;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import com.bobandthomas.Morbid.utils.CLoadableTable;

public class TablePanel<T extends CLoadableTable<?>> extends JPanel {
	TableModel model;

	JTable repTable;

	public TablePanel(CLoadableTable<?> t) {
		model = t;
		setLayout(new BorderLayout());
		repTable = new JTable(model);
		add(repTable.getTableHeader(), BorderLayout.PAGE_START);
		add(new JScrollPane(repTable), BorderLayout.CENTER);
	}


	public JDialog doDialog()
	{
		JDialog dialog = new JDialog();
		dialog.add(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		return dialog;
	}

}
