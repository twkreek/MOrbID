package com.bobandthomas.Morbid.UI;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public abstract class MorbidTableCell {
	Object value;
	class MCellEditor extends  AbstractCellEditor implements TableCellEditor
	{
		public MCellEditor() 
		{
		}

		@Override
		public Object getCellEditorValue() {
			return value;
		}

		@Override
		public Component getTableCellEditorComponent(JTable table,
                Object value,
                boolean isSelected,
                int row,
                int column){

			return null;
		}

		
	}
	TableCellEditor editor = new MCellEditor();
	class MCellRenderer extends DefaultTableCellRenderer
	{
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
	TableCellRenderer renderer = new MCellRenderer();
	
	public TableCellRenderer getRenderer()
	{
		return renderer;
	}
	public TableCellEditor getEditor()
	{
		return editor;
	}
	public MorbidTableCell() {
		renderer = new MCellRenderer();
		editor = new MCellEditor();
	}


}
