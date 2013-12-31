package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.GobList;
import com.bobandthomas.Morbid.graphics.LabelGob;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.Point3D;

public class GadgetHUD extends Gadget {

	public GadgetHUD() {
		super();
		layer = LayerPosition.LayerFront;
		setName("HUD");
	}

	@Override
	public String getGadgetType() {
		return "HUD";
	}

	@Override
	void Draw(GobList gl) {
		molecule.getEmpirical().reset();
		LabelGob lg = new LabelGob(molecule.getEmpirical().getFormula(), new Point3D(-0.5, -0.5, 1.0));
		lg.setScale(0.1);
		lg.setColor(new ColorQuad(0.5,1.0,0.5));
		gl.add(lg);
		lg = new LabelGob(molecule.getName(), new Point3D(-0.5, 0.5, 1.0));
		lg.setScale(0.05);
		lg.setColor(new ColorQuad(0.5,1.0,0.5));
		gl.add(lg);

	}

}
