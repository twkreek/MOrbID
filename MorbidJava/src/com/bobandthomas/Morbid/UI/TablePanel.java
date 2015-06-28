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

// TODO: Auto-generated Javadoc
/**
 * The Class TablePanel.
 * 
 * @author Thomas Kreek
 * @param <T>
 *            the generic type
 */
public class TablePanel<T extends CLoadableTable<?>> extends JPanel {
	
	/** The model. */
	TableModel model;

	/** The table. */
	JTable table;

	/**
	 * Instantiates a new table panel.
	 * 
	 * @param t
	 *            the t
	 */
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

	/**
	 * Setup renderers.
	 */
	private void setupRenderers()
	{
		table.setDefaultRenderer(ColorQuad.class, new ColorQuadTableCell().getRenderer());
		table.setDefaultEditor(ColorQuad.class, new ColorQuadTableCell().getEditor());
		table.setDefaultRenderer(Material.class, new MaterialTableCell().getRenderer());
		table.setDefaultEditor(Material.class, new MaterialTableCell().getEditor());

	}

	/**
	 * Do dialog.
	 * 
	 * @return the j dialog
	 */
	public JDialog doDialog()
	{
		JDialog dialog = new JDialog();
		dialog.setMinimumSize(new Dimension(300, 200));
		dialog.add(this);
		dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		return dialog;
	}

}
