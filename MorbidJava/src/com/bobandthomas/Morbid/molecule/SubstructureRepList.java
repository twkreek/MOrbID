package com.bobandthomas.Morbid.molecule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.wrapper.ColorQuadTableCell;
import com.bobandthomas.Morbid.wrapper.MorbidTableCell;

public class SubstructureRepList extends CLoadableSet<SubstructureRep> implements TableModel, TableModelListener{
	
	SubstructureSet substructureSet;
	
	HashMap<Substructure, SubstructureRep> repMap;
	class CellTool 
	{
		public String name;
		@SuppressWarnings("rawtypes")
		public Class classType;
		public MorbidTableCell mtc;
		public boolean editable;
		CellTool()
		{
			
		}
		CellTool(String n, @SuppressWarnings("rawtypes") Class c, MorbidTableCell m, boolean e)
		{
			name = n;
			classType = c;
			mtc = m;
			editable = e;
		}
	}
	static ArrayList<CellTool> cells = null;
	void setupTableCells()
	{
		cells = new ArrayList<CellTool>();
		cells.add(new CellTool("Name", String.class, null, false));
		cells.add( new CellTool("Visible", Boolean.class, null, true));
		cells.add(new CellTool("Color", ColorQuad.class, new ColorQuadTableCell(), true));
		
	}
	
	
	public SubstructureRepList(SubstructureSet inList) {
		setupTableCells();
		substructureSet = inList;
		repMap = new HashMap<Substructure, SubstructureRep>();
		for (Substructure s: substructureSet)
		{
			SubstructureRep rep = new SubstructureRep(s,true, new ColorQuad(s.getListColor()));
			repMap.put(s, rep);
			add(rep);
			if (s.getName().equals("other"))
				rep.setVisible(false);
			
		}
	}
	public SubstructureSet getSubstructureList() {
		return substructureSet;
	}
	public boolean isVisible(Atom a)
	{
		return repMap.get(substructureSet.getSubstructure(a)).visible;
	}
	public boolean isVisible(Substructure s)
	{
		return repMap.get(s).visible;
	}
	public ColorQuad getColor(Substructure s)
	{
		return new ColorQuad( repMap.get(s).color);
	}
	public ColorQuad getColor(Atom a)
	{
		return new ColorQuad(repMap.get(substructureSet.getSubstructure(a)).color);
		
	}
	public SubstructureRep get(Substructure s)
	{
		return repMap.get(s);
	}
	
	public void inheritReps()
	{
		repMap = new HashMap<Substructure, SubstructureRep>();
		for (Substructure s: substructureSet)
		{
			SubstructureRep rep = new SubstructureRep(s, true, new ColorQuad(s.getListColor()));
			repMap.put(s, rep);
			
		}
		
	}
	@Override
	public Iterator<SubstructureRep> iterator() {
		return repMap.values().iterator();
	}
	@Override
	public void addTableModelListener(TableModelListener l) {
		
	}
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return cells.get(columnIndex).classType;
	}
	@Override
	public int getColumnCount() {
		return cells.size();
	}
	@Override
	public String getColumnName(int columnIndex) {
		return cells.get(columnIndex).name;
	}
	@Override
	public int getRowCount() {
		return size();
	}
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		SubstructureRep rep = get(rowIndex);
		switch (columnIndex)
		{
		case 0: return rep.substructure.getName();
		case 1: return rep.visible;
		case 2: return rep.color;
		}
		return null;
	}
	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return cells.get(columnIndex).editable;
	}
	@Override
	public void removeTableModelListener(TableModelListener l) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		SubstructureRep rep = get(rowIndex);
		switch (columnIndex)
		{
		case 1: 
			rep.setVisible((Boolean)aValue);
		break;
		case 2: rep.setColor((ColorQuad) aValue);
		}
		return;
		
	}
	@Override
	public void tableChanged(TableModelEvent e) {
		// TODO Auto-generated method stub
		
	}

}
