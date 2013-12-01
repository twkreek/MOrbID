package com.bobandthomas.Morbid.graphics;

import com.bobandthomas.Morbid.graphics.renderers.Port;
import com.bobandthomas.Morbid.graphics.renderers.Renderer;
import com.bobandthomas.Morbid.utils.BoxType;
import com.bobandthomas.Morbid.utils.Point3D;
import com.bobandthomas.Morbid.utils.ColorQuad;

public class RenderManager {

	public enum RenderManagerMode {

		renderEdit, renderRotate, renderView, renderHQ, renderTrace, renderVRML, renderModeMax
	};

	public enum RenderManagerQuality {
		renderDraft, renderNormal, renderHigh, renderRayTrace, renderQualityVRML, renderQualityMax
	};

	public class RenderManagerDirtyFlag {
		public boolean gobList;
		public boolean onscreenBM;
		public boolean offscreenBM;
		public boolean viewCTM;
		public boolean totalCTM;
		public boolean worldBox;
		public boolean lightingModel;

		boolean IsDirty() {
			return gobList || onscreenBM || offscreenBM || viewCTM || totalCTM
					|| worldBox || lightingModel;
		}
	};

	RenderManagerMode renderMode;
	BoxType worldBox;
	CTM viewCTM;
	CTM totalCTM;
	Point3D offset;
	double /* Coord */zoom;

	public double getZoom() {
		return zoom;
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
		GetRenderer().setZoom(zoom);
	}

	Port viewingPort;
	Renderer currentRenderer;

	ColorQuad bgColor;
	java.util.HashMap<RenderManagerMode, RenderManagerQuality> renderQuality = new java.util.HashMap<RenderManagerMode, RenderManagerQuality>();
	java.util.HashMap<RenderManagerQuality, Renderer> render = new java.util.HashMap<RenderManagerQuality, Renderer>(); // we
																														// own,
																														// we
																														// delete;

	public RenderManagerDirtyFlag dirty;
	ColorQuad BackgroundColor;

	// static RenderTypeList *GetGlobalRendererTypes();
	RenderManagerMode GetRenderMode() {
		return renderMode;
	}

	public void AddRenderer(RenderManagerQuality rq, Renderer renderer) {
		render.put(rq, renderer);
	}

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

	public Renderer GetRenderer() {
		RenderManagerQuality rq = renderQuality.get(renderMode);
		currentRenderer = render.get(rq);
		return currentRenderer;
	}

	Renderer GetRenderer(RenderManagerMode rm) {
		currentRenderer = render.get(renderQuality.get(rm));
		return currentRenderer;
	}

	Renderer GetRenderer(RenderManagerQuality rq) {
		currentRenderer = render.get(rq);
		return currentRenderer;
	}

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

	public void DoRender(GobListSet gl, LightSourceList lightSources,
			CTM viewCTM) {
		// PIStatusPane::SetStatusPane(1, GetRenderer().Name, false);

		if (gl.size() == 0)
			return;

		Renderer ren = GetRenderer();
		ren.SetWorldBox(worldBox);
		ren.Resize();
		ren.setZoom(zoom);
		ren.setTranslate(offset);
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

	public void Resize() {
	}

	public void SetPort(Port port) {
		if (viewingPort == port)
			return;
		viewingPort = port;
		GetRenderer().SetPort(viewingPort);
		if (port != null)
			dirty.onscreenBM = true;
	}

	public void SetWorldBox(BoxType wb) {
		worldBox = new BoxType(wb);
		GetRenderer().SetWorldBox(worldBox);
		dirty.worldBox = true;

	}
}
