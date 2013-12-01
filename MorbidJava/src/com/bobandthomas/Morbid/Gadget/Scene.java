package com.bobandthomas.Morbid.Gadget;

import com.bobandthomas.Morbid.graphics.CTM;
import com.bobandthomas.Morbid.graphics.GobListLayers;
import com.bobandthomas.Morbid.graphics.GobListSet;
import com.bobandthomas.Morbid.graphics.LightSource;
import com.bobandthomas.Morbid.graphics.LightSourceList;
import com.bobandthomas.Morbid.graphics.RenderManager;
import com.bobandthomas.Morbid.graphics.RenderManager.RenderManagerMode;
import com.bobandthomas.Morbid.graphics.RenderManager.RenderManagerQuality;
import com.bobandthomas.Morbid.graphics.renderers.Port;
import com.bobandthomas.Morbid.graphics.renderers.Renderer;
import com.bobandthomas.Morbid.utils.CLoadableItem;
import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;
import com.bobandthomas.Morbid.molecule.Molecule;

public class Scene extends CLoadableItem {

	public enum LayerPosition {
		LayerBack(0), LayerModel(1), LayerFront(2);
		// LayerMax (3);
		int layer;

		LayerPosition(int i) {
			layer = i;
		}
	};

	public class SceneDirtyFlag {
		boolean gadgetList;
		boolean gobList;
		boolean lightingModel;
		boolean lights;
		boolean materials;
		boolean offscreenBM;
		boolean onscreenBM;
		boolean totalCTM;
		boolean viewCTM;
		boolean worldBox;

		void Clear() {
			set(false);
		}

		boolean get() {
			return gadgetList | gobList | lightingModel | lights | offscreenBM
					| onscreenBM | totalCTM | viewCTM | worldBox;
		}

		boolean IsDirty() {
			return gadgetList | gobList | lightingModel | lights | offscreenBM
					| onscreenBM | totalCTM | viewCTM | worldBox;
		}

		void set(boolean value) {
			gadgetList = gobList = lightingModel = lights = offscreenBM = onscreenBM = totalCTM = viewCTM = worldBox = value;

		}
	}

	public enum SceneDirtyTypes {

		gadgetList, gobList, lightingModel, lights, materials, offscreenBM, onscreenBM, totalCTM, viewCTM, worldBox;
	}

	private boolean rendering = false;
	ColorQuad backgroundColor;
	Renderer currentRenderer;
	SceneDirtyFlag dirty;
	GadgetList gadgetList; // we own, we delete;
	GobListLayers glLayerSet = new GobListLayers();

	LightSourceList lightSources; // we own, we delete;

	Molecule molecule; // borrowed
	
	public double getZoom() { return rm.getZoom();}
	public void setZoom(double z) { rm.setZoom(z); markDirty();}

	boolean pauseRender;

	// Port port;

	public boolean isPauseRender() {
		return pauseRender;
	}

	public void setPauseRender(boolean pauseRender) {
		this.pauseRender = pauseRender;
	}

	RenderManager rm;
	CTM totalCTM;

	public Scene() {
		gadgetList = new GadgetList(this);
		gadgetList.registerListener(this);
		molecule = null;
		totalCTM = new CTM();
		rm = new RenderManager();
		dirty = new SceneDirtyFlag();

		setName("Scene");

		lightSources = new LightSourceList();
		lightSources.registerListener(this);
		LightSource ls;
		ls = new LightSource(new ColorQuad(255, 200, 150));
		ls.setName("Pinkish Light");
		lightSources.add(ls);
		ls = new LightSource(new ColorQuad(150, 200, 255), new Point3D(20.0f,
				-10.0f, -40.0f));
		ls.setName("Bluish light");
		lightSources.add(ls);

		// ls = new LightSource(new ColorQuad(0, 0, 255), new Point3D(-20.0f,
		// 10.0f, 30.0f));
		// ls.setName("blue light");
		// lightSources.add(ls);

		for (LayerPosition l : LayerPosition.values()) {
			glLayerSet.put(l, new GobListSet());
		}
		glLayerSet.get(LayerPosition.LayerBack.layer).SetSort(false);
		glLayerSet.get(LayerPosition.LayerModel.layer).SetSort(true);
		glLayerSet.get(LayerPosition.LayerFront.layer).SetSort(false);

		glLayerSet.get(LayerPosition.LayerBack.layer).SetRotate(false);
		glLayerSet.get(LayerPosition.LayerModel.layer).SetRotate(true);
		glLayerSet.get(LayerPosition.LayerFront.layer).SetRotate(false);

		dirty.gadgetList = true;
		dirty.gobList = true;
		dirty.lights = true;
		dirty.materials = true;
		dirty.onscreenBM = true;
		dirty.offscreenBM = true;

		pauseRender = false;

	}

