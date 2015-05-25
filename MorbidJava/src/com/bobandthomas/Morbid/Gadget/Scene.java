/*
 * 
	MOrbID - Molecular Orbital Interactive Display

MOrbID is Copyright (c) 1996-2014 by Thomas W. Kreek


Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

 */
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
import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.ColorQuad;
import com.bobandthomas.Morbid.utils.IChangeNotifier;
import com.bobandthomas.Morbid.utils.MorbidEvent;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.Vector3D;
import com.bobandthomas.Morbid.molecule.Molecule;

// TODO: Auto-generated Javadoc
/**
 * The Class Scene.
 * 
 * @author Thomas Kreek
 */
public class Scene extends CLoadableItem implements IChangeNotifier {

	/**
	 * The Enum LayerPosition.
	 * 
	 * @author Thomas Kreek
	 */
	public enum LayerPosition {
		
		/** The Layer back. */
		LayerBack(0), 
 /** The Layer model. */
 LayerModel(1), 
 /** The Layer front. */
 LayerFront(2);
		// LayerMax (3);
		/** The layer. */
		int layer;

		/**
		 * Instantiates a new layer position.
		 * 
		 * @param i
		 *            the i
		 */
		LayerPosition(int i) {
			layer = i;
		}
	};

	/**
	 * The Class SceneDirtyFlag.
	 * 
	 * @author Thomas Kreek
	 */
	public class SceneDirtyFlag {
		
		/** The gadget list. */
		boolean gadgetList;
		
		/** The gob list. */
		boolean gobList;
		
		/** The lighting model. */
		boolean lightingModel;
		
		/** The lights. */
		boolean lights;
		
		/** The materials. */
		boolean materials;
		
		/** The offscreen bm. */
		boolean offscreenBM;
		
		/** The onscreen bm. */
		boolean onscreenBM;
		
		/** The total ctm. */
		boolean totalCTM;
		
		/** The view ctm. */
		boolean viewCTM;
		
		/** The world box. */
		boolean worldBox;

		/**
		 * Clear.
		 */
		void Clear() {
			set(false);
		}

		/**
		 * Gets the.
		 * 
		 * @return true, if successful
		 */
		boolean get() {
			return gadgetList | gobList | lightingModel | lights | offscreenBM
					| onscreenBM | totalCTM | viewCTM | worldBox;
		}

		/**
		 * Checks if is dirty.
		 * 
		 * @return true, if successful
		 */
		boolean IsDirty() {
			return gadgetList | gobList | lightingModel | lights | offscreenBM
					| onscreenBM | totalCTM | viewCTM | worldBox;
		}

		/**
		 * Sets the.
		 * 
		 * @param value
		 *            the value
		 */
		void set(boolean value) {
			gadgetList = gobList = lightingModel = lights = offscreenBM = onscreenBM = totalCTM = viewCTM = worldBox = value;

		}
	}

	/**
	 * The Enum SceneDirtyTypes.
	 * 
	 * @author Thomas Kreek
	 */
	public enum SceneDirtyTypes {

		/** The gadget list. */
		gadgetList, /** The gob list. */
 gobList, /** The lighting model. */
 lightingModel, /** The lights. */
 lights, /** The materials. */
 materials, /** The offscreen bm. */
 offscreenBM, /** The onscreen bm. */
 onscreenBM, /** The total ctm. */
 totalCTM, /** The view ctm. */
 viewCTM, /** The world box. */
 worldBox;
	}

	/** The rendering. */
	private boolean rendering = false;
	
	/** The background color. */
	ColorQuad backgroundColor;
	
	/** The current renderer. */
	Renderer currentRenderer;
	
	/** The dirty. */
	SceneDirtyFlag dirty;
	
	/** The gadget list. */
	GadgetList gadgetList; // we own, we delete;
	
	/** The gl layer set. */
	GobListLayers glLayerSet = new GobListLayers();

	/** The light sources. */
	LightSourceList lightSources; // we own, we delete;

	/** The molecule. */
	Molecule molecule; // borrowed
	
	/**
	 * Gets the zoom.
	 * 
	 * @return the zoom
	 */
	public double getZoom() { return rm.getZoom();}
	
	/**
	 * Sets the zoom.
	 * 
	 * @param z
	 *            the new zoom
	 */
	public void setZoom(double z) { rm.setZoom(z); markDirty();}

	/** The pause render. */
	boolean pauseRender;


	/**
	 * Checks if is pause render.
	 * 
	 * @return true, if is pause render
	 */
	public boolean isPauseRender() {
		return pauseRender;
	}

	/**
	 * Sets the pause render.
	 * 
	 * @param pauseRender
	 *            the new pause render
	 */
	public void setPauseRender(boolean pauseRender) {
		this.pauseRender = pauseRender;
	}

	/** The rm. */
	RenderManager rm;
	
