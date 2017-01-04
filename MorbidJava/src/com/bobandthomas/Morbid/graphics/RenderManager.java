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
package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.graphics.renderers.Port;
import com.bobandthomas.Morbid.graphics.renderers.Renderer;
import com.bobandthomas.Morbid.utils.BoundingBox;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.ColorQuad;

// TODO: Auto-generated Javadoc
/**
 * The Class RenderManager.
 * 
 * @author Thomas Kreek
 */
public class RenderManager {

	/**
	 * The Enum RenderManagerMode.
	 * 
	 * @author Thomas Kreek
	 */
	public enum RenderManagerMode {

		/** The render edit. */
		renderEdit, /** The render rotate. */
		renderRotate, /** The render view. */
		renderView, /** The render hq. */
		renderHQ, /** The render trace. */
		renderTrace, /** The render vrml. */
		renderVRML, /** The render mode max. */
		renderModeMax
	};

	/**
	 * The Enum RenderManagerQuality.
	 * 
	 * @author Thomas Kreek
	 */
	public enum RenderManagerQuality {

		/** The render draft. */
		renderDraft, 
		/** The render normal. */
		renderNormal, 
		/** The render high. */
		renderHigh, 
		/** The render ray trace. */
		renderRayTrace, 
		/** The render quality vrml. */
		renderQualityVRML, 
		/** The render quality max. */
		renderQualityMax
	};

	/**
	 * The Class RenderManagerDirtyFlag.
	 * 
	 * @author Thomas Kreek
	 */
	public class RenderManagerDirtyFlag {

		/** The gob list. */
		public boolean gobList;

		/** The onscreen bm. */
		public boolean onscreenBM;

		/** The offscreen bm. */
		public boolean offscreenBM;

		/** The view ctm. */
		public boolean viewCTM;

		/** The total ctm. */
		public boolean totalCTM;

		/** The world box. */
		public boolean worldBox;

		/** The lighting model. */
		public boolean lightingModel;

		/**
		 * Checks if is dirty.
		 * 
		 * @return true, if successful
		 */
		boolean IsDirty() {
			return gobList || onscreenBM || offscreenBM || viewCTM || totalCTM
					|| worldBox || lightingModel;
		}
	};

	/** The render mode. */
	RenderManagerMode renderMode;

	/** The world box. */
	BoundingBox worldBox;

	/** The zoom. */
	double /* Coord */zoom;

	/**
	 * Gets the zoom.
	 * 
	 * @return the zoom
	 */
	public double getZoom() {
		return zoom;
	}

	/**
	 * Sets the zoom.
	 * 
	 * @param zoom
	 *            the new zoom
	 */
	public void setZoom(double zoom) {
		this.zoom = zoom;
		GetRenderer().setZoom(zoom);
	}

	/** The viewing port. */
	Port viewingPort;

	/** The current renderer. */
	Renderer currentRenderer;

	/** The bg color. */
	ColorQuad bgColor;

	/** The render quality. */
	java.util.HashMap<RenderManagerMode, RenderManagerQuality> renderQuality = new java.util.HashMap<RenderManagerMode, RenderManagerQuality>();

	/** The render. */
	java.util.HashMap<RenderManagerQuality, Renderer> render = new java.util.HashMap<RenderManagerQuality, Renderer>(); // we
	// own,
	// we
	// delete;

	/**
	 * The
	 * dirty
	 * .
	 */
	public RenderManagerDirtyFlag dirty;

	/** The Background color. */
	ColorQuad BackgroundColor;

	// static RenderTypeList *GetGlobalRendererTypes();
	/**
	 * Gets the render mode.
	 * 
	 * @return the render manager mode
	 */
	RenderManagerMode GetRenderMode() {
		return renderMode;
	}

	/**
	 * Adds the renderer.
	 * 
	 * @param rq
	 *            the rq
	 * @param renderer
	 *            the renderer
	 */
	public void AddRenderer(RenderManagerQuality rq, Renderer renderer) {
		render.put(rq, renderer);
	}

