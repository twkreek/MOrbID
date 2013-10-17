package com.bobandthomas.Morbid.molecule;

import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.StaticColorQuad;

public class Substructure extends AtomList {
	
	private Fragment fragment;
	ColorQuad listColor;  // the generic color used for substructure coloring.
	public Substructure() {
		// TODO Auto-generated constructor stub
		listColor = StaticColorQuad.LiteGray.cq();
		setReParent(false);
	}
	public ColorQuad getListColor() {
		return listColor;
	}
	public void setListColor(ColorQuad listColor) {
		this.listColor = listColor;
	}
	Fragment getFragment() {
		return fragment;
	}
	void setFragment(Fragment fragment) {
		this.fragment = fragment;
	}


}
