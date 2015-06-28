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
package com.bobandthomas.Morbid.utils;

import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;

// TODO: Auto-generated Javadoc
/**
 * The Class CLoadableTable.
 * 
 * @author Thomas Kreek
 * @param <T>
 *            the generic type
 */
public abstract class CLoadableTable<T extends CLoadableItem & IPropertyAccessor> extends CLoadableSet<T> implements TableModel {
		

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getRowCount()
	 */
	@Override
	public int getRowCount() {
		return size();
	}
	

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#setValueAt(java.lang.Object, int, int)
	 */
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		get(rowIndex).setProperty(columnIndex, aValue);
		
	}
	

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnCount()
	 */
	@Override
	public int getColumnCount() {
		if (size() == 0) return 0;
			return get(0).getDescriptors().getPropertyCount();
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnName(int)
	 */
	@Override
	public String getColumnName(int columnIndex) {
		if (size() == 0)
			return null;
		return get(0).getDescriptors().getPropertyName(columnIndex);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#getColumnClass(int)
	 */
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		if (size() == 0)
			return null;
		return get(0).getDescriptors().getPropertyClass(columnIndex);
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#isCellEditable(int, int)
	 */
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		if (size() == 0)
			return false;
			return get(0).getDescriptors().isPropertyEditable(columnIndex);
	}
	
	/**
	 * Gets the column width.
	 * 
	 * @param columnIndex
	 *            the column index
	 * @return the column width
	 */
	public int getColumnWidth(int columnIndex)
	{
		if (size() == 0)
			return 0;
		return get(0).getDescriptors().getPropertyPreferredWidth(columnIndex);
		
	}

/* (non-Javadoc)
 * @see javax.swing.table.TableModel#getValueAt(int, int)
 */
@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return get(rowIndex).getProperty(columnIndex);
	}

	/** The model. */
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

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#addTableModelListener(javax.swing.event.TableModelListener)
	 */
	@Override
	public void addTableModelListener(TableModelListener l) {
		model.addTableModelListener(l);
		
	}

	/* (non-Javadoc)
	 * @see javax.swing.table.TableModel#removeTableModelListener(javax.swing.event.TableModelListener)
	 */
	@Override
	public void removeTableModelListener(TableModelListener l) {
		model.removeTableModelListener(l);
		
	}

	/**
	 * Find column.
	 * 
	 * @param columnName
	 *            the column name
	 * @return the int
	 */
	public int findColumn(String columnName) {
		return model.findColumn(columnName);
	}

	
}
