package com.bobandthomas.Morbid.graphics;

import java.util.HashMap;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.utils.CLoadableSet;

public class GobListSet extends CLoadableSet<GobList> {
	
	HashMap<Gadget, GobList> map = new HashMap<Gadget, GobList>();
	
	public GobListSet()
	{
		super();
	//	setUseByName(true);
	}
	@Override
	public void clear() {
		super.clear();
		map.clear();
		}
	public void SetRotate(boolean r) { 
		for ( GobList gl: this)
			gl.setRotate(r);
		}
	public void SetSort(boolean s){
		
		for ( GobList gl: this)
			gl.SetSort(s);
	}
	
	public void createGadgetGL(Gadget g)
	{
		GobList gl = new GobList();
		gl.setName(g.getName());
		gl.setLayer(g.getLayer());
		map.put(g, gl);
		add(gl);
	}
	public GobList getGadgetGl(Gadget g)
	{
		return map.get(g);
	}

}
