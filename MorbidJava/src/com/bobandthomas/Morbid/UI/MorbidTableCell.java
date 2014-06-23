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
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


// TODO: Auto-generated Javadoc
/**
 * The Class MorbidTableCell.
 * 
 * @author Thomas Kreek
 */
public abstract class MorbidTableCell {
	
	/** The value. */
	Object value;
	
	/**
	 * The Class MCellEditor.
	 * 
	 * @author Thomas Kreek
	 */
	class MCellEditor extends  AbstractCellEditor implements TableCellEditor
	{
		
		/**
		 * Instantiates a new m cell editor.
		 */
		public MCellEditor() 
		{
		}

		/* (non-Javadoc)
		 * @see javax.swing.CellEditor#getCellEditorValue()
		 */
		@Override
		public Object getCellEditorValue() {
			return value;
		}

		/* (non-Javadoc)
		 * @see javax.swing.table.TableCellEditor#getTableCellEditorComponent(javax.swing.JTable, java.lang.Object, boolean, int, int)
		 */
		@Override
		public Component getTableCellEditorComponent(JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column){

			return null;
		}

		
	}
	
	/** The editor. */
	TableCellEditor editor = new MCellEditor();
	
	/**
	 * The Class MCellRenderer.
	 * 
	 * @author Thomas Kreek
	 */
	class MCellRenderer extends DefaultTableCellRenderer
	{
		
		/* (non-Javadoc)
		 * @see javax.swing.table.DefaultTableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object, boolean, boolean, int, int)
		 */
		public Component getTableCellRendererComponent(JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column)
		{
			MorbidTableCell.this.value = value;
			return null;
		}
	};
	
	/** The renderer. */
	TableCellRenderer renderer = new MCellRenderer();
	
	/**
	 * Gets the renderer.
	 * 
	 * @return the renderer
	 */
	public TableCellRenderer getRenderer()
	{
		return renderer;
	}
	
	/**
	 * Gets the editor.
	 * 
	 * @return the editor
	 */
	public TableCellEditor getEditor()
	{
		return editor;
	}
	
	/**
	 * Instantiates a new morbid table cell.
	 */
	public MorbidTableCell() {
		renderer = new MCellRenderer();
		editor = new MCellEditor();
	}


}
