package com.bobandthomas.Morbid.graphics;

import java.util.ArrayList;
import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;

public class GobListLayers extends ArrayList<GobListSet> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4414636376801414307L;
	/**
	 * 
	 */
	
	public GobListLayers(){
		
	}
	public GobListSet get(LayerPosition lp)
	{
		return this.get(lp.ordinal());
	}
	public void put(LayerPosition lp, GobListSet gl) {
		this.add(lp.ordinal(), gl);
	}
	

}
