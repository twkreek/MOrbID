package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.Gadget.Scene.LayerPosition;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.utils.CLoadableSet;

public class GadgetList extends CLoadableSet<Gadget>{
	
	Scene scene;
	
	GadgetList()
	{
	}
	GadgetList(Scene sc)
	{
		scene = sc;
	}
	Scene GetScene() { return scene; }

	

	void Draw(GobListSet gobListSet, LayerPosition lp)
	{
		for (Gadget pGadget: this)
		{
			if ((pGadget != null) && (pGadget.getLayer() == lp) && pGadget.isDirty())
			{
					pGadget.DrawGadget(gobListSet.getGadgetGl(pGadget)); //Gadget handles invisibility.
			}
		} 
	}
	@Override
	public void notifyChange()
	{
		super.notifyChange();
		scene.Render();  // render after change notification
		
	}
	void InvalidateAll()
	{
		for  (Gadget g: this)
		{
			g.markDirty();
		}
	 
	}

}