	public void AddGadget(Gadget g, LayerPosition l) {
		dirty.gadgetList = true;
		dirty.gobList = true;
		gadgetList.add(g);
		glLayerSet.get(l).createGadgetGL(g);
		g.sceneAdded(this);
		for (Gadget gadg : gadgetList)
		{
			//notify all gadgets that this one is being added,
			//so they can set up notifications or change their appearance
			gadg.sceneChanged(this);
		}
	}

	void AddRenderer(RenderManagerQuality rq, Renderer ren) {
		rm.AddRenderer(rq, ren);
	}

	void CreateDefaultGadgets() {
		if (gadgetList != null)
			return;

		gadgetList = new GadgetList(this);

		dirty.gadgetList = true;
		dirty.gobList = true;

	}

	void Drag(Point3D p1, Point3D p2) {
		Vector3D v1 = p1.getVector();
		Vector3D v2 = p2.getVector();
		Vector3D cross;
		double dot, angle;

		// totalCTM.identity();
		dot = v1.Dot(v2);
		if (dot == 1.0)
			return; // nothing to do

		cross = v1.Cross(v2);

		cross.Normalize();
		angle = 2.0 * Math.acos(dot); // angle of rotation
		totalCTM.rotateAboutAxis(cross, angle);
		dirty.viewCTM = true;
		dirty.totalCTM = true;
		dirty.offscreenBM = true;
		rm.dirty.viewCTM = true;
		rm.dirty.onscreenBM = true;
	}

	GadgetList GetGadgetList() {
		return gadgetList;
	}

	LightSourceList GetLightSourceList() {
		return lightSources;
	}

	Molecule GetMolecule() {
		return molecule;
	}

	CTM GetViewCTM() {
		return totalCTM;
	}

	public void Render() {
		// int i;
		// PIWaitCursor waitCursor;
		// if (port == null) return;
		if (molecule == null)
			return;
		if (pauseRender)
			return;
		rendering = true;
		rm.GetRenderer().bgColor = this.backgroundColor;
		rm.SetWorldBox(new BoxType(molecule.GetBounds()));
		rm.GetRenderer().Rescale();
		// PIStatusPane::SetStatusPane(0, "Drawing...", false);
		if (dirty.gadgetList || gadgetList.isDirty()) {
			for (LayerPosition l : LayerPosition.values()) {
				GobListSet glSet = glLayerSet.get(l);
				gadgetList.Draw(glSet, l);
			}
			gadgetList.markDirty();
			dirty.gobList = true;
			dirty.gadgetList = false;
		}
		// PIStatusPane::SetStatusPane(0, "Rendering...",false);
		if (dirty.gobList || dirty.viewCTM || dirty.lights || dirty.materials
				|| dirty.lightingModel || (dirty.onscreenBM)) {
			rm.dirty.onscreenBM = true;
			rm.dirty.gobList = dirty.gobList;
			rm.dirty.viewCTM = dirty.viewCTM;
			rm.dirty.lightingModel = dirty.lights || dirty.materials
					|| dirty.lightingModel;

			for (LayerPosition l : LayerPosition.values())
			{
				GobListSet gLayerSet = glLayerSet.get(l);
				rm.DoRender(gLayerSet, lightSources, totalCTM);
			}

			dirty.gobList = false;
			dirty.viewCTM = false;
			dirty.lights = false;
			dirty.materials = false;
			dirty.lightingModel = false;
			dirty.offscreenBM = false;
			dirty.onscreenBM = true;
		}
		if (dirty.onscreenBM) {
			// port.SwapBuffers();
			dirty.onscreenBM = false;
		}

		// PIStatusPane::ClearStatusPane(0);
		// rm.SetPort(null);
		rendering = false;
	}

	void Resize() {
		rm.Resize();
	}

	public void SetMolecule(Molecule theMol) {
		molecule = theMol;

		SetWorldBox(molecule.GetBounds());
		dirty.gadgetList = true;
		dirty.gobList = true;
		for (Gadget pgadget : gadgetList)
			pgadget.markDirty();
	}

	public void SetPort(Port inport) {
		rm.SetPort(inport);

	}

	public void SetRenderer(Renderer renderer) {
		rm.AddRenderer(RenderManager.RenderManagerQuality.renderNormal,
				renderer);
		rm.AddRenderer(RenderManager.RenderManagerQuality.renderHigh, renderer);
		rm.AddRenderer(RenderManager.RenderManagerQuality.renderQualityMax,
				renderer);
	}

	void SetRenderMode(RenderManagerMode srm, boolean update) {
		rm.SetRenderMode(srm, update);
		if (update) {
			dirty.gobList = true;
			dirty.onscreenBM = true;
			dirty.viewCTM = true;
			Update();
		}
	}

	void SetWorldBox(BoxType wb) {
		dirty.worldBox = true;
		rm.SetWorldBox(wb);
	}
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		if(!rendering)
			Render();
		return super.handleNotify(source);
	}


	void Update() {
	}
}
