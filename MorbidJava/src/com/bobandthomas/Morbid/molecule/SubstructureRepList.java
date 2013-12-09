package com.bobandthomas.Morbid.molecule;

import java.util.HashMap;
import java.util.Iterator;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;

public class SubstructureRepList extends CLoadableTable<SubstructureRep> implements TableModel, TableModelListener{
	
	SubstructureSet substructureSet;
	
	HashMap<Substructure, SubstructureRep> repMap;

	
	public SubstructureRepList(SubstructureSet inList) {
		super();
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
	public void tableChanged(TableModelEvent arg0) {
		// TODO Auto-generated method stub
		
	}



}