	/** The total ctm. */
	CTM totalCTM;

	/**
	 * Instantiates a new scene.
	 */
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

	/**
	 * Adds the gadget.
	 * 
	 * @param g
	 *            the g
	 * @param l
	 *            the l
	 */
	public void AddGadget(Gadget g, LayerPosition l) {
		dirty.gadgetList = true;
		dirty.gobList = true;
		gadgetList.add(g);
		glLayerSet.get(l).createGadgetGL(g);
		g.setScene(this);
		g.registerListener(this);
		for (Gadget gadg : gadgetList)
		{
			//notify all gadgets that this one is being added,
			//so they can set up notifications or change their appearance
			if (g != gadg)
				gadg.sceneChanged(this);
		}
		MorbidEvent event = new MorbidEvent(this, "Added", null , g);
		notifyChange(event);
	}

	/**
	 * Creates a default gadgets.
	 */
	void CreateDefaultGadgets() {
		if (gadgetList != null)
			return;

		gadgetList = new GadgetList(this);

		dirty.gadgetList = true;
		dirty.gobList = true;

	}

	/**
	 * Drag.
	 * 
	 * @param p1
	 *            the p1
	 * @param p2
	 *            the p2
	 */
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

	/**
	 * Gets the gadget list.
	 * 
	 * @return the gadget list
	 */
	GadgetList GetGadgetList() {
		return gadgetList;
	}

	/**
	 * Gets the light source list.
	 * 
	 * @return the light source list
	 */
	LightSourceList GetLightSourceList() {
		return lightSources;
	}

	/**
	 * Gets the molecule.
	 * 
	 * @return the molecule
	 */
	public Molecule GetMolecule() {
		return molecule;
	}

	/**
	 * Gets the view ctm.
	 * 
	 * @return the ctm
	 */
	CTM GetViewCTM() {
		return totalCTM;
	}

	/**
	 * Render.
	 */
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
		rm.SetWorldBox(new BoundingBox(molecule.GetBounds()));
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
	public Renderer GetRenderer()
	{
		return rm.GetRenderer();
	}

	/**
	 * Resize.
	 */
	void Resize() {
		rm.Resize();
	}

	/**
	 * Sets the molecule.
	 * 
	 * @param theMol
	 *            the the mol
	 */
	public void SetMolecule(Molecule theMol) {
		molecule = theMol;
		SetWorldBox(molecule.GetBounds());
		dirty.gadgetList = true;
		dirty.gobList = true;
		for (Gadget pgadget : gadgetList)
			pgadget.markDirty();
	}

	/**
	 * Sets the port.
	 * 
	 * @param inport
	 *            the inport
	 */
	public void SetPort(Port inport) {
		rm.SetPort(inport);

	}

	/**
	 * Adds the renderer.
	 * 
	 * @param rq
	 *            the rq
	 * @param ren
	 *            the ren
	 */
	void AddRenderer(RenderManagerQuality rq, Renderer ren) {
		rm.AddRenderer(rq, ren);
	}
	Renderer prevRenderer;
	public void PushRenderer(Renderer r)
	{
		prevRenderer = currentRenderer;
		SetRenderer(r);
	}
	public void PopRenderer()
	{
		SetRenderer(prevRenderer);
		prevRenderer=null;
	}

	/**
	 * Sets the renderer.
	 * 
	 * @param renderer
	 *            the renderer
	 */
	public void SetRenderer(Renderer renderer) {
		currentRenderer = renderer;
		rm.AddRenderer(RenderManager.RenderManagerQuality.renderNormal,
				renderer);
		rm.AddRenderer(RenderManager.RenderManagerQuality.renderHigh, renderer);
		rm.AddRenderer(RenderManager.RenderManagerQuality.renderQualityMax,
				renderer);
	}

	/**
	 * Sets the render mode.
	 * 
	 * @param srm
	 *            the srm
	 * @param update
	 *            the update
	 */
	void SetRenderMode(RenderManagerMode srm, boolean update) {
		rm.SetRenderMode(srm, update);
		if (update) {
			dirty.gobList = true;
			dirty.onscreenBM = true;
			dirty.viewCTM = true;
			Update();
		}
	}

	/**
	 * Sets the world box.
	 * 
	 * @param wb
	 *            the wb
	 */
	void SetWorldBox(BoundingBox wb) {
		if (rm == null)
			return;
		dirty.worldBox = true;
		rm.SetWorldBox(wb);
	}
	
	/* (non-Javadoc)
	 * @see com.bobandthomas.Morbid.utils.CLoadableItem#handleNotify(com.bobandthomas.Morbid.utils.MorbidEvent)
	 */
	@Override
	public MorbidEvent handleNotify(MorbidEvent source) {
		if(!rendering)
			Render();
		return super.handleNotify(source);
	}


	/**
	 * Update.
	 */
	void Update() {
	}
	
}
