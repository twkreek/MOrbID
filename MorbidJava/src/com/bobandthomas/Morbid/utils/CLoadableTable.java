package com.bobandthomas.Morbid.utils;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

public abstract class CLoadableTable<T extends CLoadableItem & IPropertyAccessor> extends CLoadableSet<T> implements TableModel {
		

	@Override
	public int getRowCount() {
		return size();
	}
	

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		get(rowIndex).setProperty(columnIndex, aValue);
		
	}
	

	@Override
	public int getColumnCount() {
		if (size() == 0) return 0;
			return get(0).getDescriptors().getPropertyCount();
	}

	@Override
	public String getColumnName(int columnIndex) {
		if (size() == 0)
			return null;
		return get(0).getDescriptors().getPropertyName(columnIndex);
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (size() == 0)
			return null;
		return get(0).getDescriptors().getPropertyClass(columnIndex);
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (size() == 0)
			return false;
			return get(0).getDescriptors().isPropertyEditable(columnIndex);
	}

@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return get(rowIndex).getProperty(columnIndex);
	}

	AbstractTableModel model = new AbstractTableModel()
	{
		/*
		 *  referring model specific calls to the outer class to be overriden, 
		 *  while still maintaining notifications and listeners of abstract model
		 */
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return CLoadableTable.this.getColumnClass(columnIndex);
		}

		@Override
		public String getColumnName(int column) {
			return CLoadableTable.this.getColumnName(column);
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return CLoadableTable.this.isCellEditable(rowIndex, columnIndex);
		}

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			CLoadableTable.this.setValueAt(aValue, rowIndex, columnIndex);
		}

		@Override
		public int getColumnCount() {
			return CLoadableTable.this.getColumnCount();
		}

		@Override
		public int getRowCount() {
			return CLoadableTable.this.getRowCount();
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return CLoadableTable.this.getValueAt(rowIndex, columnIndex);
		}
		
	};

	@Override
	public void addTableModelListener(TableModelListener l) {
		model.addTableModelListener(l);
		
	}

	@Override
	public void removeTableModelListener(TableModelListener l) {
		model.removeTableModelListener(l);
		
	}

	public int findColumn(String columnName) {
		return model.findColumn(columnName);
	}

	
}
