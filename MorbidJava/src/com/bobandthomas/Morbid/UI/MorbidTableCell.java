package com.bobandthomas.Morbid.UI;

import java.awt.Component;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;


public class MorbidTableCell extends DefaultTableCellRenderer implements TableCellRenderer, TableCellEditor {
	class MCellEditor extends AbstractCellEditor 
	{

		@Override
		public Object getCellEditorValue() {
			// TODO return CellValue
			return null;
		}
		
	}
	MCellEditor editor;
	
	// {{ Renderer
	public Object getCellEditorValue() {
		return editor.getCellEditorValue();
	}

	public MorbidTableCell() {
		setOpaque(true);
		editor = new MCellEditor();
	}

	public void addCellEditorListener(CellEditorListener l) {
		editor.addCellEditorListener(l);
	}

	public void cancelCellEditing() {
		editor.cancelCellEditing();
		
	}

	public boolean isCellEditable(EventObject anEvent) {
		return editor.isCellEditable(anEvent);
	}

	@Override
	public void removeCellEditorListener(CellEditorListener l) {
		editor.removeCellEditorListener(l);	
	}

	@Override
	public boolean shouldSelectCell(EventObject anEvent) {
		return editor.shouldSelectCell(anEvent);
	}
	// }}
	
	// {{ Editor

	@Override
	public boolean stopCellEditing() {
		return editor.stopCellEditing();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value,
			boolean isSelected, int row, int column)
	{
		return null;
	}
	// }}
	public class Point3DTableCell extends MorbidTableCell
	{
		Point3DTableCell()
		{
			super();
		}
		
	}

}
