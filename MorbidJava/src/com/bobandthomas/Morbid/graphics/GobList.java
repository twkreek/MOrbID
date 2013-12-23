package com.bobandthomas.Morbid.graphics;


import java.util.Collection;

import com.bobandthomas.Morbid.Gadget.Gadget;
import com.bobandthomas.Morbid.Gadget.Scene;
import com.bobandthomas.Morbid.utils.CLoadableSet;
import com.bobandthomas.Morbid.utils.Point3D;

public class GobList extends CLoadableSet<Gob> {

		@Override
	public boolean add(Gob arg0) {
		markDirty();
		return super.add(arg0);
	}
	@Override
	public boolean addAll(Collection<? extends Gob> arg0) {
		markDirty();
		return super.addAll(arg0);
	}

		Gadget gadget; //the gadget that generates this goblist.
		boolean rotate;
		boolean sort;
		CTM thectm;
		Scene.LayerPosition layer;
		public void setRotate(boolean r) { rotate = r; }
		public boolean IsRotate() { return rotate; }
		public void SetSort(boolean s) { sort = s; }
		public Scene.LayerPosition getLayer() {
			return layer;
		}
		public void setLayer(Scene.LayerPosition layer) {
			this.layer = layer;
		}

// Convenience functions for creating gobs.



		public Gadget getGadget() {
			return gadget;
		}
		public void setGadget(Gadget gadget) {
			this.gadget = gadget;
		}
		public GobList()
		{   
			thectm = new CTM();

			rotate = true;
			notifier.logThis(false);
		}

		void AddVector(Point3D from, Point3D to)
		{   
			GobVector vg = new GobVector(from,to);
			add( vg );
		}

		void AddLabel(String s, Point3D pos)
		{
			LabelGob lg = new LabelGob(s, pos);
			add(lg);
		}

		void AddString(String s, Point3D pos)
		{
			StringGob lg = new StringGob(s, pos);
			add(lg);
		}

		GobList Append(GobList gl)
		{
			addAll(gl);
			return this;
		}

}
