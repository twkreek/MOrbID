package com.bobandthomas.Morbid.molecule;

import java.util.HashMap;
import com.bobandthomas.Morbid.utils.CLoadableTable;
import com.bobandthomas.Morbid.utils.ColorQuad;

public class SubstructureRepList extends CLoadableTable<SubstructureRep> 
{
	
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



}