	/**
	 * Instantiates a new render manager.
	 */
	public RenderManager() {
		renderMode = RenderManagerMode.renderView;
		renderQuality.put(RenderManagerMode.renderEdit,
				RenderManagerQuality.renderDraft);
		renderQuality.put(RenderManagerMode.renderRotate,
				RenderManagerQuality.renderDraft);
		renderQuality.put(RenderManagerMode.renderView,
				RenderManagerQuality.renderHigh);
		renderQuality.put(RenderManagerMode.renderHQ,
				RenderManagerQuality.renderHigh);
		renderQuality.put(RenderManagerMode.renderTrace,
				RenderManagerQuality.renderRayTrace);
		renderQuality.put(RenderManagerMode.renderVRML,
				RenderManagerQuality.renderQualityVRML);
		/*
		 * RenderTypeList *rt = GetGlobalRendererTypes(); for (int i = 0; i <
		 * rt.GetCount(); i++) { render.Add( ((RenderType
		 * *)rt.GetAt(i)).Create(NULL)); }
		 * 
		 * for (int i=0; i< render.GetSize(); i++) {
		 * render[i].SetName(render[i].GetTypeName()); }
		 */
		dirty = new RenderManagerDirtyFlag();
		viewingPort = null;
		zoom = 1.0;

		bgColor = new ColorQuad();
	}

	/**
	 * Gets the renderer.
	 * 
	 * @return the renderer
	 */
	public Renderer GetRenderer() {
		RenderManagerQuality rq = renderQuality.get(renderMode);
		currentRenderer = render.get(rq);
		return currentRenderer;
	}

	/**
	 * Gets the renderer.
	 * 
	 * @param rm
	 *            the rm
	 * @return the renderer
	 */
	Renderer GetRenderer(RenderManagerMode rm) {
		currentRenderer = render.get(renderQuality.get(rm));
		return currentRenderer;
	}

	/**
	 * Gets the renderer.
	 * 
	 * @param rq
	 *            the rq
	 * @return the renderer
	 */
	Renderer GetRenderer(RenderManagerQuality rq) {
		currentRenderer = render.get(rq);
		return currentRenderer;
	}

	/**
	 * Sets the render mode.
	 * 
	 * @param srm
	 *            the srm
	 * @param update
	 *            the update
	 */
	public void SetRenderMode(RenderManagerMode srm, boolean update) {
		// if (renderQuality[renderMode] == renderQuality[srm])
		// return OKCODE;
		SetPort(null);
		dirty.gobList = true;
		renderMode = srm;
		GetRenderer().SetWorldBox(worldBox);
		GetRenderer().Rescale();
		GetRenderer().Resize();

	}

	/**
	 * Do render.
	 * 
	 * @param gl
	 *            the gl
	 * @param lightSources
	 *            the light sources
	 * @param viewCTM
	 *            the view ctm
	 */
	public void DoRender(GobListSet gl, LightSourceList lightSources,
			CTM viewCTM) {
		// PIStatusPane::SetStatusPane(1, GetRenderer().Name, false);

		if (gl.size() == 0)
			return;

		Renderer ren = GetRenderer();
		ren.SetWorldBox(worldBox);
		ren.Resize();
		ren.setZoom(zoom);
		ren.Rescale();
		ren.bgColor = bgColor;

		if (dirty.offscreenBM || dirty.gobList || dirty.viewCTM
				|| dirty.lightingModel || gl.isDirty()) {
			GetRenderer().DoRender(gl, lightSources, viewCTM);
			dirty.viewCTM = dirty.lightingModel = dirty.gobList = false;
			dirty.onscreenBM = true;
		}

		// GetRenderer().SetPort(null);
		// PIStatusPane::ClearStatusPane(1);
	}

	/**
	 * Resize.
	 */
	public void Resize() {
	}

	/**
	 * Sets the port.
	 * 
	 * @param port
	 *            the port
	 */
	public void SetPort(Port port) {
		if (viewingPort == port)
			return;
		viewingPort = port;
		GetRenderer().SetPort(viewingPort);
		if (port != null)
			dirty.onscreenBM = true;
	}

	/**
	 * Sets the world box.
	 * 
	 * @param wb
	 *            the wb
	 */
	public void SetWorldBox(BoundingBox wb) {
		worldBox = new BoundingBox(wb);
		GetRenderer().SetWorldBox(worldBox);
		dirty.worldBox = true;

	}
}
